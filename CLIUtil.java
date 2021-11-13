import java.util.Scanner;

public class CLIUtil {

    // Segundo um cara no stackoverflow, scanners usados em múltiplos lugares
    // devem ficar fora de um método e ter como características public static e final
    // Funcionou por agora...
    public static final Scanner scanner = new Scanner(System.in);

    // Método que cuida de pegar os dados entrados pelo usuário
    static String getUserInput() {
        System.out.print("\033[0;4m>>\033[0;0m ");
        String str = scanner.nextLine();
        str.replace("\n", "");
        return str.trim();
    }

    // Esperar pela próxima nova linha antes de continuar
    static void waitNext() {
        // Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        // scanner.close();
    }

    // Limpar saída do terminal
    static void clear() {
        // Sempre use println e não print quando usar esse caractere especial
        // Se usar print(), dá erro depois de um tempo.
        System.out.println("\033[H\033[2J");
    }
}
