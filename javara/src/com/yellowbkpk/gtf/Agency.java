package com.yellowbkpk.gtf;

import java.io.Serializable;
import java.net.URL;
import java.util.Locale;
import java.util.TimeZone;

public class Agency implements Serializable {

	private static final long serialVersionUID = 3134356039979225297L;

	private String agencyId;
	private final String agencyName;
	private final URL agencyURL;
	private final TimeZone agencyTimezone;
	private Locale agencyLang;
	
	/**
	 * This file contains information about the transit agency.
	 * 
	 * @param agencyId
	 *            The agency_id field is an ID that uniquely identifies a
	 *            transit agency. A transit feed may represent data from more
	 *            than one agency. The agency_id is dataset unique. This field
	 *            is optional for transit feeds that only contain data for a
	 *            single agency.
	 * @param agencyName
	 *            The agency_name field contains the full name of the transit
	 *            agency. Google Transit will display this name.
	 * @param agencyURL
	 *            The agency_url field contains the URL of the transit agency.
	 *            The value must be a fully qualified URL that includes http://
	 *            or https://, and any special characters in the URL must be
	 *            correctly escaped. See
	 *            http://www.w3.org/Addressing/URL/4_URI_Recommentations.html
	 *            for a description of how to create fully qualified URL values.
	 * @param agencyTimezone
	 *            The agency_timezone field contains the timezone where the
	 *            transit agency is located. Please refer to
	 *            http://en.wikipedia.org/wiki/List_of_tz_zones for a list of
	 *            valid values.
	 * @param locale
	 *            The agency_lang field contains a two-letter ISO 639-1 code for
	 *            the primary language used by this transit agency. This setting
	 *            defines capitalization rules and other language-specific
	 *            settings for all text contained in this transit agency's feed.
	 *            Please refer to
	 *            http://www.loc.gov/standards/iso639-2/php/code_list.php for a
	 *            list of valid values.
	 */
	public Agency(String agencyId, String agencyName, URL agencyURL,
			TimeZone agencyTimezone, Locale locale) {
		if(agencyName == null || agencyURL == null || agencyTimezone == null) {
			throw new IllegalArgumentException("Name, URL, and Timezone are required attributes.");
		}
		
		this.agencyId = agencyId;
		this.agencyName = agencyName;
		this.agencyURL = agencyURL;
		this.agencyTimezone = agencyTimezone;
		this.agencyLang = locale;
	}

	public String getAgencyId() {
		return agencyId;
	}

	public String getAgencyName() {
		return agencyName;
	}

	public URL getAgencyURL() {
		return agencyURL;
	}

	public TimeZone getAgencyTimezone() {
		return agencyTimezone;
	}

	public Locale getAgencyLang() {
		return agencyLang;
	}

	public String toString() {
		StringBuffer b = new StringBuffer();
		
		if (agencyId == null) {
			b.append("");
		} else {
			b.append(agencyId);
		}
		
		b.append(",");
		b.append(agencyName);
		b.append(",");
		b.append(agencyURL);
		b.append(",");
		b.append(agencyTimezone.getID());
		b.append(",");
		
		if (agencyLang == null) {
			b.append("");
		} else {
			b.append(agencyLang.getISO3Language());
		}
		
		return b.toString();
	}
	
}
