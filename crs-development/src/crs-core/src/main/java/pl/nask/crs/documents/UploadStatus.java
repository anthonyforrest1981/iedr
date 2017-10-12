package pl.nask.crs.documents;

import java.util.Set;
import java.util.TreeSet;

public class UploadStatus {
    private final UploadStatusEnum status;
    private final Set<String> knownDomains;
    private final Long buyRequestId;

    public UploadStatus(UploadStatusEnum status, Set<String> affectedDomains, Long buyRequestId) {
        this.status = status;
        this.knownDomains = new TreeSet<>();
        if (affectedDomains != null)
            knownDomains.addAll(affectedDomains);
        this.buyRequestId = buyRequestId;
    }

    public UploadStatusEnum getStatus() {
        return status;
    }

    public Set<String> getKnownDomains() {
        return knownDomains;
    }

    public Long getBuyRequestId() {
        return buyRequestId;
    }
}
