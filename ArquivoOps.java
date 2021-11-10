import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


// Essa classe contém todos os métodos que fazem operações com arquivos
public class ArquivoOps {

    public void lerDadosLinhaPorLinha(String arq) {
 
    try {
 
        // Criar um objeto da classe FileReader
        // com o arquivo .csv como parâmetro
        FileReader fileReader = new FileReader(arq);
 
        // Criar um objeto da classe CSVReader 
        // usa fileReader como parâmetro
        CSVReader leitorCsv = new CSVReader(fileReader);
        String[] proximaEntrada;
        // Controle de iteração pra consertar um probleminha estético no loop a seguir
        int it = 0;
        // Lendo os dados linha por linha
        while ((proximaEntrada = leitorCsv.readNext()) != null) {

            for (int i=0;i<proximaEntrada.length; i++) {
                String cell = proximaEntrada[i];
                if (i==proximaEntrada.length-1 && it == 1) {
                    System.out.print("\t\t" + cell);
                } else {System.out.print(cell + "\t");}
                
            }
//            for (String cell : proximaEntrada) {
//                System.out.print(cell + "\t");
//            }
            System.out.println();
            it++;
        }
    }
    catch (Exception e) {
        e.printStackTrace();
    }
}


    void escreverDadosLinhaPorLinha(String caminhoArq, String nome, String peso, String altura, String nivelatv)
    {

        // Cria objeto da classe File usando como parâmetro o caminho do arquivo csv
        File file = new File(caminhoArq);
        try {
            // Cria objeto da classe FileWriter com file como parâmetro
            FileWriter outputfile = new FileWriter(file);
    
            // Cria objeto da classe CSVWriter com objeto da classe FileWriter como parâmetro
            CSVWriter writer = new CSVWriter(outputfile);

            // Pegando a data atual para poder adicionar ao csv
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
            LocalDateTime horaAgora = LocalDateTime.now();  
            System.out.println(dtf.format(horaAgora));
    
            // adicionando cabeçalho ao csv
            String[] header = { "Nome", "Peso", "Altura", "Nível de Atividade", "Última Atualização" };
            writer.writeNext(header);
    
            // adicionando dados ao csv
            // TODO: Descobrir como editar dados e adicionar dados sem reescrever todo o arquivo
            String[] data1 = { nome, peso, altura, nivelatv, dtf.format(horaAgora) };
            writer.writeNext(data1);
//            String[] data2 = { "Suraj", "10", "630" };
//            writer.writeNext(data2);
    
            // closing writer connection
            writer.close();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
