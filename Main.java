import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static final String VERSION = "0.02";
    // constantes com os caminhos para os arquivos csv

    public static final String CSVBASEDIR = "dados";
    public static final String CSVLOGDIR = "dados/alimento_log/";

    public static final String CSVUSUARIO = "dados/DadosUsuario.csv";
    public static final String CSVALIMENTOS = "dados/DadosAlimentos.csv";

    public static final String ARQUIVOCONFIG = "dados/ctcli.config";

    public static int FIRSTL = 0;
    
    public static void main(String[] args) throws FileNotFoundException, IOException {

        System.out.println();

        //Usuario usr = new Usuario();
        // usr.csvPessoalExiste("colette");

        // aq.criarCSVeMontarCabecalho(Main.CSVLOGDIR, "daniel.csv");
        if(init()) {
            InterfaceCLI intf = new InterfaceCLI();
            System.out.println(FIRSTL);
            if(FIRSTL != 4) {
                intf.mostrar(false);
            } else {
                intf.mostrar(true);
            }

        } else {
            System.out.println("R.I.P");
        }
        
        // 
        
    }

    public static boolean init() {
        ArquivoOps aqv = new ArquivoOps();

        File logdir = new File(Main.CSVLOGDIR);

        // verifica se logdir existe, se não existir, tenta criar
        if(!(logdir.exists())) {
            // mkdirs() só retorna verdadeiro se todos os diretórios e subdiretórios foram criados
            if(logdir.mkdirs()) {
                System.out.println("DIR: Diretório 'dados' + subdiretórios criados.");
                FIRSTL++;
            } else {
                System.out.println("ERRO: Diretórios não foram criados!");
                return false;
            }

        }
        // se CSV não existe, tenta criar, se não conseguir criar, retorna falso
        if(!(aqv.csvExiste(Main.CSVALIMENTOS))) {
            if(aqv.criarCSVeMontarCabecalho(Main.CSVALIMENTOS)) {
                System.out.println("CSV: DadosAlimentos.csv não existia e foi criado.");
                FIRSTL++;

            } else {
                System.out.println("CSV: DadosAlimentos.csv não foi criado.");
                return false;
            }
        } 

        // se CSV não existe, tenta criar, se não conseguir criar, retorna falso
        if(!(aqv.csvExiste(Main.CSVUSUARIO))) {
            if(aqv.criarCSVeMontarCabecalho(Main.CSVUSUARIO)) {
                System.out.println("CSV: DadosUsuario.csv não existia e foi criado.");
                FIRSTL++;

            } else {
                System.out.println("CSV: DadosUsuario.csv não foi criado.");
                return false;
            }
    
        }

        if(!(Config.configExiste())) {
            if(Config.criarConfig()) {
                System.out.println("CONFIG: Arquivo criado. "+"("+Config.ARQCONFIG+")");
                FIRSTL++;
            } else {
                System.out.println("CONFIG: Arquivo não foi criado.");
                return false;
            }
        }
        // só retorna verdadeiro se tudo foi executado sem erro
        return true;
    }

}