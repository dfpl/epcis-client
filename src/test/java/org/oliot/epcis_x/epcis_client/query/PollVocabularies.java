package org.oliot.epcis_x.epcis_client.query;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


import org.junit.Before;
import org.junit.Test;
import org.oliot.epcis.client.EPCISQueryClient;
import org.oliot.epcis.client.EPCISQueryClient.EPCISQueryType;
import org.oliot.epcis.model.VocabularyType;

public class PollVocabularies {

	public URL url = null;

	@Before
	public void initialize() throws MalformedURLException {
		url = new URL("http://dfpl.sejong.ac.kr:8081/epcis/query");
		// url = new URL("http://localhost:8081/epcis/query");
	}

	public void pollAllVocabulariesWOBuilder() throws IllegalAccessError, IOException, InterruptedException  {
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		EPCISQueryClient client = new EPCISQueryClient(url);
		client.showHTTPBody(EPCISQueryType.PollVocabularies).pollVocabularies();
		System.out.println("Should throw Error IllegalAccessError");
	}
	
	@Test
	public void pollAllVocabularies() throws IllegalAccessError, IOException, InterruptedException  {
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		EPCISQueryClient client = new EPCISQueryClient(url);
		List<VocabularyType> results = client.prepareVocabularyQueryParameters(true, true).build()
				.showHTTPBody(EPCISQueryType.PollVocabularies).pollVocabularies();
		System.out.println(results.size());
	}

}
