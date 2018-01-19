
/**
 * Purpose:
 * Used to encrypt and decrypt messages with a simpple substitution cipher:
 * Each letter in the message is replaced by a letter a variable number of
 * positions down the alphabet based on a key word.
 * 
 * @author Felipe Lopez
 * @version Jan 2018
 */

import edu.duke.*;
import java.lang.*;
import java.util.*;

public class VigenereCipher {
    
    // FIELDS
    CaesarCipher[] ciphers;
    
    // CONSTRUCTOR
    public VigenereCipher(int[] key){
        ciphers = new CaesarCipher[key.length];
        for (int i = 0; i < key.length; i++) {
            this.ciphers[i] = new CaesarCipher(key[i]);
        }
    }
    
    // METHODS
    // Generic method for easier printing
    private <T> void printLine(T t) { System.out.println( t ); }
    
    // Encrypt a string
    public String encrypt(String input) {
        StringBuilder answer = new StringBuilder();
        int i = 0;
        for (char c : input.toCharArray()) {
            int cipherIndex = i % ciphers.length;
            CaesarCipher thisCipher = this.ciphers[cipherIndex];
            answer.append(thisCipher.encryptLetter(c));
            i++;
        }
        return answer.toString();
    }
    
    // Decrypt a string
    public String decrypt(String input) {
        StringBuilder answer = new StringBuilder();
        int i = 0;
        for (char c : input.toCharArray()) {
            int cipherIndex = i % ciphers.length;
            CaesarCipher thisCipher = this.ciphers[cipherIndex];
            answer.append(thisCipher.decryptLetter(c));
            i++;
        }
        return answer.toString();
    }
    
    // Print
    public String toString() {
        return Arrays.toString(ciphers);
    }
    
    /*
    // Helper methods
    public String[] sliceString(String input){
        int wordLength = this.keyword.length();
        String[] slices = new String[wordLength];       
        for(int i=0; i<wordLength; i+=1){
            StringBuilder tempString = new StringBuilder();
            for(int j=i; j<input.length(); j+=wordLength){ tempString.append( input.substring(j,j+1) ); }
            slices[i] = tempString.toString() ;
        }
        return slices;
    }
    
    // Encryption methods with key
    public String encrypt(String input){       
        String[] inputSlice = sliceString(input);
        String[] encryptedSlice = new String[inputSlice.length];
        StringBuilder encryptedMessage = new StringBuilder();
        // Encrypt individual Caesar Ciphers
        for(int i=0; i<this.keyword.length(); i+=1){
            CaesarCipher encryptSlice = new CaesarCipher( this.arrayKeys.get(i) );
            encryptedSlice[i] =  encryptSlice.encrypt(inputSlice[i]);          
        }
        // Combine into one String
        for(int i=0; i<input.length(); i+=1){
            encryptedMessage.append( encryptedSlice[i % this.keyword.length()].charAt(i / this.keyword.length()) );
        }        
        return encryptedMessage.toString();        
    }
    
    public String decrypt(String encrypted){
        String[] encryptedSlice = sliceString(encrypted);
        String[] decryptedSlice = new String[encryptedSlice.length];
        StringBuilder decryptedMessage = new StringBuilder();
        // Decrypt individual Caesar Ciphers
        for(int i=0; i<this.keyword.length(); i+=1){
            CaesarCipher decryptSlice = new CaesarCipher( this.arrayKeys.get(i) );
            decryptedSlice[i] =  decryptSlice.decrypt(encryptedSlice[i]);
        }
        // Combine into one String
        for(int i=0; i<encrypted.length(); i+=1){
            decryptedMessage.append( decryptedSlice[i % this.keyword.length()].charAt(i / this.keyword.length()) );
        }    
        return decryptedMessage.toString();
    } */
    
}
