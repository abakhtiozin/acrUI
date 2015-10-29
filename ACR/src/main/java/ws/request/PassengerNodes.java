package main.java.ws.request;

import main.java.model.passenger.PassengerDocumentType;
import org.joda.time.DateTime;

import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;

/**
 * Created by Andrii.Bakhtiozin on 16.09.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class PassengerNodes {
    private SOAPElement passengerElement;

    public PassengerNodes(SOAPElement passengerElement) {
        this.passengerElement = passengerElement;
    }

    public void setSalutatuion(String salutation) {
        try {
            passengerElement.addChildElement("salutation").addTextNode(salutation);
        } catch (SOAPException e) {
            e.printStackTrace();
        }
    }

    public void setName(String name) {
        try {
            passengerElement.addChildElement("name").addTextNode(name);
        } catch (SOAPException e) {
            e.printStackTrace();
        }
    }

    public void setSurname(String surname) {
        try {
            passengerElement.addChildElement("surname").addTextNode(surname);
        } catch (SOAPException e) {
            e.printStackTrace();
        }
    }

    public void setPatronymic(String patronymic) {
        try {
            passengerElement.addChildElement("patronymic").addTextNode(patronymic);
        } catch (SOAPException e) {
            e.printStackTrace();
        }
    }

    public void setDocumentType(PassengerDocumentType documentType) {
        try {
            passengerElement.addChildElement("documentType").addTextNode(documentType.toString());
        } catch (SOAPException e) {
            e.printStackTrace();
        }
    }

    public void setDocumentNumber(String documentNumber) {
        try {
            passengerElement.addChildElement("documentNumber").addTextNode(documentNumber);
        } catch (SOAPException e) {
            e.printStackTrace();
        }
    }

    public void setDocumentExpireDate(DateTime documentExpireDate) {
        try {
            passengerElement.addChildElement("documentExpireDate").addTextNode(documentExpireDate.toString("yyyy-MM-dd"));
        } catch (SOAPException e) {
            e.printStackTrace();
        }
    }

    public void setBirthDate(DateTime birthDate) {
        try {
            passengerElement.addChildElement("birthDate").addTextNode(birthDate.toString("yyyy-MM-dd"));
        } catch (SOAPException e) {
            e.printStackTrace();
        }
    }

    public void setBirthPlace(String birthPlace) {
        try {
            passengerElement.addChildElement("birthPlace").addTextNode(birthPlace);
        } catch (SOAPException e) {
            e.printStackTrace();
        }
    }

    public void setNationality(String nationality) {
        try {
            passengerElement.addChildElement("nationality").addTextNode(nationality);
        } catch (SOAPException e) {
            e.printStackTrace();
        }
    }

    public void setResidence(String residence) {
        try {
            passengerElement.addChildElement("residence").addTextNode(residence);
        } catch (SOAPException e) {
            e.printStackTrace();
        }
    }
}
