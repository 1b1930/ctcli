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

    class MenuPrincipal {
        // polimorfismo, sobrescrita do método mostrar();
        void mostrar() {
            // Caractere especial ANSI que faz o oposto do outro acima
            System.out.println("\033[0;0m");
            System.out.println("Comandos disponíveis:");
            if(ArquivoOps.checarPrimeiraExecucao()) {
                System.out.println("Comandos de Usuário:");
                System.out.println("addusuario [nome] [peso (kg)] [altura (cm)]");
                System.out.println("remusuario [nome]");
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

        void entradaUsuario() {
            String[] cmd = CLIUtil.getUserInput().split(" ");
            String cmdPrinc = cmd[0];
            switch(cmdPrinc) {
                case "addusuario":
                    if(cmd.length != 4) {
                        System.out.println("Quantidade de argumentos inválida. Tente novamente.");
                        entradaUsuario();
                        break;
                    }
                    System.out.println("Argumento aceito.");
                    if(Usuario.usuarioExiste(cmd[1])) {
                        System.out.println("O usuário já existe no banco de dados. Tente novamente.");
                        try {Thread.sleep(500);} catch(InterruptedException e) {e.printStackTrace();};
                        mp.mostrar();
                    }
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
                
                default:
                    System.out.println("Argumento inválido.");
                    entradaUsuario();
                    break;
            }

            for(int i=0;i<cmd.length; i++) {
                // System.out.println(cmd.length);
                System.out.println(cmd[i]);
            }
        }


    }










    
}
