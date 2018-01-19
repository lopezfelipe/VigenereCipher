/**
 * Purpose:
 * Crack a simple CaesarCipher by maximizing the appearances of the
 * most common character in a language.
 * 
 * @author Felipe Lopez
 * @version Jan 2018
 */

import edu.duke.*;

public class CaesarCracker {

    // FIELDS
    char mostCommon;
    
    // CONSTRUCTOR
    public CaesarCracker() {
        this.mostCommon = 'e'; // e is the most common char in English
    }
    
    public CaesarCracker(char c) {
        mostCommon = c; // If another char is given for a diff language
    }
    
    //METHODS
    // Count occurrences of each letter of the alphabet in an array of ints
    public int[] countLetters(String message){
        String alph = "abcdefghijklmnopqrstuvwxyz";
        int[] counts = new int[26];
        for(int k=0; k < message.length(); k++){
            int dex = alph.indexOf(Character.toLowerCase(message.charAt(k)));
            if (dex != -1){
                counts[dex] += 1;
            }
        }
        return counts;
    }
    
    // Find the order of max occurrences
    public int maxIndex(int[] vals){
        int maxDex = 0;
        for(int k=0; k < vals.length; k++){
            if (vals[k] > vals[maxDex]){
                maxDex = k;
            }
        }
        return maxDex;
    }

    // Break key assuming the most common character
    public int getKey(String encrypted){
        int[] freqs = countLetters(encrypted);
        int maxDex = maxIndex(freqs);
        int mostCommonPos = this.mostCommon - 'a'; // Substracting chars in Java is allowed
        int dkey = maxDex - mostCommonPos;
        if (maxDex < mostCommonPos) {
            dkey = 26 - (mostCommonPos-maxDex);
        }
        return dkey;
    }
    
    // Decrypt message using getKey
    public String decrypt(String encrypted){
        int key = getKey(encrypted);
        CaesarCipher cc = new CaesarCipher(key);
        return cc.decrypt(encrypted);        
    }
   
}
