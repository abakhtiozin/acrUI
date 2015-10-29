package db.SqlCinn;

import db.DataBase;

public class SqlReseller extends SqlCinn {

	//private static int i = 8;
	public static int updateResellerBlockedSystems(String blockedSystems, String resellerCode) {
		String sqlUpd = "UPDATE " + DataBase.getDbName() + ".reseller r " +  
						" SET r.blockedsystems = '" + blockedSystems + "' " +
						"WHERE r.resellerCode = '" + resellerCode + "';";
		
		return DataBase.getInstance().executeUpdate(sqlUpd);
	}
	
	public static String getResellerId(String resellerCode) {
		return DataBase.getInstance().executeQuery("SELECT id FROM " + DataBase.getDbName() + ".reseller WHERE resellerCode = '" + resellerCode + "';", "id");
	}
	public static String getResellerUserId(String resellerCode, String userName) {
		return DataBase.getInstance().executeQuery("SELECT id FROM " + DataBase.getDbName() + ".reseller_user WHERE idParent = (SELECT id FROM " + DataBase.getDbName() + ".reseller WHERE resellerCode = '" + resellerCode + "') AND username = '" + userName + "';", "id");
	}
	public static String getResellerMerchantId(String resellerCode) {
		return DataBase.getInstance().executeQuery("SELECT merchant_id FROM " + DataBase.getDbName() + ".reseller WHERE resellerCode = '" + resellerCode + "';", "merchant_id");
	}
	
	public static String getLatestResellerReservationId(String resellerCode) {
		return DataBase.getInstance().executeQuery("SELECT MAX(rrr.id) AS maxId FROM " + DataBase.getDbName() + ".reseller_reservations_reservations rrr WHERE rrr.idReseller = " + DataBase.getDbName() + ".reseller_id('" + resellerCode + "');", "maxId");
	}
	
	public static String getResellerCountryIso2(String resellerCode) {
		return DataBase.getInstance().executeQuery("SELECT country FROM " + DataBase.getDbName() + ".reseller WHERE resellerCode = '" + resellerCode + "';", "country");
	}
	
	public static String getResellerEarningType(String resellerCode) {
		return DataBase.getInstance().executeQuery(SqlCinn.extendWithDBName("SELECT r.earning_type FROM reseller r WHERE r.resellerCode = '" + resellerCode + "';"), "earning_type");
	}
	
		
	public static String getPartnerExchRateType(String partnerId) {
		return DataBase.getInstance().executeQuery("SELECT pp.exchange_rate_type FROM " + DataBase.getDbName() + ".partner_preference pp WHERE pp.partner_id = " + partnerId + ";", "exchange_rate_type");
	}
	
	/**
	 * The method deletes records from search_cahe table according to the passed conditions
	 * @param resellerUserId	(String) Reseller User ID for which records should be deleted
	 * @param dates				(String) Dates pair (checkIn, checkOut) for which records should be deleted. Dates should be separated by comma. Only an even number of dates accepted
	 * @return int SQL executeUpdate result code
	 * @throws IllegalArgumentException
	 * @author Alexander.Isko (alexander.isko@viaamadeus.com)
	 */
	public static int deleteRecordsFromSearchCache(String resellerUserId, String... dates) {
		String sql = "DELETE FROM search_cache WHERE search_process_id IN (SELECT sp.id FROM search_process sp WHERE sp.user_id = " + resellerUserId;
		if (dates.length > 0) {
			if (dates.length % 2 == 0) {
				sql += " AND (";
				boolean firstSubStmt = true;
				for (int i = 0; i < dates.length - 1; i += 2) {
					if (!firstSubStmt) sql += " OR "; firstSubStmt = false;
					sql += "(sp.check_in = '" + dates[i] + "' AND sp.check_out = '" + dates[i+1] + "')";					
				}
				sql += ")";
			}
			else {
				throw new IllegalArgumentException("Incorrect number of dates arguments. Only an even number of dates accepted");
			}
		}
		sql += ");";
		//System.out.println(sql);
		
		return DataBase.getInstance().executeUpdate(extendWithDBName(sql));		
	}
	
}
