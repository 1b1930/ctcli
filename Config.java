import java.io.File;

public class Config {

    public static final String ARQCONFIG = Main.ARQUIVOCONFIG;
    public static final ArquivoOps aq = new ArquivoOps();

    static boolean criarConfig() {
        if(aq.criarArquivo(ARQCONFIG)) {
            return true;
        } else {
            return false;
        }
    }

    static boolean configExiste() {
        if(new File(ARQCONFIG).exists()) {
            return true;

        } else {return false;}
    }
    
}
