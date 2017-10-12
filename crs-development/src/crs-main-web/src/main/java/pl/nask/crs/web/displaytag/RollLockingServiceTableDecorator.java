package pl.nask.crs.web.displaytag;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.displaytag.decorator.TableDecorator;

import pl.nask.crs.domains.Domain;

public class RollLockingServiceTableDecorator extends TableDecorator {
    @Override
    public String addRowClass() {
        Date currentLockingRenewalDate = ((Domain) getCurrentRowObject()).getLockingRenewalDate();
        Date today = new Date();
        Date oneYearInTheFuture = DateUtils.addYears(today, 1);
        if (currentLockingRenewalDate == null) {
            return "missing_locking_renewal";
        } else if (currentLockingRenewalDate.after(oneYearInTheFuture)) {
            return "too_far_in_future";
        } else if (currentLockingRenewalDate.before(today)) {
            return "overdue";
        }
        return "";
    }
}
