package org.oliot.epcis.client.document_builder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.DatatypeConverter;

import org.oliot.epcis.client.EPCISQueryClient;
import org.oliot.epcis.model.ActionType;
import org.oliot.epcis.model.cbv.BusinessTransactionType;
import org.oliot.epcis.model.cbv.Comparator;
import org.oliot.epcis.model.cbv.EventType;
import org.oliot.epcis.model.cbv.Measurement;
import org.oliot.epcis.model.cbv.SourceDestinationType;
import org.oliot.epcis.unit_converter.unit.Unit;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

@SuppressWarnings("unused")
public class VocabularyQueryParamBuilder extends ParamBuilder {
	private EPCISQueryClient client;

	public VocabularyQueryParamBuilder(EPCISQueryClient client, Document pollDocument, Element pollParameters) {
		this.client = client;
		this.pollDocument = pollDocument;
		this.pollParams = pollParameters;
	}

	/**
	 * @deprecated set includeAttributes and includeChildren in a constructor
	 * @param includeAttributes
	 * @return
	 */
	@Deprecated
	public VocabularyQueryParamBuilder includeAttributes(boolean includeAttributes) {
		addPollParameter("includeAttributes", includeAttributes);
		return this;
	}

	/**
	 * @deprecated set includeAttributes and includeChildren in a constructor
	 * @param includeChildren
	 * @return
	 */
	@Deprecated
	public VocabularyQueryParamBuilder includeChildren(boolean includeChildren) {
		addPollParameter("includeChildren", includeChildren);
		return this;
	}

	/**
	 * <pre>
	 * {@code
	 * <param>
	       <name>EQATTR_urn:epcglobal:cbv:mda:sst</name>
	        <value xsi:type="query:ArrayOfString">
	          <string>201</string>
	        </value>
	       </param>
	 * }
	 * </pre>
	 * 
	 * @param attribute
	 * @param valueList
	 * @return
	 */
	public VocabularyQueryParamBuilder equalAttribute(String attribute, List<String> valueList) {
		equalAttribute(attribute, valueList);
		return this;
	}

	/**
	 * 
	 * <pre>
	 {@code
	 <param> <name>EQ_name</name> <value xsi:type="query:ArrayOfString">
		 * <string>urn:epc:id:sgln:0037000.00729.0</string> </value> </param>
		 * <param>
	                <name>WD_name</name>
	                <value xsi:type="query:ArrayOfString">
	                    <string>urn:epc:id:sgln:0037000.00729.0</string>
	                </value>
	            </param>
	 }
	 * 
	 * </pre>
	 * 
	 * @param epc
	 * @return
	 */
	public VocabularyQueryParamBuilder equalName(String epc, boolean includeDescendants) {
		// TODO
		return this;
	}

	/**
	 * 
	 * <pre>
	 {@code
		<param> <name>vocabularyName</name> <value xsi:type="query:ArrayOfString">
		 * <string>urn:epcglobal:epcis:vtype:BusinessLocation</string> </value> </param>
	 }
	 * </pre>
	 * 
	 * @param vocabularyName
	 * @return
	 */
	public VocabularyQueryParamBuilder equalVocabularyName(String vocabularyName) {
		// TODO
		return this;
	}

	/**
	 * <pre>
	 {@code
		<param>
	                <name>HASATTR</name>
	                <value xsi:type="query:ArrayOfString">
	                    <string>http://epcis.example.com/mda/address</string>
	                </value>
	            </param>
	 }
	 * </pre>
	 */
	public VocabularyQueryParamBuilder hasAttribute(List<String> attributeList) {
		// TODO
		return this;
	}

	/**
	 * 
	 * <pre>
	 {@code
		<param>
	                <name>maxElementCount</name>
	                <value xsi:type="xsd:integer">1</value>
	            </param>
	 }
	 * </pre>
	 * 
	 * @param max
	 * @return
	 */
	public VocabularyQueryParamBuilder maxElementCount(int max) {
		// TODO
		return this;
	}

	/**
	 * 
	 * <pre>
	 {@code
		 <param>
	                <name>attributeNames</name>
	                <value xsi:type="query:ArrayOfString">
	                    <string>http://epcis.example.com/mda/address</string>
	                    <string>http://epcis.example.com/mda/stringVal</string>
	                </value>
	            </param>
	 }
	 * </pre>
	 * 
	 * @param attributeList
	 * @return
	 */
	public VocabularyQueryParamBuilder projectAttributes(List<String> attributeList) {
		// TODO
		return this;
	}

	public EPCISQueryClient build() {
		return client;
	}
}
