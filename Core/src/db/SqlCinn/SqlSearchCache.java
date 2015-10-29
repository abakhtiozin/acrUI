package db.SqlCinn;

import java.util.HashMap;

import db.DataBase;

public class SqlSearchCache extends SqlCinn {

	public static String getCacheItemSupplierId(long searchCacheId) {
		return DataBase.getInstance().executeQuery(SqlCinn.extendWithDBName("SELECT sp.supplier_id FROM search_cache sc INNER JOIN search_process sp ON sc.search_process_id = sp.id WHERE sc.id = " + searchCacheId + ";"), "supplier_id");		
	}
	
	public static HashMap<String, String> getCacheItemOriginalPrice(long searchCacheId) {
		return DataBase.getInstance().getHashMap(SqlCinn.extendWithDBName("SELECT sc.original_price, sc.original_currency_code FROM search_cache sc INNER JOIN search_process sp ON sc.search_process_id = sp.id WHERE sc.id = " + searchCacheId + ";"));		
	}
	
	
}
