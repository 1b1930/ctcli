package com.anhanguera.ctcli;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.anhanguera.ctcli.arquivo.OperadorArquivos;

public class Diario {

    public static final OperadorArquivos arquivoOps = new OperadorArquivos();

    String usr;
    String nomeCsv;

    public Diario(String usr) {
        this.usr = usr;
        nomeCsv = Main.CSVLOGDIR+usr+".csv";

        if(!arquivoOps.csvExiste(nomeCsv)) {
            arquivoOps.criarCSVeMontarCabecalho(nomeCsv);
        }

        if(arquivoOps.csvExiste(nomeCsv) && arquivoOps.cabecalhoEstaEmBranco(nomeCsv)) {
            arquivoOps.montarCabecalhoDiario(nomeCsv, usr);
        }


    }
    
    public boolean adicionarAlimentoAoDiario(String[] dadosAlimento) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");  
        LocalDateTime now = LocalDateTime.now();  
        String data = dtf.format(now).toString();


        String[] fileira = { dadosAlimento[0], dadosAlimento[1], data, dadosAlimento[2] };
        arquivoOps.acrescentarAoCSV(nomeCsv, fileira);
        return true;

    }

    public boolean removerAlimento(String nome) {
        List<String> b = arquivoOps.listaCSVRemoverHeader(arquivoOps.lerDadosCSV(nomeCsv));
        for(int i=0;i<b.size();i++) {
            if(b.get(i).contains(nome)) {
                // o +1 é porque o substituirFila() lê todo o arquivo, incluindo o cabeçalho
                // então é necessário pular o cabeçalho, por isso o +1
                arquivoOps.substituirFila(nomeCsv, i+1);
                // System.out.println("werks?");
                return true;
            }
        }
    return false;
    }

    public List<String> getDiario() {
        List<String> lista = arquivoOps.listaCSVRemoverHeader(arquivoOps.lerDadosCSV(nomeCsv));
        return lista;

    }

    public List<String> getDiario(LocalDate data) {
        List<String> lista = arquivoOps.listaCSVRemoverHeader(arquivoOps.lerDadosCSV(nomeCsv));
        List<String> listaFiltrada = new ArrayList<String>();
        String alimento;
        String[] alimentoSplit;
        LocalDate dataAlimento;
        LocalDateTime dataHoraAlimento;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");  


        for(int i=0;i<lista.size();i++) {
            alimento = lista.get(i).replaceAll("[\\[\\]]", "").trim();
            //System.out.println("DEBUGUGUG"+lista.get(i));
            alimentoSplit = alimento.split(",");
            for(int j=0;j<alimentoSplit.length;j++) {
                if(j==2) {
                    dataHoraAlimento = LocalDateTime.parse(alimentoSplit[j].trim(), dtf);
                    dataAlimento = dataHoraAlimento.toLocalDate();
                    if(dataAlimento.isEqual(data)) {
                        // System.out.println("2DEBUGUGUG"+lista.get(i));
                        listaFiltrada.add(lista.get(i));
                    }

                }

            }

        }
        return listaFiltrada;
    }

    public boolean deletarDiario() {
        if(arquivoOps.deletarArquivo(nomeCsv)) {
            return true;
        } else {
            return false;
        }


    }

    public List<String> getDiarioEntreDias(LocalDate dataInicio, LocalDate dataFim) {

        int inicioDia = dataInicio.getDayOfYear();
        int fimDia = dataFim.getDayOfYear();

        if(inicioDia > fimDia) {
            System.exit(0);
        }

        List<String> lista = new ArrayList<String>();
        List<String> listaFiltrada = new ArrayList<String>();
        String[] alimentoArr;
        String alimentoStr;

        lista.addAll(getDiario());

        LocalDateTime dataHoraDiario;
        LocalDate dataDiario;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");  

        for(int i=0;i<lista.size();i++) {
            alimentoStr = lista.get(i).replaceAll("[\\[\\]]", "");
            alimentoArr = alimentoStr.split(",");
            // System.out.println(alimentoArr.length);
            dataHoraDiario = LocalDateTime.parse(alimentoArr[2].trim(), dtf);
            // System.out.println(dataHoraDiario);
            // dSystem.exit(0);
            dataDiario = dataHoraDiario.toLocalDate();
            int diarioDia = dataDiario.getDayOfYear();
            for(int j=0;j<alimentoArr.length;j++) {

                if(diarioDia >= inicioDia && diarioDia <= fimDia ) {
                    listaFiltrada.add(lista.get(i));

                    // System.exit(0);
                }

            }
            //System.out.println();
            
        }
        return listaFiltrada;
        // System.out.println();

    }

    public double getKcal(LocalDate data) {
        List<String> lista = new ArrayList<String>();
        String elementoDiario;
        String[] elementoDSplit;
        lista.addAll(getDiario(data));
        double kcal = 0.0;
        for(int i=0;i<lista.size();i++) {
            elementoDiario = lista.get(i).replaceAll("[\\[\\]]", "");
            elementoDSplit = elementoDiario.split(",");
            for(int j=0;j<elementoDSplit.length;j++) {
                if(j == 2) {
                    kcal += Double.parseDouble(elementoDSplit[1]);
                }
            }
        }
        return kcal;

    }

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

        for(int i=0;i<lista.size();i++) {
            elementoDiario = lista.get(i).replaceAll("[\\[\\]]", "");
            elementoDSplit = elementoDiario.split(",");
            for(int j=0;j<elementoDSplit.length;j++) {
                dataHoraDiario = LocalDateTime.parse(elementoDSplit[2],dtf);
                dataDiario = dataHoraDiario.toLocalDate();
                int diaAno = dataDiario.getDayOfYear();
                if(diaAno >= inicioDia && diaAno <= fimDia) {
                    kcal += Double.parseDouble(elementoDSplit[1]);
                }

            }

        }
        return String.format("%.0f",kcal);

    }

    public boolean verificarTamanho() {
        System.out.println(arquivoOps.tamanhoArquivo(nomeCsv));
        return true;
    }


    
}
