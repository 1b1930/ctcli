package com.anhanguera.ctcli.terminal.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Datas {

    // basicamente remove a hora de uma data LocalDateTime
    public static LocalDate converterDiarioLDTparaLD(String data) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        LocalDateTime ldt = LocalDateTime.parse(data.trim().replace("-", " "), dtf);
        LocalDate ld = ldt.toLocalDate();
        return ld;

    }

}
