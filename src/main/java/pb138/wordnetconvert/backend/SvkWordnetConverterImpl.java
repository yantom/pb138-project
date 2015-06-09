/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pb138.wordnetconvert.backend;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import pb138.wordnetconvert.WordnetException;

/**
 *
 * @author Honzator
 */
public class SvkWordnetConverterImpl{
    
    public static void convert(String sourcePath, String destinationPath) throws IOException, WordnetException, JAXBException {
        Map<String,Integer> senseMap = new HashMap<>();
        Wordnet wn = new Wordnet();
        try(BufferedReader br =  new BufferedReader(new InputStreamReader(new FileInputStream(sourcePath), "UTF-8"))){
        Pattern rowRegex = Pattern.compile("\\d+\\t([abnv])\\t(.*)\\t(.*) \\u241e (\\d+) \\d+ . [a-fA-F0-9]{2} (?:.+ [a-fA-F0-9] )*\\d{3} (.*)(?:(?:\\d{2}.*)|(?:\\|.*))");
        Pattern synonymsRegex = Pattern.compile("[\\+\\-\\?]? ?([^;\\?\\+\\-][^;]*)(?:; )?");
        Pattern noteRegex = Pattern.compile("\\(([^)]+)\\)");
        Pattern pointerRegex = Pattern.compile("(.) (\\d+) . ([a-fA-F0-9]{4})");
        String literal;
        String lnote;
        //row regex- 1- typ, 2- synonyma, 3- definice, 4- id, 5- pointery 
        //synonym regex- 1- synonymum s lnote uvnitr
        //pointer regex- 1- pointer type, 2- synset id, 3- hex cislo- prijimam pouze 0000
        Matcher rowMatcher;
        Matcher synonymMatcher;
        Matcher pointerMatcher;
        Matcher noteMatcher;
        String def;
        int rownumber=0;
        
        String line = br.readLine();
        while(line != null){
            System.out.println(line);
            rownumber++;
            rowMatcher = rowRegex.matcher(line);
            if(!rowMatcher.find())
                throw new WordnetException("error occured while parsing" + rownumber + ". line");
            if(!rowMatcher.group(3).equals(""))
                def = rowMatcher.group(3);
            else
                def = null;
            Synset synset=new Synset(rowMatcher.group(4), rowMatcher.group(1).charAt(0), def);
            Literal lit;
            synonymMatcher=synonymsRegex.matcher(rowMatcher.group(2));
            
            pointerMatcher = pointerRegex.matcher(rowMatcher.group(5));
            
            while(synonymMatcher.find()){
                noteMatcher = noteRegex.matcher(synonymMatcher.group(1));
                if(noteMatcher.find()){
                    lnote=noteMatcher.group(1);
                    literal = (synonymMatcher.group(1).substring(0, noteMatcher.start(1)-1) + synonymMatcher.group(1).substring(noteMatcher.end(1)+1, synonymMatcher.group(1).length()));
                    
                }
                else{
                    literal = synonymMatcher.group(1);
                    lnote=null;
                }
                synset.getSynonym().getLiterals().add(new Literal(literal,senseMap.getOrDefault(literal, 1),lnote));
                senseMap.put(literal, senseMap.getOrDefault(literal, 1)+1);
            }
            while(pointerMatcher.find()){
                if(!pointerMatcher.group(3).equals("0000"))
                    continue;
            }
            wn.getSynsets().add(synset);
            line = br.readLine();  
        }
        }
        catch(IOException ex){
            throw ex;
        }      

        try{
        JAXBContext jaxbContext = JAXBContext.newInstance(Wordnet.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        jaxbMarshaller.marshal(wn, new File("ohye.xml"));
        }catch(JAXBException ex){
            throw ex;
        }

    }
    
}
