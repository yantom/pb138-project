/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pb138.wordnetconvert.backend;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

/**
 *
 * @author Honzator
 */
@XmlRootElement(name = "LITERAL")
public class Literal {
    @XmlValue
    private String literal;
    @XmlAttribute
    private int sense;
    @XmlAttribute
    private String lnote;
    
    public Literal(String literal,int sense,String lnote){
        this.literal=literal;
        this.sense=sense;
        this.lnote=lnote;
    }
    
    public Literal(){
    }

    public String getLiteral() {
        return literal;
    }

    public int getSense() {
        return sense;
    }

    public String getLnote() {
        return lnote;
    }
    
    
}
