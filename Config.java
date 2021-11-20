import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class Config {

    public static final String ARQCONFIG = Main.ARQUIVOCONFIG;
    public static final ArquivoOps aq = new ArquivoOps();

    static boolean criarConfig() {
        if(aq.criarArquivo(ARQCONFIG)) {
            if(popularConfig()) {
                return true;
            }
            return false;
        } else {
            return false;
        }
    }

    static boolean configExiste() {
        if(new File(ARQCONFIG).exists()) {
            return true;

        } else {return false;}
    }

    static boolean popularConfig() {
        try {
            PrintWriter writer = new PrintWriter(ARQCONFIG, "UTF-8");
            writer.println("permalogin=");
            // writer.println("The second line");
            writer.close();
            return true;

        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        }


    }

    // retorna o que vem depois do = em permalogin= em ctcli.config como String, pode ser "" ou null
    static String getPermaLoginUsr() {
        List<String> lista = new ArrayList<>();
        ArquivoOps arquivoOps = new ArquivoOps();
        lista.addAll(arquivoOps.lerArquivo(Config.ARQCONFIG));
        for (int i=0;i<lista.size();i++) {
            if(lista.get(i).contains("permalogin")) {
                // retorna uma substring com tudo o que vem depois de =
                // remove espaços em branco
                return(lista.get(i)
                .substring(lista.get(i)
                .lastIndexOf("=") + 1)
                .trim());
            }
        }
        return "";

        
    }

    // adiciona $usr depois de permalogin em ctcli.config
    static boolean addPermaLoginUsr(String usr) {
        if(configExiste()) {
            ArquivoOps arquivoOps = new ArquivoOps();
            String subs = "permalogin="+usr;
            if(arquivoOps.substituirNoArquivo(ARQCONFIG, "permalogin", subs)) {
                return true;
            } else {
                return false;
            }
        }

        return false;


    }
    
}
