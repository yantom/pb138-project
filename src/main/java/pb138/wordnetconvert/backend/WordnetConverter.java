/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pb138.wordnetconvert.backend;

import java.io.IOException;
import org.xml.sax.SAXException;

/**
 *
 * @author Honzator
 */
public interface WordnetConverter {
    void convert(String sourcePath, String destinationPath) throws IOException, SAXException;
}
