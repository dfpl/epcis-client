package org.oliot.epcis.client.document_builder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class ParamBuilder {
	
	protected Document pollDocument;
	protected Element pollParams;

	// Duplicate variable
	private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

	protected ParamBuilder addPollParameter(String name, List<String> values) {
		Element paramElem = pollDocument.createElement("param");
		Element nameElem = pollDocument.createElement("name");
		nameElem.setTextContent(name);
		Element valueElem = pollDocument.createElement("value");
		valueElem.setAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "type", "query:ArrayOfString");
		for (String obj : values) {
			Element objElem = pollDocument.createElement("string");
			objElem.setTextContent(obj);
			valueElem.appendChild(objElem);
		}
		paramElem.appendChild(nameElem);
		paramElem.appendChild(valueElem);
		pollParams.appendChild(paramElem);
		return this;
	}

	protected ParamBuilder addPollParameter(String name, String value) {
		Element paramElem = pollDocument.createElement("param");
		Element nameElem = pollDocument.createElement("name");
		nameElem.setTextContent(name);
		Element valueElem = pollDocument.createElement("value");
		valueElem.setAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "type", "xsd:string");
		valueElem.setTextContent(value.toString());
		paramElem.appendChild(nameElem);
		paramElem.appendChild(valueElem);
		pollParams.appendChild(paramElem);
		return this;
	}

	protected ParamBuilder addPollParameter(String name, Date value) {
		Element paramElem = pollDocument.createElement("param");
		Element nameElem = pollDocument.createElement("name");
		nameElem.setTextContent(name);
		Element valueElem = pollDocument.createElement("value");
		String dateValue = this.dateFormat.format(value);
		valueElem.setAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "type", "xsd:dateTime");
		valueElem.setTextContent(dateValue);
		paramElem.appendChild(nameElem);
		paramElem.appendChild(valueElem);
		pollParams.appendChild(paramElem);
		return this;
	}

	protected ParamBuilder addPollParameter(String name, double value) {
		Element paramElem = pollDocument.createElement("param");
		Element nameElem = pollDocument.createElement("name");
		nameElem.setTextContent(name);
		Element valueElem = pollDocument.createElement("value");
		valueElem.setAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "type", "xsd:double");
		valueElem.setTextContent(String.valueOf(value));
		paramElem.appendChild(nameElem);
		paramElem.appendChild(valueElem);
		pollParams.appendChild(paramElem);
		return this;
	}

	protected ParamBuilder addPollParameter(String name, int value) {
		Element paramElem = pollDocument.createElement("param");
		Element nameElem = pollDocument.createElement("name");
		nameElem.setTextContent(name);
		Element valueElem = pollDocument.createElement("value");
		valueElem.setAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "type", "xsd:integer");
		valueElem.setTextContent(String.valueOf(value));
		paramElem.appendChild(nameElem);
		paramElem.appendChild(valueElem);
		pollParams.appendChild(paramElem);
		return this;
	}

	protected ParamBuilder addPollParameter(String name, boolean value) {
		Element paramElem = pollDocument.createElement("param");
		Element nameElem = pollDocument.createElement("name");
		nameElem.setTextContent(name);
		Element valueElem = pollDocument.createElement("value");
		valueElem.setAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "type", "xsd:boolean");
		valueElem.setTextContent(String.valueOf(value));
		paramElem.appendChild(nameElem);
		paramElem.appendChild(valueElem);
		pollParams.appendChild(paramElem);
		return this;
	}

	protected ParamBuilder addPollParameter(String name) {
		Element paramElem = pollDocument.createElement("param");
		Element nameElem = pollDocument.createElement("name");
		nameElem.setTextContent(name);
		Element valueElem = pollDocument.createElement("value");
		valueElem.setAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "type", "VoidHolder");
		paramElem.appendChild(nameElem);
		paramElem.appendChild(valueElem);
		pollParams.appendChild(paramElem);
		return this;
	}
}
