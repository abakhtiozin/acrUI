package main.java.model;

import org.joda.time.DateTime;

/**
 * Created by Andrii.Bakhtiozin on 10.01.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class Trip {

    private String transporterName;
    private String carriageType;
    private String tripType;
    private String trainNumber;
    private DateTime departTime;
    private DateTime arrivalTime;
    private DateTime duration;
    private String minAmount;
    private String[] tariffType;
    private String ticketType;

    public String getTicketType() {
        return ticketType;
    }

    public DateTime getDepartTime() {
        return departTime;
    }

    public DateTime getArrivalTime() {
        return arrivalTime;
    }

    public DateTime getDuration() {
        return duration;
    }

    public String[] getTariffType() {
        return tariffType;
    }

    public String getCarriageType() {
        return carriageType;
    }

    public String getTransporterNumber() {
        return trainNumber;
    }

    public String getTransporterName() {
        return transporterName;
    }

    public Trip withTicketType(String ticketType) {
        this.ticketType = ticketType;
        return this;
    }


    public Trip withTransporterName(String transporterName) {
        this.transporterName = transporterName;
        return this;
    }

    public Trip withTariffType(String[] tariffType) {
        this.tariffType = tariffType;
        return this;
    }

    public Trip withCarriageType(String carriageType) {
        this.carriageType = carriageType;
        return this;
    }

    public Trip withTripType(String tripType) {
        this.tripType = tripType;
        return this;
    }

    public Trip withDepartTime(DateTime departTime) {
        this.departTime = departTime;
        return this;
    }

    public Trip withTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
        return this;
    }

    public Trip withArrivalTime(DateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
        return this;
    }

    public Trip withDuration(DateTime duration) {
        this.duration = duration;
        return this;
    }

    public Trip withMinAmaunt(String minAmaunt) {
        this.minAmount = minAmaunt;
        return this;
    }

    @Override
    public String toString() {
        if (trainNumber == null && carriageType == null) {
            return transporterName;
        } else if (carriageType != null && trainNumber == null) {
            return transporterName + ", " + carriageType;
        } else if (carriageType == null) {
            return transporterName + ", " + trainNumber;
        } else return transporterName + ", " + trainNumber + ", " + carriageType;
    }
}
