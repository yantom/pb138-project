/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pb138.wordnetconvert.backend;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.xml.sax.SAXException;
import pb138.wordnetconvert.WordnetException;
import static java.nio.file.StandardCopyOption.*;

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
	try {
	    Files.copy(Paths.get(sourcePath), Paths.get("in"), REPLACE_EXISTING);
	    Process p;
	    p = Runtime.getRuntime().exec("java -cp saxon9he.jar net.sf.saxon.Query -s:in -o:" + destinationPath + " awnToDebvisdic.xq");
	    p.waitFor();
	}
	catch(Exception e) {
	    try {
		Files.delete(Paths.get("in"));
	    }
	    catch(Exception ex) {}
	    throw new WordnetException("Converting failed.");
	}
	try {
	    Files.delete(Paths.get("in"));
	}
	catch(Exception ex) {}
    }
}
