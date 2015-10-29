package db.SqlCinn;

import db.DataBase;

public class SqlLogging extends SqlCinn {

	public static byte[] selectRequestLog(String logId) {
		return DataBase.getInstance().executeQueryBytes(extendWithDBName("SELECT UNCOMPRESS(CONCAT(X'1f8b0800', srcl.request_body_compressed)) AS request_body_compressed FROM supplier_remote_call_log srcl WHERE srcl.id = " + logId + ";"), "request_body_compressed");			
	}
	
}
