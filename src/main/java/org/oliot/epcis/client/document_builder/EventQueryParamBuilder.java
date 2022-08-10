package org.oliot.epcis.client.document_builder;

import java.awt.JobAttributes.DestinationType;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.DatatypeConverter;

import org.oliot.epcis.client.EPCISQueryClient;
import org.oliot.epcis.model.ActionType;
import org.oliot.epcis.model.DestinationListType;
import org.oliot.epcis.model.cbv.BusinessTransactionType;
import org.oliot.epcis.model.cbv.Comparator;
import org.oliot.epcis.model.cbv.EventType;
import org.oliot.epcis.model.cbv.Measurement;
import org.oliot.epcis.model.cbv.SourceDestinationType;
import org.oliot.epcis.unit_converter.unit.Unit;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

@SuppressWarnings("unused")
public class EventQueryParamBuilder extends ParamBuilder {
	private EPCISQueryClient client;

	public EventQueryParamBuilder(EPCISQueryClient client, Document pollEventDocument, Element pollEventsParams) {
		this.client = client;
		this.pollDocument = pollEventDocument;
		this.pollParams = pollEventsParams;
	}

	public EventQueryParamBuilder equalAction(List<ActionType> actionList) {
		addPollParameter("EQ_action", actionList.stream().map(act -> act.toString()).collect(Collectors.toList()));
		return this;
	}

	public EventQueryParamBuilder equalBizLocation(List<String> sglnList, boolean includeDescendants) {
		
		if (includeDescendants) addPollParameter("WD_bizLocation", sglnList);
		else addPollParameter("EQ_bizLocation", sglnList);
		
		return this;
	}

	public EventQueryParamBuilder equalReadPoint(List<String> sglnList, boolean includeDescendants) {
		
		if (includeDescendants) addPollParameter("WD_readPoint", sglnList);
		else addPollParameter("EQ_readPoint", sglnList);
		
		return this;
	}

	public EventQueryParamBuilder equalBizStep(List<String> bizStepList) {
		addPollParameter("EQ_bizStep", bizStepList);
		return this;
	}

	public EventQueryParamBuilder equalBizTransaction(BusinessTransactionType bizTransactionType,
			List<String> bizStepList) {
		addPollParameter("EQ_bizTransaction_urn:epcglobal:cbv:btt:" + bizTransactionType.toString(), bizStepList);
		return this;
	}

	
	public EventQueryParamBuilder equalCorrectiveEventID(List<String> correctiveEventIDList) {
		
		addPollParameter("EQ_correctiveEventID",correctiveEventIDList);
		return this;
	}

	public EventQueryParamBuilder equalDestination(SourceDestinationType destinationType,List<String> destinationList) {
		
		addPollParameter("EQ_destination_rn:epcglobal:cbv:sdt:"+destinationType.toString(),destinationList);
		return this;
	}

	public EventQueryParamBuilder equalDisposition(List<String> dispositionList) {
		
		addPollParameter("EQ_disposition",dispositionList);
		return this;
	}

	public EventQueryParamBuilder equalErrorReason(List<String> errorReasonList) {
		
		addPollParameter("EQ_errorReason",errorReasonList);
		return this;
	}

	public EventQueryParamBuilder equalEventID(List<String> eventIDList) {
		
		addPollParameter("EQ_eventID",eventIDList);
		return this;
	}

	public EventQueryParamBuilder equalSetPersistentDisposition(List<String> setPersistentDisposition) {

		addPollParameter("EQ_setPersistentDisposition",setPersistentDisposition);
		return this;
	}

	public EventQueryParamBuilder equalSource(SourceDestinationType sourceType, List<String> sourceList) {
		addPollParameter("EQ_source_urn:epcglobal:cbv:sdt:"+sourceType.toString(),sourceList);
		return this;
	}

	public EventQueryParamBuilder equalTransformationID(List<String> transformationID) {
		
		addPollParameter("EQ_transformationID",transformationID);
		return this;
	}

	public EventQueryParamBuilder equalUnsetPersistentDisposition(List<String> unsetPersistentDisposition) {
		
		addPollParameter("EQ_unsetPersistentDisposition",unsetPersistentDisposition);
		return this;
	}

	public EventQueryParamBuilder equalEventType(List<String> eventTypeList) {
		
		addPollParameter("eventType",eventTypeList);
		return this;
	}

	public EventQueryParamBuilder existErrorDeclaration() {
		
		addPollParameter("EXISTS_errorDeclaration");
		
		return this;
	}

	public EventQueryParamBuilder compareEventTime(Comparator comp, Date date) {
		
		if (comp.equals(Comparator.GreaterThan)) addPollParameter("GT_eventTime",date);
		else if(comp.equals(Comparator.GreaterThanOrEqualTo)) addPollParameter("GE_eventTime",date);
		else if(comp.equals(Comparator.LessThan)) addPollParameter("LT_eventTime",date);
		else if(comp.equals(Comparator.LessThanOrEqualTo)) addPollParameter("LE_eventTime",date);
		else if(comp.equals(Comparator.Equal))addPollParameter("EQ_eventTime",date);
		else throw new IllegalArgumentException("Comparator should be one of GreaterThanOrEqualTo or LessThan");
		
		return this;
	}
	
	public EventQueryParamBuilder compareErrorDeclarationTime(Comparator comp, Date date) {
		
		if (comp.equals(Comparator.GreaterThan)) addPollParameter("GT_errorDeclarationTime",date);
		else if(comp.equals(Comparator.GreaterThanOrEqualTo)) addPollParameter("GE_errorDeclarationTime",date);
		else if(comp.equals(Comparator.LessThan)) addPollParameter("LT_errorDeclarationTime",date);
		else if(comp.equals(Comparator.LessThanOrEqualTo)) addPollParameter("LE_errorDeclarationTime",date);
		else if(comp.equals(Comparator.Equal))addPollParameter("EQ_errorDeclarationTime",date);
		else throw new IllegalArgumentException("Comparator should be one of GreaterThanOrEqualTo or LessThan");
		
		return this;
	}


	public EventQueryParamBuilder compareRecordTime(Comparator comp, Date date) {
		if (comp.equals(Comparator.GreaterThan)) addPollParameter("GT_recordTime",date);
		else if(comp.equals(Comparator.GreaterThanOrEqualTo)) addPollParameter("GE_recordTime",date);
		else if(comp.equals(Comparator.LessThan)) addPollParameter("LT_recordTime",date);
		else if(comp.equals(Comparator.LessThanOrEqualTo)) addPollParameter("LE_recordTime",date);
		else if(comp.equals(Comparator.Equal))addPollParameter("EQ_recordTime",date);
		else throw new IllegalArgumentException("Comparator should be one of GreaterThanOrEqualTo or LessThan");
		return this;
	}

	public EventQueryParamBuilder matchAnyEPC(List<String> epcList) {
	
		addPollParameter("MATCH_anyEPC",epcList);
		return this;
	}

	public EventQueryParamBuilder matchAnyEPCClass(List<String> epcList) {
	
		addPollParameter("MATCH_anyEPCClass",epcList);
		return this;
	}

	public EventQueryParamBuilder matchEPC(List<String> epcList) {
	
		addPollParameter("MATCH_epc",epcList);
		return this;
	}

	public EventQueryParamBuilder matchEPCClass(List<String> epcList) {
		
		addPollParameter("MATCH_epcClass",epcList);
		return this;
	}

	public EventQueryParamBuilder matchInputEPC(List<String> epcList) {
	
		addPollParameter("MATCH_inputEPC",epcList);
		return this;
	}

	public EventQueryParamBuilder matchInputEPCClass(List<String> epcList) {
		
		addPollParameter("MATCH_inputEPCClass",epcList);
		return this;
	}

	public EventQueryParamBuilder matchOutputEPC(List<String> epcList) {
		
		addPollParameter("MATCH_outputEPC",epcList);
		return this;
	}

	public EventQueryParamBuilder matchOutputEPCClass(List<String> epcList) {
		
		addPollParameter("MATCH_outputEPCClass",epcList);
		return this;
	}

	public EventQueryParamBuilder matchParentID(List<String> epcList) {
	
		addPollParameter("MATCH_parenID",epcList);
		return this;
	}

	public EventQueryParamBuilder sortByEventTime(boolean isAsc) {
		
		addPollParameter("orderBy","eventTime");
		if(isAsc) addPollParameter("orderDirection","ASC");
		else addPollParameter("orderDirection","DESC");
		
		return this;
	}

	public EventQueryParamBuilder sortByRecordTime(boolean isAsc) {
		
		addPollParameter("orderBy","recordTime");
		if(isAsc) addPollParameter("orderDirection","ASC");
		else addPollParameter("orderDirection","DESC");
		
		return this;
	}

	public EventQueryParamBuilder sortByExtension(String namespaceURI, String localName, boolean isAsc) {
		
		addPollParameter("orderBy",namespaceURI+"#"+localName);
		if(isAsc) addPollParameter("orderDirection","ASC");
		else addPollParameter("orderDirection","DESC");
		
		return this;
	}

	public EventQueryParamBuilder eventCountlimit(int limit) {
		
		addPollParameter("eventCountLimit",limit);
		
		return this;
	}

	public EventQueryParamBuilder maxEventCount(int limit) {
		
		addPollParameter("maxElementCount",limit);
		return this;
	}

	
	public EventQueryParamBuilder equalAttribute(String fieldName,String attributeName,List<String> valueList) {
		
		addPollParameter("EQATTR_"+fieldName+"_"+attributeName,valueList);
		
		return this;
	}

	public EventQueryParamBuilder equalErrorDeclarationExtension(String namespaceURI, String localName,
			List<String> valueList) {
		
		addPollParameter("EQ_INNER_ERROR_DECLARATION_"+namespaceURI+"#"+localName,valueList);
		return this;
	}

	public EventQueryParamBuilder equalExtension(String namespaceURI, String localName, List<String> valueList) {
		
		addPollParameter("EQ_"+namespaceURI+"#"+localName,valueList);
		
		
		return this;
	}

	public EventQueryParamBuilder equalILMDExtension(String namespaceURI, String localName, List<String> valueList) {
		
		addPollParameter("EQ_ILMD_"+namespaceURI+"#"+localName,valueList);
		
		return this;
	}

	public EventQueryParamBuilder equalInnerErrorDeclarationExtension(String namespaceURI, String localName,
			List<String> valueList) {
		
		addPollParameter("EQ_INNER_ERROR_DECLARATION_"+namespaceURI+"#"+localName,valueList);
		
		return this;
	}

	public EventQueryParamBuilder equalInnerExtension(String namespaceURI, String localName, List<String> valueList) {
		
		addPollParameter("EQ_INNER_"+namespaceURI+"#"+localName,valueList);
		
		return this;
	}

	public EventQueryParamBuilder equalInnerILMDExtension(String namespaceURI, String localName,
			List<String> valueList) {
		
		addPollParameter("EQ_INNER_ILMD_"+namespaceURI+"#"+localName,valueList);
		
		return this;
	}

	public EventQueryParamBuilder compareErrorDeclarationExtension(Comparator comp, String namespaceURI,String localName, Object value) throws ParseException {
		// GT
		// GE
		// LT
		// LE
		// EQ

		// Int Float Time
		
		if(value instanceof Double) {
			
			
			
			if (comp.equals(Comparator.GreaterThan)) addPollParameter("GT_ERROR_DECLARATION_"+namespaceURI+"#"+localName,(double)value);
			else if(comp.equals(Comparator.GreaterThanOrEqualTo)) addPollParameter("GE_ERROR_DECLARATION_"+namespaceURI+"#"+localName,(double)value);
			else if(comp.equals(Comparator.LessThan)) addPollParameter("LT_ERROR_DECLARATION_"+namespaceURI+"#"+localName,(double)value);
			else if(comp.equals(Comparator.LessThanOrEqualTo)) addPollParameter("LE_ERROR_DECLARATION_"+namespaceURI+"#"+localName,(double)value);
			else if(comp.equals(Comparator.Equal))addPollParameter("EQ_ERROR_DECLARATION_"+namespaceURI+"#"+localName,(double)value);
			else throw new IllegalArgumentException("Comparator should be one of GreaterThanOrEqualTo or LessThan");
			
		}else if(value instanceof Integer) {
			
			
			
			if (comp.equals(Comparator.GreaterThan)) addPollParameter("GT_ERROR_DECLARATION_"+namespaceURI+"#"+localName,(int)value);
			else if(comp.equals(Comparator.GreaterThanOrEqualTo)) addPollParameter("GE_ERROR_DECLARATION_"+namespaceURI+"#"+localName,(int)value);
			else if(comp.equals(Comparator.LessThan)) addPollParameter("LT_ERROR_DECLARATION_"+namespaceURI+"#"+localName,(int)value);
			else if(comp.equals(Comparator.LessThanOrEqualTo)) addPollParameter("LE_ERROR_DECLARATION_"+namespaceURI+"#"+localName,(int)value);
			else if(comp.equals(Comparator.Equal))addPollParameter("EQ_ERROR_DECLARATION_"+namespaceURI+"#"+localName,(int)value);
			else throw new IllegalArgumentException("Comparator should be one of GreaterThanOrEqualTo or LessThan");
			
		}
		else if(value instanceof Date) {
			
		
			
			if (comp.equals(Comparator.GreaterThan)) addPollParameter("GT_ERROR_DECLARATION_"+namespaceURI+"#"+localName,(Date)value);
			else if(comp.equals(Comparator.GreaterThanOrEqualTo)) addPollParameter("GE_ERROR_DECLARATION_"+namespaceURI+"#"+localName,(Date)value);
			else if(comp.equals(Comparator.LessThan)) addPollParameter("LT_ERROR_DECLARATION_"+namespaceURI+"#"+localName,(Date)value);
			else if(comp.equals(Comparator.LessThanOrEqualTo)) addPollParameter("LE_ERROR_DECLARATION_"+namespaceURI+"#"+localName,(Date)value);
			else if(comp.equals(Comparator.Equal))addPollParameter("EQ_ERROR_DECLARATION_"+namespaceURI+"#"+localName,(Date)value);
			else throw new IllegalArgumentException("Comparator should be one of GreaterThanOrEqualTo or LessThan");
		}
		else throw new IllegalArgumentException("Value should be one of Integer,Double,Date");
		
		
		

		// TODO
		return this;
	}

	public EventQueryParamBuilder compareExtension(Comparator comp, String namespaceURI, String localName,Object value) throws ParseException {
		// GT
		// GE
		// LT
		// LE
		// EQ

		// Int Float Time
		
		if(value instanceof Double) {
			
			if (comp.equals(Comparator.GreaterThan)) addPollParameter("GT_"+namespaceURI+"#"+localName,(double)value);
			else if(comp.equals(Comparator.GreaterThanOrEqualTo)) addPollParameter("GE_"+namespaceURI+"#"+localName,(double)value);
			else if(comp.equals(Comparator.LessThan)) addPollParameter("LT_"+namespaceURI+"#"+localName,(double)value);
			else if(comp.equals(Comparator.LessThanOrEqualTo)) addPollParameter("LE_"+namespaceURI+"#"+localName,(double)value);
			else if(comp.equals(Comparator.Equal))addPollParameter("EQ_"+namespaceURI+"#"+localName,(double)value);
			else throw new IllegalArgumentException("Comparator should be one of GreaterThanOrEqualTo or LessThan");
			
		}else if(value instanceof Integer) {
			
			if (comp.equals(Comparator.GreaterThan)) addPollParameter("GT_"+namespaceURI+"#"+localName,(int)value);
			else if(comp.equals(Comparator.GreaterThanOrEqualTo)) addPollParameter("GE_"+namespaceURI+"#"+localName,(int)value);
			else if(comp.equals(Comparator.LessThan)) addPollParameter("LT_"+namespaceURI+"#"+localName,(int)value);
			else if(comp.equals(Comparator.LessThanOrEqualTo)) addPollParameter("LE_"+namespaceURI+"#"+localName,(int)value);
			else if(comp.equals(Comparator.Equal))addPollParameter("EQ_"+namespaceURI+"#"+localName,(int)value);
			else throw new IllegalArgumentException("Comparator should be one of GreaterThanOrEqualTo or LessThan");
		}
		else if(value instanceof Date) {
			
			
			
			if (comp.equals(Comparator.GreaterThan)) addPollParameter("GT_"+namespaceURI+"#"+localName,(Date)value);
			else if(comp.equals(Comparator.GreaterThanOrEqualTo)) addPollParameter("GE_"+namespaceURI+"#"+localName,(Date)value);
			else if(comp.equals(Comparator.LessThan)) addPollParameter("LT_"+namespaceURI+"#"+localName,(Date)value);
			else if(comp.equals(Comparator.LessThanOrEqualTo)) addPollParameter("LE_"+namespaceURI+"#"+localName,(Date)value);
			else if(comp.equals(Comparator.Equal))addPollParameter("EQ_"+namespaceURI+"#"+localName,(Date)value);
			else throw new IllegalArgumentException("Comparator should be one of GreaterThanOrEqualTo or LessThan");
		}
		else throw new IllegalArgumentException("Value should be one of Integer,Double,Date");

		
		return this;
	}

	public EventQueryParamBuilder compareILMDExtension(Comparator comp, String namespaceURI, String localName,Object value) throws ParseException {
		// GT
		// GE
		// LT
		// LE
		// EQ

		// Int Float Time
		
		if(value instanceof Double) {
			
			if (comp.equals(Comparator.GreaterThan)) addPollParameter("GT_ILMD_"+namespaceURI+"#"+localName,(double)value);
			else if(comp.equals(Comparator.GreaterThanOrEqualTo)) addPollParameter("GE_ILMD_"+namespaceURI+"#"+localName,(double)value);
			else if(comp.equals(Comparator.LessThan)) addPollParameter("LT_ILMD_"+namespaceURI+"#"+localName,(double)value);
			else if(comp.equals(Comparator.LessThanOrEqualTo)) addPollParameter("LE_ILMD_"+namespaceURI+"#"+localName,(double)value);
			else if(comp.equals(Comparator.Equal))addPollParameter("EQ_ILMD_"+namespaceURI+"#"+localName,(double)value);
			else throw new IllegalArgumentException("Comparator should be one of GreaterThanOrEqualTo or LessThan");
			
		}else if(value instanceof Integer) {
			
			if (comp.equals(Comparator.GreaterThan)) addPollParameter("GT_ILMD_"+namespaceURI+"#"+localName,(int)value);
			else if(comp.equals(Comparator.GreaterThanOrEqualTo)) addPollParameter("GE_ILMD_"+namespaceURI+"#"+localName,(int)value);
			else if(comp.equals(Comparator.LessThan)) addPollParameter("LT_ILMD_"+namespaceURI+"#"+localName,(int)value);
			else if(comp.equals(Comparator.LessThanOrEqualTo)) addPollParameter("LE_ILMD_"+namespaceURI+"#"+localName,(int)value);
			else if(comp.equals(Comparator.Equal))addPollParameter("EQ_ILMD_"+namespaceURI+"#"+localName,(int)value);
			else throw new IllegalArgumentException("Comparator should be one of GreaterThanOrEqualTo or LessThan");
		}
		else if(value instanceof Date) {
			
			
			
			if (comp.equals(Comparator.GreaterThan)) addPollParameter("GT_ILMD_"+namespaceURI+"#"+localName,(Date)value);
			else if(comp.equals(Comparator.GreaterThanOrEqualTo)) addPollParameter("GE_ILMD_"+namespaceURI+"#"+localName,(Date)value);
			else if(comp.equals(Comparator.LessThan)) addPollParameter("LT_ILMD_"+namespaceURI+"#"+localName,(Date)value);
			else if(comp.equals(Comparator.LessThanOrEqualTo)) addPollParameter("LE_ILMD_"+namespaceURI+"#"+localName,(Date)value);
			else if(comp.equals(Comparator.Equal))addPollParameter("EQ_ILMD_"+namespaceURI+"#"+localName,(Date)value);
			else throw new IllegalArgumentException("Comparator should be one of GreaterThanOrEqualTo or LessThan");
		}
		else throw new IllegalArgumentException("Value should be one of Integer,Double,Date");
		
		
		
		// TODO
		return this;
	}

	public EventQueryParamBuilder compareInnerErrorDeclarationExtension(Comparator comp, String namespaceURI,
			String localName, Object value) throws ParseException {
		// GT
		// GE
		// LT
		// LE
		// EQ

		// Int Float Time
		
		if(value instanceof Double) {
			
			if (comp.equals(Comparator.GreaterThan)) addPollParameter("GT_INNER_ERROR_DECLARATION_"+namespaceURI+"#"+localName,(double)value);
			else if(comp.equals(Comparator.GreaterThanOrEqualTo)) addPollParameter("GE_INNER_ERROR_DECLARATION_"+namespaceURI+"#"+localName,(double)value);
			else if(comp.equals(Comparator.LessThan)) addPollParameter("LT_INNER_ERROR_DECLARATION_"+namespaceURI+"#"+localName,(double)value);
			else if(comp.equals(Comparator.LessThanOrEqualTo)) addPollParameter("LE_INNER_ERROR_DECLARATION_"+namespaceURI+"#"+localName,(double)value);
			else if(comp.equals(Comparator.Equal))addPollParameter("EQ_INNER_ERROR_DECLARATION_"+namespaceURI+"#"+localName,(double)value);
			else throw new IllegalArgumentException("Comparator should be one of GreaterThanOrEqualTo or LessThan");
			
		}else if(value instanceof Integer) {
			
			if (comp.equals(Comparator.GreaterThan)) addPollParameter("GT_INNER_ERROR_DECLARATION_"+namespaceURI+"#"+localName,(int)value);
			else if(comp.equals(Comparator.GreaterThanOrEqualTo)) addPollParameter("GE_INNER_ERROR_DECLARATION_"+namespaceURI+"#"+localName,(int)value);
			else if(comp.equals(Comparator.LessThan)) addPollParameter("LT_INNER_ERROR_DECLARATION_"+namespaceURI+"#"+localName,(int)value);
			else if(comp.equals(Comparator.LessThanOrEqualTo)) addPollParameter("LE_INNER_ERROR_DECLARATION_"+namespaceURI+"#"+localName,(int)value);
			else if(comp.equals(Comparator.Equal))addPollParameter("EQ_INNER_ERROR_DECLARATION_"+namespaceURI+"#"+localName,(int)value);
			else throw new IllegalArgumentException("Comparator should be one of GreaterThanOrEqualTo or LessThan");
			
		}
		else if(value instanceof Date) {
			
			
			
			if (comp.equals(Comparator.GreaterThan)) addPollParameter("GT_INNER_ERROR_DECLARATION_"+namespaceURI+"#"+localName,(Date)value);
			else if(comp.equals(Comparator.GreaterThanOrEqualTo)) addPollParameter("GE_INNER_ERROR_DECLARATION_"+namespaceURI+"#"+localName,(Date)value);
			else if(comp.equals(Comparator.LessThan)) addPollParameter("LT_INNER_ERROR_DECLARATION_"+namespaceURI+"#"+localName,(Date)value);
			else if(comp.equals(Comparator.LessThanOrEqualTo)) addPollParameter("LE_INNER_ERROR_DECLARATION_"+namespaceURI+"#"+localName,(Date)value);
			else if(comp.equals(Comparator.Equal))addPollParameter("EQ_INNER_ERROR_DECLARATION_"+namespaceURI+"#"+localName,(Date)value);
			else throw new IllegalArgumentException("Comparator should be one of GreaterThanOrEqualTo or LessThan");
		}
		else throw new IllegalArgumentException("Value should be one of Integer,Double,Date");
		
		
		
		
		// TODO
		return this;
	}

	public EventQueryParamBuilder compareInnerExtension(Comparator comp, String namespaceURI, String localName,
			Object value) throws ParseException {
		// GT
		// GE
		// LT
		// LE
		// EQ

		// Int Float Time
		
		if(value instanceof Double) {
			
			if (comp.equals(Comparator.GreaterThan)) addPollParameter("GT_INNER_"+namespaceURI+"#"+localName,(double)value);
			else if(comp.equals(Comparator.GreaterThanOrEqualTo)) addPollParameter("GE_INNER_"+namespaceURI+"#"+localName,(double)value);
			else if(comp.equals(Comparator.LessThan)) addPollParameter("LT_INNER_"+namespaceURI+"#"+localName,(double)value);
			else if(comp.equals(Comparator.LessThanOrEqualTo)) addPollParameter("LE_INNER_"+namespaceURI+"#"+localName,(double)value);
			else if(comp.equals(Comparator.Equal))addPollParameter("EQ_INNER_"+namespaceURI+"#"+localName,(double)value);
			else throw new IllegalArgumentException("Comparator should be one of GreaterThanOrEqualTo or LessThan");
			
		}else if(value instanceof Integer) {
			
			if (comp.equals(Comparator.GreaterThan)) addPollParameter("GT_INNER_"+namespaceURI+"#"+localName,(int)value);
			else if(comp.equals(Comparator.GreaterThanOrEqualTo)) addPollParameter("GE_INNER_"+namespaceURI+"#"+localName,(int)value);
			else if(comp.equals(Comparator.LessThan)) addPollParameter("LT_INNER_"+namespaceURI+"#"+localName,(int)value);
			else if(comp.equals(Comparator.LessThanOrEqualTo)) addPollParameter("LE_INNER_"+namespaceURI+"#"+localName,(int)value);
			else if(comp.equals(Comparator.Equal))addPollParameter("EQ_INNER_"+namespaceURI+"#"+localName,(int)value);
			else throw new IllegalArgumentException("Comparator should be one of GreaterThanOrEqualTo or LessThan");
			
		}else if(value instanceof Date) {
			
			
			
			if (comp.equals(Comparator.GreaterThan)) addPollParameter("GT_INNER_"+namespaceURI+"#"+localName,(Date)value);
			else if(comp.equals(Comparator.GreaterThanOrEqualTo)) addPollParameter("GE_INNER_"+namespaceURI+"#"+localName,(Date)value);
			else if(comp.equals(Comparator.LessThan)) addPollParameter("LT_INNER_"+namespaceURI+"#"+localName,(Date)value);
			else if(comp.equals(Comparator.LessThanOrEqualTo)) addPollParameter("LE_INNER_"+namespaceURI+"#"+localName,(Date)value);
			else if(comp.equals(Comparator.Equal))addPollParameter("EQ_INNER_"+namespaceURI+"#"+localName,(Date)value);
			else throw new IllegalArgumentException("Comparator should be one of GreaterThanOrEqualTo or LessThan");
			
		}else throw new IllegalArgumentException("Value should be one of Integer,Double,Date");
		
		 
		
		return this;
	}

	public EventQueryParamBuilder compareInnerILMDExtension(Comparator comp, String namespaceURI, String localName,
			Object value) throws ParseException {
		// GT
		// GE
		// LT
		// LE
		// EQ

		// Int Float Time
		
		if(value instanceof Double) {
			
			if (comp.equals(Comparator.GreaterThan)) addPollParameter("GT_INNER_ILMD_"+namespaceURI+"#"+localName,(double)value);
			else if(comp.equals(Comparator.GreaterThanOrEqualTo)) addPollParameter("GE_INNER_ILMD_"+namespaceURI+"#"+localName,(double)value);
			else if(comp.equals(Comparator.LessThan)) addPollParameter("LT_INNER_ILMD_"+namespaceURI+"#"+localName,(double)value);
			else if(comp.equals(Comparator.LessThanOrEqualTo)) addPollParameter("LE_INNER_ILMD_"+namespaceURI+"#"+localName,(double)value);
			else if(comp.equals(Comparator.Equal))addPollParameter("EQ_INNER_ILMD_"+namespaceURI+"#"+localName,(double)value);
			else throw new IllegalArgumentException("Comparator should be one of GreaterThanOrEqualTo or LessThan");
			
		}else if(value instanceof Integer) {
			
			if (comp.equals(Comparator.GreaterThan)) addPollParameter("GT_INNER_ILMD_"+namespaceURI+"#"+localName,(int)value);
			else if(comp.equals(Comparator.GreaterThanOrEqualTo)) addPollParameter("GE_INNER_ILMD_"+namespaceURI+"#"+localName,(int)value);
			else if(comp.equals(Comparator.LessThan)) addPollParameter("LT_INNER_ILMD_"+namespaceURI+"#"+localName,(int)value);
			else if(comp.equals(Comparator.LessThanOrEqualTo)) addPollParameter("LE_INNER_ILMD_"+namespaceURI+"#"+localName,(int)value);
			else if(comp.equals(Comparator.Equal))addPollParameter("EQ_INNER_ILMD_"+namespaceURI+"#"+localName,(int)value);
			else throw new IllegalArgumentException("Comparator should be one of GreaterThanOrEqualTo or LessThan");
			
		}else if(value instanceof Date) {
			
			
			
			if (comp.equals(Comparator.GreaterThan)) addPollParameter("GT_INNER_ILMD_"+namespaceURI+"#"+localName,(Date)value);
			else if(comp.equals(Comparator.GreaterThanOrEqualTo)) addPollParameter("GE_INNER_ILMD_"+namespaceURI+"#"+localName,(Date)value);
			else if(comp.equals(Comparator.LessThan)) addPollParameter("LT_INNER_ILMD_"+namespaceURI+"#"+localName,(Date)value);
			else if(comp.equals(Comparator.LessThanOrEqualTo)) addPollParameter("LE_INNER_ILMD_"+namespaceURI+"#"+localName,(Date)value);
			else if(comp.equals(Comparator.Equal))addPollParameter("EQ_INNER_ILMD_"+namespaceURI+"#"+localName,(Date)value);
			else throw new IllegalArgumentException("Comparator should be one of GreaterThanOrEqualTo or LessThan");
			
		}else throw new IllegalArgumentException("Value should be one of Integer,Double,Date");
		
		
		
		
		return this;
	}

	
	
	
	// Sensor Specific
	public EventQueryParamBuilder equalSensorBizRules(List<String> bizRuleList) {
		
		addPollParameter("EQ_bizRules",bizRuleList);
		
		return this;
	}

	public EventQueryParamBuilder equalSensorBooleanValue(boolean value) {
		
		
		addPollParameter("EQ_booleanValue",value);
		
		return this;
	}

	public EventQueryParamBuilder equalSensorChemicalSubstance(List<String> chemicalSubstance) {
		
		addPollParameter("EQ_chemicalSubstance",chemicalSubstance);
		
		return this;
	}

	public EventQueryParamBuilder equalSensorDataProcessingMethod(List<String> dataProcessingMethod) {
		
		addPollParameter("EQ_dataProcessingMethod",dataProcessingMethod);
		
		return this;
	}

	public EventQueryParamBuilder equalSensorDeviceID(List<String> deviceID) {
		
		addPollParameter("EQ_deviceID",deviceID);
		
		return this;
	}

	public EventQueryParamBuilder equalSensorDeviceMetaData(List<String> deviceMetaData) {
		
		addPollParameter("EQ_deviceMetaData",deviceMetaData);
		
		return this;
	}

	public EventQueryParamBuilder equalSensorHexBinaryValue(String hexBinaryValue) {
		
		
		addPollParameter("EQ_hexBinaryValue",hexBinaryValue);
		
		// sensorReport.setHexBinaryValue(DatatypeConverter.parseHexBinary(value));
		return this;
	}

	public EventQueryParamBuilder equalSensorHexBinaryValue(byte[] hexBinaryValue) {
		
		addPollParameter("EQ_hexBinaryValue",hexBinaryValue.toString());
		
		return this;
	}

	public EventQueryParamBuilder equalInnerSensorElementExtension(String namespaceURI, String localName,
			List<String> valueList) {
		
		addPollParameter("EQ_INNER_SENSORELEMENT_"+namespaceURI+"#"+localName,valueList);
		
		return this;
	}

	public EventQueryParamBuilder equalSensorMicroorganism(List<String> microorganismList) {
		
		addPollParameter("EQ_microorganism",microorganismList);
		
		return this;
	}

	public EventQueryParamBuilder equalSensorRawData(List<String> rawDataList) {
		
		addPollParameter("EQ_rawData",rawDataList);
		
		return this;
	}

	public EventQueryParamBuilder equalSensorElementExtension(String namespaceURI, String localName,
			List<String> valueList) {
		
		addPollParameter("EQ_SENSORELEMENT_"+namespaceURI+"#"+localName,valueList);
		
		return this;
	}

	public EventQueryParamBuilder equalSensorMetadataAttribute(String namespaceURI, String localName, String value) {
		
		
		addPollParameter("EQ_SENSORMETADATA_"+namespaceURI+"#"+localName,value);
		
		return this;
	}

	
	//update type???
	public EventQueryParamBuilder equalSensorReportAttribute(String namespaceURI, String localName, String value) {
		
		addPollParameter("EQ_SENSORREPORT_"+namespaceURI+"#"+localName,value);
		
		return this;
	}

	public EventQueryParamBuilder equalSensorStringValue(String value) {
		
		
		addPollParameter("EQ_stringValue",value);
		
		return this;
	}

	public EventQueryParamBuilder equalSensorType(List<String> typeList) {
		
		
		addPollParameter("EQ_type",typeList);
		
		return this;
	}

	public EventQueryParamBuilder equalSensorValue(Object unit, double value) {
		
		
		addPollParameter("EQ_value_"+unit.toString(),value);
		
		// unit.toString();
		return this;
	}

	public EventQueryParamBuilder existInnerSensorElementExtension(String namespaceURI, String localName) {
		
		addPollParameter("EXISTS_INNER_SENSORELEMENT_"+namespaceURI+"#"+localName);
		
		return this;
	}

	public EventQueryParamBuilder existSensorElement() {
		
		addPollParameter("EXISTS_sensorElement");
		
		return this;
	}

	public EventQueryParamBuilder existSensorElementExtension(String namespaceURI, String localName) {
		
		addPollParameter("EXISTS_SENSORELEMENT_"+namespaceURI+"#"+localName);
		
		return this;
	}

	public EventQueryParamBuilder existSensorMetadataAttribute(String namespaceURI, String localName) {
		
		addPollParameter("EXISTS_SENSORMETADATA_"+namespaceURI+"#"+localName);
		
		return this;
	}

	public EventQueryParamBuilder existSensorReportAttribute(String namespaceURI, String localName) {
		
		addPollParameter("EXISTS_SENSORREPORT_"+namespaceURI+"#"+localName);
		
		return this;
	}

	public EventQueryParamBuilder compareSensorMaxValue(Comparator comp, float value) {
		// GT
		// GE
		// LT
		// LE
		// EQ
		if (comp.equals(Comparator.GreaterThan)) addPollParameter("GT_maxValue",value);
		else if(comp.equals(Comparator.GreaterThanOrEqualTo)) addPollParameter("GE_maxValue",value);
		else if(comp.equals(Comparator.LessThan)) addPollParameter("LT_maxValue",value);
		else if(comp.equals(Comparator.LessThanOrEqualTo)) addPollParameter("LE_maxValue",value);
		else if(comp.equals(Comparator.Equal))addPollParameter("EQ_maxValue");
		else throw new IllegalArgumentException("Comparator should be one of GreaterThanOrEqualTo or LessThan");
		// TODO
		return this;
	}

	public EventQueryParamBuilder compareSensorPercRank(Comparator comp, float value) {
		// GT
		// GE
		// LT
		// LE
		// EQ
		if (comp.equals(Comparator.GreaterThan)) addPollParameter("GT_percRank",value);
		else if(comp.equals(Comparator.GreaterThanOrEqualTo)) addPollParameter("GE_percRank",value);
		else if(comp.equals(Comparator.LessThan)) addPollParameter("LT_percRank",value);
		else if(comp.equals(Comparator.LessThanOrEqualTo)) addPollParameter("LE_percRank",value);
		else if(comp.equals(Comparator.Equal))addPollParameter("EQ_percRank",value);
		else throw new IllegalArgumentException("Comparator should be one of GreaterThanOrEqualTo or LessThan");
		// TODO
		return this;
	}

	public EventQueryParamBuilder compareSensorPercValue(Comparator comp, float value) {
		// GT
		// GE
		// LT
		// LE
		// EQ
		if (comp.equals(Comparator.GreaterThan)) addPollParameter("GT_percValue",value);
		else if(comp.equals(Comparator.GreaterThanOrEqualTo)) addPollParameter("GE_percValue",value);
		else if(comp.equals(Comparator.LessThan)) addPollParameter("LT_percValue",value);
		else if(comp.equals(Comparator.LessThanOrEqualTo)) addPollParameter("LE_percValue",value);
		else if(comp.equals(Comparator.Equal))addPollParameter("EQ_percValue",value);
		else throw new IllegalArgumentException("Comparator should be one of GreaterThanOrEqualTo or LessThan");
		// TODO
		return this;
	}

	public EventQueryParamBuilder compareSensorStandardDeviation(Comparator comp, float value) {
		// GT
		// GE
		// LT
		// LE
		// EQ
		if (comp.equals(Comparator.GreaterThan)) addPollParameter("GT_sDev",value);
		else if(comp.equals(Comparator.GreaterThanOrEqualTo)) addPollParameter("GE_sDev",value);
		else if(comp.equals(Comparator.LessThan)) addPollParameter("LT_sDev",value);
		else if(comp.equals(Comparator.LessThanOrEqualTo)) addPollParameter("LE_sDev",value);
		else if(comp.equals(Comparator.Equal))addPollParameter("EQ_sDev",value);
		else throw new IllegalArgumentException("Comparator should be one of GreaterThanOrEqualTo or LessThan");
		// TODO
		return this;
	}

	

	public EventQueryParamBuilder compareSensorTime(Comparator comp, Date value) {
		// GT
		// GE
		// LT
		// LE
		// EQ
		if (comp.equals(Comparator.GreaterThan)) addPollParameter("GT_time",value);
		else if(comp.equals(Comparator.GreaterThanOrEqualTo)) addPollParameter("GE_time",value);
		else if(comp.equals(Comparator.LessThan)) addPollParameter("LT_time",value);
		else if(comp.equals(Comparator.LessThanOrEqualTo)) addPollParameter("LE_time",value);
		else if(comp.equals(Comparator.Equal))addPollParameter("EQ_time",value);
		else throw new IllegalArgumentException("Comparator should be one of GreaterThanOrEqualTo or LessThan");
		// TODO
		return this;
	}
	
	public EventQueryParamBuilder compareSensorStartTime(Comparator comp, Date value) throws IllegalArgumentException {
		
		if (comp.equals(Comparator.GreaterThan)) addPollParameter("GT_startTime",value);
		else if(comp.equals(Comparator.GreaterThanOrEqualTo)) addPollParameter("GE_startTime",value);
		else if(comp.equals(Comparator.LessThan)) addPollParameter("LT_startTime",value);
		else if(comp.equals(Comparator.LessThanOrEqualTo)) addPollParameter("LE_startTime",value);
		else if(comp.equals(Comparator.Equal))addPollParameter("EQ_startTime",value);
		else throw new IllegalArgumentException("Comparator should be one of GreaterThanOrEqualTo or LessThan");
		return this;
	}

	public EventQueryParamBuilder compareSensorEndTime(Comparator comp, Date value) {
		// GT
		// GE
		// LT
		// LE
		// EQ
		if (comp.equals(Comparator.GreaterThan)) addPollParameter("GT_endTime",value);
		else if(comp.equals(Comparator.GreaterThanOrEqualTo)) addPollParameter("GE_endTime",value);
		else if(comp.equals(Comparator.LessThan)) addPollParameter("LT_endTime",value);
		else if(comp.equals(Comparator.LessThanOrEqualTo)) addPollParameter("LE_endTime",value);
		else if(comp.equals(Comparator.Equal))addPollParameter("EQ_endTime",value);
		else throw new IllegalArgumentException("Comparator should be one of GreaterThanOrEqualTo or LessThan");
		// TODO
		return this;
	}

	

	

	public EPCISQueryClient build() {
		return client;
	}
}
