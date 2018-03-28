package com.oag.wdf.master.constants;

public enum DataElementIdentifier {
	
	SUBJECT_TO_GOVT_APPROVAL(201),
	PLANE_CHANGE_AT_BOARD_POINT_WITHOUT_AIRCRAFT_TYPE_CHANGE(210),
	INTERNATIONAL_DOMESTIC_STATUS_OVERRIDE(220),
	IN_FLGHT_SERVICE_INFORMATION(503),
	SECURE_FLGHT_INFORMATION(504),
	ELECTRONIC_TICKETING_INFORMATION(505),
	AUTOMATED_CHECK_IN_INFORMATION(506),
	CODE_SHARING_COMMERCIAL_DUPLICATE(2), 
	AIRCRAFT_OWNER(3),
	AIRCRAFT_OWNER_SPECIFICATION(113),
	COCKPIT_CREW_EMPLOYER(4),
	COCKPIT_CREW_EMPLOYER_SPECIFICATION(114),
	CABIN_CREW_EMPLOYER(5),
	CABIN_CREW_EMPLOYER_SPECIFICATION(115),
	CODE_SHARING_OPERATING_AIRLINE_DISCLOSURE(127);

	private int code;
	
	
	DataElementIdentifier(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public String getCodeStr() {
		return String.valueOf(code);
	}
	
	

}
