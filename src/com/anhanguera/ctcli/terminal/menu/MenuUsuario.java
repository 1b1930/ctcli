package com.anhanguera.ctcli.terminal.menu;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import javax.swing.plaf.ComponentInputMapUIResource;

import com.anhanguera.ctcli.Main;
import com.anhanguera.ctcli.Usuario;
import com.anhanguera.ctcli.arquivo.OperadorArquivos;
import com.anhanguera.ctcli.terminal.menu.mensagens.CmdGlobal;
import com.anhanguera.ctcli.terminal.menu.mensagens.CmdUsuario;
import com.anhanguera.ctcli.terminal.menu.mensagens.Erro;
import com.anhanguera.ctcli.terminal.menu.mensagens.Msg;
import com.anhanguera.ctcli.terminal.util.UtilidadesCLI;

public class MenuUsuario extends Menu {

    // Método que interpreta os comandos inseridos pelo usuário
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
            cmd = cmdStr.split(" ");

        } catch (NullPointerException e) {
            cmd[0] = cmdStr;

        }

        String cmdPrinc = cmd[0];
        String cmdSec;
        try {
            cmdSec = cmd[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            cmdSec = null;
        }

        try {
            cmd[2] = cmd[2].toLowerCase();
        } catch(ArrayIndexOutOfBoundsException e) {};

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

                if(cmd[2].contains(",") || cmd[2].length() > 10) {
                    System.out.println(Erro.ARG_NOME_INVALIDO);
                    entradaUsuario();
                }

                // copia só os dados que usuário entrou, sem os comandos
                String[] dados = Arrays.copyOfRange(cmd, 2, cmd.length);

                int validar = Usuario.validarDadosUsuario(dados);

                // printa erros conforme o valor retornado por validarDadosUsuario
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

                UtilidadesCLI.clear();
                // printa as explicações sobre o TDEE
                System.out.println(Msg.EXP_TDEE);
                System.out.println(Msg.EXP_GETNIVELATV);
                System.out.println();
                // pede ao usuário que entre seu nível de atividade física
                String resp = getNivelAtv();
                Usuario usuario = new Usuario(cmd[2], cmd[3], cmd[4], cmd[5], cmd[6], resp);
                // tenta criar o usuário, printa erro se não conseguiu
                if (usuario.criar()) {
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
                // cria uma instancia do usuario a ser removido
                Usuario u = new Usuario(cmd[2]);
                // tenta remover, se não conseguir, printa erro
                if (u.remover()) {
                    System.out.println(Msg.USUARIO_REMOVIDO_SUCESSO);
                    entradaUsuario();
                } else {
                    System.out.println(Erro.USUARIO_NAO_REMOVIDO);
                    entradaUsuario();
                }

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
                if(u2.alterarDados(cmd[3], cmd[4])) {
                    System.out.println(Msg.USUARIO_EDITADO_SUCESSO);


                } else {
                    System.out.println(Erro.USUARIO_DADOS_INVALIDOS);
                }


                entradaUsuario();

                // printa os dados de todos os usuários no csv
            } else if (cmdSec.matches("printall") || cmdSec.matches("pa")) {
                printUsuarios();
                entradaUsuario();

                // printa os dados do usuário especificado
            } else if (cmdSec.matches("print") || cmdSec.matches("p")) {
                // se tiver mais ou menos de 3 argumentos, printar erro
                if (cmd.length != 3) {
                    System.out.println(Erro.ARG_NUM_INVALIDO);
                    System.out.println(CmdUsuario.PRINT.getSintaxe());
                    entradaUsuario();
                } else {
                    Usuario u = new Usuario(cmd[2]);
                    u.printDados();
                    entradaUsuario();
                }
                entradaUsuario();

                // limpa csv + cabeçalho do CSV com os dados dos usuários, se o arquivo estiver
                // vazio
            } else if (cmdSec.matches("limparcsv") || cmdSec.matches("lcsv")) {
                OperadorArquivos csvUsuarioArq = new OperadorArquivos(Main.CSVUSUARIO);
                // limpa o csv escrevendo null para o arquivo
                csvUsuarioArq.escreverAoCSV(null);
                // cria um novo csv e monta seu cabecalho
                csvUsuarioArq.criarCSVeMontarCabecalho();
                System.out.println(Msg.CSV_LIMPO);
                entradaUsuario();

                // "loga" o usuário no app
            } else if (cmdSec.matches("logar") || cmdSec.matches("l")) {
                Usuario u = new Usuario(cmd[2]);
                if (cmd.length != 3) {
                    System.out.println(Erro.LOGIN_CAMPO_EM_BRANCO);
                    System.out.println(CmdUsuario.LOGAR.getSintaxe());
                    entradaUsuario();

                } else if (!(u.existe())) {
                    System.out.println(Erro.USUARIO_NAO_ENCONTRADO);
                    System.out.println(CmdUsuario.LOGAR.getSintaxe());
                    entradaUsuario();

                } else {
                    UtilidadesCLI.clear();
                    System.out.println(Msg.INFO_MENU_DIARIO);
                    MenuDiario md = new MenuDiario(cmd[2]);
                    md.entradaDiario();
                    entradaUsuario();
                }

            } else if(cmdSec.matches("ajuda")) {
                // printa os comandos do usuário em ordem usando os dados das constantes
                // armazenadas no Enum CmdUsuario
                UtilidadesCLI.clear();
                for (CmdUsuario c : CmdUsuario.values()) {
                    System.out.println(c);
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
            mostrarComandos();
            System.out.println("Procurando pelos comandos de usuario?");
            System.out.println(Msg.INFO_AJUDA_USUARIO);
            entradaUsuario();

        }

        else {
            System.out.println(Erro.CMD_INVALIDO_GLOBAL);
            entradaUsuario();
        }

    }

    public void printUsuarios() {
        // csvUsuarioArq csvUsuarioArq = new csvUsuarioArq();
        OperadorArquivos csvUsuarioArq = new OperadorArquivos(Main.CSVUSUARIO);
        List<String> lista = csvUsuarioArq.listaCSVRemoverHeader(csvUsuarioArq.lerDadosCSV());
        // Método bonito pra printar todos os elementos de uma lista

        // Printa a lista que só tem os elementos (sem cabeçalho)
        for (int i = 0; i < lista.size(); i++) {
            System.out.println(lista.get(i));
        }
    }

    public String getNivelAtv() {
        String resp = UtilidadesCLI.getUserInput();
        // Pedir pra tentar novamente se a resposta não tiver apenas
        // um número ou ser algum outro caractere
        if (resp.length() != 1 || !(resp.matches(".*\\d.*"))) {
            System.out.println("Nivel de atividade inválido. Tente novamente.");
            getNivelAtv();
            // também pede pra tentar novamente se a resposta não estiver entre 1 e 4.
        } else if (Integer.parseInt(resp) < 1 || Integer.parseInt(resp) > 5) {
            System.out.println("Nivel de atividade inválido. Tente novamente.");
            getNivelAtv();

        } else {
            return resp;
        }
        return resp;

    }

    public void mostrarComandos() {
        UtilidadesCLI.clear();
        // TODO: pode estar desatualizado, verificar
        // loop for each pra iterar e printar todos os valores do Enum 'Comandos'
        // Usado pra printar todos os comandos quando o usuário executar o comando
        // 'ajuda'
        System.out.println();

        for (CmdGlobal c : CmdGlobal.values()) {
            System.out.println(c);
        }

    }

}
