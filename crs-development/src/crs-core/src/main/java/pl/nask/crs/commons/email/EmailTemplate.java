package pl.nask.crs.commons.email;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pl.nask.crs.commons.utils.Validator;

public class EmailTemplate {

    private int id;

    private String text;
    private String subject;
    private List<String> toList = new ArrayList<>();
    private List<String> ccList = new ArrayList<>();
    private List<String> bccList = new ArrayList<>();
    private List<String> internalToList = new ArrayList<>();
    private List<String> internalCcList = new ArrayList<>();
    private List<String> internalBccList = new ArrayList<>();
    private String mailSmtpFrom;
    private boolean active;
    private boolean html;
    private boolean suppressible;
    private String sendReason;
    private String nonSuppressibleReason;
    private EmailGroup group;
    private boolean suppressedByGaining;
    private Date changeDate;

    public EmailTemplate() {}

    public EmailTemplate(EmailTemplate src) {
        this(src.id, src.text, src.subject, src.toList, src.ccList, src.bccList, src.internalToList,
                src.internalCcList, src.internalBccList, src.mailSmtpFrom, src.active, src.html, src.suppressible,
                src.sendReason, src.nonSuppressibleReason, src.group, src.suppressedByGaining);
    }

    public EmailTemplate(int id, String text, String subject, List<String> toList, List<String> ccList,
            List<String> bccList, List<String> internalToList, List<String> internalCcList,
            List<String> internalBccList, String mailSmtpFrom, boolean active, boolean html, boolean suppressible,
            String sendReason, String nonSuppressibleReason, EmailGroup group, boolean suppressedByGaining) {
        this.id = id;
        this.text = text;
        this.subject = subject;
        this.toList = new ArrayList<>(toList);
        this.ccList = new ArrayList<>(ccList);
        this.bccList = new ArrayList<>(bccList);
        this.internalToList = new ArrayList<>(internalToList);
        this.internalCcList = new ArrayList<>(internalCcList);
        this.internalBccList = new ArrayList<>(internalBccList);
        this.mailSmtpFrom = mailSmtpFrom;
        this.active = active;
        this.html = html;
        this.suppressible = suppressible;
        this.sendReason = sendReason;
        this.nonSuppressibleReason = nonSuppressibleReason;
        this.group = group;
        this.suppressedByGaining = suppressedByGaining;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<String> getToList() {
        return toList;
    }

    public void setToList(List<String> toList) {
        Validator.assertNotNull(toList, "toList");
        this.toList = toList;
    }

    public List<String> getCcList() {
        return ccList;
    }

    public void setCcList(List<String> ccList) {
        Validator.assertNotNull(ccList, "ccList");
        this.ccList = ccList;
    }

    public List<String> getBccList() {
        return bccList;
    }

    public void setBccList(List<String> bccList) {
        Validator.assertNotNull(bccList, "bccList");
        this.bccList = bccList;
    }

    public List<String> getInternalToList() {
        return internalToList;
    }

    public void setInternalToList(List<String> internalToList) {
        Validator.assertNotNull(internalToList, "internalToList");
        this.internalToList = internalToList;
    }

    public List<String> getInternalCcList() {
        return internalCcList;
    }

    public void setInternalCcList(List<String> internalCcList) {
        Validator.assertNotNull(internalCcList, "internalCcList");
        this.internalCcList = internalCcList;
    }

    public List<String> getInternalBccList() {
        return internalBccList;
    }

    public void setInternalBccList(List<String> internalBccList) {
        Validator.assertNotNull(internalBccList, "internalBccList");
        this.internalBccList = internalBccList;
    }

    public String getMailSmtpFrom() {
        return mailSmtpFrom;
    }

    public void setMailSmtpFrom(String mailSmtpFrom) {
        this.mailSmtpFrom = mailSmtpFrom;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isHtml() {
        return html;
    }

    public void setHtml(boolean html) {
        this.html = html;
    }

    public boolean isSuppressible() {
        return suppressible;
    }

    public void setSuppressible(boolean suppressible) {
        this.suppressible = suppressible;
    }

    public String getSendReason() {
        return sendReason;
    }

    public void setSendReason(String sendReason) {
        this.sendReason = sendReason;
    }

    public String getNonSuppressibleReason() {
        return nonSuppressibleReason;
    }

    public void setNonSuppressibleReason(String nonSuppressibleReason) {
        this.nonSuppressibleReason = nonSuppressibleReason;
    }

    public EmailGroup getGroup() {
        return group;
    }

    public void setGroup(EmailGroup group) {
        this.group = group;
    }

    public boolean isSuppressedByGaining() {
        return suppressedByGaining;
    }

    public void setSuppressedByGaining(boolean suppressedByGaining) {
        this.suppressedByGaining = suppressedByGaining;
    }

    public Date getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

}
