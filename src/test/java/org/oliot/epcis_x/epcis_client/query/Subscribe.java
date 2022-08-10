package org.oliot.epcis_x.epcis_client.query;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.oliot.epcis.client.EPCISQueryClient;
import org.oliot.epcis.client.EPCISQueryClient.EPCISQueryType;
import org.oliot.epcis.model.QuerySchedule;

public class Subscribe {

	public URL url = null;

	@Before
	public void initialize() throws MalformedURLException {
		url = new URL("http://dfpl.sejong.ac.kr:8082/epcis/query");
		// url = new URL("http://localhost:8081/epcis/query");
	}
	
	@Test
	public void subscribePeriodic() throws IOException, InterruptedException   {
		EPCISQueryClient client = new EPCISQueryClient(url);
		QuerySchedule qs = new QuerySchedule();
		qs.setSecond("0/10");
		boolean isSubscribed = client.prepareSubscriptionControl("1", new URL("http://localhost:8082/epcis/test"), qs, new Date(), true).build().showHTTPBody(EPCISQueryType.SubscribeEvents).subscribe();
		System.out.println(isSubscribed);
	}
	
	
	@Test
	public void subscribeTriggered() throws IOException, InterruptedException   {
		EPCISQueryClient client = new EPCISQueryClient(url);
		client.prepareSubscriptionControl("1", new URL("http://localhost:8082/epcis/test"), "1", true).build().showHTTPBody(EPCISQueryType.SubscribeEvents);
	}
}
