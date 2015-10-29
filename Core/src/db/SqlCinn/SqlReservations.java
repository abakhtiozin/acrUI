package db.SqlCinn;

import db.DataBase;

public class SqlReservations extends SqlCinn {
	
	public static String getReservationStatus(long reservationId) {
		return DataBase.getInstance().executeQuery(SqlCinn.extendWithDBName("SELECT rrr.status FROM reseller_reservations_reservations rrr WHERE id = " + reservationId + ";"), "status");			
	}
	
	public static String getReservationExternalId(long reservationId) {
		return DataBase.getInstance().executeQuery(SqlCinn.extendWithDBName("SELECT rrr.idExternalReservation FROM reseller_reservations_reservations rrr WHERE id = " + reservationId + ";"), "idExternalReservation");			
	}
	
	
}
