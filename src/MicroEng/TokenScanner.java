package MicroEng;


import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Simple lexical scanner class to Micro-English grammar.
 *
 * @author S. Sigman
 * @version 1.0
 */
public class TokenScanner {

    // List of tokens
    private ArrayList<String> tokens = null;

    // pattern to identify tokens which end with a .
    private final String endsWithPeriodRE = "^[a-z]*\\.$";

    // index of the next token to process
    private int nextToken;


    /**
     * Constructor to build scanner from the current line on System.in.
     */
    public TokenScanner(String line) {
       Scanner sent = new Scanner(line);
       tokens = new ArrayList<>();

       // load the tokens
       while (sent.hasNext()) {
           String temp = sent.next();

           //Check for a . on the end of temp
           if (Pattern.matches(endsWithPeriodRE, temp)){
               String prefix = temp.substring(0,temp.length()-1);
               tokens.add(prefix);
               tokens.add(".");
           }
           else
               tokens.add(temp);
       }
        tokens.add("eol");

        // set the next token to the first token
        nextToken = 0;
    }

    public String nextTerminal() {
        if (nextToken < tokens.size()) {
            nextToken++;
            return tokens.get(nextToken-1);
        }
        else
            return null;

    }

    public boolean hasTerminals() {
        return nextToken == tokens.size();
    }

    public static void main(String [] args) {
        TokenScanner ts = new TokenScanner("the cat sees a rat.");
    }
}
