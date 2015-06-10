/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pb138.wordnetconvert.backend;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author Ondra
 */
public class ArabicWordnetConverterImpl implements WordnetConverter {
    public void convert(String sourcePath, String destinationPath) throws IOException {
	File source = new File(sourcePath);
	source.renameTo(new File("in"));
	//TODO execute XQuery
	System.in.read();
	source = new File("in");
	source.renameTo(new File(sourcePath));
    }
}
