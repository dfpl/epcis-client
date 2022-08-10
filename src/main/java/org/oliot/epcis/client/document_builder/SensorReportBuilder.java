package org.oliot.epcis.client.document_builder;

import java.net.URI;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

import org.oliot.epcis.model.SensorReportType;
import org.oliot.epcis.model.exception.ValidationException;
import org.oliot.epcis.unit_converter.unit.Unit;
import org.oliot.gcp.core.SimplePureIdentityFilter;

public class SensorReportBuilder {

	private SensorReportType sensorReport;
	private Map<QName, String> otherAttributes;

	public SensorReportBuilder(Unit unit) {
		sensorReport = new SensorReportType();
		sensorReport.setType(unit.toString());
		sensorReport.setUom(unit.getUom());
		sensorReport.setValueAttribute(unit.getValue());
		otherAttributes = new HashMap<QName, String>();
		sensorReport.setOtherAttributes(otherAttributes);
	}

	public enum Target {
		min, max, mean, sdev;
	}

	public SensorReportBuilder(Unit unit, Target target) {
		sensorReport = new SensorReportType();
		sensorReport.setType(unit.toString());
		sensorReport.setUom(unit.getUom());
		if (target.equals(Target.min))
			sensorReport.setMinValue(unit.getValue());
		else if (target.equals(Target.max))
			sensorReport.setMaxValue(unit.getValue());
		else if (target.equals(Target.mean))
			sensorReport.setMeanValue(unit.getValue());
		else if (target.equals(Target.sdev))
			sensorReport.setSDev(unit.getValue());
		else
			sensorReport.setValueAttribute(unit.getValue());
	}

	public SensorReportBuilder setValue(double value) {
		sensorReport.setValueAttribute(value);
		return this;
	}

	public SensorReportBuilder setMinValue(double value) {
		sensorReport.setMinValue(value);
		return this;
	}

	public SensorReportBuilder setMaxValue(double value) {
		sensorReport.setMaxValue(value);
		return this;
	}

	public SensorReportBuilder setMeanValue(double value) {
		sensorReport.setMeanValue(value);
		return this;
	}

	public SensorReportBuilder setSDevValue(double value) {
		sensorReport.setSDev(value);
		return this;
	}

	public SensorReportBuilder setComponent(String value) {
		sensorReport.setComponent(value);
		return this;
	}

	public SensorReportBuilder setStringValue(String value) {
		sensorReport.setStringValue(value);
		return this;
	}

	public SensorReportBuilder setBooleanValue(boolean value) {
		sensorReport.setBooleanValue(value);
		return this;
	}

	public SensorReportBuilder setHexBinaryValue(String value) {
		sensorReport.setHexBinaryValue(DatatypeConverter.parseHexBinary(value));
		return this;
	}

	public SensorReportBuilder setHexBinaryValue(byte[] value) {
		sensorReport.setHexBinaryValue(value);
		return this;
	}

	public SensorReportBuilder setURIValue(URI value) {
		sensorReport.setUriValue(value.toString());
		return this;
	}

	public SensorReportBuilder setChemicalSubstance(String value) {
		sensorReport.setChemicalSubstance(value);
		return this;
	}

	public SensorReportBuilder setMicroorganism(String value) {
		sensorReport.setMicroorganism(value);
		return this;
	}

	public SensorReportBuilder setDeviceID(String deviceID) throws ValidationException {
		if (!SimplePureIdentityFilter.isPureIdentityInstanceLevelObjectIdentifier(deviceID)) {
			ValidationException e = new ValidationException();
			e.setStackTrace(new StackTraceElement[0]);
			e.setReason("Device ID should comply with instance-level object identifier");
			throw e;
		}
		sensorReport.setDeviceID(deviceID);
		return this;
	}

	public SensorReportBuilder setDeviceMetadata(String value) {
		sensorReport.setDeviceMetadata(value);
		return this;
	}

	public SensorReportBuilder setRawData(String value) {
		sensorReport.setRawData(value);
		return this;
	}

	public SensorReportBuilder setTime(Date date) {
		try {
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(date);
			XMLGregorianCalendar xmlGreCal = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
			sensorReport.setTime(xmlGreCal);
			return this;
		} catch (DatatypeConfigurationException e) {
			// Never happen or should not happen
			e.printStackTrace();
			return this;
		}
	}

	public SensorReportBuilder setPercRank(double value) {
		sensorReport.setPercRank(value);
		return this;
	}

	public SensorReportBuilder setPercValue(double value) {
		sensorReport.setPercValue(value);
		return this;
	}

	public SensorReportBuilder setDataProcessingMethod(String value) {
		sensorReport.setDataProcessingMethod(value);
		return this;
	}

	public SensorReportBuilder addOtherAttribute(QName key, String value) {
		otherAttributes.put(key, value);
		return this;
	}

	public SensorReportType build() {
		return sensorReport;
	}
}
