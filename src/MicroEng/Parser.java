package MicroEng;

import java.util.Scanner;

/**
 * Class to parse the MicroEnglish grammar from the text.
 *
 * @author S. Sigman
 * @version 1 (9/17/2019)
 */
public class Parser {
    // Current token
    private String currentTerminal;

    // List of sentence Tokens
    TokenScanner scanner = null;

    // Error flag: indicates an error occurred
    private boolean errorHappened;


    // Utility Methods
    private void accept (String expectedTerminal) {
        if (currentTerminal.equalsIgnoreCase(expectedTerminal))
           currentTerminal = scanner.nextTerminal();
        else
            writeError("Expected: "+ expectedTerminal+ " -- " +
                       "Found: " + currentTerminal+ ".");
    }

    private void writeError(String err) {
        errorHappened = true;
        System.out.printf("Syntax Error: %s.\n", err);
    }

    //------------------ public methods ------------------------------

    /**
     * Creates a instance of a recursive decent parser for the Micro-English
     * grammar from the book.
     *
     * @param sentence The input to parse.
     */
    public Parser(String sentence) {
        // tokenize the input & get the first token as the currentTerminal
        scanner = new TokenScanner(sentence);
        currentTerminal = scanner.nextTerminal();
        errorHappened = false; // at least not yet
    }

    //------------------ parsing methods ------------------------------

    private void parseSentence() {
        parseSubject();
        parseVerb();
        parseObject();
        accept(".");
    }

    private void parseSubject() {
        if (currentTerminal.equals("I"))
            accept("I");
        else if (currentTerminal.equals("a")) {
            accept("a");
            parseNoun();
        } else if (currentTerminal.equals("the")) {
            accept("the");
            parseNoun();
        }
        else
            writeError("Expected: I, a, or the.  Found:  " + currentTerminal);
    }

    private void parseVerb(){
        if(currentTerminal.equals("like"))
            accept("like");
        else if (currentTerminal.equals("is"))
            accept("is");
        else if (currentTerminal.equals("see"))
            accept("see");
        else if (currentTerminal.equals("sees"))
            accept("sees");
        else
            writeError("Expected like, is, see or sees.  Found:  " + currentTerminal);
    }

    private void parseObject() {
        if (currentTerminal.equals("me"))
            accept("me");
        else if (currentTerminal.equals("a")) {
            accept("a");
            parseNoun();
        } else if (currentTerminal.equals("the")) {
            accept("the");
            parseNoun();
        }
        else
            writeError("Expected: I, a, or the.  Found:  " + currentTerminal);

    }

    private void parseNoun() {
        if(currentTerminal.equals("cat"))
            accept("cat");
        else if (currentTerminal.equals("mat"))
            accept("mat");
        else if (currentTerminal.equals("rat"))
            accept("rat");
        else
            writeError("Expected cat, mat, or rat.  Found:  " + currentTerminal);
    }

    public void parse() {
         parseSentence();
         if(currentTerminal.equals("eol") && !errorHappened) {
             System.out.println("The sentence is in the Micro-English grammar!");
         }
         else
             writeError("Malformed sentence.");
    }

    public static void main(String [] args) {
        System.out.print("Enter a Micro-English sentence ==>  ");
        Scanner in = new Scanner(System.in);
        String sentence = in.nextLine();

        Parser prse = new Parser(sentence);
        prse.parse();
    }
}
