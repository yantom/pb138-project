package pb138.wordnetconvert.backend;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * class for SYNONYM element of DEBVisDic format XML
 * @author Jan Tomášek, uco: 422677
 * @version 11.6.2015
 */
@XmlRootElement(name = "SYNONYM")
public class Synonym {
    @XmlElement(name = "LITERAL")
    private List<Literal> literals = new ArrayList<>();

    public List<Literal> getLiterals() {
        return literals;
    }
    
}
