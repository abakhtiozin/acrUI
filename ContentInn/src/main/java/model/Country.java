package main.java.model;

import java.util.HashMap;

import main.java.model.pricing.Markup;
import db.SqlCinn.SqlCountry;
import db.SqlCinn.SqlPricing;

public enum Country {
	
	AFGHANISTAN			("AF", "Afganistan"),	
	ALGERIA				("DZ", "Algeria"),
	AMERICAN_SAMOA		("AS", "American Samoa"),
	ANDORRA				("AD", "Andorra"),	
	ANGOLA				("AO", "Angola"),
	ARGENTINA			("AR", "Argentina"),
	AUSTRALIA			("AU", "Australia"),
	BELARUS				("BY", "Belarus"),
	BELGIUM				("BE", "Belgium"),
	BOLIVIA				("BO", "Bolivia"),
	CAMEROON			("CM", "Cameroon"),
	CAPE_VERDE			("CV", "Cape Verde Islands"),
	CZECH_REPUBLIC		("CZ", "Czech Republic"),	
	FALKAND_ISLAND		("FK", "Falkand Island"),
	FRANCE				("FR", "France"),
	GERMANY				("DE", "Germany"),
	GREECE				("GR", "Greece"),
	ISRAEL				("IL", "Israel"),
	ITALY				("IT", "Italy"),
	IVORY_COAST			("CI", "Ivory Coast"),
	KAZAKHSTAN			("KZ", "Kazakhstan"),
	KOSOVO				("XK", "Kosovo"),
	LATVIA				("LV", "Latvia"),
	LUXEMBOURG			("LU", "Luxembourg"),
	MOLDOVA				("MD", "Moldova"),
	MONACO				("MC", "Monaco"),
	MOZAMBIQUE			("MZ", "Mozambique"),
	POLAND				("PL", "Poland"),
	ROMANIA				("RO", "Romania"),
	RUSSIA				("RU", "Russian Federation"),
	TURKEY				("TR", "Turkey"),
	UKRAINE				("UA", "Ukraine"),
	UAE					("AE", "United Arab Emirates"),
	UGANDA				("UG", "Uganda"),
	UNITED_KINGDOM		("GB", "United Kingdom"),
	USA					("US", "United States of America"),
	ZIMBABWE			("ZW", "Zimbabwe");		
	
	private final String iso2Code;
	private final String fullName;
	
	Country (String cIso, String cName) { 
		this.iso2Code	= cIso;
		this.fullName	= cName;
	}

	public String iso2Code()	{ return this.iso2Code; }
	public String fullName()	{ return this.fullName; }
	
	public Markup getMarketMarkup(Supplier supplier) {
		HashMap<String, String> markupMarket = SqlPricing.getMarketMarkup(iso2Code, Integer.toString(supplier.id()));
		return markupMarket == null ? supplier.getMarkup() : new Markup(Float.parseFloat(markupMarket.get("value")), Markup.Type.parseType(markupMarket.get("type")));
	}
	public long getId() {
		return Long.parseLong(SqlCountry.getGeoCountryId(iso2Code));
	}
	
	
	/**
	 * Looks up the first coincidence of ISO2-code
	 * @param	String Country ISO2-code
	 * @return 	Country
	 */
	public static Country findByIso2Code(String iso2) {
		Country fc = null;
		for (Country c : Country.values()) {
			if (c.iso2Code.equals(iso2.toUpperCase())) {
				fc = c;
				break;
			}
			else continue;
		}
		return fc;
	}
}