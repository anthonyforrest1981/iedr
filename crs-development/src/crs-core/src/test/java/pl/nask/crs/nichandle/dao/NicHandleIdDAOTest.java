package pl.nask.crs.nichandle.dao;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import pl.nask.crs.nichandle.AbstractContextAwareTest;

import static org.testng.AssertJUnit.assertEquals;

/**
 * @author Marianna Mysiorska
 */
public class NicHandleIdDAOTest extends AbstractContextAwareTest {

    @Resource
    NicHandleIdDAO nicHandleIdDAO;

    @Test
    public void getNicHandleId() {
        Long nicHandleId = nicHandleIdDAO.get();
        assertEquals(194694, nicHandleId.longValue());
    }

    @Test
    public void updateNicHandleId() {
        Long nicHandleId = nicHandleIdDAO.get();
        assertEquals(194694, nicHandleId.longValue());
        nicHandleIdDAO.update(194695L);
        nicHandleId = nicHandleIdDAO.get();
        assertEquals(194695, nicHandleId.longValue());
        nicHandleIdDAO.update(194694L);
        nicHandleId = nicHandleIdDAO.get();
        assertEquals(194694, nicHandleId.longValue());
    }

}
