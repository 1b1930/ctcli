import java.io.File;
import java.io.FileNotFoundException;
public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        // TODO: Criar um .csv pessoal para cada usuário com os alimentos consumidos? Nem sei como faz isso
        System.out.println("fucktest");
        File csv = new File("/mnt/hdd/code/test1.csv");

        // Cria instância da classe ArquivoOps
        ArquivoOps arquivoOps = new ArquivoOps();
        
        arquivoOps.lerCsv(csv);

        InterfaceCLI intf = new InterfaceCLI();
        intf.mostrar();


        
    }
}