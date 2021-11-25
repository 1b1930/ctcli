package com.anhanguera.ctcli.terminal.menu.mensagens;

import static com.anhanguera.ctcli.terminal.util.CodigosANSI.*;

import com.anhanguera.ctcli.terminal.util.UtilidadesCLI;

// Enums não precisam de import, só chamar os métodos estaticamente
// Ex: System.out.println(Mensagens.VERSAO)

// Todos os enums devem ter uma vírgula depois de ), exceto o último Enum, que deverá ter um ;

// esse enum armazena informações sobre os comandos do diário como o comando em si, sua sintaxe, seu atalho e sua descrição
public enum CmdDiario {
    HEADER(NEGRITO+"Comandos do Diário"+NORMAL),
    TUTORIAL_SINTAXE("Para usar os comandos do diário, use como prefixo \"diario\" ou o seu atalho \"d\""),
    EXEMPLO_SINTAXE("Ex: diario remover [nome], que é a mesma coisa que \"d r [nome]\""),

    ADICIONAR("adicionar"
    ,"[nome] [calorias consumidas] [nota (opcional)]"
    ,"a"
    ,"adiciona um alimento ao diário."),

    REMOVER("remover"
    ,"[nome]"
    ,"r"
    ,"remove um alimento do diário."),

    PRINT("print"
    ,"[dia] ou [dia/mes] ou palavras-chave \"ontem\" ou \"hoje\""
    ,"p"
    ,"printa o diário para o console."
    +" Se nenhum argumento válido referido a tempo foi dado, printa todo o diário."),

    VOLTAR("voltar"
    ,"voltar ao menu de usuários."),

    PERMALOGIN("permalogin"
    ,"habilita o login automático para o usuário atualmente logado."
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
        // se cmd não está em branco e extra estiver em branco, printar
        // cmd + atalho + descrição (sem extra, porque não tem)

        // se info não estiver em branco, retornar isso
        if(!UtilidadesCLI.isBlankString(info)) {
            return info+"\n";
        }

        // se info estiver em branco e extra não estiver em branco, retornar isso
        if(!UtilidadesCLI.isBlankString(extra)) {
            return NEGRITO+cmd+ANSI_RESET+" "+sintaxe+"\n"+"Atalho: "
            +"\""+atalho+"\""+"\n"
            +"Descrição: "+descricao+"\n"+extra+"\n";
        }

        // se info e extra estiverem em branco e sintaxe não estiver em branco, retornar isso
        if(!UtilidadesCLI.isBlankString(sintaxe)) {
            return NEGRITO+cmd+ANSI_RESET+" "+sintaxe+"\n"+"Atalho: "
            +"\""+atalho+"\""+"\n"
            +"Descrição: "+descricao+"\n";

        }

        // se info, extra e sintaxe estiverem em branco mas atalho não estiver em branco, retornar isso
        if(!UtilidadesCLI.isBlankString(atalho)) {
            return NEGRITO+cmd+ANSI_RESET+"\n"+"Atalho: "
            +"\""+atalho+"\""+"\n"
            +"Descrição: "+descricao+"\n";

        }

        // finalmente, se info, extra, sintaxe e atalho estiverem em branco, retornar isso
        return NEGRITO+cmd+ANSI_RESET+"\n"
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

