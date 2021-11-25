package com.anhanguera.ctcli.terminal.menu;

// import static com.anhanguera.ctcli.terminal.util.CodigosANSI.*;

import com.anhanguera.ctcli.Main;
import com.anhanguera.ctcli.Usuario;
import com.anhanguera.ctcli.arquivo.OperadorArquivos;
import com.anhanguera.ctcli.terminal.menu.mensagens.CmdGlobal;
import com.anhanguera.ctcli.terminal.menu.mensagens.CmdUsuario;
import com.anhanguera.ctcli.terminal.menu.mensagens.Erro;
import com.anhanguera.ctcli.terminal.menu.mensagens.Msg;
import com.anhanguera.ctcli.terminal.util.UtilidadesCLI;
import com.anhanguera.ctcli.arquivo.ArquivoConfig;

public class Menu {

    // É, eu sei que seria melhor criar uma classe num outro arquivo só pra
    // interpretar os comandos
    // É minha primeira vez fazendo algo assim e percebi isso muito tarde, agora não
    // tem tempo.

    public static final OperadorArquivos aq = new OperadorArquivos();
    public static final StringBuilder sb = new StringBuilder(50);

    public static final ArquivoConfig ctcliConfig = new ArquivoConfig(Main.CTCLICONFIG);

    // public static Mensagens msg;

    // public static final Alimento ali = new Alimento();

    public void iniciar() {

        // Caracteres especiais ANSI para fazer o texto ficar em negrito
        // (msg = VERSAO).print();
        System.out.println(Msg.VERSAO);

        if (Main.PRIMEIRAEXEC == 3) {
            System.out.println(Msg.INFO_PRIMEIRAEXEC);
            System.out.println("Sintaxe: usuario " + CmdUsuario.ADICIONAR.getJustSintaxe());
        }

        // System.out.println(Erros.DUPLICATE_USER);
        String usr = ctcliConfig.getPermaLoginUsr();
        if (!(usr.equals("") || usr.equals(null))) {
            if (Usuario.usuarioExiste(usr)) {
                MenuDiario md = new MenuDiario();
                System.out.println(Msg.AUTOLOGIN_SUCCESS);
                System.out.println(Msg.INFO_AJUDA);
                System.out.println(Msg.INFO_AJUDA_DIARIO);
                md.entradaAlimentos(usr);
            } else {
                System.out.println(Erro.PERMALOGIN_USUARIO_NOT_FOUND);
            }
        }

        System.out.println(Msg.INFO_AJUDA_USUARIO);
        System.out.println(Msg.INFO_USER_LOGIN);
        // System.out.println(Msg.INFO_AJUDA_USUARIO);

        // Criando instância da subclasse MenuPrincipal
        MenuUsuario mp = new MenuUsuario();
        mp.entradaUsuario();
    }

    // Printa a explicação sobre o nível de atividade física
    public void expNivelAtv() {
        System.out.println("\nEXPLICAÇÃO SOBRE COMO O CÁLCULO DO TDEE É FEITO");
        System.out.println("\nNíveis de atividade física:\n1. Sedentário\n2. Exercício leve");
        System.out.println("3. Exercício moderado\n4. Exercício intenso\n5. Exercício muito intenso (Atleta)");
        System.out.print("\nDigite o nível de atividade física do usuário ");
    }

    // Pede ao usuário seu nível de atividade física
    public String getNivelAtv() {
        String resp = UtilidadesCLI.getUserInput();
        // Pedir pra tentar novamente se a resposta não tiver apenas
        // um número ou ser algum outro caractere
        if (resp.length() != 1 || !(resp.matches(".*\\d.*"))) {
            System.out.println("Nível de atividade inválido. Tente novamente.");
            getNivelAtv();
            // também pede pra tentar novamente se a resposta não estiver entre 1 e 4.
        } else if (Integer.parseInt(resp) < 1 || Integer.parseInt(resp) > 5) {
            System.out.println("Nível de atividade inválido. Tente novamente.");
            getNivelAtv();

        } else {
            return resp;
        }
        return resp;

    }

    public void mostrarComandosUsuario() {
        UtilidadesCLI.clear();
        // TODO: pode estar desatualizado, verificar
        // Checa se é a primeira vez que o programa está sendo executado

        // loop for each pra iterar e printar todos os valores do Enum 'Comandos'
        // Usado pra printar todos os comandos quando o usuário executar o comando
        // 'ajuda'
        System.out.println();
        for (CmdUsuario c : CmdUsuario.values()) {
            System.out.println(c);
        }

        for (CmdGlobal c : CmdGlobal.values()) {
            System.out.println(c);
        }

        MenuUsuario mp = new MenuUsuario();
        mp.entradaUsuario();

    }

}