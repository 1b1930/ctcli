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


    }
    
    public boolean adicionarAlimentoAoDiario(String[] dadosAlimento) {

        // if(!(alimentoExiste(dadosAlimento[0]))) {
        //     // System.out.println("Alimento existe. Abortando...");
        //     return false;
        // }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");  
        LocalDateTime now = LocalDateTime.now();  
        String data = dtf.format(now).toString();

        // Alimento a = new Alimento();
        // String[] csvA = a.getDadosAlimento(dadosAlimento[0]);

        // dadosAlimento[1] = gramas consumidas
        // csvA[1] = kcal/100g

        // System.out.println(csvA[1].trim()+dadosAlimento[1]);

        // Divide a quantidade de kcal em 100g de alimento por 100, pra obter a quantidade de kcal em 1g de alimento
        // em seguida multiplica esse novo valor por quantas gramas o usuário consumiu do alimento
        // double kcal = (Integer.parseInt(csvA[1].trim()) / 100.0) * Integer.parseInt(dadosAlimento[1]);
        // String kcald = String.format("%.1f", kcal);
        
        //System.out.println(String.format("%.1f", kcal));
        // System.out.println(data);  
        // salva os valores, incluindo as calorias consumidas calculadas acima, pro diário
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

    public List<String> getDadosAlimentosDiario() {
        List<String> lista = arquivoOps.listaCSVRemoverHeader(arquivoOps.lerDadosCSV(nomeCsv));
        return lista;

    }

    public List<String> getDadosAlimentosDiario(LocalDate data) {
        List<String> lista = arquivoOps.listaCSVRemoverHeader(arquivoOps.lerDadosCSV(nomeCsv));
        List<String> listaFiltrada = new ArrayList<String>();
        String alimento;
        String[] alimentoSplit;
        LocalDate dataAlimento;
        LocalDateTime dataHoraAlimento;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");  


        for(int i=0;i<lista.size();i++) {
            alimento = lista.get(i).replaceAll("[\\[\\]]", "").trim();
            alimentoSplit = alimento.split(",");
            for(int j=0;j<alimentoSplit.length;j++) {
                if(j==2) {
                    dataHoraAlimento = LocalDateTime.parse(alimentoSplit[j], dtf);
                    dataAlimento = dataHoraAlimento.toLocalDate();
                    if(dataAlimento.isEqual(data)) {
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
    
}
