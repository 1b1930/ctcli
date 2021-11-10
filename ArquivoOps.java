import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// Essa classe contém todos os métodos que fazem operações com arquivos
public class ArquivoOps {

    public String converterParaCsv(String[] dado) {
        return Stream.of(dado)
            .map(this::escaparCaracteresEspeciais)
            .collect(Collectors.joining(","));
    }

    public String escaparCaracteresEspeciais(String dado) {
        String dadoEscapado = dado.replaceAll("\\R", " ");
        if (dado.contains(",") || dado.contains("\"") || dado.contains("'")) {
            dado = dado.replace("\"", "\"\"");
            dadoEscapado = "\"" + dado + "\"";
        }
        return dadoEscapado;
    }



    void lerCsv(File caminhoCsv) throws FileNotFoundException {
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
