package org.oliot.epcis.client.writer.json;

import org.bson.Document;
import org.oliot.epcis.client.EPCISCaptureClient;
import org.oliot.epcis.model.AggregationEventType;
import org.oliot.epcis.model.exception.ValidationException;
import static org.oliot.epcis.util.BSONWriteUtil.*;

public class AggregationEventWriter {
	public static Document write(AggregationEventType obj) throws ValidationException {

		Document dbo = EPCISEventWriter.write(obj);

		// Event Type
		dbo.append("isA", "AggregationEvent");
		// Parent ID
		putEPC(dbo, "parentID", obj.getParentID());
		// Child EPCs - using EPCList for query efficiency
		putEPCList(dbo, "epcList", obj.getChildEPCs());
		// Action
		putAction(dbo, obj.getAction());
		// Biz Step
		putBizStep(dbo, obj.getBizStep());
		// Disposition
		putDisposition(dbo, obj.getDisposition());
		// ReadPoint
		putReadPoint(dbo, obj.getReadPoint());
		// BizLocation
		putBizLocation(dbo, obj.getBizLocation());
		// BizTransactionList
		putBizTransactionList(dbo, obj.getBizTransactionList());

		// ChildQuantityList - using QuantityList for query efficiency
		putQuantityList(dbo, "quantityList", obj.getChildQuantityList());

		// SourceList
		putSourceList(dbo, obj.getSourceList());

		// DestinationList
		putDestinationList(dbo, obj.getDestinationList());

		// SensorElementList
		putSensorElementList(dbo, obj.getSensorElementList(), EPCISCaptureClient.unitConverter);

		// PersistentDisposition
		putPersistentDisposition(dbo, obj.getPersistentDisposition());

		// Vendor Extension
		Document extension = putAny(dbo, "extension", obj.getAny(), false);
		if (extension != null)
			putFlatten(dbo, "extf", extension);

		// put event id
		if (!dbo.containsKey("eventID")) {
			putEventHashID(dbo);
		}

		return dbo;
	}
}
