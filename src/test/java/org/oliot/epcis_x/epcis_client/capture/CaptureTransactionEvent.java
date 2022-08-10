package org.oliot.epcis_x.epcis_client.capture;

import static org.junit.Assert.assertTrue;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpResponse;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.namespace.QName;

import org.junit.Before;
import org.junit.Test;
import org.oliot.epcis.client.EPCISCaptureClient;
import org.oliot.epcis.client.document_builder.BusinessTransaction;
import org.oliot.epcis.client.document_builder.ExtensionBuilder;
import org.oliot.epcis.client.document_builder.SensorElementBuilder;
import org.oliot.epcis.client.document_builder.SensorReportBuilder;
import org.oliot.epcis.client.document_builder.TransactionEventBuilder;
import org.oliot.epcis.model.ActionType;
import org.oliot.epcis.model.cbv.BusinessStep;
import org.oliot.epcis.model.cbv.BusinessTransactionType;
import org.oliot.epcis.model.cbv.Disposition;
import org.oliot.epcis.model.cbv.SourceDestinationType;
import org.oliot.epcis.model.exception.ValidationException;
import org.oliot.epcis.unit_converter.unit.Temperature;
import org.oliot.epcis.unit_converter.unit.Temperature.Type;

public class CaptureTransactionEvent {

	public URL url = null;

	@Before
	public void initialize() throws MalformedURLException {
		url = new URL("http://dfpl.sejong.ac.kr:8080/epcis/capture");
		// url = new URL("http://localhost:8080/epcis/capture");
	}

	
	
	//minimal test 1
	@Test
	public void captureTestMinimal1() throws ValidationException, ParseException {
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISCaptureClient client = new EPCISCaptureClient(url);
		
		ArrayList<BusinessTransaction> btList = new ArrayList<BusinessTransaction>();
		BusinessTransaction bt_1=new BusinessTransaction(BusinessTransactionType.po,"urn:epc:id:gdti:0614141.00001.1618034");
		BusinessTransaction bt_2=new BusinessTransaction(BusinessTransactionType.pedigree,"urn:epc:id:gsrn:0614141.0000010253");
		btList.add(bt_1);
		btList.add(bt_2);
		
		client.addTransactionEvent(new TransactionEventBuilder("2005-04-03T20:33:31.116-06:00", "-06:00",ActionType.ADD,btList).build());
		
		System.out.println(client.exportToXML());
		
		HttpResponse<String> result = client.send();
		assertTrue(result.statusCode() == 200);
		
	}
	
	//minimal test 2
	public void captureTestMinimal2() throws ValidationException, ParseException {
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISCaptureClient client = new EPCISCaptureClient(url);
		
		ArrayList<BusinessTransaction> btList = new ArrayList<BusinessTransaction>();
		BusinessTransaction bt_1=new BusinessTransaction(BusinessTransactionType.po,"urn:epc:id:gdti:0614141.00001.1618034");
		BusinessTransaction bt_2=new BusinessTransaction(BusinessTransactionType.pedigree,"urn:epc:id:gsrn:0614141.0000010253");
		btList.add(bt_1);
		btList.add(bt_2);
		
		client.addTransactionEvent(new TransactionEventBuilder(System.currentTimeMillis(), "Z",ActionType.ADD,btList).build());
		
		System.out.println(client.exportToXML());
		
		HttpResponse<String> result = client.send();
		assertTrue(result.statusCode() == 200);
	}
	
	//parentID test
	public void captureTestParentID() throws ValidationException, ParseException {
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISCaptureClient client = new EPCISCaptureClient(url);
		
		ArrayList<BusinessTransaction> btList = new ArrayList<BusinessTransaction>();
		BusinessTransaction bt_1=new BusinessTransaction(BusinessTransactionType.po,"urn:epc:id:gdti:0614141.00001.1618034");
		BusinessTransaction bt_2=new BusinessTransaction(BusinessTransactionType.pedigree,"urn:epc:id:gsrn:0614141.0000010253");
		btList.add(bt_1);
		btList.add(bt_2);
		
		client.addTransactionEvent(new TransactionEventBuilder("2005-04-03T20:33:31.116-06:00", "-06:00",ActionType.ADD,btList)
				.setParentId("urn:epc:id:sscc:0614141.1234567890")
				.build());
		
		System.out.println(client.exportToXML());
		
		HttpResponse<String> result = client.send();
		assertTrue(result.statusCode() == 200);
		
		
	}
	
	
	//epcList test
	public void captureTestEPCList() throws ValidationException, ParseException {
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISCaptureClient client = new EPCISCaptureClient(url);
		
		ArrayList<BusinessTransaction> btList = new ArrayList<BusinessTransaction>();
		BusinessTransaction bt_1=new BusinessTransaction(BusinessTransactionType.po,"urn:epc:id:gdti:0614141.00001.1618034");
		BusinessTransaction bt_2=new BusinessTransaction(BusinessTransactionType.pedigree,"urn:epc:id:gsrn:0614141.0000010253");
		btList.add(bt_1);
		btList.add(bt_2);
		
		client.addTransactionEvent(new TransactionEventBuilder("2005-04-03T20:33:31.116-06:00", "-06:00",ActionType.ADD,btList)
				.setEPCList(List.of("urn:epc:id:sgtin:0614141.107346.2017","urn:epc:id:sgtin:0614141.107346.2018"))
				.build());
		
		System.out.println(client.exportToXML());
		
		HttpResponse<String> result = client.send();
		assertTrue(result.statusCode() == 200);
		
		
	}
	
	
	//action test
	public void captureTestAction() throws ValidationException, ParseException {
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISCaptureClient client = new EPCISCaptureClient(url);
		
		ArrayList<BusinessTransaction> btList = new ArrayList<BusinessTransaction>();
		BusinessTransaction bt_1=new BusinessTransaction(BusinessTransactionType.po,"urn:epc:id:gdti:0614141.00001.1618034");
		BusinessTransaction bt_2=new BusinessTransaction(BusinessTransactionType.pedigree,"urn:epc:id:gsrn:0614141.0000010253");
		btList.add(bt_1);
		btList.add(bt_2);
		
		client.addTransactionEvent(new TransactionEventBuilder("2005-04-03T20:33:31.116-06:00", "-06:00",ActionType.ADD,btList)
				.setAction(ActionType.ADD)
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
		
		ArrayList<BusinessTransaction> btList = new ArrayList<BusinessTransaction>();
		BusinessTransaction bt_1=new BusinessTransaction(BusinessTransactionType.po,"urn:epc:id:gdti:0614141.00001.1618034");
		BusinessTransaction bt_2=new BusinessTransaction(BusinessTransactionType.pedigree,"urn:epc:id:gsrn:0614141.0000010253");
		btList.add(bt_1);
		btList.add(bt_2);
		
		client.addTransactionEvent(new TransactionEventBuilder("2005-04-03T20:33:31.116-06:00", "-06:00",ActionType.ADD,btList)
				.setBizStep(BusinessStep.shipping)
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
		
		ArrayList<BusinessTransaction> btList = new ArrayList<BusinessTransaction>();
		BusinessTransaction bt_1=new BusinessTransaction(BusinessTransactionType.po,"urn:epc:id:gdti:0614141.00001.1618034");
		BusinessTransaction bt_2=new BusinessTransaction(BusinessTransactionType.pedigree,"urn:epc:id:gsrn:0614141.0000010253");
		btList.add(bt_1);
		btList.add(bt_2);
		
		client.addTransactionEvent(new TransactionEventBuilder("2005-04-03T20:33:31.116-06:00", "-06:00",ActionType.ADD,btList)
				.setDisposition(Disposition.in_transit)
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
		
		ArrayList<BusinessTransaction> btList = new ArrayList<BusinessTransaction>();
		BusinessTransaction bt_1=new BusinessTransaction(BusinessTransactionType.po,"urn:epc:id:gdti:0614141.00001.1618034");
		BusinessTransaction bt_2=new BusinessTransaction(BusinessTransactionType.pedigree,"urn:epc:id:gsrn:0614141.0000010253");
		btList.add(bt_1);
		btList.add(bt_2);
		
		client.addTransactionEvent(new TransactionEventBuilder("2005-04-03T20:33:31.116-06:00", "-06:00",ActionType.ADD,btList)
				.setReadPoint("urn:epc:id:sgln:0614141.07346.1234")
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
		
		ArrayList<BusinessTransaction> btList = new ArrayList<BusinessTransaction>();
		BusinessTransaction bt_1=new BusinessTransaction(BusinessTransactionType.po,"urn:epc:id:gdti:0614141.00001.1618034");
		BusinessTransaction bt_2=new BusinessTransaction(BusinessTransactionType.pedigree,"urn:epc:id:gsrn:0614141.0000010253");
		btList.add(bt_1);
		btList.add(bt_2);
		
		client.addTransactionEvent(new TransactionEventBuilder("2005-04-03T20:33:31.116-06:00", "-06:00",ActionType.ADD,btList)
				.setBizLocation("urn:epc:id:sgln:0614141.00888.0")
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
		
		ArrayList<BusinessTransaction> btList = new ArrayList<BusinessTransaction>();
		BusinessTransaction bt_1=new BusinessTransaction(BusinessTransactionType.po,"urn:epc:id:gdti:0614141.00001.1618034");
		BusinessTransaction bt_2=new BusinessTransaction(BusinessTransactionType.pedigree,"urn:epc:id:gsrn:0614141.0000010253");
		btList.add(bt_1);
		btList.add(bt_2);
		
		client.addTransactionEvent(new TransactionEventBuilder("2005-04-03T20:33:31.116-06:00", "-06:00",ActionType.ADD,btList)
				.addQauntityElement("urn:epc:class:lgtin:4012345.011111.4444", Double.valueOf(10), "KGM")
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
		
		ArrayList<BusinessTransaction> btList = new ArrayList<BusinessTransaction>();
		BusinessTransaction bt_1=new BusinessTransaction(BusinessTransactionType.po,"urn:epc:id:gdti:0614141.00001.1618034");
		BusinessTransaction bt_2=new BusinessTransaction(BusinessTransactionType.pedigree,"urn:epc:id:gsrn:0614141.0000010253");
		btList.add(bt_1);
		btList.add(bt_2);
		
		client.addTransactionEvent(new TransactionEventBuilder("2005-04-03T20:33:31.116-06:00", "-06:00",ActionType.ADD,btList)
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
		
		ArrayList<BusinessTransaction> btList = new ArrayList<BusinessTransaction>();
		BusinessTransaction bt_1=new BusinessTransaction(BusinessTransactionType.po,"urn:epc:id:gdti:0614141.00001.1618034");
		BusinessTransaction bt_2=new BusinessTransaction(BusinessTransactionType.pedigree,"urn:epc:id:gsrn:0614141.0000010253");
		btList.add(bt_1);
		btList.add(bt_2);
		
		client.addTransactionEvent(new TransactionEventBuilder("2005-04-03T20:33:31.116-06:00", "-06:00",ActionType.ADD,btList)
				.addDestination(SourceDestinationType.location, "urn:epc:id:sgln:0614141.00777.0")
				.addDestination(SourceDestinationType.possessing_party, "urn:epc:id:pgln:0614141.00777")
				.addDestination(SourceDestinationType.owning_party, "urn:epc:id:pgln:0614141.00777")
				.build());
		
		System.out.println(client.exportToXML());
		
		HttpResponse<String> result = client.send();
		assertTrue(result.statusCode() == 200);
		
		
	}
	
	//sensor element test
	public void captureTestSeonsorElement() throws ValidationException, URISyntaxException, ParseException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISCaptureClient client = new EPCISCaptureClient(url);
		
		ArrayList<BusinessTransaction> btList = new ArrayList<BusinessTransaction>();
		BusinessTransaction bt_1=new BusinessTransaction(BusinessTransactionType.po,"urn:epc:id:gdti:0614141.00001.1618034");
		BusinessTransaction bt_2=new BusinessTransaction(BusinessTransactionType.pedigree,"urn:epc:id:gsrn:0614141.0000010253");
		btList.add(bt_1);
		btList.add(bt_2);
		
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
		
		
		client.addTransactionEvent(new TransactionEventBuilder("2005-04-03T20:33:31.116-06:00", "-06:00",ActionType.ADD,btList)
				.addSensorElement(builder).build());
		
		
		
		System.out.println(client.exportToXML());
		
		HttpResponse<String> result = client.send();
		assertTrue(result.statusCode() == 200);
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
