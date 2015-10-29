package main.java.model;

import java.util.HashMap;

import main.java.model.pricing.Price;
import db.SqlCinn.SqlSearchCache;

public class CacheItem {
	
	protected static Supplier getSupplier(long searchCacheId) {
		return Supplier.findById(Integer.parseInt(SqlSearchCache.getCacheItemSupplierId(searchCacheId)));
	}
	
	protected static Price getOrignalPrice(long searchCacheId) {
		HashMap<String, String> originalPrice = SqlSearchCache.getCacheItemOriginalPrice(searchCacheId);
		return new Price(originalPrice.get("original_price"), originalPrice.get("original_currency_code"));
	}
	
}
