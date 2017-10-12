package pl.nask.crs.api.vo;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

import pl.nask.crs.documents.DocumentPurpose;

@XmlType
@XmlEnum(String.class)
public enum UploadPurposeVO {
    REGISTRATION, MODIFICATION, BUY_REQUEST;

    public DocumentPurpose asDocumentPurpose() {
        switch (this) {
            case REGISTRATION:
                return DocumentPurpose.REGISTRATION;
            case MODIFICATION:
                return DocumentPurpose.GENERAL_MOD;
            case BUY_REQUEST:
                return DocumentPurpose.BUY_REQUEST;
            default:
                throw new IllegalArgumentException("Unknown upload purpose " + name());
        }
    }

}
