package pl.nask.crs.api.vo;

/*>>>import org.checkerframework.checker.nullness.qual.Nullable;*/

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import pl.nask.crs.app.tickets.TicketModel;
import pl.nask.crs.payment.PaymentMethod;
import pl.nask.crs.ticket.AdminStatus;
import pl.nask.crs.ticket.FinancialStatus;
import pl.nask.crs.ticket.TechStatus;
import pl.nask.crs.ticket.Ticket;

@XmlType
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TicketVO {

    private long id;

    private String type;

    private DomainOperationVO operation;

    private AdminStatus adminStatus;

    private Date adminStatusChangeDate;

    private TechStatus techStatus;

    private Date techStatusChangeDate;

    private FinancialStatus financialStatus;

    private Date financialStatusChangeDate;

    private /*>>>@Nullable*/ String requestersRemark;

    private /*>>>@Nullable*/ String hostmastersRemark;

    private String creator;

    private Date creationDate;

    private Date changeDate;

    private /*>>>@Nullable*/ String checkedOutTo;

    private boolean clikPaid;

    private boolean hasDocuments;

    private String domainName;

    private /*>>>@Nullable*/ String ticketHolder;

    private /*>>>@Nullable*/ String previousHolder;

    private /*>>>@Nullable*/ Date domainRenevalDate;

    private /*>>>@Nullable*/ String charityCode;

    private int period;

    private /*>>>@Nullable*/ PaymentMethod paymentType;

    @SuppressWarnings("nullness")
    public TicketVO() {}

    @SuppressWarnings("nullness")
    public TicketVO(TicketModel view) {
        this(view.getTicket());

        ticketHolder = view.getAdditionalInfo().getTicketHolder();
        previousHolder = view.getAdditionalInfo().getPreviousHolder();
        paymentType = view.getAdditionalInfo().getPaymentMethod();
    }

    public TicketVO(Ticket t) {
        id = t.getId();
        assert t.getOperation().getDomainNameField().getNewValue() != null : "@AssumeAssertion(nullness)";
        domainName = t.getOperation().getDomainNameField().getNewValue();
        type = t.getOperation().getType().getFullName();
        operation = new DomainOperationVO(t.getOperation());
        adminStatus = t.getAdminStatus();
        adminStatusChangeDate = t.getAdminStatusChangeDate();
        techStatus = t.getTechStatus();
        techStatusChangeDate = t.getTechStatusChangeDate();
        financialStatus = t.getFinancialStatus();
        financialStatusChangeDate = t.getFinancialStatusChangeDate();
        requestersRemark = t.getRequestersRemark();
        hostmastersRemark = t.getHostmastersRemark();
        creator = t.getCreator().getNicHandle();
        creationDate = t.getCreationDate();
        changeDate = t.getChangeDate();
        checkedOutTo = t.getCheckedOutTo() == null ? null : t.getCheckedOutTo().getNicHandle();
        clikPaid = t.isClikPaid();
        hasDocuments = t.isHasDocuments();
        domainRenevalDate = t.getOperation().getRenewalDate();
        charityCode = t.getCharityCode();
        period = t.getDomainPeriod() != null ? t.getDomainPeriod().getYears() : 0;
    }

    public long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public DomainOperationVO getOperation() {
        return operation;
    }

    public AdminStatus getAdminStatus() {
        return adminStatus;
    }

    public Date getAdminStatusChangeDate() {
        return adminStatusChangeDate;
    }

    public TechStatus getTechStatus() {
        return techStatus;
    }

    public Date getTechStatusChangeDate() {
        return techStatusChangeDate;
    }

    public /*>>>@Nullable*/ String getRequestersRemark() {
        return requestersRemark;
    }

    public /*>>>@Nullable*/ String getHostmastersRemark() {
        return hostmastersRemark;
    }

    public String getCreator() {
        return creator;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getChangeDate() {
        return changeDate;
    }

    public /*>>>@Nullable*/ String getCheckedOutTo() {
        return checkedOutTo;
    }

    public boolean isClikPaid() {
        return clikPaid;
    }

    public boolean isHasDocuments() {
        return hasDocuments;
    }

    public String getDomainName() {
        return domainName;
    }

    public /*>>>@Nullable*/ String getTicketHolder() {
        return ticketHolder;
    }

    public /*>>>@Nullable*/ String getPreviousHolder() {
        return previousHolder;
    }

    public /*>>>@Nullable*/ Date getDomainRenevalDate() {
        return domainRenevalDate;
    }

    public /*>>>@Nullable*/ String getCharityCode() {
        return charityCode;
    }

    public int getPeriod() {
        return period;
    }

    public /*>>>@Nullable*/ PaymentMethod getPaymentType() {
        return paymentType;
    }

}
