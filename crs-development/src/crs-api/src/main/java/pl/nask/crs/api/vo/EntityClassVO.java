package pl.nask.crs.api.vo;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import pl.nask.crs.entities.EntityCategory;
import pl.nask.crs.entities.EntityClass;

/**
 * (C) Copyright 2011 NASK
 * Software Research & Development Department
 */
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class EntityClassVO {

    private long id;
    private String name;
    private List<EntityCategoryVO> categories;

    public EntityClassVO() {}

    public EntityClassVO(EntityClass entityClass) {
        this.id = entityClass.getId();
        this.name = entityClass.getName();
        this.categories = Lists.transform(entityClass.getCategories(), new Function<EntityCategory, EntityCategoryVO>() {
            @Override
            public EntityCategoryVO apply(EntityCategory category) {
                return new EntityCategoryVO(category);
            }
        });
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<EntityCategoryVO> getCategories() {
        return categories;
    }
}
