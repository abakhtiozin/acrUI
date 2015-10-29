package main.java.ws.response;

import main.java.ws.XMLHandler;
import main.java.ws.XpathEvaluator;

import javax.xml.soap.SOAPMessage;

/**
 * Created by Andrii.Bakhtiozin on 30.07.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class Response {
    protected XpathEvaluator evaluator;

    public Response(SOAPMessage soapResponse) {
        this.evaluator = new XpathEvaluator(XMLHandler.soapMessageToDocument(soapResponse));
    }
}
