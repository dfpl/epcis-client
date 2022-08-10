package org.oliot.epcis.client.document_builder;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

import org.oliot.epcis.model.SensorElementType;
import org.oliot.epcis.model.SensorMetadataType;
import org.oliot.epcis.model.SensorReportType;
import org.oliot.epcis.model.exception.ValidationException;
import org.oliot.gcp.core.SimplePureIdentityFilter;

public class SensorElementBuilder {

	private SensorElementType sensorElement;
	private SensorMetadataType sensorMetadata;
	private List<SensorReportType> sensorReportList;
	private Map<QName, String> otherAttributes;
	private List<Object> extensionList;

	public SensorElementBuilder() {
		sensorElement = new SensorElementType();
		sensorMetadata = new SensorMetadataType();
		sensorElement.setSensorMetadata(sensorMetadata);
		sensorReportList = new ArrayList<SensorReportType>();
		sensorElement.setSensorReport(sensorReportList);
		otherAttributes = new HashMap<QName, String>();
		sensorMetadata.setOtherAttributes(otherAttributes);
		extensionList = new ArrayList<Object>();
		sensorElement.setAny(extensionList);
	}

	public SensorElementBuilder setSensorMetadataTime(Date date) {
		try {
			if (sensorMetadata == null) {
				sensorMetadata = new SensorMetadataType();
				sensorElement.setSensorMetadata(sensorMetadata);
			}
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(date);
			XMLGregorianCalendar xmlGreCal = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
			sensorMetadata.setTime(xmlGreCal);
			return this;
		} catch (DatatypeConfigurationException e) {
			// Never happen or should not happen
			e.printStackTrace();
			return this;
		}
	}

	public SensorElementBuilder setSensorMetadataDeviceID(String deviceID) throws ValidationException {
		if (sensorMetadata == null) {
			sensorMetadata = new SensorMetadataType();
			sensorElement.setSensorMetadata(sensorMetadata);
		}

		if (!SimplePureIdentityFilter.isPureIdentityInstanceLevelObjectIdentifier(deviceID)) {
			ValidationException e = new ValidationException();
			e.setStackTrace(new StackTraceElement[0]);
			e.setReason("Device ID should comply with instance-level object identifier");
			throw e;
		}
		sensorMetadata.setDeviceID(deviceID);
		return this;
	}

	public SensorElementBuilder setSensorMetadataDeviceMetadata(String deviceMetadata) {
		if (sensorMetadata == null) {
			sensorMetadata = new SensorMetadataType();
			sensorElement.setSensorMetadata(sensorMetadata);
		}

		sensorMetadata.setDeviceMetadata(deviceMetadata);
		return this;
	}

	public SensorElementBuilder setSensorMetadataRawData(String rawData) {
		if (sensorMetadata == null) {
			sensorMetadata = new SensorMetadataType();
			sensorElement.setSensorMetadata(sensorMetadata);
		}

		sensorMetadata.setRawData(rawData);
		return this;
	}

	public SensorElementBuilder setSensorMetadataStartTime(Date date) {
		try {
			if (sensorMetadata == null) {
				sensorMetadata = new SensorMetadataType();
				sensorElement.setSensorMetadata(sensorMetadata);
			}
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(date);
			XMLGregorianCalendar xmlGreCal = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
			sensorMetadata.setStartTime(xmlGreCal);
			return this;
		} catch (DatatypeConfigurationException e) {
			// Never happen or should not happen
			e.printStackTrace();
			return this;
		}
	}

	public SensorElementBuilder setSensorMetadataEndTime(Date date) {
		try {
			if (sensorMetadata == null) {
				sensorMetadata = new SensorMetadataType();
				sensorElement.setSensorMetadata(sensorMetadata);
			}
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(date);
			XMLGregorianCalendar xmlGreCal = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
			sensorMetadata.setEndTime(xmlGreCal);
			return this;
		} catch (DatatypeConfigurationException e) {
			// Never happen or should not happen
			e.printStackTrace();
			return this;
		}
	}

	public SensorElementBuilder setSensorMetadataBizRules(String bizRules) {
		if (sensorMetadata == null) {
			sensorMetadata = new SensorMetadataType();
			sensorElement.setSensorMetadata(sensorMetadata);
		}

		sensorMetadata.setBizRules(bizRules);
		return this;
	}

	public SensorElementBuilder setSensorMetadataDataProcessingMethod(String dataProcessingMethod) {
		if (sensorMetadata == null) {
			sensorMetadata = new SensorMetadataType();
			sensorElement.setSensorMetadata(sensorMetadata);
		}

		sensorMetadata.setDataProcessingMethod(dataProcessingMethod);
		return this;
	}

	public SensorElementBuilder addSensorMetadataOtherAttribute(QName key, String value) {
		if (sensorMetadata == null) {
			sensorMetadata = new SensorMetadataType();
			sensorElement.setSensorMetadata(sensorMetadata);
		}
		otherAttributes.put(key, value);
		return this;
	}

	public SensorElementBuilder addExtension(ExtensionBuilder extension) {
		List<Object> extensionList = sensorElement.getAny();
		extensionList.add(extension.build());
		return this;
	}

	public SensorElementBuilder addSensorReport(SensorReportType sensorReport) {
		sensorReportList.add(sensorReport);
		return this;
	}

	public SensorElementType build() throws ValidationException {
		List<SensorReportType> sensorReportList = sensorElement.getSensorReport();
		if (sensorReportList == null) {
			ValidationException e = new ValidationException();
			e.setStackTrace(new StackTraceElement[0]);
			e.setReason("SensorElement should have at least one Sensor Report");
			throw e;
		}
		for (SensorReportType sensorReport : sensorReportList) {
			if (sensorReport.getType() == null) {
				ValidationException e = new ValidationException();
				e.setStackTrace(new StackTraceElement[0]);
				e.setReason("Each sensor report should have 'type' field");
				throw e;
			}
		}
		return sensorElement;
	}
}
