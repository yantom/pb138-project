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
    @Override
    public void convert(String sourcePath, String destinationPath) throws WordnetException {
	SchemaValidator validator = new SchemaValidator("arabicWordnet.xsd");
	try {
	    validator.validate(sourcePath);
	}
	catch(SAXException ex) {
	    throw new WordnetException("Invalid input file.");
	}
        catch(IOException ex){
            throw new WordnetException(ex.getMessage());
        }
	File source = new File(sourcePath);
	source.renameTo(new File("in"));
	
	Process p;
	try {
		p = Runtime.getRuntime().exec("java -cp saxon9he.jar net.sf.saxon.Query -s:in -o:" + destinationPath + " awnToDebvisdic.xq");
		p.waitFor();
	}
	catch(Exception e) {
	    source = new File("in");
	    source.renameTo(new File(sourcePath));
	    throw new WordnetException("Converting failed.");
	}
	
	source = new File("in");
	source.renameTo(new File(sourcePath));
    }
}
