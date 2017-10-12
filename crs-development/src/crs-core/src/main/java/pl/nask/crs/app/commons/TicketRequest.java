package pl.nask.crs.app.commons;

/*>>>import org.checkerframework.checker.nullness.qual.Nullable;*/
/*>>>import org.checkerframework.dataflow.qual.Pure;*/

import java.util.List;

import pl.nask.crs.commons.Period;
import pl.nask.crs.domains.nameservers.Nameserver;
import pl.nask.crs.ticket.AdminStatus;

public interface TicketRequest {
    enum PeriodType {
        Y, M
    }

    String getDomainName();

    String getDomainHolder();

    Long getDomainOwnerTypeId();

    /*>>>@Pure*/
    List</*>>>? extends @Nullable*/String> getAdminContactNicHandles();

    String getTechContactNicHandle();

    List<Nameserver> getNameservers();

    String getRequestersRemark();

    String getCharityCode();

    boolean getAutorenewMode();

    Period getRegPeriod();

    String getAuthCode();

    boolean isCharity();

    TicketSource getTicketSource();

    AdminStatus getDefaultAdminStatus();

    void setDomainHolder(String domainHolder);

    void setDomainOwnerTypeId(Long domainOwnerTypeId);

    void setAdminContactNicHandles(List</*>>>? extends @Nullable*/ String> adminContactNicHandles);

    void setNameservers(List<Nameserver> nameservers);

}
