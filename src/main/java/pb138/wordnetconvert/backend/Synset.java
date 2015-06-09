/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pb138.wordnetconvert.backend;
import java.util.HashSet;
import java.util.Set;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import pb138.wordnetconvert.WordnetException;


/**
 *
 * @author Honzator
 */
@XmlRootElement(name = "SYNSET")
public class Synset {
    @XmlElement(name = "ID")
    private String id;
    @XmlElement(name = "POS")
    private char pos;
    @XmlElement(name = "SYNONYM")
    private Synonym synonym = new Synonym();
    @XmlElement(name = "ILR")
    private Set<InternalLink> internalLinks = new HashSet<>();
    @XmlElement(name = "DEF")
    private String def;
    public Synset(String id, char pos, String def) throws WordnetException{
        this.id = id;
        switch(pos){
            case 'r':
                this.pos = 'b';
                break;
            case 's':
                this.pos = 'a';
                break;
            case 'b':
            case 'a':
            case 'n':
            case 'v':
                this.pos = pos;
                break;
            default: throw new WordnetException("unknown pos");
        }
        this.def = def;
    }
    
    public Synset(){}

    public String getId() {
        return id;
    }

    public char getPos() {
        return pos;
    }

    public Synonym getSynonym() {
        return synonym;
    }

    public Set<InternalLink> getInternalLinks() {
        return internalLinks;
    }

    public String getDef() {
        return def;
    }
}
