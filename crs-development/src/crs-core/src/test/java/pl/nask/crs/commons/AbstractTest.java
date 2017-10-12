package pl.nask.crs.commons;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;

@ContextConfiguration(locations = {"classpath:application-services-config.xml", "classpath:commons-config-test.xml",})
public abstract class AbstractTest extends AbstractTransactionalTestNGSpringContextTests {

}
