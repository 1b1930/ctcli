import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


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
            System.out.println();
            it++;
        }
    }
    catch (Exception e) {
        e.printStackTrace();
    }
}

    // TODO: retirar essa classe do modo estático
    List<String> lerDadosCSV(String arq) {

        List<List<String>> records = new ArrayList<List<String>>();
        List<String> recordsNoHeader = new ArrayList<String>();


        try {
            CSVReader csvReader = new CSVReader(new FileReader(arq));
            String[] values = null;
            while ((values = csvReader.readNext()) != null) {
                records.add(Arrays.asList(values));
            }
            // Método bonito pra printar todos os elementos de uma lista
            // records.forEach(System.out::println);

            // Método mais flexível que o outro acima
            // Pula o cabeçalho.
            for (int i=1; i<records.size(); i++) {
                System.out.println(records.get(i));
            }
            
            // Cria uma lista sem o cabeçalho
            for (int i=1; i<records.size(); i++) {
                recordsNoHeader.add(records.get(i).toString());
            }

            // Printa a lista que só tem os elementos (sem cabeçalho)
            for (int i=0; i<recordsNoHeader.size(); i++) {
                System.out.println(recordsNoHeader.get(i));
            }
            
            return recordsNoHeader;

        } catch (Exception e) {
            e.printStackTrace();
            }
        return recordsNoHeader;
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
    
            // closing writer connection
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }




    static boolean checarPrimeiraExecucao() {
        File arqTemp = new File(Main.CSVUSUARIO);
        boolean exists = arqTemp.exists();
        //System.out.println("file exists:" + exists);
        return !exists;
    }

    // Acrescenta dados ao final do arquivo csv
    void acrescentarAoCSV(String arq, String[] fileira) {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(arq, true));
            writer.writeNext(fileira);
            writer.close();

        } catch (IOException e) {e.printStackTrace();}
    }

}
