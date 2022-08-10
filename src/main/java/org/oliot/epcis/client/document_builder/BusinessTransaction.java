package org.oliot.epcis.client.document_builder;

import org.oliot.epcis.model.cbv.BusinessTransactionType;

public class BusinessTransaction {
	private BusinessTransactionType type;
	private String value;

	public BusinessTransactionType getType() {
		return type;
	}

	public void setType(BusinessTransactionType type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public BusinessTransaction(BusinessTransactionType type, String value) {
		super();
		this.type = type;
		this.value = value;
	}

}
