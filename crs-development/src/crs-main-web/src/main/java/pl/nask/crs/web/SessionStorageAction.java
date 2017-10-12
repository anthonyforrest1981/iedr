package pl.nask.crs.web;

import java.io.IOException;

public interface SessionStorageAction {
    public String addToStorage() throws IOException;

    public String removeFromStorage() throws IOException;
}
