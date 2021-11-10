import java.io.File;
import java.io.FileNotFoundException;
public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        // TODO: Criar um .csv pessoal para cada usuário com os alimentos consumidos? Nem sei como faz isso
        System.out.println("fucktest");
        File csv = new File("/mnt/hdd/code/DadosUsuario.csv");

        // Cria instância da classe ArquivoOps
        ArquivoOps arquivoOps = new ArquivoOps();

//        arquivoOps.lerCsv(csv);
        arquivoOps.lerDadosLinhaPorLinha("/mnt/hdd/code/DadosUsuario.csv");
        arquivoOps.escreverCsv();

        InterfaceCLI intf = new InterfaceCLI();
//        intf.mostrar();


        
    }
}