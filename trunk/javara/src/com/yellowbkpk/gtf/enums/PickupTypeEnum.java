package com.yellowbkpk.gtf.enums;

public enum PickupTypeEnum {
	
	/** Regularly scheduled pickup/dropoff. */
	REGULARLY_SCHEDULED(0),
	
	/** No pickup/dropoff available. */
	NONE(1),
	
	/** Must phone agency to arrange pickup/dropoff. */
	MUST_PHONE(2),
	
	/** Must coordinate with driver to arrange pickup/dropoff. */
	MUST_COORDINATE(3);
	
	private int type;

	private PickupTypeEnum(int type) {
		this.type = type;
	}
	
	public int getType() {
		return type;
	}

	public String toString() {
		return (""+getType());
	}
}
