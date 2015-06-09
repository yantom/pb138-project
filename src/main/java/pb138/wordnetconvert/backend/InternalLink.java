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
@XmlRootElement(name = "ILR")
public class InternalLink {
    @XmlValue
    private String id;
    @XmlAttribute
    private String type;

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }
    
    
}
