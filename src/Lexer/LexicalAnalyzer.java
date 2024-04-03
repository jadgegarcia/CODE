package Lexer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LexicalAnalyzer {
    // Define reserved keywords
    private static final List<String> keywords = Arrays.asList("BEGIN", "CODE", "END", "INT", "CHAR", "BOOL", "FLOAT", "IF", "ELSE", "WHILE", "DISPLAY", "SCAN");

    // Function to check if a string is a keyword
    private static boolean isKeyword(String word) {
        return keywords.contains(word);
    }

    private static boolean isDigit(String str) {
        for (int i = 0; i < str.length(); i++) {
            // Check if the current character matches the target letter
            if (!Character.isDigit(str.charAt(i))) {

                return false; // If found, exit the loop
            }
        }
        return true;
    }

    // Lexer function to tokenize the input code
    public static List<Token> tokenize(String code) {
        List<Token> tokens = new ArrayList<>();
        StringBuilder token = new StringBuilder();
        boolean inStringLiteral = false;
        boolean inComment = false;

        for (int i = 0; i < code.length(); i++) {
            char c = code.charAt(i);

            if (c == '#') {
                inComment = true;
            }

            if (inComment) {
                if (c == '\n') {
                    inComment = false;
                }
                continue;
            }

            if (Character.isWhitespace(c)) {
                if (token.length() > 0) {

                    if (isKeyword(token.toString())) {
                        tokens.add(new Token(TokenType.KEYWORD, token.toString()));
                    } else if(isDigit(token.toString())) {
                        tokens.add(new Token(TokenType.NUMBER, token.toString()));
                    } else {
                        if(token.toString().equals("TRUE") || token.toString().equals("FALSE")) {
                            tokens.add(new Token(TokenType.BOOL_LITERAL, token.toString()));
                        }else {
                            tokens.add(new Token(TokenType.IDENTIFIER, token.toString()));
                        }
                    }
                    token.setLength(0);
                }
            } else if (c == '$') {
                if (token.length() > 0) {
                    tokens.add(new Token(TokenType.STRING_LITERAL, token.toString()));
                    token.setLength(0);
                }
                tokens.add(new Token(TokenType.NEWLINE, "$"));
            } else if (c == '&') {
                if (token.length() > 0) {
                    tokens.add(new Token(TokenType.STRING_LITERAL, token.toString()));
                    token.setLength(0);
                }
                tokens.add(new Token(TokenType.CONCATENATOR, "&"));
            } else if (c == '[') {
                if (token.length() > 0) {
                    tokens.add(new Token(TokenType.STRING_LITERAL, token.toString()));
                    token.setLength(0);
                } else {
                    tokens.add(new Token(TokenType.L_ESCAPE, "["));
                }
                StringBuilder escapeCode = new StringBuilder();
                i++;
                while (i < code.length() && code.charAt(i+1) == ']') {
                    escapeCode.append(code.charAt(i));
                    i++;
                }
                if (i == code.length()) {
                    // Error: Missing closing ']'
                    break;
                }
                tokens.add(new Token(TokenType.STRING_LITERAL, escapeCode.toString()));
            } else if(c == ']') {
                if (token.length() > 0) {
                    tokens.add(new Token(TokenType.STRING_LITERAL, token.toString()));
                    token.setLength(0);
                } else {
                    tokens.add(new Token(TokenType.R_ESCAPE, "["));
                }
            } else if (Character.isLetter(c) || (Character.compare(c, '_') == 0)) {

                token.append(c);
                if(i == (code.length() - 1)) {
//                    tokens.add(new Token(TokenType.EOF_TOKEN, "PROBLEM"));
                    if (isKeyword(token.toString())) {
                        tokens.add(new Token(TokenType.KEYWORD, token.toString()));
                        token.setLength(0);
                    } else {
                        //ERRORR
                    }
                }
            } else if (Character.isDigit(c)) {
                token.append(c);

            } else if (c == '+' || c == '-' || c == '*' || c == '/' || c == '%' || c == '>' || c == '<' || c == '=') {
                if (token.length() > 0) {
                    tokens.add(new Token(TokenType.STRING_LITERAL, token.toString()));
                    token.setLength(0);
                }
                tokens.add(new Token(TokenType.OPERATOR, String.valueOf(c)));
            } else if (c == '\'') {
                if (token.length() > 0) {
                    tokens.add(new Token(TokenType.STRING_LITERAL, token.toString()));
                    token.setLength(0);
                }
                StringBuilder charLiteral = new StringBuilder("'");
                i++;
                while (i < code.length() && code.charAt(i) != '\'') {
                    charLiteral.append(code.charAt(i));
                    i++;
                }
                if (i == code.length()) {
                    // Error: Missing closing '\''
                    break;
                }
                charLiteral.append("'");
                tokens.add(new Token(TokenType.CHAR_LITERAL, charLiteral.toString()));
            } else if (c == '"') {
                if (token.length() > 0) {
                    tokens.add(new Token(TokenType.STRING_LITERAL, token.toString()));
                    token.setLength(0);
                }
                StringBuilder stringLiteral = new StringBuilder("\"");
                i++;
                while (i < code.length() && code.charAt(i) != '"') {
                    if (code.charAt(i) == '[') {
                        // Escape sequence detected
                        StringBuilder escapeCode = new StringBuilder("[");
                        i++;
                        while (i < code.length() && code.charAt(i) != ']') {
                            escapeCode.append(code.charAt(i));
                            i++;
                        }
                        if (i == code.length()) {
                            // Error: Missing closing ']'
                            break;
                        }
                        escapeCode.append("]");
                        stringLiteral.append(escapeCode);
                    } else {
                        stringLiteral.append(code.charAt(i));
                    }
                    i++;
                }
                if (i == code.length()) {
                    // Error: Missing closing '"'
                    break;
                }
                stringLiteral.append("\"");
                tokens.add(new Token(TokenType.STRING_LITERAL, stringLiteral.toString()));
            }
        }

        if (token.length() > 0) {
            tokens.add(new Token(TokenType.STRING_LITERAL, token.toString()));
        }

        return tokens;
    }
}