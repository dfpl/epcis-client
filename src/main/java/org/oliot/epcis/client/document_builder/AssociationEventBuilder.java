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

import org.oliot.epcis.model.AssociationEventType;
import org.oliot.epcis.model.BusinessLocationType;
import org.oliot.epcis.model.BusinessTransactionListType;
import org.oliot.epcis.model.BusinessTransactionType;
import org.oliot.epcis.model.DestinationListType;
import org.oliot.epcis.model.EPC;
import org.oliot.epcis.model.EPCListType;

import org.oliot.epcis.model.QuantityElementType;
import org.oliot.epcis.model.QuantityListType;
import org.oliot.epcis.model.ReadPointType;
import org.oliot.epcis.model.SensorElementListType;
import org.oliot.epcis.model.SensorElementType;
import org.oliot.epcis.model.SourceDestType;
import org.oliot.epcis.model.SourceListType;

import org.oliot.epcis.model.cbv.BusinessStep;
import org.oliot.epcis.model.cbv.Disposition;

import org.oliot.epcis.model.cbv.SourceDestinationType;
import org.oliot.epcis.model.exception.ValidationException;
import org.oliot.gcp.core.SimplePureIdentityFilter;


public class AssociationEventBuilder {
	private AssociationEventType associationEvent;

	//constructors - string
	public AssociationEventBuilder(String eventTime, String eventTimeZoneOffset, ActionType action) throws ParseException {
		
		this.associationEvent=new AssociationEventType();
		
		this.setEventTime(eventTime);
		this.setEventTimeZoneOffset(eventTimeZoneOffset);
		
		
		this.setAction(action);
		
		
	}
	
	
	//constructors - long
	public AssociationEventBuilder(long eventTimeInMillis, String eventTimeZoneOffset,ActionType action) throws ParseException {
		this.associationEvent = new AssociationEventType();
		
		this.setEventTime(eventTimeInMillis);
		this.setEventTimeZoneOffset(eventTimeZoneOffset);
		
	}
	
	//build
	public AssociationEventType build() {
		return this.associationEvent;
	}
	
	
	//time - date
	public AssociationEventBuilder setEventTime(Date date) {

		try {
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(date);
			XMLGregorianCalendar xmlGreCal = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
			associationEvent.setEventTime(xmlGreCal);
			return this;
		} catch (DatatypeConfigurationException e) {
			// Never happen or should not happen
			e.printStackTrace();
			return this;
		}
	}
	
	//time - string
	public AssociationEventBuilder setEventTime(String dateValue) throws ParseException {
		try {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
			Date date = format.parse(dateValue);
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(date);
			XMLGregorianCalendar xmlGreCal = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
			associationEvent.setEventTime(xmlGreCal);
			return this;
		} catch (DatatypeConfigurationException e) {
			// Never happen or should not happen
			e.printStackTrace();
			return this;
		}
	}

	//time - unixEpoch
	public AssociationEventBuilder setEventTime(long unixEpoch) {
		try {
			GregorianCalendar calendar = new GregorianCalendar();
			calendar.setTimeInMillis(unixEpoch);
			XMLGregorianCalendar xmlGreCal = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
			associationEvent.setEventTime(xmlGreCal);
			return this;
		} catch (DatatypeConfigurationException e) {
			// Never happen or should not happen
			e.printStackTrace();
			return this;
		}
	}

	//time zone - string
	public AssociationEventBuilder setEventTimeZoneOffset(String timeZone) throws ParseException {
		DateFormat format = new SimpleDateFormat("XXX");
		format.parse(timeZone);
		this.associationEvent.setEventTimeZoneOffset(timeZone);
		return this;
	}
	
	//set parentID
	public AssociationEventBuilder setParentId(String id) {
		this.associationEvent.setParentID(id);
		return this;
	}

	
	//set childEPCs
	public AssociationEventBuilder setChildEPCs(List<String> epcs) throws ValidationException {
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
		this.associationEvent.setChildEPCs(epcList);
		return this;
	}
	

	
	//add childEPC
	public AssociationEventBuilder addChildEPC(String epc) throws ValidationException {
		if (!SimplePureIdentityFilter.isPureIdentityInstanceLevelObjectIdentifier(epc)) {
			ValidationException e = new ValidationException();
			e.setStackTrace(new StackTraceElement[0]);
			e.setReason(epc + " should comply with pure identity instance-level object identifier format");
			throw e;
		}
		EPCListType epcList = this.associationEvent.getChildEPCs();
		if (epcList == null) {
			epcList = new EPCListType();
		}
		List<EPC> curEPCs = epcList.getEpc();
		curEPCs.add(new EPC(epc));
		this.associationEvent.setChildEPCs(epcList);
		return this;
	}
	
	//add childQuantityElement
	public AssociationEventBuilder addChildQuantityElement(String classLevelEPC, Double quantity, String uom) throws ValidationException {
		if (!SimplePureIdentityFilter.isPureIdentityClassLevelObjectIdentifier(classLevelEPC)) {
			ValidationException e = new ValidationException();
			e.setStackTrace(new StackTraceElement[0]);
			e.setReason(classLevelEPC + " should comply with pure identity instance-level object identifier format");
			throw e;
		}

		QuantityListType epcList = this.associationEvent.getChildQuantityList();
		if (epcList == null) {
			epcList = new QuantityListType();
		}

		List<QuantityElementType> curQuantityList = epcList.getQuantityElement();
		curQuantityList.add(new QuantityElementType(classLevelEPC, quantity, uom));
		this.associationEvent.setChildQuantityList(epcList);
		return this;
	}
	
	
	//set action
	public AssociationEventBuilder setAction(ActionType action) {
		this.associationEvent.setAction(action);

		return this;
	}
	
	
	//set business step
	public AssociationEventBuilder setBizStep(BusinessStep bizStep) {
		this.associationEvent.setBizStep(bizStep.getBusinessStep());
		return this;
	}
	
	//set disposition
	public AssociationEventBuilder setDisposition(Disposition disposition) {
		this.associationEvent.setDisposition(disposition.getDisposition());
		return this;
	}
	
	//set read point - string
	public AssociationEventBuilder setReadPoint(String readPoint) throws ValidationException{
		
		if (!SimplePureIdentityFilter.isPureIdentityLocationIdentifier(readPoint)) {
			ValidationException e = new ValidationException();
			e.setStackTrace(new StackTraceElement[0]);
			e.setReason(readPoint + " should comply with pure identity location identifier format");
			throw e;
		}
		this.associationEvent.setReadPoint(new ReadPointType(readPoint));
		return this;
	}
	
	//set read point - latitude,longitude
	public AssociationEventBuilder setReadPoint(float latitude, float longitude) throws ValidationException {
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
		
		this.associationEvent.setReadPoint(new ReadPointType("geo:" + latitude + "," + longitude));
		return this;
	}
	
	//set business location - string
	public AssociationEventBuilder setBizLocation(String bizLocation) throws ValidationException {
		if (!SimplePureIdentityFilter.isPureIdentityLocationIdentifier(bizLocation)) {
			ValidationException e = new ValidationException();
			e.setStackTrace(new StackTraceElement[0]);
			e.setReason(bizLocation + " should comply with pure identity location identifier format");
			throw e;
		}
		
		this.associationEvent.setBizLocation(new BusinessLocationType(bizLocation));
		return this;
	}
	
	//set business location - latitude,longitude
	public AssociationEventBuilder setBizLocation(float latitude, float longitude) throws ValidationException {
		
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
		
		this.associationEvent.setBizLocation(new BusinessLocationType("geo:" + latitude + "," + longitude));
		
		return this;
		
	}
	
	//add business transaction
	public AssociationEventBuilder addBizTransaction(org.oliot.epcis.model.cbv.BusinessTransactionType type,String value) {
		
		BusinessTransactionListType btlt = this.associationEvent.getBizTransactionList();
		if (btlt == null)
			btlt = new BusinessTransactionListType();
		List<BusinessTransactionType> bttList = btlt.getBizTransaction();
		if (bttList == null) {
			bttList = new ArrayList<BusinessTransactionType>();
			btlt.setBizTransaction(bttList);
		}
		bttList.add(new BusinessTransactionType(type.getBusinessTransactionType(), value));
		
		this.associationEvent.setBizTransactionList(btlt);
		
		return this;
		
	}
	
	//add source
	public AssociationEventBuilder addSource(SourceDestinationType type, String value) {
		
		SourceListType slt=this.associationEvent.getSourceList();
		if (slt == null)
			slt = new SourceListType();
		List<SourceDestType> sdtList = slt.getSource();
		if (sdtList == null) {
			sdtList = new ArrayList<SourceDestType>();
			slt.setSource(sdtList);
		}
		sdtList.add(new SourceDestType(type.getSourceDestinationType(), value));
		
		this.associationEvent.setSourceList(slt);
		return this;
	}
	
	
	//add destination
	public AssociationEventBuilder addDestination(SourceDestinationType type, String value) {
	
		DestinationListType dlt=this.associationEvent.getDestinationList();
		
		if (dlt == null)
			dlt = new DestinationListType();
		List<SourceDestType> sdtList = dlt.getDestination();
		if (sdtList == null) {
			sdtList = new ArrayList<SourceDestType>();
			dlt.setDestination(sdtList);
		}
		sdtList.add(new SourceDestType(type.getSourceDestinationType(), value));
		
		this.associationEvent.setDestinationList(dlt);
		
		return this;
	}
	
	//add sensor element
	public AssociationEventBuilder addSensorElement(SensorElementBuilder builder) throws ValidationException {
		
		SensorElementListType selt = this.associationEvent.getSensorElementList();
		if (selt == null) {
			selt = new SensorElementListType();
			this.associationEvent.setSensorElementList(selt);
		}
		List<SensorElementType> sensorElementList = selt.getSensorElement();
		sensorElementList.add(builder.build());
		return this;
	}
	
	
	
	
	
	
	
	
	
	
	
}
