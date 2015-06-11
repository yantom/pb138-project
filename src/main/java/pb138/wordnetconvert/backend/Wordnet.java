package pb138.wordnetconvert.backend;

import java.util.HashSet;
import java.util.Set;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * class for WN element of DEBVisDic format XML
 * @author Jan Tomášek, uco: 422677
 * @version 11.6.2015
 */

@XmlRootElement(name = "WN")
public class Wordnet {
    
    @XmlElement(name = "SYNSET")
    private Set<Synset> synsets = new HashSet<>();

    public Set<Synset> getSynsets() {
        return synsets;
    }
}
