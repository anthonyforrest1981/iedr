package pl.nask.crs.nichandle.service;

import java.util.Arrays;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.nichandle.AbstractContextAwareTest;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.dao.NicHandleDAO;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static pl.nask.crs.nichandle.testhelp.NicHandleTestHelp.*;

public class NicHandleSearchServiceTest extends AbstractContextAwareTest {

    private static final String NH_ID_1 = "AA11-IEDR";
    private static final String NH_ID_2 = "AAE553-IEDR";
    private static final String NH_ID_3 = "AAE359-IEDR";

    @Resource
    protected NicHandleSearchService searchService;
    @Resource
    protected NicHandleDAO nicHandleDAO;

    @Test
    public void getNicHandleTest() throws Exception {
        NicHandle nicHandle = searchService.getNicHandle(NH_ID_1);
        compareNicHandle(nicHandle, nicHandleDAO.get(NH_ID_1));
    }

    @Test(expectedExceptions = NicHandleNotFoundException.class)
    public void getNicHandleNotExistsTest() throws Exception {
        searchService.getNicHandle("NOT-EXISTS-IEDR");
    }

    @Test
    public void findNicHandlesByNicHandleTest() {
        SearchResult<NicHandle> result = searchService.findNicHandle(criteria1);
        compareNicHandleList(result.getResults(), Arrays.asList(nicHandleDAO.get(NH_ID_3), nicHandleDAO.get(NH_ID_2)));
    }

    @Test
    public void findNicHandlesByNicHandleWithLimitTest() {
        LimitedSearchResult<NicHandle> result = searchService.findNicHandle(criteria1, 0, 1, null);
        compareNicHandleList(result.getResults(), Arrays.asList(nicHandleDAO.get(NH_ID_3)));
    }

    @Test
    public void isNicHandleDirect() throws Exception{
        assertFalse(searchService.isNicHandleDirect(NH_ID_1));
        assertFalse(searchService.isNicHandleDirect(NH_ID_2));
        assertTrue(searchService.isNicHandleDirect(NH_ID_3));
    }

}
