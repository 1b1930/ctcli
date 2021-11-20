import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Diario extends Alimento {

    public static final ArquivoOps arquivoOps = new ArquivoOps();

    String usr;
    String nomeCsv;

    Diario(String usr) {
        this.usr = usr;
        nomeCsv = Main.CSVLOGDIR+usr+".csv";


    }
    
    boolean adicionarAlimentoAoDiario(String[] dadosAlimento) {

        if(!(alimentoExiste(dadosAlimento[0]))) {
            // System.out.println("Alimento existe. Abortando...");
            return false;
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");  
        LocalDateTime now = LocalDateTime.now();  
        data = dtf.format(now).toString();
        // System.out.println(data);  
        String[] fileira = { dadosAlimento[0], dadosAlimento[1], data, dadosAlimento[2] };
        arquivoOps.acrescentarAoCSV(nomeCsv, fileira);
        return true;

    }

    boolean removerAlimento(String nome) {
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

    boolean deletarDiario() {
        if(arquivoOps.deletarArquivo(nomeCsv)) {
            return true;
        } else {
            return false;
        }


    }
    
}
