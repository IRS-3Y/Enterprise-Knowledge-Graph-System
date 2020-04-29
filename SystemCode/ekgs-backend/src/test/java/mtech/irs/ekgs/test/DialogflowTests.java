package mtech.irs.ekgs.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import mtech.irs.ekgs.gcloud.DialogflowClient;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DialogflowClient.class)
public class DialogflowTests {

	@Autowired
	private DialogflowClient client;
	
	@Test
	public void testDetectIntent() throws Exception {
		client.detectIntent("hello", null);
	}
}
