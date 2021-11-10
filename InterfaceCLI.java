public class InterfaceCLI {
    
    void mostrar() {
        // Caracteres especiais ANSI para fazer o texto ficar em negrito
        System.out.println("\033[0;1m");
        // mensagem de boas vindas + explicação do app
        System.out.println("Bem vindo ao ctcli.\n");
        System.out.println("Esse aplicativo irá lhe ajudar a contar suas calorias.");
        System.out.println("Ele também informa o seu valor total de energia gasta por dia" +
        " " + "(sigla em inglês: TDEE)");
        System.out.println("O TDEE é importante pelo fato de ");
    }

    class MenuPrincipal {
        void mostrar() {
            System.out.println("");
        }
    }






    
}
