package com.anhanguera.ctcli.arquivo;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class ArquivoConfig {

    public static final OperadorArquivos aq = new OperadorArquivos();

    public String configArq;

    public ArquivoConfig(String configArq) {
        this.configArq = configArq;
    }

    // tenta criar config

    // verifica se config existe
    public boolean configExiste() {
        if(aq.arquivoExiste(configArq)) {
            return true;

        } else {return false;}
    }

    public boolean criarConfig() {
        if(aq.criarArquivo(configArq)) {
            if(popularConfig()) {
                return true;
            }
            return false;
        } else {
            return false;
        }
    }

        // popular ctcli.config com os parâmetros que serão usados pelo código
    public boolean popularConfig() {
        try {
            PrintWriter writer = new PrintWriter(configArq, "UTF-8");
            writer.println("permalogin=");
            // writer.println("The second line");
            writer.close();
            return true;

        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        }


    }

    // adiciona $usr depois de permalogin em ctcli.config
    public boolean addPermaLoginUsr(String usr) {
        if(configExiste()) {
            OperadorArquivos arquivoOps = new OperadorArquivos();
            String subs = "permalogin="+usr;
            if(arquivoOps.substituirNoArquivo(configArq, "permalogin", subs)) {
                return true;
            } else {
                return false;
            }
        }
        return false;

    }

    // retorna o que vem depois do = em permalogin= em ctcli.config como String, pode ser "" ou null
    public String getPermaLoginUsr() {
        List<String> lista = new ArrayList<>();
        OperadorArquivos arquivoOps = new OperadorArquivos();
        lista.addAll(arquivoOps.lerArquivo(configArq));
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
    
}
