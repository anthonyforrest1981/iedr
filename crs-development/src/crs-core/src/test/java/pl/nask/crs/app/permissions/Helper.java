package pl.nask.crs.app.permissions;

/*>>>import org.checkerframework.checker.nullness.qual.Nullable;*/

import java.util.Arrays;
import java.util.List;

import pl.nask.crs.app.commons.TicketRequest;
import pl.nask.crs.app.commons.TicketSource;
import pl.nask.crs.commons.Period;
import pl.nask.crs.domains.nameservers.Nameserver;
import pl.nask.crs.ticket.AdminStatus;

public final class Helper {

    private static class TicketRequestImpl implements TicketRequest {
        private String domainName;
        private String domainHolder;
        private Long domainOwnerTypeId;
        private String techNh;
        private List<Nameserver> nameservers;
        private String remark;
        private String charityCode;
        private boolean autorenewal;
        private Period period;
        private String authCode;
        private List</*>>>? extends @Nullable*/String> adminContactNicHandles;

        TicketRequestImpl(String domainName, String domainHolder, Long domainOwnerTypeId, String techNh,
                List<Nameserver> nameservers, String remark, String charityCode, boolean autorenewal, Integer period,
                String authCode, List</*>>>? extends @Nullable*/String> adminContactNicHandles) {
            this.domainName = domainName;
            this.domainHolder = domainHolder;
            this.domainOwnerTypeId = domainOwnerTypeId;
            this.techNh = techNh;
            this.nameservers = nameservers;
            this.remark = remark;
            this.charityCode = charityCode;
            this.autorenewal = autorenewal;
            this.period = Period.fromYears(period);
            this.authCode = authCode;
            this.adminContactNicHandles = adminContactNicHandles;
        }

        @Override
        public String getDomainName() {
            return domainName;
        }

        @Override
        public String getDomainHolder() {
            return domainHolder;
        }

        @Override
        public Long getDomainOwnerTypeId() {
            return domainOwnerTypeId;
        }

        @Override
        public String getTechContactNicHandle() {
            return techNh;
        }

        @Override
        public List<Nameserver> getNameservers() {
            return nameservers;
        }

        @Override
        public String getRequestersRemark() {
            return remark;
        }

        @Override
        public String getCharityCode() {
            return charityCode;
        }

        @Override
        public boolean getAutorenewMode() {
            return autorenewal;
        }

        @Override
        public Period getRegPeriod() {
            return period;
        }

        @Override
        public boolean isCharity() {
            return charityCode != null;
        }

        @Override
        public TicketSource getTicketSource() {
            return TicketSource.CONSOLE;
        }

        @Override
        public String getAuthCode() {
            return authCode;
        }

        @Override
        public List</*>>>? extends @Nullable*/String> getAdminContactNicHandles() {
            return adminContactNicHandles;
        }

        @Override
        public AdminStatus getDefaultAdminStatus() {
            return AdminStatus.NEW;
        }

        @Override
        public void setDomainHolder(String domainHolder) {
            this.domainHolder = domainHolder;
        }

        @Override
        public void setDomainOwnerTypeId(Long domainOwnerTypeId) {
            this.domainOwnerTypeId = domainOwnerTypeId;
        }

        @Override
        public void setAdminContactNicHandles(List</*>>>? extends @Nullable*/String> adminContactNicHandles) {
            this.adminContactNicHandles = adminContactNicHandles;
        }

        @Override
        public void setNameservers(List<Nameserver> nameservers) {
            this.nameservers = nameservers;
        }
    }

    public static TicketRequest prepareTicketRequest(String domainName, String billingNH, String nameserverName,
            long ownerTypeId, Integer period, String charityCode, String remark) {
        List<Nameserver> nameservers = Arrays.asList(new Nameserver(nameserverName, null, null),
                new Nameserver("dns2.com", null, null));
        return new TicketRequestImpl(domainName, "domain holder", ownerTypeId, billingNH, nameservers, remark,
                charityCode, false, period, null, Arrays.asList(billingNH, null));
    }

    public static TicketRequest prepareTicketRequest(String domainName, String billingNH, String nameserverName) {
        return prepareTicketRequest(domainName, billingNH, nameserverName, 1L, 1, null, "remark");
    }

    public static TicketRequest prepareTicketRequest(String domainName, String billingNH) {
        return prepareTicketRequest(domainName, billingNH, "dns1.com");
    }

}
