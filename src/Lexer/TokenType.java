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
    COMMENT,
    EOF_TOKEN
}
