package pl.nask.crs.domains.dao;

import java.util.Date;

import pl.nask.crs.commons.dao.HistoricalDAO;
import pl.nask.crs.domains.Domain;

public interface HistoricalDomainDAO extends HistoricalDAO<Domain, Long> {

    Long create(Domain domain, int targetState, Date changeDate, String changedBy);

}
