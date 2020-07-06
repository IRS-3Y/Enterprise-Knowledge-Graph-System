package mtech.irs.ekgs.test.gcloud;

import org.junit.Assert;
import org.junit.Test;

import mtech.irs.ekgs.gcloud.GoogleAppCredential;

public class GoogleAppCredentialTests {

	@Test
	public void testLoad() {
		GoogleAppCredential data = GoogleAppCredential.load();
		Assert.assertEquals("ekgs-default", data.getProjectId());
	}
}
