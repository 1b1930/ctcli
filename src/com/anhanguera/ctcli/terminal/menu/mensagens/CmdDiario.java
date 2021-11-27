package com.anhanguera.ctcli.terminal.menu.mensagens;

// import static com.anhanguera.ctcli.terminal.util.CodigosANSI.*;

import com.anhanguera.ctcli.terminal.util.UtilidadesCLI;

// Enums não precisam de import, só chamar os metodos estaticamente
// Ex: System.out.println(Mensagens.VERSAO)

// Todos os enums devem ter uma vírgula depois de ), exceto o último Enum, que devera ter um ;

// esse enum armazena informações sobre os comandos do diario como o comando em si, sua sintaxe, seu atalho e sua Descricao
public enum CmdDiario {
    HEADER("Comandos do Diario"),
    TUTORIAL_SINTAXE("Para usar os comandos do diario, use como prefixo \"diario\" ou o seu atalho \"d\""),
    EXEMPLO_SINTAXE("Ex: diario remover [nome], que e a mesma coisa que \"d r [nome]\""),

    ADICIONAR("adicionar"
    ,"[nome] [calorias consumidas] [nota (opcional)]"
    ,"a"
    ,"adiciona um alimento ao diario."),

    REMOVER("remover"
    ,"[nome]"
    ,"r"
    ,"remove um alimento do diario."),

    PRINT("print"
    ,"[dia] ou [dia/mes] ou palavras-chave \"ontem\" ou \"hoje\""
    ,"p"
    ,"printa o diario para o console."
    +" Se nenhum argumento valido referido a tempo foi dado, printa todo o diario."),

    VOLTAR("voltar"
    ,"voltar ao menu de usuarios."),

    PERMALOGIN("permalogin"
    ,"habilita o login automatico para o usuario atualmente logado."
    ,"[0 ou 1]");





    private final String cmd;
    private final String sintaxe;
    private final String atalho;
    private final String descricao;
    private final String extra;
    private final String info;


    private CmdDiario(String cmd, String sintaxe, String atalho, String descricao) {
        this.cmd = cmd;
        this.sintaxe = sintaxe;
        this.atalho = atalho;
        this.descricao = descricao;
        this.extra = "";
        this.info = "";
    }

    private CmdDiario(String cmd, String sintaxe, String atalho, String descricao, String extra) {
        this.cmd = cmd;
        this.sintaxe = sintaxe;
        this.atalho = atalho;
        this.descricao = descricao;
        this.extra = extra;
        this.info = "";
    }

    private CmdDiario(String info) {
        this.cmd = "";
        this.sintaxe = "";
        this.atalho = "";
        this.descricao = "";
        this.extra = "";
        this.info = info;

    }

    private CmdDiario(String cmd, String atalho, String descricao) {
        this.cmd = cmd;
        this.atalho = atalho;
        this.descricao = descricao;
        this.extra = "";
        this.info = "";
        this.sintaxe = "";

    }

    private CmdDiario(String cmd, String descricao) {
        this.cmd = cmd;
        this.atalho = "";
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

        // se info, extra e sintaxe estiverem em branco mas atalho não estiver em branco, retornar isso
        if(!UtilidadesCLI.isBlankString(atalho)) {
            return cmd+"\n"+"Atalho: "
            +"\""+atalho+"\""+"\n"
            +"Descricao: "+descricao+"\n";

        }

        // finalmente, se info, extra, sintaxe e atalho estiverem em branco, retornar isso
        return cmd+"\n"
        +"Descricao: "+descricao+"\n";

    }


}

