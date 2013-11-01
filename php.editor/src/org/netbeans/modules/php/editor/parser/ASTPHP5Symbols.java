
//----------------------------------------------------
// The following code was generated by CUP v0.11a beta 20060608
// Tue Oct 29 11:48:16 CET 2013
//----------------------------------------------------

package org.netbeans.modules.php.editor.parser;

/** CUP generated interface containing symbol constants. */
public interface ASTPHP5Symbols {
  /* terminals */
  public static final int T_BOOLEAN_AND = 99;
  public static final int T_INLINE_HTML = 10;
  public static final int T_EMPTY = 46;
  public static final int T_PROTECTED = 141;
  public static final int T_CLOSE_RECT = 132;
  public static final int T_IS_NOT_EQUAL = 104;
  public static final int T_INCLUDE = 74;
  public static final int T_QUATE = 147;
  public static final int T_GLOBAL = 42;
  public static final int T_PRINT = 83;
  public static final int T_OR_EQUAL = 92;
  public static final int T_LOGICAL_XOR = 81;
  public static final int T_FUNCTION = 33;
  public static final int T_STATIC = 137;
  public static final int T_NEKUDA = 120;
  public static final int T_THROW = 39;
  public static final int T_CLASS = 48;
  public static final int T_ABSTRACT = 138;
  public static final int T_ENCAPSED_AND_WHITESPACE = 11;
  public static final int T_MOD_EQUAL = 90;
  public static final int T_BREAK = 30;
  public static final int T_WHILE = 15;
  public static final int T_DO = 14;
  public static final int T_CONST = 34;
  public static final int T_CONTINUE = 31;
  public static final int T_FUNC_C = 58;
  public static final int T_DIV = 116;
  public static final int T_LOGICAL_OR = 80;
  public static final int T_DIR = 70;
  public static final int T_OPEN_PARENTHESE = 143;
  public static final int T_REFERENCE = 102;
  public static final int T_COMMA = 79;
  public static final int T_FINALLY = 40;
  public static final int T_ELSE = 136;
  public static final int T_IS_EQUAL = 103;
  public static final int T_LIST = 54;
  public static final int T_NAMESPACE = 68;
  public static final int T_NS_SEPARATOR = 71;
  public static final int T_OR = 100;
  public static final int T_IS_IDENTICAL = 105;
  public static final int T_INC = 121;
  public static final int T_ELSEIF = 135;
  public static final int T_TRY = 37;
  public static final int T_START_NOWDOC = 149;
  public static final int T_PRIVATE = 140;
  public static final int T_UNSET_CAST = 129;
  public static final int T_INCLUDE_ONCE = 75;
  public static final int T_ENDIF = 134;
  public static final int T_SR_EQUAL = 95;
  public static final int EOF = 0;
  public static final int T_PUBLIC = 142;
  public static final int T_OBJECT_OPERATOR = 52;
  public static final int T_TILDA = 119;
  public static final int T_PAAMAYIM_NEKUDOTAYIM = 67;
  public static final int T_IS_SMALLER_OR_EQUAL = 107;
  public static final int T_XOR_EQUAL = 93;
  public static final int T_ENDFOREACH = 20;
  public static final int T_CONSTANT_ENCAPSED_STRING = 12;
  public static final int T_BACKQUATE = 148;
  public static final int T_AT = 130;
  public static final int T_AS = 25;
  public static final int T_CURLY_CLOSE = 66;
  public static final int T_ENDDECLARE = 22;
  public static final int T_CATCH = 38;
  public static final int T_CASE = 28;
  public static final int T_VARIABLE = 8;
  public static final int T_INSTEADOF = 152;
  public static final int T_NEW = 133;
  public static final int T_MINUS_EQUAL = 86;
  public static final int T_PLUS = 113;
  public static final int T_SL_EQUAL = 94;
  public static final int T_ENDWHILE = 16;
  public static final int T_ENDFOR = 18;
  public static final int T_TRAIT = 151;
  public static final int T_CLONE = 24;
  public static final int T_BOOLEAN_OR = 98;
  public static final int T_UNSET = 44;
  public static final int T_INTERFACE = 49;
  public static final int T_SWITCH = 26;
  public static final int T_IS_GREATER_OR_EQUAL = 108;
  public static final int T_OPEN_RECT = 131;
  public static final int T_CURLY_OPEN_WITH_DOLAR = 64;
  public static final int T_FINAL = 139;
  public static final int T_REQUIRE = 77;
  public static final int T_FILE = 60;
  public static final int T_DEC = 122;
  public static final int T_CLOSE_PARENTHESE = 144;
  public static final int T_CLASS_C = 56;
  public static final int T_EVAL = 76;
  public static final int T_RGREATER = 109;
  public static final int T_IS_NOT_IDENTICAL = 106;
  public static final int T_NOT = 118;
  public static final int T_REQUIRE_ONCE = 78;
  public static final int T_NS_C = 69;
  public static final int T_DOLLAR_OPEN_CURLY_BRACES = 63;
  public static final int T_VAR = 43;
  public static final int T_START_HEREDOC = 61;
  public static final int T_ENDSWITCH = 27;
  public static final int T_OBJECT_CAST = 127;
  public static final int T_ECHO = 13;
  public static final int T_LINE = 59;
  public static final int T_FOR = 17;
  public static final int T_IMPLEMENTS = 51;
  public static final int T_ARRAY_CAST = 126;
  public static final int T_DOLLAR = 146;
  public static final int T_TIMES = 115;
  public static final int T_DOUBLE_CAST = 124;
  public static final int T_BOOL_CAST = 128;
  public static final int T_PRECENT = 117;
  public static final int T_LNUMBER = 4;
  public static final int T_CURLY_OPEN = 65;
  public static final int T_DEFINE = 73;
  public static final int T_QUESTION_MARK = 96;
  public static final int T_END_NOWDOC = 150;
  public static final int T_USE = 41;
  public static final int T_KOVA = 101;
  public static final int T_IF = 3;
  public static final int T_MUL_EQUAL = 87;
  public static final int T_ARRAY = 55;
  public static final int T_LGREATER = 110;
  public static final int T_SEMICOLON = 97;
  public static final int T_NEKUDOTAIM = 145;
  public static final int T_VAR_COMMENT = 72;
  public static final int T_CONCAT_EQUAL = 89;
  public static final int T_YIELD = 36;
  public static final int T_AND_EQUAL = 91;
  public static final int T_DNUMBER = 5;
  public static final int T_MINUS = 114;
  public static final int T_FOREACH = 19;
  public static final int T_EXIT = 2;
  public static final int T_DECLARE = 21;
  public static final int T_STRING_VARNAME = 7;
  public static final int T_EXTENDS = 50;
  public static final int T_METHOD_C = 57;
  public static final int T_INT_CAST = 123;
  public static final int T_ISSET = 45;
  public static final int T_LOGICAL_AND = 82;
  public static final int error = 1;
  public static final int T_RETURN = 35;
  public static final int T_DEFAULT = 29;
  public static final int T_SR = 112;
  public static final int T_EQUAL = 84;
  public static final int T_SL = 111;
  public static final int T_END_HEREDOC = 62;
  public static final int T_DOUBLE_ARROW = 53;
  public static final int T_STRING_CAST = 125;
  public static final int T_STRING = 6;
  public static final int T_PLUS_EQUAL = 85;
  public static final int T_INSTANCEOF = 23;
  public static final int T_DIV_EQUAL = 88;
  public static final int T_NUM_STRING = 9;
  public static final int T_HALT_COMPILER = 47;
  public static final int T_GOTO = 32;
}

