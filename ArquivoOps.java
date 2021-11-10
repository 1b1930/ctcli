import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import com.opencsv.CSVReader;

import java.io.PrintWriter;


// Essa classe contém todos os métodos que fazem operações com arquivos
public class ArquivoOps {

    public void lerDadosLinhaPorLinha(String arq) {
 
    try {
 
        // Criar um objeto da classe FileReader
        // com o arquivo .csv como parâmetro
        FileReader leitorDeArquivo = new FileReader(arq);
 
        // Criar um objeto da classe CSVReader 
        // file reader as a parameter
        CSVReader leitorCsv = new CSVReader(leitorDeArquivo);
        String[] proximaEntrada;
 
        // we are going to read data line by line
        while ((proximaEntrada = leitorCsv.readNext()) != null) {
            for (String cell : proximaEntrada) {
                System.out.print(cell + "\t");
            }
            System.out.println();
        }
    }
    catch (Exception e) {
        e.printStackTrace();
    }
}


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

}
