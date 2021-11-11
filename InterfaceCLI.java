public class InterfaceCLI {

    MenuPrincipal mp = new MenuPrincipal();
    
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
        
        // Criando instância da subclasse MenuPrincipal
        // executando o método sobrescrito mostrar()
        mp.mostrar();
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

    class MenuPrincipal {
        // polimorfismo, sobrescrita do método mostrar();
        void mostrar() {
            // Caractere especial ANSI que faz o oposto do outro acima
            System.out.println("\033[0;0m");
            System.out.println("Comandos disponíveis:");
            // Checa se é a primeira vez que o programa está sendo executado
            if(ArquivoOps.checarPrimeiraExecucao()) {
                System.out.println("Comandos de Usuário:");
                System.out.println("addusuario [nome] [peso (kg)] [altura (cm)]");
                // System.out.println("remusuario [nome]");
                System.out.println("editusuario [nome] [propriedade (peso, altura, nivelatv)]");
                entradaUsuario();
                
                

            } else {
                System.out.println("\n- Verificar estatísticas de usuário");
                System.out.println("\tchecartdee");
                System.out.println("\n- Adicionar novos alimentos na base de dados:");
                System.out.println("\taddalimento [nome] [kcal/100g]");
                System.out.println("\tremalimento [nome]");
                System.out.println("\teditalimento [nome] [propriedade (kcal/100g)]");

                System.out.println("");
                System.out.println("logaralimento [nome] [quantidade consumida (g)]");
                entradaUsuario();

            }

        }

        // Método que cuida da entrada e sanitização de dados do usuário
        void entradaUsuario() {
            // TODO: Mais checagem de erros
            // ex: peso, altura, nivelatv só devem conter números
            String[] cmd = CLIUtil.getUserInput().split(" ");
            String cmdPrinc = cmd[0];
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
                        System.out.print("\033[H\033[2J");
                        System.out.println("O usuário já existe no banco de dados. Tente novamente.");
                        try {Thread.sleep(3000);} catch(InterruptedException e) {e.printStackTrace();};
                        mp.mostrar();
                    }

                    expNivelAtv();
                    String resp = getNivelAtv();
                    Usuario usuario = new Usuario(cmd[1],cmd[2],cmd[3],resp);
                    System.out.println(usuario.nome + usuario.peso + usuario.altura);
                    usuario.criarUsuario();
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
                    break;
                
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
