package Lexer;

public enum TokenType {
    KEYWORD, // Reserved words
    IDENTIFIER,
    NUMBER,
    CHAR_LITERAL,
    BOOL_LITERAL,
    STRING_LITERAL,
    OPERATOR,
    DELIMITER,
    NEWLINE,
    CONCATENATOR,
    L_ESCAPE,
    R_ESCAPE,
    COMMENT,
    EOF_TOKEN
}
