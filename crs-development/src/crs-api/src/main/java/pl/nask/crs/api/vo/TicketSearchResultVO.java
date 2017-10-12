package pl.nask.crs.api.vo;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TicketSearchResultVO {

    private List<TicketVO> results;
    private long totalResults;

    /**
     * use {@link #TicketSearchResultVO(List, long)} instead - this is left public only to let Enunciate generate WS documentation properly
     */
    @SuppressWarnings("nullness")
    public TicketSearchResultVO() {
        // for jax-ws
    }

    public TicketSearchResultVO(List<TicketVO> list, long totalResults) {
        results = list;
        this.totalResults = totalResults;
    }

    public List<TicketVO> getResults() {
        return results;
    }

    public long getTotalResults() {
        return totalResults;
    }

}
