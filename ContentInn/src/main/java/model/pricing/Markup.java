package main.java.model.pricing;

/**
 * This class represents markups in the general meaning, i.e. Markup itself and others (like Payment Gateway Commission or PayBack Commission or Agency Commission).
 * To differentiate they Markup.Type is used.
 * @author Alexander Isko
 * 
 */
public class Markup implements Cloneable {
	
	public enum Type {
		Fixed,
		Percentage,
		/** This type is used for such markups such as Payment Gateway Commission or PayBack Commission */
		Proportional	
		;
		
		public static Type parseType(String rawType) {
			switch (rawType.trim().toLowerCase()) {
				case "amount":
				case "fixed":
					return Type.Fixed;
				case "%":
				case "percent":
					return Type.Percentage;
				default:
					return null;
			}			
		}		
	}	
	
	
	protected double value;
	protected Type type;
	protected Currency currency;
	
	
	public Markup(double value, Type type) {
		this.value = value;
		if (type.equals(Type.Fixed)) throw new IllegalArgumentException("Could not initialize Markup: if not a Type.Percent argument was passed the Currency must be provided; ");
		else this.type = type;
	}	
	public Markup(double value, Type type, Currency currency) {
		this.value = value;
		this.type = type;
		this.currency = currency;
	}
	
	
	public double value() {
		return value;
	}
	public Type type() {
		return type;
	}
	public Currency currency() {
		return currency;
	}
	
	
	public Markup addMarkup(Markup... markups) {
		if (null != markups && markups.length > 0) {
			for (Markup markup : markups) {
				if (null != markup) {
					if (	(this.type.equals(Type.Percentage) && markup.type().equals(Type.Percentage))
						||	(this.type.equals(Type.Proportional) && markup.type().equals(Type.Proportional))
						||	(this.type.equals(Type.Fixed) && markup.type().equals(Type.Fixed) && this.currency.equals(markup.currency()))) {		
						this.value += markup.value();
					}
					else {
						throw new IllegalArgumentException("Markup.addMarkup(): Cannot summarize markups: Types and currencies must be equal; ");
					}
				}
			}				
		}
		return this;
	}		
	
	
	
	@Override
	public String toString() {
		return Double.toString(value) + (type == Type.Fixed ? currency : "") + "(" + type + ")";
	}
	
	@Override
	public Markup clone() {		
		try {
			return (Markup) super.clone();
		} catch (CloneNotSupportedException  e) {
			e.printStackTrace();
			throw new RuntimeException("Cannot clone the object. Clonning isn't supported; ");			
		}		
	}
	
	
}
