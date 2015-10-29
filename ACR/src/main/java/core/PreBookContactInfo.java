package main.java.core;

/**
 * Created by Andrii.Bakhtiozin on 17.09.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public interface PreBookContactInfo {
    void setTitle(String title) throws Exception;

    void setName(String name) throws Exception;

    void setSurname(String surname) throws Exception;

    void setFlat(String flat) throws Exception;

    void setBuildingName(String buildingName) throws Exception;

    void setBuildingNumber(String buildingNumber) throws Exception;

    void setStreet(String street) throws Exception;

    void setCity(String city) throws Exception;

    void setPostcode(String postcode) throws Exception;

    void setCountry(String country) throws Exception;

    void setInternationalCode(String internationalCode) throws Exception;

    void setAreaCode(String areaCode) throws Exception;

    void setNumber(String number) throws Exception;

    void setExtension(String extension) throws Exception;

    void setEmail(String email) throws Exception;
}
