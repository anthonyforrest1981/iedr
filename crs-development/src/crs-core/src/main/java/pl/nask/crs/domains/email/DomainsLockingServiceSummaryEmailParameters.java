package pl.nask.crs.domains.email;

import java.util.List;
import java.util.Locale;

import pl.nask.crs.commons.email.service.MapBasedEmailParameters;
import pl.nask.crs.commons.email.service.ParameterNameEnum;
import pl.nask.crs.commons.utils.TableFormatter;
import pl.nask.crs.domains.Domain;

public class DomainsLockingServiceSummaryEmailParameters extends MapBasedEmailParameters {

    public DomainsLockingServiceSummaryEmailParameters(String username, List<Domain> domains) {
        TableFormatter formatter = new TableFormatter(Locale.getDefault());
        formatter.addColumn("Domain name", 40, TableFormatter.leftAlignedStringFormat(40), true);
        formatter.addColumn("Bill NH", 15, TableFormatter.rightAlignedStringFormat(15), true);
        formatter.addColumn("New locking renewal date", 25, TableFormatter.dateYMDFormat(25), true);
        for (Domain d : domains) {
            formatter.addDataLine(new Object[] {d.getName(), d.getBillingContactNic(), d.getLockingRenewalDate()});
        }
        set(ParameterNameEnum.DOMAIN_LIST, formatter.toString());
        set(ParameterNameEnum.NIC_NAME, username);
    }
}
