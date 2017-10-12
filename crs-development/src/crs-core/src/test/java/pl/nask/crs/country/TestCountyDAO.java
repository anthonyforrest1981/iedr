package pl.nask.crs.country;

import javax.annotation.Resource;

import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.commons.AbstractTest;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.country.dao.CountyDAO;
import pl.nask.crs.country.search.CountySearchCriteria;

public class TestCountyDAO extends AbstractTest {
    @Resource
    CountyDAO countyDAO;

    @Test
    public void testFindNone() {
        CountySearchCriteria c = new CountySearchCriteria();
        c.setCountryId(1); // Afghanistan
        SearchResult<County> r = countyDAO.find(c);

        AssertJUnit.assertNotNull("No results found 1", r);
        AssertJUnit.assertNotNull("No results found 2", r.getResults());
        AssertJUnit.assertEquals("Illegal number of counties", 0, r.getResults().size());
    }

    @Test
    public void testFindAll() {
        SearchResult<County> r = countyDAO.find(null);

        AssertJUnit.assertNotNull("No results found 1", r);
        AssertJUnit.assertNotNull("No results found 2", r.getResults());

        AssertJUnit.assertTrue("More than zero results expected", r.getResults().size() > 0);
    }

    @Test
    public void testFindIrelandById() {
        CountySearchCriteria c = new CountySearchCriteria();
        c.setCountryId(121);
        SearchResult<County> r = countyDAO.find(c);

        AssertJUnit.assertNotNull("No results found 1", r);
        AssertJUnit.assertNotNull("No results found 2", r.getResults());
        AssertJUnit.assertEquals("Illegal number of counties", 28, r.getResults().size());
    }

    @Test
    public void testFindNorthernIreland() {
        CountySearchCriteria c = new CountySearchCriteria();
        c.setCountryId(119);
        SearchResult<County> r = countyDAO.find(c);

        AssertJUnit.assertNotNull("No results found 1", r);
        AssertJUnit.assertNotNull("No results found 2", r.getResults());
        AssertJUnit.assertEquals("Illegal number of counties", 6, r.getResults().size());
    }

    @Test
    public void testFindUK() {
        CountySearchCriteria c = new CountySearchCriteria();
        c.setCountryId(254); // UK
        SearchResult<County> r = countyDAO.find(c);

        AssertJUnit.assertNotNull("No results found 1", r);
        AssertJUnit.assertNotNull("No results found 2", r.getResults());
        AssertJUnit.assertEquals("Illegal number of counties", 76, r.getResults().size());
    }

    @Test
    public void testFindUSA() {
        CountySearchCriteria c = new CountySearchCriteria();
        c.setCountryId(265); // USA
        SearchResult<County> r = countyDAO.find(c);

        AssertJUnit.assertNotNull("No results found 1", r);
        AssertJUnit.assertNotNull("No results found 2", r.getResults());
        AssertJUnit.assertEquals("Illegal number of counties", 51, r.getResults().size());
    }

    @Test
    public void testFindByName() {
        CountySearchCriteria c = new CountySearchCriteria();
        c.setCountyName("Avon");
        SearchResult<County> r = countyDAO.find(c);

        AssertJUnit.assertNotNull("No results found 1", r);
        AssertJUnit.assertNotNull("No results found 2", r.getResults());
        AssertJUnit.assertEquals("Illegal number of counties", 1, r.getResults().size());
    }

    @Test
    public void testFindUft8Name() {
        CountySearchCriteria c = new CountySearchCriteria();
        c.setCountyName("Co. Düblin1");
        AssertJUnit.assertEquals(1, countyDAO.count(c));
        c.setCountyName("Cö. Dooblin");
        AssertJUnit.assertEquals(1, countyDAO.count(c));
    }
}
