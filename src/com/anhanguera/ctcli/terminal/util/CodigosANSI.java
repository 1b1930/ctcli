package com.anhanguera.ctcli.terminal.util;

// declarando as constantes em um arquivo separado pra ficar mais limpo.
// ATENÇÃO: usar "final" ao criar classe e usar "import static "Constantes.*"" 
// no arquivo onde deseja essas constantes

public final class CodigosANSI {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String NEGRITO = "\033[0;1m";
    public static final String ESTL = "\t\033[0;1m*\033[0;0m ";
    public static final String NORMAL = "\033[0;0m";
    public static final String SUBL = "\033[0;4m";




    
}
