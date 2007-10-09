package com.yellowbkpk.gtf.enums;

public enum RouteTypeEnum {

	/**
	 * Tram, Streetcar, Light rail. Any light rail or street level system within
	 * a metropolitan area.
	 */
	LIGHT_RAIL(0),

	/** Subway, Metro. Any underground rail system within a metropolitan area. */
	SUBWAY(1),

	/** Rail. Used for intercity or long-distance travel. */
	RAIL(2),

	/** Bus. Used for short- and long-distance bus routes. */
	BUS(3),

	/** Ferry. Used for short- and long-distance boat service. */
	FERRY(4),

	/**
	 * Cable car. Used for street-level cable cars where the cable runs beneath
	 * the car.
	 */
	CABLE_CAR(5),

	/**
	 * Gondola, Suspended cable car. Typically used for aerial cable cars where
	 * the car is suspended from the cable.
	 */
	GONDOLA(6),

	/** Funicular. Any rail system designed for steep inclines. */
	FUNICULAR(7);

	private final short type;

	private RouteTypeEnum(int type) {
		this.type = (short) type;
	}
	
	public short getType() {
		return this.type;
	}

	public String toString() {
		return (""+getType());
	}
}
