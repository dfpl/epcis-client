package org.oliot.epcis_x.epcis_client.capture;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpResponse;
import java.text.ParseException;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.junit.Before;
import org.junit.Test;
import org.oliot.epcis.client.EPCISCaptureClient;
import org.oliot.epcis.client.document_builder.ExtensionBuilder;
import org.oliot.epcis.client.document_builder.VocabularyBuilder;
import org.oliot.epcis.model.cbv.EPCISVocabularyType;
import org.oliot.epcis.model.exception.ValidationException;

public class CaptureMasterData {
	
	public URL url = null;

	@Before
	public void initialize() throws MalformedURLException {
		url = new URL("http://dfpl.sejong.ac.kr:8080/epcis/capture");
		// url = new URL("http://localhost:8080/epcis/capture");
	}

	@Test
	public void captureVocabulary() throws DatatypeConfigurationException, ParseException, IOException,
			InterruptedException, URISyntaxException, ValidationException, TransformerFactoryConfigurationError,
			TransformerException, ParserConfigurationException, JAXBException {
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		EPCISCaptureClient client = new EPCISCaptureClient(url);
		client.addVocabulary(
				new VocabularyBuilder(EPCISVocabularyType.BusinessLocationID, "urn:epc:id:sgln:0037000.00729.0")
						.addAttribute("http://ext.com/ext1#string", "stringValue")
						.addAttribute("http://ext.com/ext1#object1",
								new ExtensionBuilder("http://ext.com/ext1", "object2", "ext1")
										.addInnerExtension("http://ext.com/ext1", "string", "ext1", "val1")));
		client.addVocabulary(
				new VocabularyBuilder(EPCISVocabularyType.BusinessLocationID, "urn:epc:id:sgln:0037000.00729.0")
						.addAttribute("http://ext.com/ext1#string", "stringValue")
						.addAttribute("http://ext.com/ext1#object1",
								new ExtensionBuilder("http://ext.com/ext1", "object2", "ext1")
										.addInnerExtension("http://ext.com/ext1", "string", "ext1", "val1"))
						.setChildren(List.of("urn:epc:id:sgln:0037000.00729.8201", "urn:epc:id:sgln:0037000.00729.8202",
								"urn:epc:id:sgln:0037000.00729.8203"))
						.addChild("urn:epc:id:sgln:0037000.00729.8204").addChild("urn:epc:id:sgln:0037000.00729.8205"));
		client.addVocabulary(new VocabularyBuilder(EPCISVocabularyType.EPCClass, "urn:epc:id:sgtin:8801094.001300.*")
				.addAttribute("http://schema.org/NutritionInformation#category", "beverage")
				.addAttribute("http://schema.org/NutritionInformation#name", "Coca-cola")
				.addAttribute("http://schema.org/NutritionInformation#servingSize", "600")
				.addAttribute("http://schema.org/NutritionInformation#calories", "258126.195")
				.addAttribute("http://schema.org/NutritionInformation#sugarContent", "64")
				.addAttribute("http://schema.org/NutritionInformation#saturatedFatContent", "0")
				.addAttribute("http://schema.org/NutritionInformation#fatContent", "0")
				.addAttribute("http://schema.org/NutritionInformation#sodiumContent", "60")
				.addAttribute("http://schema.org/NutritionInformation#fiberContent", "0")
				.addAttribute("http://schema.org/NutritionInformation#proteinContent", "0"));
		System.out.println(client.exportToXML());
		HttpResponse<String> result = client.send();
		// System.out.println(result.body());
		assertTrue(result.statusCode() == 200);
	}
}
