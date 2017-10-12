package pl.nask.crs.ticket.dao.ibatis;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import pl.nask.crs.commons.dao.Converter;
import pl.nask.crs.commons.dao.ConvertingGenericDAO;
import pl.nask.crs.commons.dao.GenericDAO;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.dao.HistoricalTicketDAO;
import pl.nask.crs.ticket.dao.ibatis.objects.InternalHistoricalTicket;
import pl.nask.crs.ticket.search.HistoricTicketSearchCriteria;

public class HistoricalTicketIBatisDAO extends ConvertingGenericDAO<InternalHistoricalTicket, HistoricalObject<Ticket>, Long> implements
        HistoricalTicketDAO {
    //fix for bug #1417
    private final static List<SortCriterion> DESCENDING_ORDER = Arrays.asList(new SortCriterion("histChangeDate", false), new SortCriterion("changeId", false));

    public HistoricalTicketIBatisDAO(GenericDAO<InternalHistoricalTicket, Long> internalDao,
            Converter<InternalHistoricalTicket, HistoricalObject<Ticket>> internalConverter) {
        super(internalDao, internalConverter);
    }

    public List<HistoricalObject<Ticket>> findAll(long id) {
        HistoricTicketSearchCriteria criteria = new HistoricTicketSearchCriteria();
        criteria.setTicketId(id);
        return find(criteria, DESCENDING_ORDER).getResults();
    }

    @Override
    public Long create(Ticket ticket, Date changeDate, String changedBy) {
        HistoricalObject<Ticket> histTicket = new HistoricalObject<>(ticket, changeDate, changedBy);
        InternalHistoricalTicket iht = getInternalConverter().from(histTicket);
        assert iht != null : "@AssumeAssertion(nullness)";
        getInternalDao().create(iht);
        return iht.getChangeId();
    }
}
