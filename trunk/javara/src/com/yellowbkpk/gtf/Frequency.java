package com.yellowbkpk.gtf;

import java.io.Serializable;

public class Frequency implements Serializable {

	private static final long serialVersionUID = 33639553776987579L;

	private final Trip trip;
	private final String startTime;
	private final String endTime;
	private final Integer headwaySecs;

	/**
	 * @param trip
	 *            The trip_id contains an ID that identifies a trip on which the
	 *            specified frequency of service applies. Trip IDs are
	 *            referenced from the trips.txt file.
	 * @param startTime
	 *            The start_time field specifies the time at which service
	 *            begins with the specified frequency. For times occurring after
	 *            midnight, enter the time as a value greater than 24:00:00 in
	 *            HH:MM:SS local time for the day on which the trip schedule
	 *            begins. E.g. 25:35:00.
	 * @param endTime
	 *            The end_time field indicates the time at which service changes
	 *            to a different frequency (or ceases). For times occurring
	 *            after midnight, enter the time as a value greater than
	 *            24:00:00 in HH:MM:SS local time for the day on which the trip
	 *            schedule begins. E.g. 25:35:00.
	 * @param headwaySecs
	 *            The headway_secs field indicates the time between departures
	 *            from the same stop (headway) for this trip type, during the
	 *            time interval specified by start_time and end_time. The
	 *            headway value must be entered in seconds.
	 */
	public Frequency(Trip trip, String startTime, String endTime,
			Integer headwaySecs) {
		this.trip = trip;
		this.startTime = startTime;
		this.endTime = endTime;
		this.headwaySecs = headwaySecs;
	}

	public Trip getTripId() {
		return trip;
	}

	public String getStartTime() {
		return startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public Integer getHeadwaySecs() {
		return headwaySecs;
	}

	public String toString() {
		StringBuffer b = new StringBuffer();
		
		b.append(trip.getTripId());
		b.append(",");
		b.append(startTime);
		b.append(",");
		b.append(endTime);
		b.append(",");
		b.append(headwaySecs);
		
		return b.toString();
	}
}
