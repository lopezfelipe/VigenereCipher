

/**
 * Purpose:
 * Break a Viegenere Cipher without knowing the key length and
 * assuming the most common character is repeated multiple times.
 * 
 * @author Felipe Lopez
 * @version Jan 2018
 */

import java.util.*;
import edu.duke.*;
import java.io.File;

public class VigenereBreaker {
    
    // FIELDS
    private HashMap<String,HashSet<String>> dictionaries; // Set of lower case words for all langs
    private HashMap<String,Character> mostCommonChar; // Most common char in lang
    
    // Generic method for easier printing
    private <T> void printLine(T t) { System.out.println( t ); }
    
    // Helper function for constructor: Read dictionary
    private HashSet<String> readDictionary(FileResource fr){
        HashSet<String> hs = new HashSet<String>();
        for(String word : fr.lines()){
            hs.add(word.toLowerCase());
        }
        return hs;
    }
    // Helper function for constructor: Get mostCommonChar
    private char mostCommonCharIn(HashSet<String> dictionary){
        HashMap<Character,Integer> charCounts = new HashMap<Character,Integer>();
        for(String word : dictionary){
            for(char c : word.toLowerCase().toCharArray()){
                if(!charCounts.containsKey(c)) { charCounts.put(c,1); }
                else { charCounts.put(c, charCounts.get(c)+1); }
            }
        }
        int maxFreq = 0;
        char mostCommon = ' ';
        for(char c : charCounts.keySet()){
            if(charCounts.get(c) > maxFreq){
                maxFreq = charCounts.get(c);
                mostCommon = c;
            }
        }
        return mostCommon;        
    }
    
    // CONSTRUCTOR
    public VigenereBreaker(){
        this.dictionaries = new HashMap<String,HashSet<String>>();
        this.mostCommonChar = new HashMap<String,Character>();        
        // Read all dictionaries
        DirectoryResource dictDirectory = new DirectoryResource();
        for(File f : dictDirectory.selectedFiles()){
            FileResource fr = new FileResource(f);
            HashSet<String> dict = readDictionary(fr);
            String lan = f.getName();
            //printLine(lan);
            this.dictionaries.put(lan,dict);
            //printLine(mostCommonCharIn(dict));
            this.mostCommonChar.put(lan,mostCommonCharIn(dict));
        }
    }
    
    // Helper functions for breaking the code
    // Slice string from given starting point and periodicity
    private String sliceString(String message, int whichSlice, int totalSlices) {
        StringBuilder answer = new StringBuilder();
        for(int i=whichSlice;i<message.length();i+=totalSlices){
            answer.append(message.charAt(i));
        }
        return answer.toString();
    }
    
    // Finds encryption keys by assuming that the most common character is the one given
    private int[] tryKeyLength(String encrypted, int klength, char mostCommon) {
        int[] key = new int[klength];
        for(int i=0; i<klength; i+=1){
            String encryptedSlice = sliceString(encrypted,i,klength);
            CaesarCracker encryptedCaesar = new CaesarCracker(mostCommon); // Most common character
            key[i] = encryptedCaesar.getKey(encryptedSlice);
        }
        return key;
    }
    
    // Count the number of real words in lower case that appear in a message
    private int countWords(String message, HashSet<String> dictionary){
        message = message.toLowerCase();
        String[] words = message.split("\\W+");        
        int count = 0;
        for(String word : words){
            if(dictionary.contains(word)){ count += 1; }
        }
        return count;
    }
    
    // Break for all languages
    private String breakForAllLangs(String encrypted, HashMap<String,HashSet<String>> languages){
        int maxWords = 0;
        String maxLanguage = new String();
        String maxDecrypted = new String();
        for(String language : languages.keySet()){
            String decryptedForLanguage = breakForLanguage(encrypted, languages.get(language));
            int currWords = countWords(decryptedForLanguage, languages.get(language));
            if (currWords>maxWords){
                maxLanguage = language;
                maxWords = currWords;
                maxDecrypted = decryptedForLanguage;
            }
        }
        printLine("The language is "+maxLanguage);
        return maxDecrypted;
    }
    
    // Break a VigenereCipher
    private String breakForLanguage(String encrypted, HashSet<String> dictionary){
        int maxWords = 0;
        String maxDecrypted = new String();
        ArrayList<Integer> maxKeys = new ArrayList<Integer>();
        String language = new String();
        // Get key from value
        for(String o: this.dictionaries.keySet()){
            if(this.dictionaries.get(o).equals(dictionary)) { 
                language = o;
            }
        }
        printLine("Trying to break code in "+language);
        char mostCommon = this.mostCommonChar.get(language);
        // Trying all klengths between 1 and 100        
        for(int i=1; i<=100; i+=1){
            int[] myKeys = tryKeyLength(encrypted, i,mostCommon);
            VigenereCipher testBreaker = new VigenereCipher(myKeys);
            String decrypted = testBreaker.decrypt(encrypted);
            int currWords = countWords(decrypted, dictionary);
            if (currWords>maxWords){
                maxWords = currWords;
                maxDecrypted = decrypted;
                maxKeys.clear();
                for(int j=0;j<i;j+=1){ maxKeys.add(myKeys[j]); }
            }
        }
        printLine("The key lenght was " + maxKeys.size());
        for(int i=0;i<maxKeys.size();i+=1){ printLine( maxKeys.get(i) ); }
        printLine("There are a total of "+maxWords+" words");
        return maxDecrypted;
    }
    
    // Main code
    public void breakVigenere () {
        // Read encrypted message
        FileResource fr = new FileResource();
        String encrypted = fr.asString();
        // Trying to break for all languages
        String decrypted = breakForAllLangs(encrypted, this.dictionaries);
        printLine(decrypted.substring(0,300)); 
    }
    
}
