package org.oliot.epcis_x.epcis_client.query;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.oliot.epcis.client.EPCISQueryClient;
import org.oliot.epcis.client.EPCISQueryClient.EPCISQueryType;
import org.oliot.epcis.model.ActionType;
import org.oliot.epcis.model.cbv.BusinessTransactionType;
import org.oliot.epcis.model.cbv.Comparator;
import org.oliot.epcis.model.cbv.SourceDestinationType;
import org.oliot.epcis.unit_converter.unit.Length;


public class PollEvents {

	public URL url = null;

	@Before
	public void initialize() throws MalformedURLException {
		url = new URL("http://dfpl.sejong.ac.kr:8081/epcis/query");
		// url = new URL("http://localhost:8081/epcis/query");
	}

	
	
	//Poll_event_single_param_basic
	
	//test-all events
	public void pollTestAllEvents() throws IOException, InterruptedException {
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		List<Object> result = client.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
	}

	
	//test-equal action
	public void pollTestEQAction() throws IOException, InterruptedException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		List<Object> result = client.prepareEventQueryParameters().equalAction(List.of(ActionType.ADD)).build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
	}
	
	
	
	//test-equal business location
	public void pollTestEQBizLocation() throws IOException, InterruptedException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		List<Object> result=client.prepareEventQueryParameters()
				.equalBizLocation(List.of("urn:epc:id:sgln:0037000.00729.8202"), false).build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
	}
	
	
	
	
	//test-equal read point
	public void pollEQTestReadPoint() throws IOException, InterruptedException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		List<Object> result=client.prepareEventQueryParameters()
				.equalReadPoint(List.of("urn:epc:id:sgln:0037000.00729.8202"), false).build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
	}
	
	
	
	
	//test-equal business step
	public void pollTestEQBizStep() throws IOException, InterruptedException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		List<Object> result=client.prepareEventQueryParameters()
				.equalBizStep(List.of("urn:epcglobal:cbv:bizstep:receiving")).build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
		
	}
	
	
	//test-equal business transaction
	public void pollTestEQBizTransaction() throws IOException, InterruptedException {
	
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		List<Object> result=client.prepareEventQueryParameters()
				.equalBizTransaction(BusinessTransactionType.po,List.of("urn:epc:id:gdti:0614141.00001.1618034")).build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
		
	}
	
	
	//test-equal event id 
	public void pollTestEQEventID() throws IOException, InterruptedException {
	
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		List<Object> result=client.prepareEventQueryParameters()
				.equalEventID(List.of("5722d7e1deab322596705146")).build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
		
	}
	
	
	//test-equal corrective event id 
	public void pollTestEQCorrectiveEventID() throws IOException, InterruptedException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		List<Object> result=client.prepareEventQueryParameters()
				.equalCorrectiveEventID(List.of("5722d7e1deab322596705146","5722d7e1deab322596705147")).build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
		
	}
	
	
	//test-equal destination
	public void pollTestEQDestination() throws IOException, InterruptedException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		List<Object> result=client.prepareEventQueryParameters()
				.equalDestination(SourceDestinationType.owning_party,List.of("urn:epc:id:sgln:0614141.00001.0")).build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
		
	}
	
	
	//test-equal disposition
	public void pollTestEQDisposition() throws IOException, InterruptedException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		List<Object> result=client.prepareEventQueryParameters()
				.equalDisposition(List.of("urn:epcglobal:cbv:disp:in_progress","urn:epcglobal:cbv:disp:active")).build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
		
	}
	
	
	//test-equal error reason
	public void pollTestEQErrorReason() throws IOException, InterruptedException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		List<Object> result=client.prepareEventQueryParameters()
				.equalErrorReason(List.of("urn:epcglobal:noncbv:error:error1","urn:epcglobal:cbv:error:remove")).build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
		
	}
	
	
	//test-equal set persistent disposition
	public void pollTestEQSetPersistentDisposition() throws IOException, InterruptedException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		List<Object> result=client.prepareEventQueryParameters()
				.equalSetPersistentDisposition(List.of("urn:epcglobal:cbv:disp:completeness_verified")).build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
		
	}
	
	
	
	//test-equal unset persistent disposition
	public void pollTestEQUnSetPersistentDisposition() throws IOException, InterruptedException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		List<Object> result=client.prepareEventQueryParameters()
				.equalUnsetPersistentDisposition(List.of("urn:epcglobal:cbv:disp:completeness_inferred")).build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
		
	}
	
	
	//test-equal source
	public void pollTestEQSource() throws IOException, InterruptedException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		List<Object> result=client.prepareEventQueryParameters()
				.equalSource(SourceDestinationType.location,List.of("urn:epc:id:sgln:4012345.00225.0")).build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
		
	}
	
	
	//test-equal transformation id 
	public void pollTestEQTransformationID() throws IOException, InterruptedException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		List<Object> result=client.prepareEventQueryParameters()
				.equalTransformationID(List.of("TransformationID")).build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
		
	}
	
	
	//test-equal event type
	public void pollTestEQEventType() throws IOException, InterruptedException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		List<Object> result=client.prepareEventQueryParameters()
				.equalEventType(List.of("AggregationEvent")).build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
		
	}
	
	
	//test-exist error declaration
	public void pollTestExistErrorDeclaration() throws IOException, InterruptedException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		List<Object> result=client.prepareEventQueryParameters()
				.existErrorDeclaration().build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
		
	}
	
	
	//test-compare event time
	public void pollTestCompareEventTime() throws IOException, InterruptedException, ParseException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		
		String strDate="2000-06-08T23:58:56.591-09:00";
		SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		
		
		List<Object> result=client.prepareEventQueryParameters()
				.compareEventTime(Comparator.GreaterThanOrEqualTo, dtFormat.parse(strDate)).build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
		
		
		
	}
	
	
	//test-compare error declaration time
	public void pollTestCompareErrorDeclarationTime() throws IOException, InterruptedException, ParseException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		
		String strDate="2000-06-08T23:58:56.591-09:00";
		SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
	
		
		
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		List<Object> result=client.prepareEventQueryParameters()
				.compareErrorDeclarationTime(Comparator.GreaterThanOrEqualTo, dtFormat.parse(strDate)).build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
		
	}
	
	
	//test-compare record time
	public void pollTestCompareRecordTime() throws IOException, InterruptedException, ParseException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		
		String strDate="2000-06-08T23:58:56.591-09:00";
		SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		List<Object> result=client.prepareEventQueryParameters()
				.compareRecordTime(Comparator.GreaterThanOrEqualTo, dtFormat.parse(strDate)).build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
		
	}
	
	
	//test-match any EPC
	public void pollTestMatchAnyEPC() throws IOException, InterruptedException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		List<Object> result=client.prepareEventQueryParameters()
				.matchAnyEPC(List.of("urn:epc:id:sgtin:0614141.*.*")).build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
		
	}
	
	
	//test-match any EPC class
	public void pollTestMatchAnyEPCClass() throws IOException, InterruptedException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		List<Object> result=client.prepareEventQueryParameters()
				.matchAnyEPCClass(List.of("urn:epc:class:lgtin:4012345.012345.998877","urn:epc:class:lgtin:4012345.011111.4444")).build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
		
	}
	
	
	//test-match EPC
	public void pollTestMatchEPC() throws IOException, InterruptedException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		List<Object> result=client.prepareEventQueryParameters()
				.matchEPC(List.of("urn:epc:id:sgtin:0614141.*.*")).build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
		
	}
	
	
	//test-match EPC class
	public void pollTestMatchEPCClass() throws IOException, InterruptedException {
	
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		List<Object> result=client.prepareEventQueryParameters()
				.matchEPCClass(List.of("urn:epc:class:lgtin:4012345.012345.998877")).build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
		
	}
	
	
	//test-match input EPC
	public void pollTestMatchInputEPC() throws IOException, InterruptedException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		List<Object> result=client.prepareEventQueryParameters()
				.matchInputEPC(List.of("urn:epc:id:sgtin:4012345.011122.25","urn:epc:id:sgtin:4000001.065432.99886655")).build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
		
	}
	
	
	//test-match input EPC class
	public void pollTestMatchInputEPCClass() throws IOException, InterruptedException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		List<Object> result=client.prepareEventQueryParameters()
				.matchInputEPCClass(List.of("urn:epc:class:lgtin:4012345.011111.4444")).build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
		
		
	}
	
	//test-match output EPC
	public void pollTestMatchOutputEPC() throws IOException, InterruptedException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		List<Object> result=client.prepareEventQueryParameters()
				.matchOutputEPC(List.of("urn:epc:id:sgtin:4012345.077889.25","urn:epc:id:sgtin:4012345.077889.26")).build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
		
	}
	
	
	//test-match output EPC class
	public void pollTestMatchOutputEPCClass() throws IOException, InterruptedException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		List<Object> result=client.prepareEventQueryParameters()
				.matchOutputEPCClass(List.of("urn:epc:class:lgtin:4012345.011111.4444")).build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
		
	}
	
	
	//test-match parent id 
	public void pollTestMatchParentID() throws IOException, InterruptedException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		List<Object> result=client.prepareEventQueryParameters()
				.matchParentID(List.of("urn:epc:id:sscc:0614141.12342567890","urn:epc:id:sscc:0614141.1234567891")).build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
		
	}
	
	
	
	//Poll_event_sort_limit
	
	//test-sort by event time
	public void pollTestSortByEventTime() throws IOException, InterruptedException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		List<Object> result=client.prepareEventQueryParameters()
				.sortByEventTime(false).build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
		
	}
	
	
	//test-sort by record time
	public void pollTestSortByRecordTime() throws IOException, InterruptedException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		List<Object> result=client.prepareEventQueryParameters()
				.sortByRecordTime(false).build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
		
	}
	
	
	//test-sort by extension
	public void pollTestSortByExtension() throws IOException, InterruptedException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		List<Object> result=client.prepareEventQueryParameters()
				.sortByExtension("http://ns.example.com/epcis", "a", false).build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
		
	}
	
	
	//test-limit
	public void pollTestLimit() throws IOException, InterruptedException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		List<Object> result=client.prepareEventQueryParameters()
				.eventCountlimit(3).build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
		
	}
	
	
	//test-max event count  -> 3개로 넣으면 4개 나옴
	public void pollTestMaxEventCount() throws IOException, InterruptedException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		List<Object> result=client.prepareEventQueryParameters()
				.maxEventCount(3).build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
		
	}
	
	
	//Poll_event_single_param_advanced
	
	//test-equal attribute
	public void pollTestEQAttribute() throws IOException, InterruptedException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		List<Object> result=client.prepareEventQueryParameters()
				.equalAttribute("bizLocation", "http://epcis.example.com/mda/latitude", List.of("+18.0000")).build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
		
	}
	
	
	//test-equal error declaration extension
	public void pollTestEQErrorDeclarationExtension() throws IOException, InterruptedException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		List<Object> result=client.prepareEventQueryParameters()
				.equalErrorDeclarationExtension("http://ext.com/ext1", "string", List.of("string")).build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
		
	}
	
	
	//test-equal extension
	public void pollTestEQExtension() throws IOException, InterruptedException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		List<Object> result=client.prepareEventQueryParameters()
				.equalExtension("http://ext.com/ext1", "string", List.of("string")).build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
		
	}
	
	
	//test-equal ILMD extension
	public void pollTestEQILMDExtension() throws IOException, InterruptedException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		List<Object> result=client.prepareEventQueryParameters()
				.equalILMDExtension("http://ext.com/ext1", "string", List.of("string")).build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
		
		
	}
	
	
	//test-equal inner error declaration extension
	public void pollTestEQInnerErrorDeclarationExtesion() throws IOException, InterruptedException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		List<Object> result=client.prepareEventQueryParameters()
				.equalInnerErrorDeclarationExtension("http://ext.com/ext2", "string", List.of("string2")).build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
		
	}
	
	
	//test-equal inner extension
	public void pollTestEQInnerExtension() throws IOException, InterruptedException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		List<Object> result=client.prepareEventQueryParameters()
				.equalInnerExtension("http://ext.com/ext2", "string", List.of("string2")).build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
		
	}
	

	//test-equal inner ILMD extension
	public void pollTestEQInnerILMDExtension() throws IOException, InterruptedException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		List<Object> result=client.prepareEventQueryParameters()
				.equalInnerILMDExtension("http://ext.com/ext2", "string", List.of("string2")).build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
		
	}
	
	
	//test-compare error declaration extension
	public void pollTestCompareErrorDeclarationExtension() throws IOException, InterruptedException, ParseException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		String strDate="2000-06-08T23:58:56.591-09:00";
		SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		
		List<Object> result=client.prepareEventQueryParameters()
				.compareErrorDeclarationExtension(Comparator.GreaterThan,"http://ext.com/ext1","date",dtFormat.parse(strDate)).build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
		
	}
	
	
	//test-compare extension
	public void pollTestCompareExtension() throws IOException, InterruptedException, ParseException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		List<Object> result=client.prepareEventQueryParameters()
				.compareExtension(Comparator.GreaterThan,"http://ext.com/ext1","int",3).build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
		
	}
	
	
	//test-compare ILMD extension
	public void pollTestCompareILMDExtension() throws IOException, InterruptedException, ParseException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		List<Object> result=client.prepareEventQueryParameters()
				.compareILMDExtension(Comparator.GreaterThan,"http://ext.com/ext1","int",3).build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
		
	}
	
	
	//test-compare inner error declaration extension
	public void pollTestCompareInnerErrorDeclarationExtension() throws IOException, InterruptedException, ParseException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		List<Object> result=client.prepareEventQueryParameters()
				.compareInnerErrorDeclarationExtension(Comparator.GreaterThan,"http://ext.com/ext2","int",3).build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
		
	}
	
	
	//test-compare inner extension
	public void pollTestCompareInnerExtension() throws IOException, InterruptedException, ParseException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		List<Object> result=client.prepareEventQueryParameters()
				.compareInnerExtension(Comparator.GreaterThan,"http://ext.com/ext2","int",3).build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
		
	}
	
	
	//test-compare inner ILMD extension
	public void pollTestCompareInnerILMDExtension() throws IOException, InterruptedException, ParseException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		List<Object> result=client.prepareEventQueryParameters()
				.compareInnerILMDExtension(Comparator.GreaterThan,"http://ext.com/ext2","int",3).build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
		
	}
	
	
	
	
	//test-equal sensor business rules
	public void pollTestEQSensorBizRules() throws IOException, InterruptedException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		List<Object> result=client.prepareEventQueryParameters()
				.equalSensorBizRules(List.of("https://example.com/gdti/4012345000054987")).build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
		
	}
	
	
	//test-equal sensor boolean value
	public void pollTestEQSensorBooleanValue() throws IOException, InterruptedException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		List<Object> result=client.prepareEventQueryParameters()
				.equalSensorBooleanValue(true).build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
		
	}
	
	
	//test-equal sensor chemical substance
	public void pollTestEQSensorChemicalSubstance() throws IOException, InterruptedException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		List<Object> result=client.prepareEventQueryParameters()
				.equalSensorChemicalSubstance(List.of("urn:epcglobal:cbv:inchikey:CZMRCDWAGMRECN-UGDNZRGBSA-N")).build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
		
	}
	
	
	//test-equal sensor data processing method
	public void pollTestEQSensorDataProcessingMethod() throws IOException, InterruptedException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		List<Object> result=client.prepareEventQueryParameters()
				.equalSensorDataProcessingMethod(List.of("https://example.com/gdti/4012345000054987")).build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
		
	}
	
	
	//test-equal sensor device id
	public void pollTestEQSensorDeviceID() throws IOException, InterruptedException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		List<Object> result=client.prepareEventQueryParameters()
				.equalSensorDeviceID(List.of("urn:epc:id:giai:4000001.111")).build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
		
	}
	
	
	//test-equal sensor device meta data
	public void pollTestEQSensorDeviceMetaData() throws IOException, InterruptedException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		List<Object> result=client.prepareEventQueryParameters()
				.equalSensorDeviceMetaData(List.of("https://id.gs1.org/giai/4000001111")).build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
		
	}
	
	
	//test-equal sensor hex binary value 
	public void pollTestEQSensorHexBinaryValue() throws IOException, InterruptedException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		List<Object> result=client.prepareEventQueryParameters()
				.equalSensorHexBinaryValue("8PDw").build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
		
	}
	
	
	//test-equal inner sensor element extension
	public void pollTestEQInnerSensorElementExtension() throws IOException, InterruptedException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		List<Object> result=client.prepareEventQueryParameters()
				.equalSensorElementExtension("http://ext.com/ext2","string",List.of("string2")).build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
		
	}
	
	
	//test-equal sensor micro organism
	public void pollTestEQSensorMicroOrganism() throws IOException, InterruptedException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		List<Object> result=client.prepareEventQueryParameters()
				.equalSensorMicroorganism(List.of("https://www.ncbi.nlm.nih.gov/taxonomy/1126011")).build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
		
	}
	
	
	//test-equal sensor raw data 
	public void pollTestEQSensorRawData() throws IOException, InterruptedException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		List<Object> result=client.prepareEventQueryParameters()
				.equalSensorRawData(List.of("https://example.org/giai/401234599999")).build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
		
		
	}
	
	
	//test-equal sensor element extension
	public void pollTestEQSensorElementExtension() throws IOException, InterruptedException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		List<Object> result=client.prepareEventQueryParameters()
				.equalSensorElementExtension("http://ext.com/ext1","string",List.of("string")).build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
		
	}
	
	
	//test-equal sensor meta data attribute
	public void pollTestEQSensorMetaDataAttribute() throws IOException, InterruptedException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		List<Object> result=client.prepareEventQueryParameters()
				.equalSensorMetadataAttribute("http://ext.com/ext1","someFurtherMetaData","someText").build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
		
	}
	
	
	//test-equal sensor report attribute
	public void pollTestEQSensorReportAttribute() throws IOException, InterruptedException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		List<Object> result=client.prepareEventQueryParameters()
				.equalSensorReportAttribute("http://ext.com/ext1","someFurtherReportData","").build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
		
	}
	
	
	//test-equal sensor string value
	public void pollTestEQSensorStringValue() throws IOException, InterruptedException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		List<Object> result=client.prepareEventQueryParameters()
				.equalSensorStringValue("SomeString").build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
		
	}
	
	
	//test-equal sensor type
	public void pollTestEQSensorType() throws IOException, InterruptedException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		List<Object> result=client.prepareEventQueryParameters()
				.equalSensorType(List.of("gs1:Temperature")).build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
		
	}
	
	
	//test-equal sensor value
	public void pollTestEQSensorValue() throws IOException, InterruptedException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		List<Object> result=client.prepareEventQueryParameters()
				.equalSensorValue(Length.Type.A11,(double)78.8).build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
	
	}
	
	
	//test-exist inner sensor element extension
	public void pollTestExistInnerSensorElementExtension() throws IOException, InterruptedException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		List<Object> result=client.prepareEventQueryParameters()
				.existInnerSensorElementExtension("http://ext.com/ext2","float").build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
		
	}
	

	//test-exist sensor element
	public void pollTestExistSensorElement() throws IOException, InterruptedException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		
		List<Object> result=client.prepareEventQueryParameters()
				.existSensorElement().build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
	}
	
	
	//test-exist sensor element extension
	public void pollTestExistSensorElementExtension() throws IOException, InterruptedException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		List<Object> result=client.prepareEventQueryParameters()
				.existSensorElementExtension("http://ext.com/ext1","time").build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
		
	}
	
	
	//test-exist sensor meta data attribute
	public void pollTestExistSensorMetaDataAttribute() throws IOException, InterruptedException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		List<Object> result=client.prepareEventQueryParameters()
				.existSensorMetadataAttribute("http://ext.com/ext1","someFurtherMetaData").build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
		
	}
	
	
	//test-exist sensor report attribute
	public void pollTestExistSensorReportAttribute() throws IOException, InterruptedException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		List<Object> result=client.prepareEventQueryParameters()
				.existSensorReportAttribute("http://ext.com/ext1","someFurtherReportData").build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
		
	}
	
	
	//test-compare sensor max value
	public void pollTestCompareSensorMaxValue() throws IOException, InterruptedException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		List<Object> result=client.prepareEventQueryParameters()
				.compareSensorMaxValue(Comparator.GreaterThanOrEqualTo,(float) 0).build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
		
	}
	
	
	//test-compare sensor perc rank
	public void pollTestCompareSensorPercRank() throws IOException, InterruptedException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		List<Object> result=client.prepareEventQueryParameters()
				.compareSensorPercRank(Comparator.GreaterThanOrEqualTo,(float) 10000000000000000000000.0).build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
		
	}
	
	
	//test-compare sensor perc value
	public void pollTestCompareSensorPercValue() throws IOException, InterruptedException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		List<Object> result=client.prepareEventQueryParameters()
				.compareSensorPercValue(Comparator.GreaterThanOrEqualTo,(float) 10000000000.0).build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
		
	}
	

	//test-compare sensor standard deviation
	public void pollTestCompareSensorStandardDeviation() throws IOException, InterruptedException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		List<Object> result=client.prepareEventQueryParameters()
				.compareSensorStandardDeviation(Comparator.GreaterThanOrEqualTo,(float) 10000000000.0).build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
		
	}
	
	
	//test-compare sensor time
	public void pollTestCompareSensorTime() throws IOException, InterruptedException, ParseException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		
		
		String dataStr="2000-06-08T23:58:56.591-09:00";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		java.util.Date date = formatter.parse(dataStr);
		
		
		
		List<Object> result=client.prepareEventQueryParameters()
				.compareSensorTime(Comparator.GreaterThanOrEqualTo,date).build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
		
	}
	
	
	//test-compare sensor start time
	public void pollTestCompareSensorStartTime() throws IOException, InterruptedException, ParseException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		String dataStr="2000-06-08T23:58:56.591-09:00";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		java.util.Date date = formatter.parse(dataStr);
		
		List<Object> result=client.prepareEventQueryParameters()
				.compareSensorStartTime(Comparator.GreaterThanOrEqualTo,date).build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
		
	}
	
	@Test
	//test-compare sensor end time
	public void pollTestCompareSensorEndTime() throws IOException, InterruptedException, ParseException {
		
		System.out.println("--" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "--");
		
		EPCISQueryClient client = new EPCISQueryClient(url);
		
		String dataStr="2000-06-08T23:58:56.591-09:00";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		java.util.Date date = formatter.parse(dataStr);
		
		List<Object> result=client.prepareEventQueryParameters()
				.compareSensorEndTime(Comparator.GreaterThanOrEqualTo,date).build()
				.showHTTPBody(EPCISQueryType.PollEvents).pollEvents();
		
		System.out.println(result.size());
		
		
	}
	
	
	
	
	
	
	
	
	
	
}
