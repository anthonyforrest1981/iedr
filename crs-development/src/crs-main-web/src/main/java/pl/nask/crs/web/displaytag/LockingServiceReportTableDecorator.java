package pl.nask.crs.web.displaytag;

import java.util.Date;

import org.displaytag.decorator.TableDecorator;

import pl.nask.crs.domains.Domain;

public class LockingServiceReportTableDecorator extends TableDecorator {
    @Override
    public String addRowClass() {
        Date currentLockingRenewalDate = ((Domain) getCurrentRowObject()).getLockingRenewalDate();
        Date today = new Date();
        if (currentLockingRenewalDate == null) {
            return "missing_locking_renewal";
        } else if (currentLockingRenewalDate.before(today)) {
            return "overdue";
        }
        return "";
    }
}
