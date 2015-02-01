package model;

/**
 * Created by AA on 10.01.2015.
 */
public class Trip {

    private String transporterName;
    private String tripType;
    private String trainNumber;
    private String departTime;
    private String arrivalTime;
    private String duration;
    private String minAmount;
    private String tariffType;

    public String getTariffType() {
        return tariffType;
    }

    public String getCarriageType() {
        return carriageType;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public String getTransporterName() {
        return transporterName;
    }

    private String carriageType;


    public Trip withTransporterName(String transporterName) {
        this.transporterName = transporterName;
        return this;
    }

    public Trip withTariffType(String tariffType) {
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

    @Override
    public String toString() {
        return "Trip [transporter name=" + transporterName + ", train number=" + trainNumber + ", departure time=" + departTime + ", min amount=" + minAmount + "]";
    }


    public Trip withDepartTime(String departTime) {
        this.departTime = departTime;
        return this;
    }

    public Trip withTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
        return this;
    }

    public Trip withArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
        return this;
    }

    public Trip withDuration(String duration) {
        this.duration = duration;
        return this;
    }

    public Trip withMinAmaunt(String minAmaunt) {
        this.minAmount = minAmaunt;
        return this;
    }


}
