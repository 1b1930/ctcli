public class InterfaceCLI {
    
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
        MenuPrincipal mp = new MenuPrincipal();
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
                

            } else {
                System.out.println("\n- Verificar estatísticas de usuário");
                System.out.println("\tchecartdee");
                System.out.println("\n- Adicionar novos alimentos na base de dados:");
                System.out.println("\taddalimento [nome] [kcal/100g]");
                System.out.println("\tremalimento [nome]");
                System.out.println("\teditalimento [nome] [propriedade (kcal/100g)]");

                System.out.println("");
                System.out.println("logaralimento [nome] [quantidade consumida (g)]");
                // CLIUtil.getUserInput();

            }

        }

    }








    
}
