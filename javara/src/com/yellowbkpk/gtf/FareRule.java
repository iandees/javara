package com.yellowbkpk.gtf;

import java.io.Serializable;

public class FareRule implements Serializable {

	private static final long serialVersionUID = -8667498985417630519L;

	private final FareAttributes fare;
	private Route route;
	private Stop origin;
	private Stop destination;
	private Stop contains;

	/**
	 * @param fare
	 *            The fare_id field contains an ID that uniquely identifies a
	 *            fare class. This value is referenced from the
	 *            fare_attributes.txt file.
	 * @param route
	 *            The route_id field associates the fare ID with a route. Route
	 *            IDs are referenced from the routes.txt file.
	 * @param origin
	 *            The origin_id field associates the fare ID with an origin zone
	 *            ID. Zone IDs are referenced from the stops.txt file.
	 * @param destination
	 *            The destination_id field associates the fare ID with a
	 *            destination zone ID. Zone IDs are referenced from the
	 *            stops.txt file.
	 * @param contains
	 *            The contains_id field associates the fare ID with all routes
	 *            that pass through a specified location. The contains ID field
	 *            is a zone ID, referenced from the stops.txt file.
	 */
	public FareRule(FareAttributes fare, Route route, Stop origin,
			Stop destination, Stop contains) {
		this.fare = fare;
		this.route = route;
		this.origin = origin;
		this.destination = destination;
		this.contains = contains;
	}

	public FareAttributes getFare() {
		return fare;
	}

	public Route getRoute() {
		return route;
	}

	public Stop getOrigin() {
		return origin;
	}

	public Stop getDestination() {
		return destination;
	}

	public Stop getContains() {
		return contains;
	}

	public String toString() {
		StringBuffer b = new StringBuffer();

		b.append(fare.getFareId());
		b.append(",");

		if (route == null) {
			b.append("");
		} else {
			b.append(route.getRouteId());
		}

		b.append(",");

		if (origin == null) {
			b.append("");
		} else {
			b.append(origin.getZoneId());
		}

		b.append(",");

		if (destination == null) {
			b.append("");
		} else {
			b.append(destination.getZoneId());
		}

		b.append(",");

		if (contains == null) {
			b.append("");
		} else {
			b.append(contains.getZoneId());
		}

		return b.toString();
	}
}
