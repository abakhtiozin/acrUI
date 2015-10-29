package main.java.model.pricing;


import db.SqlCinn.SqlPricing;
import main.java.model.Reseller;
import main.java.model.Supplier;
import main.java.model.searchParameters.HotelLocation;

public class DestinationMarkup extends Markup {

	private Supplier supplier;
	
	public DestinationMarkup(Supplier supplier) {
		super(0, Type.Percentage);
		this.supplier = supplier;
	}
	
	public Supplier supplier() {
		return supplier;
	}
	
	
	public DestinationMarkup getActualMarkup(HotelLocation location, Reseller reseller) {
		String currDM = SqlPricing.getDestinationMarkupValue(supplier.id(), location.city().getId(), location.country().getId(), reseller.getCountry().getId());
		if (null != currDM) {
			value = Double.parseDouble(currDM);
		}		
		return this;
	}
	
	
	
}
