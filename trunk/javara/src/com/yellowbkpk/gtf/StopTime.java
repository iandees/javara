package com.yellowbkpk.gtf;

import java.io.Serializable;

import com.yellowbkpk.gtf.enums.PickupTypeEnum;

public class StopTime implements Serializable {

	private static final long serialVersionUID = 2445270925528834089L;

	private final Trip tripId;
	private final String arrivalTime;
	private final String departureTime;
	private final Stop stopId;
	private final Integer stopSequence;
	private String stopHeadsign;
	private PickupTypeEnum pickupType;
	private PickupTypeEnum dropOffType;
	private String shapeDistTraveled;

	/**
	 * @param tripId
	 *            The trip_id field contains an ID that identifies a trip. This
	 *            value is referenced from the trips.txt file.
	 * @param arrivalTime
	 *            The arrival_time specifies the arrival time at a specific stop
	 *            for a specific trip on a route. For times occurring after
	 *            midnight, enter the time as a value greater than 24:00:00 in
	 *            HH:MM:SS local time for the day on which the trip schedule
	 *            begins.
	 * @param departureTime
	 *            The departure_time specifies the departure time from a
	 *            specific stop for a specific trip on a route. For times
	 *            occurring after midnight, enter the time as a value greater
	 *            than 24:00:00 in HH:MM:SS local time for the day on which the
	 *            trip schedule begins. If the departure and arrival times are
	 *            identical, please duplicate the values in the arrival_time and
	 *            departure_time fields.
	 * @param stopId
	 *            The stop_id field contains an ID that uniquely identifies a
	 *            stop. Multiple routes may use the same stop. This value is
	 *            referenced from the stops.txt file.
	 * @param stopSequence
	 *            The stop_sequence field identifies the order of the stops for
	 *            a particular trip. The values for stop_sequence must be
	 *            non-negative integers, and they must increase along the trip.
	 * @param stopHeadsign
	 *            The stop_headsign field contains the text that appears on a
	 *            sign that identifies the trip's destination to passengers. Use
	 *            this field to override the default trip_headsign when the
	 *            headsign changes between stops. If this headsign is associated
	 *            with an entire trip, use trip_headsign instead.
	 * @param pickupType
	 *            The pickup_type field indicates whether passengers are picked
	 *            up at a stop as part of the normal schedule or whether a
	 *            pickup at the stop is not available. This field also allows
	 *            the transit agency to indicate that passengers must call the
	 *            agency or notify the driver to arrange a pickup at a
	 *            particular stop.
	 * @param dropOffType
	 *            The drop_off_type field indicates whether passengers are
	 *            dropped off at a stop as part of the normal schedule or
	 *            whether a drop off at the stop is not available. This field
	 *            also allows the transit agency to indicate that passengers
	 *            must call the agency or notify the driver to arrange a drop
	 *            off at a particular stop.
	 * @param shapeDistTraveled
	 *            When used in the stop_times.txt file, the shape_dist_traveled
	 *            field positions a stop as a distance from the first shape
	 *            point. The shape_dist_traveled field represents a real
	 *            distance traveled along the route in units such as feet or
	 *            kilometers.
	 */
	public StopTime(Trip tripId, String arrivalTime, String departureTime,
			Stop stopId, Integer stopSequence, String stopHeadsign,
			PickupTypeEnum pickupType, PickupTypeEnum dropOffType,
			String shapeDistTraveled) {
		if (tripId == null || arrivalTime == null || departureTime == null
				|| stopId == null || stopSequence == null) {
			throw new IllegalArgumentException(
					"tripId, arrivalTime, departureTime, stopId, and stopSequence are all required attributes.");
		}

		this.tripId = tripId;
		this.arrivalTime = arrivalTime;
		this.departureTime = departureTime;
		this.stopId = stopId;
		this.stopSequence = stopSequence;
		this.stopHeadsign = stopHeadsign;
		this.pickupType = pickupType;
		this.dropOffType = dropOffType;
		this.shapeDistTraveled = shapeDistTraveled;
	}

	public Trip getTripId() {
		return tripId;
	}

	public String getArrivalTime() {
		return arrivalTime;
	}

	public String getDepartureTime() {
		return departureTime;
	}

	public Stop getStopId() {
		return stopId;
	}

	public Integer getStopSequence() {
		return stopSequence;
	}

	public String getStopHeadsign() {
		return stopHeadsign;
	}

	public PickupTypeEnum getPickupType() {
		return pickupType;
	}

	public PickupTypeEnum getDropOffType() {
		return dropOffType;
	}

	public String getShapeDistTraveled() {
		return shapeDistTraveled;
	}

	public String toString() {
		StringBuffer b = new StringBuffer();
		
		b.append(tripId.getTripId());
		b.append(",");
		b.append(arrivalTime);
		b.append(",");
		b.append(departureTime);
		b.append(",");
		b.append(stopId.getStopId());
		b.append(",");
		b.append(stopSequence);
		b.append(",");
		b.append(stopHeadsign);
		b.append(",");
		b.append(pickupType);
		b.append(",");
		b.append(dropOffType);
		b.append(",");
		b.append(shapeDistTraveled);
		
		return b.toString();
	}

}
