package com.yellowbkpk.gtf.enums;

public enum DirectionEnum {

	/** Travel in one direction. (e.g. outbound travel) */
	OUTBOUND(0),

	/** Travel in the other direction. (e.g. inbound travel) */
	INBOUND(1);

	private byte type;

	private DirectionEnum(int id) {
		this.type = (byte) id;
	}

	public byte getType() {
		return this.type;
	}

	public String toString() {
		return (""+getType());
	}
	
}
