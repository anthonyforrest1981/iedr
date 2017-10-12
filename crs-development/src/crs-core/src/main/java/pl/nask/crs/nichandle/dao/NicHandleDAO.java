package pl.nask.crs.nichandle.dao;

import pl.nask.crs.commons.dao.GenericDAO;
import pl.nask.crs.nichandle.NicHandle;

public interface NicHandleDAO extends GenericDAO<NicHandle, String> {

    void deleteMarkedNichandles();

    NicHandle getDirectNhForContact(String nicHandleId);
}
