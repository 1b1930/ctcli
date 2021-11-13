
import java.io.FileNotFoundException;
public class Main {
    // constantes com os caminhos para os arquivos csv
    // TODO: botar os CSVs dentro da pasta do programa

    public static final String CSVUSUARIO = "/mnt/hdd/code/DadosUsuario.csv";
    public static final String CSVALIMENTOS = "/mnt/hdd/code/DadosAlimentos.csv";
    public static void main(String[] args) throws FileNotFoundException {

        
        InterfaceCLI intf = new InterfaceCLI();
        intf.mostrar();
        
    }
}