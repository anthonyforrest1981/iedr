package pl.nask.crs.api.vo;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import pl.nask.crs.country.Country;
import pl.nask.crs.country.County;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
@XmlRootElement
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class CountryVO {

    private int id;
    private String name;
    private List<CountyVO> counties;

    public CountryVO() {}

    public CountryVO(Country country) {
        this.setId(country.getId());
        this.setName(country.getName());
        this.counties = Lists.transform(country.getCounties(), new Function<County, CountyVO>() {
            @Override
            public CountyVO apply(County county) {
                return new CountyVO(county);
            }
        });
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
