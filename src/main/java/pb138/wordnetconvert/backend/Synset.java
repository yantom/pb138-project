/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pb138.wordnetconvert.backend;
import java.util.Set;


/**
 *
 * @author Honzator
 */
public class Synset {
    String id;
    Pos pos;
    Set<Synonym> synonyms;
    Set<InternalLink> internalLinks;
    String def;
}
