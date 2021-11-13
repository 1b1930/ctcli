public class InterfaceCLI {

    final String NEGRITO = "\033[0;1m";
    final String ESTL = "\t\033[0;1m*\033[0;0m ";
    final String NORMAL = "\033[0;0m";
    final String SUBL = "\033[0;4m";
    final String BIGL = "⊢ ";
    final String SETA = "❯ ";

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
        } else if (Integer.parseInt(resp) < 1 || Integer.parseInt(resp) > 4) {
            System.out.println("Nível de atividade inválido. Tente novamente.");
            getNivelAtv();

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
            String[] cmd = CLIUtil.getUserInput().split(" ");
            String cmdPrinc = cmd[0];
            
            // Comandos que o usuário pode usar
            switch(cmdPrinc) {
                case "addusuario":
                    // Checa se a quantidade de argumentos dados pelo usuário está correta
                    // Essa descrição vale para todos os ifs diretamente abaixo dos cases
                    if(cmd.length < 4 || cmd.length > 4) {
                        System.out.println("Quantidade de argumentos maior ou menor do que o esperado. Tente novamente.");
                        entradaUsuario();
                        break;
                    }
                    System.out.println("Argumento aceito.");
                    // Checa se o usuário existe, se já existe, manda o usuário tentar novamente
                    if(Usuario.usuarioExiste(cmd[1])) {
                        // Char especial ANSI pra limpar a tela do console
                        // TODO: Deve ter uma maneira melhor de fazer isso.
                        CLIUtil.clear();
                        System.out.println("O usuário já existe no banco de dados. Tente novamente.");
                        try {Thread.sleep(3000);} catch(InterruptedException e) {e.printStackTrace();};
                        mostrar();
                    }

                    expNivelAtv();
                    String resp = getNivelAtv();
                    Usuario usuario = new Usuario(cmd[1],cmd[2],cmd[3],resp);
                    // DEBUG
                    // System.out.println(usuario.nome + usuario.peso + usuario.altura);
                    usuario.criarUsuario();
                    entradaUsuario();
                    break;

                case "remusuario":
                    if(cmd.length != 1) {
                        System.out.println("Quantidade de argumentos inválida. Tente novamente.");
                        entradaUsuario();
                        break;
                    }
                    System.out.println("Argumento aceito");
                    break;
                
                case "editusuario":
                    if(cmd.length < 2) {
                        System.out.println("Quantidade de argumentos insuficiente. Tente novamente.");
                        entradaUsuario();
                        break;
                    } else if(cmd.length > 2) {
                        System.out.println("Quantidade de argumentos excedida para esse comando. Tente novamente");
                        entradaUsuario();
                        break;
                    }
                    System.out.println("Argumento aceito.");
                    break;

                case "printusuarios":
                    Usuario.printUsuarios();
                    entradaUsuario();
                    break;
                
                // TODO: selecionar entre os dois CSVs
                case "criarcabecalho":
                    ArquivoOps arq = new ArquivoOps();
                    arq.criarCSVeMontarCabecalho(Main.CSVUSUARIO);
                    entradaUsuario();
                    break;
                // TODO: selecionar entre os dois CSVs
                case "limparcsv":
                    ArquivoOps.limparCSV();
                    System.out.println("CSV limpo.");
                    entradaUsuario();
                    break;

                case "sair":
                    // System.out.println("Saindo...");
                    CLIUtil.clear();
                    System.exit(0);

                case "clear":
                    CLIUtil.clear();
                    entradaUsuario();
                    break;
                    
                case "ajuda":
                    mostrarComandos();
                    entradaUsuario();    
                
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
                        String[] dados = Usuario.getDadosUsuario(cmd[1]);
                        if(dados == null) {
                            entradaUsuario();;
                        } else {
                            System.out.println("Nome: "+dados[0]);
                            System.out.println("Peso: "+dados[1]+"kg");
                            System.out.println("Altura: "+dados[2]+"cm");
                        }
                        


                        entradaUsuario();
                    }
                    entradaUsuario();

                default:
                    System.out.println("Argumento inválido.");
                    entradaUsuario();
                    break;
            }

            // for(int i=0;i<cmd.length; i++) {
            //     // System.out.println(cmd.length);
            //     System.out.println(cmd[i]);
            // }
        }


    }
}

    

