package com.anhanguera.ctcli.terminal.menu.mensagens;

// import static com.anhanguera.ctcli.terminal.util.CodigosANSI.*;

import com.anhanguera.ctcli.Main;

// Enums nao precisam de import, só chamar os metodos estaticamente
// Ex: System.out.println(Mensagens.VERSAO)

// Todos os enums devem ter uma virgula depois de ), exceto o último Enum, que devera ter um ;

// armazena todas as mensagens que o menu pode querer usar

public enum Msg {
    VERSAO("ctcli"+" v"+Main.VERSAO),
    AUTOLOGIN_SUCCESS("Logado automaticamente"),

    INFO_PRIMEIRAEXEC("Essa e a primeira vez que o programa esta sendo executado."
    +"\n\nPara começar, crie um usuario usando o comando 'usuario adicionar'"),

    
    INFO_AUTOLOGIN("Para desabilitar o login automatico, digite: permalogin 0"),
    INFO_AJUDA("Digite \"ajuda\" para visualizar a lista de comandos."),
    INFO_AJUDA_USUARIO("Digite \"usuario ajuda\" para visualizar a lista completa de comandos de usuarios disponiveis nessa área."),
    INFO_AJUDA_DIARIO("Digite \"diario ajuda\" para visualizar a lista de comandos do diario."),

    INFO_USER_LOGIN("Entre como um usuario para obter acesso aos comandos do diario."
    +"\nUse: usuario logar [nome do usuario]"),
    EXP_TDEE("Para monitorar ganho ou perda de peso, e recomendado calcular o seu TDEE"
    +", sigla em ingles para gasto diario total de energia.\n"
    +"Esse valor e representado em calorias e e usado para o controle de ingestao de calorias.\n"
    +"Se voce ingeriu menos calorias que o seu TDEE em um dia, significa que voce perdeu peso.\n"
    +"\nPara calcular o TDEE, sao necessarios os dados: peso, altura, idade, sexo, e nivel de atividade fisica."),
    EXP_GETNIVELATV("\nSelecione seu nivel de atividade fisica"+"\n1. Sedentario\n2. Exercicio leve\n"
    +"3. Exercicio moderado\n4. Exercicio intenso\n5. Exercicio muito intenso (Atleta)"),
    USUARIO_CRIADO_SUCESSO("Usuario criado com sucesso."
    +"\n\nEntre como um usuario para obter acesso aos comandos do diario!"
    +"\nUse: usuario logar [nome do usuario]"),
    USUARIO_REMOVIDO_SUCESSO("Usuario removido"),
    USUARIO_EDITADO_SUCESSO("Usuario editado com sucesso."),
    USUARIO_LOGADO_SUCESSO("Logado com sucesso.\n"),
    INFO_MENU_DIARIO("Voce esta no menu do diario."
    +"\n"+"\nDigite \"ajuda\" para obter os comandos disponiveis neste submenu.\n"
    +"\nDigite \"permalogin 1\" para habilitar login automatico para esse usuario."),
    CSV_LIMPO("CSV limpo."),
    PERMALOGIN_HABILITADO("Permalogin habilitado para esse usuario."),
    PERMALOGIN_DESABILITADO("Permalogin desabilitado para esse usuario."),
    DIARIO_ALIMENTO_ADICIONAR_SUCESSO("Alimento adicionado com sucesso."),
    DIARIO_ALIMENTO_REMOVER_SUCESSO("Alimento removido com sucesso."),
    DIARIO_FAXINA("Diario limpo."),
    CLEARSC("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");





    




    private final String msg;

    private Msg(String msg) {
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

