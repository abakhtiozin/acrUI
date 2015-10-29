package db.SqlCinn;

import java.util.HashMap;
import java.util.List;

import db.DataBase;

public class SqlSupplier extends SqlCinn {
	
	public static String getSupplierTbsName(int supplierId) {
		String sName = DataBase.getInstance()
							   .executeQuery("SELECT tbs_name FROM " + DataBase.getDbName() + ".supplier WHERE id = " + Integer.toString(supplierId) + ";", "tbs_name");
		
		assert (null != sName);
		return sName;
	}	
	
	public static List<HashMap<String, String>> getAllSuppliersTbsName() {
		return DataBase.getInstance().getList("SELECT tbs_name FROM " + DataBase.getDbName() + ".supplier;");		
	}
	
	public static String getOriginalSupplierId(int supplierId) {
		String sId = DataBase.getInstance()
				   .executeQuery(SqlCinn.extendWithDBName("SELECT s.original_supplier_id FROM supplier s WHERE s.id = " + supplierId + ";"), "original_supplier_id");
		return sId;
	}
	
	public static String getIsSubSupplierValue(int supplierId) {
		return DataBase.getInstance().executeQuery(SqlCinn.extendWithDBName("SELECT s.is_sub_supplier FROM supplier s WHERE s.id = " + supplierId + ";"), "is_sub_supplier");
	}
	
}
