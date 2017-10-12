package pl.nask.crs.secondarymarket;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;

@ContextConfiguration(locations = {"/application-services-config.xml", "/secondary-market-config-test.xml",
        "/payment-config-test.xml"})
public class AbstractContextAwareTest extends AbstractTransactionalTestNGSpringContextTests {

}
