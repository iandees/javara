package com.yellowbkpk.gtf;

import java.awt.Color;
import java.io.Serializable;
import java.net.URL;

import com.yellowbkpk.gtf.enums.RouteTypeEnum;

/**
 * This file contains information about a transit organization's routes. A route
 * is a sequence of two or more stops.
 * 
 * @author Ian Dees
 * 
 */
public class Route implements Serializable {

	private static final long serialVersionUID = -7436140466075265265L;

	private final String routeId;
	private String agencyId;
	private final String routeShortName;
	private final String routeLongName;
	private String routeDesc;
	private final RouteTypeEnum routeType;
	private URL routeURL;
	private Color routeColor;
	private Color routeTextColor;

	/**
	 * @param routeId
	 *            The route_id field contains an ID that uniquely identifies a
	 *            route. The route_id is dataset unique.
	 * @param agencyId
	 *            The agency_id field defines an agency for the specified route.
	 *            This value is referenced from the agency.txt file. Use this
	 *            field when you are providing data for routes from more than
	 *            one agency.
	 * @param routeShortName
	 *            The route_short_name contains the short name of a route. This
	 *            will often be the route number or route character(s). If the
	 *            route does not have a short name, please use an empty string
	 *            as the value for this field.
	 * @param routeLongName
	 *            The route_long_name contains the full name of a route. This
	 *            name will often include the route's destination or stop. If
	 *            the route does not have a long name, please use an empty
	 *            string as the value for this field.
	 * @param routeDesc
	 *            The route_desc field contains a description of a route. Please
	 *            provide useful, quality information. Do not simply duplicate
	 *            the name of the route.
	 * @param routeType
	 *            The route_type field describes the type of transportation used
	 *            on a route.
	 * @param routeURL
	 *            The route_url field contains the URL of a web page about that
	 *            particular route. This should be different from the
	 *            agency_url.
	 * @param routeColor
	 *            In systems that have colors assigned to routes, the
	 *            route_color field defines a color that corresponds to a route.
	 * @param routeTextColor
	 *            The route_text_color field can be used to specify a legible
	 *            color to use for text drawn against a background of
	 *            route_color.
	 */
	public Route(String routeId, String agencyId, String routeShortName,
			String routeLongName, String routeDesc, RouteTypeEnum routeType,
			URL routeURL, Color routeColor, Color routeTextColor) {
		if(routeId == null || routeShortName == null || routeLongName == null || routeType == null) {
			throw new IllegalArgumentException("RouteID, routeShortName, routeLongName, and routeType are required attributes.");
		}
		
		this.routeId = routeId;
		this.agencyId = agencyId;
		this.routeShortName = routeShortName;
		this.routeLongName = routeLongName;
		this.routeDesc = routeDesc;
		this.routeType = routeType;
		this.routeURL = routeURL;
		this.routeColor = routeColor;
		this.routeTextColor = routeTextColor;
	}

	public String getRouteId() {
		return routeId;
	}

	public String getAgencyId() {
		return agencyId;
	}

	public String getRouteShortName() {
		return routeShortName;
	}

	public String getRouteLongName() {
		return routeLongName;
	}

	public String getRouteDesc() {
		return routeDesc;
	}

	public RouteTypeEnum getRouteType() {
		return routeType;
	}

	public URL getRouteURL() {
		return routeURL;
	}

	public Color getRouteColor() {
		return routeColor;
	}

	public Color getRouteTextColor() {
		return routeTextColor;
	}

	public String toString() {
		StringBuffer b = new StringBuffer();
		
		b.append(routeId);
		b.append(",");
		b.append(agencyId);
		b.append(",");
		b.append(routeShortName);
		b.append(",");
		b.append(routeLongName);
		b.append(",");
		b.append(routeDesc);
		b.append(",");
		b.append(routeType.getType());
		b.append(",");
		b.append(routeURL);
		b.append(",");
		b.append(routeColor);
		b.append(",");
		b.append(routeTextColor);
		
		return b.toString();
	}

}
