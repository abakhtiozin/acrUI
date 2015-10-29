package main.java.model;

import db.SqlCinn.SqlCity;

public enum City {
	/* UKRAINE */
	KYIV			("IEV", "Kyiv", Country.UKRAINE),
	LVIV			("LWO", "Lviv", Country.UKRAINE),
	
	/* LUXEMBOURG */
	LUXEMBOURG		("LUX", "Luxembourg", Country.LUXEMBOURG),
	
	/* FRANCE */
	PARIS			("PAR", "Paris", Country.FRANCE),
	
	/* UNITED KINGDOM */
	LONDON			("LON", "London", Country.UNITED_KINGDOM),
	
	/* UNITED STATES */
	NEW_YORK		("NYC", "New York City", Country.USA),
	WASHINGTON		("WAS", "Washington", Country.USA),
	
	/* POLAND */
	WARSAW			("WAW", "Warsaw", Country.POLAND),
	WARSAW_AIRPORT	("WAW", "Warsaw Airport", Country.POLAND),
	
	/* GERMANY */
	BERLIN			("BER", "Berlin", Country.GERMANY),
	
	/* MOLDOVA */
	CHISINAU		("KIV", "Chisinau", Country.MOLDOVA),
	
	/* ITALY */
	ROMA			("ROM", "Roma1", Country.ITALY),
	
	/* RUSSIA */
	MOSCOW			("MOW", "Moscow", Country.RUSSIA),
	
	;	
	
	private final String iataCode;
	private final String fullName;
	private final Country country;
	
	City (String iata, String name, Country country) { 
		this.iataCode	= iata;
		this.fullName	= name;
		this.country	= country;
	}

	public String iataCode()	{ return this.iataCode;	}
	public String fullName()	{ return this.fullName;	}
	public Country country()	{ return this.country;	}	
	
	public long getId() {
		return Long.parseLong(SqlCity.getGeoCityId(iataCode));		
	}
	
	
	
	/**
	 * Looks up the first coincidence of IATA code
	 * @param	String IATA-code
	 * @return 	City
	 */
	public static City findByIata(String iata) {
		City fc = null;
		for (City c : City.values()) {
			if (c.iataCode.equals(iata.toUpperCase())) {
				fc = c;
				break;
			}
			else continue;
		}
		return fc;
	}
	
}
