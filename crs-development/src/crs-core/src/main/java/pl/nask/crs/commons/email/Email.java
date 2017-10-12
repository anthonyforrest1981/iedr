package pl.nask.crs.commons.email;

import java.io.File;
import java.util.*;

import pl.nask.crs.commons.utils.Validator;

/**
 * todo: email model
 *
 * @author Patrycja Wegrzynowicz
 * @author Kasia Fulara
 * @author Artur Gniadzik
 */
public class Email {

    private String text;
    private String subject;
    private List<String> toList = new ArrayList<String>();
    private List<String> ccList = new ArrayList<String>();
    private List<String> bccList = new ArrayList<String>();
    private List<File> attachments;
    private String from;
    private boolean active;
    private boolean html;
    private boolean suppressible;
    private String sendReason;
    private String nonSuppressibleReason;
    private int templateId;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        Validator.assertNotNull(text, "text");
        this.text = text;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        Validator.assertNotNull(subject, "subject");
        this.subject = subject;
    }

    public List<String> getToList() {
        return copyRo(toList);
    }

    public void setToList(Collection<String> toList) {
        Validator.assertNotNull(toList, "toList");
        this.toList = copyRo(toList);
    }

    public List<String> getCcList() {
        return copyRo(ccList);
    }

    public void setCcList(Collection<String> ccList) {
        Validator.assertNotNull(ccList, "ccList");
        this.ccList = copyRo(ccList);
    }

    public List<String> getBccList() {
        return copyRo(bccList);
    }

    public void setBccList(Collection<String> bccList) {
        Validator.assertNotNull(bccList, "bccList");
        this.bccList = copyRo(bccList);
    }

    public List<File> getAttachments() {
        return copyRo(attachments);
    }

    public void setAttachments(List<File> attachments) {
        this.attachments = copyRo(attachments);
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return String.format("Email[to: %s\ncc:%s, \nbcc: %s, \nSubject: %s, \nBody: %s]", new Object[] {toList,
                ccList, bccList, subject, text});
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

    private <T> List<T> copyRo(Collection<T> list) {
        if (Validator.isEmpty(list)) {
            return Collections.emptyList();
        } else {
            return Collections.unmodifiableList(new LinkedList<T>(list));
        }
    }

    public boolean isToBeSent() {
        return active && !from.isEmpty() && !((toList.isEmpty()) && (ccList.isEmpty()) && (bccList.isEmpty()));
    }

    public int getTemplateId() {
        return templateId;
    }

    public void setTemplateId(int templateId) {
        this.templateId = templateId;
    }
}
