import java.util.Scanner;

public class CLIUtil {

    // Segundo um cara no stackoverflow, scanners usados em múltiplos lugares
    // devem ficar fora de um método e ter como características public static e final
    // Funcionou por agora...
    public static final Scanner scanner = new Scanner(System.in);

    // Método que cuida de pegar os dados entrados pelo usuário
    static String getUserInput() {
        System.out.print("\033[0;4m>>\033[0;0m ");
        if(scanner.hasNextLine()) {
            String str = scanner.nextLine();
            str.replace("\n", "");

            if (!str.trim().isEmpty() && str != "") {

            } else {
                System.out.println("Comando inválido.");
                CLIUtil.getUserInput();
            }
            return str;

        }
        return "aaa";




    }

    // Esperar pela próxima nova linha antes de continuar
    static void waitNext() {
        // Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        // scanner.close();
    }

    // Limpar saída do terminal
    static void clear() {
        System.out.print("\033[H\033[2J");
    }
}
