package org.oliot.epcis_x.epcis_client.query;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.oliot.epcis.client.EPCISQueryClient;
import org.oliot.epcis.client.EPCISQueryClient.EPCISQueryType;

public class Unsubscribe {

	public URL url = null;

	@Before
	public void initialize() throws MalformedURLException {
		url = new URL("http://dfpl.sejong.ac.kr:8082/epcis/query");
		// url = new URL("http://localhost:8081/epcis/query");
	}
	
	@Test
	public void unsubscribe() throws IOException, InterruptedException   {
		EPCISQueryClient client = new EPCISQueryClient(url);
		boolean isUnsubscribed = client.showHTTPBody(EPCISQueryType.Unsubscribe).unsubscribe("1");
		System.out.println(isUnsubscribed);
	}
}
