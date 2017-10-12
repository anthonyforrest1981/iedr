package pl.nask.crs.web.ticket.wrappers;

import java.util.List;

import pl.nask.crs.entities.EntityCategory;
import pl.nask.crs.entities.EntitySubcategory;
import pl.nask.crs.entities.exceptions.HolderCategoryNotExistException;
import pl.nask.crs.entities.service.EntityService;
import pl.nask.crs.ticket.operation.DomainOperation.DomainOperationType;
import pl.nask.crs.ticket.operation.SimpleDomainFieldChange;

public class CategoryFieldChangeWrapper extends SimpleDomainFieldChangeWrapper<EntityCategory> {

    private EntityService entityService;

    public CategoryFieldChangeWrapper(SimpleDomainFieldChange<EntityCategory> orig, DomainOperationType opType,
            EntityService entityService) {
        super(orig, opType);
        this.entityService = entityService;
    }

    public Long getId() {
        return getNewValue().getId();
    }

    public void setId(Long id) {
        try {
            setNewValue(entityService.getCategory(id));
        } catch (HolderCategoryNotExistException e) {
            setNewValue(null);
        }
    }

    public String getName() {
        return getNewValue().getName();
    }

    public List<EntitySubcategory> getSubcategories() {
        return getNewValue().getSubcategories();
    }

}
