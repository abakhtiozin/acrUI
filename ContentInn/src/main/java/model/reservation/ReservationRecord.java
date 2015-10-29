package main.java.model.reservation;

import db.SqlCinn.SqlReservations;

public class ReservationRecord {
	
	public static ReservationStatus getStatus(long reservationId) {
		String status = SqlReservations.getReservationStatus(reservationId);
		return ReservationStatus.find(status);
	}
	
	
	public static String getConfirmationNumber(long reservationId) {
		return SqlReservations.getReservationExternalId(reservationId);
	}
	
	
	
	
}
