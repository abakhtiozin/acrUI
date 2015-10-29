package main.java.model.pricing;

import java.util.HashMap;

import db.SqlCinn.SqlPricing;

public class ResellerCommission extends Markup {
	private long id;
	private String name;
	
	public enum Type {
		content, webservice, whitelabel;
	}
	
	
	
	
	public ResellerCommission(long id, String name, double value) {
		super(value, Markup.Type.Percentage);
		this.id = id;
		this.name = name;
	}
	
	public long id() {
		return this.id;
	}
	public String name() {
		return this.name;
	}
	
	public boolean isDefault() {
		return SqlPricing.getResellerOwnCommissionIsDefault(id).equals("1");
	}
	
	public static ResellerCommission findById(long id) {
		HashMap<String, String> cl = SqlPricing.getResellerOwnCommissionById(id);
		return new ResellerCommission(id, cl.get("name"), Double.parseDouble(cl.get("value")));		
	}
	
	
	
	
	@Override
	public String toString() {
		return "'" + name + "':" + super.toString();
	}
	
}
