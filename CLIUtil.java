import java.util.Scanner;

public class CLIUtil {
    // Método que cuida de pegar os dados entrados pelo usuário
    static String getUserInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\033[0;4m>>\033[0;0m ");
        String str = scanner.nextLine();
        str.replace("\n", "");
        if (!str.trim().isEmpty() && str != "") {

        } else {
            System.out.println("Comando inválido.");
            CLIUtil.getUserInput();
        }
        // scanner.close();
        return str;


    }

    // Esperar pela próxima nova linha antes de continuar
    static void waitNext() {
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        // scanner.close();
    }

    // Limpar saída do terminal
    static void clear() {
        System.out.print("\033[H\033[2J");
    }
}
