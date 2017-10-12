package pl.nask.crs.api.vo;

import java.util.List;

import javax.xml.bind.annotation.*;

import pl.nask.crs.documents.DocumentUpload;

@XmlType
@XmlAccessorType(XmlAccessType.NONE)
public class DocumentUploadVO implements DocumentUpload {

    @XmlElement(required = true)
    private String filename;
    @XmlElementWrapper
    private List<String> domains;
    @XmlElement
    private Long buyRequestId;

    public DocumentUploadVO() {}

    public DocumentUploadVO(String filename, List<String> domains, Long buyRequestId) {
        this.filename = filename;
        this.domains = domains;
        this.buyRequestId = buyRequestId;
    }

    @Override
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Override
    public List<String> getDomains() {
        return domains;
    }

    public void setDomains(List<String> domains) {
        this.domains = domains;
    }

    @Override
    public Long getBuyRequestId() {
        return buyRequestId;
    }

    public void setBuyRequestId(Long buyRequestId) {
        this.buyRequestId = buyRequestId;
    }
}
