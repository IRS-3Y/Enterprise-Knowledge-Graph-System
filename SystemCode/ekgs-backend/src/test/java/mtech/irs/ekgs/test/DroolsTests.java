package mtech.irs.ekgs.test;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import mtech.irs.ekgs.config.KieConfiguration;
import mtech.irs.ekgs.kie.KieSystem;
import mtech.irs.ekgs.test.model.Applicant;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = KieConfiguration.class)
public class DroolsTests {
	
	@Autowired
	private KieSystem kie;

	@Test
    public void testDrlStateless() throws IOException {
        StatelessKieSession session = kie.newStatelessSession();
        Assert.assertNotNull(session);
        
        Applicant applicant = new Applicant( "Mr John Smith", 16 );
        Assert.assertTrue( applicant.isValid() );
        session.execute( applicant );
        Assert.assertFalse( applicant.isValid() );
    }
    
    @Test
    public void testDrlStateful() throws IOException {
    	KieSession session = kie.newSession();
    	Assert.assertNotNull(session);
        try{
            Applicant applicant = new Applicant( "Mr John Smith", 16 );
            Assert.assertTrue( applicant.isValid() );
            session.insert( applicant );
            session.fireAllRules();
            Assert.assertFalse( applicant.isValid() );
        }finally {
        	session.dispose();
        }
    }
}
