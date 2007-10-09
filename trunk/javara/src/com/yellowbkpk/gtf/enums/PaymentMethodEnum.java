package com.yellowbkpk.gtf.enums;

public enum PaymentMethodEnum {

	/** Fare is paid on board. */
	ON_BOARD(0),
	
	/** Fare must be paid before boarding. */
	BEFORE_BOARDING(1);
	
	private final int type;

	private PaymentMethodEnum(int type) {
		this.type = type;
	}
	
	public int getType() {
		return type;
	}

	public String toString() {
		return (""+getType());
	}
}
