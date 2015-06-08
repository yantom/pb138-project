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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Honzator
 */
public class SvkWordnetConverterImpl{

    public static void convert(String sourcePath, String destinationPath) throws IOException {
        try(BufferedReader br =  new BufferedReader(new InputStreamReader(new FileInputStream(sourcePath), "UTF-8"))){
        Pattern rowRegex = Pattern.compile("\\d+\\t([abnv])\\t(.*)\\t(.*) \\u241e (\\d+) \\d+ . \\d+ (.+ [a-fA-F0-9] )*\\d{3} (.*)((\\d{2}.*)|(\\|.*))");
        Pattern synonymsRegex = Pattern.compile("[\\+\\-\\?]? ?([^ ;]*)(; )?");
        
        int countOfSynonymsToSkip;
        Matcher m;
        Matcher m2;
        String line = br.readLine();
        while(line != null){
            m = rowRegex.matcher(line);
            m.find();
            System.out.println(line);
            System.out.println(m.group(1));
            System.out.println(m.group(2));
            System.out.println(m.group(3));
            System.out.println(m.group(4));
            
            System.out.println(m.group(6));
            m2=synonymsRegex.matcher(m.group(2));
            while(m2.find()){
                System.out.println(m2.group(1));
            }
            line = br.readLine();
        }
        }
        catch(IOException ex){
            throw ex;
        }      
        /*
        1. typ, 2. synonyma, 3. synsetid, 4. pocet pointru ktere nasleduji
        */
    }
    
}
