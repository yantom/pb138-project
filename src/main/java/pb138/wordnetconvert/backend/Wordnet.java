/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pb138.wordnetconvert.backend;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Honzator
 */

@XmlRootElement(name = "WN")
public class Wordnet {
    
    @XmlElement(name = "SYNSET")
    private List<Synset> synsets = new ArrayList<>();

    public List<Synset> getSynsets() {
        return synsets;
    }
}
