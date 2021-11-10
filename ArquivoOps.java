import java.io.FileReader;
import java.util.Arrays;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

// Essa classe contém todos os métodos que fazem operações com arquivos
public class ArquivoOps {

    static void lerCsv(File caminhoCsv) throws FileNotFoundException {
        // OK, esse funciona direito. Lê todas as linhas e colunas do arquivo csv
        // TODO: Botar esses dados num ArrayList (2d?) para poderem ser manipulados ou printados
        Scanner scanner = new Scanner(caminhoCsv);
        scanner.useDelimiter(",");

        while (scanner.hasNext()) {
            System.out.println(scanner.next());
        }
        scanner.close();
    }

}
