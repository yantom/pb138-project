/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pb138.wordnetconvert;


/**
 * exception which is thrown if anything goes wrong
 * @author Jan Tomášek, uco: 422677
 * @version 11.6.2015
 */
public class WordnetException extends Exception{

    public WordnetException(String message) {
        super(message);
    }

    public WordnetException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
