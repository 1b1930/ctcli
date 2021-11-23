package com.anhanguera.ctcli.terminal.menu.mensagens;

import static com.anhanguera.ctcli.terminal.util.CodigosANSI.*;


// Enums não precisam de import, só chamar os métodos estaticamente
// Ex: System.out.println(Mensagens.VERSAO)

// Todos os enums devem ter uma vírgula depois de ), exceto o último Enum, que deverá ter um ;

// esse enum armazena informações sobre os comandos do usuário, como o comando em si, sua sintaxe, seu atalho e sua descrição

public enum CmdUsuario {

    HEADER(NEGRITO+"Comandos de Usuário"+NORMAL),

    TUTORIAL_SINTAXE(ANSI_GREEN+"Para usar os comandos de usuário, use como prefixo \"usuario\" ou o seu atalho \"u\""
    +"\nEx: usuario remover [usuario]"+ANSI_RESET),

    ADICIONAR("adicionar"
    ,"[nome] [peso (kg)] [altura (cm)] [idade] [sexo (F ou M])"
    ,"a"
    ,"adiciona um usuário à base de dados."),

    EDITAR("editar"
    , "[nome] [propriedade a ser alterada] [novo valor]"
    ,"e"
    ,"edita uma propriedade do usuário dado como argumento."
    ,"Propriedades válidas: nome, peso, altura, idade, nivelatv"),

    REMOVER("remover"
    ,"[nome]"
    ,"p"
    ,"remove o usuário dado como argumento."),

    PRINT("print"
    ,"[nome]"
    ,"p"
    ,"printa os dados do usuário dado como argumento."),

    PRINTALL("printall"
    ,"pa"
    ,"printa todos os usuários salvos na base de dados."),

    LOGAR("logar"
    ,"[nome do usuário]"
    ,"l"
    ,"loga como o usuário dado como argumento.");




    private final String cmd;
    private final String sintaxe;
    private final String atalho;
    private final String descricao;
    private final String extra;
    private final String info;


    private CmdUsuario(String cmd, String sintaxe, String atalho, String descricao) {
        this.cmd = cmd;
        this.sintaxe = sintaxe;
        this.atalho = atalho;
        this.descricao = descricao;
        this.extra = "";
        this.info = "";
    }

    private CmdUsuario(String cmd, String sintaxe, String atalho, String descricao, String extra) {
        this.cmd = cmd;
        this.sintaxe = sintaxe;
        this.atalho = atalho;
        this.descricao = descricao;
        this.extra = extra;
        this.info = "";
    }

    private CmdUsuario(String info) {
        this.cmd = "";
        this.sintaxe = "";
        this.atalho = "";
        this.descricao = "";
        this.extra = "";
        this.info = info;

    }

    private CmdUsuario(String cmd, String atalho, String descricao) {
        this.cmd = cmd;
        this.atalho = atalho;
        this.descricao = descricao;
        this.extra = "";
        this.info = "";
        this.sintaxe = "";

    }

    public String getComando() {
        return cmd;
     }

    public String getSintaxe() {
        return "Sintaxe: "+cmd+" "+sintaxe;
    }

    public String getJustSintaxe() {
        return cmd+" "+sintaxe;
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

    // Override de toString(). Possibilita o utilizar sysout.println() pra
    // printar o valor do Enum (string) 
    @Override
    public String toString() {
        // se cmd não está em branco e extra estiver em branco, printar
        // cmd + atalho + descrição (sem extra, porque não tem)

        // se info não estiver em branco, retornar isso
        if(!info.isBlank()) {
            return info+"\n";
        }

        // se info estiver em branco e extra não estiver em branco, retornar isso
        if(!extra.isBlank()) {
            return NEGRITO+cmd+ANSI_RESET+" "+sintaxe+"\n"+"Atalho: "
            +"\""+atalho+"\""+"\n"
            +"Descrição: "+descricao+"\n"+extra+"\n";
        }

        // se info e extra estiverem em branco e sintaxe não estiver em branco, retornar isso
        if(!sintaxe.isBlank()) {
            return NEGRITO+cmd+ANSI_RESET+" "+sintaxe+"\n"+"Atalho: "
            +"\""+atalho+"\""+"\n"
            +"Descrição: "+descricao+"\n";

        }

        // finalmente, se info, extra e sintaxe estiverem em branco, retornar isso
        return NEGRITO+cmd+ANSI_RESET+"\n"+"Atalho: "
        +"\""+atalho+"\""+"\n"
        +"Descrição: "+descricao+"\n";







        // if(StringUtils.isNoneBlank(cmd,sintaxe,atalho,descricao,extra)) {
        //     return NEGRITO+cmd+ANSI_RESET+" "+sintaxe+"\n"+"Atalho: "
        //     +"\""+atalho+"\""+"\n"
        //     +"Descrição: "+descricao+"\n"+extra+"\n";
        // }

        // if(StringUtils.isNoneBlank(cmd,atalho,descricao) && extra.isBlank()) {
        //     return NEGRITO+cmd+ANSI_RESET+" "+sintaxe+"\n"+"Atalho: "
        //     +"\""+atalho+"\""+"\n"
        //     +"Descrição: "+descricao+"\n";

        // }

        // // se não, verificar se cmd e extra estão em branco
        // // se sim, quer dizer que só tem um campo, info
        // if(StringUtils.isAllBlank(cmd,sintaxe,atalho,descricao,extra) && !info.isBlank()) {
        //     return info+"\n";

        // }

        // // if(StringUtils.isNoneBlank(cmd,atalho,descricao) && StringUtils.isAllBlank(extra,info,sintaxe)) {

        // // }
    

    }


}
