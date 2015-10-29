package main.java.model.pricing;

public class ReturnCommission extends Markup {
	
	public enum Type {
		PartOfMarkup,
		AdditionalMarkup;
		
		public static Type parseType(String rawType) {
			switch (rawType.trim().toLowerCase()) {
				case "part_of_markup":		return Type.PartOfMarkup;
				case "additional_markup":	return Type.AdditionalMarkup;
				default:					return null;
			}			
		}
		
	}
	
	private Type type;
		
	
	public ReturnCommission(double value, Type type) {
		super(value, Markup.Type.Fixed, Currency.UAH);
		this.type = type;
	}
	
	public Type commissionType() {
		return this.type;
	}
	
	
	
	
	
	
}
