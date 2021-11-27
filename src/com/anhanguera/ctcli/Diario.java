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
import com.anhanguera.ctcli.terminal.util.Datas;
import com.anhanguera.ctcli.terminal.util.Listas;
import com.anhanguera.ctcli.terminal.util.UtilidadesCLI;

public class Diario {

    public final OperadorArquivos arqDiario;
    public static final int TAMANHO_MAXIMO_KB = 500;
    public static final int TAMANHO_MAXIMO_LIST = 1000;

    // nome do usuário a qual o diário pertence
    String usuario;
    // caminho do arquivo csv desse diário
    String nomeCsv;

    public Diario(String usuario) {
        this.usuario = usuario;
        nomeCsv = Main.CSVLOGDIR + usuario + ".csv";
        arqDiario = new OperadorArquivos(nomeCsv);

    }

    public boolean criarDiario() {
        Usuario u = new Usuario(usuario);
        if (u.existe()) {
            if (!(diarioExiste())) {
                if (arqDiario.criarCSVeMontarCabecalho(usuario)) {
                    // System.out.println("CSV criado");
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    public boolean diarioExiste() {
        List<String> arqs = new ArrayList<String>();
        arqs.addAll(UtilidadesCLI.getListaArq(Main.CSVLOGDIR));

        for (int i = 0; i < arqs.size(); i++) {
            // se a lista de arquivos contém o nome, retorna existe
            if (arqs.get(i).contains(usuario)) {
                return true;

            }
        }
        return false;
    }

    // retorna todos os elementos do diário
    public List<String> getDiario() {
        List<String> lista = arqDiario.listaCSVRemoverHeader(arqDiario.lerDadosCSV());
        return lista;

    }

    // retorna o conteúdo do diário que tem a mesma data que o parâmetro
    public List<String> getDiario(LocalDate data) {
        List<String> lista = arqDiario.listaCSVRemoverHeader(arqDiario.lerDadosCSV());

        // lista que irá armazenar os alimentos com a mesma data dada como parametro
        List<String> listaFiltrada = new ArrayList<String>();

        String[] alimentoSplit;
        LocalDate dataAlimento;

        // itera pela lista
        for (int i = 0; i < lista.size(); i++) {
            // adiciona uma linha a string alimento
            // quebra a string em varios pedaços, adiciona esses pedaços a alimentoSplit
            alimentoSplit = Listas.separarElementos(lista.get(i));
            // itera por alimentoSplit
            for (int j = 0; j < alimentoSplit.length; j++) {
                if (j == 2) {
                    // pega a data e hora do alimento e extrai só a data dela
                    dataAlimento = Datas.converterDiarioLDTparaLD(alimentoSplit[j]);
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
        if (arqDiario.deletarArquivo()) {
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
        // String alimentoStr;

        // armazena a data completa armazenada no diario
        // armazena só a data (sem hora) armazenada no diário
        LocalDate dataDiario;

        // itera pela lista
        for (int i = 0; i < lista.size(); i++) {

            // adiciona uma linha a string alimentoStr
            // quebra essa linha na virgula, adiciona os pedaços em alimentoArr
            alimentoArr = Listas.separarElementos(lista.get(i));

            // joga a data do alimento na variavel dataHoraDiario

            // adiciona só a data de dataHoraDiario em dataDiario
            dataDiario = Datas.converterDiarioLDTparaLD(alimentoArr[2]);
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
        // String elementoDiario;
        String[] elementoDSplit;
        lista.addAll(getDiario(data));
        double kcal = 0.0;
        for (int i = 0; i < lista.size(); i++) {
            elementoDSplit = Listas.separarElementos(lista.get(i));
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
    // TODO: usar método pra converter data do diário disponível na classe Datas em
    // util
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
        if (arqDiario.tamanhoArquivo() < TAMANHO_MAXIMO_KB) {
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
        if (!(arqDiario.arquivoExiste())) {
            arqDiario.criarCSVeMontarCabecalho(usuario);

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
                arqDiario.criarCSVeMontarCabecalho(usuario);
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
                    arqDiario.acrescentarAoCSV(elArr);

                }
            }

        }

    }

    public boolean cabecalhoEstaEmBranco() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(nomeCsv));
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
}
