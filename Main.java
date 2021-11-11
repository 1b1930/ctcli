
import java.io.FileNotFoundException;
public class Main {
    public static final String CSVUSUARIO = "/mnt/hdd/code/DadosUsuario.csv";
    public static final String CSVALIMENTOS = "/mnt/hdd/code/DadosAlimentos.csv";
    public static void main(String[] args) throws FileNotFoundException {


        // Cria instância da classe ArquivoOps
        //ArquivoOps arquivoOps = new ArquivoOps();
        // String[] fileira1 = {"TEST7", "50", "51", "1", "30/12/1990 55:33"};

//        arquivoOps.lerCsv(csv);
        //arquivoOps.criarCSVeMontarCabecalho(CSVUSUARIO);

        // arquivoOps.lerDadosLinhaPorLinha("/mnt/hdd/code/DadosUsuario.csv");
        //arquivoOps.acrescentarAoCSV(CSVUSUARIO, fileira1);
        //arquivoOps.lerDadosLinhaPorLinha(CSVUSUARIO);

        Usuario usuario = new Usuario("Daniel", "90", "10", "1");
        if (usuario.usuarioExiste("TEST7")) {
            System.out.println("funciona");
        } else {
            System.out.println("n funciona");
        }


        // ArquivoOps.checarPrimeiraExecucao();

        InterfaceCLI intf = new InterfaceCLI();
        intf.mostrar();
        
    }
}