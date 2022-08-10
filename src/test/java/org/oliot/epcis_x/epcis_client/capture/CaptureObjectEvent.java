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
import org.oliot.epcis.client.document_builder.ObjectEventBuilder;
import org.oliot.epcis.client.document_builder.SensorElementBuilder;
import org.oliot.epcis.client.document_builder.SensorReportBuilder;
import org.oliot.epcis.model.ActionType;
import org.oliot.epcis.model.cbv.BusinessStep;
import org.oliot.epcis.model.cbv.BusinessTransactionType;
import org.oliot.epcis.model.cbv.Disposition;
import org.oliot.epcis.model.cbv.ErrorReason;
import org.oliot.epcis.model.cbv.SourceDestinationType;
import org.oliot.epcis.model.exception.ValidationException;
import org.oliot.epcis.unit_converter.unit.Temperature;
import org.oliot.epcis.unit_converter.unit.Temperature.Type;

public class CaptureObjectEvent {

	public URL url = null;

	@Before
	public void initialize() throws MalformedURLException {
		url = new URL("http://dfpl.sejong.ac.kr:8080/epcis/capture");
		// url = new URL("http://localhost:8080/epcis/capture");
	}

	//minimal test 1
	public void captureTestMinimal1() throws MalformedURLException, ValidationException, ParseException {
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISCaptureClient client = new EPCISCaptureClient(url);
		
		client.addObjectEvent(new ObjectEventBuilder("2020-10-15T20:33:31.116Z", "-09:00", ActionType.OBSERVE).build());
		
		System.out.println(client.exportToXML());
		
		HttpResponse<String> result = client.send();
		assertTrue(result.statusCode() == 200);
	}

	
	//minimal test 2
	public void captureTestMinimal2() throws MalformedURLException, ValidationException, ParseException {
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISCaptureClient client = new EPCISCaptureClient(url);
		
		client.addObjectEvent(new ObjectEventBuilder(System.currentTimeMillis(), "Z", ActionType.OBSERVE).build());
		
		System.out.println(client.exportToXML());
		
		HttpResponse<String> result = client.send();
		assertTrue(result.statusCode() == 200);
	}

	//EPCList test
	public void captureTestEPCList() throws MalformedURLException, ValidationException, ParseException {
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISCaptureClient client = new EPCISCaptureClient(url);
		
		client.addObjectEvent(new ObjectEventBuilder("2020-10-15T20:33:31.116Z", "-09:00", ActionType.OBSERVE)
				.setEPCList(List.of("urn:epc:id:sgtin:0614141.712345.21", "urn:epc:id:sgtin:0614141.712345.22"))
				.addEPC("urn:epc:id:sgtin:0614141.712345.23").build());
		
		System.out.println(client.exportToXML());
		
		HttpResponse<String> result = client.send();
		assertTrue(result.statusCode() == 200);
	}

	
	//action test 
	public void captureTestAction() throws ValidationException, ParseException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISCaptureClient client = new EPCISCaptureClient(url);
		
		client.addObjectEvent(new ObjectEventBuilder("2020-10-15T20:33:31.116Z", "-09:00", ActionType.OBSERVE)
				.setAction(ActionType.OBSERVE)
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
		
		client.addObjectEvent(new ObjectEventBuilder("2020-10-15T20:33:31.116Z", "-09:00", ActionType.OBSERVE)
				.setBizStep(BusinessStep.receiving)
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
		
		client.addObjectEvent(new ObjectEventBuilder("2020-10-15T20:33:31.116Z", "-09:00", ActionType.OBSERVE)
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
		
		client.addObjectEvent(new ObjectEventBuilder("2020-10-15T20:33:31.116Z", "-09:00", ActionType.OBSERVE)
				.setReadPoint("urn:epc:id:sgln:0012345.11111.400")
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
		
		client.addObjectEvent(new ObjectEventBuilder("2020-10-15T20:33:31.116Z", "-09:00", ActionType.OBSERVE)
				.setBizLocation("urn:epc:id:sgln:0012345.11111.0")
				.build());
		
		System.out.println(client.exportToXML());
		
		HttpResponse<String> result = client.send();
		assertTrue(result.statusCode() == 200);
		
	}
	
	//business transaction test
	public void captureTestBizTransaction() throws ValidationException, ParseException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISCaptureClient client = new EPCISCaptureClient(url);
		
		client.addObjectEvent(new ObjectEventBuilder("2020-10-15T20:33:31.116Z", "-09:00", ActionType.OBSERVE)
				.addBizTransaction(BusinessTransactionType.po, "urn:epc:id:gdti:0614141.00001.1618034")
				.addBizTransaction(BusinessTransactionType.pedigree, "urn:epc:id:gsrn:0614141.0000010253")
				.build());
		
		System.out.println(client.exportToXML());
		
		HttpResponse<String> result = client.send();
		assertTrue(result.statusCode() == 200);
		
	}
	
	
	//quantity list test
	public void captureTestQuantityList() throws ValidationException, ParseException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISCaptureClient client = new EPCISCaptureClient(url);
		
		client.addObjectEvent(new ObjectEventBuilder("2020-10-15T20:33:31.116Z", "-09:00", ActionType.OBSERVE)
				.addQuantityElement("urn:epc:class:lgtin:4012345.012345.998877", Double.valueOf(200), "KGM")
				.build());
		
		System.out.println(client.exportToXML());
		
		HttpResponse<String> result = client.send();
		assertTrue(result.statusCode() == 200);
		
	}
	
	//source list test
	public void captureTestSourceList() throws ValidationException, ParseException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISCaptureClient client = new EPCISCaptureClient(url);
		
		client.addObjectEvent(new ObjectEventBuilder("2020-10-15T20:33:31.116Z", "-09:00", ActionType.OBSERVE)
				.addSource(SourceDestinationType.location, "urn:epc:id:sgln:4012345.00225.0")
				.addSource(SourceDestinationType.possessing_party, "urn:epc:id:pgln:4012345.00225")
				.addSource(SourceDestinationType.owning_party, "urn:epc:id:pgln:4012345.00225")
				.build());
		
		System.out.println(client.exportToXML());
		
		HttpResponse<String> result = client.send();
		assertTrue(result.statusCode() == 200);
		
	}
	
	
	//destination list test
	public void captureTestDestinationList() throws ValidationException, ParseException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISCaptureClient client = new EPCISCaptureClient(url);
		
		client.addObjectEvent(new ObjectEventBuilder("2020-10-15T20:33:31.116Z", "-09:00", ActionType.OBSERVE)
				.addDestination(SourceDestinationType.location, "urn:epc:id:sgln:0614141.00777.0")
				.addDestination(SourceDestinationType.possessing_party, "urn:epc:id:pgln:0614141.00777")
				.addDestination(SourceDestinationType.owning_party, "urn:epc:id:pgln:0614141.00777")
				.build());
		
		System.out.println(client.exportToXML());
		
		HttpResponse<String> result = client.send();
		assertTrue(result.statusCode() == 200);
		
	}
	
	
	
	
	
	
	//extension, sensor 

	
	

	public void captureObjectEventWithStringExtension()
			throws MalformedURLException, ValidationException, ParseException {
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		EPCISCaptureClient client = new EPCISCaptureClient(url);

		client.addObjectEvent(new ObjectEventBuilder("2020-10-15T20:33:31.116Z", "-09:00", ActionType.OBSERVE)
				.addExtension(new ExtensionBuilder("http://dfpl.org/epcis", "stringValue1", "dfpl", "hello"))
				.addExtension(new ExtensionBuilder("http://dfpl.org/epcis", "stringValue2", "dfpl", "epcis"))
				.addExtension(new ExtensionBuilder("http://dfpl.org/epcis", "stringValue2", "dfpl", "world")).build());
		System.out.println(client.exportToXML());
		HttpResponse<String> result = client.send();
		// System.out.println(result.body());
		assertTrue(result.statusCode() == 200);
	}

	public void captureObjectEventWithDoubleExtension()
			throws MalformedURLException, ValidationException, ParseException {
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		EPCISCaptureClient client = new EPCISCaptureClient(url);
		client.addObjectEvent(new ObjectEventBuilder("2020-10-15T20:33:31.116Z", "-09:00", ActionType.OBSERVE)
				.addExtension(new ExtensionBuilder("http://dfpl.org/epcis", "doubleValue1", "dfpl", 3.0))
				.addExtension(new ExtensionBuilder("http://dfpl.org/epcis", "doubleValue2", "dfpl", 5.0))
				.addExtension(new ExtensionBuilder("http://dfpl.org/epcis", "doubleValue2", "dfpl", 7.0)).build());
		System.out.println(client.exportToXML());
		HttpResponse<String> result = client.send();
		// System.out.println(result.body());
		assertTrue(result.statusCode() == 200);
	}

	public void captureObjectEventWithIntegerExtension()
			throws MalformedURLException, ValidationException, ParseException {
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		EPCISCaptureClient client = new EPCISCaptureClient(url);
		client.addObjectEvent(new ObjectEventBuilder("2020-10-15T20:33:31.116Z", "-09:00", ActionType.OBSERVE)
				.addExtension(new ExtensionBuilder("http://dfpl.org/epcis", "integerValue1", "dfpl", 1))
				.addExtension(new ExtensionBuilder("http://dfpl.org/epcis", "integerValue2", "dfpl", 2))
				.addExtension(new ExtensionBuilder("http://dfpl.org/epcis", "integerValue2", "dfpl", 3)).build());
		System.out.println(client.exportToXML());
		HttpResponse<String> result = client.send();
// System.out.println(result.body());
		assertTrue(result.statusCode() == 200);
	}

	public void captureObjectEventWithBooleanExtension()
			throws MalformedURLException, ValidationException, ParseException {
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		EPCISCaptureClient client = new EPCISCaptureClient(url);
		client.addObjectEvent(new ObjectEventBuilder("2020-10-15T20:33:31.116Z", "-09:00", ActionType.OBSERVE)
				.addExtension(new ExtensionBuilder("http://dfpl.org/epcis", "booleanValue1", "dfpl", true))
				.addExtension(new ExtensionBuilder("http://dfpl.org/epcis", "booleanValue2", "dfpl", true))
				.addExtension(new ExtensionBuilder("http://dfpl.org/epcis", "booleanValue2", "dfpl", false)).build());
		System.out.println(client.exportToXML());
		HttpResponse<String> result = client.send();
// System.out.println(result.body());
		assertTrue(result.statusCode() == 200);
	}

	public void captureObjectEventWithDateExtension()
			throws MalformedURLException, ValidationException, ParseException {
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		EPCISCaptureClient client = new EPCISCaptureClient(url);
		client.addObjectEvent(new ObjectEventBuilder("2020-10-15T20:33:31.116Z", "-09:00", ActionType.OBSERVE)
				.addExtension(new ExtensionBuilder("http://dfpl.org/epcis", "dateValue1", "dfpl", new Date()))
				.addExtension(new ExtensionBuilder("http://dfpl.org/epcis", "dateValue2", "dfpl", new Date()))
				.addExtension(new ExtensionBuilder("http://dfpl.org/epcis", "dateValue2", "dfpl", new Date())).build());
		System.out.println(client.exportToXML());
		HttpResponse<String> result = client.send();
		System.out.println(result.body());
		assertTrue(result.statusCode() == 200);
	}

	public void captureObjectEventWithComplexExtension()
			throws MalformedURLException, ValidationException, ParseException {
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		EPCISCaptureClient client = new EPCISCaptureClient(url);
		client.addObjectEvent(new ObjectEventBuilder("2020-10-15T20:33:31.116Z", "-09:00", ActionType.OBSERVE)
				.addExtension(new ExtensionBuilder("http://dfpl.org/epcis", "objectValue", "dfpl")
						.addInnerExtension("http://dfpl.org/epcis", "dateValue1", "dfpl", new Date())
						.addInnerExtension("http://dfpl.org/epcis", "dateValue1", "dfpl", true))
				.addExtension(new ExtensionBuilder("http://dfpl.org/epcis", "dateValue2", "dfpl", new Date()))
				.addExtension(new ExtensionBuilder("http://dfpl.org/epcis", "dateValue2", "dfpl", new Date())).build());
		System.out.println(client.exportToXML());
		HttpResponse<String> result = client.send();
		System.out.println(result.body());
		assertTrue(result.statusCode() == 200);
	}

	public void captureObjectEventWithStringILMD() throws MalformedURLException, ValidationException, ParseException {
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		EPCISCaptureClient client = new EPCISCaptureClient(url);

		client.addObjectEvent(new ObjectEventBuilder("2020-10-15T20:33:31.116Z", "-09:00", ActionType.OBSERVE)
				.addILMD(new ExtensionBuilder("http://dfpl.org/epcis", "stringValue1", "dfpl", "hello"))
				.addILMD(new ExtensionBuilder("http://dfpl.org/epcis", "stringValue2", "dfpl", "epcis"))
				.addILMD(new ExtensionBuilder("http://dfpl.org/epcis", "stringValue2", "dfpl", "world")).build());
		System.out.println(client.exportToXML());
		HttpResponse<String> result = client.send();
// System.out.println(result.body());
		assertTrue(result.statusCode() == 200);
	}

	public void captureObjectEventWithDoubleILMD() throws MalformedURLException, ValidationException, ParseException {
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		EPCISCaptureClient client = new EPCISCaptureClient(url);
		client.addObjectEvent(new ObjectEventBuilder("2020-10-15T20:33:31.116Z", "-09:00", ActionType.OBSERVE)
				.addILMD(new ExtensionBuilder("http://dfpl.org/epcis", "doubleValue1", "dfpl", 3.0))
				.addILMD(new ExtensionBuilder("http://dfpl.org/epcis", "doubleValue2", "dfpl", 5.0))
				.addILMD(new ExtensionBuilder("http://dfpl.org/epcis", "doubleValue2", "dfpl", 7.0)).build());
		System.out.println(client.exportToXML());
		HttpResponse<String> result = client.send();
// System.out.println(result.body());
		assertTrue(result.statusCode() == 200);
	}

	public void captureObjectEventWithIntegerILMD() throws MalformedURLException, ValidationException, ParseException {
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		EPCISCaptureClient client = new EPCISCaptureClient(url);
		client.addObjectEvent(new ObjectEventBuilder("2020-10-15T20:33:31.116Z", "-09:00", ActionType.OBSERVE)
				.addILMD(new ExtensionBuilder("http://dfpl.org/epcis", "integerValue1", "dfpl", 1))
				.addILMD(new ExtensionBuilder("http://dfpl.org/epcis", "integerValue2", "dfpl", 2))
				.addILMD(new ExtensionBuilder("http://dfpl.org/epcis", "integerValue2", "dfpl", 3)).build());
		System.out.println(client.exportToXML());
		HttpResponse<String> result = client.send();
//System.out.println(result.body());
		assertTrue(result.statusCode() == 200);
	}

	public void captureObjectEventWithBooleanILMD() throws MalformedURLException, ValidationException, ParseException {
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		EPCISCaptureClient client = new EPCISCaptureClient(url);
		client.addObjectEvent(new ObjectEventBuilder("2020-10-15T20:33:31.116Z", "-09:00", ActionType.OBSERVE)
				.addILMD(new ExtensionBuilder("http://dfpl.org/epcis", "booleanValue1", "dfpl", true))
				.addILMD(new ExtensionBuilder("http://dfpl.org/epcis", "booleanValue2", "dfpl", true))
				.addILMD(new ExtensionBuilder("http://dfpl.org/epcis", "booleanValue2", "dfpl", false)).build());
		System.out.println(client.exportToXML());
		HttpResponse<String> result = client.send();
//System.out.println(result.body());
		assertTrue(result.statusCode() == 200);
	}

	public void captureObjectEventWithDateILMD() throws MalformedURLException, ValidationException, ParseException {
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		EPCISCaptureClient client = new EPCISCaptureClient(url);
		client.addObjectEvent(new ObjectEventBuilder("2020-10-15T20:33:31.116Z", "-09:00", ActionType.OBSERVE)
				.addILMD(new ExtensionBuilder("http://dfpl.org/epcis", "dateValue1", "dfpl", new Date()))
				.addILMD(new ExtensionBuilder("http://dfpl.org/epcis", "dateValue2", "dfpl", new Date()))
				.addILMD(new ExtensionBuilder("http://dfpl.org/epcis", "dateValue2", "dfpl", new Date())).build());
		System.out.println(client.exportToXML());
		HttpResponse<String> result = client.send();
		System.out.println(result.body());
		assertTrue(result.statusCode() == 200);
	}

	public void captureObjectEventWithComplexILMD() throws MalformedURLException, ValidationException, ParseException {
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		EPCISCaptureClient client = new EPCISCaptureClient(url);
		client.addObjectEvent(new ObjectEventBuilder("2020-10-15T20:33:31.116Z", "-09:00", ActionType.OBSERVE)
				.addILMD(new ExtensionBuilder("http://dfpl.org/epcis", "objectValue", "dfpl")
						.addInnerExtension("http://dfpl.org/epcis", "dateValue1", "dfpl", new Date())
						.addInnerExtension("http://dfpl.org/epcis", "dateValue1", "dfpl", true))
				.addILMD(new ExtensionBuilder("http://dfpl.org/epcis", "dateValue2", "dfpl", new Date()))
				.addILMD(new ExtensionBuilder("http://dfpl.org/epcis", "dateValue2", "dfpl", new Date())).build());
		System.out.println(client.exportToXML());
		HttpResponse<String> result = client.send();
		System.out.println(result.body());
		assertTrue(result.statusCode() == 200);
	}

	public void captureObjectEventWithErrorDeclaration()
			throws MalformedURLException, ValidationException, ParseException {
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		EPCISCaptureClient client = new EPCISCaptureClient(url);
		client.addObjectEvent(
				new ObjectEventBuilder("2020-10-15T20:33:31.116Z", "-09:00", ActionType.OBSERVE)
						.setErrorDeclaration(new Date(), ErrorReason.did_not_occur,
								List.of("93259689d1a3ad0c4f3eddea45316a589d41a292d68860d88936d489e51e6d95"),
								List.of(new ExtensionBuilder("http://dfpl.org/epcis", "booleanValue1", "dfpl", true)))
						.build());
		System.out.println(client.exportToXML());
		HttpResponse<String> result = client.send();
		System.out.println(result.body());
		assertTrue(result.statusCode() == 200);
	}

	public void captureObjectEventWithUserDefinedEventID()
			throws MalformedURLException, ValidationException, ParseException {
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		EPCISCaptureClient client = new EPCISCaptureClient(url);
		client.addObjectEvent(new ObjectEventBuilder("2020-10-15T20:33:31.116Z", "-09:00", ActionType.OBSERVE)
				.setEventId("user-defined ID").build());
		System.out.println(client.exportToXML());
		HttpResponse<String> result = client.send();
		System.out.println(result.body());
		assertTrue(result.statusCode() == 200);
	}

	public void captureObjectEventWithSensorElement()
			throws MalformedURLException, ValidationException, ParseException, URISyntaxException {
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		EPCISCaptureClient client = new EPCISCaptureClient(url);

		SensorElementBuilder builder = new SensorElementBuilder()
				.setSensorMetadataBizRules("https://example.com/gdti/4012345000054987")
				.setSensorMetadataDataProcessingMethod("https://example.com/gdti/4012345000054987")
				.setSensorMetadataDeviceID("urn:epc:id:giai:4000001.111")
				.setSensorMetadataDeviceMetadata("https://id.gs1.org/giai/4000001111")
				.setSensorMetadataEndTime(new Date()).setSensorMetadataRawData("https://example.org/giai/401234599999")
				.setSensorMetadataStartTime(new Date()).setSensorMetadataTime(new Date())
				.addSensorMetadataOtherAttribute(new QName("http://ext.com/ext1", "someFurtherMetadata", "ext1"),
						"someText")
				.addSensorMetadataOtherAttribute(new QName("http://ext.com/ext2", "someFurtherMetadata2", "ext2"),
						"someText2")

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

		client.addObjectEvent(new ObjectEventBuilder("2020-10-15T20:33:31.116Z", "-09:00", ActionType.OBSERVE)
				.addSensorElement(builder).build());

		System.out.println(client.exportToXML());
		HttpResponse<String> result = client.send();
		System.out.println(result.body());
		assertTrue(result.statusCode() == 200);
	}
	
	@Test
	public void captureObjectEventWithMinimalSensorElement()
			throws MalformedURLException, ValidationException, ParseException, URISyntaxException {
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		EPCISCaptureClient client = new EPCISCaptureClient(url);

		SensorElementBuilder builder = new SensorElementBuilder()
				

				.addSensorReport(new SensorReportBuilder(new Temperature(Type.CEL, 26.0)).build())
				;

		client.addObjectEvent(new ObjectEventBuilder("2020-10-15T20:33:31.116Z", "-09:00", ActionType.OBSERVE)
				.addSensorElement(builder).build());

		System.out.println(client.exportToXML());
		HttpResponse<String> result = client.send();
		System.out.println(result.body());
		assertTrue(result.statusCode() == 200);
	}
}
