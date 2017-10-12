package pl.nask.crs.nichandle.dao.ibatis;

import pl.nask.crs.commons.dao.Converter;
import pl.nask.crs.commons.dao.ConvertingGenericDAO;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.dao.NicHandleDAO;
import pl.nask.crs.nichandle.dao.ibatis.objects.InternalNicHandle;

public class ConvertingNicHandleDAO extends ConvertingGenericDAO<InternalNicHandle, NicHandle, String> implements
        NicHandleDAO {

    InternalNicHandleIBatisDAO internalDao;

    public ConvertingNicHandleDAO(InternalNicHandleIBatisDAO internalDao,
            Converter<InternalNicHandle, NicHandle> internalConverter) {
        super(internalDao, internalConverter);
        Validator.assertNotNull(internalDao, "internal dao");
        Validator.assertNotNull(internalConverter, "internal converter");
        this.internalDao = internalDao;

    }

    @Override
    public void deleteMarkedNichandles() {
        internalDao.deleteMarkedNichandles();
    }

    @Override
    public NicHandle getDirectNhForContact(String nicHandleId) {
        Validator.assertNotNull(nicHandleId, "nicHandleId");
        String directId = internalDao.getBillingNhForContact(nicHandleId);
        if (directId == null) {
            return null;
        } else {
            return get(directId);
        }
    }
}
