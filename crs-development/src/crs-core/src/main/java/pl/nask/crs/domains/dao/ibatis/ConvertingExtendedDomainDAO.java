package pl.nask.crs.domains.dao.ibatis;

import pl.nask.crs.commons.dao.Converter;
import pl.nask.crs.commons.dao.ConvertingGenericDAO;
import pl.nask.crs.commons.dao.GenericDAO;
import pl.nask.crs.domains.ExtendedDomain;
import pl.nask.crs.domains.dao.ExtendedDomainDAO;
import pl.nask.crs.domains.dao.ibatis.objects.InternalExtendedDomain;

public class ConvertingExtendedDomainDAO extends ConvertingGenericDAO<InternalExtendedDomain, ExtendedDomain, String>
        implements ExtendedDomainDAO {

    public ConvertingExtendedDomainDAO(GenericDAO<InternalExtendedDomain, String> internalDao,
            Converter<InternalExtendedDomain, ExtendedDomain> internalConverter) {
        super(internalDao, internalConverter);
    }

}
