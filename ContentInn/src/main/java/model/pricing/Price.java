package main.java.model.pricing;

import main.java.model.Reseller;

public class Price implements Cloneable{
	private double amount;
	private Currency currency;
	
	
	public Price(double amount, Currency currency) {
		this.amount = amount;
		this.currency = currency;
	}
	public Price(String amount, String isoCode) {		
		this(
			Double.parseDouble(amount.trim().replace(",", ".").replace(" ", "")),
			Currency.find(isoCode)
		);
	}	
	
	public double amount() {
		return this.amount;
	}
	public double amount(Currency currency, Reseller reseller) {
		return amount() * currency.getRate(currency(), reseller);
	}
	public Currency currency() {
		return this.currency;
	}	
	public double roundedAmount(int scale) {		
		return Math.round(this.amount * Math.pow(10, scale)) / Math.pow(10, scale);		
	}
	
	/**
	 * The method converts the price to the price in another currency
	 * @param currency Currency to which price has to be converted
	 * @param reseller Reseller for whom currency rate will be defined
	 * @return Price with converted values
	 */	
	public Price convert(Currency currency, Reseller reseller) {
		if (!this.currency.equals(currency)) {
			this.amount = amount(currency, reseller);
			this.currency = currency;
		}
		return this;
	}
	public Price round(int scale) {
		this.amount = roundedAmount(scale);
		return this;
	}
	
	
	/**
	 * The method adds to the current price an additional prices with the same currency as current one  
	 * @param price Price which has to be added to the current price
	 * @return Current price with the added amount 
	 */
	
	public Price addPrice(Price... prices) {
		if (null != prices && 0 < prices.length) {
			for (Price price : prices) {
				if (null != price) {
					if (this.currency.equals(price.currency())) {
						this.amount += price.amount();
					}
					else {
						throw new IllegalArgumentException("Can't summarize prices: Currencies must be equal for both prices (" + this.currency + " isn't equal to the " + price.currency());
					}
				}
			}
		}
		return this;
	}
	
	
	
	/**
	 * The method parses raw string with amount and currency ISO-code and returns Price
	 * @param raw	String that contains data peer amount and currency (e.q., 123.45USD or 1000,05 EUR)
	 * @return Price
	 * @author Alexander.Isko@viaamadeus.com
	 */	
	public static Price parse(String raw) {
		Price price = null;
		double amount = 0;
		Currency currency = null;		
		
		// 0. toUpperCase all symbols
		raw = raw.toUpperCase();
		
		// 1. Shrink the string to one line. It is supposed that amount and currency will be found in the first line
		while (raw.lastIndexOf("\n") > 0) {
			raw = raw.substring(0, raw.lastIndexOf("\n"));			
		}
		
		// 2. Remove all possible not required symbols and convert to the expected format
		String[][] rp = {
			{",",	"."},	{"\n",	""},	{"\r",	""},	{"\t",	""},
			{" ",	""},	{"(",	""},	{")",	""},	{"|",	""},
		};		
		for (String[] s : rp) {	raw = raw.replace(s[0], s[1]);	}
		
		// 3. Try to get currency from the string
		int idxDivider = Integer.MIN_VALUE;
		for(Currency c : Currency.values()) {
			idxDivider = raw.indexOf(c.name());
			if (idxDivider >= 0) {
				currency = c;
				amount = idxDivider > 0 ? Double.parseDouble(raw.substring(0, idxDivider).trim()) : Double.parseDouble(raw.substring(idxDivider + currency.name().length()).trim());
				price = new Price(amount, currency);
				break;			
			}
			else continue;
		}
		if (price == null) throw new IllegalArgumentException("Couldn't parse price. Input string has unresolvable format.");
				
		return price;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Price) {
			return this.amount == ((Price) obj).amount() && this.currency.equals(((Price) obj).currency()); 
		}
		return false;
	}
	
	public boolean equals(Object obj, double delta) {
		if (obj instanceof Price) {
			return Math.abs((this.amount - ((Price) obj).amount())) <= delta && this.currency.equals(((Price) obj).currency()); 
		}
		return false;
	}
	
	
	
	@Override
	public String toString() {
		return Double.toString(amount()) + "(" + currency().name() + ")";
	}
	@Override
	public Price clone() {		
		try {
			return (Price) super.clone();
		} catch (CloneNotSupportedException  e) {
			e.printStackTrace();
			throw new RuntimeException("Cannot clone the object. Clonning isn't supported; ");			
		}		
	}
	
}
