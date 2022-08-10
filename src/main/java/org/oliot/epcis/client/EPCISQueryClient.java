package org.oliot.epcis.client;

import org.oliot.epcis.client.document_builder.EventQueryParamBuilder;
import org.oliot.epcis.client.document_builder.SubscriptionControlBuilder;
import org.oliot.epcis.client.document_builder.VocabularyQueryParamBuilder;
import org.oliot.epcis.model.QueryResults;
import org.oliot.epcis.model.QuerySchedule;
import org.oliot.epcis.model.VocabularyType;
import org.oliot.epcis.util.HTTPUtil;
import org.oliot.epcis.util.XMLUtil;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXB;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EPCISQueryClient {

	private URL queryEndpoint;
	
	private String getStandardVersionQuery;
	private String getVendorVersionQuery;
	private String getQueryNamesQuery;
	
	private Document pollEventDocument;
	private Element pollEventsParams;
	
	private Document pollVocabularyDocument;
	private Element pollVocabulariesParams;
	
	private String getSubscriptionIDsQuery;
	private Document subscribeEventDocument;
	private Element subscribeEventsParams;
	private Element subscribeElement;

	private boolean isVocabularyBuilderUsed;

	public enum EPCISQueryType {
		GetStandardVersion, GetVendorVersion, GetQueryNames, PollEvents, PollVocabularies, GetSubscriptionIDs,
		Unsubscribe, SubscribeEvents;
	}

	public EPCISQueryClient(URL queryEndpoint) {
		this.queryEndpoint = queryEndpoint;
		initialize();
	}

	public void initialize() {
		
		prepareGetStandardVersionQuery();
		prepareGetVendorVersionQuery();
		prepareGetQueryNames();
		
		preparePollEvents();
		preparePollVocabularies();
		
		prepareGetSubscriptionIDsQuery();
		prepareSubscribeEvents();
		isVocabularyBuilderUsed = false;
	}

	private void prepareGetStandardVersionQuery() {
		try {
			// prepare SOAP envelope
			Document getStandardVersionQueryDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder()
					.newDocument();
			
			Element envelope = getStandardVersionQueryDocument
					.createElementNS("http://schemas.xmlsoap.org/soap/envelope/", "soapenv:Envelope");
			Element header = getStandardVersionQueryDocument
					.createElementNS("http://schemas.xmlsoap.org/soap/envelope/", "soapenv:Header");
			Element body = getStandardVersionQueryDocument.createElementNS("http://schemas.xmlsoap.org/soap/envelope/",
					"soapenv:Body");
			Element query = getStandardVersionQueryDocument.createElementNS("urn:epcglobal:epcis-query:xsd:1",
					"query:GetStandardVersion");
			
			body.appendChild(query);
			header.appendChild(body);
			envelope.appendChild(header);
			getStandardVersionQueryDocument.appendChild(envelope);
			
			getStandardVersionQuery = XMLUtil.toString(getStandardVersionQueryDocument);
			
		} catch (ParserConfigurationException | TransformerException e) {
			// Never or should not happen
			e.printStackTrace();
		}
	}

	public String getStandardVersion() throws IOException, InterruptedException {
		try {
			HttpResponse<String> result = HTTPUtil.post(queryEndpoint.toURI(), getStandardVersionQuery);
			initialize();
			Document resultDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder()
					.parse(new ByteArrayInputStream(result.body().getBytes("UTF-8")));
			return resultDocument.getElementsByTagName("query:GetStandardVersionResult").item(0).getTextContent();
		} catch (ParserConfigurationException | SAXException | URISyntaxException e) {
			// Never or should not happen
			e.printStackTrace();
			return null;
		}
	}

	private void prepareGetVendorVersionQuery() {
		try {
			Document getVendorVersionQueryDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder()
					.newDocument();
			Element envelope = getVendorVersionQueryDocument
					.createElementNS("http://schemas.xmlsoap.org/soap/envelope/", "soapenv:Envelope");
			Element header = getVendorVersionQueryDocument.createElementNS("http://schemas.xmlsoap.org/soap/envelope/",
					"soapenv:Header");
			Element body = getVendorVersionQueryDocument.createElementNS("http://schemas.xmlsoap.org/soap/envelope/",
					"soapenv:Body");
			Element query = getVendorVersionQueryDocument.createElementNS("urn:epcglobal:epcis-query:xsd:1",
					"query:GetVendorVersion");
			body.appendChild(query);
			header.appendChild(body);
			envelope.appendChild(header);
			getVendorVersionQueryDocument.appendChild(envelope);
			getVendorVersionQuery = XMLUtil.toString(getVendorVersionQueryDocument);
		} catch (ParserConfigurationException | TransformerException e) {
			// Never happen or should not happen
			e.printStackTrace();
		}
	}

	public String getVendorVersion() throws IOException, InterruptedException {
		try {
			HttpResponse<String> result = HTTPUtil.post(queryEndpoint.toURI(), getVendorVersionQuery);
			initialize();
			Document resultDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder()
					.parse(new ByteArrayInputStream(result.body().getBytes("UTF-8")));
			return resultDocument.getElementsByTagName("query:GetVendorVersionResult").item(0).getTextContent();
		} catch (ParserConfigurationException | SAXException | URISyntaxException e) {
			// Never or should not happen
			e.printStackTrace();
			return null;
		}
	}

	private void prepareGetQueryNames() {
		try {
			Document getQueryNamesDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			Element envelope = getQueryNamesDocument.createElementNS("http://schemas.xmlsoap.org/soap/envelope/",
					"soapenv:Envelope");
			Element header = getQueryNamesDocument.createElementNS("http://schemas.xmlsoap.org/soap/envelope/",
					"soapenv:Header");
			Element body = getQueryNamesDocument.createElementNS("http://schemas.xmlsoap.org/soap/envelope/",
					"soapenv:Body");
			Element query = getQueryNamesDocument.createElementNS("urn:epcglobal:epcis-query:xsd:1",
					"query:GetQueryNames");
			body.appendChild(query);
			header.appendChild(body);
			envelope.appendChild(header);
			getQueryNamesDocument.appendChild(envelope);
			getQueryNamesQuery = XMLUtil.toString(getQueryNamesDocument);
		} catch (ParserConfigurationException | TransformerException e) {
			// Never or should not happen
			e.printStackTrace();
		}
	}

	public List<String> getQueryNames() throws IOException, InterruptedException {
		try {
			HttpResponse<String> result = HTTPUtil.post(queryEndpoint.toURI(), getQueryNamesQuery);
			initialize();
			Document resultDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder()
					.parse(new ByteArrayInputStream(result.body().getBytes("UTF-8")));

			Node getQueryNamesResult = resultDocument.getElementsByTagName("query:GetQueryNamesResult").item(0);
			NodeList queryNamesList = getQueryNamesResult.getChildNodes();
			ArrayList<String> supportedQueryNames = new ArrayList<String>();
			for (int i = 0; i < queryNamesList.getLength(); i++) {
				Node n = queryNamesList.item(i);
				if (n instanceof Element)
					supportedQueryNames.add(queryNamesList.item(i).getTextContent());
			}
			return supportedQueryNames;
		} catch (ParserConfigurationException | SAXException | URISyntaxException e) {
			// Never happen or should not happen
			e.printStackTrace();
			return null;
		}
	}

	private void preparePollEvents() {
		try {
			pollEventDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			
			Element envelope = pollEventDocument.createElementNS("http://schemas.xmlsoap.org/soap/envelope/",
					"soapenv:Envelope");
			
			Element header = pollEventDocument.createElementNS("http://schemas.xmlsoap.org/soap/envelope/",
					"soapenv:Header");
			
			Element body = pollEventDocument.createElementNS("http://schemas.xmlsoap.org/soap/envelope/",
					"soapenv:Body");
			
			Element poll = pollEventDocument.createElementNS("urn:epcglobal:epcis-query:xsd:1", "query:Poll");
			
			Element queryName = pollEventDocument.createElement("queryName");
			
			queryName.setTextContent("SimpleEventQuery");
			
			pollEventsParams = pollEventDocument.createElement("params");
			
			poll.appendChild(queryName);
			poll.appendChild(pollEventsParams);
			body.appendChild(poll);
			header.appendChild(body);
			envelope.appendChild(header);
			
			pollEventDocument.appendChild(envelope);
		} catch (ParserConfigurationException e) {
			// Never or should not happen
			e.printStackTrace();
		}
	}

	public EPCISQueryClient showHTTPBody(EPCISQueryType query) {
		try {
			if (query == EPCISQueryType.GetStandardVersion) {
				System.out.println(getStandardVersionQuery);
			} else if (query == EPCISQueryType.GetVendorVersion) {
				System.out.println(getVendorVersionQuery);
			} else if (query == EPCISQueryType.GetQueryNames) {
				System.out.println(getQueryNamesQuery);
			} else if (query == EPCISQueryType.PollEvents) {
				System.out.println(XMLUtil.toString(pollEventDocument));
			} else if (query == EPCISQueryType.PollVocabularies) {
				System.out.println(XMLUtil.toString(pollVocabularyDocument));
			} else if (query == EPCISQueryType.GetSubscriptionIDs) {
				System.out.println(getSubscriptionIDsQuery);
			} else if (query == EPCISQueryType.Unsubscribe) {
				System.out.println(generateUnsubscribeQuery());
			} else if (query == EPCISQueryType.SubscribeEvents) {
				System.out.println(XMLUtil.toString(subscribeEventDocument));
			}
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		return this;
	}

	public List<Object> pollEvents() throws IOException, InterruptedException {
		try {
			// update queries

			// post
			String httpBody = XMLUtil.toString(pollEventDocument);
			HttpResponse<String> result = HTTPUtil.post(queryEndpoint.toURI(), httpBody);
			initialize();
			Document resultDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder()
					.parse(new ByteArrayInputStream(result.body().getBytes("UTF-8")));

			// prepare unmarshalling
			NamedNodeMap soapEnvelopeAttributes = resultDocument.getFirstChild().getAttributes();
			Element queryResults = (Element) resultDocument.getElementsByTagName("ns2:queryResults").item(0);
			for (int i = 0; i < soapEnvelopeAttributes.getLength(); i++) {
				Attr attr = (Attr) soapEnvelopeAttributes.item(i);
				queryResults.setAttribute(attr.getName(), attr.getValue());
			}

			// unmarshall
			InputStream stream = new ByteArrayInputStream(
					XMLUtil.toString(queryResults).getBytes(StandardCharsets.UTF_8));
			QueryResults list = JAXB.unmarshal(stream, QueryResults.class);

			return list.getResultsBody().getEventList().getObjectEventOrAggregationEventOrTransformationEvent();

		} catch (URISyntaxException | NullPointerException | ParserConfigurationException | SAXException
				| TransformerFactoryConfigurationError | TransformerException e) {
			// Never or should not happen
			e.printStackTrace();
			return null;
		}

	}

	public EventQueryParamBuilder prepareEventQueryParameters() {
		return new EventQueryParamBuilder(this, pollEventDocument, pollEventsParams);
	}

	private void preparePollVocabularies() {
		try {
			pollVocabularyDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			Element envelope = pollVocabularyDocument.createElementNS("http://schemas.xmlsoap.org/soap/envelope/",
					"soapenv:Envelope");
			Element header = pollVocabularyDocument.createElementNS("http://schemas.xmlsoap.org/soap/envelope/",
					"soapenv:Header");
			Element body = pollVocabularyDocument.createElementNS("http://schemas.xmlsoap.org/soap/envelope/",
					"soapenv:Body");
			Element poll = pollVocabularyDocument.createElementNS("urn:epcglobal:epcis-query:xsd:1", "query:Poll");
			Element queryName = pollVocabularyDocument.createElement("queryName");
			queryName.setTextContent("SimpleMasterDataQuery");
			pollVocabulariesParams = pollVocabularyDocument.createElement("params");
			poll.appendChild(queryName);
			poll.appendChild(pollVocabulariesParams);
			body.appendChild(poll);
			header.appendChild(body);
			envelope.appendChild(header);
			pollVocabularyDocument.appendChild(envelope);
		} catch (ParserConfigurationException e) {
			// Never or should not happen
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	public VocabularyQueryParamBuilder prepareVocabularyQueryParameters(boolean includeAttributes,
			boolean includeChildren) {
		isVocabularyBuilderUsed = true;
		return new VocabularyQueryParamBuilder(this, pollVocabularyDocument, pollVocabulariesParams)
				.includeAttributes(includeAttributes).includeChildren(includeChildren);
	}

	public List<VocabularyType> pollVocabularies() throws IOException, InterruptedException, IllegalAccessError {
		try {
			if (isVocabularyBuilderUsed == false) {
				throw new IllegalAccessError("invoke prepareVocabularyQueryParameters before poll");
			}

			// update queries

			// post
			String httpBody = XMLUtil.toString(pollVocabularyDocument);
			HttpResponse<String> result = HTTPUtil.post(queryEndpoint.toURI(), httpBody);
			initialize();
			Document resultDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder()
					.parse(new ByteArrayInputStream(result.body().getBytes("UTF-8")));

			// prepare unmarshalling
			NamedNodeMap soapEnvelopeAttributes = resultDocument.getFirstChild().getAttributes();
			Element queryResults = (Element) resultDocument.getElementsByTagName("ns2:queryResults").item(0);
			for (int i = 0; i < soapEnvelopeAttributes.getLength(); i++) {
				Attr attr = (Attr) soapEnvelopeAttributes.item(i);
				queryResults.setAttribute(attr.getName(), attr.getValue());
			}

			// unmarshall
			InputStream stream = new ByteArrayInputStream(
					XMLUtil.toString(queryResults).getBytes(StandardCharsets.UTF_8));
			QueryResults list = JAXB.unmarshal(stream, QueryResults.class);

			return list.getResultsBody().getVocabularyList().getVocabulary();

		} catch (URISyntaxException | NullPointerException | ParserConfigurationException | SAXException
				| TransformerFactoryConfigurationError | TransformerException e) {
			// Never or should not happen
			e.printStackTrace();
			return null;
		}

	}

	private void prepareGetSubscriptionIDsQuery() {
		try {
			Document getSubscriptionIDsDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder()
					.newDocument();
			Element envelope = getSubscriptionIDsDocument.createElementNS("http://schemas.xmlsoap.org/soap/envelope/",
					"soapenv:Envelope");
			Element header = getSubscriptionIDsDocument.createElementNS("http://schemas.xmlsoap.org/soap/envelope/",
					"soapenv:Header");
			Element body = getSubscriptionIDsDocument.createElementNS("http://schemas.xmlsoap.org/soap/envelope/",
					"soapenv:Body");
			Element poll = getSubscriptionIDsDocument.createElementNS("urn:epcglobal:epcis-query:xsd:1",
					"query:GetSubscriptionIDs");
			Element queryName = getSubscriptionIDsDocument.createElement("queryName");
			queryName.setTextContent("SimpleEventQuery");
			poll.appendChild(queryName);
			body.appendChild(poll);
			header.appendChild(body);
			envelope.appendChild(header);
			getSubscriptionIDsDocument.appendChild(envelope);
			getSubscriptionIDsQuery = XMLUtil.toString(getSubscriptionIDsDocument);
		} catch (ParserConfigurationException | TransformerException e) {
			// Never or should not happen
			e.printStackTrace();
		}
	}

	public List<String> getSubscriptionIDs() throws IOException, InterruptedException {
		try {
			HttpResponse<String> result = HTTPUtil.post(queryEndpoint.toURI(), getSubscriptionIDsQuery);
			initialize();
			Document resultDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder()
					.parse(new ByteArrayInputStream(result.body().getBytes("UTF-8")));

			Node getSubscriptionIDsResult = resultDocument.getElementsByTagName("query:GetSubscriptionIDsResult")
					.item(0);
			NodeList subscriptionIDsNodeList = getSubscriptionIDsResult.getChildNodes();
			ArrayList<String> subscriptionIDs = new ArrayList<String>();
			for (int i = 0; i < subscriptionIDsNodeList.getLength(); i++) {
				Node n = subscriptionIDsNodeList.item(i);
				if (n instanceof Element)
					subscriptionIDs.add(subscriptionIDsNodeList.item(i).getTextContent());
			}
			return subscriptionIDs;
		} catch (ParserConfigurationException | SAXException | URISyntaxException e) {
			// Never happen or should not happen
			e.printStackTrace();
			return null;
		}
	}

	private String generateUnsubscribeQuery() {
		try {
			Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			Element envelope = document.createElementNS("http://schemas.xmlsoap.org/soap/envelope/",
					"soapenv:Envelope");
			Element header = document.createElementNS("http://schemas.xmlsoap.org/soap/envelope/", "soapenv:Header");
			Element body = document.createElementNS("http://schemas.xmlsoap.org/soap/envelope/", "soapenv:Body");
			Element unsubscribe = document.createElementNS("urn:epcglobal:epcis-query:xsd:1", "query:Unsubscribe");
			Element id = document.createElement("subscriptionID");
			id.setTextContent("Your Subscription ID");
			unsubscribe.appendChild(id);
			body.appendChild(unsubscribe);
			header.appendChild(body);
			envelope.appendChild(header);
			document.appendChild(envelope);
			return XMLUtil.toString(document);
		} catch (ParserConfigurationException | TransformerException e) {
			// Never or should not happen
			e.printStackTrace();
			return null;
		}
	}

	public boolean unsubscribe(String subscriptionID) throws IOException, InterruptedException {
		try {
			Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			Element envelope = document.createElementNS("http://schemas.xmlsoap.org/soap/envelope/",
					"soapenv:Envelope");
			Element header = document.createElementNS("http://schemas.xmlsoap.org/soap/envelope/", "soapenv:Header");
			Element body = document.createElementNS("http://schemas.xmlsoap.org/soap/envelope/", "soapenv:Body");
			Element unsubscribe = document.createElementNS("urn:epcglobal:epcis-query:xsd:1", "query:Unsubscribe");
			Element id = document.createElement("subscriptionID");
			id.setTextContent(subscriptionID);
			unsubscribe.appendChild(id);
			body.appendChild(unsubscribe);
			header.appendChild(body);
			envelope.appendChild(header);
			document.appendChild(envelope);
			String unsubscribeQuery = XMLUtil.toString(document);

			HttpResponse<String> result = HTTPUtil.post(queryEndpoint.toURI(), unsubscribeQuery);
			initialize();
			Document resultDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder()
					.parse(new ByteArrayInputStream(result.body().getBytes("UTF-8")));

			if (resultDocument.getElementsByTagName("query:UnsubscribeResult").getLength() != 0)
				return true;
			else
				return false;
		} catch (ParserConfigurationException | SAXException | URISyntaxException | TransformerException e) {
			// Never happen or should not happen
			e.printStackTrace();
			return false;
		}
	}

	private void prepareSubscribeEvents() {
		try {
			subscribeEventDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			Element envelope = subscribeEventDocument.createElementNS("http://schemas.xmlsoap.org/soap/envelope/",
					"soapenv:Envelope");
			Element header = subscribeEventDocument.createElementNS("http://schemas.xmlsoap.org/soap/envelope/",
					"soapenv:Header");
			Element body = subscribeEventDocument.createElementNS("http://schemas.xmlsoap.org/soap/envelope/",
					"soapenv:Body");
			subscribeElement = subscribeEventDocument.createElementNS("urn:epcglobal:epcis-query:xsd:1",
					"query:Subscribe");
			Element queryName = subscribeEventDocument.createElement("queryName");
			queryName.setTextContent("SimpleEventQuery");
			subscribeEventsParams = subscribeEventDocument.createElement("params");
			subscribeElement.appendChild(queryName);
			subscribeElement.appendChild(subscribeEventsParams);
			body.appendChild(subscribeElement);
			header.appendChild(body);
			envelope.appendChild(header);
			subscribeEventDocument.appendChild(envelope);
		} catch (ParserConfigurationException e) {
			// Never or should not happen
			e.printStackTrace();
		}
	}

	public Element getSubscribeElement() {
		return subscribeElement;
	}

	public Document getSubscribeEventDocument() {
		return subscribeEventDocument;
	}

	public SubscriptionControlBuilder prepareSubscriptionControl(String subscriptionID, URL dest,
			QuerySchedule schedule, Date initialRecordTime, boolean reportIfEmpty) {
		return new SubscriptionControlBuilder(this, subscriptionID, dest, schedule, initialRecordTime, reportIfEmpty);
	}

	public SubscriptionControlBuilder prepareSubscriptionControl(String subscriptionID, URL dest, String triggerID,
			boolean reportIfEmpty) {
		return new SubscriptionControlBuilder(this, subscriptionID, dest, triggerID, reportIfEmpty);
	}

	public boolean subscribe() throws IOException, InterruptedException {
		try {
			// update queries

			// post
			String httpBody = XMLUtil.toString(subscribeEventDocument);
			HttpResponse<String> result = HTTPUtil.post(queryEndpoint.toURI(), httpBody);
			initialize();
			Document resultDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder()
					.parse(new ByteArrayInputStream(result.body().getBytes("UTF-8")));

			if (resultDocument.getElementsByTagName("query:SubscribeResult").getLength() != 0)
				return true;
			else
				return false;

		} catch (URISyntaxException | NullPointerException | ParserConfigurationException | SAXException
				| TransformerFactoryConfigurationError | TransformerException e) {
			// Never or should not happen
			e.printStackTrace();
			return false;
		}
	}

	public String trace(String epc, boolean includeTransformation, boolean includeAggregation,
			String temporalRelation) {
		return null;
	}

}
