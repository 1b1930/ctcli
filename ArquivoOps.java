import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


// Essa classe contém todos os métodos que fazem operações com arquivos
public class ArquivoOps {

    // TODO: Esse método deve ser movido pra Usuario.java e Alimento.java
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
        leitorCsv.close();
    }
    catch (Exception e) {
        e.printStackTrace();
    }
}

    // Lê os dados do arquivo csv especificado como parâmetro
    List<List<String>> lerDadosCSV(String arq) {
        
        // Cria uma lista vazia que irá armazenar todos os dados do arquivo csv
        List<List<String>> records = new ArrayList<List<String>>();
        // Cria uma lista vazia que irá armazenar tudo menos o cabeçalho

        try {
            // Criando objeto para ler o arquivo CSV
            CSVReader csvReader = new CSVReader(new FileReader(arq));
            String[] val = null;
            // Enquanto uma linha não for nula, leia e adicione ela a lista
            while ((val = csvReader.readNext()) != null) {
                records.add(Arrays.asList(val));
            }

            return records;

        } catch (Exception e) {
            e.printStackTrace();
            }
        return records;
}
    // Só executa se o arquivo CSV não existe
    // Cria um CSV sem nenhum dado exceto o cabeçalho
    // Qual CSV irá criar e qual cabeçalho irá usar depende no parâmetro passado
    // Existem duas possibilidades: CSV c/ dados do usuário e CSV c/ os alimentos
    // TODO: Esses comandos de manipular arquivos deviam estar em um try{}
    void criarCSVeMontarCabecalho(String caminhoArq) {

        // Cria objeto da classe File usando como parâmetro o caminho do arquivo csv
        File file = new File(caminhoArq);
        if (file.length() != 0) {System.out.println("Arquivo não está vazio, abortando..."); System.exit(0);}
        else {
            try {
                // Cria objeto da classe FileWriter com file como parâmetro
                FileWriter outputfile = new FileWriter(file);
        
                // Cria objeto da classe CSVWriter com objeto da classe FileWriter como parâmetro
                CSVWriter writer = new CSVWriter(outputfile);
                
                // Decide se tem que escrever o cabeçalho de alimentos ou do usuário
                if (caminhoArq.contains("Alimentos")) {
                    String[] header = {"Nome", "KCAL/100g", "Data da Adição"};
                    writer.writeNext(header);
                    // TODO: DEBUG print, remover antes de enviar o código
                    System.out.println("header criado");

                } else {
                    String[] header = { "Nome", "Peso", "Altura", "Nível de Atividade", "Última Atualização" };
                    writer.writeNext(header);
                    // TODO: DEBUG print, remover antes de enviar o código
                    System.out.println("header criado");
                }
                // fechando o writer
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Mesma coisa do de cima, mas pro CSV pessoal contendo os alimentos consumidos pelo usuário
    // TODO: Adicionar suporte pra esse método nos comandos limparcsv em InterfaceCLI
    void criarCSVeMontarCabecalho(String caminhoArq, String uNome) {

        // Cria objeto da classe File usando como parâmetro o caminho do arquivo csv
        File file = new File(caminhoArq);
        if (file.length() != 0) {System.out.println("Arquivo não está vazio, abortando..."); System.exit(0);}
        else {
            try {
                // Cria objeto da classe FileWriter com file como parâmetro
                FileWriter outputfile = new FileWriter(file);
        
                // Cria objeto da classe CSVWriter com objeto da classe FileWriter como parâmetro
                CSVWriter writer = new CSVWriter(outputfile);
                
                // Decide se tem que escrever o cabeçalho de alimentos ou do usuário
                if (caminhoArq.contains(uNome)) {
                    String[] header = {"Nome", "KCAL", "Data da Adição", "Notas", "Usuário que Consumiu"};
                    writer.writeNext(header);
                    System.out.println("header criado");
                }
                // closing writer connection
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Checa se o arquivo CSV existe ou não.
    void checarPrimeiraExecucao() {
        // essa condição dentro do if só retorna true se o diretório e subdiretórios foram criados
        if(new File(Main.CSVLOGDIR).mkdirs()) {
            System.out.println("Diretório 'dados' + subdiretórios criados.");
        }
        if(!(new File(Main.CSVALIMENTOS).exists())) {
            criarCSVeMontarCabecalho(Main.CSVALIMENTOS);
        } 
        if(!(new File(Main.CSVUSUARIO).exists())) {
            criarCSVeMontarCabecalho(Main.CSVUSUARIO);

        }

    }

    // Acrescenta dados ao final do arquivo csv
    void acrescentarAoCSV(String arq, String[] fileira) {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(arq, true));
            writer.writeNext(fileira);
            writer.close();

        } catch (IOException e) {e.printStackTrace();}
    }

    // Deleta tudo, e escreve um String[] array ao CSV
    // array pode ser nulo pra limpar o arquivo completamente
    void escreverAoCSV(String arq, String[] fileira) {
        try {
            CSVWriter writer = new CSVWriter (new FileWriter(arq, false));
            writer.writeNext(fileira);
            writer.close();
        } catch (IOException e) {e.printStackTrace();}
    }

    // Remove o cabeçalho da lista CSV lida por lerDadosCSV();
    List<String> listaCSVRemoverHeader(List<List<String>> listaHeader) {
        List<String> listaNoHeader = new ArrayList<String>();

        for (int i=1; i<listaHeader.size(); i++) {
            listaNoHeader.add(listaHeader.get(i).toString());
        }

        return listaNoHeader;
    }

    // Substitui uma linha (fila) no arquivo CSV por outra.
    void substituirFila(String arq, int numFila, String[] novaFila) {
        
        String arqRem = arq;
        // Index de numFila começa com 0, não 1.

        try {
            CSVReader reader2 = new CSVReader(new FileReader(arqRem));
            // Lê todos os elementos e joga eles numa lista
            List<String[]> allElements = reader2.readAll();
            // Remove o elemento na linha de número numFila
            allElements.remove(numFila);
            // Adiciona os novos dados (novaFila) no lugar do objeto removido
            allElements.add(numFila, novaFila);
            // Cria objeto da classe FileWriter para reescrever todo o arquivo, agora sem a linha
            FileWriter sw = new FileWriter(arqRem);
            // Nova instância de CSVWriter, que irá escrever os dados no arquivo
            CSVWriter writer = new CSVWriter(sw);
            writer.writeAll(allElements);
            writer.close();
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }

        
    }

    // Método sobrecarregado que faz a mesma coisa desse de cima, porém não adiciona nenhum elemento
    // Só remove.
    void substituirFila(String arq, int numFila) {
        // Caminho do arquivo
        String arqRem = arq;
        // Index de numFila começa com 0, não 1.

        try {
            CSVReader reader2 = new CSVReader(new FileReader(arqRem));
            // Lê todos os elementos e joga eles numa lista
            List<String[]> allElements = reader2.readAll();
            // Remove o elemento na linha de número numFila
            allElements.remove(numFila);
            // Cria objeto da classe FileWriter para reescrever todo o arquivo, agora sem a linha
            FileWriter sw = new FileWriter(arqRem);
            // Nova instância de CSVWriter, que irá escrever os dados no arquivo
            CSVWriter writer = new CSVWriter(sw);
            writer.writeAll(allElements);
            writer.close();
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }

    }



}
