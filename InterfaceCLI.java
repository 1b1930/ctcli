import java.lang.reflect.Array;
import java.util.Arrays;


// Ex: usuario logar daniel; alimento adicionar arroz 40; usuario remover daniel

public class InterfaceCLI {

    // É, eu sei que seria melhor criar uma classe num outro arquivo só pra interpretar os comandos
    // É minha primeira vez fazendo algo assim e percebi isso muito tarde, agora não tem tempo.

    final String NEGRITO = "\033[0;1m";
    final String ESTL = "\t\033[0;1m*\033[0;0m ";
    final String NORMAL = "\033[0;0m";
    final String SUBL = "\033[0;4m";
    final String BIGL = "⊢ ";
    final String SETA = "❯ ";

    public static final ArquivoOps aq = new ArquivoOps();
    public static final StringBuilder sb = new StringBuilder(50);
    public static final Alimento ali = new Alimento();

    void mostrar() {
        // Caracteres especiais ANSI para fazer o texto ficar em negrito
        System.out.println("\033[0;1m");
        // mensagem de boas vindas + explicação do app
        System.out.println("Bem vindo ao ctcli.\n");
        System.out.println("Esse programa irá ajudar você a contar suas calorias.");
        System.out.println("Ele também informa o seu valor total de energia gasta por dia" +
        " " + "(sigla em inglês: TDEE)");
        System.out.println("Isso ajuda você a controlar seu peso, já que se as calorias consumidas por dia" +
        " excederem o valor do TDEE, você irá ganhar peso.");

        System.out.println("\nDigite \"ajuda\" para obter todos os comandos disponíveis");
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

    public void mostrarComandos() {
        CLIUtil.clear();
        // TODO: Isso tá muuuuuito desatualizado
        // System.out.println("\t\t"+NEGRITO+"Comandos"+NORMAL+"\n");       
        // Checa se é a primeira vez que o programa está sendo executado
        System.out.println("\t\t"+NEGRITO+"Comandos de Usuário"+NORMAL+"\n");
        System.out.println(SETA+"Manipular usuários na base de dados");
        System.out.println(ESTL+"addusuario [nome] [peso (kg)] [altura (cm)]");
        // System.out.println("remusuario [nome]");
        System.out.println(ESTL+"editusuario [nome] [propriedade (peso, altura, nivelatv)]");
        System.out.println("\n"+SETA+"Verificar estatísticas de usuário");
        System.out.println(ESTL+"checartdee");
        System.out.println("\n"+SETA+"Manipular alimentos na base de dados:");
        // O algoritmo irá checar se o alimento existe, se não, irá criar uma entrada
        System.out.println(ESTL+"logaralimento [nome] [kcal/100g]");
        System.out.println(ESTL+"remalimento [nome]");
        System.out.println(ESTL+"editalimento [nome] [propriedade (kcal/100g)]");
        System.out.println("\nDigite \"adc\" para obter comandos adicionais");

        MenuPrincipal mp = new MenuPrincipal();
        mp.entradaUsuario();

    }
    

    void mostrarComandosAdicionais() {

        System.out.println("\t\t"+NEGRITO+"Comandos adicionais\n");
        System.out.println(ESTL+"clear - Limpar saída do terminal");
        System.out.println(ESTL+"limparcsv - Limpar arquivo CSV, deletará todos os dados.");

        System.out.println(ESTL+"printusuarios - Printa todos os usuários");
        System.out.println(ESTL+"printdadosusuario - Printa os dados de um usuário");

        System.out.println(ESTL+"criarcabecalho - Só pode ser usado se o .csv estiver limpo, cria um cabeçalho.");
        MenuPrincipal mp = new MenuPrincipal();
        mp.entradaUsuario();

    }

    class MenuPrincipal extends InterfaceCLI {
        // polimorfismo, sobrescrita do método mostrar();
        void mostrar() {
            // Caractere especial ANSI que faz o oposto do outro acima
            // System.out.println("\033[0;0m");
            // mostrarComandos();
        }

        // Método que cuida da entrada e sanitização de dados do usuário
        void entradaUsuario() {
            // TODO: Mais checagem de erros
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
                    if(cmd.length < 5 || cmd.length > 5) {
                        System.out.println("Número de argumentos inválido. Tente novamente.");
                        entradaUsuario();
                    }
                    // Checa se o usuário existe, se já existe, manda o usuário tentar novamente
                    if(Usuario.usuarioExiste(cmd[2])) {
                        // Char especial ANSI pra limpar a tela do console
                        // TODO: Deve ter uma maneira melhor de fazer isso.
                        System.out.println("O usuário já existe no banco de dados. Tente novamente.");
                        entradaUsuario();
                        try {Thread.sleep(3000);} catch(InterruptedException e) {e.printStackTrace();};
                        mostrar();
                    }

                    expNivelAtv();
                    String resp = getNivelAtv();
                    Usuario usuario = new Usuario(cmd[2],cmd[3],cmd[4],resp);
                    usuario.criarUsuario();
                    entradaUsuario();
                    
                // remove um usuário do csv
                } else if(cmdSec.matches("remover") || cmdSec.matches("r")) {
                    if(cmd.length != 3) {
                        System.out.println("Quantidade de argumentos inválida. Tente novamente.");
                        entradaUsuario();
                    }
                    Usuario u = new Usuario();
                    if(u.removerUsuario(cmd[2])) {
                        System.out.println("Usuário removido");
                        entradaUsuario();
                    } else {
                        System.out.println("Usuário não removido"+
                        " pois não foi encontrado.");
                        entradaUsuario();
                    }
                    // System.out.println("Argumento aceito");
                    entradaUsuario();
                
                // edita o dado especificado
                } else if(cmdSec.matches("editar") || cmdSec.matches("e")) {
                    if(cmd.length < 5) {
                        System.out.println("Quantidade de argumentos insuficiente. Tente novamente.");
                        entradaUsuario();

                    } else if(cmd.length > 5) {
                        System.out.println("Quantidade de argumentos excedida para esse comando. Tente novamente");
                        entradaUsuario();

                    } 
                    // System.out.println("+"+cmd[2]+"+");
                    if(!(cmd[3].matches("peso") || cmd[3].matches("altura") || cmd[3].matches("nome") || cmd[3].matches("nivelatv"))) {
                        System.out.println("Propriedade inválida.");
                        System.out.println("Comando: editusuario [nome] [propriedade (peso, altura, nivelatv)] [valor]");
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
                
                // cria o cabeçalho do CSV com os dados dos usuários, se o arquivo estiver vazio
                } else if(cmdSec.matches("criarcabecalho") || cmdSec.matches("cc")) {
                    aq.criarCSVeMontarCabecalho(Main.CSVUSUARIO);
                    entradaUsuario();
                
                // limpa o arquivo csv do usuário, incluindo o cabeçalho
                } else if(cmdSec.matches("limparcsv") || cmdSec.matches("lcsv")) {
                    aq.escreverAoCSV(Main.CSVUSUARIO, null);
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
                        System.out.println("Indo para o menu de alimentos");
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
                mostrarComandos();
                entradaUsuario();

            } else if(cmdPrinc.matches("adc")) {
                mostrarComandosAdicionais();
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
            // submenu, basicamente a mesma coisa de entradaUsuarios

            // TODO: Refazer isso usando if-else

            // TODO: Codar o resto disso fdp

            // TODO: função printAlimentosConsumidos

            switch(cmdPrinc) {
                // TODO: tentando fazer um bagulho dinâmico que vai permitir ao usuário digitar qualquer alimento, até nomes com espaço
                // sem nenhum problema
                case "addalimento":
                    // o numero de parâmetros minimo é 2 (comprimento do array 3)
                   if(cmd.length < 3) {
                        System.out.println("cmd length:"+cmd.length);

                        System.out.println("Número de argumentos inválido. Tente novamente.");
                        entradaAlimentos(usuario);

                    // detecta se o usuário errou a ordem dos parâmetros
                   } else if(cmd[1].matches("[0-9]+")) {
                       System.out.println("O nome do alimento vem antes das calorias.");
                       System.out.println("Tente novamente.");
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
                            System.out.println("match "+c);
                            c++;
                        }
                        
                        // verdadeiro se cmd[i] conter SOMENTE números
                        if(cmd[i].matches("[0-9]+")) {
                            temNumQ++;
                            temNum = i;
                            System.out.println("matches!");
                        }

                    }

                    //System.out.println(cmd.length);
                    //System.out.println(temNum+1);
                    //entradaAlimentos(usuario);
                    
                    // checa se o usuário realmente adicionou as calorias antes de continuar
                    if(temNum == 0) {
                        System.out.println("Você esqueceu de adicionar as calorias");
                        System.out.println("Tente novamente.");
                        entradaAlimentos(usuario);

                    } else if(temNumQ > 1) {
                        System.out.println("Comando inválido. (mais de um argumento [kcal])");
                        System.out.println("Tente novamente.");
                        entradaAlimentos(usuario);
                    
                    // checa se existe algum outro comando depois de [kcal], se sim, printar erro.
                    } else if (temNum+1 < cmd.length) {
                        System.out.println("Comando inválido. (Ordem incorreta)");
                        System.out.println("Tente novamente.");
                        entradaAlimentos(usuario);
                    }

                    // checa se o nome do alimento tem muitos espaços
                    if(c > 5) {
                        System.out.println("Nome do alimento muito grande. (Muitos espaços)");
                        System.out.println("Tente novamente.");
                        entradaAlimentos(usuario);

                    }

                    // debug
                    System.out.println("num array position: "+temNum);
                    System.out.println(c);
                    // Objeto StringBuilder pra juntar as partes do comando que são o nome do alimento
                    // StringBuilder sb = new StringBuilder(50);
                    for(int i=1; i<c;i++) {
                        // Se o nome do alimento for muito grande, printar erro.
                        if(cmd[i].length() > 10) {
                            System.out.println("Nome do alimento muito grande!");
                            System.out.println("Tente novamente.");
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
                        entradaAlimentos(usuario);
                    }

                    System.exit(0);
                    break;
                
                // comando de teste pra checar se alimentoExiste() tá funcionando
                case "ax":
                    ali = new Alimento();
                    if(ali.alimentoExiste("eileen")) {
                        System.out.println("werks");
                        System.exit(0);
                    } else {
                        System.out.println("dont werk");
                        System.exit(0);
                    }
                
                // printar os dados do alimento dado como parâmetro
                case "pda":
                    if(cmd.length < 2 || cmd.length > 10) {
                        System.out.println("Argumento inválido");
                        entradaAlimentos(usuario);

                    }
                    // acrescentando nome ao objeto sb
                    for(int i=1;i<cmd.length;i++) {
                        sb.append(" "+cmd[i]);

                    }
                    // TODO: Posso ganhar performance não chamando o replace(), botando underlines direto em sb.append();
                    // System.out.println(sb.toString().trim().replace(" ","_"));

                    ali = new Alimento(sb.toString().trim().replace(" ","_"));
                    System.out.println(Arrays.toString(ali.getDadosAlimento(sb.toString().trim().replace(" ","_"))));
                    entradaAlimentos(usuario);


                case "remalimento":
                    if(cmd.length<2) {
                        System.out.println("Argumento inválido");
                        entradaAlimentos(usuario);
                    }
                    // acrescentando todas as partes do nome do alimento ao objeto sb (StringBuilder)
                    for(int i=1;i<cmd.length;i++) {
                        sb.append(" "+cmd[i]);

                    }
                    // recriando o objeto ali denovo
                    ali = new Alimento(sb.toString().trim().replace(" ","_"));
                    if(ali.removerAlimento(ali.nome) == false) {
                        System.out.println("Alimento não removido pois não existe.");
                        entradaAlimentos(usuario);

                    }
                    System.out.println("Alimento removido.");
                    entradaAlimentos(usuario);

                
                case "altalimento":
                    if(cmd.length < 4 || cmd.length > 15) {
                        System.out.println("ERRO: Quantidade de parâmetros inválida.");
                        entradaAlimentos(usuario);
                    }
                    // armazena em qual posição o campo [propriedade] está em cmd[]
                    int c2 = 0;
                    // indica se o algoritmo achou mais de uma propriedade
                    int match = 0;

                    for(int i=1;i<cmd.length;i++) {
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
                    for(int i=1;i<c2;i++) {
                        sb.append(" "+cmd[i]);
                    }
                    String nomeLimpo = sb.toString().trim().replace(" ","_");
                    System.out.println(nomeLimpo);
                    // recriar instância do objeto Alimento
                    ali = new Alimento(nomeLimpo);
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
                            for(int i=1;i<diff;i++) {
                                nom.append(" "+cmd[c2+i]);
                            }
                            String no = nom.toString().trim().replace(" ","_");
                            ali.alterarDados(ali.nome, cmd[c2], no, true);
                            entradaAlimentos(usuario);

                        case default:
                            System.exit(0);
                            

                    }

                case "printalimentos":
                    Alimento.printAlimentos();
                    entradaAlimentos(usuario);

                case "pdap":
                ali = new Alimento();
                if(cmd.length < 2 || cmd.length > 10) {
                    System.out.println("Argumentos insuficientes.");
                    entradaAlimentos(usuario);
                } else {
                    for(int i=1;i<cmd.length;i++) {
                        sb.append(" "+cmd[i]);
                    }
                    ali.printDadosAlimento(sb.toString().trim().replace(" ","_"));
                    entradaAlimentos(usuario);
                    
                        
                }


                
                }



                }
            }
        }


    

