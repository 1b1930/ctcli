package com.anhanguera.ctcli.terminal.menu;

// import static com.anhanguera.ctcli.terminal.util.CodigosANSI.*;

import com.anhanguera.ctcli.Main;
import com.anhanguera.ctcli.Usuario;
import com.anhanguera.ctcli.terminal.menu.mensagens.CmdUsuario;
import com.anhanguera.ctcli.terminal.menu.mensagens.Erro;
import com.anhanguera.ctcli.terminal.menu.mensagens.Msg;
import com.anhanguera.ctcli.arquivo.ArquivoConfig;

public class Menu {

    // É, eu sei que seria melhor criar uma classe num outro arquivo só pra
    // interpretar os comandos
    // É minha primeira vez fazendo algo assim e percebi isso muito tarde, agora não
    // tem tempo.
    protected static final StringBuilder sb = new StringBuilder(50);

    protected static final ArquivoConfig ctcliConfig = new ArquivoConfig(Main.CTCLICONFIG);

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
            Usuario u = new Usuario(usr);
            if (u.existe()) {
                MenuDiario md = new MenuDiario(usr);
                System.out.println(Msg.AUTOLOGIN_SUCCESS);
                System.out.println(Msg.INFO_AJUDA);
                System.out.println(Msg.INFO_AJUDA_DIARIO);
                md.entradaDiario();
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

    // Pede ao usuário seu nível de atividade física


}