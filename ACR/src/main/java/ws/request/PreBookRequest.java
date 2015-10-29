package main.java.ws.request;

import main.java.core.WithPassenger;
import main.java.model.BookingPlacesSettings;
import main.java.model.TicketType;
import main.java.model.passenger.PassengerContactInfo;

import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;

/**
 * Created by Andrii.Bakhtiozin on 12.08.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public abstract class PreBookRequest extends Request implements WithPassenger {

    protected final SOAPElement bookingRequest = addBookingRequest();

    public PreBookRequest() {
        super();
        addAcrMethodName("PreBooking");
    }

    public void addFareOfferId(String fareOfferId) {
        try {
            soapBodyElement.addChildElement("fareOfferId").addTextNode(fareOfferId);
        } catch (SOAPException e) {
            e.printStackTrace();
        }
    }

    private SOAPElement addBookingRequest() {
        SOAPElement bookingRequestElement = null;
        try {
            bookingRequestElement = soapBodyElement.addChildElement("bookingRequest");
        } catch (SOAPException e) {
            e.printStackTrace();
        }
        return bookingRequestElement;
    }

    public void addTicketTypeToBookRequest(TicketType ticketType) {
        try {
            this.bookingRequest.addChildElement("ticketOption").addTextNode(ticketType.toString());
        } catch (SOAPException e) {
            e.printStackTrace();
        }
    }

    public void addGenderCoupeToBookRequest(BookingPlacesSettings.CoupeType coupeType) {
        try {
            this.bookingRequest.addChildElement("genderCoupe").addTextNode(coupeType.toString());
        } catch (SOAPException e) {
            e.printStackTrace();
        }
    }

    public void addDiapasonOfSeats(int min, int max) {
        try {
            this.bookingRequest.addChildElement("diapasonSeatMin").addTextNode(min + "");
            this.bookingRequest.addChildElement("diapasonSeatMax").addTextNode(max + "");
        } catch (SOAPException e) {
            e.printStackTrace();
        }
    }

    public void addContactInfo(PassengerContactInfo passengerContactInfo) {
        try {
            soapBodyElement.addChildElement("contactDetails");
        } catch (SOAPException e) {
            e.printStackTrace();
        }
    }

}
