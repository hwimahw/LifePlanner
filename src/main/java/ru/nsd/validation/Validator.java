package ru.nsd.validation;

import java.io.InputStream;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import ru.nsd.exceptions.InvalidXmlFileException;


public class Validator {

    public static void validate(InputStream inputStream) throws InvalidXmlFileException {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            saxParser.parse(inputStream, new MyHandler());
        } catch (Exception ex) {
            throw new InvalidXmlFileException("The file LifePlan is invalid");
        }
    }

    static class MyHandler extends DefaultHandler {
        @Override
        public void startElement(String uri, String localName, String qName,
                                 Attributes attributes) throws InvalidXmlFileException {
            String name = attributes.getValue("name");
            if (name == null) {
                throw new InvalidXmlFileException("The file LifePlan is invalid");
            }
        }
    }
}