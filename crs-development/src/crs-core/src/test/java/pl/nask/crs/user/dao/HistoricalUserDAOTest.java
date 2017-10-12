package pl.nask.crs.user.dao;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

import pl.nask.crs.commons.TestOpInfo;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.user.User;
import pl.nask.crs.user.UserHelper;
import pl.nask.crs.user.search.HistoricalUserSearchCriteria;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

@ContextConfiguration(locations = {"/users-config.xml", "/users-config-test.xml", "/test-config.xml",
        "/commons-base-config.xml"})
public class HistoricalUserDAOTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    HistoricalUserDAO historicalUserDAO;

    @Autowired
    UserDAO userDAO;

    @Test
    public final void testCreate() {
        String username = "IDL2-IEDR";
        String chngBy = TestOpInfo.DEFAULT.getActorName();
        Date now = DateUtils.setMilliseconds(new Date(), 999);

        User user = userDAO.get(username);
        historicalUserDAO.create(user, now, chngBy);

        HistoricalUserSearchCriteria criteria = new HistoricalUserSearchCriteria(username);
        LimitedSearchResult<HistoricalObject<User>> res = historicalUserDAO.find(criteria, 0, 1);

        // first historical object should contain current user
        assertNotNull(res.getResults());
        assertTrue(res.getResults().size() > 0);
        HistoricalObject<User> histUser = res.getResults().get(0);

        assertEquals(histUser.getChangeDate(), DateUtils.truncate(now, Calendar.SECOND));
        assertEquals(histUser.getObject().getUsername(), user.getUsername());
        assertEquals(histUser.getChangedBy(), chngBy);

        UserHelper.compareUsers(histUser.getObject(), user);
    }

    @Test
    public void testLooseUtf8Validation() {
        HistoricalUserSearchCriteria criteria = new HistoricalUserSearchCriteria("AA10-IEDR\01");
        LimitedSearchResult<HistoricalObject<User>> result = historicalUserDAO.find(criteria, 0, 1);
        assertEquals(result.getTotalResults(), 1, "Should find one historical user");
        HistoricalObject<User> user = result.getResults().get(0);
        assertEquals(user.getChangedBy(), "IDL2-IEDR\01");
    }
}
