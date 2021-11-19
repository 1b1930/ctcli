import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static final String VERSION = "0.02";
    // constantes com os caminhos para os arquivos csv

    public static final String CSVUSUARIO = "dados/DadosUsuario.csv";
    public static final String CSVALIMENTOS = "dados/DadosAlimentos.csv";
    public static final String CSVBASEDIR = "dados";
    public static final String CSVLOGDIR = "dados/alimento_log/";
    
    public static void main(String[] args) throws FileNotFoundException, IOException {

        //Usuario usr = new Usuario();
        // usr.csvPessoalExiste("colette");

        ArquivoOps aq = new ArquivoOps();

        // aq.criarCSVeMontarCabecalho(Main.CSVLOGDIR, "daniel.csv");
        if(aq.init()) {
            InterfaceCLI intf = new InterfaceCLI();
            intf.mostrar();

        } else {
            System.out.println("R.I.P");
        }
        
        // 
        
    }
}