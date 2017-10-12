package pl.nask.crs.web.ticket.wrappers;

import pl.nask.crs.entities.EntitySubcategory;
import pl.nask.crs.entities.exceptions.HolderSubcategoryNotExistException;
import pl.nask.crs.entities.service.EntityService;
import pl.nask.crs.ticket.operation.DomainOperation.DomainOperationType;
import pl.nask.crs.ticket.operation.SimpleDomainFieldChange;

public class SubcategoryFieldChangeWrapper extends SimpleDomainFieldChangeWrapper<EntitySubcategory> {

    private EntityService entityService;

    public SubcategoryFieldChangeWrapper(SimpleDomainFieldChange<EntitySubcategory> orig, DomainOperationType opType,
            EntityService entityService) {
        super(orig, opType);
        this.entityService = entityService;
    }

    public Long getId() {
        return getNewValue() == null ? null : getNewValue().getId();
    }

    public void setId(Long id) throws HolderSubcategoryNotExistException {
        EntitySubcategory subcategory = id == null ? null : entityService.getSubcategory(id);
        setNewValue(subcategory);
    }

    public String getName() {
        return getNewValue() == null ? null : getNewValue().getName();
    }

}
