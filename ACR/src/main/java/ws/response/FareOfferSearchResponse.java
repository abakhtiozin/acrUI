package main.java.ws.response;

import main.java.model.Trip;

import javax.xml.soap.SOAPMessage;

/**
 * Created by Andrii.Bakhtiozin on 30.07.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class FareOfferSearchResponse extends Response {
    private String sampleXpathWithFareOfferId;
    private String fareOfferId;

    public FareOfferSearchResponse(SOAPMessage soapResponse) {
        super(soapResponse);
    }

    public String getFareOfferId() {
        return fareOfferId;
    }

    public void findOfferIdByTrip(Trip trip) {
        String xpath = createFareOfferIdXpath(trip);
        this.fareOfferId = evaluator.getResult(xpath);
        if (this.fareOfferId == null || this.fareOfferId.equals(""))
            throw new NullPointerException("fareOfferId not found for such xpath: " + xpath);
        this.sampleXpathWithFareOfferId = "//fareOffer[fareOfferId[text()='" + this.fareOfferId + "']]";
    }

    private String createFareOfferIdXpath(Trip trip) {
        String tariff = trip.getTariffType() == null ? null : trip.getTariffType()[0];
        System.out.println("//fareOffer" + (trip.getCarriageType() != null ? "[contains(serviceType,'" + trip.getCarriageType() + "')]" : "") +
                (trip.getTicketType() != null ? "[contains(ticketOptions,'" + trip.getTicketType() + "')]" : "") +
                (tariff != null ? "[contains(serviceClass,'" + tariff + "')]" : "") +
                "[1]/fareOfferId");
        return "//fareOffer" + (trip.getCarriageType() != null ? "[contains(serviceType,'" + trip.getCarriageType() + "')]" : "") +
                (trip.getTicketType() != null ? "[contains(ticketOptions,'" + trip.getTicketType() + "')]" : "") +
                (tariff != null ? "[contains(serviceClass,'" + tariff + "')]" : "") +
                "[1]/fareOfferId";
    }

    public String getOfferCurrency() {
        return evaluator.getResult(sampleXpathWithFareOfferId.concat("//currency"));
    }

}
