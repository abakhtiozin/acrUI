package db.SqlCinn;

import db.DataBase;

public class SqlCity extends SqlCinn {
	
	
	public static String getGeoCityId(String cityIataCode) {
		return DataBase.getInstance().executeQuery(SqlCinn.extendWithDBName("SELECT gc.id FROM geo_city gc WHERE gc.iata = '" + cityIataCode + "';"), "id");
	}	
	
	
	
}
