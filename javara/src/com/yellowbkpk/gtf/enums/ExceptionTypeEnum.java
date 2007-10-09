package com.yellowbkpk.gtf.enums;

public enum ExceptionTypeEnum {

	/** Service has been added for the specified date. */
	ADDED(1),
	
	/** Service has been removed for the specified date. */
	REMOVED(2);
	
	private final int type;

	private ExceptionTypeEnum(int type) {
		this.type = type;
	}
	
	public int getType() {
		return type;
	}

	public String toString() {
		return (""+getType());
	}
}
