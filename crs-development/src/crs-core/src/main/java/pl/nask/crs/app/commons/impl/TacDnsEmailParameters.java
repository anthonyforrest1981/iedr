package pl.nask.crs.app.commons.impl;

import java.util.List;
import java.util.Locale;

import pl.nask.crs.commons.email.service.EmailParametersUtils;
import pl.nask.crs.commons.email.service.MapBasedEmailParameters;
import pl.nask.crs.commons.email.service.ParameterNameEnum;
import pl.nask.crs.commons.utils.TableFormatter;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.nameservers.Nameserver;
import pl.nask.crs.nichandle.NicHandle;

public class TacDnsEmailParameters extends MapBasedEmailParameters {

    private String loggedInNH;
    private String billingNH;
    private String username;

    public TacDnsEmailParameters(String username, NicHandle creatorNh, Domain domain, List<Nameserver> oldList) {
        Validator.assertNotNull(creatorNh, "creatorNh");
        Validator.assertNotNull(domain, "domain");
        Validator.assertNotNull(oldList, "oldNameservers");
        Contact techC = domain.getTechContact();
        Contact billC = domain.getBillingContact();
        Validator.assertNotNull(techC, "techC");
        Validator.assertNotNull(domain.getFirstAdminContact(), "adminC");
        Validator.assertNotNull(billC, "billC");
        set(ParameterNameEnum.CREATOR_C_EMAIL, creatorNh.getEmail());
        set(ParameterNameEnum.CREATOR_C_NAME, creatorNh.getName());
        set(ParameterNameEnum.TECH_C_EMAIL, techC.getEmail());
        set(ParameterNameEnum.ADMIN_C_EMAIL, EmailParametersUtils.getAdminContactEmails(domain.getAdminContacts()));
        set(ParameterNameEnum.DOMAIN, domain.getName());
        set(ParameterNameEnum.BILL_C_EMAIL, billC.getEmail());
        set(ParameterNameEnum.BILL_C_NAME, billC.getName());
        set(ParameterNameEnum.DNS_LIST_OLD, makeFormatter(oldList));
        set(ParameterNameEnum.DNS_LIST_NEW, makeFormatter(domain.getNameservers()));
        set(ParameterNameEnum.REGISTRAR_NAME, billC.getName());
        this.loggedInNH = creatorNh.getNicHandleId();
        this.billingNH = domain.getBillingContact().getNicHandle();
        this.username = username;
    }

    public String getLoggedInNicHandle() {
        return this.username;
    }

    public String getAccountRelatedNicHandle(boolean gaining) {
        return this.billingNH;
    }

    public String getDomainName() {
        return this.getParameterValue(ParameterNameEnum.DOMAIN.toString(), false);
    }

    //getParameterValue(String name, boolean html)
    private TableFormatter makeFormatter(List<Nameserver> list) {
        TableFormatter formatter = getFormatter();
        for (Nameserver ns : list) {
            formatter.addDataLine(new Object[] {ns.getName(), ns.getIpv4Address(), ns.getIpv6Address()});
        }
        return formatter;
    }

    private TableFormatter getFormatter() {
        TableFormatter formatter = new TableFormatter(Locale.getDefault());
        formatter.addColumn("host", 60, TableFormatter.leftAlignedStringFormat(60), true);
        formatter.addColumn("ipv4", 60, TableFormatter.leftAlignedStringFormat(60), true);
        formatter.addColumn("ipv6", 60, TableFormatter.leftAlignedStringFormat(60), true);

        return formatter;
    }
}
