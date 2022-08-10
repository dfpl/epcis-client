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
import org.oliot.epcis.model.AggregationEventType;
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
import org.oliot.epcis.model.cbv.BusinessStep;
import org.oliot.epcis.model.cbv.Disposition;
import org.oliot.epcis.model.cbv.ErrorReason;
import org.oliot.epcis.model.cbv.SourceDestinationType;
import org.oliot.epcis.model.exception.ValidationException;
import org.oliot.gcp.core.SimplePureIdentityFilter;
import org.w3c.dom.Element;

public class AggregationEventBuilder {
	private AggregationEventType aggregationEvent;

	/**
	 * Create a convenient builder for generating AggregationEvent
	 * 
	 * @param eventTime String representation matched with yyyy-MM-dd'T'HH:mm:ss.SSSXXX pattern
	 * @param eventTimeZoneOffset String representation matched with XXX 
	 * @param action One of ActionType
	 * @throws ParseException throws if eventTime or eventTimeZoneOffset violates each of their pattern
	 */
	public AggregationEventBuilder(String eventTime, String eventTimeZoneOffset, ActionType action)
			throws ParseException {

		this.aggregationEvent = new AggregationEventType();
		this.aggregationEvent.setChildEPCs(new EPCListType());

		this.setEventTime(eventTime);
		this.setEventTimeZoneOffset(eventTimeZoneOffset);

		this.setAction(action);

	}
	
	//constructors - long
	public AggregationEventBuilder(long eventTimeInMillis, String eventTimeZoneOffset, ActionType action)
			throws ParseException {

		this.aggregationEvent = new AggregationEventType();
		this.aggregationEvent.setChildEPCs(new EPCListType());

		this.setEventTime(eventTimeInMillis);
		this.setEventTimeZoneOffset(eventTimeZoneOffset);

		this.setAction(action);

	}

	
	//build
	public AggregationEventType build() {
		return this.aggregationEvent;
	}
	
	
	//time - date
	public AggregationEventBuilder setEventTime(Date date) {
		try {
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(date);
			XMLGregorianCalendar xmlGreCal = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
			aggregationEvent.setEventTime(xmlGreCal);
			return this;
		} catch (DatatypeConfigurationException e) {
			// Never happen or should not happen
			e.printStackTrace();
			return this;
		}
	}

	//time - string
	public AggregationEventBuilder setEventTime(String dateValue) throws ParseException {
		try {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
			Date date = format.parse(dateValue);
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(date);
			XMLGregorianCalendar xmlGreCal = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
			aggregationEvent.setEventTime(xmlGreCal);
			return this;
		} catch (DatatypeConfigurationException e) {
			// Never happen or should not happen
			e.printStackTrace();
			return this;
		}
	}

	//time - unixEpoch
	public AggregationEventBuilder setEventTime(long unixEpoch) {
		try {
			GregorianCalendar calendar = new GregorianCalendar();
			calendar.setTimeInMillis(unixEpoch);
			XMLGregorianCalendar xmlGreCal = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
			aggregationEvent.setEventTime(xmlGreCal);
			return this;
		} catch (DatatypeConfigurationException e) {
			// Never happen or should not happen
			e.printStackTrace();
			return this;
		}
	}
	
	//time zone - string
	public AggregationEventBuilder setEventTimeZoneOffset(String timeZone) throws ParseException {
		DateFormat format = new SimpleDateFormat("XXX");
		format.parse(timeZone);
		this.aggregationEvent.setEventTimeZoneOffset(timeZone);
		return this;
	}

	

	//set parentID
	public AggregationEventBuilder setParentId(String id) {
		this.aggregationEvent.setParentID(id);
		return this;
	}

	//set childEPCs
	public AggregationEventBuilder setChildEPCList(List<String> epcs) throws ValidationException {
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
		this.aggregationEvent.setChildEPCs(epcList);
		return this;
	}
	
	
	//add childEPC
	public AggregationEventBuilder addChildEPC(String epc) throws ValidationException {
		if (!SimplePureIdentityFilter.isPureIdentityInstanceLevelObjectIdentifier(epc)) {
			ValidationException e = new ValidationException();
			e.setStackTrace(new StackTraceElement[0]);
			e.setReason(epc + " should comply with pure identity instance-level object identifier format");
			throw e;
		}

		EPCListType epcList = this.aggregationEvent.getChildEPCs();
		if (epcList == null) {
			epcList = new EPCListType();
		}
		List<EPC> curEPCs = epcList.getEpc();
		curEPCs.add(new EPC(epc));
		this.aggregationEvent.setChildEPCs(epcList);
		return this;
	}

	//set action
	public AggregationEventBuilder setAction(ActionType action) {
		this.aggregationEvent.setAction(action);

		return this;
	}

	//set business step
	public AggregationEventBuilder setBizStep(BusinessStep bizStep) {
		this.aggregationEvent.setBizStep(bizStep.getBusinessStep());
		return this;
	}

	//set disposition
	public AggregationEventBuilder setDisposition(Disposition disposition) {
		this.aggregationEvent.setDisposition(disposition.getDisposition());
		return this;
	}
	
	//set read point - string
	public AggregationEventBuilder setReadPoint(String readPoint) throws ValidationException{
		
		if (!SimplePureIdentityFilter.isPureIdentityLocationIdentifier(readPoint)) {
			ValidationException e = new ValidationException();
			e.setStackTrace(new StackTraceElement[0]);
			e.setReason(readPoint + " should comply with pure identity location identifier format");
			throw e;
		}
		this.aggregationEvent.setReadPoint(new ReadPointType(readPoint));
		return this;
	}

	//set read point - latitude,longitude
	public AggregationEventBuilder setReadPoint(float latitude, float longitude) throws ValidationException {
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
		this.aggregationEvent.setReadPoint(new ReadPointType("geo:" + latitude + "," + longitude));
		return this;
	}

	//set business location - string
	public AggregationEventBuilder setBizLocation(String bizLocation) throws ValidationException {
		if (!SimplePureIdentityFilter.isPureIdentityLocationIdentifier(bizLocation)) {
			ValidationException e = new ValidationException();
			e.setStackTrace(new StackTraceElement[0]);
			e.setReason(bizLocation + " should comply with pure identity location identifier format");
			throw e;
		}
		this.aggregationEvent.setBizLocation(new BusinessLocationType(bizLocation));
		return this;
	}

	
	//set business location - latitude,longitude
	public AggregationEventBuilder setBizLocation(float latitude, float longitude) throws ValidationException {

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
		this.aggregationEvent.setBizLocation(new BusinessLocationType("geo:" + latitude + "," + longitude));
		return this;

	}

	//add business transaction
	public AggregationEventBuilder addBizTransaction(org.oliot.epcis.model.cbv.BusinessTransactionType type,
			String value) {
		
		BusinessTransactionListType btlt = this.aggregationEvent.getBizTransactionList();
		if (btlt == null)
			btlt = new BusinessTransactionListType();
		List<BusinessTransactionType> bttList = btlt.getBizTransaction();
		if (bttList == null) {
			bttList = new ArrayList<BusinessTransactionType>();
			btlt.setBizTransaction(bttList);
		}
		bttList.add(new BusinessTransactionType(type.getBusinessTransactionType(), value));
		this.aggregationEvent.setBizTransactionList(btlt);
		return this;
		
	}

	
	//add childQuantityElement
	public AggregationEventBuilder addChildQuantityElement(String classLevelEPC, Double quantity, String uom) throws ValidationException {
		if (!SimplePureIdentityFilter.isPureIdentityClassLevelObjectIdentifier(classLevelEPC)) {
			ValidationException e = new ValidationException();
			e.setStackTrace(new StackTraceElement[0]);
			e.setReason(classLevelEPC + " should comply with pure identity instance-level object identifier format");
			throw e;
		}

		QuantityListType epcList = this.aggregationEvent.getChildQuantityList();
		if (epcList == null) {
			epcList = new QuantityListType();
		}

		List<QuantityElementType> curQuantityList = epcList.getQuantityElement();
		curQuantityList.add(new QuantityElementType(classLevelEPC, quantity, uom));
		this.aggregationEvent.setChildQuantityList(epcList);
		return this;
	}

	
	
	
	//add source
	public AggregationEventBuilder addSource(SourceDestinationType type, String value) {
		
		SourceListType slt=this.aggregationEvent.getSourceList();
		if (slt == null)
			slt = new SourceListType();
		List<SourceDestType> sdtList = slt.getSource();
		if (sdtList == null) {
			sdtList = new ArrayList<SourceDestType>();
			slt.setSource(sdtList);
		}
		sdtList.add(new SourceDestType(type.getSourceDestinationType(), value));
		
		
		this.aggregationEvent.setSourceList(slt);
		
		return this;
		
	}
	
	//add destination
	public AggregationEventBuilder addDestination(SourceDestinationType type, String value) {
	
		DestinationListType dlt=this.aggregationEvent.getDestinationList();
		
		if (dlt == null)
			dlt = new DestinationListType();
		List<SourceDestType> sdtList = dlt.getDestination();
		if (sdtList == null) {
			sdtList = new ArrayList<SourceDestType>();
			dlt.setDestination(sdtList);
		}
		sdtList.add(new SourceDestType(type.getSourceDestinationType(), value));
		
		this.aggregationEvent.setDestinationList(dlt);
		
		return this;
	}
	
	

	
	//add sensor element
	public AggregationEventBuilder addSensorElement(SensorElementBuilder builder) throws ValidationException {
		SensorElementListType selt = aggregationEvent.getSensorElementList();
		if (selt == null) {
			selt = new SensorElementListType();
			aggregationEvent.setSensorElementList(selt);
		}
		List<SensorElementType> sensorElementList = selt.getSensorElement();
		sensorElementList.add(builder.build());
		return this;
	}

	//add extension
		public AggregationEventBuilder addExtension(ExtensionBuilder extension) {
			List<Object> extensionList = aggregationEvent.getAny();
			extensionList.add(extension.build());
			return this;
		}
	
		
		
		
		
		
		
		
		
		
	// error declaration
	public AggregationEventBuilder setErrorDeclaration(Date declarationTime, ErrorReason reason,
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
		aggregationEvent.setErrorDeclaration(edt);
		return this;
	}

	
	public AggregationEventBuilder addExtension(Element extension) {
		this.aggregationEvent.getAny().add(extension);
		return this;
	}
	
	

}
