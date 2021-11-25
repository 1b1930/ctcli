package com.anhanguera.ctcli;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.anhanguera.ctcli.arquivo.OperadorArquivos;

public class Diario {

    public static final OperadorArquivos arquivoOps = new OperadorArquivos();
    public static final int TAMANHO_MAXIMO_KB = 500;
    public static final int TAMANHO_MAXIMO_LIST = 1000;

    // nome do usuário a qual o diário pertence
    String usr;
    // caminho do arquivo csv desse diário
    String nomeCsv;

    public Diario(String usr) {
        this.usr = usr;
        nomeCsv = Main.CSVLOGDIR + usr + ".csv";

        // ao criar o diário, verificar se csv existe, se não, tentar criar
        if (!arquivoOps.csvExiste(nomeCsv)) {
            arquivoOps.criarCSVeMontarCabecalho(nomeCsv);
        }

        if (arquivoOps.csvExiste(nomeCsv) && arquivoOps.cabecalhoEstaEmBranco(nomeCsv)) {
            arquivoOps.montarCabecalhoDiario(nomeCsv, usr);
        }

    }

    // adiciona um alimento ao diario, nunca sobrescreve
    public boolean adicionarAlimentoAoDiario(String[] dadosAlimento) {

        // formatador de data, basicamente o metodo como formatará uma data
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        // pega a data e hora atuais
        LocalDateTime now = LocalDateTime.now();
        // transforma essa data numa string
        String data = dtf.format(now).toString().replace(" ","-");

        // adiciona os elementos (+data) no CSV
        String[] fileira = { dadosAlimento[0], dadosAlimento[1], data, dadosAlimento[2] };
        arquivoOps.acrescentarAoCSV(nomeCsv, fileira);
        return true;

    }

    // remove um alimento do diário
    public boolean removerAlimento(String nome) {
        // lista que armazena o conteudo do diario
        List<String> b = getDiario();
        // itera pela lista
        for (int i = 0; i < b.size(); i++) {
            if (b.get(i).contains(nome)) {
                // o +1 é porque o substituirFila() lê todo o arquivo, incluindo o cabeçalho
                // então é necessário pular o cabeçalho, por isso o +1
                arquivoOps.substituirFila(nomeCsv, i + 1);
                // System.out.println("werks?");
                return true;
            }
        }
        return false;
    }

    // retorna todos os elementos do diário
    public List<String> getDiario() {
        List<String> lista = arquivoOps.listaCSVRemoverHeader(arquivoOps.lerDadosCSV(nomeCsv));
        return lista;

    }

    // retorna o conteúdo do diário que tem a mesma data que o parâmetro
    public List<String> getDiario(LocalDate data) {
        List<String> lista = arquivoOps.listaCSVRemoverHeader(arquivoOps.lerDadosCSV(nomeCsv));

        // lista que irá armazenar os alimentos com a mesma data dada como parametro
        List<String> listaFiltrada = new ArrayList<String>();

        String alimento;
        String[] alimentoSplit;
        LocalDate dataAlimento;
        LocalDateTime dataHoraAlimento;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");

        // itera pela lista
        for (int i = 0; i < lista.size(); i++) {
            // adiciona uma linha a string alimento
            alimento = lista.get(i).replaceAll("[\\[\\]]", "").trim();
            // quebra a string em varios pedaços, adiciona esses pedaços a alimentoSplit
            alimentoSplit = alimento.split(",");
            // itera por alimentoSplit
            for (int j = 0; j < alimentoSplit.length; j++) {
                if (j == 2) {
                    // pega a data e hora do alimento e extrai só a data dela
                    dataHoraAlimento = LocalDateTime.parse(alimentoSplit[j].trim().replace("-"," "), dtf);
                    dataAlimento = dataHoraAlimento.toLocalDate();
                    // se a data do alimento for igual a data dada como parametro, adicionar a lista
                    if (dataAlimento.isEqual(data)) {
                        listaFiltrada.add(lista.get(i));
                    }

                }

            }

        }
        return listaFiltrada;
    }

    // tenta deletar o diario, retorna true se conseguiu, se não, false
    public boolean deletarDiario() {
        if (arquivoOps.deletarArquivo(nomeCsv)) {
            return true;
        } else {
            return false;
        }

    }

    // retorna uma lista com os alimentos logados entre os dias dataInicio e dataFim
    public List<String> getDiarioEntreDias(LocalDate dataInicio, LocalDate dataFim) {

        // pega os dias do ano de dataInicio e dataFim
        int inicioDia = dataInicio.getDayOfYear();
        int fimDia = dataFim.getDayOfYear();

        // se inicio for maior que fim, retornar falso
        if (inicioDia > fimDia) {
            return null;
        }

        // cria a lista que armazenam o conteúdo do arquivo
        List<String> lista = getDiario();
        // cria lista que armazenará só os alimentos consumidos entre as duas datas
        List<String> listaFiltrada = new ArrayList<String>();
        // armazena uma linha quebrada por virgula
        String[] alimentoArr;
        // armazena uma linha do arquivo
        String alimentoStr;

        // armazena a data completa armazenada no diario
        LocalDateTime dataHoraDiario;
        // armazena só a data (sem hora) armazenada no diário
        LocalDate dataDiario;
        // formatador de datas, é o método usado pra formatar uma data
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");

        // itera pela lista
        for (int i = 0; i < lista.size(); i++) {

            // adiciona uma linha a string alimentoStr
            alimentoStr = lista.get(i).replaceAll("[\\[\\]]", "");
            // quebra essa linha na virgula, adiciona os pedaços em alimentoArr
            alimentoArr = alimentoStr.split(",");

            // joga a data do alimento na variavel dataHoraDiario
            dataHoraDiario = LocalDateTime.parse(alimentoArr[2].trim().replace("-"," "), dtf);

            // adiciona só a data de dataHoraDiario em dataDiario
            dataDiario = dataHoraDiario.toLocalDate();
            // pega o dia do ano de dataDiario
            int diarioDia = dataDiario.getDayOfYear();

            // se o dia do ano da data do diario estiver entre os dias do ano de inicio e
            // fim, adicionar à lista
            if (diarioDia >= inicioDia && diarioDia <= fimDia) {
                listaFiltrada.add(lista.get(i));

            }

        }
        return listaFiltrada;

    }

    // não é usado, pelo menos nessa versão, é suposto a retornar o total de
    // calorias
    // dos alimentos consumidos pelo usuario na data dada como parametro
    public double getKcal(LocalDate data) {
        List<String> lista = new ArrayList<String>();
        String elementoDiario;
        String[] elementoDSplit;
        lista.addAll(getDiario(data));
        double kcal = 0.0;
        for (int i = 0; i < lista.size(); i++) {
            elementoDiario = lista.get(i).replaceAll("[\\[\\]]", "");
            elementoDSplit = elementoDiario.split(",");
            for (int j = 0; j < elementoDSplit.length; j++) {
                if (j == 2) {
                    kcal += Double.parseDouble(elementoDSplit[1]);
                }
            }
        }
        return kcal;

    }

    // não é usado, pelo menos nessa versão, é suposto a retornar as calorias
    // consumidas pelo usuario entre as duas datas
    public String getKcal(LocalDate inicio, LocalDate fim) {
        List<String> lista = new ArrayList<String>();
        String elementoDiario;
        String[] elementoDSplit;
        int inicioDia = inicio.getDayOfYear();
        int fimDia = fim.getDayOfYear();
        LocalDateTime dataHoraDiario;
        LocalDate dataDiario;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");

        lista.addAll(getDiario());
        double kcal = 0.0;

        for (int i = 0; i < lista.size(); i++) {
            elementoDiario = lista.get(i).replaceAll("[\\[\\]]", "");
            elementoDSplit = elementoDiario.split(",");
            for (int j = 0; j < elementoDSplit.length; j++) {
                dataHoraDiario = LocalDateTime.parse(elementoDSplit[2], dtf);
                dataDiario = dataHoraDiario.toLocalDate();
                int diaAno = dataDiario.getDayOfYear();
                if (diaAno >= inicioDia && diaAno <= fimDia) {
                    kcal += Double.parseDouble(elementoDSplit[1]);
                }

            }

        }
        return String.format("%.0f", kcal);

    }

    // retorna o tamanho do arquivo diario em kbytes
    public boolean tamanhoFoiExcedido() {
        // se o tamanho do arquivo for
        if (arquivoOps.tamanhoArquivo(nomeCsv) < TAMANHO_MAXIMO_KB) {
            return false;
        }
        return true;
    }

    // método que garante que o arquivo CSV tem um cabeçalho, e é menor que 1000
    // linhas
    // se não tiver cabeçalho, sobrescreve a primeira linha do arquivo com o
    // cabeçalho
    // se for maior que 1000 linhas, começa a deletar dados do início do arquivo até
    // que o arquivo seja menor que 1000 linhas

    public void faxina() {
        if (!(arquivoOps.csvExiste(nomeCsv))) {
            arquivoOps.criarCSVeMontarCabecalho(Main.CSVLOGDIR, usr);

        }
        // lista que vai armazenar cada linha do diário
        List<String> lista = getDiario();

        try {
            // armazena o cabeçalho
            String cbc = "\"Nome\",\"Kcal\",\"Data da Adicao\",\"Notas\"";

            // bufferedreader que irá ser usado para ler a primeira linha do arquivo
            BufferedReader br = new BufferedReader(new FileReader(nomeCsv));

            // se a primeira linha do arquivo não é o cabeçalho
            if (!(br.readLine().trim().equals(cbc))) {
                // remover essa primeira linha
                lista.remove(0);
                // adicionar o cabeçalho no lugar dela
                lista.add(0, cbc);
                // fechar bufferedreader
                br.close();
            }

            // caso der IOException
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        // lista que vai armazenar uma versão filtrada da primeira lista
        // deletando os primeiros elementos até a lista ser menor que
        // $TAMANHO_MAXIMO_LIST linhas
        List<String> lista2 = new ArrayList<String>();

        // armazena uma linha do arquivo
        String el;
        // armazena a linha, quebrada nas virgulas, em um array
        String[] elArr;
        // System.out.println(lista.size());

        // se o arquivo excedeu TAMANHO_MAXIMO_LIST
        if (lista.size() > TAMANHO_MAXIMO_LIST) {
            // calcular diferença entre o tamanho da lista e o tamanho máximo (-2, por causa
            // do cabeçalho)
            int r = Math.abs(lista.size() - (TAMANHO_MAXIMO_LIST - 2));
            // System.out.println(r);
            // joga todos os elementos da lista entre a diferença e o tamanho da lista na
            // lista2
            lista2 = lista.subList(r, lista.size());

            // se a lista2 for menor que TAMANHO_MAXIMO_LIST
            if (lista2.size() < TAMANHO_MAXIMO_LIST) {
                // deleta o arquivo velho
                deletarDiario();
                // cria um novo arquivo com o cabeçalho
                arquivoOps.criarCSVeMontarCabecalho(Main.CSVLOGDIR, usr);
                // itera por lista2
                for (int i = 0; i < lista2.size(); i++) {
                    // armazena uma linha de lista2 em el
                    el = lista2.get(i).replaceAll("[\\[\\]]", "");
                    // quebra el em vários pedaços, quebrado na virgula
                    elArr = el.split(",");
                    // remove espaços desnecessários dos elementos de elArr
                    for (int j = 0; j < elArr.length; j++) {
                        elArr[j] = elArr[j].trim();
                    }
                    // acrescenta elArr (uma linha) ao CSV
                    arquivoOps.acrescentarAoCSV(nomeCsv, elArr);

                }
            }

        }

    }

}
