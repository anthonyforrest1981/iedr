package pl.nask.crs.ticket;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;

@ContextConfiguration(locations = {"/application-services-config.xml", "/commons-base-config.xml", "/users-config.xml",
        "/domains-config.xml", "/nic-handles-config.xml", "/security-config.xml", "/ticket-config.xml", "/commons.xml",
        "/ticket-config-test.xml", "/test-config.xml"})
public abstract class AbstractContextAwareTest extends AbstractTransactionalTestNGSpringContextTests {

}
