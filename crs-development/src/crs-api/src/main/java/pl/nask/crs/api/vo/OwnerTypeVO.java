package pl.nask.crs.api.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import pl.nask.crs.entities.OwnerType;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class OwnerTypeVO {

    private long id;
    private String name;
    private boolean charity;

    public OwnerTypeVO() {}

    public OwnerTypeVO(OwnerType ownerType) {
        this.id = ownerType.getId();
        this.name = ownerType.getName();
        this.charity = ownerType.isCharity();
    }

}
