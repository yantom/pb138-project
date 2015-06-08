/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pb138.wordnetconvert.backend;

import java.io.IOException;

/**
 *
 * @author Honzator
 */
public class TestMain {
    public static void main(String[] args) throws IOException{
        SvkWordnetConverterImpl.convert("d:\\School\\4th\\pb138-xml\\WordnetConvert\\svktest.txt", null);
    }
}
