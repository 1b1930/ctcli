
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

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

    static String getDataHora() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");  
        LocalDateTime now = LocalDateTime.now();  
        return(dtf.format(now).toString());

    }

    static void printFileDirList(String dir) {
        try {
            List<File> files = Files.list(Paths.get(dir))
            .filter(Files::isRegularFile)
            .map(Path::toFile)
            .collect(Collectors.toList());
            //return files;
            files.forEach(System.out::println);

        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
