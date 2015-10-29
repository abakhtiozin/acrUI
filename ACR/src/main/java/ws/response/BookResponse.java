package main.java.ws.response;

import javax.xml.soap.SOAPMessage;

/**
 * Created by Andrii.Bakhtiozin on 12.08.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class BookResponse extends Response {
    public BookResponse(SOAPMessage soapResponse) {
        super(soapResponse);
    }
}
