package org.oliot.epcis.client.document_builder;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.DatatypeConverter;

import org.oliot.epcis.client.EPCISQueryClient;
import org.oliot.epcis.model.ActionType;
import org.oliot.epcis.model.QuerySchedule;
import org.oliot.epcis.model.Subscribe;
import org.oliot.epcis.model.SubscriptionControls;
import org.oliot.epcis.model.cbv.BusinessTransactionType;
import org.oliot.epcis.model.cbv.Comparator;
import org.oliot.epcis.model.cbv.EventType;
import org.oliot.epcis.model.cbv.Measurement;
import org.oliot.epcis.model.cbv.SourceDestinationType;
import org.oliot.epcis.unit_converter.unit.Unit;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

@SuppressWarnings("unused")
public class SubscriptionControlBuilder {
	private EPCISQueryClient client;
	private Document subscribeDocument;
	private Element subscribeElement;
	
	public SubscriptionControlBuilder(EPCISQueryClient client, String subscriptionID, URL dest, QuerySchedule schedule, Date initialRecordTime, boolean reportIfEmpty) {
		this.client = client;
		subscribeDocument = this.client.getSubscribeEventDocument();
		subscribeElement = this.client.getSubscribeElement();
		
		Element subscriptionIDElement = subscribeDocument.createElement("subscriptionID");
		subscriptionIDElement.setTextContent(subscriptionID);
		subscribeElement.appendChild(subscriptionIDElement);
		
		Element destElement = subscribeDocument.createElement("dest");
		destElement.setTextContent(dest.toString());
		subscribeElement.appendChild(destElement);
		
		Element controls = subscribeDocument.createElement("controls");
		subscribeElement.appendChild(controls);
				
		Element reportIfEmptyElement = subscribeDocument.createElement("reportIfEmpty");
		reportIfEmptyElement.setTextContent(String.valueOf(reportIfEmpty));
		controls.appendChild(reportIfEmptyElement);
		
		if(initialRecordTime != null) {
			Element initialRecordTimeElement = subscribeDocument.createElement("initialRecordTime");
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
			initialRecordTimeElement.setTextContent(format.format(initialRecordTime));
			controls.appendChild(initialRecordTimeElement);
		}
		
		Element scheduleElement = subscribeDocument.createElement("schedule");
		controls.appendChild(scheduleElement);
		
		if( schedule.getSecond() != null ) {
			Element second = subscribeDocument.createElement("second");
			second.setTextContent(schedule.getSecond());
			scheduleElement.appendChild(second);
		}
		if( schedule.getMinute() != null) {
			Element minute = subscribeDocument.createElement("minute");
			minute.setTextContent(schedule.getMinute());
			scheduleElement.appendChild(minute);
		}
		if( schedule.getHour() != null) {
			Element hour = subscribeDocument.createElement("hour");
			hour.setTextContent(schedule.getHour());
			scheduleElement.appendChild(hour);
		}
		if( schedule.getDayOfMonth() != null ) {
			Element dayOfMonth = subscribeDocument.createElement("dayOfMonth");
			dayOfMonth.setTextContent(schedule.getDayOfMonth());			
			scheduleElement.appendChild(dayOfMonth);
		}
		if( schedule.getMonth() != null ) {
			Element month = subscribeDocument.createElement("month");
			month.setTextContent(schedule.getMonth());
			scheduleElement.appendChild(month);
		}
		if( schedule.getDayOfWeek() != null ) {
			Element dayOfWeek = subscribeDocument.createElement("dayOfWeek");
			dayOfWeek.setTextContent(schedule.getDayOfWeek());
			scheduleElement.appendChild(dayOfWeek);
		}
	}
	
	public SubscriptionControlBuilder(EPCISQueryClient client, String subscriptionID, URL dest, String triggerID, boolean reportIfEmpty) {
		this.client = client;
		subscribeDocument = this.client.getSubscribeEventDocument();
		subscribeElement = this.client.getSubscribeElement();
		
		Element subscriptionIDElement = subscribeDocument.createElement("subscriptionID");
		subscriptionIDElement.setTextContent(subscriptionID);
		subscribeElement.appendChild(subscriptionIDElement);
		
		Element destElement = subscribeDocument.createElement("dest");
		destElement.setTextContent(dest.toString());
		subscribeElement.appendChild(destElement);
		
		Element controls = subscribeDocument.createElement("controls");
		subscribeElement.appendChild(controls);
				
		Element reportIfEmptyElement = subscribeDocument.createElement("reportIfEmpty");
		reportIfEmptyElement.setTextContent(String.valueOf(reportIfEmpty));
		controls.appendChild(reportIfEmptyElement);
		
		Element triggerElement = subscribeDocument.createElement("trigger");
		triggerElement.setTextContent(triggerID);		
		controls.appendChild(triggerElement);
	}
	
	public EPCISQueryClient build() {
		return client;
	}
}
