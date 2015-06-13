package pb138.wordnetconvert.backend;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import pb138.wordnetconvert.WordnetException;

/**
 * class for slovak and lithuanian Wordnets convert
 * 
 * @author Jan Tomášek, uco: 422677
 * @version 11.6.2015
 */
public class SLWordnetConverterImpl implements WordnetConverter{
 
    private Lang language;
    
    public SLWordnetConverterImpl(Lang language){
        this.language = language;
    }
    
    /**
     * Method finds out if input file is in slovak or lithuian format and
     * according to language attribute of class returns pattern (regex) for one record of the file.
     * Method also reads the file and stores all ids of synsets which are in file to a set of Strings.
     * 
     * @param pathToFile    path to source file with Wordnet
     * @param setOfSynsetIDs    initialized set of Strings
     * @return pattern (regex) which will be used for every record to parse data
     * @throws WordnetException throws exception if input file cant be read, is empty or has wrong format
     */
    public String prepareSources(String pathToFile, Set<String> setOfSynsetIDs) throws WordnetException{
        String rowPattern=null;
        try(BufferedReader br =  new BufferedReader(new InputStreamReader(new FileInputStream(pathToFile), "UTF-8"))){
            String SynsetID;
            String line = br.readLine();
            if(line!= null){
                while(line.isEmpty()){
                    line = br.readLine();
                }
                if(line == null)
                    throw new WordnetException("File does not containt any record.");
                int countOfSeparators = line.length() - line.replace("\u241e", "").length();
                switch(language){
                    case SK:
                        if(countOfSeparators == 1)
                            rowPattern = "\\d+\\t([arnv])\\t(.*?)\\t(.*?) \\u241e (\\d{8}) \\d{2} . ([a-fA-F0-9]{2}) (.+)";
                        else if(countOfSeparators == 2)
                            rowPattern = "[^\\u241e]*+\\u241e\\d+\\t([arnv])\\t(.*?)\\t(.*?)\\u241e(\\d{8}) \\d{2} . ([a-fA-F0-9]{2}) (.+)";
                        break;
                    case LT:
                        if(countOfSeparators == 2)
                            rowPattern ="\\d+\\t([arnv])\\t(.*?)\\t(.*?)\\u241e[^\\u241e]*+\\u241e(\\d{8}) \\d{2} . ([a-fA-F0-9]{2}) (.+)";
                        break;
                    default:
                        throw new WordnetException("Wrong input file format.");           
                }
                if(rowPattern == null)
                    throw new WordnetException("Wrong input file format.");
            }
            while(line != null){
                if(line.isEmpty()){
                    line = br.readLine();
                    continue;
                }
                SynsetID = line.substring(line.lastIndexOf("\u241e")+1, line.lastIndexOf("\u241e")+10).trim();
                setOfSynsetIDs.add(SynsetID);
                line = br.readLine();
            }
        }
        catch(IOException ex){
            throw new WordnetException("Problem occured while reading from file at path: " + pathToFile,ex);
        }
        return rowPattern;
    }
    
    /**
     * Method exports given Wordnet object to the file.
     * @param wn    Wordnet object
     * @param w Initialized writer
     * @throws JAXBException
     */
    public void exportWNToFile(Wordnet wn, Writer w) throws JAXBException{
        JAXBContext jaxbContext = JAXBContext.newInstance(Wordnet.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        jaxbMarshaller.marshal(wn, w);
    }
    
    /**
     * Method which returns description of pointer type.
     * @param pointerType   type of pointer
     * @param pos   part of speech- some pointers have different semantic for different poss
     * @return description of pointer, or null if pointer is unknown
     */
    public String getPointerDescription(String pointerType, String pos){
        switch(pointerType){
            case "@":
                return "Hypernym";
            case "@i":
                return  "Instance Hypernym";
            case "#m":
                return  "Member holonym";
            case "#p":
                return  "Part holonym";
            case "#s":
                return  "Substance holonym";
            case "!":
                return  "Antonym";
            case "=":
                return  "Attribute";
            case "+":
                return  "Derivationally related form";
            case "*":
                return  "Entailment";
            case ">":
                return  "Cause";
            case "^":
                return  "Also see";
            case "$":
                return  "Verb Group";
            case "&":
                return  "Similar to";
            case "<":
                return  "Participle of verb";
            case ";c":
                return  "Domain of synset - TOPIC";
            case ";r":
                return  "Domain of synset - REGION";
            case ";u":
                return  "Domain of synset - USAGE";
            case "\\":
                if(pos.equals("b"))
                    return  "Derived from adjective";
                else
                    return  "Pertainym";
            default:
                return null;
        }
    }
    
    /**
     * Method uses 6 regular expressions to parse input file:
     * 
     * 1) rowRegex- groups- 1- part of speech, 2- synonyms, 3- definition, 4- id, 5-count of synonyms, 6- rest of line
     * rowRegex is returned from function 'prepareSources' it has different format for different wordnets but groups are always the same
     * rowRegex is used to match and parse each row of input data
     * 2) skipSynonymsRegex- groups- 1- count of pointers, 2- rest of line
     * skipSynonymsRegex is used to match 6. group from rowRegex
     * 3) pointersRegex- groups- 1- pointers
     * pointersRegex is used to match 2. group from skipSynonymsRegex
     * 4) synonymRegex- groups- 1- synonym with note inside
     * synonymRegex is used to match each synonym from 2. group from rowRegex
     * 5) noteRegex- groups- 1- note
     * noteRegex is used to extract note from synonym
     * 6) pointerRegex- groups- 1- pointer type, 2- synset id, 3- relation number
     * pointerRegex is used to match each pointer from 1. group of pointesRegex
     * 
     * sense number for every literal is 1 by default, every next occurrence have sense number incremented by one
     * pointers to synsets which does not exist in file are logged into wordnet_invalid_pointers_log.txt file which stored with converted file
     * pointers for: Hyponym, Instance Hyponym, Member meronym, Substance meronym, Part meronym are not stored
     * 
     * @param sourcePath
     * @param destinationPath
     * @throws WordnetException 
     */
    @Override
    public  void convert(String sourcePath, String destinationPath) throws WordnetException {
        Set<String> synsetIDset = new HashSet<>();
        Map<String,Integer> senseMap = new HashMap<>();
        Wordnet wn = new Wordnet();
        int rownumber=0;
        
        Pattern rowRegex = Pattern.compile(prepareSources(sourcePath,synsetIDset));
        Pattern synonymsRegex = Pattern.compile("[\\+\\-\\?]? ?([^;\\?\\+\\-][^;]*)(?:; )?");
        Pattern noteRegex = Pattern.compile("\\(([^)]+)\\)");
        Pattern pointerRegex = Pattern.compile("([^ ]{1,2}) (\\d{8}) . ([a-fA-F0-9]{4})");
        int countOfSynonyms;
        int countOfPointers;
        Pattern skipSynonymsRegex;
        Pattern pointersRegex;
        Matcher rowMatcher;
        Matcher synonymMatcher;
        Matcher pointerMatcher;
        Matcher noteMatcher;
        Matcher pointersMatcher;
        Matcher skipSynonymsMatcher;
        
        String literal;
        String lnote;
        String def;
        String pointerDescription;
        Synset synset;
        
        File destFile=new File(destinationPath);
        File logFile=new File(destinationPath.substring(0, destinationPath.lastIndexOf('.')) + "_invalid_pointers_log.txt");
        
        try(BufferedReader br =  new BufferedReader(new InputStreamReader(new FileInputStream(sourcePath), "UTF-8"));
                Writer wordnetWriter= new OutputStreamWriter(new FileOutputStream(destFile), "UTF-8");
                BufferedWriter logWriter = new BufferedWriter(new FileWriter(logFile))){
        
            logWriter.write("missing_synset_id,'pointed_as',from_synset_with_id");
            logWriter.newLine();
        String line = br.readLine();
        while(line != null){
            if(line.isEmpty()){
                    line = br.readLine();
                    continue;
                }
            rownumber++;
            rowMatcher = rowRegex.matcher(line);
            if(!rowMatcher.find()){
                destFile.delete();
                logFile.delete();
                throw new WordnetException("Error occured while converting " + rownumber + ". line.");}
            if(!rowMatcher.group(3).equals(""))
                def = rowMatcher.group(3);
            else
                def = null;
            synset=new Synset(rowMatcher.group(4), rowMatcher.group(1), def);
            synonymMatcher=synonymsRegex.matcher(rowMatcher.group(2));
            
            while(synonymMatcher.find()){
                noteMatcher = noteRegex.matcher(synonymMatcher.group(1));
                if(noteMatcher.find()){
                    lnote=noteMatcher.group(1);
                    literal = (synonymMatcher.group(1).substring(0, noteMatcher.start(1)-1) + synonymMatcher.group(1).substring(noteMatcher.end(1)+1, synonymMatcher.group(1).length())).trim(); 
                }
                else{
                    literal = synonymMatcher.group(1);
                    lnote=null;
                }
                synset.getSynonym().getLiterals().add(new Literal(literal,senseMap.getOrDefault(literal, 1),lnote));
                senseMap.put(literal, senseMap.getOrDefault(literal, 1)+1);
            }
            
            countOfSynonyms = Integer.parseInt(rowMatcher.group(5),16);
            skipSynonymsRegex = Pattern.compile("(?:[^ ]+ [a-fA-F0-9] ){" + countOfSynonyms + "}(\\d{3}) (.+)");
            skipSynonymsMatcher = skipSynonymsRegex.matcher(rowMatcher.group(6));
            
            if(!skipSynonymsMatcher.find()){
                destFile.delete();
                logFile.delete();
                throw new WordnetException("Error occured while converting " + rownumber + ". line, "
                        + "somewhere between 'word' and 'p_cnt' part of Princeton Wordnet.");
            }
            countOfPointers = Integer.parseInt(skipSynonymsMatcher.group(1));
            pointersRegex = Pattern.compile("((?:[^ ]{1,2} \\d{8} . [a-fA-F0-9]{4} ){" + countOfPointers + "}).+");
            pointersMatcher = pointersRegex.matcher(skipSynonymsMatcher.group(2));
            if(!pointersMatcher.find()){
                destFile.delete();
                logFile.delete();
                throw new WordnetException("Error occured while converting " + rownumber + ". line, "
                        + "somewhere near the 'ptr' part of Princeton Wordnet.");}
            pointerMatcher = pointerRegex.matcher(pointersMatcher.group(1));
            while(pointerMatcher.find()){
                if(!pointerMatcher.group(3).equals("0000"))
                    continue;
                pointerDescription = getPointerDescription(pointerMatcher.group(1), synset.getPos());
                if(pointerDescription == null)
                    continue;
                if(!synsetIDset.contains(pointerMatcher.group(2))){
                    logWriter.write(pointerMatcher.group(2) + ",'" + pointerDescription + "'," + synset.getId());
                    logWriter.newLine();
                    continue;
                }
                synset.getInternalLinks().add(new InternalLink(pointerMatcher.group(2),pointerDescription));
                }
            wn.getSynsets().add(synset);
            line = br.readLine();
            }
        exportWNToFile(wn, wordnetWriter);
        }
        catch(IOException ex){
            destFile.delete();
            logFile.delete();
            throw new WordnetException("Input/Output problem occured.", ex); 
        }
        catch(JAXBException ex){
            destFile.delete();
            logFile.delete();
            throw new WordnetException("Error occured at the final transformation to XML file.", ex);
        }
    }
}
