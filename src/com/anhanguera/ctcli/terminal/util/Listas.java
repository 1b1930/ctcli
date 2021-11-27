package com.anhanguera.ctcli.terminal.util;

public class Listas {

    public static String[] separarElementos(String elemento) {

        String elemString = elemento.replaceAll("[\\[\\]]", "").trim();
        String[] elemSplit = elemString.split(",");
        return elemSplit;

    }

}
