package db.SqlCinn;

import java.util.HashMap;
import java.util.List;

import db.DataBase;

public class SqlPricing extends SqlCinn {
/*
	public static String getResellerMarkup(String resellerId, String supplierTbsName) {
		return DataBase.getInstance().executeQuery(SqlCinn.extendWithDBName("SELECT rm.value FROM reseller_markup rm WHERE rm.class = 'markup' AND rm.idReseller = " + resellerId + " AND rm.system = '" + supplierTbsName + "';"), "value");		
	}

	public static String getMarketMarkup(String countryIso2Code, String supplierId) {
		return DataBase.getInstance().executeQuery(SqlCinn.extendWithDBName("SELECT imm.value FROM internal_market_markup imm WHERE imm.market_id = '" + countryIso2Code + "' AND imm.provider_id = " + supplierId + ";"), "value");		
	}

	public static String getSupplierMarkup(String supplierId, String onDate) {
		return DataBase.getInstance().executeQuery(SqlCinn.extendWithDBName("SELECT sp.markup FROM supplier_pricing sp WHERE sp.supplier_id = " + supplierId + " AND sp.date <= '" + onDate + "' AND sp.deleted = 0 AND sp.activated = 1 ORDER BY sp.date DESC LIMIT 1;"), "markup"); 
	}
*/	
	public static HashMap<String, String> getResellerMarkup(String resellerId, String supplierTbsName) {
		return DataBase.getInstance().getHashMap(SqlCinn.extendWithDBName("SELECT rm.value, rm.type FROM reseller_markup rm WHERE rm.class = 'markup' AND rm.idReseller = " + resellerId + " AND rm.system = '" + supplierTbsName + "';"));		
	}	
	public static String getResellerPayBackCommissionValue(String resellerId) {
		return DataBase.getInstance().executeQuery(SqlCinn.extendWithDBName("SELECT r.pay_back_commission FROM reseller r WHERE r.id = " + resellerId + ";"), "pay_back_commission");
	}
	public static String getResellerPayBackCommissionAgentValue(String resellerId) {
		return DataBase.getInstance().executeQuery(SqlCinn.extendWithDBName("SELECT r.pay_back_commission_agent FROM reseller r WHERE r.id = " + resellerId + ";"), "pay_back_commission_agent");
	}
	/* Reseller Own Commission */
	public static List<HashMap<String, String>> getResellerOwnCommission(String resellerId, String commissionType) {
		return DataBase.getInstance().getList(SqlCinn.extendWithDBName("SELECT roc.id, roc.name, roc.value FROM reseller_own_commission roc WHERE roc.reseller_id = " + resellerId + " AND roc.type = '" + commissionType + "';"));
	}
	public static String getResellerOwnCommissionIsDefault(long commissionId) {
		return DataBase.getInstance().executeQuery(SqlCinn.extendWithDBName("SELECT roc.is_default FROM reseller_own_commission roc WHERE roc.id = " + commissionId + ";"), "is_default");
	}
	public static HashMap<String, String> getResellerOwnCommissionById(long commissionId) {
		return DataBase.getInstance().getHashMap(SqlCinn.extendWithDBName("SELECT roc.id, roc.name, roc.value, roc.type FROM reseller_own_commission roc WHERE roc.id = " + commissionId + ";"));
	}
	
	/* Return Commission */
	public static HashMap<String, String> getReturnCommission(String resellerId) {
		return DataBase.getInstance().getHashMap(SqlCinn.extendWithDBName("SELECT m.id, m.type, m.percent, m.amount, m.currency, rc.return_commission_type FROM reseller_contract rc INNER JOIN markup m ON rc.return_commission_markup_id = m.id INNER JOIN contract c ON rc.contract_id = c.id WHERE c.type = 'content' AND rc.reseller_id = " + resellerId + ";"));
	}
	
	

	public static HashMap<String, String> getMarketMarkup(String countryIso2Code, String supplierId) {
		return DataBase.getInstance().getHashMap(SqlCinn.extendWithDBName("SELECT imm.value, imm.type FROM internal_market_markup imm WHERE imm.market_id = '" + countryIso2Code + "' AND imm.provider_id = " + supplierId + ";"));		
	}
	
	public static HashMap<String, String> getSupplierEarning(String supplierId, String onDate) {
		return DataBase.getInstance().getHashMap(SqlCinn.extendWithDBName("SELECT sp.reverse_commission, sp.markup, sp.markup_type FROM supplier_pricing sp WHERE sp.supplier_id = " + supplierId + " AND sp.date <= '" + onDate + "' AND sp.deleted = 0 AND sp.activated = 1 ORDER BY sp.date DESC LIMIT 1;")); 
	}

	public static String getCurrencyRate(String merchantId, String partnerId, String type, String date, String currency) {
		//i++;
		String sql = String.join(" ", 
				"SELECT cr.value FROM " + DataBase.getDbName() + ".currency_rate_group crg LEFT JOIN " + DataBase.getDbName() + ".currency_rate cr ON crg.id = cr.currency_rate_group_id",
				"	WHERE crg.merchant_id " + ("NULL".equals(merchantId.toUpperCase()) ? "IS " : "= ") + merchantId,
				"		AND crg.partner_id = " + partnerId,
				"		AND crg.type = '" + type + "'",
				"		AND cr.currency = '" + currency + "'",
				"		AND crg.date = (",
				"			SELECT MAX(crg1.date) FROM " + DataBase.getDbName() + ".currency_rate_group crg1",
				"				WHERE crg1.merchant_id " + ("NULL".equals(merchantId.toUpperCase()) ? "IS " : "= ") + merchantId,
				"					AND crg1.partner_id = " + partnerId,
				"					AND crg1.type = '" + type + "'",
				"					AND crg1.date <= '" + date + "'",
				"		)",
				"	ORDER BY crg.created_at DESC, crg.id DESC",
				"	LIMIT 1",
				";");		
		
		//System.out.println(i + ":\t" +  sql);
		
		return DataBase.getInstance().executeQuery(sql, "value");				
	}
	
	public static String getDestinationMarkupValue(long supplierId, long geoCityId, long geoCountryId, long marketId) {
		String sql = String.join(" ",
				"SELECT *",
				"FROM destination_markup",
				"WHERE destination_markup.is_active=true",

				"AND destination_markup.supplier_id = " + supplierId,
  
				"  AND (",
				"    destination_markup.active_date_from <= NOW()",
				"    AND destination_markup.active_date_to >= NOW()",
				"  )",
  
				"  AND (",
				"    (destination_markup.geo_city_id = " + geoCityId,
				"      OR destination_markup.geo_city_id IS NULL)",
    
				"    AND (destination_markup.geo_country_id = " + geoCountryId,
				"      OR destination_markup.geo_country_id IS NULL)",
    
				"    AND (destination_markup.market_id = " + marketId,
				"      OR destination_markup.market_id IS NULL)",
				"  )",
  
				"  ORDER BY",
				"    destination_markup.geo_city_id DESC,",
				"    destination_markup.geo_country_id DESC,",
				"    destination_markup.market_id DESC,",
				"    destination_markup.id DESC",
				"  LIMIT 1",
				";");
		
		
		return DataBase.getInstance().executeQuery(SqlCinn.extendWithDBName(sql), "percent");
	}
	
	

}
