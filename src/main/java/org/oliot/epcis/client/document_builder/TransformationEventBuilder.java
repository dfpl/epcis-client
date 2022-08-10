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

import org.oliot.epcis.model.BusinessLocationType;
import org.oliot.epcis.model.BusinessTransactionListType;
import org.oliot.epcis.model.BusinessTransactionType;
import org.oliot.epcis.model.DestinationListType;
import org.oliot.epcis.model.EPC;
import org.oliot.epcis.model.EPCListType;
import org.oliot.epcis.model.ILMDType;
import org.oliot.epcis.model.PersistentDispositionType;
import org.oliot.epcis.model.QuantityElementType;
import org.oliot.epcis.model.QuantityListType;
import org.oliot.epcis.model.ReadPointType;
import org.oliot.epcis.model.SensorElementListType;
import org.oliot.epcis.model.SensorElementType;
import org.oliot.epcis.model.SourceDestType;
import org.oliot.epcis.model.SourceListType;
import org.oliot.epcis.model.TransformationEventType;
import org.oliot.epcis.model.cbv.BusinessStep;
import org.oliot.epcis.model.cbv.Disposition;
import org.oliot.epcis.model.cbv.SourceDestinationType;
import org.oliot.epcis.model.exception.ValidationException;
import org.oliot.gcp.core.SimplePureIdentityFilter;

public class TransformationEventBuilder {
	private TransformationEventType transformationEvent;


	//constructors - string
	public TransformationEventBuilder(String eventTime, String eventTimeZoneOffset) throws ParseException {
		this.transformationEvent = new TransformationEventType();
		this.transformationEvent.setInputEPCList(new EPCListType());
		this.transformationEvent.setOutputEPCList(new EPCListType());
		this.transformationEvent.setInputQuantityList(new QuantityListType());
		this.transformationEvent.setOutputQuantityList(new QuantityListType());
		
		this.setEventTime(eventTime);
		this.setEventTimeZoneOffset(eventTimeZoneOffset);
	}
	
	//constructors - long
	public TransformationEventBuilder(long eventTimeInMillis, String eventTimeZoneOffset) throws ParseException {
		this.transformationEvent = new TransformationEventType();
		this.transformationEvent.setInputEPCList(new EPCListType());
		this.transformationEvent.setOutputEPCList(new EPCListType());
		this.transformationEvent.setInputQuantityList(new QuantityListType());
		this.transformationEvent.setOutputQuantityList(new QuantityListType());
		
		this.setEventTime(eventTimeInMillis);
		this.setEventTimeZoneOffset(eventTimeZoneOffset);
		
	}
	
	//build
	public TransformationEventType build() {
		return this.transformationEvent;
	}
	

	//time - date
	public TransformationEventBuilder setEventTime(Date date) {

		try {
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(date);
			XMLGregorianCalendar xmlGreCal = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
			transformationEvent.setEventTime(xmlGreCal);
			
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		return this;
	}
	
	//time - string
	public TransformationEventBuilder setEventTime(String dateValue) throws ParseException {

		try {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
			Date date = format.parse(dateValue);
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(date);
			XMLGregorianCalendar xmlGreCal = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
			transformationEvent.setEventTime(xmlGreCal);
			
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return this;
	}
	
	//time - unixEpoch
	public TransformationEventBuilder setEventTime(long unixEpoch) {
		
		try {
			GregorianCalendar calendar = new GregorianCalendar();
			calendar.setTimeInMillis(unixEpoch);
			XMLGregorianCalendar xmlGreCal = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
			transformationEvent.setEventTime(xmlGreCal);
			
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return this;
	}
	
	
	//time zone - string
	public TransformationEventBuilder setEventTimeZoneOffset(String timeZone) throws ParseException {
		DateFormat format = new SimpleDateFormat("XXX");
		format.parse(timeZone);
		this.transformationEvent.setEventTimeZoneOffset(timeZone);
		return this;
	}
	
	
	//set input EPCList
	public TransformationEventBuilder setInputEPCList(List<String> epcs) throws ValidationException{
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
		
		
		this.transformationEvent.setInputEPCList(epcList);
		return this;
		
	}
	
	//set output EPCList
	public TransformationEventBuilder setOutputEPCList(List<String> epcs) throws ValidationException{
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

		this.transformationEvent.setOutputEPCList(epcList);
		return this;
	}
	
	//add input EPC
	public TransformationEventBuilder addInputEPC(String epc) throws ValidationException {
		
		if (!SimplePureIdentityFilter.isPureIdentityInstanceLevelObjectIdentifier(epc)) {
			ValidationException e = new ValidationException();
			e.setStackTrace(new StackTraceElement[0]);
			e.setReason(epc + " should comply with pure identity instance-level object identifier format");
			throw e;
		}
		
		EPCListType epcList=this.transformationEvent.getInputEPCList();
		
		if (epcList == null) {
			epcList = new EPCListType();
		}
		
		List<EPC> curEPCs = epcList.getEpc();
		curEPCs.add(new EPC(epc));
		
		this.transformationEvent.setInputEPCList(epcList);
		
		return this;
	}
	
	//add output EPC
	public TransformationEventBuilder addOutputEPC(String epc) throws ValidationException {
		
		if (!SimplePureIdentityFilter.isPureIdentityInstanceLevelObjectIdentifier(epc)) {
			ValidationException e = new ValidationException();
			e.setStackTrace(new StackTraceElement[0]);
			e.setReason(epc + " should comply with pure identity instance-level object identifier format");
			throw e;
		}
		
		EPCListType epcList=this.transformationEvent.getOutputEPCList();
		
		if (epcList == null) {
			epcList = new EPCListType();
		}
		
		List<EPC> curEPCs = epcList.getEpc();
		curEPCs.add(new EPC(epc));
		
		this.transformationEvent.setOutputEPCList(epcList);;
		
		return this;
		
	}
	
	
	
	
	//add input quantity element
	public TransformationEventBuilder addInputQuantityElement(String classLevelEPC,Double quantity,String uom) throws ValidationException {
		
		if (!SimplePureIdentityFilter.isPureIdentityClassLevelObjectIdentifier(classLevelEPC)) {
			ValidationException e = new ValidationException();
			e.setStackTrace(new StackTraceElement[0]);
			e.setReason(classLevelEPC + " should comply with pure identity instance-level object identifier format");
			throw e;
		}

		QuantityListType epcList = this.transformationEvent.getInputQuantityList();
		if (epcList == null) {
			epcList = new QuantityListType();
		}

		List<QuantityElementType> curQuantityList = epcList.getQuantityElement();
		curQuantityList.add(new QuantityElementType(classLevelEPC, quantity, uom));
		
		this.transformationEvent.setInputQuantityList(epcList);
		
		return this;
	}

	//add output quantity element
	public TransformationEventBuilder addOutputQuantityElement(String classLevelEPC,Double quantity,String uom) throws ValidationException {
		
		if (!SimplePureIdentityFilter.isPureIdentityClassLevelObjectIdentifier(classLevelEPC)) {
			ValidationException e = new ValidationException();
			e.setStackTrace(new StackTraceElement[0]);
			e.setReason(classLevelEPC + " should comply with pure identity instance-level object identifier format");
			throw e;
		}

		QuantityListType epcList = this.transformationEvent.getOutputQuantityList();
		if (epcList == null) {
			epcList = new QuantityListType();
		}

		List<QuantityElementType> curQuantityList = epcList.getQuantityElement();
		curQuantityList.add(new QuantityElementType(classLevelEPC, quantity, uom));
		
		this.transformationEvent.setOutputQuantityList(epcList);
		
		
		return this;
	}
	
	
	//set transformationID
	public TransformationEventBuilder setTransformationId(String transformationId) {
		
		this.transformationEvent.setTransformationID(transformationId);
		
		return this;
	}
	
	//set business step
	public TransformationEventBuilder setBizStep(BusinessStep bizStep) {
		this.transformationEvent.setBizStep(bizStep.getBusinessStep());
		return this;
	}
	
	//set disposition
	public TransformationEventBuilder setDisposition(Disposition disposition) {
		this.transformationEvent.setDisposition(disposition.getDisposition());
		return this;
	}
	
	//set read point - string
	public TransformationEventBuilder setReadPoint(String readPoint) throws ValidationException{
		
		if (!SimplePureIdentityFilter.isPureIdentityLocationIdentifier(readPoint)) {
			ValidationException e = new ValidationException();
			e.setStackTrace(new StackTraceElement[0]);
			e.setReason(readPoint + " should comply with pure identity location identifier format");
			throw e;
		}
		this.transformationEvent.setReadPoint(new ReadPointType(readPoint));
		return this;
	}
	
	//set read point - latitude,longitude
	public TransformationEventBuilder setReadPoint(float latitude, float longitude) throws ValidationException {
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
		
		this.transformationEvent.setReadPoint(new ReadPointType("geo:" + latitude + "," + longitude));
		return this;
	}
	
	//set business location - string
	public TransformationEventBuilder setBizLocation(String bizLocation) throws ValidationException {
		if (!SimplePureIdentityFilter.isPureIdentityLocationIdentifier(bizLocation)) {
			ValidationException e = new ValidationException();
			e.setStackTrace(new StackTraceElement[0]);
			e.setReason(bizLocation + " should comply with pure identity location identifier format");
			throw e;
		}
		
		this.transformationEvent.setBizLocation(new BusinessLocationType(bizLocation));
		return this;
	}
	
	//set business location - latitude,longitude
	public TransformationEventBuilder setBizLocation(float latitude, float longitude) throws ValidationException {
		
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
		
		this.transformationEvent.setBizLocation(new BusinessLocationType("geo:" + latitude + "," + longitude));
		
		return this;
		
	}

	
	
	//add business transaction
	public TransformationEventBuilder addBizTransaction(org.oliot.epcis.model.cbv.BusinessTransactionType type,String value) {
		
		BusinessTransactionListType btlt = this.transformationEvent.getBizTransactionList();
		if (btlt == null)
			btlt = new BusinessTransactionListType();
		List<BusinessTransactionType> bttList = btlt.getBizTransaction();
		if (bttList == null) {
			bttList = new ArrayList<BusinessTransactionType>();
			btlt.setBizTransaction(bttList);
		}
		bttList.add(new BusinessTransactionType(type.getBusinessTransactionType(), value));
		
		this.transformationEvent.setBizTransactionList(btlt);
		
		return this;
		
	}

	
	//add source
	public TransformationEventBuilder addSource(SourceDestinationType type, String value) {
		
		SourceListType slt=this.transformationEvent.getSourceList();
		if (slt == null)
			slt = new SourceListType();
		List<SourceDestType> sdtList = slt.getSource();
		if (sdtList == null) {
			sdtList = new ArrayList<SourceDestType>();
			slt.setSource(sdtList);
		}
		sdtList.add(new SourceDestType(type.getSourceDestinationType(), value));
		
		this.transformationEvent.setSourceList(slt);
		return this;
	}
	
	
	//add destination
	public TransformationEventBuilder addDestination(SourceDestinationType type, String value) {
	
		DestinationListType dlt=this.transformationEvent.getDestinationList();
		
		if (dlt == null)
			dlt = new DestinationListType();
		List<SourceDestType> sdtList = dlt.getDestination();
		if (sdtList == null) {
			sdtList = new ArrayList<SourceDestType>();
			dlt.setDestination(sdtList);
		}
		sdtList.add(new SourceDestType(type.getSourceDestinationType(), value));
		
		this.transformationEvent.setDestinationList(dlt);
		
		return this;
	}

	
	//add sensor element
	public TransformationEventBuilder addSensorElement(SensorElementBuilder builder) throws ValidationException {
		
		SensorElementListType selt = this.transformationEvent.getSensorElementList();
		if (selt == null) {
			selt = new SensorElementListType();
			this.transformationEvent.setSensorElementList(selt);
		}
		List<SensorElementType> sensorElementList = selt.getSensorElement();
		sensorElementList.add(builder.build());
		return this;
	}
	
	//add persistent disposition
	public TransformationEventBuilder addPersistentDisposition(String setValue,String unsetValue) {
		
		PersistentDispositionType pd=this.transformationEvent.getPersistentDisposition();
		List<String> setList=pd.getSet();
		List<String> unsetList=pd.getUnset();
		
		setList.add(setValue);
		unsetList.add(unsetValue);
		
		
		return this;
	}
	
	//add extension
	public TransformationEventBuilder addExtension(ExtensionBuilder extension) {
		List<Object> extensionList = this.transformationEvent.getAny();
		extensionList.add(extension.build());
		return this;
	}

	
	//add ILMD
	public TransformationEventBuilder addILMD(ExtensionBuilder extension) {
		
		ILMDType ilmd=this.transformationEvent.getIlmd();
		if (ilmd == null) {
			ilmd = new ILMDType();
			this.transformationEvent.setIlmd(ilmd);
		}
		List<Object> ilmdList = ilmd.getAny();
		ilmdList.add(extension.build());
		
		return this;
	}
}
