
import java.io.FileNotFoundException;
public class Main {
    public static final String CSVUSUARIO = "/mnt/hdd/code/DadosUsuario.csv";
    public static final String CSVALIMENTOS = "/mnt/hdd/code/DadosAlimentos.csv";
    public static void main(String[] args) throws FileNotFoundException {
        // TODO: Criar um .csv pessoal para cada usuário com os alimentos consumidos? Nem sei como faz isso

        // Cria instância da classe ArquivoOps
        ArquivoOps arquivoOps = new ArquivoOps();
        String[] fileira1 = {"TEST7", "50", "51", "1"};

//        arquivoOps.lerCsv(csv);
        arquivoOps.escreverDadosLinhaPorLinha(CSVUSUARIO, "Daniel", "190", "170", "1" );;
        arquivoOps.lerDadosLinhaPorLinha("/mnt/hdd/code/DadosUsuario.csv");
        arquivoOps.acrescentarAoCSV(CSVUSUARIO, fileira1);


        ArquivoOps.checarPrimeiraExecucao();

        InterfaceCLI intf = new InterfaceCLI();
        intf.mostrar();
        
    }
}