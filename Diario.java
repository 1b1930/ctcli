import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Diario extends Alimento {

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
        System.out.println(data);  
        String[] fileira = { dadosAlimento[0], dadosAlimento[1], data, dadosAlimento[2] };
        arquivoOps.acrescentarAoCSV(nomeCsv, fileira);
        return true;

    }
    
}
