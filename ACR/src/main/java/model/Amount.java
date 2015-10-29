package main.java.model;

/**
 * Created by Andrii.Bakhtiozin on 10.09.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class Amount {
    private double transporterAmount;
    private double commissionsAmount;
    private double resellerAmount;
    private double totalAmount;

    public double getCommissionsAmount() {
        return commissionsAmount;
    }

    public void setCommissionsAmount(double commissionsAmount) {
        this.commissionsAmount = commissionsAmount;
    }

    public double getResellerAmount() {
        return resellerAmount;
    }

    public void setResellerAmount(double resellerAmount) {
        this.resellerAmount = resellerAmount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getTransporterAmount() {

        return transporterAmount;
    }

    public void setTransporterAmount(double transporterAmount) {
        this.transporterAmount = transporterAmount;
    }
}
