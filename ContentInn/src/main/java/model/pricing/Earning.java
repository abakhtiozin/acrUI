package main.java.model.pricing;

import java.util.HashMap;

import main.java.model.Reseller;
import main.java.model.pricing.EarningChain.Component;

public class Earning {
	
	private Reseller reseller = null;
	
	private Price inPrice = null;
	private Price outPrice = null;
	private HashMap<EarningChain.Component, Price> priceComponents = null;	
	
	
	public Earning() {		
	}
	
	public Earning(Price inPrice) {
		setBasePrice(inPrice);
	}
	
	public Price basePrice() {
		return inPrice;
	}
	public Reseller reseller() {
		return reseller;
	}
	
	
	
	
	
	public Earning setBasePrice(Price price) {
		inPrice = price;
		return this;
	}
	public Earning forReseller(Reseller reseller) {
		this.reseller = reseller;		
		return this;
	}	
	
	
	public Earning applyMarkup(Markup... markups) {
		for (Markup markup : markups) {
			inPrice.addPrice(calculateMarkup(markup));
		}
		return this;
	}
	
	public Earning addPrice(Price... prices) {
		inPrice.addPrice(prices);
		return this;
	}
	
	
	
	/**
	 * @see Earning.calculateMarkup(Markup markup, Markup... markups)
	 * @param markup Markup which represents percentage rate or fixed value to calculate price 
	 * @return Price which is calculated based on the input price and the markup
	 */
	public Price calculateMarkup(Markup markup) {
		return calculateMarkup(markup, new Markup[] {});
	}
	/**
	 * The method calculates price based on the input price and markup rate.
	 * Also the method is able to calculate proportional values (like, Payment Gateway Commission, PayBack Commission)
	 * @param markups Markups which represent percentage rate or fixed value to calculate price 
	 * @return Price which is calculated based on the input price and the markups
	 */	
	public Price calculateMarkup(Markup markup, Markup... markups) {
		if (null != inPrice) {
			if (null != markup && markup.type().equals(Markup.Type.Fixed)) {
				return new Price(markup.value(), markup.currency()).convert(inPrice.currency(), reseller);
			}
			else if (null != markup && markup.type().equals(Markup.Type.Percentage)) {
				return new Price(inPrice.amount() * markup.value() / 100, inPrice.currency());
			}
			else if (null != markup && markup.type().equals(Markup.Type.Proportional)) {
				double calcAmount = (markup.value() / 100) * (inPrice.amount() / (1 - markup.clone().addMarkup(markups).value() / 100 ));
				return new Price(calcAmount, inPrice.currency());			
			}
		}
		else {
			throw new IllegalStateException("Can't calculate markup: The base price isn't defined; ");			
		}
		return null;
	}
	
	public Earning applyChain(EarningChain chain) {
		
		//for (HashMap<Component, Markup> components : chain) {
		//	for (EarningChain.Component k : components.keySet()) {
		//		priceComponents.put(k, components.)
		//	}
			
			
			
		//}		
		
		return this;
	}
	
	

	
	
	
}
