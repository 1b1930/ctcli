import java.io.FileReader;
import java.util.Arrays;
import java.io.BufferedReader;
import java.io.File;
import java.util.Scanner;

public class ArquivoOps {

    static String[] lerCsv(File caminhoCsv) {
        String[] dadosCsv = {""};
        try {
            // funcionou??? pelo menos um tico...
            BufferedReader _lerCsv = new BufferedReader(new FileReader(caminhoCsv));
            String row;
            while ((row = _lerCsv.readLine()) != null) {
                dadosCsv = row.split(",");
                // do something with the data
            }
            _lerCsv.close();
        } catch(Exception e) {
            System.out.println("Arquivo CSV não existe, fechando o programa...");
            System.exit(0);
        }

        for (String str : dadosCsv) {
            System.out.println(str);
        }

        return(dadosCsv);

    }

}
