package pl.nask.crs.entities;

import java.util.List;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import pl.nask.crs.commons.AbstractTest;
import pl.nask.crs.entities.dao.OwnerTypeDAO;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;

public class OwnerTypeDAOTest extends AbstractTest {

    @Resource OwnerTypeDAO ownerTypeDAO;

    private static final long OWNER_TYPE_ID = 1L;

    @Test
    public void testGet() {
        OwnerType ownerType = ownerTypeDAO.get(OWNER_TYPE_ID);
        assertNotNull(ownerType);
        assertEquals(ownerType.getId(), OWNER_TYPE_ID);
        assertEquals(ownerType.getName(), "Company");
        assertEquals(ownerType.getClassId(), 2L);
        assertEquals(ownerType.getCategoryId(), 11L);
    }

    @Test
    public void testGetByName() {
        OwnerType ownerType = ownerTypeDAO.getByName("Company");
        assertNotNull(ownerType);
        assertEquals(ownerType.getId(), OWNER_TYPE_ID);
        assertEquals(ownerType.getName(), "Company");
        assertEquals(ownerType.getClassId(), 2L);
        assertEquals(ownerType.getCategoryId(), 11L);
    }

    @Test
    public void testGetAll() {
        List<OwnerType> ownerTypes = ownerTypeDAO.getAllOwnerTypes();
        assertNotNull(ownerTypes);
        assertFalse(ownerTypes.isEmpty());
    }

}
