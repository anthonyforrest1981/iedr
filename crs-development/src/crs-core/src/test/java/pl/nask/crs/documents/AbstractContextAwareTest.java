package pl.nask.crs.documents;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;

@ContextConfiguration(locations = {"/application-services-config.xml", "/commons.xml",
        "/incoming-docs-config-test.xml", "/test-config.xml"})
public abstract class AbstractContextAwareTest extends AbstractTransactionalTestNGSpringContextTests {

}
