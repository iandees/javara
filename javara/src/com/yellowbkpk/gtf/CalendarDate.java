package com.yellowbkpk.gtf;

import java.io.Serializable;

import com.yellowbkpk.gtf.enums.ExceptionTypeEnum;

public class CalendarDate implements Serializable {

	private static final long serialVersionUID = -7051855075344061717L;

	private final Trip serviceId;
	private final String date;
	private final ExceptionTypeEnum exceptionType;

	/**
	 * @param serviceId
	 *            The service_id contains an ID that uniquely identifies a set
	 *            of dates when a service exception is available for one or more
	 *            routes. If the same service_id value appears in both files,
	 *            the information in calendar_dates.txt modifies the service
	 *            information specified in calendar.txt.
	 * @param date
	 *            The date field specifies a particular date when service
	 *            availability is different than the norm. You can use the
	 *            exception_type field to indicate whether service is available
	 *            on the specified date.
	 * @param exceptionType
	 *            The exception_type indicates whether service is available on
	 *            the date specified in the date field.
	 */
	public CalendarDate(Trip serviceId, String date,
			ExceptionTypeEnum exceptionType) {
		this.serviceId = serviceId;
		this.date = date;
		this.exceptionType = exceptionType;
	}

	public Trip getServiceId() {
		return serviceId;
	}

	public String getDate() {
		return date;
	}

	public ExceptionTypeEnum getExceptionType() {
		return exceptionType;
	}

	public String toString() {
		StringBuffer b = new StringBuffer();
		
		b.append(serviceId.getServiceId());
		b.append(",");
		b.append(date);
		b.append(",");
		b.append(exceptionType.getType());
		
		return b.toString();
	}
}
