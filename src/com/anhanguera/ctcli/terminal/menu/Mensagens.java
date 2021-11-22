package com.anhanguera.ctcli.terminal.menu;

import static com.anhanguera.ctcli.terminal.util.CodigosANSI.*;
import static com.anhanguera.ctcli.terminal.util.SimbolosUnicode.*;

import com.anhanguera.ctcli.Main;

// Enums não precisam de import, só chamar os métodos estaticamente
// Ex: System.out.println(Mensagens.VERSAO)

// Todos os enums devem ter uma vírgula depois de ), exceto o último Enum, que deverá ter um ;

public enum Mensagens {
    VERSAO(NEGRITO+"ctcli"+" v"+Main.VERSAO+ANSI_RESET),
    AUTOLOGIN_SUCCESS("Logado automaticamente"),
    INFO_AUTOLOGIN("Para desabilitar o login automático, digite: permalogin 0"),
    INFO_AJUDA(ANSI_GREEN+"Digite \"ajuda\" para obter os comandos disponíveis."+ANSI_RESET),

    INFO_USER_LOGIN(NEGRITO+"Entre como um usuário para obter acesso aos demais comandos."
    +"\nUse: usuario logar [nome do usuário]"+ANSI_RESET);

    private final String msg;

    private Mensagens(String msg) {
        this.msg = msg;
    }

    public String getMensagem() {
        return msg;
     }

    public void print() {
        System.out.println("\n"+msg);
    }

    // Override de toString(). Possibilita o utilizar sysout.println() pra
    // printar o valor do Enum (string) 
    @Override
    public String toString() {
      return "\n"+msg;
    }


}
