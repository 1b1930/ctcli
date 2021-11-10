
import java.io.FileNotFoundException;
public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        // TODO: Criar um .csv pessoal para cada usuário com os alimentos consumidos? Nem sei como faz isso
        System.out.println("fucktest");
//        File csv = new File("/mnt/hdd/code/DadosUsuario.csv");
        final String CAMINHOARQUIVO = "/mnt/hdd/code/DadosUsuario.csv";

        // Cria instância da classe ArquivoOps
        ArquivoOps arquivoOps = new ArquivoOps();

//        arquivoOps.lerCsv(csv);
        arquivoOps.escreverDadosLinhaPorLinha(CAMINHOARQUIVO, "Daniel", "190", "170", "1" );;
        arquivoOps.lerDadosLinhaPorLinha("/mnt/hdd/code/DadosUsuario.csv");

//        InterfaceCLI intf = new InterfaceCLI();
//        intf.mostrar();


        
    }
}