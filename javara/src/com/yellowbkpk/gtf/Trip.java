package com.yellowbkpk.gtf;

import java.io.Serializable;

import com.yellowbkpk.gtf.enums.DirectionEnum;

/**
 * This file contains information about scheduled service along a particular
 * route. Trips consist of two or more stops that are made at regularly
 * scheduled intervals.
 * 
 * @author Ian Dees
 * 
 */
public class Trip implements Serializable {

	private static final long serialVersionUID = 722928262646167915L;

	private final Route routeId;
	private final Calendar serviceId;
	private final String tripId;
	private String tripHeadsign;
	private DirectionEnum directionId;
	private String blockId;
	private Shape shapeId;

	/**
	 * @param routeId
	 *            The route_id field contains an ID that uniquely identifies a
	 *            route. This value is referenced from the routes.txt file.
	 * @param serviceId
	 *            The service_id contains an ID that uniquely identifies a set
	 *            of dates when service is available for one or more routes.
	 *            This value is referenced from the calendar.txt or
	 *            calendar_dates.txt file.
	 * @param tripId
	 *            The trip_id field contains an ID that identifies a trip. The
	 *            trip_id is dataset unique.
	 * @param tripHeadsign
	 *            The trip_headsign field contains the text that appears on a
	 *            sign that identifies the trip's destination to passengers. Use
	 *            this field to distinguish between different patterns of
	 *            service in the same route. If the headsign changes during a
	 *            trip, you can override the trip_headsign by specifying values
	 *            for the the stop_headsign field in stop_times.txt.
	 * @param directionId
	 *            The direction_id field contains a binary value that indicates
	 *            the direction of travel for a trip. Use this field to
	 *            distinguish between bi-directional trips with the same
	 *            route_id. This field is not used in routing; it provides a way
	 *            to separate trips by direction when publishing time tables.
	 * @param blockId
	 *            The block_id field identifies the block to which the trip
	 *            belongs. A block consists of two or more sequential trips made
	 *            using the same vehicle, where a passenger can transfer from
	 *            one trip to the next just by staying in the vehicle.
	 * @param shapeId
	 *            The shape_id field contains an ID that defines a shape for the
	 *            trip. This value is referenced from the shapes.txt file. The
	 *            shapes.txt file allows you to define how a line should be
	 *            drawn on the map to represent a trip.
	 */
	public Trip(Route routeId, Calendar serviceId, String tripId,
			String tripHeadsign, DirectionEnum directionId, String blockId,
			Shape shapeId) {
		this.routeId = routeId;
		this.serviceId = serviceId;
		this.tripId = tripId;
		this.tripHeadsign = tripHeadsign;
		this.directionId = directionId;
		this.blockId = blockId;
		this.shapeId = shapeId;
	}

	public Route getRouteId() {
		return routeId;
	}

	public Calendar getServiceId() {
		return serviceId;
	}

	public String getTripId() {
		return tripId;
	}

	public String getTripHeadsign() {
		return tripHeadsign;
	}

	public DirectionEnum getDirectionId() {
		return directionId;
	}

	public String getBlockId() {
		return blockId;
	}

	public Shape getShapeId() {
		return shapeId;
	}

	public String toString() {
		StringBuffer b = new StringBuffer();

		b.append(routeId.getRouteId());
		b.append(",");
		b.append(serviceId.getServiceId());
		b.append(",");
		b.append(tripId);
		b.append(",");

		if (tripHeadsign == null) {
			b.append("");
		} else {
			b.append(tripHeadsign);
		}

		b.append(",");

		if (directionId == null) {
			b.append("");
		} else {
			b.append(directionId);
		}

		b.append(",");

		if (blockId == null) {
			b.append("");
		} else {
			b.append(blockId);
		}

		b.append(",");

		if (shapeId == null) {
			b.append("");
		} else {
			b.append(shapeId.getShapeId());
		}

		return b.toString();
	}

}
