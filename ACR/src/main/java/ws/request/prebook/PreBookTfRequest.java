package main.java.ws.request.prebook;

import main.java.ApplicationStorage;
import main.java.core.PreBookContactInfo;
import main.java.model.passenger.Passenger;
import main.java.model.passenger.PassengerContactInfo;
import main.java.ws.request.PreBookRequest;

import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;

/**
 * Created by Andrii.Bakhtiozin on 16.09.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class PreBookTfRequest extends PreBookRequest implements PreBookContactInfo {
    private SOAPElement contactDetails;

    @Override
    public void addPassenger(Passenger passenger) {

    }

    @Override
    public void addContactInfo(PassengerContactInfo passengerContactInfo) {
        try {
            this.contactDetails = soapBodyElement.addChildElement("contactDetails");
            setTitle("Mr");
            setName(ApplicationStorage.getInstance().getPassengers().get(0).getName());
            setSurname(ApplicationStorage.getInstance().getPassengers().get(0).getSurname());
            setFlat(passengerContactInfo.getApartment());
            setBuildingName("test");
            setBuildingNumber(passengerContactInfo.getHouse());
            setStreet(passengerContactInfo.getStreet());
            setCity(passengerContactInfo.getCity());
            setPostcode(passengerContactInfo.getIndex());
            setCountry("UA");
            setInternationalCode("00380");
            setAreaCode(passengerContactInfo.getPhoneCode());
            setNumber(passengerContactInfo.getPhoneNumber());
            setExtension("");
            setEmail(passengerContactInfo.getEmail());
        } catch (SOAPException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setTitle(String title) throws SOAPException {
        this.contactDetails.addChildElement("title").addTextNode(title);
    }

    @Override
    public void setName(String name) throws SOAPException {
        this.contactDetails.addChildElement("name").addTextNode(name);
    }

    @Override
    public void setSurname(String surname) throws SOAPException {
        this.contactDetails.addChildElement("surname").addTextNode(surname);
    }

    @Override
    public void setFlat(String flat) throws SOAPException {
        this.contactDetails.addChildElement("flat").addTextNode(flat);
    }

    @Override
    public void setBuildingName(String buildingName) throws SOAPException {
        this.contactDetails.addChildElement("buildingName").addTextNode(buildingName);
    }

    @Override
    public void setBuildingNumber(String buildingNumber) throws SOAPException {
        this.contactDetails.addChildElement("buildingNumber").addTextNode(buildingNumber);
    }

    @Override
    public void setStreet(String street) throws SOAPException {
        this.contactDetails.addChildElement("street").addTextNode(street);
    }

    @Override
    public void setCity(String city) throws SOAPException {
        this.contactDetails.addChildElement("city").addTextNode(city);
    }

    @Override
    public void setPostcode(String postcode) throws SOAPException {
        this.contactDetails.addChildElement("postcode").addTextNode(postcode);
    }

    @Override
    public void setCountry(String country) throws SOAPException {
        this.contactDetails.addChildElement("country").addTextNode(country);
    }

    @Override
    public void setInternationalCode(String internationalCode) throws SOAPException {
        this.contactDetails.addChildElement("internationalCode").addTextNode(internationalCode);
    }

    @Override
    public void setAreaCode(String areaCode) throws SOAPException {
        this.contactDetails.addChildElement("areaCode").addTextNode(areaCode);
    }

    @Override
    public void setNumber(String number) throws SOAPException {
        this.contactDetails.addChildElement("number").addTextNode(number);
    }

    @Override
    public void setExtension(String extension) throws SOAPException {
        this.contactDetails.addChildElement("extension").addTextNode(extension);
    }

    @Override
    public void setEmail(String email) throws SOAPException {
        this.contactDetails.addChildElement("email").addTextNode(email);
    }
}
