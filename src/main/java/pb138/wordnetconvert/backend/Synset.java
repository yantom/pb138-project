package pb138.wordnetconvert.backend;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import pb138.wordnetconvert.WordnetException;


/**
 * class for SYNSET element of DEBVisDic format XML
 * @author Jan Tomášek, uco: 422677
 * @version 11.6.2015
 */
@XmlRootElement(name = "SYNSET")
public class Synset {
    @XmlElement(name = "ID")
    private String id;
    @XmlElement(name = "POS")
    private String pos;
    @XmlElement(name = "SYNONYM")
    private Synonym synonym = new Synonym();
    @XmlElement(name = "ILR")
    private Set<InternalLink> internalLinks = new HashSet<>();
    @XmlElement(name = "DEF")
    private String def;
    
    /**
     * constructor of Synset class
     * @param id    id of synset
     * @param pos   part of speech- some Wordnets uses different names- these are converted
     * @param def   definition of synset
     * @throws WordnetException throws exception if pos is unknown
     */
    public Synset(String id, String pos, String def) throws WordnetException{
        this.id = id;
        switch(pos){
            case "r":
                this.pos = "b";
                break;
            case "s":
                this.pos = "a";
                break;
            case "b":
            case "a":
            case "n":
            case "v":
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

    public String getPos() {
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Synset other = (Synset) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
}
