package com.anhanguera.ctcli.arquivo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.anhanguera.ctcli.Main;
import com.anhanguera.ctcli.Usuario;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import com.anhanguera.ctcli.terminal.util.UtilidadesCLI;

// Essa classe contém todos os métodos que fazem operações com arquivos
public class OperadorArquivos {

    // Lê os dados do arquivo csv especificado como parâmetro
    public List<List<String>> lerDadosCSV(String arq) {

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
    // TODO: usar método escreverAoCSV() ao invés de fazer toda essa coisa aí em
    // baixo
    public boolean criarCSVeMontarCabecalho(String caminhoArq) {

        // Cria objeto da classe File usando como parâmetro o caminho do arquivo csv
        File file = new File(caminhoArq);
        if (file.length() != 0) {
            System.out.println("Arquivo não está vazio, abortando...");
            return false;
        } else {
            try {
                // Cria objeto da classe FileWriter com file como parâmetro
                FileWriter outputfile = new FileWriter(file);

                // Cria objeto da classe CSVWriter com objeto da classe FileWriter como
                // parâmetro
                CSVWriter writer = new CSVWriter(outputfile);

                // escreve o cabeçalho do usuário no arquivo
                if (caminhoArq.contains("Usuario")) {

                    String[] header = { "Nome", "Peso", "Altura", "Idade", "Sexo", "Nível de Atividade", "TDEE",
                            "Última Atualização" };
                    writer.writeNext(header);

                    // se caminho do arquivo não contém a palavra Usuario, retornar false
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

    // Mesma coisa do de cima, mas pro diário contendo os alimentos consumidos pelo
    // usuário.
    // Ao invés de ter como parâmetro o caminho relativo do arquivo DadosUsuario,
    // ele usa o caminho do diretório
    // do diário.
    public boolean criarCSVeMontarCabecalho(String caminhoDir, String uNome) {
        Usuario u = new Usuario(uNome);
        if (u.csvPessoalExiste(uNome)) {
            return false;
        } else {
            String arq = caminhoDir + uNome + ".csv";
            String[] header = { "Nome", "Kcal", "Data da Adição", "Notas" };
            if (escreverAoCSV(arq, header)) {
                return true;

            } else {
                return false;
            }

        }

    }

    // tenta escrever o cabeçalho, sem criar um arquivo csv.
    public boolean montarCabecalhoDiario(String caminhoArq, String uNome) {
        Usuario u = new Usuario(uNome);
        if (u.csvPessoalExiste(uNome)) {
            String[] header = { "Nome", "Kcal", "Data da Adição", "Notas" };

            if (cabecalhoEstaEmBranco(caminhoArq)) {
                if (escreverAoCSV(caminhoArq, header)) {
                    return true;

                } else {
                    return false;
                }
            }

            return false;

        }
        return false;

    }

    // verifica se o cabeçalho está em branco, só
    public boolean cabecalhoEstaEmBranco(String caminhoArq) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(caminhoArq));
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

    public boolean csvExiste(String caminhoArq) {
        if (new File(caminhoArq).exists()) {
            return true;
        } else {
            return false;
        }

    }

    // Acrescenta dados ao final do arquivo csv
    public boolean acrescentarAoCSV(String arq, String[] fileira) {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(arq, true));
            writer.writeNext(fileira);
            writer.close();
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Deleta tudo, e escreve um String[] array ao CSV
    // array pode ser nulo pra limpar o arquivo completamente
    public boolean escreverAoCSV(String arq, String[] fileira) {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(arq, false));
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

    // Substitui uma linha (fila) no arquivo CSV por outra.
    public void substituirFila(String arq, int numFila, String[] novaFila) {

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
            // Cria objeto da classe FileWriter para reescrever todo o arquivo, agora sem a
            // linha
            FileWriter sw = new FileWriter(arqRem);
            // Nova instância de CSVWriter, que irá escrever os dados no arquivo
            CSVWriter writer = new CSVWriter(sw);
            writer.writeAll(allElements);
            writer.close();
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }

    }

    // Método sobrecarregado que faz a mesma coisa desse de cima, porém não adiciona
    // nenhum elemento
    // Só remove.
    public void substituirFila(String arq, int numFila) {
        // Caminho do arquivo
        String arqRem = arq;
        // Index de numFila começa com 0, não 1.

        try {
            CSVReader reader2 = new CSVReader(new FileReader(arqRem));
            // Lê todos os elementos e joga eles numa lista
            List<String[]> allElements = reader2.readAll();
            // Remove o elemento na linha de número numFila
            allElements.remove(numFila);
            // Cria objeto da classe FileWriter para reescrever todo o arquivo, agora sem a
            // linha
            FileWriter sw = new FileWriter(arqRem);
            // Nova instância de CSVWriter, que irá escrever os dados no arquivo
            CSVWriter writer = new CSVWriter(sw);
            writer.writeAll(allElements);
            writer.close();
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }

    }

    // cria um arquivo se não existe.
    public boolean criarArquivo(String arquivo) {
        try {
            File arq = new File(arquivo);
            if (arq.createNewFile()) {
                // System.out.println("Arquivo de configuração criado: " + arq.getName());
                return true;
            } else {
                // System.out.println("Arquivo já existe.");
                return false;

            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    // lê todas as linhas do arquivo para uma Lista<String> e retorna ela
    public List<String> lerArquivo(String arq) {
        List<String> resultado = new ArrayList<>();
        try (Stream<String> lines = Files.lines(Paths.get(arq))) {
            resultado = lines.collect(Collectors.toList());
            return resultado;
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }

        return resultado;

    }

    // substituir uma linha que tenha $match no arquivo config, pela linha $subs
    // TODO: Não é genérico
    public boolean substituirNoArquivoConfig(String arq, String match, String subs) {
        // lista com as linhas do arquivo
        List<String> lista = new ArrayList<String>();
        // objeto config
        ArquivoConfig ctcliConf = new ArquivoConfig(Main.CTCLICONFIG);
        // adiciona todas as linhas de config na lista
        lista.addAll(lerArquivo(ctcliConf.configArq));
        int cont = 0;
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).contains(match)) {
                lista.remove(i);
                lista.add(i, subs);
                cont++;
            }
        }
        // só continua se só achou uma instância de permalogin=
        if (cont == 1) {
            // deleta tudo do arquivo
            if (escreverAoArquivo(arq)) {
                for (int i = 0; i < lista.size(); i++) {
                    // escreve as linhas no arquivo denovo, se não conseguiu, retorna false
                    if (!(acrescentarAoArquivo(arq, lista.get(i).toString()))) {
                        return false;
                    }
                }
            } else {
                return false;
            }
            return true;
        } else {
            System.out.println("ERRO: Mais de um 'permalogin' no arquivo ctcli.config?");
        }
        return false;

    }

    // acrescenta $asc ao final do arquivo, não sobrescreve nada
    public boolean acrescentarAoArquivo(String arq, String asc) {
        try {
            String str = asc;
            String fileName = arq;
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

    // escreve ao arquivo, deletando tudo
    public boolean escreverAoArquivo(String arq) {
        try {
            String str = "";
            BufferedWriter writer = new BufferedWriter(new FileWriter(arq));
            writer.write(str);

            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean deletarArquivo(String arq) {
        File f = new File(arq);
        if (f.delete()) {
            return true;
        } else {
            return false;
        }

    }

    public boolean arquivoExiste(String arq) {
        if (new File(arq).exists()) {
            return true;
        }
        return false;

    }

    public long tamanhoArquivo(String arq) {

        // cria objeto arquivo
        File a = new File(arq);

        // armazena o tamanho (em bytes)
        long tamanho = a.length();

        // retorna tamanho em kbytes
        return tamanho / 1024;
    }

}
