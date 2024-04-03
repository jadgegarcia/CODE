package Parser;

import Lexer.Token;

import java.util.ArrayList;

public class Parser {
    private ArrayList<Token> tokens;
    private int currentTokenIndex;

    public Parser(ArrayList<Token> tokens) {
        this.tokens = tokens;
        this.currentTokenIndex = 0;
    }

    public void parse() {
        program();
    }

    private void program() {
        match("BEGIN");
        variableDeclarations();
        executableCode();
        match("END");
        match("EOF");
    }


    private void variableDeclarations() {
        while (currentTokenIndex < tokens.size() && tokens.get(currentTokenIndex).getType().equals("VAR_DECLARATION")) {
            match("VAR_DECLARATION");
        }
    }


    private void executableCode() {
        while (currentTokenIndex < tokens.size() && !tokens.get(currentTokenIndex).getType().equals("END")) {
            if (tokens.get(currentTokenIndex).getType().equals("ASSIGNMENT")) {
                match("ASSIGNMENT");
            } else if (tokens.get(currentTokenIndex).getType().equals("DISPLAY")) {
                match("DISPLAY");
            } else if (tokens.get(currentTokenIndex).getType().equals("COMMENT")) {
                match("COMMENT");
            } else if (tokens.get(currentTokenIndex).getType().equals("NEWLINE")) {
                match("NEWLINE");
            } else {
                // Unexpected token
                throw new RuntimeException("Syntax error: Unexpected token " + tokens.get(currentTokenIndex).getValue());
            }
        }
        // Check for the "END" token after parsing each statement
        if (currentTokenIndex < tokens.size() && tokens.get(currentTokenIndex).getType().equals("END")) {
            match("END");
        }
    }


    private void match(String expectedTokenType) {
        if (currentTokenIndex < tokens.size() && tokens.get(currentTokenIndex).getType().equals(expectedTokenType)) {
            currentTokenIndex++;
        } else {
            throw new RuntimeException("Syntax error: Expected " + expectedTokenType +
                    ", but found " + (currentTokenIndex < tokens.size() ? tokens.get(currentTokenIndex).getType() : "EOF"));
        }
    }
}
