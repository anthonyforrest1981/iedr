package pl.nask.crs.app.tickets;

/*>>>import org.checkerframework.checker.nullness.qual.Nullable;*/

import java.util.Collection;
import java.util.Collections;
import java.util.TreeSet;

import pl.nask.crs.payment.PaymentMethod;

public class TicketInfo {

    private /*>>>@Nullable*/ String domainName;

    private /*>>>@Nullable*/ String ticketHolder;

    private /*>>>@Nullable*/ String previousHolder;

    private Collection<String> relatedDomainNames;

    private Collection<String> pendingDomainNames;

    private boolean documents;

    // payment method for the registration ticket
    private /*>>>@Nullable*/ PaymentMethod paymentMethod;

    public TicketInfo() {
        this(null, null, null, null, null);
    }

    public TicketInfo(/*>>>@Nullable*/ String domainName, /*>>>@Nullable*/ String ticketHolder, /*>>>@Nullable*/
            String previousHolder, /*>>>@Nullable*/ Collection<String> relatedDomainNames, /*>>>@Nullable*/
            Collection<String> pendingDomainNames) {
        this(domainName, ticketHolder, previousHolder, relatedDomainNames, pendingDomainNames, false, null);
    }

    public TicketInfo(/*>>>@Nullable*/ String domainName, /*>>>@Nullable*/ String ticketHolder, /*>>>@Nullable*/
            String previousHolder, /*>>>@Nullable*/ Collection<String> relatedDomainNames, /*>>>@Nullable*/
            Collection<String> pendingDomainNames, boolean faxes, /*>>>@Nullable*/ PaymentMethod paymentMethod) {
        this.domainName = domainName;
        this.ticketHolder = ticketHolder;
        this.previousHolder = previousHolder;
        this.pendingDomainNames = pendingDomainNames == null ? Collections.<String>emptySet() : new TreeSet<>(
                pendingDomainNames);
        this.relatedDomainNames = relatedDomainNames == null ? Collections.<String>emptySet() : new TreeSet<>(
                relatedDomainNames);
        this.documents = faxes;
        this.paymentMethod = paymentMethod;
    }

    public /*>>>@Nullable*/ String getDomainName() {
        return domainName;
    }

    public /*>>>@Nullable*/ String getTicketHolder() {
        return ticketHolder;
    }

    public /*>>>@Nullable*/ String getPreviousHolder() {
        return previousHolder;
    }

    public Collection<String> getRelatedDomainNames() {
        return relatedDomainNames;
    }

    public boolean isDocuments() {
        return documents;
    }

    public Collection<String> getPendingDomainNames() {
        return pendingDomainNames;
    }

    public /*>>>@Nullable*/ PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }
}
