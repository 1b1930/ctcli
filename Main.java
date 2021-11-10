import java.io.File;
public class Main {
    public static void main(String[] args) {
        System.out.println("fucktest");
        File csv = new File("/mnt/hdd/code/test1.csv");
        ArquivoOps.lerCsv(csv);
        
    }
}