package com.yellowbkpk.gtf;

import java.io.Serializable;

public class Calendar implements Serializable {

	private static final long serialVersionUID = 6295119903524422577L;

	private final Trip serviceId;
	private final Boolean monday;
	private final Boolean tuesday;
	private final Boolean wednesday;
	private final Boolean thursday;
	private final Boolean friday;
	private final Boolean saturday;
	private final Boolean sunday;
	private final String startDate;
	private final String endDate;

	/**
	 * @param serviceId
	 *            he service_id contains an ID that uniquely identifies a set of
	 *            dates when service is available for one or more routes. This
	 *            value is dataset unique. It is referenced by the trips.txt
	 *            file.
	 * @param monday
	 *            The monday field contains a binary value that indicates
	 *            whether the service is valid for all Mondays.
	 * @param tuesday
	 *            The tuesday field contains a binary value that indicates
	 *            whether the service is valid for all Tuesdays.
	 * @param wednesday
	 *            The wednesday field contains a binary value that indicates
	 *            whether the service is valid for all Wednesdays.
	 * @param thursday
	 *            The thursday field contains a binary value that indicates
	 *            whether the service is valid for all Thursdays.
	 * @param friday
	 *            The friday field contains a binary value that indicates
	 *            whether the service is valid for all Fridays.
	 * @param saturday
	 *            The saturday field contains a binary value that indicates
	 *            whether the service is valid for all Saturdays.
	 * @param sunday
	 *            The sunday field contains a binary value that indicates
	 *            whether the service is valid for all Sundays.
	 * @param startDate
	 *            The start_date field contains the start date for the service.
	 * @param endDate
	 *            The end_date field contains the end date for the service. This
	 *            date is included in the service interval.
	 */
	public Calendar(Trip serviceId, Boolean monday, Boolean tuesday,
			Boolean wednesday, Boolean thursday, Boolean friday,
			Boolean saturday, Boolean sunday, String startDate, String endDate) {
		if (serviceId == null || monday == null || tuesday == null
				|| wednesday == null || thursday == null || friday == null
				|| saturday == null || sunday == null || startDate == null
				|| endDate == null) {
			throw new IllegalArgumentException("All arguments are required.");
		}

		this.serviceId = serviceId;
		this.monday = monday;
		this.tuesday = tuesday;
		this.wednesday = wednesday;
		this.thursday = thursday;
		this.friday = friday;
		this.saturday = saturday;
		this.sunday = sunday;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public Trip getServiceId() {
		return serviceId;
	}

	public Boolean getMonday() {
		return monday;
	}

	public Boolean getTuesday() {
		return tuesday;
	}

	public Boolean getWednesday() {
		return wednesday;
	}

	public Boolean getThursday() {
		return thursday;
	}

	public Boolean getFriday() {
		return friday;
	}

	public Boolean getSaturday() {
		return saturday;
	}

	public Boolean getSunday() {
		return sunday;
	}

	public String getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public String toString() {
		StringBuffer b = new StringBuffer();
		
		b.append(serviceId.getServiceId());
		b.append(",");
		b.append(monday);
		b.append(",");
		b.append(tuesday);
		b.append(",");
		b.append(wednesday);
		b.append(",");
		b.append(thursday);
		b.append(",");
		b.append(friday);
		b.append(",");
		b.append(saturday);
		b.append(",");
		b.append(sunday);
		b.append(",");
		b.append(startDate);
		b.append(",");
		b.append(endDate);
		
		return b.toString();
	}

}
