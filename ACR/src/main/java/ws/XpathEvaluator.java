package main.java.ws;

import org.w3c.dom.Document;

import javax.xml.xpath.*;

/**
 * Created by Andrii.Bakhtiozin on 30.07.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class XpathEvaluator {
    private Document document;

    public XpathEvaluator(Document document) {
        this.document = document;
    }

    public String getResult(String xpathExpression) {
        XPath xpath = XPathFactory.newInstance().newXPath();
        String result = null;
        try {
            XPathExpression expr = xpath.compile(xpathExpression);
            result = (String) expr.evaluate(document, XPathConstants.STRING);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return result;
    }
}
