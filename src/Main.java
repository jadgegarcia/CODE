import Lexer.LexicalAnalyzer;
import Lexer.Token;
import Parser.Parser;

import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        String code = "# this is a sample program in CODE\n" +
                "BEGIN CODE\n" +
                "INT x, y, z = 5\n" +
                "CHAR a_1 = 'n'\n" +
                "BOOL t = TRUE\n" +
                "x = y = 4\n" +
                "a_1='c'\n" +
                "# this is a comment\n" +
                "DISPLAY: x & t & z & $ & a_1 & [#] & \"last\"\n" +
                "END CODE";

        // Tokenize the code
        List<Token> tokens = LexicalAnalyzer.tokenize(code);

        // Traverse and print tokens
        for (Token token : tokens) {
            System.out.println(token);
        }

    }
}

