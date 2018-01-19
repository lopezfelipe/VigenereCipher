/**
 * Purpose:
 * Used to encrypt and decrypt messages with a simpple substitution cipher:
 * Each letter in the message is replaced by a letter some fixed number of
 * positions down the alphabet, e.g., using 3 as a key would replace A by D
 * and B by E.
 * 
 * @author Felipe Lopez
 * @version Jan 2018
 */

import edu.duke.*;
import java.lang.*;

public class CaesarCipher {
    
    // FIELDS
    private String alphabet;
    private String shiftedAlphabet;
    private int theKey;
    
    // CONSTRUCTOR
    public CaesarCipher(int key) {
        this.theKey = key;
        this.alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        this.shiftedAlphabet = this.alphabet.substring(key) +
                               this.alphabet.substring(0,key);
        this.alphabet = this.alphabet + this.alphabet.toLowerCase();
        this.shiftedAlphabet = this.shiftedAlphabet + this.shiftedAlphabet.toLowerCase();
    }
    
    // METHODS
    // Generic method for easier printing
    private <T> void printLine(T t) { System.out.println( t ); }
    
    // Transform a letter
    private char transformLetter(char c, String from, String to) {
        int idx = from.indexOf(c);
        if (idx != -1) { return to.charAt(idx); }
        return c;
    }
    
    // Encrypt a letter
    public char encryptLetter(char c) {
        return transformLetter(c, this.alphabet, this.shiftedAlphabet);
    }
    
    // Decrypt a letter
    public char decryptLetter(char c) {
        return transformLetter(c, this.shiftedAlphabet, this.alphabet);
    }
    
    // Transform an input string following two reference "alphabets"
    private String transform(String input, String from, String to){
        StringBuilder sb = new StringBuilder(input);
        for (int i = 0; i < sb.length(); i++) {
            char c = sb.charAt(i);
            c = transformLetter(c, from, to);
            sb.setCharAt(i, c);
        }
        return sb.toString();
    }
    
    
    // Encryption using key
    public String encrypt(String input){
        return transform(input, this.alphabet, this.shiftedAlphabet);
    }
    
    // Decrypt using key
    public String decrypt(String input) {
        return transform(input, this.shiftedAlphabet, this.alphabet);
    }
    
    // When prompted, print the key
    public String toString() {
        return "" + this.theKey;
    }
    
}
