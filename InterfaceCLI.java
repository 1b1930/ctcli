// import java.lang.reflect.Array;
import java.util.Arrays;

// Ex: usuario logar daniel; alimento adicionar arroz 40; usuario remover daniel

public class InterfaceCLI {

    // É, eu sei que seria melhor criar uma classe num outro arquivo só pra interpretar os comandos
    // É minha primeira vez fazendo algo assim e percebi isso muito tarde, agora não tem tempo.
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String NEGRITO = "\033[0;1m";
    public static final String ESTL = "\t\033[0;1m*\033[0;0m ";
    public static final String NORMAL = "\033[0;0m";
    public static final String SUBL = "\033[0;4m";
    public static final String BIGL = "⊢ ";
    public static final String SETA = "❯ ";



    public static final ArquivoOps aq = new ArquivoOps();
    public static final StringBuilder sb = new StringBuilder(50);
    public static final Alimento ali = new Alimento();

    void mostrar() {
        // Caracteres especiais ANSI para fazer o texto ficar em negrito
        System.out.println("\033[0;1m");
        // mensagem de boas vindas + explicação do app
        System.out.println("ctcli"+" v"+Main.VERSION);
        // System.out.println("Esse programa irá ajudar você a contar suas calorias.");
        // System.out.println("Ele também informa o seu valor total de energia gasta por dia" +
        // " " + "(sigla em inglês: TDEE)");
        // System.out.println("Isso ajuda você a controlar seu peso, já que se as calorias consumidas por dia" +
        // " excederem o valor do TDEE, você irá ganhar peso.");
        String usr = Config.getPermaLoginUsr();
        if(!(usr.equals("") || usr.equals(null))) {
            if(Usuario.usuarioExiste(usr)) {
                MenuPrincipal mp = new MenuPrincipal();
                System.out.println("\nLogado automaticamente como "+usr);
                System.out.println("\nPara desabilitar o login automático, digite: permalogin 0");
                System.out.println(ANSI_GREEN+"\nDigite \"ajuda\" para obter os comandos disponíveis."+ANSI_RESET);
                mp.entradaAlimentos(usr);
            } else {
                System.out.println(ANSI_RED+"\nERRO: Usuário especificado em $permalogin não existe (ctcli.config)"+ANSI_RESET);
            }
        }

        System.out.println("\n"+NEGRITO+"Entre como um usuário para obter acesso aos demais comandos.");
        System.out.println("Use: usuario logar [nome do usuário]");

        System.out.println("\n"+ANSI_GREEN+"Digite \"ajuda\" para obter todos os comandos disponíveis"+ANSI_RESET);



        // CLIUtil.waitNext();
        
        // Criando instância da subclasse MenuPrincipal
        // executando o método sobrescrito mostrar()
        MenuPrincipal mp = new MenuPrincipal();
        mp.entradaUsuario();
    }

    // Printa a explicação sobre o nível de atividade física
    void expNivelAtv() {
        System.out.println("\nEXPLICAÇÃO SOBRE COMO O CÁLCULO DO TDEE É FEITO");
        System.out.println("\nNíveis de atividade física:\n1. Sedentário\n2. Exercício leve");
        System.out.println("3. Exercício moderado\n4. Atleta");
        System.out.print("\nDigite o nível de atividade física do usuário ");
    }

    // Pede ao usuário seu nível de atividade física
    String getNivelAtv() {
        String resp = CLIUtil.getUserInput();
        // Pedir pra tentar novamente se a resposta não tiver apenas 
        // um número ou ser algum outro caractere
        if(resp.length() != 1 || !(resp.matches(".*\\d.*"))) {
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
        CLIUtil.clear();
        // TODO: Isso tá muuuuuito desatualizado
        // System.out.println("\t\t"+NEGRITO+"Comandos"+NORMAL+"\n");       
        // Checa se é a primeira vez que o programa está sendo executado
        System.out.println(NEGRITO+"Comandos de Usuário"+NORMAL);
        System.out.println("Para usar os comandos de usuário, use como prefixo \"usuario\" ou o seu atalho \"u\"");
        System.out.println("Ex: usuario remover [usuario]\n");

        System.out.println("\t\"adicionar\" [nome] [peso (kg)] [altura (cm)] [idade]\t\tAtalho: \"a\"");
        System.out.println("\tDescrição: adiciona um usuário à base de dados.\n");

        // System.out.println("remusuario [nome]");

        System.out.println("\t\"editar\" [nome] [propriedade a ser alterada] [novo valor]\tAtalho: \"e\"");
        System.out.println("\tPropriedades válidas: nome, peso, altura, idade, nivelatv");
        System.out.println("\tDescrição: edita uma propriedade do usuário dado como parâmetro.\n");

        System.out.println("\t\"remover\" [nome]\t\t\t\t\t\tAtalho: \"r\"");
        System.out.println("\tDescrição: remove o usuário dado como parâmetro.\n");

        System.out.println("\t\"print\" [nome]\t\t\t\t\t\t\tAtalho: \"p\"");
        System.out.println("\tDescrição: printa o usuário dado como parâmetro.\n");

        System.out.println("\t\"printall\"\t\t\t\t\t\t\tAtalho: \"pa\"");
        System.out.println("\tDescrição: printa todos os usuários salvos na base de dados.\n");

        System.out.println("\t\"logar\" [nome]\t\t\t\t\t\t\tAtalho: \"l\"");
        System.out.println("\tDescrição: loga como o usuário dado como parâmetro.\n");




        System.out.println(NEGRITO+"Comandos Globais"+NORMAL);
        System.out.println("Esses comandos não necessitam de um prefixo.\n");

        System.out.println("\t\"sair\"\t\t\t\t\t\t\t\tAtalho: \"s\"");
        System.out.println("\tDescrição: sai do programa.\n");

        System.out.println("\t\"clear\"\t\t\t\t\t\t\t\tAtalho: \"c\"");
        System.out.println("\tDescrição: limpa a tela do terminal (se for possível).\n");


        System.out.println("\n"+ANSI_GREEN+"Entre como um usuário para obter acesso aos demais comandos."+ANSI_RESET);
        System.out.println("Use: usuario logar [nome do usuário]");

        MenuPrincipal mp = new MenuPrincipal();
        mp.entradaUsuario();

    }
    

    void mostrarComandosUsuarioAdicionais() {
        System.out.println(NEGRITO+"Comandos Adicionais"+NORMAL);
        System.out.println("\n\tusuario: \"limparcsv\"\t\t\t\t\t\tAtalho: \"lcsv\"");
        System.out.println("\tDescrição: limpa o arquivo CSV do usuário, só deixa o cabeçalho.");

        
        MenuPrincipal mp = new MenuPrincipal();
        mp.entradaUsuario();

    }

    void mostrarComandosAlimentos() {
        CLIUtil.clear();
        System.out.println(NEGRITO+"Comandos do Submenu Pessoal"+NORMAL);
        System.out.println(NEGRITO+"\nComandos Referentes aos Alimentos\n"+NORMAL);
        System.out.println("Para usar os comandos de alimentos, use como prefixo \"alimento\" ou o seu atalho \"a\"");
        System.out.println("Ex: alimento remover [nome], que é a mesma coisa que \"a r [nome]\"\n");
        System.out.println("\t\"adicionar\" [nome] [kcal/100g]\t\t\t\t\tAtalho: \"a\"");
        System.out.println("\tDescrição: Adiciona um alimento à base de dados."+
        "\n\tKcal/100g são quantas calorias tem 100g desse alimento.\n");

        System.out.println("\t\"print\" [nome]\tAtalho: \"p\"");
        System.out.println("\tDescrição: edita uma propriedade do usuário dado como parâmetro.\n");

        System.out.println("\t\"remover\" [nome]\t\t\t\t\t\tAtalho: \"r\"");
        System.out.println("\tDescrição: remove o alimento dado como parâmetro do banco de dados.\n");

        System.out.println("\t\"alterar\" [nome] [propriedade a ser alterada] [novo valor]\tAtalho: \"alt\"");
        System.out.println("\tPropriedades válidas: nome, kcal");
        System.out.println("\tDescrição: alterar uma propriedade do alimento dado como parâmetro.\n");


        System.out.println("\t\"print\" [nome]\t\t\t\t\t\t\tAtalho: \"p\"");
        System.out.println("\tDescrição: printa os dados do alimento dado como parâmetro.\n");

        System.out.println("\t\"printpretty\" [nome]\t\t\t\t\t\tAtalho: \"pp\"");
        System.out.println("\tDescrição: printa os dados do alimento dado como parâmetro, de forma mais bonitinha.\n");

        System.out.println("\t\"printall\" [nome]\t\t\t\t\t\tAtalho: \"pa\"");
        System.out.println("\tDescrição: printa todos os alimentos.\n");
        

    }

    class MenuPrincipal extends InterfaceCLI {

        // Método que cuida da entrada e sanitização de dados do usuário
        void entradaUsuario() {
            // ex: peso, altura, nivelatv só devem conter números
            System.out.println();
            String[] cmd = new String[15];
            String cmdStr = "";
            while(cmdStr.isEmpty()) {
                cmdStr = CLIUtil.getUserInput();
            }

            // Interpreta corretamente o comando se só tiver um argumento
            try {
                // System.out.println(cmdStr);
                cmd = cmdStr.split(" ");

            } catch (NullPointerException e) {
                // System.out.println("caught");
                cmd[0] = cmdStr;
                System.out.println(cmdStr);

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

            if(cmdPrinc.matches("usuario") || cmdPrinc.matches("u")) {
                // adiciona um novo usuário ao csv
                if(cmdSec.matches("adicionar") || cmdSec.matches("a")) {
                    if(cmd.length < 6 || cmd.length > 6) {
                        System.out.println("Número de argumentos inválido. Tente novamente.");
                        System.out.println("Uso: usuario adicionar [nome] [peso (kg)] [altura (cm)] [idade]");
                        entradaUsuario();
                    }
                    // Checa se o usuário existe, se já existe, manda o usuário tentar novamente
                    if(Usuario.usuarioExiste(cmd[2])) {
                        // Char especial ANSI pra limpar a tela do console
                        // TODO: Deve ter uma maneira melhor de fazer isso.
                        System.out.println("O usuário já existe no banco de dados. Tente novamente.");
                        System.out.println("Uso: usuario adicionar [nome] [peso (kg)] [altura (cm)] [idade]");
                        entradaUsuario();
                        try {Thread.sleep(3000);} catch(InterruptedException e) {e.printStackTrace();};
                    }

                    expNivelAtv();
                    String resp = getNivelAtv();
                    Usuario usuario = new Usuario(cmd[2],cmd[3],cmd[4],cmd[5],resp);
                    if(usuario.criarUsuario()) {
                        System.out.println("Usuário criado com sucesso.");
                        System.out.println("\nEntre como um usuário para obter acesso aos demais comandos!");
                        System.out.println("Use: usuario logar [nome do usuário]");
                    } else {
                        System.out.println("Usuário não criado. Tente novamente.");
                        System.out.println("Uso: usuario adicionar [nome] [peso (kg)] [altura (cm)] [idade]");
                    }
                    entradaUsuario();
                    
                // remove um usuário do csv
                } else if(cmdSec.matches("remover") || cmdSec.matches("r")) {
                    if(cmd.length != 3) {
                        System.out.println("Quantidade de argumentos inválida. Tente novamente.");
                        System.out.println("Uso: usuario remover [nome]");
                        entradaUsuario();
                    }
                    Usuario u = new Usuario();
                    if(u.removerUsuario(cmd[2])) {
                        System.out.println("Usuário removido");
                        entradaUsuario();
                    } else {
                        System.out.println("Usuário não removido"+
                        " pois não foi encontrado.");
                        System.out.println("Uso: usuario remover [nome]");
                        entradaUsuario();
                    }
                    // System.out.println("Argumento aceito");
                    entradaUsuario();
                
                // edita o dado especificado
                } else if(cmdSec.matches("editar") || cmdSec.matches("e")) {
                    if(cmd.length < 5) {
                        System.out.println("Quantidade de argumentos insuficiente. Tente novamente.");
                        System.out.println("Uso: usuario editar [nome] [propriedade] [novo valor]");

                        entradaUsuario();

                    } else if(cmd.length > 5) {
                        System.out.println("Quantidade de argumentos excedida para esse comando. Tente novamente");
                        System.out.println("Uso: usuario editar [nome] [propriedade] [novo valor]");
                        entradaUsuario();

                    } 
                    // System.out.println("+"+cmd[2]+"+");
                    if(!(cmd[3].matches("peso") || cmd[3].matches("altura") || cmd[3].matches("nome") || cmd[3].matches("nivelatv") || cmd[3].matches("idade"))) {
                        System.out.println("Propriedade inválida.");
                        System.out.println("Uso: usuario editar [nome] [propriedade] [novo valor]");
                        System.out.println("Propriedades válidas: nome, peso, altura, idade, nivelatv");
                        entradaUsuario();
                    }

                    Usuario u2 = new Usuario();
                    u2.alterarDados(cmd[2], cmd[3], cmd[4]);
                    System.out.println("Usuário editado com sucesso.");
                    
                    entradaUsuario();
                
                // printa os dados de todos os usuários no csv
                } else if(cmdSec.matches("printall") || cmdSec.matches("pa")) {
                    Usuario.printUsuarios();
                    entradaUsuario();
                
                // printa os dados do usuário especificado
                } else if(cmdSec.matches("print") || cmdSec.matches("p")) {

                    if(cmd.length != 3) {
                        System.out.println("Argumentos inválidos/insuficientes.");
                            entradaUsuario();
                        } else {
                            Usuario.printDadosUsuario(cmd[2]);
                            entradaUsuario();
                        }
                        entradaUsuario();
                
                // limpa csv + cabeçalho do CSV com os dados dos usuários, se o arquivo estiver vazio
                } else if(cmdSec.matches("limparcsv") || cmdSec.matches("lcsv")) {
                    aq.escreverAoCSV(Main.CSVUSUARIO, null);
                    aq.criarCSVeMontarCabecalho(Main.CSVUSUARIO);
                    System.out.println("CSV limpo.");
                    entradaUsuario();

                // "loga" o usuário no app
                // TODO: perguntar se o usuário quer logar pra sempre no aplicativo, salvar dados num arquivo config
                // tipo permalogin=username em dados/ctcli.config
                } else if(cmdSec.matches("logar") || cmdSec.matches("l")) {
                    if(cmd.length != 3) {
                        System.out.println("Campo de usuário em branco. Digite um usuário para logar");
                        System.out.println("Comando: logar [usuario]");
                        entradaUsuario();

                    } else if(!(Usuario.usuarioExiste(cmd[2]))) {
                        System.out.println("Usuário não encontrado. Tente novamente");
                        entradaUsuario();

                    } else {
                        CLIUtil.clear();
                        System.out.println("Logado com sucesso.\n");
                        System.out.println("Você está no seu submenu pessoal.");
                        System.out.println(ANSI_GREEN+"\nDigite \"ajuda\" para obter os comandos disponíveis neste submenu.\n"+ANSI_RESET);
                        // System.out.println();
                        System.out.println(ANSI_GREEN+"Digite \"permalogin 1\" para habilitar login automático para esse usuário."+ANSI_RESET);


                        entradaAlimentos(cmd[2]);
                        entradaUsuario();
                    }
                }
                
                
                
                else {System.out.println("Comando inválido."); entradaUsuario();}

            /* COMANDOS PRINCIPAIS */
            } else if(cmdPrinc.matches("alimento") || cmdPrinc.matches("a")) {
                System.out.print("\nPara obter acesso aos comandos de alimento, você precisa logar como um usuário salvo usando ");
                System.out.print("\"usuario logar [nome do usuário]\n\"");
                System.out.println("Caso não tenha salvo seus dados, use \"usuario adicionar [nome] [peso] [altura]\"");
                entradaUsuario();
            } else if(cmdPrinc.matches("sair") || cmdPrinc.matches("s")) {
                CLIUtil.clear();
                System.exit(0);
                
            } else if(cmdPrinc.matches("clear") || cmdPrinc.matches("c")) {
                CLIUtil.clear();
                entradaUsuario();

            } else if(cmdPrinc.matches("ajuda")) {
                mostrarComandosUsuario();
                entradaUsuario();

            } else if(cmdPrinc.matches("adc")) {
                mostrarComandosUsuarioAdicionais();
                entradaUsuario();
            }

            else {System.out.println("entradaUsuario: Comando inválido."); entradaUsuario();}

        }

        void entradaAlimentos(String usuario) {
            sb.setLength(0);
            // System.out.println("werks");

            // ex: peso, altura, nivelatv só devem conter números
            System.out.println();
            String[] cmd = new String[10];
            String cmdStr = "";
            while(cmdStr.isEmpty()) {
                System.out.print(usuario+" ");
                cmdStr = CLIUtil.getUserInput();
            }

            // Interpreta corretamente o comando se só tiver um argumento
            try {
                // System.out.println(cmdStr);
                cmd = cmdStr.split(" ");

            } catch (NullPointerException e) {
                // System.out.println("caught");
                cmd[0] = cmdStr;
                System.out.println(cmdStr);

            }

            String cmdPrinc = cmd[0];
            String cmdSec;
            try {
                cmdSec = cmd[1];
            } catch (ArrayIndexOutOfBoundsException e) {
                cmdSec = "";
            }

            // TODO: comandos de logar no CSV pessoal

            if(cmdPrinc.matches("alimento") || cmdPrinc.matches("a")) {

                // *CMD - descrição: adiciona um alimento ao CSVALIMENTOS (Não pessoal)
                // Sintaxe: alimento adicionar [nome] [kcal/100g]
                // Atalho(s): [a]dicionar - a
                if(cmdSec.matches("adicionar") || cmdSec.matches("a")) {
                    if(cmd.length < 4) {
                        // System.out.println("cmd length:"+cmd.length);

                        System.out.println("Número de argumentos inválido. Tente novamente.");
                        System.out.println("Uso: alimento adicionar [nome] [kcal/100g]");
                        entradaAlimentos(usuario);

                    // detecta se o usuário errou a ordem dos parâmetros
                   } else if(cmd[2].matches("[0-9]+")) {
                       System.out.println("O nome do alimento vem antes das calorias.");
                       System.out.println("Uso: alimento adicionar [nome] [kcal/100g]");
                       entradaAlimentos(usuario);
                   }
                   System.out.println("cmd length:"+cmd.length);
                    // c contém o número de partes do comando que tem letras
                    // ATENÇÃO: Também conta o comando em si (addalimento)
                    int c = 0;
                    // localização do número de calorias em cmd[]
                    int temNum = 0;
                    // Controle: usado pra checar se o usuário digitou mais de um argumento [kcal]
                    int temNumQ = 0;

                    for(int i=0;i<cmd.length;i++) {
                        // verdadeiro se cmd[i] conter SOMENTE letras
                        if(cmd[i].matches("[a-zA-Z]+")) {
                            // System.out.println("match "+c);
                            c++;
                        }
                        
                        // verdadeiro se cmd[i] conter SOMENTE números
                        if(cmd[i].matches("[0-9]+")) {
                            temNumQ++;
                            temNum = i;
                            // System.out.println("matches!");
                        }

                    }

                    //System.out.println(cmd.length);
                    //System.out.println(temNum+1);
                    //entradaAlimentos(usuario);
                    
                    // checa se o usuário realmente adicionou as calorias antes de continuar
                    if(temNum == 0) {
                        System.out.println("Você esqueceu de adicionar as calorias");
                        System.out.println("Uso: alimento adicionar [nome] [kcal/100g]");
                        entradaAlimentos(usuario);

                    } else if(temNumQ > 1) {
                        System.out.println("Comando inválido. (mais de um argumento [kcal])");
                        System.out.println("Uso: alimento adicionar [nome] [kcal/100g]");
                        entradaAlimentos(usuario);
                    
                    // checa se existe algum outro comando depois de [kcal], se sim, printar erro.
                    } else if (temNum+1 < cmd.length) {
                        System.out.println("Comando inválido. (Ordem incorreta)");
                        System.out.println("Uso: alimento adicionar [nome] [kcal/100g]");
                        entradaAlimentos(usuario);
                    }

                    // checa se o nome do alimento tem muitos espaços
                    if(c > 5) {
                        System.out.println("Nome do alimento muito grande. (Muitos espaços)");
                        System.out.println("Uso: alimento adicionar [nome] [kcal/100g]");
                        entradaAlimentos(usuario);

                    }

                    // debug
                    //System.out.println("num array position: "+temNum);
                    // System.out.println(c);
                    // Objeto StringBuilder pra juntar as partes do comando que são o nome do alimento
                    // StringBuilder sb = new StringBuilder(50);
                    for(int i=2; i<c;i++) {
                        // Se o nome do alimento for muito grande, printar erro.
                        if(cmd[i].length() > 10) {
                            System.out.println("Nome do alimento muito grande!");
                            System.out.println("Uso: alimento adicionar [nome] [kcal/100g]");
                            entradaAlimentos(usuario);
                        }
                        sb.append(" "+cmd[i]);

                    }
                    // no CSV, os espaços serão substituidos por underlines
                    System.out.println(sb.toString().trim().replace(" ","_"));
                    String validA = sb.toString().trim().replace(" ","_");
                    Alimento ali = new Alimento(validA,cmd[temNum]);
                    if(ali.adicionarAlimento() == true) {
                        System.out.println("Alimento adicionado com sucesso!");
                        entradaAlimentos(usuario);

                    } else {
                        System.out.println("Alimento não adicionado pois já existe. Tente novamente.");
                        System.out.println("Uso: alimento adicionar [nome] [kcal/100g]");
                        entradaAlimentos(usuario);
                    }

                    System.out.println("Você não é suposto a ver essa mensagem. Oops!");
                    System.exit(0);
                
                // *CMD - descrição: printa os dados de um usuário
                // Sintaxe: alimento print [nome do usuário]
                // Atalho(s): [p]rint - p
                } else if(cmdSec.matches("print") || cmdSec.matches("p")) {
                    if(cmd.length < 3 || cmd.length > 10) {
                        System.out.println("Argumento inválido");
                        System.out.println("Uso: alimento print [nome]");
                        entradaAlimentos(usuario);

                    }
                    // acrescentando nome ao objeto sb
                    for(int i=2;i<cmd.length;i++) {
                        sb.append(" "+cmd[i]);

                    }

                    // System.out.println(sb.toString().trim().replace(" ","_"));

                    Alimento ali = new Alimento(sb.toString().trim().replace(" ","_"));
                    System.out.println(Arrays.toString(ali.getDadosAlimento(sb.toString().trim().replace(" ","_"))));
                    entradaAlimentos(usuario);

                // *CMD - descrição: printa os dados do alimento, com descrições
                // Sintaxe: alimento printpretty [alimento]
                // Atalho(s): [p]rint[p]retty - pp
                } else if(cmdSec.matches("printpretty") || cmdSec.matches("pp")) {
                    Alimento ali = new Alimento();
                    if(cmd.length < 3 || cmd.length > 10) {
                        System.out.println("Argumentos insuficientes.");
                        System.out.println("Uso: alimento printpretty [nome]");

                        entradaAlimentos(usuario);
                    } else {
                        for(int i=2;i<cmd.length;i++) {
                            sb.append(" "+cmd[i]);
                        }
                        ali.printDadosAlimento(sb.toString().trim().replace(" ","_"));
                        entradaAlimentos(usuario);
                    }

                // *CMD - descrição: printar todos os alimentos em Main.CSVALIMENTOS
                // Sintaxe: alimento printall
                // Atalho(s): [p]rint[a]ll - pa
                } else if(cmdSec.matches("printall") || cmdSec.matches("pa")) {
                    Alimento.printAlimentos();
                    entradaAlimentos(usuario);
                
                // *CMD - descrição: remover um alimento
                // Sintaxe: alimento remover [alimento]
                // Atalho(s): [r]emover - r
                } else if(cmdSec.matches("remover") || cmdSec.matches("r")) {
                    if(cmd.length<3) {
                        System.out.println("Argumento inválido");
                        System.out.println("Uso: alimento remover [nome]");
                        entradaAlimentos(usuario);
                    }
                    // acrescentando todas as partes do nome do alimento ao objeto sb (StringBuilder)
                    for(int i=2;i<cmd.length;i++) {
                        sb.append(" "+cmd[i]);

                    }
                    // recriando o objeto ali denovo
                    Alimento ali = new Alimento(sb.toString().trim().replace(" ","_"));
                    if(ali.removerAlimento(ali.nome) == false) {
                        System.out.println("Alimento não removido pois não existe.");
                        System.out.println("Uso: alimento remover [nome]");
                        entradaAlimentos(usuario);

                    }
                    System.out.println("Alimento removido.");
                    entradaAlimentos(usuario);

                // *CMD - descrição: alterar dados do alimento
                // Sintaxe: alimento alterar [nome] [propriedade] [novo valor da propriedade]
                // Atalho(s): [alt]erar - alt
                } else if(cmdSec.matches("alterar") || cmdSec.matches("alt")) {
                    if(cmd.length < 5 || cmd.length > 15) {
                        System.out.println("ERRO: Quantidade de parâmetros inválida.");
                        entradaAlimentos(usuario);
                    }
                    // armazena em qual posição o campo [propriedade] está em cmd[]
                    int c2 = 0;
                    // indica se o algoritmo achou mais de uma propriedade
                    int match = 0;

                    for(int i=2;i<cmd.length;i++) {
                        if(cmd[i].matches("kcal")) {
                            c2 = i;
                            match++;
                            try {
                                if(!(cmd[i+1].matches("[0-9]+"))) {
                                    System.out.println("Comando inválido. (Valor inválido depois de [propriedade], deveria ser [kcal].");
                                    entradaAlimentos(usuario);
                                
                                // se a diferença entre o comprimento do array de comando e a posição do valor depois de kcal for
                                // um valor diferente de 1, significa que o usuario adicionou um valor a mais depois
                                // desse valor, então printar erro.
                                } else if ((cmd.length - (i+1)) != 1) {
                                    //System.out.println("cmd length: "+cmd.length);
                                    //System.out.println("i+1: "+(i+1));
                                    System.out.println("ERRO: Só é necessário um [valor].");
                                    entradaAlimentos(usuario);
                                }

                            } catch (ArrayIndexOutOfBoundsException e) {
                                System.out.println("Comando inválido. (Valor inválido depois de [propriedade], deveria ser [kcal].");
                                entradaAlimentos(usuario);
                                
                            }

                        } else if(cmd[i].matches("nome")) {
                            c2 = i;
                            match++;
                            try {
                                if(!(cmd[i+1].matches("[a-zA-Z]+"))) {
                                    System.out.println("Comando inválido. (Valor inválido depois de [propriedade], deveria ser [nome].");
                                    entradaAlimentos(usuario);
                                }

                            } catch (ArrayIndexOutOfBoundsException e) {
                                System.out.println("Comando inválido. (Valor inválido depois de [propriedade], deveria ser [nome].");
                                entradaAlimentos(usuario);
                            }
                            // System.out.println("cmd length: "+cmd.length+" i: "+i);

                        }
                    }

                    System.out.println("c2: "+c2+" match: "+match);


                    if(c2==0) {
                        System.out.println("Comando inválido. (Valor inválido no campo [propriedade]).");
                        entradaAlimentos(usuario);
                    } else if(match > 1) {
                        System.out.println("Comando inválido. (Múltiplas propriedades).");
                        entradaAlimentos(usuario);
                    }

                    // adiciona o nome do alimento no objeto StringBuilder
                    // necessário para suporte a espaços nos nomes
                    for(int i=2;i<c2;i++) {
                        sb.append(" "+cmd[i]);
                    }
                    String nomeLimpo = sb.toString().trim().replace(" ","_");
                    System.out.println(nomeLimpo);
                    // recriar instância do objeto Alimento
                    Alimento ali = new Alimento(nomeLimpo);
                    if(!(ali.alimentoExiste(ali.nome))) {
                        System.out.println("Erro: o alimento que você está tentando editar não existe.");
                        entradaAlimentos(usuario);
                    }

                    // chama o método alterarDados() dependendo em qual propriedade o usuário escolheu
                    switch(cmd[c2]) {
                        case "kcal":
                            System.out.println(cmd[c2]+" "+cmd[c2+1]);
                            entradaAlimentos(usuario);
                            ali.alterarDados(ali.nome, cmd[c2], cmd[c2+1], false);
                            entradaAlimentos(usuario);
                            break;

                        case "nome":
                            StringBuilder nom = new StringBuilder(50);
                            int diff = cmd.length - c2;
                            for(int i=2;i<diff;i++) {
                                nom.append(" "+cmd[c2+i]);
                            }
                            String no = nom.toString().trim().replace(" ","_");
                            ali.alterarDados(ali.nome, cmd[c2], no, true);
                            entradaAlimentos(usuario);

                        case default:
                            System.exit(0);
                    }

                } // outros else ifs de cmdSec ficam aqui
                
                
                else {
                    System.out.println("Comando inválido. [secundário]"); entradaAlimentos(usuario);
                }

            } else if(cmdPrinc.matches("voltar")) {
                entradaUsuario();
            } else if(cmdPrinc.matches("sair") || cmdPrinc.matches("s")) {
                CLIUtil.clear();
                System.exit(0);
            } else if(cmdPrinc.matches("clear") || cmdPrinc.matches("c")) {
                CLIUtil.clear();
            } else if(cmdPrinc.matches("ajuda")) {
                mostrarComandosAlimentos();

            } else if(cmdPrinc.matches("permalogin")) {
                if(cmd.length<2) {
                    System.out.println("Argumentos insuficientes.");
                    System.out.println("Uso: permalogin [0 ou 1]");
                } else if(cmd.length>2) {
                    System.out.println("Quantidade de argumentos excedida.");
                    System.out.println("Uso: permalogin [0 ou 1]");
                } else if(cmdSec.matches("1")) {
                    if(Config.getPermaLoginUsr().equals(usuario)) {
                        System.out.println("Permalogin já está ativado para esse usuário.");
                    }
                    if(Config.addPermaLoginUsr(usuario)) {
                        System.out.println("Permalogin habilitado para o usuário "+usuario);
                    } else {
                        System.out.println("ERRO: Permalogin não habilitado.");
                        System.out.println("Uso: permalogin [0 ou 1]");
                    }
                } else if(cmdSec.matches("0")) {
                    if(Config.getPermaLoginUsr().equals(usuario)) {
                        if(Config.addPermaLoginUsr("")) {
                            System.out.println("Permalogin desativado para o usuário "+usuario);
                        } else {
                            System.out.println("Permalogin não desativado por algum motivo.");
                        }
                    } else {
                        System.out.println("Permalogin não está habilitado para esse usuário.");
                    }

                } else {
                    System.out.println("Argumentos inválidos.");
                    System.out.println("Uso: permalogin [0 ou 1]");

                }
                entradaAlimentos(usuario);

            }
            
            
            
            else {
                System.out.println("Comando inválido. [primário]"); entradaAlimentos(usuario);
            }
            entradaAlimentos(usuario);
        }
    }
}


    

