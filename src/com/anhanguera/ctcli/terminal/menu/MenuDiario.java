package com.anhanguera.ctcli.terminal.menu;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import com.anhanguera.ctcli.Alimento;
import com.anhanguera.ctcli.Diario;
import com.anhanguera.ctcli.Usuario;
import com.anhanguera.ctcli.terminal.menu.mensagens.CmdDiario;
import com.anhanguera.ctcli.terminal.menu.mensagens.CmdGlobal;
import com.anhanguera.ctcli.terminal.menu.mensagens.Erro;
import com.anhanguera.ctcli.terminal.menu.mensagens.Msg;
import com.anhanguera.ctcli.terminal.util.UtilidadesCLI;

//import static com.anhanguera.ctcli.terminal.util.CodigosANSI.*;

public class MenuDiario extends Menu {

    String usuario;

    MenuDiario(String usuario) {
        this.usuario = usuario;

    }

    public void entradaDiario() {

        Diario d = new Diario(usuario);
        d.faxina();
        // cria objeto stringbuilder que seŕa usado por outros métodos
        sb.setLength(0);

        // ex: peso, altura, nivelatv só devem conter números
        System.out.println();
        String[] cmd = new String[10];
        String cmdStr = "";
        // enquanto usuArio não digitar nenhum comando, continuar printando prompt
        while (cmdStr.isEmpty()) {
            System.out.print(usuario + " ");
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

        // strings que armazenam os comandos
        // cmdPrinc é o primeiro comando
        String cmdPrinc = cmd[0];
        // cmdSec é o segundo comando (pode estar em branco)
        String cmdSec;
        // cmdTer é o terceiro comando (pode estar em branco)
        String cmdTer;

        // tenta inicializar cmdSec, se não conseguiu (for nulo), inicializar como
        // string vazia
        try {
            cmdSec = cmd[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            cmdSec = "";
        }

        // tenta inicializar cmdTer, se não conseguiu (for nulo), inicializar como
        // string vazia
        try {
            cmdTer = cmd[2];
        } catch (ArrayIndexOutOfBoundsException e) {
            cmdTer = "";
        }

        // Comandos globais

        if (cmdPrinc.matches("voltar")) {
            MenuUsuario mu = new MenuUsuario();
            mu.entradaUsuario();
        } else if (cmdPrinc.matches("sair") || cmdPrinc.matches("s")) {
            UtilidadesCLI.clear();
            System.exit(0);
        } else if (cmdPrinc.matches("clear") || cmdPrinc.matches("c")) {
            UtilidadesCLI.clear();
        } else if (cmdPrinc.matches("ajuda")) {
            System.out.println();
            for (CmdGlobal c : CmdGlobal.values()) {
                System.out.println(c);
            }
            System.out.println(CmdDiario.VOLTAR);

        } else if (cmdPrinc.matches("permalogin")) {

            // se tiver menos ou mais de 1 argumento, printar erro
            if (cmd.length != 2) {
                System.out.println(Erro.ARG_NUM_INVALIDO);
                System.out.println(CmdDiario.PERMALOGIN.getSintaxe());

                // se argumento for 1, tentar habilitar
            } else if (cmdSec.matches("1")) {
                // se o permalogin jA estA habilitado para esse usuArio, printar erro
                if (ctcliConfig.getPermaLoginUsr().equals(usuario)) {
                    System.out.println(Erro.PERMALOGIN_JA_HABILITADO);
                }
                // printa sucesso se conseguiu habilitar
                if (ctcliConfig.addPermaLoginUsr(usuario)) {
                    System.out.println(Msg.PERMALOGIN_HABILITADO);
                } else {
                    // se não conseguiu habilitar, printar erro
                    System.out.println(Erro.PERMALOGIN_NAO_FOI_HAB);
                    System.out.println(CmdDiario.PERMALOGIN.getSintaxe());
                }
                // se for 0, tentar desabilitar
            } else if (cmdSec.matches("0")) {
                // se permalogin estiver habilitado pra esse usuario
                if (ctcliConfig.getPermaLoginUsr().equals(usuario)) {
                    // tenta desabilitar, se não conseguiu, printa erro
                    if (ctcliConfig.addPermaLoginUsr("")) {
                        System.out.println(Msg.PERMALOGIN_DESABILITADO);
                    } else {
                        System.out.println(Erro.PERMALOGIN_NAO_FOI_DESAB);
                    }
                    // printa erro se permalogin não estA habilitado pro usuArio
                    // se não estA habilitado, não precisa desabilitar,
                } else {
                    System.out.println(Erro.PERMALOGIN_NAO_ESTA_HAB);
                }

            } else {
                // se argumento não for 0 ou 1, printar erro
                System.out.println(Erro.ARG_NUM_INVALIDO);
                System.out.println(CmdDiario.PERMALOGIN.getSintaxe());

            }
            // voltar ao menu do diArio
            entradaDiario();

            // comandos de diArio
        } else if (cmdPrinc.matches("diario") || cmdPrinc.matches("d")) {

            if (cmdSec.matches("adicionar") || cmdSec.matches("a")) {

                // se tiver menos de 2 argumentos (excluindo cmdPrinc e cmdSec), printar erro
                if (cmd.length < 4) {

                    System.out.println(Erro.ARG_NUM_INVALIDO);
                    System.out.println(CmdDiario.ADICIONAR.getSintaxe());
                    entradaDiario();

                    // detecta se o usuArio errou a ordem dos argumentos
                } else if (cmd[2].matches("[0-9]+")) {
                    System.out.println(Erro.DIARIO_NOME_INCORRETO);
                    System.out.println(CmdDiario.ADICIONAR.getSintaxe());
                    entradaDiario();
                }

                // c contém o número de partes do comando que tem letras
                // ATENÇÃO: Também conta o comando em si (diario adicionar), ou seja,
                // posições 0 e 1
                int c = 0;
                // localização do número de calorias em cmd[]
                int temNum = 0;
                // quantas posições tem somente números em cmd[]
                int temNumQ = 0;

                for (int i = 0; i < cmd.length; i++) {
                    // verdadeiro se cmd[i] conter SOMENTE letras
                    // Fix: só adicionar a c se ainda não foi achado nenhum número
                    if (!cmd[i].matches("[0-9]+") && !cmd[i].contains(",") && temNum == 0) {
                        // se posição tiver letras, assume que é o nome do alimento, adiciona 1 a c
                        c++;
                    }

                    // verdadeiro se cmd[i] conter SOMENTE números
                    if (cmd[i].matches("[0-9]+")) {
                        // se for a primeira vez que esse if estA sendo executado,
                        // pegar o valor de i e botar em temNum
                        // basicamente pega o valor das calorias
                        if (temNumQ == 0) {
                            temNum = i;
                        }
                        temNumQ++;
                    }

                }

                // checa se o usuArio realmente adicionou as calorias antes de continuar
                if (temNum == 0) {
                    System.out.println(Erro.DIARIO_CALORIAS_NAO_ENC);
                    System.out.println(CmdDiario.ADICIONAR.getSintaxe());
                    entradaDiario();

                }
                // checa se o nome do alimento tem muitos espaços
                if (c > 9) {
                    System.out.println(Erro.DIARIO_ADD_NOME_MUITOS_ESPACOS);
                    System.out.println(CmdDiario.ADICIONAR.getSintaxe());
                    entradaDiario();

                }

                // checa se o nome estA em branco
                if (c == 2) {
                    System.out.println(Erro.DIARIO_NOME_INCORRETO);
                    System.out.println(CmdDiario.ADICIONAR.getSintaxe());
                    entradaDiario();

                }

                // se o valor das calorias tiver mais de 4 dígitos, printar erro
                if (cmd[temNum].length() > 4) {
                    System.out.println(Erro.ARG_KCAL_INVALIDO);
                    System.out.println(CmdDiario.ADICIONAR.getSintaxe());
                    entradaDiario();

                }

                // Objeto StringBuilder pra juntar as partes do comando que são o nome do
                // alimento
                // StringBuilder sb = new StringBuilder(50);
                for (int i = 2; i < c; i++) {
                    // Se o nome do alimento for muito grande, printar erro.
                    if (cmd[i].length() > 14) {
                        System.out.println(Erro.DIARIO_NOME_COMPRIMENTO_EXCEDIDO);
                        System.out.println(CmdDiario.ADICIONAR.getSintaxe());
                        entradaDiario();
                    }
                    sb.append(" " + cmd[i]);

                }
                // no CSV, os espaços serão substituidos por underlines
                String validA = sb.toString().trim().replace(" ", "_");

                // stringbuilder que vai armazenar a nota
                StringBuilder nom = new StringBuilder(50);

                // pega o comprimento da nota calculando a diferença entre o
                // comprimento de todo o cmd[] e o comprimento de cmd[] até a posição
                // das calorias
                int diff = cmd.length - temNum;
                // se o comprimento da nota é maior que 6
                if (diff > 6) {
                    System.out.println(Erro.DIARIO_NOTA_COMPRIMENTO_EXCEDIDO);
                    entradaDiario();
                }

                // junta todas as 'partes' da nota no stringbuilder
                for (int i = 1; i < diff; i++) {
                    nom.append(" " + cmd[temNum + i]);
                }
                String no = nom.toString().trim().replace(" ", "_");

                // quantidade de caracteres da nota não pode ter maior que 18 (inclui espaços)
                if (no.length() > 18) {
                    System.out.println(Erro.DIARIO_NOTA_COMPRIMENTO_EXCEDIDO);
                    entradaDiario();
                }

                Alimento ali = new Alimento(validA, cmd[temNum], no, usuario);

                // String[] dados = { validA, cmd[temNum], no };

                // Diario diario = new Diario(usuario);

                // só printa sucesso se o alimento foi adicionado ao diArio com sucesso
                if (ali.adicionar()) {
                    System.out.println(Msg.DIARIO_ALIMENTO_ADICIONAR_SUCESSO);
                    entradaDiario();

                    // se não, printa erro
                } else {
                    System.out.println(Erro.DIARIO_ALIMENTO_NAO_ADICIONADO);
                    System.out.println(CmdDiario.ADICIONAR.getSintaxe());
                    entradaDiario();
                }

                System.out.println("Voce nao e suposto a ver essa mensagem. Oops!");
                System.exit(0);

            } else if (cmdSec.matches("remover") || cmdSec.matches("r")) {
                if (cmd.length < 3) {
                    System.out.println(Erro.ARG_NUM_INVALIDO);
                    System.out.println(CmdDiario.REMOVER.getSintaxe());
                    entradaDiario();
                }
                // acrescentando todas as partes do nome do alimento ao objeto sb
                // (StringBuilder)
                for (int i = 2; i < cmd.length; i++) {
                    sb.append(" " + cmd[i]);

                }
                // criando objeto diario
                // Diario ali = new Diario(usuario);
                Alimento ali = new Alimento(sb.toString().trim().replace(" ", "_"), usuario);
                if (ali.remover() == false) {
                    System.out.println(Erro.DIARIO_ALIMENTO_NAO_EXISTE);
                    System.out.println(CmdDiario.REMOVER.getSintaxe());
                    entradaDiario();

                }
                System.out.println(Msg.DIARIO_ALIMENTO_REMOVER_SUCESSO);
                entradaDiario();

            } else if (cmdSec.matches("print") || cmdSec.matches("p")) {

                if (cmdTer.matches("hoje") || cmdTer.matches("h")) {
                    LocalDate hoje = LocalDate.now();
                    printDiarioHeader();
                    printDiarioDia(hoje, usuario);

                }

                else if (cmdTer.matches("ontem") || cmdTer.matches("o")) {
                    LocalDate hoje = LocalDate.now();
                    LocalDate ontem = hoje.minus(Period.ofDays(1));
                    printDiarioDia(ontem, usuario);
                }

                else if (cmdTer.matches("semana") || cmdTer.matches("s")) {
                    LocalDate hoje = LocalDate.now();
                    LocalDate comecoSemana = hoje.with(DayOfWeek.MONDAY);

                    printDiarioHeader();
                    printDiarioSemana(comecoSemana, hoje, usuario);

                    // printDiarioSemana(umaSemanaAtras, hoje, usuario);
                    // printKcalDia(hoje, usuario);
                    entradaDiario();

                }

                else if (cmdTer.matches("tudo") || cmdTer.matches("t")) {
                    printDiario(usuario);

                }

                else {
                    printDiarioHeader();
                    printDiario(usuario);

                }

                // printa o cabeçalho da lista
                // usa um mínimo de 30 caracteres para cada string e é alinhado à esquerda por
                // causa do - antes do 30.

            } else if (cmdSec.matches("ajuda")) {
                System.out.println();
                for (CmdDiario c : CmdDiario.values()) {
                    System.out.println(c);
                }

            }

            else {
                System.out.println(Erro.CMD_INVALIDO_DIARIO);

            }

        } // else ifs de cmdPrinc são aqui

        else {
            System.out.println(Erro.CMD_INVALIDO_DIARIO);
            entradaDiario();
        }
        entradaDiario();
    }

    void printDiarioHeader() {
        System.out.printf("%-30s%-30s%-30s%-30s", "NOME", "KCAL", "DATA", "NOTAS");
        System.out.println();
    }

    void printDiarioDia(LocalDate data, String usr) {

        // Cria um objeto diArio que vamos usar pra ler o conteúdo do diArio do usuArio
        Diario d = new Diario(usr);

        Usuario u = new Usuario(usr);
        // armazena o tdee do usuario
        String uTDEE = u.getTDEE();
        double uTDEEconv = Double.parseDouble(uTDEE);

        // lista que vai armazenar os dados lidos
        List<String> lista = new ArrayList<>();
        // pega os dados e adiciona eles na lista
        lista.addAll(d.getDiario(data));
        // irA armazenar os dados de um registro individualmente
        String[] indiv;
        // armazena todos os dados de um registro, irA ser quebrado
        // e os dados serão adicionados individualmente em indiv[]
        String alimento;
        // calorias consumidas no dia (hoje)
        double kDia = 0.0;

        // itera pela lista
        for (int i = 0; i < lista.size(); i++) {
            // armazena um registro da lista em alimento
            alimento = lista.get(i).replaceAll("[\\[\\]]", "");
            // divide os dados do alimento num array
            indiv = alimento.split(",");
            // itera pelo array
            for (int j = 0; j < indiv.length; j++) {
                if(j == 2) {
                    System.out.print(String.format("%-30s", indiv[j].replace("-", " ").trim()));

                } else {
                    System.out.print(String.format("%-30s", indiv[j].replace("_", " ").trim()));
                }
                // printa os dados formatados, mínimo de 30 espaços, alinhado à esquerda
                // System.out.print(String.format("%-30s", indiv[j].replace("_", " ").trim()));
                if (j == 1) {
                    kDia += Double.parseDouble(indiv[j]);

                }
            }
            System.out.println();
        }

        if (data.isEqual(LocalDate.now())) {
            System.out.printf("\nTOTAL DE CALORIAS CONSUMIDAS HOJE: " + "%.0f\n", kDia);

        } else if (data.isEqual(LocalDate.now().minus(Period.ofDays(1)))) {
            System.out.printf("\nTOTAL DE CALORIAS CONSUMIDAS ONTEM: " + "%.0f\n", kDia);

        } else {
            System.out.printf("\nTOTAL DE CALORIAS CONSUMIDAS" + "NO DIA " + data.toString() + ": " + "%.0f\n", kDia);

        }

        if (UtilidadesCLI.isBlankString(uTDEE)) {
            System.out.println("");

        } else {
            // porcentagem = (kDia / uTDEEconv) * 100;
            System.out.printf("\nVOCE CONSUMIU ");
            System.out.printf("%.0f%%", ((kDia / uTDEEconv) * 100));
            System.out.printf(" DO SEU TDEE (%s)\n", uTDEE);
        }

    }

    void printDiarioSemana(LocalDate inicio, LocalDate fim, String usr) {
        // Cria um objeto diArio que vamos usar pra ler o conteúdo do diArio do usuArio
        Diario d = new Diario(usr);

        Usuario u = new Usuario(usr);

        // lista que vai armazenar os dados lidos
        List<String> lista = new ArrayList<>();
        // pega os dados e adiciona eles na lista
        lista.addAll(d.getDiarioEntreDias(inicio, fim));
        // irA armazenar os dados de um registro individualmente
        String[] indiv;
        // armazena todos os dados de um registro, irA ser quebrado
        // e os dados serão adicionados individualmente em indiv[]
        String alimento;
        // calorias consumidas no dia (hoje)
        double kRange = 0.0;

        // itera pela lista
        for (int i = 0; i < lista.size(); i++) {
            // armazena um registro da lista em alimento
            alimento = lista.get(i).replaceAll("[\\[\\]]", "");
            // divide os dados do alimento num array
            indiv = alimento.split(",");
            // itera pelo array
            for (int j = 0; j < indiv.length; j++) {
                if(j == 2) {
                    System.out.print(String.format("%-30s", indiv[j].replace("-", " ").trim()));

                } else {
                    System.out.print(String.format("%-30s", indiv[j].replace("_", " ").trim()));
                }
                // printa os dados formatados, mínimo de 30 espaços, alinhado à esquerda
                if (j == 1) {
                    kRange += Double.parseDouble(indiv[j]);

                }
            }
            System.out.println();
        }

        // printa as calorias consumidas nessa semana
        System.out.printf("\nTOTAL DE CALORIAS CONSUMIDAS" + " ENTRE " + inicio.toString() + " E " + fim.toString()
                + ": " + "%.0f\n", kRange);

        double tdee = Double.parseDouble(u.getTDEE());
        double tdeeSemana = tdee * 7;
        double percent = (kRange / tdeeSemana) * 100;
        System.out.printf("\nVOCE CONSUMIU ");
        System.out.printf("%.0f%%", percent);
        System.out.printf(" DO SEU TDEE SEMANAL (%.0f)\n", tdeeSemana);

        if (percent < 100) {
            System.out
                    .println("\nVOCE ESTA ABAIXO DO SEU CONSUMO DE ENERGIA SEMANAL E PERDERA PESO SE CONTINUAR ASSIM!");

        } else if (percent > 100) {
            System.out
                    .println("\nVOCE ESTA ACIMA DO SEU CONSUMO DE ENERGIA SEMANAL E GANHARA PESO SE CONTINUAR ASSIM.");
        }

    }

    void printDiario(String usr) {
        List<String> lista = new ArrayList<String>();

        Diario d = new Diario(usr);

        lista.addAll(d.getDiario());

        double kcal = 0.0;

        String diarioStr;
        String[] diarioArr;

        for (int i = 0; i < lista.size(); i++) {
            diarioStr = lista.get(i).replaceAll("[\\[\\]]", "");
            diarioArr = diarioStr.split(",");
            for (int j = 0; j < diarioArr.length; j++) {
                if(j == 2) {
                    System.out.print(String.format("%-30s", diarioArr[j].replace("-", " ").trim()));

                } else {
                    System.out.print(String.format("%-30s", diarioArr[j].replace("_", " ").trim()));
                }
                // System.out.print(String.format("%-30s", diarioArr[j].replace("_", " ").trim()));
                if (j == 1) {
                    kcal += Double.parseDouble(diarioArr[j]);
                }
            }
            System.out.println();
        }

        System.out.printf("\nTOTAL DE CALORIAS CONSUMIDAS DESDE O INICIO: " + "%.0f\n", kcal);
    }
}
