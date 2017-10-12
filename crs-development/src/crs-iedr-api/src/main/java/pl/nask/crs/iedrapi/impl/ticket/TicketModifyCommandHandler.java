package pl.nask.crs.iedrapi.impl.ticket;

/*>>>import org.checkerframework.checker.nullness.qual.Nullable;*/

import java.util.List;

import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.api.vo.AuthenticatedUserVO;
import pl.nask.crs.app.ValidationException;
import pl.nask.crs.app.tickets.exceptions.*;
import pl.nask.crs.commons.dns.exceptions.*;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.iedrapi.*;
import pl.nask.crs.iedrapi.exceptions.*;
import pl.nask.crs.iedrapi.impl.domain.DomainValidationHelper;
import pl.nask.crs.nichandle.exception.NicHandleException;
import pl.nask.crs.nichandle.exception.NicHandleIncorrectForAccountException;
import pl.nask.crs.nichandle.exception.NicHandleNotActiveException;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.exceptions.TicketAlreadyCheckedOutException;
import pl.nask.crs.ticket.exceptions.TicketEditFlagException;
import pl.nask.crs.ticket.exceptions.TicketHolderChangeException;
import pl.nask.crs.ticket.exceptions.TicketNotFoundException;
import pl.nask.crs.ticket.operation.IpFieldChange;
import pl.nask.crs.ticket.operation.NameserverChange;
import pl.nask.crs.ticket.operation.SimpleDomainFieldChange;
import pl.nask.crs.ticket.operation.TicketEdit;

import ie.domainregistry.ieapi_1.ResponseType;
import ie.domainregistry.ieapi_ticket_1.*;
import ie.domainregistry.ieapicom_1.ContactAttrType;

import static pl.nask.crs.commons.utils.Validator.isEmpty;

/**
 * Handler for ticket:modify iedr-api command.
 *
 * This class is not thread safe!
 *
 * @author Artur Gniadzik
 *
 */
public class TicketModifyCommandHandler extends AbstractTicketCommandHandler implements APICommandHandler<ModifyType> {

    @Override
    public ResponseType handle(AuthData auth, ModifyType command, ValidationCallback callback)
            throws AccessDeniedException, IedrApiException {
        String domainName = command.getName();
        if (isEmpty(domainName))
            throw new RequiredParameterMissingException(ReasonCode.TICKET_NAME_IS_MANDATORY_FIELD);
        try {
            ApiValidator.assertNoError(callback);
            /*
             * FIXME: since the ticket HAS TO be checked-out before it can be modified,
             * we have to perform checkOut, modify and checkIn. Every operation updates ticket history,
             * so we have to wait 1 second between every operation (operation date is part of the key)
             *
             * Also, the requirement not to allow deletion tickets to be modified is
             * currently present only in the IEDR-API (no such assumptions in the CRS-API are made)
             */
            AuthenticatedUserVO user = auth.getUser();
            Ticket ticket = getTicketForDomain(user, domainName);
            checkTicket(user, ticket, domainName);
            TicketEdit ticketEdit = new TicketEdit(ticket.getOperation());
            String holderRemarks = null;
            boolean documentsWereSent = false;
            // change data
            if (command.getChg() != null) {
                ChgType chg = command.getChg();
                HolderType holder = chg.getHolder();
                if (holder != null) {
                    if (!isEmpty(holder.getHolderRemarks())) {
                        holderRemarks = holder.getHolderRemarks();
                    }
                    ticketEdit.getDomainHolderField().setNewValue(holder.getHolderName());
                }
                if (chg.getStatus() != null) {
                    switch (chg.getStatus()) {
                        case DOCSENT:
                            documentsWereSent = true;
                            break;
                        default:
                            throw new ParamValuePolicyErrorException("Unknown admin status");
                    }
                }
            }

            // remove data
            if (command.getRem() != null) {
                RemType rem = command.getRem();
                for (ContactType c: rem.getContact()) {
                    switch (c.getType()) {
                        case ADMIN:
                            removeContact(ticketEdit.getAdminContactField(), c);
                            break;
                        case TECH:
                            removeContact(ticketEdit.getTechContactField(), c);
                            break;
                        default:
                            throw new ParamValuePolicyErrorException("Unknown Contact type: " + c.getType());
                    }
                }

                for (NsNameType ns: rem.getNs()) {
                    removeNs(ticketEdit, ns);
                }
            }

            // add data
            if (command.getAdd() != null) {
                AddType add = command.getAdd();
                for (ContactType c: add.getContact()) {
                    switch (c.getType()) {
                    case ADMIN:
                        addContact(ticketEdit.getAdminContactField(), c);
                        break;
                    case TECH:
                        addContact(ticketEdit.getTechContactField(), c);
                        break;
                    default:
                        throw new ParamValuePolicyErrorException("Unknown Contact type: " + c.getType());
                    }
                }

                for (NsType ns: add.getNs()) {
                    addNs(ticketEdit, ns);
                }
            }

            getTicketAppService().updateAsOwner(user, ticket.getId(), ticketEdit, holderRemarks,
                    "Modified with IEDR-API", ticket.isClikPaid(), documentsWereSent);
        } catch (TicketNotFoundException e) {
            throw new ParamValuePolicyErrorException(ReasonCode.TICKET_NAME_DOES_NOT_EXIST,
                    new Value("name", TICKET_NAMESPACE, command.getName()));
        } catch (TicketAlreadyCheckedOutException e) {
            throw new ParamValuePolicyErrorException(ReasonCode.TICKET_IN_USE);
        } catch (EmptyRemarkException | TicketEditFlagException e) {
            throw new CommandFailed(e);
        } catch (HolderRemarkTooLongException e) {
            throw new ParamValuePolicyErrorException(ReasonCode.HOLDER_REMARK_TOO_LONG);
        } catch (DomainHolderMandatoryException e) {
            throw new ParamValuePolicyErrorException(ReasonCode.DOMAIN_HOLDER_MANDATORY);
        } catch (TooFewContactsException e) {
            throw DomainValidationHelper.mapExceptionForTooFewContacts(e);
        } catch (TooManyContactsException e) {
            throw DomainValidationHelper.mapExceptionForTooManyContacts(e);
        } catch (DuplicatedContactException e) {
            throw new ParamValuePolicyErrorException(ReasonCode.DUPLICATE_CONTACT_ID, new Value("contact",
                    TICKET_NAMESPACE, e.getNicHandleId()));
        } catch (ContactSyntaxException e) {
            throw new ParamValuePolicyErrorException(ReasonCode.CONTACT_ID_SYNTAX_ERROR, new Value("contact",
                    TICKET_NAMESPACE, e.getNicHandleId()));
        } catch (NicHandleNotFoundException e) {
            throw new ParameterValueRangeErrorException(ReasonCode.CONTACT_ID_DOESNT_EXIST, new Value("contact",
                    TICKET_NAMESPACE, e.getNicHandleId()));
        } catch (TooFewNameserversException e) {
            throw new DataManagementPolicyViolationException(ReasonCode.TOO_FEW_DNS);
        } catch (TooManyNameserversException e) {
            throw new DataManagementPolicyViolationException(ReasonCode.TOO_MANY_DNS);
        } catch (DuplicatedNameserverException e) {
            throw new DataManagementPolicyViolationException(ReasonCode.DUPLICATE_DNS_NAME, new Value("nsName",
                    TICKET_NAMESPACE, e.getNsName()));
        } catch (NameserverNameSyntaxException e) {
            throw new ParamValueSyntaxErrorException(ReasonCode.DNS_SYNTAX_ERROR, new Value("nsName", TICKET_NAMESPACE,
                    e.getNsName()));
        } catch (IpSyntaxException e) {
            throw new ParamValueSyntaxErrorException(ReasonCode.IP_SYTAX_ERROR, new Value("nsAddr", TICKET_NAMESPACE,
                    e.getIpAddress()));
        } catch (GlueNotAllowedException e) {
            throw new DataManagementPolicyViolationException(ReasonCode.GLUE_NOT_ALLOWED, new Value("nsName",
                    TICKET_NAMESPACE, e.getNsName()));
        } catch (GlueRequiredException e) {
            throw new DataManagementPolicyViolationException(ReasonCode.GLUE_IS_REQUIRED, new Value("nsName",
                    TICKET_NAMESPACE, e.getNsName()));
        } catch (NicHandleNotActiveException e) {
            throw new ParamValuePolicyErrorException(ReasonCode.CONTACT_NOT_ACTIVE, new Value("contact",
                    TICKET_NAMESPACE, e.getNicHandleId()));
        } catch (NicHandleIncorrectForAccountException e) {
            throw new ParamValuePolicyErrorException(ReasonCode.CONTACT_MANAGED_BY_ANOTHER_RESELLER, new Value("contact",
                    TICKET_NAMESPACE, e.getNicHandleId()));
        } catch (TicketHolderChangeException e) {
            throw new ParamValuePolicyErrorException(ReasonCode.HOLDER_MODIFICATION_NOT_ALLOWED);
        } catch (AccountNotFoundException | DomainNotFoundException | NicHandleException | ValidationException e) {
            // Should never happen
            throw new CommandFailed(e);
        }
        return ResponseTypeFactory.success();
    }

    private void addNs(TicketEdit ticketEdit, NsType ns)
            throws ParamValuePolicyErrorException, ParamValueSyntaxErrorException {
        final String name = ns.getNsName();
        if (Validator.isEmpty(name)) {
            throw new ParamValueSyntaxErrorException(ReasonCode.DNS_SYNTAX_ERROR,
                    new Value("nsName", TICKET_NAMESPACE, name));
        }
        if (!ticketEdit.containsNameserver(name)) {
            final List<String> ipAddresses = ns.getNsAddr();
            final String ipv6 = ns.getNsAddr6();
            ticketEdit.addNameserverChange(new NameserverChange(new SimpleDomainFieldChange<>(null, name),
                    new IpFieldChange(null, isEmpty(ipAddresses) ? null : ipAddresses.get(0)),
                    new IpFieldChange(null, ipv6)));
        } else {
            throw new ParamValuePolicyErrorException(ReasonCode.TICKET_ALREADY_DELEGATED_TO_HOST_TO_ADD, new Value(
                    "nsName", TICKET_NAMESPACE, name));
        }
    }

    private void removeNs(TicketEdit ticketEdit, NsNameType ns) throws ParamValuePolicyErrorException {
        if (ticketEdit.containsNameserver(ns.getNsName())) {
            ticketEdit.removeNameserverChange(ns.getNsName());
        } else {
            throw new ParamValuePolicyErrorException(ReasonCode.TICKET_NOT_DELEGATED_TO_HOST_TO_REMOVE, new Value(
                    "nsName", TICKET_NAMESPACE, ns.getNsName()));
        }
    }

    private void addContact(List<SimpleDomainFieldChange<String>> list, ContactType c)
            throws ParamValuePolicyErrorException, DataManagementPolicyViolationException {
        SimpleDomainFieldChange<String> cvo = findContact(list, c);
        if (cvo != null) {
            throw new ParamValuePolicyErrorException(
                    c.getType() == ContactAttrType.ADMIN ? ReasonCode.ADMINC_TO_ADD_ALREADY_ASSOCIATED_WITH_TICKET
                            : ReasonCode.TECHC_TO_ADD_ALREADY_ASSOCIATED_WITH_TICKET, new Value("contact",
                            TICKET_NAMESPACE, c.getValue()));
        } else {
            cvo = findContact(list, null); // find the first empty place to add this contact
            if (cvo != null) {
                cvo.setNewValue(c.getValue());
            } else {
                throw new DataManagementPolicyViolationException(
                        c.getType() == ContactAttrType.ADMIN ? ReasonCode.TOO_MANY_ADMIN_CONTACTS
                                : ReasonCode.TOO_MANY_TECH_CONTACTS);
            }
        }
    }

    private void removeContact(List<SimpleDomainFieldChange<String>> list, ContactType c)
            throws ParamValuePolicyErrorException {
        SimpleDomainFieldChange<String> cvo = findContact(list, c);
        if (cvo != null) {
            cvo.setNewValue(null);
        } else
            throw new ParamValuePolicyErrorException(c.getType() == ContactAttrType.ADMIN ? ReasonCode.ADMINC_TO_REMOVE_NOT_ASSOCIATED_WITH_TICKET : ReasonCode.TECHC_TO_REMOVE_NOT_ASSOCIATED_WITH_TICKET, new Value("contact", TICKET_NAMESPACE, c.getValue()));
    }


    private /*>>>@Nullable*/ SimpleDomainFieldChange<String> findContact(List<SimpleDomainFieldChange<String>> list,
            /*>>>@Nullable*/ ContactType c) {
        for (int i = 0; i < list.size(); i++) {
            SimpleDomainFieldChange<String> cvo = list.get(i);
            if (c == null && (cvo.getNewValue() == null || isEmpty(cvo.getNewValue()))) {
                return cvo;
            } else if (cvo.getNewValue() != null && c != null && cvo.getNewValue().equals(c.getValue())) {
                return cvo;
            }
        }
        return null;
    }

}
