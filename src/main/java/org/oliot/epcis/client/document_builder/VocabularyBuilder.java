package org.oliot.epcis.client.document_builder;

import java.util.ArrayList;
import java.util.List;

import org.oliot.epcis.model.AttributeType;
import org.oliot.epcis.model.IDListType;
import org.oliot.epcis.model.VocabularyElementListType;
import org.oliot.epcis.model.VocabularyElementType;
import org.oliot.epcis.model.VocabularyType;
import org.oliot.epcis.model.cbv.EPCISVocabularyType;
import org.oliot.epcis.model.exception.ValidationException;
import org.oliot.gcp.core.SimplePureIdentityFilter;

public class VocabularyBuilder {
	private VocabularyType vocabulary;
	private VocabularyElementType vocabularyElement;
	private List<AttributeType> attributeList;
	private List<String> children;

	public VocabularyBuilder(EPCISVocabularyType vocabularyType, String epc) throws ValidationException {
		if (!SimplePureIdentityFilter.isPureIdentity(epc)) {
			ValidationException e = new ValidationException();
			e.setStackTrace(new StackTraceElement[] {});
			e.setReason(epc + " is not complying with EPC Pure Identity Format");
			throw e;
		}
		vocabulary = new VocabularyType();
		vocabulary.setType(vocabularyType.getVocabularyType());
		VocabularyElementListType vocabularyElementListType = new VocabularyElementListType();
		vocabulary.setVocabularyElementList(vocabularyElementListType);
		List<VocabularyElementType> vocabularyElementList = new ArrayList<VocabularyElementType>();
		vocabularyElementListType.setVocabularyElement(vocabularyElementList);
		this.vocabularyElement = new VocabularyElementType();
		this.vocabularyElement.setId(epc);
		this.attributeList = new ArrayList<AttributeType>();
		this.vocabularyElement.setAttribute(attributeList);
		IDListType idList = new IDListType();
		this.children = new ArrayList<String>();
		idList.setId(children);
		vocabularyElement.setChildren(idList);
		vocabularyElementList.add(vocabularyElement);
	}

	public VocabularyType build() {
		return this.vocabulary;
	}

	public VocabularyBuilder addAttribute(String key, String value) {
		AttributeType newAttribute = new AttributeType();
		newAttribute.setId(key);
		newAttribute.setContent(List.of(value));
		attributeList.add(newAttribute);
		return this;
	}
	
	public VocabularyBuilder addAttribute(String key, ExtensionBuilder extensionBuilder) {
		AttributeType newAttribute = new AttributeType();
		newAttribute.setId(key);
		newAttribute.setContent(List.of(extensionBuilder.build()));
		attributeList.add(newAttribute);
		return this;
	}

	public VocabularyBuilder setChildren(List<String> children) {
		if (children == null)
			throw new NullPointerException();
		this.children.clear();
		this.children.addAll(children);
		return this;
	}

	public VocabularyBuilder addChild(String child) {
		this.children.add(child);
		return this;
	}
}
