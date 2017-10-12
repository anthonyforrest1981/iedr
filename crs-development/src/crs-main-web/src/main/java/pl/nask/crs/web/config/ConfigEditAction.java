package pl.nask.crs.web.config;

import pl.nask.crs.app.config.ConfigAppService;
import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.commons.config.ConfigEntry;
import pl.nask.crs.commons.config.MutableConfigEntry;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.web.security.AuthenticatedUserAwareAction;

public class ConfigEditAction extends AuthenticatedUserAwareAction {
    private final ConfigAppService service;

    private ApplicationConfig config;

    private ConfigEntry entry;

    private String newValue;

    private MutableConfigEntry newEntry;

    private String key;

    public ConfigEditAction(ConfigAppService service, ApplicationConfig config) {
        this.service = service;
        this.config = config;
    }

    boolean validateSave() throws AccessDeniedException {
        newEntry = new MutableConfigEntry();
        newEntry.setKey(getEntry().getKey());
        newEntry.setType(getEntry().getType());
        newEntry.setValue(newValue);
        if (!newEntry.validateValue()) {
            addFieldError("newValue", "Wrong value type, cannot parse to " + getEntry().getType());
            return false;
        } else if (!isNewValueLegit(newEntry)) {
            return false;
        } else {
            return true;
        }
    }

    private boolean isNewValueLegit(ConfigEntry entry) throws AccessDeniedException {
        switch (entry.getKey()) {
            case "reservation_expiration_period":
                int ticket_expiration_period = config.getTicketExpirationPeriod();
                if (ticket_expiration_period > (Integer) entry.getTypedValue()) {
                    addFieldError("newValue",
                            "Reservation expiration period cannot be shorter than ticket expiration period");
                    return false;
                }
                return true;
            case "ticket_expiration_period":
                int reservation_expiration_period = config.getReservationExpirationPeriod();
                if (reservation_expiration_period < (Integer) entry.getTypedValue()) {
                    addFieldError("newValue",
                            "Ticket expiration period cannot be longer than reservation expiration period");
                    return false;
                }
                return true;
            default:
                return true;
        }
    }

    public String save() throws AccessDeniedException {
        if (!validateSave()) {
            return ERROR;
        } else {
            service.updateEntry(getUser(), newEntry);
            return SUCCESS;
        }
    }

    public ConfigEntry getEntry() throws AccessDeniedException {
        if (entry == null) {
            entry = service.getEntry(getUser(), key);
        }
        return entry;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
