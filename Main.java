
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    // constantes com os caminhos para os arquivos csv
    // TODO: botar os CSVs dentro da pasta do programa

    public static final String CSVUSUARIO = "dados/DadosUsuario.csv";
    public static final String CSVALIMENTOS = "dados/DadosAlimentos.csv";
    public static final String CSVBASEDIR = "dados";
    public static final String CSVLOGDIR = "dados/alimento_log";
    public static void main(String[] args) throws FileNotFoundException, IOException {

        ArquivoOps aq = new ArquivoOps();
        aq.checarPrimeiraExecucao();



        
        //InterfaceCLI intf = new InterfaceCLI();
        // intf.mostrar();
        
    }
}