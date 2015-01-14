package model;

import java.util.Date;

/**
 * Created by AA on 10.01.2015.
 */
public class Trip {

    private String transporterName;
    private String tripType;
    private String trainNumber;
    private Date departTime;
    private Date arrivalTime;
    private Date duration;
    private double minAmaunt;


    public Trip withTransporterName(String transporterName){
        this.transporterName = transporterName;
        return this;
    }

    public Trip withTripType(String tripType){
        this.tripType = tripType;
        return this;
    }

    @Override
    public String toString() {
        return "Trip [transporter name=" + transporterName + ", train number=" + trainNumber + "]";
    }

    public Trip withDepartTime(Date departTime){
        this.departTime = departTime;
        return this;
    }
    public Trip withTrainNumber(String trainNumber){
        this.trainNumber = trainNumber;
        return this;
    }
    public Trip withArrivalTime(Date arrivalTime){
        this.arrivalTime = arrivalTime;
        return this;
    }
    public Trip withDuration(Date duration){
        this.duration = duration;
        return this;
    }
    public Trip withMinAmaunt(Double minAmaunt){
        this.minAmaunt = minAmaunt;
        return this;
    }


}
