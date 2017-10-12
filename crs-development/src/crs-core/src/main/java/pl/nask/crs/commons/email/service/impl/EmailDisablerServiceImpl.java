package pl.nask.crs.commons.email.service.impl;

import java.util.*;

import org.apache.log4j.Logger;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;

import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.email.EmailDisabler;
import pl.nask.crs.commons.email.EmailGroup;
import pl.nask.crs.commons.email.EmailTemplate;
import pl.nask.crs.commons.email.NicHandleDetails;
import pl.nask.crs.commons.email.dao.EmailDisablerDAO;
import pl.nask.crs.commons.email.dao.EmailTemplateDAO;
import pl.nask.crs.commons.email.dao.HistoricalEmailDisablerDAO;
import pl.nask.crs.commons.email.search.*;
import pl.nask.crs.commons.email.service.*;
import pl.nask.crs.history.HistoricalObject;

/*>>>import org.checkerframework.checker.nullness.qual.NonNull;*/

public class EmailDisablerServiceImpl implements EmailDisablerService {

    public static final Logger LOG = Logger.getLogger(EmailDisablerServiceImpl.class);

    EmailTemplateDAO emailTemplateDao;
    EmailDisablerDAO dao;
    HistoricalEmailDisablerDAO histDao;

    private EmailTemplateSender sender;

    public EmailDisablerServiceImpl(EmailDisablerDAO dao, HistoricalEmailDisablerDAO histDao,
            EmailTemplateDAO emailTemplateDao, EmailTemplateSender sender) {
        this.dao = dao;
        this.histDao = histDao;
        this.emailTemplateDao = emailTemplateDao;
        this.sender = sender;
    }

    @Override
    public boolean isTemplateDisabledForNH(int templateId, String nicHandle) {
        EmailDisabler disabler = dao.get(new EmailDisablerKey(templateId, nicHandle));
        return disabler == null || disabler.isDisabled();
    }

    @Override
    public List<EmailDisabler> findAllEmailDisablersWithAddInfoForUser(String nicHandle) {
        return dao.findWithEmailTempAndEmailGroup(nicHandle);
    }

    @Override
    public List<EmailDisabler> findAllOfTemplatesBelongingToGroup(long groupId) {
        return dao.findAllDisablingsOfTemplatesBelongingToGroup(groupId);
    }

    @Override
    public List<EmailDisabler> findAllOfTemlpate(int templateId) {
        return dao.findAllDisablingsOfTemplate(templateId);
    }

    @Override
    public List<EmailDisablerSuppressInfo> updateEmailStatus(final List<EmailDisablerSuppressInfo> emaildisablerInfo,
            final OpInfo opInfo) {
        List<EmailTemplate> emailTemplates =
                emailTemplateDao.findAndLockInShareMode(new EmailTemplateSearchCriteria()).getResults();
        this.dao.findAndLockForUpdate(new EmailDisablerSearchCriteria(null, opInfo.getUserName()));
        List<EmailDisablerSuppressInfo> suppressibleEmailDisablers =
                removeNonSuppressibleIds(emaildisablerInfo, emailTemplates);
        List<HistoricalObject<EmailDisabler>> entriesToEnter = new ArrayList<>(
                Lists.transform(suppressibleEmailDisablers, getToHistObjFunction(opInfo.getActorName())));

        if (!suppressibleEmailDisablers.isEmpty()) {
            saveHistoryAndUpdateForAll(entriesToEnter);
            sendConfirmationEmail(opInfo.getActorName(), suppressibleEmailDisablers, emailTemplates);
        }
        return suppressibleEmailDisablers;
    }

    private void saveHistoryAndUpdateForAll(List<HistoricalObject<EmailDisabler>> entriesToEnter) {
        for (HistoricalObject<EmailDisabler> ho: entriesToEnter) {
            // updateUsingHistory is doing the "insert on duplicate key update" - all the keys are known beforehand,
            // there is no need for additional explicit insert to current table
            HistoricalEmailDisablerKey key = histDao.create(ho.getObject(), ho.getChangeDate(), ho.getChangedBy());
            dao.updateUsingHistory(key.getChangeId());
        }
    }


    /*>>>@NonNull*/
    private Function<EmailDisablerSuppressInfo, HistoricalObject<EmailDisabler>> getToHistObjFunction(
            final String changeNH) {
        return new Function<EmailDisablerSuppressInfo, HistoricalObject<EmailDisabler>>() {
            @Override
            public HistoricalObject<EmailDisabler> apply(EmailDisablerSuppressInfo emailDisablerSuppressInfo) {
                Date changeDate = new Date();

                EmailTemplate emailTemplate = new EmailTemplate();
                emailTemplate.setId(emailDisablerSuppressInfo.getEmailId());
                String nicHandle = emailDisablerSuppressInfo.getNicHandle();
                boolean disabled = emailDisablerSuppressInfo.isDisabled();
                EmailDisabler emailDisabler = new EmailDisabler(emailTemplate, nicHandle, disabled, changeDate);

                return new HistoricalObject<>(emailDisabler, changeDate, changeNH);
            }
        };
    }

    private List<EmailDisablerSuppressInfo> removeNonSuppressibleIds(List<EmailDisablerSuppressInfo> emaildisablerInfo,
            List<EmailTemplate> emailTemplates) {

        Set<Integer> nonSuppressibleEmailTemplates = new HashSet<Integer>();
        for (EmailTemplate emailTemplate : emailTemplates) {
            if (!emailTemplate.isSuppressible()) {
                nonSuppressibleEmailTemplates.add(emailTemplate.getId());
            } else {
                EmailGroup grp = emailTemplate.getGroup();
                if (grp != null && grp.getId() != EmailGroup.EMPTY_ID && !grp.getVisible()) {
                    nonSuppressibleEmailTemplates.add(emailTemplate.getId());
                }
            }
        }

        List<EmailDisablerSuppressInfo> suppressibleEmailDisablers = new LinkedList<EmailDisablerSuppressInfo>();
        for (EmailDisablerSuppressInfo emailDisablerInfo : emaildisablerInfo) {
            if (!nonSuppressibleEmailTemplates.contains(emailDisablerInfo.getEmailId())) {
                suppressibleEmailDisablers.add(emailDisablerInfo);
            }
        }
        return suppressibleEmailDisablers;
    }

    public void removeDisablingsOfTemplate(int templateId, final OpInfo op) {
        List<EmailDisabler> toDisable = FluentIterable
                .from(dao.findAndLockForUpdate(new EmailDisablerSearchCriteria(templateId, null)).getResults())
                .filter(new Predicate<EmailDisabler>() {
                    @Override
                    public boolean apply(EmailDisabler emailDisabler) {
                        return emailDisabler.isDisabled();
                    }
                })
                .toList();
        if (toDisable.isEmpty())
            return;

        final Date changeDate = new Date();
        List<HistoricalObject<EmailDisabler>> entriesToEnter = new ArrayList<>(Lists.transform(toDisable,
                new Function<EmailDisabler, HistoricalObject<EmailDisabler>>() {
                    @Override
                    public HistoricalObject<EmailDisabler> apply(EmailDisabler emailDisabler) {
                        emailDisabler.setDisabled(false);
                        return new HistoricalObject<>(emailDisabler, changeDate, op.getActorName());
                    }
                }));
        saveHistoryAndUpdateForAll(entriesToEnter);
    }

    private void sendConfirmationEmail(String user, List<EmailDisablerSuppressInfo> changed,
            List<EmailTemplate> emailTemplates) {
        Map<Integer, EmailTemplate> templates = new TreeMap<Integer, EmailTemplate>();
        for (EmailTemplate t : emailTemplates) {
            templates.put(t.getId(), t);
        }

        StringBuilder builder = new StringBuilder();
        for (EmailDisablerSuppressInfo suppressInfo : changed) {
            final int emailId = suppressInfo.getEmailId();
            final EmailTemplate emailTemplate = templates.get(emailId);
            if (emailTemplate != null) {
                builder.append("Email ID: ").append(emailTemplate.getId()).append(" with subject \"")
                        .append(emailTemplate.getSubject()).append("\" is ")
                        .append(suppressInfo.isDisabled() ? "disabled" : "enabled").append("\n");
            }
        }

        String accountUserNH = changed.get(0).getNicHandle();
        NicHandleDetails userDetails = dao.getUserDetailsByNicHandle(accountUserNH);
        String accountUserName = userDetails.getName();
        String accountUserEmail = userDetails.getEmail();
        String message = builder.toString();
        EmailDisabledEmailParameters params = new EmailDisabledEmailParameters(user, accountUserNH, accountUserEmail,
                accountUserName, message);
        final int templateId = EmailTemplateNamesEnum.EMAIL_DISABLER_CONFIRMATION.getId();
        try {
            sender.sendEmail(templateId, params);
        } catch (TemplateNotFoundException e) {
            LOG.error("Unknown email template " + templateId, e);
        } catch (TemplateInstantiatingException e) {
            LOG.error("Cannot instantiate email template", e);
        } catch (EmailSendingException e) {
            LOG.error("Error sending email", e);
        } catch (IllegalArgumentException e) {
            LOG.error("Error sending email", e);
        }
    }
}
