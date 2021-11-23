package com.anhanguera.ctcli.terminal.menu.mensagens;

import static com.anhanguera.ctcli.terminal.util.CodigosANSI.*;

// esse enum armazena informações sobre os comandos globais, como o comando em si, seu atalho e sua descrição

public enum CmdGlobal {
    HEADER(NEGRITO+"Comandos Globais"+NORMAL),
    GLOBAL_CMD_INFO(ANSI_GREEN+"Esses comandos não necessitam de um prefixo."+ANSI_RESET),
    
    SAIR("sair"
    ,"s"
    ,"sai do programa."),

    CLEAR("clear"
    ,"c"
    ,"limpa a tela do terminal (se for possível).");
    


    private final String cmd;
    private final String atalho;
    private final String descricao;
    private final String info;

    private CmdGlobal(String cmd, String atalho, String descricao) {
        this.cmd = cmd;
        this.atalho = atalho;
        this.descricao = descricao;
        this.info = "";
    }
    
    private CmdGlobal(String info) {
        this.info = info;
        this.cmd = "";
        this.atalho = "";
        this.descricao = "";
    }

    public String getComando() {
        return cmd;
    }

    public String getAtalho() {
        return atalho;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getInfo() {
        return info;
    }

    @Override
    public String toString() {

        // se info não estiver em branco, retornar isso
        if(!info.isBlank()) {
            return info+"\n";
        }

        // se estiver em branco sim, retornar isso
        return NEGRITO+cmd+ANSI_RESET+"\n"+"Atalho: "
        +"\""+atalho+"\""+"\n"
        +"Descrição: "+descricao+"\n";



    }
}
