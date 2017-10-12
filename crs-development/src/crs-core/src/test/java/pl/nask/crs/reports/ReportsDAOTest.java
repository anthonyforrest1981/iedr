package pl.nask.crs.reports;

import java.util.*;

import javax.annotation.Resource;

import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

import pl.nask.crs.commons.AbstractTest;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.domains.CustomerType;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.dao.DomainDAO;
import pl.nask.crs.domains.search.DomainSearchCriteria;
import pl.nask.crs.reports.dao.ReportsDAO;
import pl.nask.crs.reports.search.DomainsPerClassSearchCriteria;
import pl.nask.crs.reports.search.TotalDomainsCriteria;
import pl.nask.crs.reports.search.TotalDomainsPerDateCriteria;

import static org.testng.AssertJUnit.assertEquals;

public class ReportsDAOTest extends AbstractTest {

    private static final Function<Domain, Integer> BY_YEAR = new Function<Domain, Integer>() {
        @Override
        public Integer apply(Domain domain) {
            return getYear(domain);
        }
    };

    private static final Function<Domain, Integer> BY_YEAR_MONTH = new Function<Domain, Integer>() {
        @Override
        public Integer apply(Domain domain) {
            return getYearMonth(domain);
        }
    };

    private static final Function<Domain, String> BY_BILLNH_CLASS_CATEGORY_YEAR = new Function<Domain, String>() {
        @Override
        public String apply(Domain domain) {
            int year = getYear(domain);
            String billingNH = domain.getBillingNH();
            long classId = domain.getHolderClass().getId();
            long categoryId = domain.getHolderCategory().getId();
            return String.format("%s@%d@%d@%d", billingNH, classId, categoryId, year);
        }
    };

    private static final Function<Domain, String> BY_BILLNH_CLASS_CATEGORY_YEAR_MONTH = new Function<Domain, String>() {
        @Override
        public String apply(Domain domain) {
            int yearmonth = getYearMonth(domain);
            String billingNH = domain.getBillingNH();
            long classId = domain.getHolderClass().getId();
            long categoryId = domain.getHolderCategory().getId();
            return String.format("%s@%d@%d@%d", billingNH, classId, categoryId, yearmonth);
        }
    };

    private static final Function<Domain, String> BY_CLASS_CATEGORY_ACCOUNTNAME = new Function<Domain, String>() {
        @Override
        public String apply(Domain domain) {
            String accountName = domain.getResellerAccount().getName();
            long classId = domain.getHolderClass().getId();
            long categoryId = domain.getHolderCategory().getId();
            return String.format("%s@%d@%d", accountName, classId, categoryId);
        }
    };

    private static Integer getYear(Domain domain) {
        Calendar regCal = Calendar.getInstance();
        regCal.setTime(domain.getRegistrationDate());
        return regCal.get(Calendar.YEAR);
    }

    private static Integer getYearMonth(Domain domain) {
        Calendar regCal = Calendar.getInstance();
        regCal.setTime(domain.getRegistrationDate());
        int year = regCal.get(Calendar.YEAR);
        int month = regCal.get(Calendar.MONTH);
        return year * 100 + month;
    }

    private static Predicate<Domain> BY_HOLDER_CLASS_ID(final int id) {
        return new Predicate<Domain>() {
            @Override
            public boolean apply(Domain domain) {
                return domain.getHolderClass().getId() == id;
            }
        };
    }

    private static Predicate<Domain> BY_HOLDER_CATEGORY_ID(final int id) {
        return new Predicate<Domain>() {
            @Override
            public boolean apply(Domain domain) {
                return domain.getHolderCategory().getId() == id;
            }
        };
    }

    private static Predicate<Domain> BY_CUSTOMER_TYPE(final CustomerType type) {
        return new Predicate<Domain>() {
            @Override
            public boolean apply(Domain domain) {
                return type == CustomerType.Direct ? domain.getAccountId() == 1 : domain.getAccountId() != 1;
            }
        };
    }

    private static Predicate<Domain> BY_ACCOUNT_ID(final int id) {
        return new Predicate<Domain>() {
            @Override
            public boolean apply(Domain domain) {
                return domain.getAccountId() == id;
            }
        };
    }

    private static Predicate<Domain> BY_BILL_NH(final String nh) {
        return new Predicate<Domain>() {
            @Override
            public boolean apply(Domain domain) {
                return domain.getResellerAccount().getBillingNH().equals(nh);
            }
        };
    }

    private static Predicate<Domain> BY_DATE_FROM(final Date from) {
        return new Predicate<Domain>() {
            @Override
            public boolean apply(Domain domain) {
                return domain.getRegistrationDate().after(from);
            }
        };
    }

    private static Predicate<Domain> BY_DATE_TO(final Date to) {
        return new Predicate<Domain>() {
            @Override
            public boolean apply(Domain domain) {
                return domain.getRegistrationDate().before(to);
            }
        };
    }

    @Resource
    ReportsDAO reportsDAO;

    @Resource
    DomainDAO domainDAO;

    private List<Domain> getAllDomains() {
        return domainDAO.findAll(new DomainSearchCriteria(), null);
    }

    @Test
    public void findTotalDomainsTest() {
        LimitedSearchResult<TotalDomains> result = reportsDAO.findTotalDomains(null, 0, 50, null);
        AssertJUnit.assertEquals(115, result.getTotalResults());
        AssertJUnit.assertEquals(51, result.getResults().size());

        TotalDomainsCriteria criteria = new TotalDomainsCriteria();
        criteria.setExcludedRegistrarsNic("WAIVED-IEDR");
        result = reportsDAO.findTotalDomains(criteria, 0, 50, null);
        AssertJUnit.assertEquals(114, result.getTotalResults());
        AssertJUnit.assertEquals(51, result.getResults().size());

        criteria = new TotalDomainsCriteria();
        criteria.setExcludedRegistrarsNic("IH4-IEDR");
        result = reportsDAO.findTotalDomains(criteria, 0, 50, null);
        AssertJUnit.assertEquals(111, result.getTotalResults());
        AssertJUnit.assertEquals(51, result.getResults().size());

        criteria = new TotalDomainsCriteria();
        criteria.setExcludedRegistrarsNic("WAIVED-IEDR", "IH4-IEDR");
        result = reportsDAO.findTotalDomains(criteria, 0, 50, null);
        AssertJUnit.assertEquals(110, result.getTotalResults());
        AssertJUnit.assertEquals(51, result.getResults().size());

        criteria = new TotalDomainsCriteria();
        criteria.setExcludedRegistrarsNic("IDE\u03082-IEDR");
        result = reportsDAO.findTotalDomains(criteria, 0, 200, null);
        AssertJUnit.assertEquals(114, result.getTotalResults());
        AssertJUnit.assertEquals(114, result.getResults().size());
        TotalDomains normalized = result.getResults().get(107);
        AssertJUnit.assertEquals(normalized.getBillingNH(), "XXŸ7-IEDR");
        AssertJUnit.assertEquals(normalized.getAccountName(), "Normalized Regiṡtraṟ");
        TotalDomains unnormalized = result.getResults().get(111);
        AssertJUnit.assertEquals(unnormalized.getBillingNH(), "IDL2-IËDP");
        AssertJUnit.assertEquals(unnormalized.getAccountName(), "Irish Domäins");
    }

    @Test
    public void findDomainsPerMonthPerRegistrar() {
        TotalDomainsPerDateCriteria criteria = new TotalDomainsPerDateCriteria();
        criteria.setReportTypeGranulation(ReportTypeGranulation.REGISTRAR);
        criteria.setReportTimeGranulation(ReportTimeGranulation.MONTH);
        LimitedSearchResult<TotalDomainsPerDate> result = reportsDAO.findTotalDomainsPerDate(criteria, 0, 100, null);
        Multimap<String, Domain> byBillNHYearMonth = Multimaps.index(getAllDomains(), BY_BILLNH_CLASS_CATEGORY_YEAR_MONTH);
        AssertJUnit.assertEquals(byBillNHYearMonth.keySet().size(), result.getTotalResults());
        AssertJUnit.assertEquals(byBillNHYearMonth.keySet().size(), result.getResults().size());
        TotalDomainsPerDate domainsPerDate = result.getResults().get(result.getResults().size() - 1);
        AssertJUnit.assertEquals(domainsPerDate.getDomainClass().getName(), "Unnormalized Clãss");
        AssertJUnit.assertEquals(domainsPerDate.getDomainCategory().getName(), "Unnormalized Cateǵōry");
        AssertJUnit.assertEquals(domainsPerDate.getBillingNH(), "YYY9-IEDR");
        AssertJUnit.assertEquals(domainsPerDate.getAccountName(), "Ìṃḃúȅl");
    }

    @Test
    public void findDomainsPerYearPerRegistrar() {
        TotalDomainsPerDateCriteria criteria = new TotalDomainsPerDateCriteria();
        criteria.setReportTypeGranulation(ReportTypeGranulation.REGISTRAR);
        criteria.setReportTimeGranulation(ReportTimeGranulation.YEAR);
        LimitedSearchResult<TotalDomainsPerDate> result = reportsDAO.findTotalDomainsPerDate(criteria, 0, 100, null);
        Multimap<String, Domain> byBillNHYear = Multimaps.index(getAllDomains(), BY_BILLNH_CLASS_CATEGORY_YEAR);
        AssertJUnit.assertEquals(byBillNHYear.keySet().size(), result.getTotalResults());
        AssertJUnit.assertEquals(byBillNHYear.keySet().size(), result.getResults().size());
    }

    @Test
    public void findDomainsPerMonth() {
        TotalDomainsPerDateCriteria criteria = new TotalDomainsPerDateCriteria();
        criteria.setReportTypeGranulation(ReportTypeGranulation.ALLREGISTRARS);
        criteria.setReportTimeGranulation(ReportTimeGranulation.MONTH);
        LimitedSearchResult<TotalDomainsPerDate> result = reportsDAO.findTotalDomainsPerDate(criteria, 0, 100, null);
        Multimap<Integer, Domain> byMonth = Multimaps.index(getAllDomains(), BY_YEAR_MONTH);
        AssertJUnit.assertEquals(byMonth.keySet().size(), result.getTotalResults());
        AssertJUnit.assertEquals(byMonth.keySet().size(), result.getResults().size());
    }

    @Test
    public void findDomainsPerYear() {
        TotalDomainsPerDateCriteria criteria = new TotalDomainsPerDateCriteria();
        criteria.setReportTypeGranulation(ReportTypeGranulation.ALLREGISTRARS);
        criteria.setReportTimeGranulation(ReportTimeGranulation.YEAR);
        LimitedSearchResult<TotalDomainsPerDate> result = reportsDAO.findTotalDomainsPerDate(criteria, 0, 100, null);
        Multimap<Integer, Domain> byYear = Multimaps.index(getAllDomains(), BY_YEAR);
        AssertJUnit.assertEquals(byYear.keySet().size(), result.getTotalResults());
        AssertJUnit.assertEquals(byYear.keySet().size(), result.getResults().size());
    }

    @Test
    public void findDomainsPerMonthPerDirectRegistrar() {
        TotalDomainsPerDateCriteria criteria = new TotalDomainsPerDateCriteria();
        criteria.setReportTypeGranulation(ReportTypeGranulation.REGISTRAR);
        criteria.setReportTimeGranulation(ReportTimeGranulation.MONTH);
        criteria.setCustomerType(CustomerType.Direct);
        LimitedSearchResult<TotalDomainsPerDate> result = reportsDAO.findTotalDomainsPerDate(criteria, 0, 100, null);
        Multimap<String, Domain> byRegMonth = Multimaps
                .index(Iterables.filter(getAllDomains(), BY_CUSTOMER_TYPE(CustomerType.Direct)),
                        BY_BILLNH_CLASS_CATEGORY_YEAR_MONTH);
        AssertJUnit.assertEquals(byRegMonth.keySet().size(), result.getTotalResults());
        AssertJUnit.assertEquals(byRegMonth.keySet().size(), result.getResults().size());
    }

    @Test
    public void findDomainsPerMonthPerRegistrarWithClassCriteria() {
        TotalDomainsPerDateCriteria criteria = new TotalDomainsPerDateCriteria();
        criteria.setReportTypeGranulation(ReportTypeGranulation.REGISTRAR);
        criteria.setReportTimeGranulation(ReportTimeGranulation.MONTH);
        criteria.setHolderClassId(1L);
        LimitedSearchResult<TotalDomainsPerDate> result = reportsDAO.findTotalDomainsPerDate(criteria, 0, 100, null);
        Multimap<String, Domain> byBillNhMonth = Multimaps.index(
                Iterables.filter(getAllDomains(), BY_HOLDER_CLASS_ID(1)), BY_BILLNH_CLASS_CATEGORY_YEAR_MONTH);
        AssertJUnit.assertEquals(byBillNhMonth.keySet().size(), result.getTotalResults());
        AssertJUnit.assertEquals(byBillNhMonth.keySet().size(), result.getResults().size());
    }

    @Test
    public void findDomainsPerMonthPerRegistrarWithUnnormalizedUtf8ClassCriteria() {
        TotalDomainsPerDateCriteria criteria = new TotalDomainsPerDateCriteria();
        criteria.setReportTypeGranulation(ReportTypeGranulation.REGISTRAR);
        criteria.setReportTimeGranulation(ReportTimeGranulation.MONTH);
        criteria.setHolderClassId(98L);
        LimitedSearchResult<TotalDomainsPerDate> result = reportsDAO.findTotalDomainsPerDate(criteria, 0, 100, null);
        AssertJUnit.assertEquals(1, result.getTotalResults());
        AssertJUnit.assertEquals(1, result.getResults().size());
        TotalDomainsPerDate domainsPerDate = result.getResults().get(0);
        AssertJUnit.assertEquals(domainsPerDate.getDomainClass().getName(), "Normalized Clãss");
        AssertJUnit.assertEquals(domainsPerDate.getDomainCategory().getName(), "Normalized Cateǵōry");
        AssertJUnit.assertEquals(domainsPerDate.getBillingNH(), "XXŸ7-IEDR");
        AssertJUnit.assertEquals(domainsPerDate.getAccountName(), "Ìṃḃúȅl");
    }

    @Test
    public void findDomainsPerClassTest() {
        final List<Domain> allDomains = getAllDomains();
        List<SortCriterion> orderBy = Arrays.asList(new SortCriterion("className", true),
                new SortCriterion("categoryName", true));

        Multimap<String, Domain> expected = Multimaps.index(allDomains, BY_CLASS_CATEGORY_ACCOUNTNAME);
        LimitedSearchResult<DomainsPerClass> result = reportsDAO.findTotalDomainsPerClass(null, 0, 100, orderBy);
        AssertJUnit.assertEquals(expected.keySet().size(), result.getResults().size());
        AssertJUnit.assertEquals(expected.keySet().size(), result.getTotalResults());

        DomainsPerClassSearchCriteria criteria = new DomainsPerClassSearchCriteria();
        criteria.setHolderClassId(3L);
        result = reportsDAO.findTotalDomainsPerClass(criteria, 0, 10, orderBy);
        expected = Multimaps.index(Iterables.filter(allDomains, BY_HOLDER_CLASS_ID(3)), BY_CLASS_CATEGORY_ACCOUNTNAME);
        AssertJUnit.assertEquals(expected.keySet().size(), result.getResults().size());
        AssertJUnit.assertEquals(expected.keySet().size(), result.getTotalResults());

        criteria = new DomainsPerClassSearchCriteria();
        criteria.setHolderClassId(98L);
        result = reportsDAO.findTotalDomainsPerClass(criteria, 0, 10, orderBy);
        expected = Multimaps.index(Iterables.filter(allDomains, BY_HOLDER_CLASS_ID(98)), BY_CLASS_CATEGORY_ACCOUNTNAME);
        AssertJUnit.assertEquals(expected.keySet().size(), result.getResults().size());
        AssertJUnit.assertEquals(expected.keySet().size(), result.getTotalResults());

        criteria = new DomainsPerClassSearchCriteria();
        criteria.setHolderCategoryId(5L);
        result = reportsDAO.findTotalDomainsPerClass(criteria, 0, 10, orderBy);
        expected = Multimaps.index(Iterables.filter(allDomains, BY_HOLDER_CATEGORY_ID(5)),
                BY_CLASS_CATEGORY_ACCOUNTNAME);
        AssertJUnit.assertEquals(expected.keySet().size(), result.getResults().size());
        AssertJUnit.assertEquals(expected.keySet().size(), result.getTotalResults());

        criteria = new DomainsPerClassSearchCriteria();
        criteria.setHolderCategoryId(98L);
        result = reportsDAO.findTotalDomainsPerClass(criteria, 0, 10, orderBy);
        expected = Multimaps.index(Iterables.filter(allDomains, BY_HOLDER_CATEGORY_ID(98)),
                BY_CLASS_CATEGORY_ACCOUNTNAME);
        AssertJUnit.assertEquals(expected.keySet().size(), result.getResults().size());
        AssertJUnit.assertEquals(expected.keySet().size(), result.getTotalResults());

        criteria = new DomainsPerClassSearchCriteria();
        Date from = new Date(107, 5, 1);
        criteria.setFrom(from);
        Date to = new Date(108, 7, 31);
        criteria.setTo(to);
        result = reportsDAO.findTotalDomainsPerClass(criteria, 0, 10, orderBy);
        expected = Multimaps.index(Iterables.filter(allDomains, Predicates.and(BY_DATE_FROM(from), BY_DATE_TO(to))),
                BY_CLASS_CATEGORY_ACCOUNTNAME);
        AssertJUnit.assertEquals(10, result.getResults().size());
        AssertJUnit.assertEquals(expected.keySet().size(), result.getTotalResults());

        criteria = new DomainsPerClassSearchCriteria();
        criteria.setAccountId(666L);
        result = reportsDAO.findTotalDomainsPerClass(criteria, 0, 10, orderBy);
        expected = Multimaps.index(Iterables.filter(allDomains, BY_ACCOUNT_ID(666)), BY_CLASS_CATEGORY_ACCOUNTNAME);
        AssertJUnit.assertEquals(expected.keySet().size(), result.getResults().size());
        AssertJUnit.assertEquals(expected.keySet().size(), result.getTotalResults());
        AssertJUnit.assertEquals(expected.get("API TESTS@2@5").size(), result.getResults().get(0).getDomainCount());

        criteria = new DomainsPerClassSearchCriteria();
        criteria.setBillingNH("APITEST-IEDR");
        result = reportsDAO.findTotalDomainsPerClass(criteria, 0, 10, orderBy);
        expected = Multimaps.index(Iterables.filter(allDomains, BY_BILL_NH("APITEST-IEDR")),
                BY_CLASS_CATEGORY_ACCOUNTNAME);
        AssertJUnit.assertEquals(expected.keySet().size(), result.getResults().size());
        AssertJUnit.assertEquals(expected.keySet().size(), result.getTotalResults());
        AssertJUnit.assertEquals(expected.get("API TESTS@2@5").size(), result.getResults().get(0).getDomainCount());

        criteria = new DomainsPerClassSearchCriteria();
        criteria.setBillingNH("XXY\u03087-IEDR");
        result = reportsDAO.findTotalDomainsPerClass(criteria, 0, 10, orderBy);
        AssertJUnit.assertEquals(1, result.getResults().size());
        AssertJUnit.assertEquals(1, result.getTotalResults());
        AssertJUnit.assertEquals(1, result.getResults().get(0).getDomainCount());

        criteria = new DomainsPerClassSearchCriteria();
        criteria.setBillingNH("APITEST-IEDR");
        from = new Date(114, 3, 15);
        criteria.setFrom(from);
        result = reportsDAO.findTotalDomainsPerClass(criteria, 0, 10, orderBy);
        expected = Multimaps
                .index(Iterables.filter(allDomains, Predicates.and(BY_BILL_NH("APITEST-IEDR"), BY_DATE_FROM(from))),
                        BY_CLASS_CATEGORY_ACCOUNTNAME);
        AssertJUnit.assertEquals(expected.keySet().size(), result.getResults().size());
        AssertJUnit.assertEquals(expected.keySet().size(), result.getTotalResults());
        AssertJUnit.assertEquals(expected.get("API TESTS@3@2").size(), result.getResults().get(2).getDomainCount());
    }

    @Test
    public void findDomainsPerClassWithDateRangeTest() {
        DomainsPerClassSearchCriteria criteria = new DomainsPerClassSearchCriteria();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2002, Calendar.JULY, 30);
        Date registrationDate = calendar.getTime();
        criteria.setFrom(registrationDate);
        criteria.setTo(registrationDate);
        LimitedSearchResult<DomainsPerClass> result = reportsDAO.findTotalDomainsPerClass(criteria, 0, 10, null);
        assertEquals(1, result.getResults().size());
        assertEquals(1, result.getTotalResults());
    }

    @Test
    public void findDomainsPerDateWithDateRangeTest() {
        TotalDomainsPerDateCriteria criteria = new TotalDomainsPerDateCriteria();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2002, Calendar.JULY, 30);
        Date registrationDate = calendar.getTime();
        criteria.setRegistrationFrom(registrationDate);
        criteria.setRegistrationTo(registrationDate);
        criteria.setReportTypeGranulation(ReportTypeGranulation.REGISTRAR);
        criteria.setReportTimeGranulation(ReportTimeGranulation.MONTH);
        LimitedSearchResult<TotalDomainsPerDate> result = reportsDAO.findTotalDomainsPerDate(criteria, 0, 10, null);
        assertEquals(1, result.getResults().size());
        assertEquals(1, result.getTotalResults());
    }

}
