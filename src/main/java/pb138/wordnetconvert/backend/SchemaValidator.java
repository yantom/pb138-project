package pb138.wordnetconvert.backend;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.MessageContext;
import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 *
 * @author Ondřej Bulla, uco: 422296
 * @version 11.6.2015
 */
public class SchemaValidator {
    class ValidationErrorsHandler implements ErrorHandler{

        @Override
        public void warning(SAXParseException exception) throws SAXException {
            Logger.getAnonymousLogger(SchemaValidator.class.getName()).log(Level.INFO,exception.getMessage());
        }

        @Override
        public void error(SAXParseException exception) throws SAXException {
            error = exception.getMessage();
            throw new SAXException(error);
        }

        @Override
        public void fatalError(SAXParseException exception) throws SAXException {
            error = exception.getMessage();
            throw new SAXException(error);
        }

        
    }
    private DocumentBuilder docBuilder;
    private String error;
    public SchemaValidator(){
        try {
            DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            docBuilder = dbf.newDocumentBuilder();
            docBuilder.setErrorHandler(new ValidationErrorsHandler());
        } catch (ParserConfigurationException ex) {
            System.err.println("Error initializing parser: "+ ex);
        }
        
    }
    public SchemaValidator(String schemaName){
        try {
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = sf.newSchema(new File(schemaName));
            
            DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            
            dbf.setSchema(schema);
            docBuilder = dbf.newDocumentBuilder();
            docBuilder.setErrorHandler(new ValidationErrorsHandler());
        } catch (SAXException ex) {
            System.err.println("Invalid schema: " + ex.getMessage());
            System.exit(-1);
        } catch (ParserConfigurationException ex) {
            System.err.println("Parser configuration error: " + ex.getMessage());
        }
    }
    
    public Boolean validate(String xmlFilename) throws IOException, SAXException {
        Document doc = docBuilder.parse(new File(xmlFilename));
        return true;
    }
}
