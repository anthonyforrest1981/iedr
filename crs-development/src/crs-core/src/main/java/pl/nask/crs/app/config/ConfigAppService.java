package pl.nask.crs.app.config;

import java.util.List;

import pl.nask.crs.commons.config.ConfigEntry;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;

public interface ConfigAppService {
    public List<ConfigEntry> getEntries(AuthenticatedUser user) throws AccessDeniedException;

    public void updateEntry(AuthenticatedUser user, ConfigEntry entry) throws AccessDeniedException;

    public void createEntry(AuthenticatedUser user, ConfigEntry entry) throws AccessDeniedException;

    public ConfigEntry getEntry(AuthenticatedUser user, String key) throws AccessDeniedException;
}
