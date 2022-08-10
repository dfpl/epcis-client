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
import org.oliot.epcis.model.VocabularyType;

public class PollAltogether {

	public URL url = null;

	@Before
	public void initialize() throws MalformedURLException {
		url = new URL("http://dfpl.sejong.ac.kr:8081/epcis/query");
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
		List<Object> result = client.prepareEventQueryParameters()
				.equalBizLocation(List.of("urn:epc:id:sgln:0037000.00729.8202"), false).build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		System.out.println(result.size());
		List<VocabularyType> results = client.prepareVocabularyQueryParameters(true, true).build()
				.showHTTPBody(EPCISQueryType.PollVocabularies).pollVocabularies();
		System.out.println(results.size());
	}
}
