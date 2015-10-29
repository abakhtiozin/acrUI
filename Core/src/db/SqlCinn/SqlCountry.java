package db.SqlCinn;

import db.DataBase;

public class SqlCountry extends SqlCinn {
	public static String getGeoCountryId(String iso2Code) {
		return DataBase.getInstance().executeQuery(SqlCinn.extendWithDBName("SELECT gc.id FROM geo_country gc WHERE gc.iso = '" + iso2Code + "';"), "id");		
	}
}
