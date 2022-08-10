package org.oliot.epcis_x.epcis_client.query;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.oliot.epcis.client.EPCISQueryClient;
import org.oliot.epcis.client.EPCISQueryClient.EPCISQueryType;

public class GetStandardVersion {

	public URL url = null;

	@Before
	public void initialize() throws MalformedURLException {
		url = new URL("http://dfpl.sejong.ac.kr:8081/epcis/query");
		// url = new URL("http://localhost:8081/epcis/query");
	}

	@Test
	public void getStandardVersion() throws IOException, InterruptedException {

		EPCISQueryClient client = new EPCISQueryClient(url);
		String standardVersion = client.showHTTPBody(EPCISQueryType.GetStandardVersion).getStandardVersion();
		System.out.println(standardVersion);
		assertTrue(standardVersion instanceof String);
	}
}
