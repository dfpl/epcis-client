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
import org.oliot.epcis.model.ILMDType;
import org.oliot.epcis.model.ObjectEventType;
import org.oliot.epcis.model.PersistentDispositionType;
import org.oliot.epcis.model.QuantityElementType;
import org.oliot.epcis.model.QuantityListType;
import org.oliot.epcis.model.ReadPointType;
import org.oliot.epcis.model.SensorElementListType;
import org.oliot.epcis.model.SensorElementType;
import org.oliot.epcis.model.SourceDestType;
import org.oliot.epcis.model.SourceListType;
import org.oliot.epcis.model.cbv.BusinessStep;
import org.oliot.epcis.model.cbv.Disposition;
import org.oliot.epcis.model.cbv.ErrorReason;
import org.oliot.epcis.model.cbv.SourceDestinationType;
import org.oliot.epcis.model.exception.ValidationException;
import org.oliot.gcp.core.SimplePureIdentityFilter;
import org.w3c.dom.Element;

/**
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
 * @author Jaewook Byun, Ph.D., Assistant Professor, Sejong University,
 *         jwbyun@sejong.ac.kr
 *         <p>
 *         Associate Director, Auto-ID Labs, KAIST, bjw0829@kaist.ac.kr
 */
public class ObjectEventBuilder {

	private ObjectEventType objectEvent;

	/**
	 * require a constructor supporting minimal fields
	 * 
	 * @throws ParseException
	 * @throws DatatypeConfigurationException
	 */
	// constructors - string
	
	public ObjectEventBuilder(String eventTime, String eventTimeZoneOffset, ActionType action) throws ParseException {
		this.objectEvent = new ObjectEventType();
		this.objectEvent.setEpcList(new EPCListType());

		this.setEventTime(eventTime);
		this.setEventTimeZoneOffset(eventTimeZoneOffset);

		this.setAction(action);
	}

	// constructors - long
	public ObjectEventBuilder(long eventTimeInMillis, String eventTimeZoneOffset, ActionType action)
			throws ParseException {
		this.objectEvent = new ObjectEventType();
		this.objectEvent.setEpcList(new EPCListType());

		this.setEventTime(eventTimeInMillis);
		this.setEventTimeZoneOffset(eventTimeZoneOffset);

		this.setAction(action);
	}

	// build
	public ObjectEventType build() {
		return this.objectEvent;
	}

	// time - date
	public ObjectEventBuilder setEventTime(Date date) {
		try {
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(date);
			XMLGregorianCalendar xmlGreCal = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
			objectEvent.setEventTime(xmlGreCal);
			return this;
		} catch (DatatypeConfigurationException e) {
			// Never happen or should not happen
			e.printStackTrace();
			return this;
		}
	}

	// time - string
	public ObjectEventBuilder setEventTime(String dateValue) throws ParseException {
		try {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
			Date date = format.parse(dateValue);
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(date);
			XMLGregorianCalendar xmlGreCal = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
			objectEvent.setEventTime(xmlGreCal);
			return this;
		} catch (DatatypeConfigurationException e) {
			// Never happen or should not happen
			e.printStackTrace();
			return this;
		}
	}

	// time - unixEpoch
	public ObjectEventBuilder setEventTime(long unixEpoch) {
		try {
			GregorianCalendar calendar = new GregorianCalendar();
			calendar.setTimeInMillis(unixEpoch);
			XMLGregorianCalendar xmlGreCal = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
			objectEvent.setEventTime(xmlGreCal);
			return this;
		} catch (DatatypeConfigurationException e) {
			// Never happen or should not happen
			e.printStackTrace();
			return this;
		}
	}

	// time zone - string
	public ObjectEventBuilder setEventTimeZoneOffset(String timeZone) throws ParseException {
		DateFormat format = new SimpleDateFormat("XXX");
		format.parse(timeZone);
		this.objectEvent.setEventTimeZoneOffset(timeZone);
		return this;
	}

	// set EPCList
	public ObjectEventBuilder setEPCList(List<String> epcs) throws ValidationException {
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
		this.objectEvent.setEpcList(epcList);
		return this;
	}

	// add EPC
	public ObjectEventBuilder addEPC(String epc) throws ValidationException {
		if (!SimplePureIdentityFilter.isPureIdentityInstanceLevelObjectIdentifier(epc)) {
			ValidationException e = new ValidationException();
			e.setStackTrace(new StackTraceElement[0]);
			e.setReason(epc + " should comply with pure identity instance-level object identifier format");
			throw e;
		}
		EPCListType epcList = this.objectEvent.getEpcList();
		if (epcList == null) {
			epcList = new EPCListType();
		}
		List<EPC> curEPCs = epcList.getEpc();
		curEPCs.add(new EPC(epc));
		this.objectEvent.setEpcList(epcList);
		return this;
	}

	// set action
	public ObjectEventBuilder setAction(ActionType action) {
		this.objectEvent.setAction(action);
		return this;
	}

	// set business step
	public ObjectEventBuilder setBizStep(BusinessStep bizStep) {
		this.objectEvent.setBizStep(bizStep.getBusinessStep());
		return this;
	}

	// set disposition
	public ObjectEventBuilder setDisposition(Disposition disposition) {
		this.objectEvent.setDisposition(disposition.getDisposition());
		return this;
	}

	// set read point - string
	public ObjectEventBuilder setReadPoint(String readPoint) throws ValidationException {
		if (!SimplePureIdentityFilter.isPureIdentityLocationIdentifier(readPoint)) {
			ValidationException e = new ValidationException();
			e.setStackTrace(new StackTraceElement[0]);
			e.setReason(readPoint + " should comply with pure identity location identifier format");
			throw e;
		}
		this.objectEvent.setReadPoint(new ReadPointType(readPoint));
		return this;
	}

	// set read point - latitude,longitude
	public ObjectEventBuilder setReadPoint(float latitude, float longitude) throws ValidationException {
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
		this.objectEvent.setReadPoint(new ReadPointType("geo:" + latitude + "," + longitude));
		return this;
	}

	// set business location - string
	public ObjectEventBuilder setBizLocation(String bizLocation) throws ValidationException {
		if (!SimplePureIdentityFilter.isPureIdentityLocationIdentifier(bizLocation)) {
			ValidationException e = new ValidationException();
			e.setStackTrace(new StackTraceElement[0]);
			e.setReason(bizLocation + " should comply with pure identity location identifier format");
			throw e;
		}
		this.objectEvent.setBizLocation(new BusinessLocationType(bizLocation));
		return this;
	}

	// set business location - latitude,longitude
	public ObjectEventBuilder setBizLocation(float latitude, float longitude) throws ValidationException {
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
		this.objectEvent.setBizLocation(new BusinessLocationType("geo:" + latitude + "," + longitude));
		return this;
	}

	// add business transaction
	public ObjectEventBuilder addBizTransaction(org.oliot.epcis.model.cbv.BusinessTransactionType type, String value) {
		BusinessTransactionListType btlt = this.objectEvent.getBizTransactionList();
		if (btlt == null)
			btlt = new BusinessTransactionListType();
		List<BusinessTransactionType> bttList = btlt.getBizTransaction();
		if (bttList == null) {
			bttList = new ArrayList<BusinessTransactionType>();
			btlt.setBizTransaction(bttList);
		}
		bttList.add(new BusinessTransactionType(type.getBusinessTransactionType(), value));
		this.objectEvent.setBizTransactionList(btlt);
		return this;
	}

	// add quantity list
	public ObjectEventBuilder addQuantityElement(String classLevelEPC, Double quantity, String uom)
			throws ValidationException {
		if (!SimplePureIdentityFilter.isPureIdentityClassLevelObjectIdentifier(classLevelEPC)) {
			ValidationException e = new ValidationException();
			e.setStackTrace(new StackTraceElement[0]);
			e.setReason(classLevelEPC + " should comply with pure identity instance-level object identifier format");
			throw e;
		}

		QuantityListType epcList = this.objectEvent.getQuantityList();
		if (epcList == null) {
			epcList = new QuantityListType();
		}

		List<QuantityElementType> curQuantityList = epcList.getQuantityElement();
		curQuantityList.add(new QuantityElementType(classLevelEPC, quantity, uom));
		this.objectEvent.setQuantityList(epcList);
		return this;
	}

	// add source
	public ObjectEventBuilder addSource(SourceDestinationType type, String value) {
		SourceListType slt = this.objectEvent.getSourceList();
		if (slt == null)
			slt = new SourceListType();
		List<SourceDestType> sdtList = slt.getSource();
		if (sdtList == null) {
			sdtList = new ArrayList<SourceDestType>();
			slt.setSource(sdtList);
		}
		sdtList.add(new SourceDestType(type.getSourceDestinationType(), value));
		this.objectEvent.setSourceList(slt);
		return this;
	}

	// add destination
	public ObjectEventBuilder addDestination(SourceDestinationType type, String value) {
		DestinationListType dlt = this.objectEvent.getDestinationList();
		if (dlt == null)
			dlt = new DestinationListType();
		List<SourceDestType> sdtList = dlt.getDestination();
		if (sdtList == null) {
			sdtList = new ArrayList<SourceDestType>();
			dlt.setDestination(sdtList);
		}
		sdtList.add(new SourceDestType(type.getSourceDestinationType(), value));
		this.objectEvent.setDestinationList(dlt);
		return this;
	}

	// add sensor element
	public ObjectEventBuilder addSensorElement(SensorElementBuilder builder) throws ValidationException {
		SensorElementListType selt = objectEvent.getSensorElementList();
		if (selt == null) {
			selt = new SensorElementListType();
			objectEvent.setSensorElementList(selt);
		}
		List<SensorElementType> sensorElementList = selt.getSensorElement();
		sensorElementList.add(builder.build());
		return this;
	}

	// add unset persistent disposition
	public ObjectEventBuilder addUnsetPersistentDisposition(Disposition disposition) {
		PersistentDispositionType pdt = this.objectEvent.getPersistentDisposition();
		if (pdt == null) {
			pdt = new PersistentDispositionType();
			this.objectEvent.setPersistentDisposition(pdt);
		}
		List<String> unsetList = pdt.getUnset();
		if (unsetList == null) {
			unsetList = new ArrayList<String>();
			pdt.setUnset(unsetList);
		}
		unsetList.add(disposition.getDisposition());
		return this;
	}

	// add set persistent disposition
	public ObjectEventBuilder addSetPersistentDisposition(Disposition disposition) {
		PersistentDispositionType pdt = this.objectEvent.getPersistentDisposition();
		if (pdt == null) {
			pdt = new PersistentDispositionType();
			this.objectEvent.setPersistentDisposition(pdt);
		}
		List<String> setList = pdt.getSet();
		if (setList == null) {
			setList = new ArrayList<String>();
			pdt.setSet(setList);
		}
		setList.add(disposition.getDisposition());
		return this;
	}

	// add extension
	public ObjectEventBuilder addExtension(ExtensionBuilder extension) {
		List<Object> extensionList = objectEvent.getAny();
		extensionList.add(extension.build());
		return this;
	}

	// add ILMD
	public ObjectEventBuilder addILMD(ExtensionBuilder extension) {
		ILMDType ilmd = objectEvent.getIlmd();
		if (ilmd == null) {
			ilmd = new ILMDType();
			objectEvent.setIlmd(ilmd);
		}
		List<Object> ilmdList = ilmd.getAny();
		ilmdList.add(extension.build());
		return this;
	}

	// set error declaration
	public ObjectEventBuilder setErrorDeclaration(Date declarationTime, ErrorReason reason,
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
		objectEvent.setErrorDeclaration(edt);
		return this;
	}

	// set event id
	public ObjectEventBuilder setEventId(String eventID) {
		this.objectEvent.setEventID(eventID);
		return this;
	}

}
