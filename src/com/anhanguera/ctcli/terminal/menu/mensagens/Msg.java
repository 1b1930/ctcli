package com.anhanguera.ctcli.terminal.menu.mensagens;

import static com.anhanguera.ctcli.terminal.util.CodigosANSI.*;

import com.anhanguera.ctcli.Main;

// Enums não precisam de import, só chamar os métodos estaticamente
// Ex: System.out.println(Mensagens.VERSAO)

// Todos os enums devem ter uma vírgula depois de ), exceto o último Enum, que deverá ter um ;

// armazena todas as mensagens que o menu pode querer usar

public enum Msg {
    VERSAO(NEGRITO+"ctcli"+" v"+Main.VERSAO+ANSI_RESET),
    AUTOLOGIN_SUCCESS("Logado automaticamente"),

    INFO_PRIMEIRAEXEC("Essa é a primeira vez que o programa está sendo executado."
    +"\n\nPara começar, crie um usuário usando o comando 'usuario adicionar'"),

    
    INFO_AUTOLOGIN("Para desabilitar o login automático, digite: permalogin 0"),
    INFO_AJUDA(ANSI_GREEN+"Digite \"ajuda\" para visualizar a lista de comandos."+ANSI_RESET),
    INFO_AJUDA_USUARIO(ANSI_GREEN+"Digite \"ajuda\" para visualizar a lista completa de comandos disponíveis nessa área."+ANSI_RESET),
    INFO_AJUDA_DIARIO(ANSI_GREEN+"Digite \"diario ajuda\" para visualizar a lista de comandos do diário."+ANSI_RESET),

    INFO_USER_LOGIN("Entre como um usuário para obter acesso aos comandos do diário."
    +"\nUse: usuario logar [nome do usuário]"),
    EXP_TDEE("Para monitorar ganho ou perda de peso, é recomendado calcular o seu TDEE"
    +", sigla em inglês para gasto diário total de energia.\n"
    +"Esse valor é representado em calorias e é usado para o controle de ingestão de calorias.\n"
    +"Se você ingeriu menos calorias que o seu TDEE em um dia, significa que você perdeu peso.\n"
    +"\nPara calcular o TDEE, são necessários os dados: peso, altura, idade, sexo, e nivel de atividade física."),
    EXP_GETNIVELATV("\nSelecione seu nível de atividade física"+"\n1. Sedentário\n2. Exercício leve\n"
    +"3. Exercício moderado\n4. Exercício intenso\n5. Exercício muito intenso (Atleta)"),
    USUARIO_CRIADO_SUCESSO("Usuário criado com sucesso."
    +"\n\nEntre como um usuário para obter acesso aos comandos do diário!"
    +"\nUse: usuario logar [nome do usuário]"),
    USUARIO_REMOVIDO_SUCESSO("Usuário removido"),
    USUARIO_EDITADO_SUCESSO("Usuário editado com sucesso."),
    USUARIO_LOGADO_SUCESSO("Logado com sucesso.\n"),
    INFO_MENU_DIARIO("Você está no menu do diário."
    +"\n"
    +ANSI_GREEN+"\nDigite \"ajuda\" para obter os comandos disponíveis neste submenu.\n"
    +"\nDigite \"permalogin 1\" para habilitar login automático para esse usuário."+ANSI_RESET),
    CSV_LIMPO("CSV limpo."),
    PERMALOGIN_HABILITADO("Permalogin habilitado para esse usuário."),
    PERMALOGIN_DESABILITADO("Permalogin desabilitado para esse usuário."),
    DIARIO_ALIMENTO_ADICIONAR_SUCESSO("Alimento adicionado com sucesso."),
    DIARIO_ALIMENTO_REMOVER_SUCESSO("Alimento removido com sucesso."),
    DIARIO_FAXINA("Diário limpo.");





    




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

