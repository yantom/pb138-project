package pb138.wordnetconvert.backend;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

/**
 * class for ILR element of DEBVisDic format XML
 * @author Jan Tomášek, uco: 422677
 * @version 11.6.2015
 */

@XmlRootElement(name = "ILR")
public class InternalLink {
    @XmlValue
    private String id;
    @XmlAttribute
    private String type;

    public InternalLink(String id, String type){
        this.id = id;
        this.type = type;
    }
    
    public InternalLink(){}
    
    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }
    
    
}
