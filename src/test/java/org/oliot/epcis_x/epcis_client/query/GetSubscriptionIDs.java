package org.oliot.epcis_x.epcis_client.query;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.oliot.epcis.client.EPCISQueryClient;
import org.oliot.epcis.client.EPCISQueryClient.EPCISQueryType;

public class GetSubscriptionIDs {

	public URL url = null;

	@Before
	public void initialize() throws MalformedURLException {
		url = new URL("http://dfpl.sejong.ac.kr:8082/epcis/query");
		// url = new URL("http://localhost:8081/epcis/query");
	}
	
	@Test
	public void getStandardVersion() throws IOException, InterruptedException   {

		EPCISQueryClient client = new EPCISQueryClient(url);
		List<String> queryNames = client.showHTTPBody(EPCISQueryType.GetSubscriptionIDs).getSubscriptionIDs();
		queryNames.forEach(System.out::println);
		assertTrue(queryNames instanceof List);
	}
}
