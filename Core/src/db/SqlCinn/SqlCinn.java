package db.SqlCinn;

import db.DataBase;


public class SqlCinn {
		
	protected static String extendWithDBName(String rawSql) {
		return rawSql.replace("FROM ", "FROM " + DataBase.getDbName() + ".").replace("JOIN ", "JOIN " + DataBase.getDbName() + ".");
	}	
	
}
