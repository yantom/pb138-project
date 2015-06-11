package pb138.wordnetconvert.backend;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

/**
 * class for LITERAL element of DEBVisDic format XML
 * @author Jan Tomášek, uco: 422677
 * @version 11.6.2015
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
