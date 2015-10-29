package main.java.model.reservation;

/**
 * Created by trofimenko on 18.02.2015.
 */
public enum ReservationStatus {
    OK, RQ, XX, POK, CP, RJ, PR, XR, RF;
    
    public static ReservationStatus find(String rawSatusCode) {
    	for (ReservationStatus rc : ReservationStatus.values()) {
			if (rc.name().equals(rawSatusCode.trim().toUpperCase())) return rc;
		}
    	throw new IllegalStateException("ReservationStatus.find: Can not return reservation status: input status code" + rawSatusCode + " is unknown" );
    }
    
}
