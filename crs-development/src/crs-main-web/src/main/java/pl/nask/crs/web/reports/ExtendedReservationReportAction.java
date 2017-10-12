package pl.nask.crs.web.reports;

import java.util.List;

import pl.nask.crs.app.AppSearchService;
import pl.nask.crs.app.payment.PaymentAppService;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.payment.ExtendedReservation;
import pl.nask.crs.payment.ExtendedReservationSearchCriteria;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.web.GenericSearchAction;

public class ExtendedReservationReportAction extends GenericSearchAction<ExtendedReservation, ExtendedReservationSearchCriteria> {

    private static final String YEAR_MONTH_DAY_PATTERN = "yyyy-MM-dd";
    private boolean showAll;

    public ExtendedReservationReportAction(final PaymentAppService paymentAppService) {
        super(new AppSearchService<ExtendedReservation, ExtendedReservationSearchCriteria>() {
            @Override
            public LimitedSearchResult<ExtendedReservation> search(AuthenticatedUser user,
                    ExtendedReservationSearchCriteria criteria, long offset, long limit, List<SortCriterion> orderBy)
                    throws AccessDeniedException {
                return paymentAppService.findExtendedReservations(user, criteria, offset, limit, orderBy);
            }
        });
    }

    @Override
    protected ExtendedReservationSearchCriteria createSearchCriteria() {
        return new ExtendedReservationSearchCriteria();
    }

    public String getDatePattern() {
        return YEAR_MONTH_DAY_PATTERN;
    }

    protected void updateSearchCriteria(ExtendedReservationSearchCriteria searchCriteria) {
        if (Validator.isEmpty(searchCriteria.getInvoiceNumber())) {
            searchCriteria.setInvoiceNumber(null);
        }
        if (Validator.isEmpty(searchCriteria.getInvoiceNumberFrom())) {
            searchCriteria.setInvoiceNumberFrom(null);
        }
        if (Validator.isEmpty(searchCriteria.getInvoiceNumberTo())) {
            searchCriteria.setInvoiceNumberTo(null);
        }
    }

    public boolean isShowAll() {
        return showAll;
    }

    public void setShowAll(boolean showAll) {
        this.showAll = showAll;
    }

    @Override
    protected int getPageSize() {
        return showAll ? Integer.MAX_VALUE : super.getPageSize();
    }

}
