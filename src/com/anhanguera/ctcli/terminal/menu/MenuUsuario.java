package com.anhanguera.ctcli.terminal.menu;

import java.util.Arrays;

import com.anhanguera.ctcli.Main;
import com.anhanguera.ctcli.Usuario;
import com.anhanguera.ctcli.terminal.menu.mensagens.CmdUsuario;
import com.anhanguera.ctcli.terminal.menu.mensagens.Erro;
import com.anhanguera.ctcli.terminal.menu.mensagens.Msg;
import com.anhanguera.ctcli.terminal.util.UtilidadesCLI;

public class MenuUsuario extends Menu {

    // Método que cuida da entrada e sanitização de dados do usuário
    public void entradaUsuario() {
        // ex: peso, altura, nivelatv só devem conter números
        System.out.println();
        String[] cmd = new String[15];
        String cmdStr = "";
        while (cmdStr.isEmpty()) {
            cmdStr = UtilidadesCLI.getUserInput();
        }

        // Interpreta corretamente o comando se só tiver um argumento
        try {
            // System.out.println(cmdStr);
            cmd = cmdStr.split(" ");

        } catch (NullPointerException e) {
            // System.out.println("caught");
            cmd[0] = cmdStr;

        }

        String cmdPrinc = cmd[0];
        String cmdSec;
        try {
            cmdSec = cmd[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            cmdSec = null;
        }

        // Comandos que o usuário pode usar
        // sintaxe: [categoria de comando] [comando] [parametros...]
        // ex: usuario logar daniel
        // ex2: usuario editar daniel peso 80

        if (cmdPrinc.matches("usuario") || cmdPrinc.matches("u")) {
            // adiciona um novo usuário ao csv
            if (cmdSec.matches("adicionar") || cmdSec.matches("a")) {

                if (cmd.length != 7) {
                    System.out.println(Erro.ARG_NUM_INVALIDO);
                    System.out.println(CmdUsuario.ADICIONAR.getSintaxe());
                    entradaUsuario();
                }

                // copia só os dados que usuário entrou, sem os comandos
                String[] dados = Arrays.copyOfRange(cmd, 2, cmd.length);
                System.out.println(Arrays.toString(dados));

                int validar = Usuario.validarDadosUsuario(dados);

                switch (validar) {
                case 1:
                    System.out.println(Erro.USUARIO_JA_EXISTE);
                    System.out.println(CmdUsuario.ADICIONAR.getSintaxe());
                    entradaUsuario();

                case 2:
                    System.out.println(Erro.ARG_PESO_INVALIDO);
                    System.out.println(CmdUsuario.ADICIONAR.getSintaxe());
                    entradaUsuario();

                case 3:
                    System.out.println(Erro.ARG_ALTURA_INVALIDO);
                    System.out.println(CmdUsuario.ADICIONAR.getSintaxe());
                    entradaUsuario();

                case 4:
                    System.out.println(Erro.ARG_IDADE_INVALIDO);
                    System.out.println(CmdUsuario.ADICIONAR.getSintaxe());
                    entradaUsuario();

                case 5:
                    System.out.println(Erro.ARG_SEXO_INVALIDO);
                    System.out.println(CmdUsuario.ADICIONAR.getSintaxe());
                    entradaUsuario();

                }

                System.out.println(Msg.EXP_TDEE);
                System.out.println(Msg.EXP_GETNIVELATV);
                System.out.println();
                String resp = getNivelAtv();
                Usuario usuario = new Usuario(cmd[2], cmd[3], cmd[4], cmd[5], cmd[6], resp);
                if (usuario.criarUsuario()) {
                    System.out.println(Msg.USUARIO_CRIADO_SUCESSO);
                } else {
                    System.out.println(Erro.USUARIO_NAO_CRIADO);
                }
                entradaUsuario();

                // remove um usuário do csv, deleta seu diário
            } else if (cmdSec.matches("remover") || cmdSec.matches("r")) {
                // esse if de checagem de comprimento de cmd[] pode funcionar melhor na linha
                // acima
                if (cmd.length != 3) {
                    System.out.println(Erro.ARG_NUM_INVALIDO);
                    entradaUsuario();
                }
                Usuario u = new Usuario(cmd[2]);
                if (u.removerUsuario()) {
                    System.out.println(Msg.USUARIO_REMOVIDO_SUCESSO);
                    entradaUsuario();
                } else {
                    System.out.println(Erro.USUARIO_NAO_REMOVIDO);
                    entradaUsuario();
                }
                // System.out.println("Argumento aceito");
                entradaUsuario();

                // edita o dado especificado
            } else if (cmdSec.matches("editar") || cmdSec.matches("e")) {
                if (cmd.length != 5) {
                    System.out.println(Erro.ARG_NUM_INVALIDO);
                    System.out.println(CmdUsuario.EDITAR.getSintaxe());

                    entradaUsuario();

                }
                
                // se cmd[3] não for uma propriedade inválida, printar erro
                if (!(cmd[3].matches("peso") || cmd[3].matches("altura") || cmd[3].matches("nome")
                        || cmd[3].matches("nivelatv") || cmd[3].matches("idade") || cmd[3].matches("sexo"))) {
                    System.out.println(Erro.PROP_INVALIDA);
                    entradaUsuario();
                }
                // valida os dados do usuario, só continua se os dados forem validos
                if (!(Usuario.validarDadosUsuario(cmd[3], cmd[4]))) {
                    System.out.println(Erro.USUARIO_DADOS_INVALIDOS);
                    System.out.println(CmdUsuario.EDITAR.getSintaxe());
                    entradaUsuario();
                }

                Usuario u2 = new Usuario(cmd[2]);
                u2.alterarDados(cmd[3], cmd[4]);
                System.out.println(Msg.USUARIO_EDITADO_SUCESSO);

                entradaUsuario();

                // printa os dados de todos os usuários no csv
            } else if (cmdSec.matches("printall") || cmdSec.matches("pa")) {
                Usuario.printUsuarios();
                entradaUsuario();

                // printa os dados do usuário especificado
            } else if (cmdSec.matches("print") || cmdSec.matches("p")) {
                // se tiver mais ou menos de 3 argumentos, printar erro
                if (cmd.length != 3) {
                    System.out.println(Erro.ARG_NUM_INVALIDO);
                    System.out.println(CmdUsuario.PRINT.getSintaxe());
                    entradaUsuario();
                } else {
                    Usuario.printDadosUsuario(cmd[2]);
                    entradaUsuario();
                }
                entradaUsuario();

                // limpa csv + cabeçalho do CSV com os dados dos usuários, se o arquivo estiver
                // vazio
            } else if (cmdSec.matches("limparcsv") || cmdSec.matches("lcsv")) {
                aq.escreverAoCSV(Main.CSVUSUARIO, null);
                aq.criarCSVeMontarCabecalho(Main.CSVUSUARIO);
                System.out.println(Msg.CSV_LIMPO);
                entradaUsuario();

                // "loga" o usuário no app
            } else if (cmdSec.matches("logar") || cmdSec.matches("l")) {
                if (cmd.length != 3) {
                    System.out.println(Erro.LOGIN_CAMPO_EM_BRANCO);
                    System.out.println(CmdUsuario.LOGAR.getSintaxe());
                    entradaUsuario();

                } else if (!(Usuario.usuarioExiste(cmd[2]))) {
                    System.out.println(Erro.USUARIO_NAO_ENCONTRADO);
                    System.out.println(CmdUsuario.LOGAR.getSintaxe());
                    entradaUsuario();

                } else {
                    UtilidadesCLI.clear();
                    System.out.println(Msg.INFO_MENU_DIARIO);
                    MenuDiario md = new MenuDiario();
                    md.entradaAlimentos(cmd[2]);
                    entradaUsuario();
                }
            }

            else {
                System.out.println(Erro.CMD_INVALIDO_USUARIO);
                entradaUsuario();
            }

            /* COMANDOS PRINCIPAIS */
        } else if (cmdPrinc.matches("sair") || cmdPrinc.matches("s")) {
            UtilidadesCLI.clear();
            System.exit(0);

        } else if (cmdPrinc.matches("clear") || cmdPrinc.matches("c")) {
            UtilidadesCLI.clear();
            entradaUsuario();

        } else if (cmdPrinc.matches("ajuda")) {
            mostrarComandosUsuario();
            entradaUsuario();

        } else if (cmdPrinc.matches("adc")) {
            mostrarComandosUsuarioAdicionais();
            entradaUsuario();
        }

        else {
            System.out.println(Erro.CMD_INVALIDO_GLOBAL);
            entradaUsuario();
        }

    }

}
