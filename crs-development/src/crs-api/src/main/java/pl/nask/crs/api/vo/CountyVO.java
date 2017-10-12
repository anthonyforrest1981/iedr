package pl.nask.crs.api.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import pl.nask.crs.country.County;

@XmlRootElement
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class CountyVO {

    private int id;
    private String name;

    public CountyVO() {}

    public CountyVO(County county) {
        this.setId(county.getId());
        this.setName(county.getName());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
