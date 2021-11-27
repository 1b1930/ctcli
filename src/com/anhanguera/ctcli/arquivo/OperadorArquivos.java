package com.anhanguera.ctcli.arquivo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.anhanguera.ctcli.Diario;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import com.anhanguera.ctcli.terminal.util.UtilidadesCLI;

// Essa classe contém todos os métodos que fazem operações com Arquivos
public class OperadorArquivos {

    String arquivo;

    public OperadorArquivos(String arquivo) {
        this.arquivo = arquivo;

    }

    // Lê os dados do Arquivo csv especificado como parâmetro
    public List<List<String>> lerDadosCSV() {

        // Cria uma lista vazia que ira armazenar todos os dados do Arquivo csv
        List<List<String>> records = new ArrayList<List<String>>();
        // Cria uma lista vazia que ira armazenar tudo menos o cabeçalho

        try {
            // Criando objeto para ler o Arquivo CSV
            CSVReader csvReader = new CSVReader(new FileReader(arquivo));
            String[] val = null;
            // Enquanto uma linha nao for nula, leia e adicione ela a lista
            while ((val = csvReader.readNext()) != null) {
                records.add(Arrays.asList(val));
            }

            return records;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return records;
    }

    // Só executa se o Arquivo CSV nao existe
    // Cria um CSV sem nenhum dado exceto o cabeçalho
    // Qual CSV ira criar e qual cabeçalho ira usar depende no parâmetro passado
    // Existem duas possibilidades: CSV c/ dados do usuario e CSV c/ os alimentos
    // TODO: usar método escreverAoCSV() ao invés de fazer toda essa coisa aí em
    // baixo
    public boolean criarCSVeMontarCabecalho() {

        // Cria objeto da classe File usando como parâmetro o caminho do Arquivo csv
        File file = new File(arquivo);
        if (file.length() != 0) {
            System.out.println("Arquivo nao esta vazio, abortando...");
            return false;
        } else {
            try {
                // Cria objeto da classe FileWriter com file como parâmetro
                FileWriter outputfile = new FileWriter(file);

                // Cria objeto da classe CSVWriter com objeto da classe FileWriter como
                // parâmetro
                CSVWriter writer = new CSVWriter(outputfile);

                // escreve o cabeçalho do usuario no Arquivo
                if (arquivo.contains("Usuario")) {

                    String[] header = { "Nome", "Peso", "Altura", "Idade", "Sexo", "Nivel de Atividade", "TDEE",
                            "Ultima Atualizacao" };
                    writer.writeNext(header);

                    // se caminho do Arquivo nao contém a palavra Usuario, retornar false
                } else {
                    writer.close();
                    return false;

                }
                // fechando o writer
                writer.close();
                return true;

                // se dar IOException, retornar false
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    // Mesma coisa do de cima, mas pro diario contendo os alimentos consumidos pelo
    // usuario.
    // Ao invés de ter como parâmetro o caminho relativo do Arquivo DadosUsuario,
    // ele usa o caminho do diretório
    // do diario.
    public boolean criarCSVeMontarCabecalho(String uNome) {
        Diario d = new Diario(uNome);
        if (d.diarioExiste()) {
            return false;
        } else {
            // String arquivo = Main.CSVLOGDIR + uNome + ".csv";
            String[] header = { "Nome", "Kcal", "Data da Adicao", "Notas" };
            if (escreverAoCSV(header)) {
                return true;

            } else {
                return false;
            }

        }

    }

    // tenta escrever o cabeçalho, sem criar um Arquivo csv.
    public boolean escreverCabecalhoDiario(String uNome) {
        Diario d = new Diario(uNome);
        String[] header = { "Nome", "Kcal", "Data da Adicao", "Notas" };

        if (d.diarioExiste() && d.cabecalhoEstaEmBranco()) {
            if (escreverAoCSV(header)) {
                return true;

            } else {
                return false;
            }
        }

        return false;

    }

    // verifica se o cabeçalho esta em branco, só
    public boolean cabecalhoEstaEmBranco() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(arquivo));
            try {
                if (UtilidadesCLI.isBlankString(br.readLine())) {
                    br.close();
                    return true;
                } else {
                    br.close();
                    return false;
                }

            } catch (NullPointerException e) {
                br.close();
                return true;
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        return false;

    }

    // Acrescenta dados ao final do Arquivo csv
    public boolean acrescentarAoCSV(String[] fileira) {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(arquivo, true));
            writer.writeNext(fileira);
            writer.close();
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Deleta tudo, e escreve um String[] array ao CSV
    // array pode ser nulo pra limpar o Arquivo completamente
    public boolean escreverAoCSV(String[] fileira) {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(arquivo, false));
            writer.writeNext(fileira);
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Remove o cabeçalho da lista CSV lida por lerDadosCSV();
    public List<String> listaCSVRemoverHeader(List<List<String>> listaHeader) {
        List<String> listaNoHeader = new ArrayList<String>();

        for (int i = 1; i < listaHeader.size(); i++) {
            listaNoHeader.add(listaHeader.get(i).toString());
        }

        return listaNoHeader;
    }

    // Substitui uma linha (fila) no Arquivo CSV por outra.
    public void substituirFila(int numFila, String[] novaFila) {

        String arquivoRem = arquivo;
        // Index de numFila começa com 0, nao 1.

        try {
            CSVReader reader2 = new CSVReader(new FileReader(arquivoRem));
            // Lê todos os elementos e joga eles numa lista
            List<String[]> allElements = reader2.readAll();
            // Remove o elemento na linha de número numFila
            allElements.remove(numFila);
            // Adiciona os novos dados (novaFila) no lugar do objeto removido
            allElements.add(numFila, novaFila);
            // Cria objeto da classe FileWriter para reescrever todo o Arquivo, agora sem a
            // linha
            FileWriter sw = new FileWriter(arquivoRem);
            // Nova instância de CSVWriter, que ira escrever os dados no Arquivo
            CSVWriter writer = new CSVWriter(sw);
            writer.writeAll(allElements);
            writer.close();
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }

    }

    // Método sobrecarregado que faz a mesma coisa desse de cima, porém nao adiciona
    // nenhum elemento
    // Só remove.
    public void substituirFila(int numFila) {
        // Caminho do Arquivo
        String arquivoRem = arquivo;
        // Index de numFila começa com 0, nao 1.

        try {
            CSVReader reader2 = new CSVReader(new FileReader(arquivoRem));
            // Lê todos os elementos e joga eles numa lista
            List<String[]> allElements = reader2.readAll();
            // Remove o elemento na linha de número numFila
            allElements.remove(numFila);
            // Cria objeto da classe FileWriter para reescrever todo o Arquivo, agora sem a
            // linha
            FileWriter sw = new FileWriter(arquivoRem);
            // Nova instância de CSVWriter, que ira escrever os dados no Arquivo
            CSVWriter writer = new CSVWriter(sw);
            writer.writeAll(allElements);
            writer.close();
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }

    }

    // cria um Arquivo se nao existe.
    public boolean criarArquivo() {
        try {
            File arquivouiv = new File(arquivo);
            if (arquivouiv.createNewFile()) {
                // System.out.println("Arquivo de configuraçao criado: " + arquivo.getName());
                return true;
            } else {
                // System.out.println("Arquivo ja existe.");
                return false;

            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    // lê todas as linhas do Arquivo para uma Lista<String> e retorna ela
    public List<String> lerArquivo() {
        List<String> resultado = new ArrayList<>();
        try (Stream<String> lines = Files.lines(Paths.get(arquivo))) {
            resultado = lines.collect(Collectors.toList());
            return resultado;
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }

        return resultado;

    }

    // substituir uma linha que tenha $match no Arquivo config, pela linha $subs
    // TODO: Nao é genérico, mudar pra ArquivoConfig!


    // acrescenta $asc ao final do Arquivo, nao sobrescreve nada
    public boolean acrescentarAoArquivo(String asc) {
        try {
            String str = asc;
            String fileName = arquivo;
            // cria BW com objeto FileWriter em modo append (acrescentar)
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
            // acrescenta a string + uma nova linha
            writer.append(str + "\n");
            // fecha o writer e retorna true
            writer.close();
            return true;

            // se ocorreu IOException, retorna falso
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    // escreve ao Arquivo, deletando tudo
    public boolean escreverNoArquivo() {
        try {
            String str = "";
            BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo));
            writer.write(str);

            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean deletarArquivo() {
        File f = new File(arquivo);
        if (f.delete()) {
            return true;
        } else {
            return false;
        }

    }

    public boolean arquivoExiste() {
        if (new File(arquivo).exists()) {
            return true;
        }
        return false;

    }

    public long tamanhoArquivo() {

        // cria objeto Arquivo
        File a = new File(arquivo);

        // armazena o tamanho (em bytes)
        long tamanho = a.length();

        // retorna tamanho em kbytes
        return tamanho / 1024;
    }

}
