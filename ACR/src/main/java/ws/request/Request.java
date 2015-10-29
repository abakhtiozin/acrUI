package main.java.ws.request;

import main.java.model.Reseller;

import javax.xml.soap.*;

/**
 * Created by Andrii.Bakhtiozin on 18.07.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public abstract class Request {
    protected SOAPMessage soapMsg;
    protected SOAPBodyElement soapBodyElement;
    protected SOAPConnection soapConnection;
    protected String url = "http://dev.acr.local/ws/SoapApi";
    protected Reseller reseller;
    private MessageFactory factory;
    private SOAPPart part;
    private SOAPEnvelope envelope;
    private SOAPBody body;
    private SOAPHeader header;
    private SOAPConnectionFactory soapConnectionFactory;

    public Request() {
        setUpSoapRequest();
    }

    private void setUpSoapRequest() {
        try {
            this.factory = MessageFactory.newInstance();
            this.soapMsg = factory.createMessage();
            this.part = soapMsg.getSOAPPart();
            addEnvelope();
            this.header = envelope.getHeader();
            this.body = envelope.getBody();
            this.soapConnectionFactory = SOAPConnectionFactory.newInstance();
            this.soapConnection = soapConnectionFactory.createConnection();
        } catch (SOAPException e) {
            e.printStackTrace();
        }
    }

    private void addEnvelope() throws SOAPException {
        this.envelope = part.getEnvelope();
        this.envelope.addNamespaceDeclaration("ns", "http://rail-russia.test/ws/SoapApi/0.1/");
    }

    public final void withReseller(Reseller reseller) {
        this.reseller = reseller;
        try {
            SOAPElement access = soapBodyElement.addChildElement("access");
            access.addChildElement("resellerCode").addTextNode(reseller.getResellerCode());
            access.addChildElement("userName").addTextNode(reseller.getUserName());
            access.addChildElement("password").addTextNode(reseller.getPassword());
            access.addChildElement("lang").addTextNode("ru");
        } catch (SOAPException e) {
            e.printStackTrace();
        }
    }

    protected final void addAcrMethodName(String methodName) {
        try {
            this.soapBodyElement = this.body.addBodyElement(envelope.createName("ns:acr_" + methodName));
        } catch (SOAPException e) {
            e.printStackTrace();
        }
    }

    protected SOAPMessage createRequest() {
        try {
            soapMsg.saveChanges();
        } catch (SOAPException e) {
            e.printStackTrace();
        }
        return soapMsg;
    }
}
