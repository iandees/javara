package com.yellowbkpk.gtf;

import java.io.Serializable;
import java.net.URL;

public class Stop implements Serializable {

	private static final long serialVersionUID = -8795843251553917040L;

	private final String stopId;
	private final String stopName;
	private String stopDesc;
	private final Double stopLat;
	private final Double stopLon;
	private String zoneId;
	private URL stopURL;

	/**
	 * This file contains information about individual locations where vehicles
	 * pick up or drop off passengers.
	 * 
	 * @param stopId
	 *            The stop_id field contains an ID that uniquely identifies a
	 *            stop. Multiple routes may use the same stop. The stop_id is
	 *            dataset unique.
	 * @param stopName
	 *            The stop_name field contains the name of a stop. Please use a
	 *            name that people will understand in the local and tourist
	 *            vernacular.
	 * @param stopDesc
	 *            The stop_desc field contains a description of a stop. Please
	 *            provide useful, quality information. Do not simply duplicate
	 *            the name of the stop.
	 * @param stopLat
	 *            The stop_lat field contains the latitude of a stop. The field
	 *            value must be a valid WGS 84 latitude.
	 * @param stopLon
	 *            The stop_lon field contains the longitude of a stop. The field
	 *            value must be a valid WGS 84 longitude value from -180 to 180.
	 * @param zoneId
	 *            The zone_id field defines the fare zone for a stop. Zone IDs
	 *            are required if you want to provide fare information using
	 *            fare_rules.txt.
	 * @param stopURL
	 *            The stop_url field contains the URL of a web page about a
	 *            particular stop. This should be different from the agency_url
	 *            and the route_url fields.
	 */
	public Stop(String stopId, String stopName, String stopDesc,
			Double stopLat, Double stopLon, String zoneId, URL stopURL) {
		if (stopId == null || stopName == null) {
			throw new IllegalArgumentException(
					"stopID, stopName, stopLat, stopLon are all required.");
		}

		this.stopId = stopId;
		this.stopName = stopName;
		this.stopDesc = stopDesc;
		this.stopLat = stopLat;
		this.stopLon = stopLon;
		this.zoneId = zoneId;
		this.stopURL = stopURL;
	}

	public String getStopId() {
		return stopId;
	}

	public String getStopName() {
		return stopName;
	}

	public String getStopDesc() {
		return stopDesc;
	}

	public Double getStopLat() {
		return stopLat;
	}

	public Double getStopLon() {
		return stopLon;
	}

	public String getZoneId() {
		return zoneId;
	}

	public URL getStopURL() {
		return stopURL;
	}

	public String toString() {
		StringBuffer b = new StringBuffer();

		b.append(stopId);
		b.append(",");
		b.append(stopName);
		b.append(",");
		
		if (stopDesc == null) {
			b.append("");
		} else {
			b.append(stopDesc);
		}
		
		b.append(",");
		b.append(stopLat);
		b.append(",");
		b.append(stopLon);
		b.append(",");
		
		if (zoneId == null) {
			b.append("");
		} else {
			b.append(zoneId);
		}
		
		b.append(",");
		
		if (stopURL == null) {
			b.append("");
		} else {
			b.append(stopURL);
		}

		return b.toString();
	}

}
