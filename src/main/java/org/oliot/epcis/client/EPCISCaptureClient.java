package org.oliot.epcis.client;

import org.oliot.epcis.model.*;
import org.oliot.epcis.model.exception.ValidationException;
import org.oliot.epcis.unit_converter.UnitConverter;
import org.oliot.epcis.client.document_builder.Validator;
import org.oliot.epcis.client.document_builder.VocabularyBuilder;
import org.w3c.dom.Document;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.*;

/**
 * Copyright (C) 2020-2021. (DFPL) all rights reserved. -
 * https://sites.google.com/view/jack-dfpl/home
 * <p>
 * Oliot EPCIS X is an open source implementation of Electronic Product Code
 * Information Service (EPCIS) v2.0,
 * <p>
 * epcis-client is a convenient library to use standardized interfaces ratified
 * by EPCIS v2.0 in Java language.
 * <p>
 * 
 * @author Jaewook Byun, Ph.D., Assistant Professor, Sejong University,
 *         jwbyun@sejong.ac.kr
 *         <p>
 *         Associate Director, Auto-ID Labs, KAIST, bjw0829@kaist.ac.kr
 * 
 * @author Hyungchan Kim, Bachelor Student, Sejong University, koc0819@gmail.com
 * 
 */
public class EPCISCaptureClient {
	private URL captureEndpoint;
	private EPCISDocumentType epcisDoc;
	private List<Object> events;
	private List<VocabularyType> vocabularies;

	public static UnitConverter unitConverter;

	public EPCISCaptureClient(URL captureEndpoint) {
		this.captureEndpoint = captureEndpoint;
		epcisDoc = new EPCISDocumentType();
		try {
			epcisDoc.setCreationDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar()));
		} catch (DatatypeConfigurationException e) {
			// Never happen
			e.printStackTrace();
		}
		epcisDoc.setSchemaVersion(new BigDecimal("2.0"));

		// initialize events
		events = new ArrayList<Object>();
		EventListType eventList = new EventListType();
		eventList.setObjectEventOrAggregationEventOrTransformationEvent(events);
		EPCISBodyType epcisBody = new EPCISBodyType();
		epcisBody.setEventList(eventList);
		epcisDoc.setEPCISBody(epcisBody);

		// vocabulary
		EPCISHeaderType epcisHeader = new EPCISHeaderType();
		EPCISHeaderExtensionType epcisHeaderExtension = new EPCISHeaderExtensionType();
		EPCISMasterDataType masterData = new EPCISMasterDataType();
		VocabularyListType vocaList = new VocabularyListType();
		vocabularies = new ArrayList<VocabularyType>();
		vocaList.setVocabulary(vocabularies);
		masterData.setVocabularyList(vocaList);
		epcisHeaderExtension.setEPCISMasterData(masterData);
		epcisHeader.setExtension(epcisHeaderExtension);
		epcisDoc.setEPCISHeader(epcisHeader);
	}

	public EPCISCaptureClient addObjectEvent(ObjectEventType objectEvent) throws ValidationException {
		Validator.validateObjectEvent(objectEvent);
		events.add(objectEvent);
		return this;
	}

	public EPCISCaptureClient addAggregationEvent(AggregationEventType aggregationEvent) throws ValidationException {
		Validator.validateAggregationEvent(aggregationEvent);
		events.add(aggregationEvent);
		return this;
	}

	public EPCISCaptureClient addTransactionEvent(TransactionEventType transactionEvent) throws ValidationException {
		Validator.validateTransactionEvent(transactionEvent);
		events.add(transactionEvent);
		return this;
	}

	public EPCISCaptureClient addTransformationEvent(TransformationEventType transformationEvent)
			throws ValidationException {
		Validator.validateTransformationEvent(transformationEvent);
		events.add(transformationEvent);
		return this;
	}

	public EPCISCaptureClient addAssociationEvent(AssociationEventType associationEvent) throws ValidationException {
		Validator.validateAssociationEvent(associationEvent);
		events.add(associationEvent);
		return this;
	}

	public EPCISCaptureClient addVocabulary(VocabularyBuilder vocabularyBuilder) throws ValidationException {
		vocabularies.add(vocabularyBuilder.build());
		return this;
	}

	public void clearEvents() {
		events = new ArrayList<Object>();
	}

	public void clearVocabularies() {
		vocabularies = new ArrayList<VocabularyType>();
	}

	public void clearAll() {
		clearEvents();
		clearVocabularies();
	}

	public HttpResponse<String> send() {
		try {
			return HttpClient.newBuilder().build()
					.send(HttpRequest.newBuilder().header("Content-Type", "application/xml")
							.uri(captureEndpoint.toURI()).POST(BodyPublishers.ofString(this.exportToXML())).build(),
							BodyHandlers.ofString());
		} catch (IOException | InterruptedException | URISyntaxException e) {
			// Never happen or should not happen
			e.printStackTrace();
			return null;
		}
	}

	public Document marshall() {
		// Note: add @XmlRootElement(name = "EPCISDocument") to EPCISDocumentType class
		// if necessary
		try {
			Document result = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			Marshaller marshaller = JAXBContext.newInstance(EPCISDocumentType.class).createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(epcisDoc, result);
			result.getDocumentElement().setPrefix("epcis");
			result.setXmlStandalone(true);
			result.normalize();
			return result;
		} catch (ParserConfigurationException | JAXBException e) {
			// Never happen or Should not happen
			e.printStackTrace();
			return null;
		}
	}

	public String exportToXML() {
		try {
			Transformer tf = TransformerFactory.newInstance().newTransformer();
			StringWriter sw = new StringWriter();
			tf.setOutputProperty(OutputKeys.INDENT, "yes");
			tf.transform(new DOMSource(marshall()), new StreamResult(sw));
			return sw.toString();
		} catch (TransformerException e) {
			// Never happen or Should not happen
			e.printStackTrace();
			return null;
		}
	}

	// TODO: export to json
	public String exportToJson() {
		// TODO: export to json
		return null;
	}
}
