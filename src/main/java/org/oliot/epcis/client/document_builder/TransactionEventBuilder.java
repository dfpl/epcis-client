package org.oliot.epcis.client.document_builder;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.oliot.epcis.model.ActionType;

import org.oliot.epcis.model.BusinessLocationType;
import org.oliot.epcis.model.BusinessTransactionListType;
import org.oliot.epcis.model.BusinessTransactionType;
import org.oliot.epcis.model.DestinationListType;
import org.oliot.epcis.model.EPC;
import org.oliot.epcis.model.EPCListType;
import org.oliot.epcis.model.ErrorDeclarationType;

import org.oliot.epcis.model.QuantityElementType;
import org.oliot.epcis.model.QuantityListType;
import org.oliot.epcis.model.ReadPointType;
import org.oliot.epcis.model.SensorElementListType;
import org.oliot.epcis.model.SensorElementType;
import org.oliot.epcis.model.SourceDestType;
import org.oliot.epcis.model.SourceListType;
import org.oliot.epcis.model.TransactionEventType;
import org.oliot.epcis.model.cbv.BusinessStep;
import org.oliot.epcis.model.cbv.Disposition;
import org.oliot.epcis.model.cbv.ErrorReason;
import org.oliot.epcis.model.cbv.SourceDestinationType;
import org.oliot.epcis.model.exception.ValidationException;
import org.oliot.gcp.core.SimplePureIdentityFilter;
import org.w3c.dom.Element;

/**
 * 
 * Copyright (C) 2020-2021. (DFPL) all rights reserved.
 * <p>
 * Oliot EPCIS X is an open source implementation of Electronic Product Code
 * Information Service (EPCIS) v2.0,
 * <p>
 * Among various modules, epcis-capture-xml acts as a server to receive
 * XML-formatted EPCIS documents to capture events in the documents into an
 * EPCIS repository.
 * <p>
 *
 *@author Jaewook Byun, Ph.D., Assistant Professor, Sejong University,
 *        jwbyun@sejong.ac.kr
 *        <p>
 *        Associate Director, Auto-ID Labs, KAIST, bjw0829@kaist.ac.kr
 *
 *@author Jaehyun Ahn, Master Student, Sejong University,
 *		  jhahn@sejong.ac.kr
 *	
 */

/**
 * 
 * TransactionEvent represents an event in which one or more objects become
 * associated or disassociated with one or more identified business
 * transactions.
 *
 */
public class TransactionEventBuilder {
	private TransactionEventType transactionEvent;

	
	//constructors - string
	public TransactionEventBuilder(String eventTime, String eventTimeZoneOffset, ActionType action,
			List<BusinessTransaction> businessTransactionList) throws ParseException {
		
		this.transactionEvent = new TransactionEventType();
		this.transactionEvent.setEpcList(new EPCListType());
		
		BusinessTransactionListType btlt = new BusinessTransactionListType();
		ArrayList<BusinessTransactionType> btList = new ArrayList<BusinessTransactionType>();
		btlt.setBizTransaction(btList);
		for (BusinessTransaction bt : businessTransactionList) {
			btList.add(new BusinessTransactionType(bt.getType().getBusinessTransactionType(), bt.getValue()));
		}
		this.transactionEvent.setBizTransactionList(btlt);
		
		this.setEventTime(eventTime);
		this.setEventTimeZoneOffset(eventTimeZoneOffset);
		this.setAction(action);
	}

	
	//constructors - long
	public TransactionEventBuilder(long eventTimeInMillis, String eventTimeZoneOffset,ActionType action,List<BusinessTransaction> businessTransactionList) throws ParseException {
		
		this.transactionEvent = new TransactionEventType();
		this.transactionEvent.setEpcList(new EPCListType());
		
		BusinessTransactionListType btlt = new BusinessTransactionListType();
		ArrayList<BusinessTransactionType> btList = new ArrayList<BusinessTransactionType>();
		btlt.setBizTransaction(btList);
		for (BusinessTransaction bt : businessTransactionList) {
			btList.add(new BusinessTransactionType(bt.getType().getBusinessTransactionType(), bt.getValue()));
		}
		this.transactionEvent.setBizTransactionList(btlt);
		
		
		this.setEventTime(eventTimeInMillis);
		this.setEventTimeZoneOffset(eventTimeZoneOffset);
		this.setAction(action);
		
	}

	
	//build
	public TransactionEventType build() {
		return this.transactionEvent;
	}

	
	
	
	//time - date
	public TransactionEventBuilder setEventTime(Date date) {

		try {
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(date);
			XMLGregorianCalendar xmlGreCal = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
			transactionEvent.setEventTime(xmlGreCal);
			return this;
		} catch (DatatypeConfigurationException e) {
			// Never happen or should not happen
			e.printStackTrace();
			return this;
		}
	}

	
	//time - string
	public TransactionEventBuilder setEventTime(String dateValue) throws ParseException {
		try {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
			Date date = format.parse(dateValue);
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(date);
			XMLGregorianCalendar xmlGreCal = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
			transactionEvent.setEventTime(xmlGreCal);
			return this;
		} catch (DatatypeConfigurationException e) {
			// Never happen or should not happen
			e.printStackTrace();
			return this;
		}
	}

	//time - unixEpoch
	public TransactionEventBuilder setEventTime(long unixEpoch) {
		try {
			GregorianCalendar calendar = new GregorianCalendar();
			calendar.setTimeInMillis(unixEpoch);
			XMLGregorianCalendar xmlGreCal = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
			transactionEvent.setEventTime(xmlGreCal);
			return this;
		} catch (DatatypeConfigurationException e) {
			// Never happen or should not happen
			e.printStackTrace();
			return this;
		}
	}

	//time zone - string
	public TransactionEventBuilder setEventTimeZoneOffset(String timeZone) throws ParseException {
		DateFormat format = new SimpleDateFormat("XXX");
		format.parse(timeZone);
		this.transactionEvent.setEventTimeZoneOffset(timeZone);
		return this;
	}

	//set parentID
	public TransactionEventBuilder setParentId(String id) {
		this.transactionEvent.setParentID(id);
		return this;
	}

	
	//add business transaction
	public TransactionEventBuilder addBizTransaction(org.oliot.epcis.model.cbv.BusinessTransactionType type,
			String value) {
		BusinessTransactionListType btlt = this.transactionEvent.getBizTransactionList();
		if (btlt == null)
			btlt = new BusinessTransactionListType();
		List<BusinessTransactionType> bttList = btlt.getBizTransaction();
		if (bttList == null) {
			bttList = new ArrayList<BusinessTransactionType>();
			btlt.setBizTransaction(bttList);
		}
		bttList.add(new BusinessTransactionType(type.getBusinessTransactionType(), value));
		this.transactionEvent.setBizTransactionList(btlt);
		return this;
	}

	//set EPCList
	public TransactionEventBuilder setEPCList(List<String> epcs) throws ValidationException {
		EPCListType epcList = new EPCListType();
		List<EPC> curEPCs = epcList.getEpc();
		for (String epc : epcs) {
			if (!SimplePureIdentityFilter.isPureIdentityInstanceLevelObjectIdentifier(epc)) {
				ValidationException e = new ValidationException();
				e.setStackTrace(new StackTraceElement[0]);
				e.setReason(epc + " should comply with pure identity instance-level object identifier format");
				throw e;
			}
			curEPCs.add(new EPC(epc));
		}
		this.transactionEvent.setEpcList(epcList);
		return this;
	}

	//add EPC
	public TransactionEventBuilder addEPC(String epc) throws ValidationException {
		if (!SimplePureIdentityFilter.isPureIdentityInstanceLevelObjectIdentifier(epc)) {
			ValidationException e = new ValidationException();
			e.setStackTrace(new StackTraceElement[0]);
			e.setReason(epc + " should comply with pure identity instance-level object identifier format");
			throw e;
		}
		EPCListType epcList = this.transactionEvent.getEpcList();
		if (epcList == null) {
			epcList = new EPCListType();
		}
		List<EPC> curEPCs = epcList.getEpc();
		curEPCs.add(new EPC(epc));
		this.transactionEvent.setEpcList(epcList);
		return this;
	}

	
	
	//set action
	public TransactionEventBuilder setAction(ActionType action) {
		this.transactionEvent.setAction(action);
		return this;
	}

	//set business step
	public TransactionEventBuilder setBizStep(BusinessStep bizStep) {
		this.transactionEvent.setBizStep(bizStep.getBusinessStep());
		return this;
	}

	//set disposition
	public TransactionEventBuilder setDisposition(Disposition disposition) {
		this.transactionEvent.setDisposition(disposition.getDisposition());
		return this;
	}

	
	//set read point - string
	public TransactionEventBuilder setReadPoint(String readPoint) throws ValidationException{
		
		if (!SimplePureIdentityFilter.isPureIdentityLocationIdentifier(readPoint)) {
			ValidationException e = new ValidationException();
			e.setStackTrace(new StackTraceElement[0]);
			e.setReason(readPoint + " should comply with pure identity location identifier format");
			throw e;
		}
		this.transactionEvent.setReadPoint(new ReadPointType(readPoint));
		return this;
	}
	
	//set read point - latitude,longitude
	public TransactionEventBuilder setReadPoint(float latitude, float longitude) throws ValidationException {
		if (latitude < -90 || latitude > 90) {
			ValidationException e = new ValidationException();
			e.setStackTrace(new StackTraceElement[0]);
			e.setReason(latitude + " should be in a range (-90,90)");
			throw e;
		}
		if (longitude < -180 || longitude > 180) {
			ValidationException e = new ValidationException();
			e.setStackTrace(new StackTraceElement[0]);
			e.setReason(latitude + " should be in a range (-180,180)");
			throw e;
		}
		this.transactionEvent.setReadPoint(new ReadPointType("geo:" + latitude + "," + longitude));
		return this;
	}
	
	//set business location - string
	public TransactionEventBuilder setBizLocation(String bizLocation) throws ValidationException {
		if (!SimplePureIdentityFilter.isPureIdentityLocationIdentifier(bizLocation)) {
			ValidationException e = new ValidationException();
			e.setStackTrace(new StackTraceElement[0]);
			e.setReason(bizLocation + " should comply with pure identity location identifier format");
			throw e;
		}
		
		this.transactionEvent.setBizLocation(new BusinessLocationType(bizLocation));
		return this;
	}
	
	//set business location - latitude,longitude
	public TransactionEventBuilder setBizLocation(float latitude, float longitude) throws ValidationException {
		
		if (latitude < -90 || latitude > 90) {
			ValidationException e = new ValidationException();
			e.setStackTrace(new StackTraceElement[0]);
			e.setReason(latitude + " should be in a range (-90,90)");
			throw e;
		}
		if (longitude < -180 || longitude > 180) {
			ValidationException e = new ValidationException();
			e.setStackTrace(new StackTraceElement[0]);
			e.setReason(latitude + " should be in a range (-180,180)");
			throw e;
		}
		
		this.transactionEvent.setBizLocation(new BusinessLocationType("geo:" + latitude + "," + longitude));
		
		return this;
		
	}
	
	
	
	//add quantity element
	public TransactionEventBuilder addQauntityElement(String classLevelEPC, Double quantity, String uom)
			throws ValidationException {
		if (!SimplePureIdentityFilter.isPureIdentityClassLevelObjectIdentifier(classLevelEPC)) {
			ValidationException e = new ValidationException();
			e.setStackTrace(new StackTraceElement[0]);
			e.setReason(classLevelEPC + " should comply with pure identity instance-level object identifier format");
			throw e;
		}

		QuantityListType quantityList = this.transactionEvent.getQuantityList();
		if (quantityList == null) {
			quantityList = new QuantityListType();
		}

		List<QuantityElementType> curQuantityList = quantityList.getQuantityElement();
		curQuantityList.add(new QuantityElementType(classLevelEPC, quantity, uom));
		this.transactionEvent.setQuantityList(quantityList);
		return this;
	}
	
	
	
	//add source
	public TransactionEventBuilder addSource(SourceDestinationType type, String value) {
		SourceListType slt = this.transactionEvent.getSourceList();
		if (slt == null)
			slt = new SourceListType();
		List<SourceDestType> sdtList = slt.getSource();
		if (sdtList == null) {
			sdtList = new ArrayList<SourceDestType>();
			slt.setSource(sdtList);
		}
		sdtList.add(new SourceDestType(type.getSourceDestinationType(), value));
		this.transactionEvent.setSourceList(slt);
		return this;
	}
	
	//add destination
	public TransactionEventBuilder addDestination(SourceDestinationType type, String value) {
		DestinationListType dlt = this.transactionEvent.getDestinationList();
		if (dlt == null)
			dlt = new DestinationListType();
		List<SourceDestType> sdtList = dlt.getDestination();
		if (sdtList == null) {
			sdtList = new ArrayList<SourceDestType>();
			dlt.setDestination(sdtList);
		}
		sdtList.add(new SourceDestType(type.getSourceDestinationType(), value));
		this.transactionEvent.setDestinationList(dlt);
		return this;
	}
	
	
	

	

	//add sensor element 
	public TransactionEventBuilder addSensorElement(SensorElementBuilder builder) throws ValidationException {
		SensorElementListType selt = transactionEvent.getSensorElementList();
		if (selt == null) {
			selt = new SensorElementListType();
			transactionEvent.setSensorElementList(selt);
		}
		List<SensorElementType> sensorElementList = selt.getSensorElement();
		sensorElementList.add(builder.build());
		return this;
	}
	
	//add extension
	public TransactionEventBuilder addExtension(ExtensionBuilder extension) {
		List<Object> extensionList = transactionEvent.getAny();
		extensionList.add(extension.build());
		return this;
	}

	//set error declaration
	public TransactionEventBuilder setErrorDeclaration(Date declarationTime, ErrorReason reason,
			List<String> correctiveEventIDs, List<ExtensionBuilder> extensionList) {
		ErrorDeclarationType edt = new ErrorDeclarationType();
		List<Object> edtList = edt.getDeclarationTimeOrReasonOrCorrectiveEventIDs();
		Element t = ExtensionBuilder.getErrorDeclarationTime(declarationTime);
		if (t != null)
			edtList.add(t);
		Element r = ExtensionBuilder.getErrorReason(reason);
		if (r != null)
			edtList.add(r);
		Element c = ExtensionBuilder.getCorrectiveEventIDs(correctiveEventIDs);
		if (c != null)
			edtList.add(c);
		if (extensionList != null) {
			for (ExtensionBuilder ext : extensionList) {
				edtList.add(ext.build());
			}
		}
		transactionEvent.setErrorDeclaration(edt);
		return this;
	}

}
