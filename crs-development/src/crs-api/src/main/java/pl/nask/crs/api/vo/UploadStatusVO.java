package pl.nask.crs.api.vo;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

import pl.nask.crs.documents.UploadStatusEnum;

@XmlType
@XmlEnum(String.class)
public enum UploadStatusVO {
    OK,
    FILE_TOO_BIG,
    WRONG_FILE_TYPE,
    UPLOAD_INSUFFICIENT_PERMISSIONS_FOR_DOMAIN,
    UPLOAD_INSUFFICIENT_PERMISSIONS_FOR_BUY_REQUEST;

    public static UploadStatusVO fromUploadStatus(UploadStatusEnum status) {
        switch (status) {
            case FILE_TOO_BIG:
                return FILE_TOO_BIG;
            case WRONG_FILE_TYPE:
                return WRONG_FILE_TYPE;
            case UPLOAD_INSUFFICIENT_PERMISSIONS_FOR_DOMAIN:
                return UPLOAD_INSUFFICIENT_PERMISSIONS_FOR_DOMAIN;
            case UPLOAD_INSUFFICIENT_PERMISSIONS_FOR_BUY_REQUEST:
                return UPLOAD_INSUFFICIENT_PERMISSIONS_FOR_BUY_REQUEST;
            case OK:
                return OK;
            default:
                throw new IllegalArgumentException("Unknown UploadStatus " + status.name());
        }
    }
}
