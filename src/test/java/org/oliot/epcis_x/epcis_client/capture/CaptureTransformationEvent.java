package org.oliot.epcis_x.epcis_client.capture;


import static org.junit.Assert.assertTrue;



import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpResponse;
import java.text.ParseException;
import java.util.Date;

import java.util.List;


import javax.xml.namespace.QName;


import org.junit.Before;
import org.junit.Test;
import org.oliot.epcis.client.EPCISCaptureClient;
import org.oliot.epcis.client.document_builder.ExtensionBuilder;
import org.oliot.epcis.client.document_builder.SensorElementBuilder;
import org.oliot.epcis.client.document_builder.SensorReportBuilder;
import org.oliot.epcis.client.document_builder.TransformationEventBuilder;
import org.oliot.epcis.model.cbv.BusinessStep;
import org.oliot.epcis.model.cbv.BusinessTransactionType;
import org.oliot.epcis.model.cbv.Disposition;
import org.oliot.epcis.model.cbv.SourceDestinationType;
import org.oliot.epcis.model.exception.ValidationException;
import org.oliot.epcis.unit_converter.unit.Temperature;
import org.oliot.epcis.unit_converter.unit.Temperature.Type;

public class CaptureTransformationEvent {

	public URL url = null;

	@Before
	public void initialize() throws MalformedURLException {
		url = new URL("http://dfpl.sejong.ac.kr:8080/epcis/capture");
		// url = new URL("http://localhost:8080/epcis/capture");
	}
	
	
	
	//minimal test 1
	public void captureTestMinimal1() throws ValidationException, ParseException {
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISCaptureClient client = new EPCISCaptureClient(url);
		
		client.addTransformationEvent(new TransformationEventBuilder("2020-10-15T20:33:31.116Z", "-09:00").build());
		
		System.out.println(client.exportToXML());
		
		HttpResponse<String> result = client.send();
		assertTrue(result.statusCode() == 200);
		
	}
	
	//minimal test 2
	public void captureTestMinimal2() throws ValidationException, ParseException {
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISCaptureClient client = new EPCISCaptureClient(url);
		
		client.addTransformationEvent(new TransformationEventBuilder(System.currentTimeMillis(), "Z").build());
		
		System.out.println(client.exportToXML());
		
		HttpResponse<String> result = client.send();
		assertTrue(result.statusCode() == 200);
	}
	
	
	//input EPCList test
	public void captureTestInputEPCList() throws ValidationException, ParseException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISCaptureClient client = new EPCISCaptureClient(url);
		
		client.addTransformationEvent(new TransformationEventBuilder("2020-10-15T20:33:31.116Z", "-09:00")
				.setInputEPCList(List.of("urn:epc:id:sgtin:4012345.011122.25", "urn:epc:id:sgtin:4000001.065432.99886655"))
				.addInputEPC("urn:epc:id:sgtin:0614141.712345.23")
				.build());
		
		System.out.println(client.exportToXML());
		
		HttpResponse<String> result = client.send();
		assertTrue(result.statusCode() == 200);
	}
	
	
	//output EPCList test
	
	public void captureTestOutputEPCList() throws ValidationException, ParseException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISCaptureClient client = new EPCISCaptureClient(url);
		
		client.addTransformationEvent(new TransformationEventBuilder("2020-10-15T20:33:31.116Z", "-09:00")
				.setOutputEPCList(List.of("urn:epc:id:sgtin:4012345.011122.25", "urn:epc:id:sgtin:4000001.065432.99886655"))
				.addOutputEPC("urn:epc:id:sgtin:0614141.712345.23")
				.build());
		
		System.out.println(client.exportToXML());
		
		HttpResponse<String> result = client.send();
		assertTrue(result.statusCode() == 200);
		
		
	}
	
	
	
	//input QuantityList test
	
	public void captureTestInputQuantityList() throws ValidationException, ParseException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISCaptureClient client = new EPCISCaptureClient(url);
		
		client.addTransformationEvent(new TransformationEventBuilder("2020-10-15T20:33:31.116Z", "-09:00")
				.addInputQuantityElement("urn:epc:class:lgtin:4012345.011111.4444", Double.valueOf(10), "KGM")
				.build());
		
		System.out.println(client.exportToXML());
		
		HttpResponse<String> result = client.send();
		assertTrue(result.statusCode() == 200);
		
	}
	
	
	
	//output QuantityList test
	
	public void captureTestOutputQuantityList() throws ValidationException, ParseException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISCaptureClient client = new EPCISCaptureClient(url);
		
		client.addTransformationEvent(new TransformationEventBuilder("2020-10-15T20:33:31.116Z", "-09:00")
				.addOutputQuantityElement("urn:epc:idpat:sgtin:4012345.066666.*", Double.valueOf(220), null)
				.build());
		
		System.out.println(client.exportToXML());
		
		HttpResponse<String> result = client.send();
		assertTrue(result.statusCode() == 200);
	}
	
	
	//transformationID test 
	
	public void captureTestTransformationId() throws ValidationException, ParseException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISCaptureClient client = new EPCISCaptureClient(url);
		
		client.addTransformationEvent(new TransformationEventBuilder("2020-10-15T20:33:31.116Z", "-09:00")
				.setTransformationId("urn:epc:id:gdti:0614141.12345.400")
				.build());
		
		System.out.println(client.exportToXML());
		
		HttpResponse<String> result = client.send();
		assertTrue(result.statusCode() == 200);
		
	}
	
	//business step test
	
	public void captureTestBizStep() throws ValidationException, ParseException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISCaptureClient client = new EPCISCaptureClient(url);
		
		client.addTransformationEvent(new TransformationEventBuilder("2020-10-15T20:33:31.116Z", "-09:00")
				.setBizStep(BusinessStep.commissioning)
				.build());
		
		System.out.println(client.exportToXML());
		
		HttpResponse<String> result = client.send();
		assertTrue(result.statusCode() == 200);
		
	}
	
	//disposition test
	
	public void captureTestDisposition() throws ValidationException, ParseException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISCaptureClient client = new EPCISCaptureClient(url);
		
		client.addTransformationEvent(new TransformationEventBuilder("2020-10-15T20:33:31.116Z", "-09:00")
				.setDisposition(Disposition.in_progress)
				.build());
		
		System.out.println(client.exportToXML());
		
		HttpResponse<String> result = client.send();
		assertTrue(result.statusCode() == 200);
		
	}
	
	//read point test
	
	public void captureTestReadPoint() throws ValidationException, ParseException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISCaptureClient client = new EPCISCaptureClient(url);
		
		client.addTransformationEvent(new TransformationEventBuilder("2020-10-15T20:33:31.116Z", "-09:00")
				.setReadPoint("urn:epc:id:sgln:4012345.00001.0")
				.build());
		
		System.out.println(client.exportToXML());
		
		HttpResponse<String> result = client.send();
		assertTrue(result.statusCode() == 200);
		
	}
	
	//business location test
	
	public void captureTestBizLocation() throws ValidationException, ParseException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISCaptureClient client = new EPCISCaptureClient(url);
		
		client.addTransformationEvent(new TransformationEventBuilder("2020-10-15T20:33:31.116Z", "-09:00")
				.setBizLocation("urn:epc:id:sgln:0614141.00888.0")
				.build());
		
		System.out.println(client.exportToXML());
		
		HttpResponse<String> result = client.send();
		assertTrue(result.statusCode() == 200);
		
	}
	
	
	//business transaction test
	
	public void captureTestBizTransactionList() throws ValidationException, ParseException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISCaptureClient client = new EPCISCaptureClient(url);
		
		client.addTransformationEvent(new TransformationEventBuilder("2020-10-15T20:33:31.116Z", "-09:00")
				.addBizTransaction(BusinessTransactionType.po, "urn:epc:id:gdti:0614141.00001.1618034")
				.addBizTransaction(BusinessTransactionType.pedigree, "urn:epc:id:gsrn:0614141.0000010253")
				.build());
		
		System.out.println(client.exportToXML());
		
		HttpResponse<String> result = client.send();
		assertTrue(result.statusCode() == 200);
		
	}
	
	//source test
	
	public void captureTestSourceList() throws ValidationException, ParseException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISCaptureClient client = new EPCISCaptureClient(url);
		
		client.addTransformationEvent(new TransformationEventBuilder("2020-10-15T20:33:31.116Z", "-09:00")
				.addSource(SourceDestinationType.location,"urn:epc:id:sgln:4012345.00225.0")
				.addSource(SourceDestinationType.possessing_party,"urn:epc:id:pgln:4012345.00225")
				.addSource(SourceDestinationType.owning_party,"urn:epc:id:pgln:4012345.00225")
				.build());
		
		System.out.println(client.exportToXML());
		
		HttpResponse<String> result = client.send();
		assertTrue(result.statusCode() == 200);
		
	}
	
	
	//destination test
	@Test
	public void captureTestDestinationList() throws ValidationException, ParseException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISCaptureClient client = new EPCISCaptureClient(url);
		
		client.addTransformationEvent(new TransformationEventBuilder("2020-10-15T20:33:31.116Z", "-09:00")
				.addDestination(SourceDestinationType.location, "urn:epc:id:sgln:0614141.00777.0")
				.addDestination(SourceDestinationType.possessing_party, "urn:epc:id:pgln:0614141.00777")
				.addDestination(SourceDestinationType.owning_party, "urn:epc:id:pgln:0614141.00777")
				.build());
		
		System.out.println(client.exportToXML());
		
		HttpResponse<String> result = client.send();
		assertTrue(result.statusCode() == 200);
		
	}
	
	
	//sensor data test
	
	public void captureTestSensorElement() throws ValidationException, URISyntaxException, ParseException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISCaptureClient client = new EPCISCaptureClient(url);
		
		
		SensorElementBuilder builder = new SensorElementBuilder()
				.setSensorMetadataTime(new Date())
				.setSensorMetadataDeviceID("urn:epc:id:giai:4000001.111")
				.setSensorMetadataDeviceMetadata("https://id.gs1.org/giai/4000001111")
				.setSensorMetadataRawData("https://example.org/giai/401234599999")
				.setSensorMetadataStartTime(new Date())
				.setSensorMetadataEndTime(new Date())
				.setSensorMetadataBizRules("https://example.com/gdti/4012345000054987")
				.setSensorMetadataDataProcessingMethod("https://example.com/gdti/4012345000054987")
				.addSensorMetadataOtherAttribute(new QName("http://ext.com/ext1", "someFurtherMetadata", "ext1"),
						"someText")
				.addSensorReport(new SensorReportBuilder(new Temperature(Type.CEL, 26.0)).setComponent("example:x")
						.setStringValue("SomeString").setBooleanValue(true).setHexBinaryValue("f0f0f0")
						.setURIValue(new URI("https://id.gs1.org/giai/4000001111")).setMinValue(26.0f)
						.setMaxValue(26.2f).setSDevValue(0.1f)
						.setChemicalSubstance("https://identifiers.org/inchikey:CZMRCDWAGMRECN-UGDNZRGBSA-N")
						.setMicroorganism("https://www.ncbi.nlm.nih.gov/taxonomy/1126011")
						.setDeviceID("urn:epc:id:giai:4000001.111")
						.setDeviceMetadata("https://id.gs1.org/giai/4000001111")
						.setRawData("https://example.org/giai/401234599999").setTime(new Date()).setPercRank(50.0f)
						.setPercValue(12.7f).setDataProcessingMethod("https://example.com/gdti/4012345000054987")
						.addOtherAttribute(new QName("http://ext.com/ext1", "someFurtherMetadata", "ext1"), "someText")
						.addOtherAttribute(new QName("http://ext.com/ext2", "someFurtherMetadata2", "ext2"),
								"someText2")
						.build())
				.addExtension(new ExtensionBuilder("http://dfpl.org/epcis", "integerValue1", "dfpl", 1))
				.addExtension(new ExtensionBuilder("http://dfpl.org/epcis", "integerValue2", "dfpl", 2))
				.addExtension(new ExtensionBuilder("http://dfpl.org/epcis", "integerValue2", "dfpl", 3));
				
				
		client.addTransformationEvent(new TransformationEventBuilder("2020-10-15T20:33:31.116Z", "-09:00")
				.addSensorElement(builder).build());
		
		
		
		
		System.out.println(client.exportToXML());
		
		HttpResponse<String> result = client.send();
		assertTrue(result.statusCode() == 200);
		
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
