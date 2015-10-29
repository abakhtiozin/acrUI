package main.java.model;


import db.SqlCinn.SqlSupplier;
import db.SqlCinn.SqlPricing;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import main.java.model.pricing.Markup;



public enum Supplier {
	Acase				(1,		"Academservice"	),
	Avialine			(64,	"Avialine"		),
	ContentHellas		(184,	"Content Hellas"),
	ContentLink			(196,	"ContentLink"	),
	Dotw				(11,	"DOTW"			),
	ElegantTravel		(6,		"Elegant travel"),
	Expedia				(220,	"Expedia"		),
	ExpediaOpaque		(244,	"Expedia Opaque"),
	GoGlobal			(2,		"GoGlobal"		),
	GoGlobalAustralia	(32,	"GoGlobal Australia"),
	GoGlobalGreece		(16,	"GoGlobal Greece"	),
	GoGlobalIsrael		(26,	"GoGlobal Israel"	),
	GoGlobalRussia		(28,	"GoGlobal Russia"	),
	GoGlobalTurkey		(30,	"GoGlobal Turkey"	),
	Gta					(18,	"GTA"			),
	GtaCache			(204,	"Gta Cache"		),
	HotelBeds			(192,	"HotelBeds"		),
	Ip					(228,	"InstantPayment"),
	JacTravel			(216,	"Jac Travel"	),
	Netmedia			(72,	"Netmedia"		),
	Restel				(17,	"Restel"		), 
	RoomsXml			(52,	"RoomsXml"		),
	TotalStay			(248,	"TotalStay"		),
	TotalStayQuick		(254,	"TotalStayQuick"),
	Tourico				(3,		"Tourico"		),
	TouricoTurkey		(210,	"Tourico Turkey"),
	Transhotel			(4,		"Transhotel"	),
	Travco				(5,		"Travco"		)
	; 
	
	private final int	 id;
	private final String label;
	
	public enum EarningType {
		Markup, ReverseCommission;
	}
	
	Supplier (int id, String label) { 
		this.id		= id;
		this.label	= label;
	}
	
	public int id() {
		return this.id;
	}	
	public String label() {
		return this.label;
	}
	public String getTbsName() {
		String tbsName = "";
		tbsName = SqlSupplier.getSupplierTbsName(this.id);
		return tbsName;			
	}
	public Markup getMarkup() {
		HashMap<String, String> markupSupplier = SqlPricing.getSupplierEarning(Integer.toString(this.id), new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		return markupSupplier != null ? new Markup(Float.parseFloat(markupSupplier.get("markup")), Markup.Type.parseType(markupSupplier.get("markup_type"))) : null;
	}
	public Markup getReverseCommission() {
		HashMap<String, String> rcSupplier = SqlPricing.getSupplierEarning(Integer.toString(this.id), new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		return rcSupplier != null ? new Markup(Float.parseFloat(rcSupplier.get("reverse_commission")), Markup.Type.Percentage) : null;
	}
	public Supplier getOriginalSupplier() {
		String origSupplId = SqlSupplier.getOriginalSupplierId(id);
		return null != origSupplId ? findById(Integer.parseInt(origSupplId)) : null;		
	}
	public boolean isSubSupplier() {
		return SqlSupplier.getIsSubSupplierValue(id).equals("1");
	}
	public Supplier.EarningType getEarningType() {
		Markup rc = getReverseCommission();
		if (null != rc) {
			return rc.value() > 0 ? Supplier.EarningType.ReverseCommission : Supplier.EarningType.Markup;
		}
		return Supplier.EarningType.Markup;
	}
	public boolean hasMarkupEarning() {
		return Supplier.EarningType.Markup.equals(getEarningType());
	}
		
	
	/**
	 * Looks up the first coincidence of the supplier id
	 * @param	int supplier Id
	 * @return 	Supplier
	 */
	public static Supplier findById(int supplierId) {
		for (Supplier s : Supplier.values()) {
			if (s.id == supplierId) {
				return s;
			}
			else continue;
		}
		return null;
	}
	
}
