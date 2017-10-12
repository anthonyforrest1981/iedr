package pl.nask.crs.api.vo;

/*>>>import org.checkerframework.checker.nullness.qual.Nullable;*/
/*>>>import org.checkerframework.dataflow.qual.Pure;*/

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.*;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import pl.nask.crs.app.commons.TicketRequest;
import pl.nask.crs.app.commons.TicketSource;
import pl.nask.crs.commons.Period;
import pl.nask.crs.domains.nameservers.Nameserver;
import pl.nask.crs.ticket.AdminStatus;

@XmlType
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class AbstractTicketRequestVO implements TicketRequest {

    public static final int DEFAULT_PERIOD = 1;

    private TicketSource ticketSource;

    @XmlElement(required = true, nillable = false)
    private String domainName;

    @XmlElement(required = true, nillable = false)
    private String domainHolder;

    @XmlElement(required = true, nillable = false)
    private Long domainOwnerTypeId;

    @XmlElement(required = true, nillable = false)
    private List</*>>>? extends @Nullable*/String> adminContactNicHandles;

    @XmlElement(required = true, nillable = false)
    private String techContactNicHandle;

    @XmlElement(required = true, nillable = false)
    private List<NameserverVO> nameservers = new ArrayList<NameserverVO>();

    @XmlElement(required = true, nillable = false)
    private String requestersRemark;

    private int period = DEFAULT_PERIOD;
    private PeriodType periodType = PeriodType.Y;

    @XmlElement(required = true, nillable = false)
    private boolean autorenewMode;

    @SuppressWarnings("nullness")
    public AbstractTicketRequestVO() {}

    /* (non-Javadoc)
      * @see pl.nask.crs.api.vo.RegistrationRequest#getDomainName()
      */
    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    /* (non-Javadoc)
      * @see pl.nask.crs.api.vo.RegistrationRequest#getDomainHolder()
      */
    public String getDomainHolder() {
        return domainHolder;
    }

    public void setDomainHolder(String domainHolder) {
        this.domainHolder = domainHolder;
    }

    public Long getDomainOwnerTypeId() {
        return domainOwnerTypeId;
    }

    public void setDomainOwnerTypeId(Long domainOwnerTypeId) {
        this.domainOwnerTypeId = domainOwnerTypeId;
    }

    /*>>>@Pure*/
    public List</*>>>? extends @Nullable*/String> getAdminContactNicHandles() {
        return adminContactNicHandles;
    }

    public void setAdminContactNicHandles(List</*>>>? extends @Nullable*/String> adminContactNicHandles) {
        this.adminContactNicHandles = adminContactNicHandles;
    }

    /* (non-Javadoc)
      * @see pl.nask.crs.api.vo.RegistrationRequest#getTechContactNicHandle()
      */
    public String getTechContactNicHandle() {
        return techContactNicHandle;
    }

    public void setTechContactNicHandle(String techContactNicHandle) {
        this.techContactNicHandle = techContactNicHandle;
    }

    public void setNameservers(List<Nameserver> nameservers) {
        this.nameservers.clear();
        this.nameservers.addAll(Lists.transform(nameservers, new Function<Nameserver, NameserverVO>() {
            @Override
            public NameserverVO apply(Nameserver nameserver) {
                return new NameserverVO(nameserver);
            }
        }));
    }

    public void updateNameservers(List<Nameserver> newNameservers) {
        int i = 0;
        for (Nameserver ns : newNameservers)
            nameservers.set(i++, new NameserverVO(ns));
    }

    public void resetToNameservers(List<Nameserver> newNameservers) {
        if (newNameservers == null)
            return;
        nameservers.clear();
        for (Nameserver ns : newNameservers) {
            nameservers.add(new NameserverVO(ns.getName(), ns.getIpv4Address(), ns.getIpv6Address()));
        }
    }

    @Override
    public List<Nameserver> getNameservers() {
        List<Nameserver> res = new ArrayList<Nameserver>();

        for (NameserverVO nsVO : nameservers) {
            res.add(new Nameserver(nsVO.getName(), nsVO.getIpv4Address(), nsVO.getIpv6Address()));
        }

        return res;
    }

    /* (non-Javadoc)
      * @see pl.nask.crs.api.vo.RegistrationRequest#getRequestersRemark()
      */
    public String getRequestersRemark() {
        return requestersRemark;
    }

    @Override
    public boolean getAutorenewMode() {
        return autorenewMode;
    }

    public void setAutorenewMode(boolean autorenewMode) {
        this.autorenewMode = autorenewMode;
    }

    public void setRequestersRemark(String requestersRemark) {
        this.requestersRemark = requestersRemark;
    }

    @Override
    public Period getRegPeriod() {
        switch (periodType) {
            case M:
                return Period.fromMonths(period);
            case Y:
                return Period.fromYears(period);
            default:
                throw new IllegalStateException("Unhandled period type: " + periodType);
        }
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public PeriodType getPeriodType() {
        return periodType;
    }

    public void setPeriodType(PeriodType periodType) {
        this.periodType = periodType;
    }

    public AdminStatus getDefaultAdminStatus() {
        return AdminStatus.NEW;
    }

    public void setTicketSource(TicketSource ticketSource) {
        this.ticketSource = ticketSource;
    }

    @Override
    public TicketSource getTicketSource() {
        return this.ticketSource;
    }

}
