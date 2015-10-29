package main.java.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import db.SqlCinn.SqlPricing;
import db.SqlCinn.SqlSupplier;
import db.SqlCinn.SqlReseller;
import main.java.model.pricing.Markup;
import main.java.model.pricing.ResellerCommission;
import main.java.model.pricing.ReturnCommission;
import main.java.model.searchParameters.Period;


public class Reseller {
	
	public enum EarningType {
		Markup,
		PayBack
		;
		
		private static EarningType parseType(String rawType) {
			switch (rawType.trim().toLowerCase()) {
				case "markup":		return EarningType.Markup;
				case "pay_back":	return EarningType.PayBack;
				default:			return null;
			}			
		}		
	}
	
	private String code;
	private String userName;
	private String password;
	private Language language;
	private Reseller partner;
	
	public Reseller(String resellerCode, String userName, String password, Language language) {
        this(resellerCode, userName, password);
        this.language = language;
    }
	
	public Reseller(String resellerCode, String userName, String password) {
        this.code = resellerCode;
        this.userName = userName;
        this.password = password;
    }	
	
	public String code() {
        return code;
    }
    public String userName() {
        return userName;
    }
    public String password() {
        return password;
    }
    public Language language() {
        return language;
    }
    public Reseller partner() {
		return partner;
	}
    public long getId() {
		return Long.parseUnsignedLong(SqlReseller.getResellerId(this.code));
	}
    public int getMerchantId() {
    	return Integer.parseUnsignedInt(SqlReseller.getResellerMerchantId(this.code));    	
	}
    public long getLatestReservationId() {
    	String rid = SqlReseller.getLatestResellerReservationId(this.code);
    	return Long.parseUnsignedLong(rid);
	}
    public Country getCountry() {
    	String cIso2 = SqlReseller.getResellerCountryIso2(this.code);
    	return Country.findByIso2Code(cIso2);
    }    
    
    
    public Markup getMarkup(Supplier supplier) {
    	HashMap<String, String> markupReseller = SqlPricing.getResellerMarkup(Long.toString(getId()), supplier.getTbsName());    	
    	Markup actualMarkup = markupReseller == null ? getCountry().getMarketMarkup(supplier) : new Markup(Float.parseFloat(markupReseller.get("value")), Markup.Type.parseType(markupReseller.get("type")));
    	return actualMarkup;
    }
    public EarningType getEarningType() {
		return EarningType.parseType(SqlReseller.getResellerEarningType(code));
	}    
    public Markup getPayBackCommission() {
    	if (EarningType.PayBack.equals(getEarningType())) {
    		return new Markup(Float.parseFloat(SqlPricing.getResellerPayBackCommissionValue(Long.toString(getId()))), Markup.Type.Proportional);
    	}    	
		return null;
	}
    public Markup getPayBackCommissionAgent() {
    	if (EarningType.PayBack.equals(getEarningType())) {
    		return new Markup(Float.parseFloat(SqlPricing.getResellerPayBackCommissionAgentValue(Long.toString(getId()))), Markup.Type.Proportional);
    	}    	
		return null;
	}
    public List<ResellerCommission> getOwnCommissionList(ResellerCommission.Type type) {
    	List<ResellerCommission> resCommissions = new ArrayList<ResellerCommission>();
    	for (HashMap<String, String> cRow : SqlPricing.getResellerOwnCommission(Long.toString(getId()), type.name())) {
			resCommissions.add(new ResellerCommission(Long.parseLong(cRow.get("id")), cRow.get("name"), Double.parseDouble(cRow.get("value"))));
		}
    	return resCommissions;
	}
    public ResellerCommission getDefaultOwnCommission(ResellerCommission.Type type) { 
    	for (ResellerCommission c : getOwnCommissionList(type)) {
			if (c.isDefault()) return c;
		}
    	return null;
	}
    public ReturnCommission getReturnCommission() {
    	HashMap<String, String> retComm = SqlPricing.getReturnCommission(Long.toString(getId()));
    	return new ReturnCommission(Double.parseDouble(retComm.get("amount")), ReturnCommission.Type.parseType(retComm.get("return_commission_type")));
	}
    
    
    
    public Reseller setPartner(Reseller partner) {
		this.partner = partner;
		return this;
	}	
    /**
     * Sets all suppliers blocked except supplier from the input parameter
     * @author Alexander Isko (alexander.isko@viaamadeus.com)
     * @param supplier The supplier which have to be unblocked
     * @return
     */
    @Deprecated
    public Reseller setUnblockedSupplier(Supplier supplier) {
    	Supplier[] suppliers = {supplier};
    	return setUnblockedSuppliers(suppliers);
    }    
    /**
     * Sets all suppliers blocked except suppliers from the input parameter
     * @author Alexander Isko (alexander.isko@viaamadeus.com)
     * @param suppliers The list of suppliers which have to be unblocked
     * @return
     */
    public Reseller setUnblockedSuppliers(Supplier... suppliers) {
    	String strBlockedSystems = "";		// example: academservice|a2btransfers|railway|restel|goglobalisrael|goglobalrussia|goglobalturkey|goglobalaustralia|e-bilet.ua|roomsXml|Unit|netmedia|ufs|acp|contentlinknew|tf|gtacache|touricoturkey|dbaws|ip
		List<HashMap<String, String>> allSupplTbsNames = SqlSupplier.getAllSuppliersTbsName();
    	
		/**	To minimize DB connections, we collect tbs_name for each supplier from the input parameter once	*/
		String[] supplTbsNames = new String[suppliers.length];
    	for (int i = 0; i < suppliers.length; i++) {
			supplTbsNames[i] = suppliers[i].getTbsName();
		}
		
		for (HashMap<String, String> row : allSupplTbsNames) {
			boolean isFound = false;
			for (String sTbsName : supplTbsNames) {
				if (row.get("tbs_name").toLowerCase().equals(sTbsName.toLowerCase())) {
					isFound = true;
					break;
				}
			}
			if (!isFound) strBlockedSystems += (row.get("tbs_name") + "|");    		
		}
    	SqlReseller.updateResellerBlockedSystems(strBlockedSystems, this.code);
    	return this;
	}    
    public int clearOwnSearchCache(Period period) {
    	String userId = SqlReseller.getResellerUserId(this.code, this.userName);
    	return SqlReseller.deleteRecordsFromSearchCache(userId, period.checkInDate(), period.checkOutDate());
	}
    
    
    
    @Override
    public String toString() {
    	return this.userName + "@" + this.code.toUpperCase();
    }
    
    
	
}
