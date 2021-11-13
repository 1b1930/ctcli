
public class InterfaceCLI {

    final String NEGRITO = "\033[0;1m";
    final String ESTL = "\t\033[0;1m*\033[0;0m ";
    final String NORMAL = "\033[0;0m";
    final String SUBL = "\033[0;4m";
    final String BIGL = "⊢ ";
    final String SETA = "❯ ";

    public static final ArquivoOps aq = new ArquivoOps();

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
        // TODO: Decidi juntar logaralimento e addalimento num só.
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
            String[] cmd = new String[10];
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
            
            // Comandos que o usuário pode usar
            switch(cmdPrinc) {
                case "addusuario":
                    // Checa se a quantidade de argumentos dados pelo usuário está correta
                    // Essa descrição vale para todos os ifs diretamente abaixo dos cases
                    if(cmd.length < 4 || cmd.length > 4) {
                        System.out.println("Número de argumentos inválido. Tente novamente.");
                        entradaUsuario();
                        break;
                    }
                    // Checa se o usuário existe, se já existe, manda o usuário tentar novamente
                    if(Usuario.usuarioExiste(cmd[1])) {
                        // Char especial ANSI pra limpar a tela do console
                        // TODO: Deve ter uma maneira melhor de fazer isso.
                        System.out.println("O usuário já existe no banco de dados. Tente novamente.");
                        entradaUsuario();
                        try {Thread.sleep(3000);} catch(InterruptedException e) {e.printStackTrace();};
                        mostrar();
                    }

                    expNivelAtv();
                    String resp = getNivelAtv();
                    Usuario usuario = new Usuario(cmd[1],cmd[2],cmd[3],resp);
                    usuario.criarUsuario();
                    entradaUsuario();
                    break;

                case "remusuario":
                    if(cmd.length != 2) {
                        System.out.println("Quantidade de argumentos inválida. Tente novamente.");
                        entradaUsuario();
                        break;
                    }
                    Usuario u = new Usuario();
                    if(u.removerUsuario(cmd[1])) {
                        System.out.println("Usuário removido");
                        entradaUsuario();
                    } else {
                        System.out.println("Usuário não removido"+
                        " pois não foi encontrado ou deu merda em outro lugar.");
                        entradaUsuario();
                    }
                    System.out.println("Argumento aceito");
                    entradaUsuario();
                    break;
                
                case "editusuario":
                    if(cmd.length < 4) {
                        System.out.println("Quantidade de argumentos insuficiente. Tente novamente.");
                        entradaUsuario();
                        break;
                    } else if(cmd.length > 4) {
                        System.out.println("Quantidade de argumentos excedida para esse comando. Tente novamente");
                        entradaUsuario();
                        break;
                    } 
                    // System.out.println("+"+cmd[2]+"+");
                    if(!(cmd[2].matches("peso") || cmd[2].matches("altura") || cmd[2].matches("nome") || cmd[2].matches("nivelatv"))) {
                        System.out.println("Propriedade inválida.");
                        System.out.println("Comando: editusuario [nome] [propriedade (peso, altura, nivelatv)] [valor]");
                        entradaUsuario();
                    }

                    Usuario u2 = new Usuario();
                    u2.alterarDados(cmd[1], cmd[2], cmd[3]);
                    System.out.println("Usuário editado com sucesso.");
                    
                    entradaUsuario();
                    break;
                
                case "printusuarios":
                    Usuario.printUsuarios();
                    entradaUsuario();
                    break;
                
                // TODO: add suporte pra selecionar entre os dois CSVs
                case "criarcabecalho":
                    aq.criarCSVeMontarCabecalho(Main.CSVUSUARIO);
                    entradaUsuario();
                    break;

                // TODO: add suporte pra selecionar entre os dois CSVs
                case "limparcsv":
                    aq.escreverAoCSV(Main.CSVUSUARIO, null);
                    System.out.println("CSV limpo.");
                    entradaUsuario();
                    break;

                // Termina o programa
                case "sair":
                    // System.out.println("Saindo...");
                    CLIUtil.clear();
                    System.exit(0);

                // Limpa a janela do terminal
                case "clear":
                    CLIUtil.clear();
                    entradaUsuario();
                    break;
                
                // lista os comandos principais
                case "ajuda":
                    mostrarComandos();
                    entradaUsuario();    
                
                // lista os comandos adicionais
                case "adc":
                    mostrarComandosAdicionais();
                    entradaUsuario();
                
                // printdadosusuario - Printar os dados do usuário
                // abreviado pra debugar mais rápido ;)
                case "pdu":
                    // TODO: talvez mover a checagem de existência do usuário pra cá?
                    if(cmd.length != 2) {
                        System.out.println("Argumentos inválidos/insuficientes.");
                        entradaUsuario();
                    } else {
                        Usuario.printDadosUsuario(cmd[1]);
                        entradaUsuario();
                    }
                    entradaUsuario();

                /* PARTE DOS COMANDOS DE ALIMENTOS */
                
                // "loga" o usuário, joga ele no submenu de alimentos para que possa adicionar ou logar alimentos
                // TODO: talvez seja melhor jogar o usuário num menu intermediário que tenha opção de ir pro menu
                // de alimentos além de outros menus como o de TDEE e a lista de calorias gastas no dia.
                case "logar":
                    if(cmd.length != 2) {
                        System.out.println("Campo de usuário em branco. Digite um usuário para logar");
                        System.out.println("Comando: logar [usuario]");
                        entradaUsuario();
                        break;
                    } else if(!(Usuario.usuarioExiste(cmd[1]))) {
                        System.out.println("Usuário não encontrado. Tente novamente");
                        entradaUsuario();
                        break;
                    } else {
                        entradaAlimentos(cmd[1]);
                        entradaUsuario();
                        break;

                    }

                case default:
                    System.out.println("Comando inválido.");
                    entradaUsuario();

            }

        }

        // TODO: Submenu que vai tomar conta dos comandos de alimentos, igual entradaUsuario
        void entradaAlimentos(String usuario) {
            System.out.println("werks");
            // TODO: Mais checagem de erros
            // ex: peso, altura, nivelatv só devem conter números
            System.out.println();
            String[] cmd = new String[10];
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
            // submenu, basicamente a mesma coisa de entradaUsuarios

            // TODO: Codar o resto disso fdp
            // TODO: função printAlimentos
            // TODO: função printAlimentosConsumidos
            // TODO: Função deletar alimentos
            // TODO: Função editar alimentos
            switch(cmdPrinc) {
                // TODO: tentando fazer um bagulho dinâmico que vai permitir ao usuário digitar qualquer alimento, até nomes com espaço
                // sem nenhum problema
                case "addalimento":
                   if(cmd.length < 3) {
                       System.out.println("Número de argumentos inválido. Tente novamente.");
                       entradaAlimentos(usuario);

                   }
                    int c = 0;
                    for(int i=0;i<cmd.length;i++) {
                        // se conter somente letras
                        if(cmd[i].matches("[a-zA-Z]+")) {
                            System.out.println("match"+c);
                            c++;
                        }
                    }
                    System.exit(0);
                    break;
                
                // comando de teste pra checar se alimentoExiste() tá funcionando
                case "ax":
                    if(Alimento.alimentoExiste("eileen")) {
                        System.out.println("werks");
                        System.exit(0);
                    } else {
                        System.out.println("dont werk");
                        System.exit(0);
                    }


                    //else {
                       
                //        Alimento ali = new Alimento(cmd[1], cmd[2]);
                //        ali.adicionarAlimento();
                //        entradaAlimentos(usuario);

                   }
                }
            }
        }


    

