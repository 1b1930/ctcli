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
    // TODO: usar método escreverAoCSV() ao invés de fazer toda essa porra ai em baixo
    boolean criarCSVeMontarCabecalho(String caminhoArq) {

        // Cria objeto da classe File usando como parâmetro o caminho do arquivo csv
        File file = new File(caminhoArq);
        if (file.length() != 0) {System.out.println("Arquivo não está vazio, abortando..."); return false;}
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
                    String[] header = { "Nome", "Peso", "Altura", "Idade", "Nível de Atividade", "Última Atualização" };
                    writer.writeNext(header);
                    // TODO: DEBUG print, remover antes de enviar o código
                    System.out.println("header criado");
                }
                // fechando o writer
                writer.close();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    // Mesma coisa do de cima, mas pro CSV pessoal contendo os alimentos consumidos pelo usuário
    boolean criarCSVeMontarCabecalho(String caminhoDir, String uNome) {
        Usuario u = new Usuario();
        u.csvPessoalExiste(uNome);
        if(u.csvPessoalExiste(uNome)) {
           System.out.println("Arquivo CSV pessoal do usuário já existe. Abortando...");
           return false;
        } else {
            String arq = caminhoDir+uNome+".csv";
            String[] header = {"Nome", "KCAL", "Data da Adição", "Notas"};
            if(escreverAoCSV(arq, header)) {
                return true;

            } else {
                return false;
            }

            // String arq = caminhoDir+uNome;
            // File file = new File(arq);
            // if (file.length() != 0) {System.out.println("Arquivo não está vazio, abortando..."); System.exit(0);}
            // else {
            //     try {
            //         // Cria objeto da classe FileWriter com file como parâmetro
            //         FileWriter outputfile = new FileWriter(file);
            
            //         // Cria objeto da classe CSVWriter com objeto da classe FileWriter como parâmetro
            //         CSVWriter writer = new CSVWriter(outputfile);
                    
            //         // Decide se tem que escrever o cabeçalho de alimentos ou do usuári
            //         String[] header = {"Nome", "KCAL", "Data da Adição", "Notas", "Usuário que Consumiu"};
            //         writer.writeNext(header);
            //         System.out.println("header criado");
            //         // closing writer connection
            //         writer.close();
            //         return true;
            //     } catch (IOException e) {
            //         e.printStackTrace();
            //         return false;
            //     }
            // }

        }

        // Cria objeto da classe File usando como parâmetro o caminho do arquivo csv

    }

    boolean csvExiste(String caminhoArq) {
        if(new File(caminhoArq).exists()) {
            return true;
        } else {return false;}

    }

    // verifica várias coisas antes de iniciar o programa
    boolean init() {

        File logdir = new File(Main.CSVLOGDIR);

        // verifica se logdir existe, se não existir, tenta criar
        if(!(logdir.exists())) {
            // mkdirs() só retorna verdadeiro se todos os diretórios e subdiretórios foram criados
            if(logdir.mkdirs()) {
                System.out.println("DIR: Diretório 'dados' + subdiretórios criados.");
            } else {
                System.out.println("ERRO: Diretórios não foram criados!");
                return false;
            }

        }

        // se CSV não existe, tenta criar, se não conseguir criar, retorna falso
        if(!(csvExiste(Main.CSVALIMENTOS))) {
            if(criarCSVeMontarCabecalho(Main.CSVALIMENTOS)) {
                System.out.println("CSV: DadosAlimentos.csv não existia e foi criado.");

            } else {
                System.out.println("CSV: DadosAlimentos.csv não foi criado.");
                return false;
            }
        } 

        // se CSV não existe, tenta criar, se não conseguir criar, retorna falso
        if(!(csvExiste(Main.CSVUSUARIO))) {
            if(criarCSVeMontarCabecalho(Main.CSVUSUARIO)) {
                System.out.println("CSV: DadosUsuario.csv não existia e foi criado.");

            } else {
                System.out.println("CSV: DadosUsuario.csv não foi criado.");
                return false;
            }
    
        }

        // só retorna verdadeiro se tudo foi executado sem erro
        return true;
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
    boolean escreverAoCSV(String arq, String[] fileira) {
        try {
            CSVWriter writer = new CSVWriter (new FileWriter(arq, false));
            writer.writeNext(fileira);
            writer.close();
            return true;
        } catch (IOException e) {e.printStackTrace(); return false;}
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
