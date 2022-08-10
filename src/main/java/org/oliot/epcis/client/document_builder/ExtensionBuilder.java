package org.oliot.epcis.client.document_builder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.oliot.epcis.model.cbv.ErrorReason;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ExtensionBuilder {
	private Element element;
	private Document docForExtension;
	private boolean hasTextContent;

	/**
	 * new Extension("http://dfpl.org/epcis", "stringValue", "dfpl", "hello");
	 * 
	 * would generate the followings
	 * 
	 * <pre>
	 * {@code
	 * <dfpl:stringValue xmlns:dfpl="http://dfpl.org/epcis" xmlns:xsd=
	 * "http://www.w3.org/2001/XMLSchema" xmlns:xsi=
	 * "http://www.w3.org/2001/XMLSchema-instance" xsi:type=
	 * "xsd:string">hello</dfpl:stringValue>
	 * }
	 * </pre>
	 * 
	 * @param client
	 * @param namespace   namespace of the extension element e.g.,
	 *                    http://dfpl.org/epcis
	 * @param localName   local name of the extension element e.g., stringValue
	 * @param prefix      prefix of the extension element e.g., dfpl
	 * @param stringValue text value of the extension e.g., hello
	 * @throws ParserConfigurationException
	 */
	public ExtensionBuilder(String namespace, String localName, String prefix, String stringValue) {
		try {
			this.docForExtension = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			element = docForExtension.createElementNS(namespace, localName);
			element.setPrefix(prefix);

			element.setAttribute("xmlns:xsd", "http://www.w3.org/2001/XMLSchema");
			element.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
			element.setAttribute("xsi:type", "xsd:string");
			element.setTextContent(stringValue);
			hasTextContent = true;
		} catch (ParserConfigurationException e) {
			// Never happen or should not happen
			e.printStackTrace();
		}
	}

	public ExtensionBuilder(String namespace, String localName, String prefix, double doubleValue) {
		try {
			this.docForExtension = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			element = docForExtension.createElementNS(namespace, localName);
			element.setPrefix(prefix);
			element.setAttribute("xmlns:xsd", "http://www.w3.org/2001/XMLSchema");
			element.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
			element.setAttribute("xsi:type", "xsd:double");
			element.setTextContent(String.valueOf(doubleValue));
			hasTextContent = true;
		} catch (ParserConfigurationException e) {
			// Never happen or should not happen
			e.printStackTrace();
		}
	}

	public ExtensionBuilder(String namespace, String localName, String prefix, int intValue) {
		try {
			this.docForExtension = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			element = docForExtension.createElementNS(namespace, localName);
			element.setPrefix(prefix);
			element.setAttribute("xmlns:xsd", "http://www.w3.org/2001/XMLSchema");
			element.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
			element.setAttribute("xsi:type", "xsd:integer");
			element.setTextContent(String.valueOf(intValue));
			hasTextContent = true;
		} catch (ParserConfigurationException e) {
			// Never happen or should not happen
			e.printStackTrace();
		}
	}

	public ExtensionBuilder(String namespace, String localName, String prefix, boolean booleanValue) {
		try {
			this.docForExtension = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			element = docForExtension.createElementNS(namespace, localName);
			element.setPrefix(prefix);

			element.setAttribute("xmlns:xsd", "http://www.w3.org/2001/XMLSchema");
			element.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
			element.setAttribute("xsi:type", "xsd:boolean");
			element.setTextContent(String.valueOf(booleanValue));
			hasTextContent = true;
		} catch (ParserConfigurationException e) {
			// Never happen or should not happen
			e.printStackTrace();
		}
	}

	public ExtensionBuilder(String namespace, String localName, String prefix, Date dateValue) {
		try {
			this.docForExtension = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			element = docForExtension.createElementNS(namespace, localName);
			element.setPrefix(prefix);

			element.setAttribute("xmlns:xsd", "http://www.w3.org/2001/XMLSchema");
			element.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
			element.setAttribute("xsi:type", "xsd:dateTime");
			element.setTextContent(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").format(dateValue));
			hasTextContent = true;
		} catch (ParserConfigurationException e) {
			// Never happen or should not happen
			e.printStackTrace();
		}
	}

	public ExtensionBuilder(String namespace, String localName, String prefix) {
		try {
			this.docForExtension = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			element = docForExtension.createElementNS(namespace, localName);
			element.setPrefix(prefix);
			hasTextContent = false;
		} catch (ParserConfigurationException e) {
			// Never happen or should not happen
			e.printStackTrace();
		}
	}

	public ExtensionBuilder addInnerExtension(String namespace, String localName, String prefix, String stringValue)
			throws IllegalAccessError {
		if (hasTextContent)
			throw new IllegalAccessError(
					"This extension should not have text content. Use Extension(EPCISCaptureClient client, String namespace, String localName, String prefix) constructor");

		Element ext = docForExtension.createElementNS(namespace, localName);
		ext.setPrefix(prefix);

		ext.setAttribute("xmlns:xsd", "http://www.w3.org/2001/XMLSchema");
		ext.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		ext.setAttribute("xsi:type", "xsd:string");
		ext.setTextContent(stringValue);

		element.appendChild(ext);
		return this;
	}

	public ExtensionBuilder addInnerExtension(String namespace, String localName, String prefix, double doubleValue)
			throws IllegalAccessError {
		if (hasTextContent)
			throw new IllegalAccessError(
					"This extension should not have text content. Use Extension(EPCISCaptureClient client, String namespace, String localName, String prefix) constructor");

		Element ext = docForExtension.createElementNS(namespace, localName);
		ext.setPrefix(prefix);

		ext.setAttribute("xmlns:xsd", "http://www.w3.org/2001/XMLSchema");
		ext.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		ext.setAttribute("xsi:type", "xsd:double");
		ext.setTextContent(String.valueOf(doubleValue));

		element.appendChild(ext);
		return this;
	}

	public ExtensionBuilder addInnerExtension(String namespace, String localName, String prefix, int intValue)
			throws IllegalAccessError {
		if (hasTextContent)
			throw new IllegalAccessError(
					"This extension should not have text content. Use Extension(EPCISCaptureClient client, String namespace, String localName, String prefix) constructor");

		Element ext = docForExtension.createElementNS(namespace, localName);
		ext.setPrefix(prefix);

		ext.setAttribute("xmlns:xsd", "http://www.w3.org/2001/XMLSchema");
		ext.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		ext.setAttribute("xsi:type", "xsd:integer");
		ext.setTextContent(String.valueOf(intValue));

		element.appendChild(ext);
		return this;
	}

	public ExtensionBuilder addInnerExtension(String namespace, String localName, String prefix, boolean booleanValue)
			throws IllegalAccessError {
		if (hasTextContent)
			throw new IllegalAccessError(
					"This extension should not have text content. Use Extension(EPCISCaptureClient client, String namespace, String localName, String prefix) constructor");

		Element ext = docForExtension.createElementNS(namespace, localName);
		ext.setPrefix(prefix);

		ext.setAttribute("xmlns:xsd", "http://www.w3.org/2001/XMLSchema");
		ext.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		ext.setAttribute("xsi:type", "xsd:boolean");
		ext.setTextContent(String.valueOf(booleanValue));

		element.appendChild(ext);
		return this;
	}

	public ExtensionBuilder addInnerExtension(String namespace, String localName, String prefix, Date dateValue)
			throws IllegalAccessError {

		if (hasTextContent)
			throw new IllegalAccessError(
					"This extension should not have text content. Use Extension(EPCISCaptureClient client, String namespace, String localName, String prefix) constructor");

		Element ext = docForExtension.createElementNS(namespace, localName);
		ext.setPrefix(prefix);

		ext.setAttribute("xmlns:xsd", "http://www.w3.org/2001/XMLSchema");
		ext.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		ext.setAttribute("xsi:type", "xsd:dateTime");
		ext.setTextContent(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").format(dateValue));

		element.appendChild(ext);
		return this;
	}

	public ExtensionBuilder addInnerExtension(ExtensionBuilder extension) throws IllegalAccessError {
		if (hasTextContent)
			throw new IllegalAccessError(
					"This extension should not have text content. Use Extension(EPCISCaptureClient client, String namespace, String localName, String prefix) constructor");
		element.appendChild(extension.element);
		return this;
	}

	public Element build() {
		return element;
	}

	static Element getErrorDeclarationTime(Date dateValue) {
		try {
			Element element = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument()
					.createElement("declarationTime");
			element.setTextContent(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").format(dateValue));
			return element;
		} catch (ParserConfigurationException e) {
			// Never happen or should not happen
			e.printStackTrace();
			return null;
		}
	}

	static Element getErrorReason(ErrorReason errorReason) {
		try {
			Element element = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument()
					.createElement("reason");
			element.setTextContent(errorReason.getErrorReason());
			return element;
		} catch (ParserConfigurationException e) {
			// Never happen or should not happen
			e.printStackTrace();
			return null;
		}
	}

	static Element getCorrectiveEventIDs(List<String> correctiveEventIDs) {
		try {
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			Element element = doc.createElement("correctiveEventIDs");
			for (String correctiveEventID : correctiveEventIDs) {
				Element inner = doc.createElement("correctiveEventID");
				inner.setTextContent(correctiveEventID);
				element.appendChild(inner);
			}
			return element;
		} catch (ParserConfigurationException e) {
			// Never happen or should not happen
			e.printStackTrace();
			return null;
		}
	}
}
