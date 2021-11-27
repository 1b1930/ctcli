package com.anhanguera.ctcli.terminal.menu.mensagens;

// import static com.anhanguera.ctcli.terminal.util.CodigosANSI.*;

import com.anhanguera.ctcli.terminal.util.UtilidadesCLI;


// Enums não precisam de import, só chamar os métodos estaticamente
// Ex: System.out.println(Mensagens.VERSAO)

// Todos os enums devem ter uma vírgula depois de ), exceto o último Enum, que devera ter um ;

// esse enum armazena informações sobre os comandos do usuario, como o comando em si, sua sintaxe, seu atalho e sua Descricao

public enum CmdUsuario {

    HEADER("Comandos de Usuario"),

    TUTORIAL_SINTAXE("Para usar os comandos de usuario, use como prefixo \"usuario\" ou o seu atalho \"u\""
    +"\nEx: usuario remover [usuario]"),

    ADICIONAR("adicionar"
    ,"[nome] [peso (kg)] [altura (cm)] [idade] [sexo (F ou M])"
    ,"a"
    ,"adiciona um usuario a base de dados."),

    EDITAR("editar"
    , "[nome] [propriedade a ser alterada] [novo valor]"
    ,"e"
    ,"edita uma propriedade do usuario dado como argumento."
    ,"Propriedades validas: nome, peso, altura, idade, nivelatv"),

    REMOVER("remover"
    ,"[nome]"
    ,"p"
    ,"remove o usuario dado como argumento."),

    PRINT("print"
    ,"[nome]"
    ,"p"
    ,"printa os dados do usuario dado como argumento."),

    PRINTALL("printall"
    ,"pa"
    ,"printa todos os usuarios salvos na base de dados."),

    LOGAR("logar"
    ,"[nome do usuario]"
    ,"l"
    ,"loga como o usuario dado como argumento.");




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
        // se cmd não esta em branco e extra estiver em branco, printar
        // cmd + atalho + Descricao (sem extra, porque não tem)

        // se info não estiver em branco, retornar isso
        if(!UtilidadesCLI.isBlankString(info)) {
            return info+"\n";
        }

        // se info estiver em branco e extra não estiver em branco, retornar isso
        if(!UtilidadesCLI.isBlankString(extra)) {
            return cmd+" "+sintaxe+"\n"+"Atalho: "
            +"\""+atalho+"\""+"\n"
            +"Descricao: "+descricao+"\n"+extra+"\n";
        }

        // se info e extra estiverem em branco e sintaxe não estiver em branco, retornar isso
        if(!UtilidadesCLI.isBlankString(sintaxe)) {
            return cmd+" "+sintaxe+"\n"+"Atalho: "
            +"\""+atalho+"\""+"\n"
            +"Descricao: "+descricao+"\n";

        }

        // finalmente, se info, extra e sintaxe estiverem em branco, retornar isso
        return cmd+"\n"+"Atalho: "
        +"\""+atalho+"\""+"\n"
        +"Descricao: "+descricao+"\n";

    }


}
