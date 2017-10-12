package pl.nask.crs.web.ticket.wrappers;

import pl.nask.crs.entities.EntityClass;
import pl.nask.crs.entities.exceptions.HolderClassNotExistException;
import pl.nask.crs.entities.service.EntityService;
import pl.nask.crs.ticket.operation.DomainOperation.DomainOperationType;
import pl.nask.crs.ticket.operation.SimpleDomainFieldChange;

public class ClassFieldChangeWrapper extends SimpleDomainFieldChangeWrapper<EntityClass> {

    private EntityService entityService;

    public ClassFieldChangeWrapper(SimpleDomainFieldChange<EntityClass> orig, DomainOperationType opType,
            EntityService entityService) {
        super(orig, opType);
        this.entityService = entityService;
    }

    public Long getId() {
        return getNewValue().getId();
    }

    public void setId(Long id) {
        try {
            setNewValue(entityService.getClass(id));
        } catch (HolderClassNotExistException e) {
            setNewValue(null);
        }
    }

    public String getName() {
        return getNewValue().getName();
    }

}
