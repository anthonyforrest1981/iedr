package pl.nask.crs.commons.email;

import java.util.List;

import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pl.nask.crs.commons.email.service.EmailParameters;
import pl.nask.crs.commons.email.service.ParameterName;
import pl.nask.crs.commons.email.service.impl.EmailInstantiatorImpl;
import pl.nask.crs.ticket.AbstractContextAwareTest;

public class EmailInstantiatorImplTest extends AbstractContextAwareTest {

    private EmailInstantiatorImpl ei;

    @BeforeMethod
    public void setUp() throws Exception {
        ei = new EmailInstantiatorImpl();
    }

    // CRS-72
    @Test
    public void instatiateShouldThrowAnException() throws Exception {
        // having
        EmailParameters anyParameters = new EmailParameters() {
            public String getLoggedInNicHandle() {
                return null; //TODO: Not implemented yet
            }

            public String getAccountRelatedNicHandle(boolean gaining) {
                return null; //TODO: Not implemented yet
            }

            public String getDomainName() {
                return null; //TODO: Not implemented yet
            }

            @Override
            public List<? extends ParameterName> getParameterNames() {
                return null; //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getParameterValue(String name, boolean html) {
                return null; //To change body of implemented methods use File | Settings | File Templates.
            }
        };
        EmailTemplate anyTemplate = new EmailTemplate();
        int anyId = 8998;
        anyTemplate.setId(anyId);

        Email email = ei.instantiate(anyTemplate, anyParameters, false);
        AssertJUnit.assertEquals(email.isToBeSent(), false);
    }
}
