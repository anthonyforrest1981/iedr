package pl.nask.crs.app.commons;

import java.util.List;

public class RelatedDomains {
    private final List<String> relatedDomains;
    private final List<String> pendingDomains;

    public RelatedDomains(List<String> relatedDomains, List<String> pendingDomains) {
        this.relatedDomains = relatedDomains;
        this.pendingDomains = pendingDomains;
    }

    public List<String> getRelatedDomains() {
        return relatedDomains;
    }

    public List<String> getPendingDomains() {
        return pendingDomains;
    }
}
