/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pb138.wordnetconvert.backend;

import java.io.File;
import java.io.IOException;
import org.xml.sax.SAXException;
import pb138.wordnetconvert.WordnetException;

/**
 *
 * @author Ondra
 */
public class ArabicWordnetConverterImpl implements WordnetConverter {
    public void convert(String sourcePath, String destinationPath) throws IOException, WordnetException {
	SchemaValidator validator = new SchemaValidator("arabicWordnet.xsd");
	try {
	    validator.validate(sourcePath);
	}
	catch(SAXException ex) {
	    throw new WordnetException(ex.getMessage());
	}
	File source = new File(sourcePath);
	source.renameTo(new File("in"));
	//TODO execute XQuery
	source = new File("in");
	source.renameTo(new File(sourcePath));
    }
}
