import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import java.io.PrintWriter;


// Essa classe contém todos os métodos que fazem operações com arquivos
public class ArquivoOps {

    /* TODO: Obviamente modificar essa função pra ser dinâmica e atender as necessidades do programa. */
    void escreverCsv() {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new File("/mnt/hdd/code/DadosUsuario.csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        StringBuilder builder = new StringBuilder();
        // A última coluna, DataAtt, refere-se a última vez que os dados foram atualizados.  
        String nomeColunas = "UsrId,Nome,Peso,Altura,NivelAtividade,DataAtt";
        // No need give the headers Like: id, Name on builder.append
        builder.append(nomeColunas +"\n");
        builder.append("1"+",");
        builder.append("Daniel");
        builder.append('\n');
        pw.write(builder.toString());
        pw.close();
        System.out.println("CSV escrito.");
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
