package org.oliot.epcis_x.epcis_client.query;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.oliot.epcis.client.EPCISQueryClient;
import org.oliot.epcis.client.EPCISQueryClient.EPCISQueryType;
import org.oliot.epcis.model.QuerySchedule;

public class SubscriptionAltogether {

	public URL url = null;

	@Before
	public void initialize() throws MalformedURLException {
		url = new URL("http://dfpl.sejong.ac.kr:8082/epcis/query");
		// url = new URL("http://localhost:8081/epcis/query");
	}

	@Test
	public void testQueries() throws IOException, InterruptedException {
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		EPCISQueryClient client = new EPCISQueryClient(url);
		List<String> queryNames = client.showHTTPBody(EPCISQueryType.GetQueryNames).getQueryNames();
		queryNames.forEach(System.out::println);
		assertTrue(queryNames instanceof List);
		String standardVersion = client.showHTTPBody(EPCISQueryType.GetStandardVersion).getStandardVersion();
		System.out.println(standardVersion);
		assertTrue(standardVersion instanceof String);
		String vendorVersion = client.showHTTPBody(EPCISQueryType.GetVendorVersion).getVendorVersion();
		System.out.println(vendorVersion);
		assertTrue(vendorVersion instanceof String);

		QuerySchedule qs = new QuerySchedule();
		qs.setSecond("0/10");
		boolean isSubscribed = client
				.prepareSubscriptionControl("3", new URL("http://localhost:8082/epcis/test"), qs, new Date(), true)
				.build().showHTTPBody(EPCISQueryType.SubscribeEvents).subscribe();
		if (isSubscribed)
			System.out.println(isSubscribed + " subscribed ");
		else
			System.out.println("subscription " + isSubscribed + " failed ");

		List<String> subscriptionIDs = client.showHTTPBody(EPCISQueryType.GetSubscriptionIDs).getSubscriptionIDs();
		System.out.println("--subscription list---");
		subscriptionIDs.forEach(System.out::println);

		for (String subscriptionID : subscriptionIDs) {
			boolean isUnsubscribed = client.showHTTPBody(EPCISQueryType.Unsubscribe).unsubscribe(subscriptionID);
			if (isUnsubscribed)
				System.out.println(subscriptionID + " unsubscribed ");
			else
				System.out.println("unsubscribe " + subscriptionID + " failed ");
		}

	}
}
