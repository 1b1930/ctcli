package com.anhanguera.ctcli;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.anhanguera.ctcli.arquivo.OperadorArquivos;
import com.anhanguera.ctcli.arquivo.ArquivoConfig;
import com.anhanguera.ctcli.terminal.menu.Menu;

public class Main {
    public static final String VERSAO = "0.3";

    // constantes com os caminhos para os arquivos csv
    public static final String CSVBASEDIR = "dados";
    public static final String CSVLOGDIR = "dados/alimento_log/";
    public static final String CSVUSUARIO = "dados/DadosUsuario.csv";
    public static final String CTCLICONFIG = "dados/ctcli.config";

    public static int PRIMEIRAEXEC = 0;

    public static final ArquivoConfig ctcliConfig = new ArquivoConfig(CTCLICONFIG);
    
    public static void main(String[] args) throws FileNotFoundException, IOException {

        System.out.println();

        //Usuario usr = new Usuario();
        // usr.csvPessoalExiste("colette");

        // aq.criarCSVeMontarCabecalho(Main.CSVLOGDIR, "daniel.csv");
        if(init()) {
            Menu intf = new Menu();
            // System.out.println(PRIMEIRAEXEC);
            intf.mostrar();

        } else {
            System.out.println("R.I.P");
        }
        
        // 
        
    }

    public static boolean init() {
        OperadorArquivos aqv = new OperadorArquivos();

        File logdir = new File(Main.CSVLOGDIR);

        // verifica se logdir existe, se não existir, tenta criar
        if(!(logdir.exists())) {
            // mkdirs() só retorna verdadeiro se todos os diretórios e subdiretórios foram criados
            if(logdir.mkdirs()) {
                System.out.println("DIR: Diretório 'dados' + subdiretórios criados.");
                PRIMEIRAEXEC++;
            } else {
                System.out.println("ERRO: Diretórios não foram criados!");
                return false;
            }

        }

        // se CSV não existe, tenta criar, se não conseguir criar, retorna falso
        if(!(aqv.csvExiste(Main.CSVUSUARIO))) {
            if(aqv.criarCSVeMontarCabecalho(Main.CSVUSUARIO)) {
                System.out.println("CSV: DadosUsuario.csv não existia e foi criado.");
                PRIMEIRAEXEC++;

            } else {
                System.out.println("CSV: DadosUsuario.csv não foi criado.");
                return false;
            }
    
        }

        if(!(ctcliConfig.configExiste())) {
            if(ctcliConfig.criarConfig()) {
                System.out.println("CONFIG: Arquivo criado. "+"("+ctcliConfig.configArq+")");
                PRIMEIRAEXEC++;
            } else {
                System.out.println("CONFIG: Arquivo não foi criado.");
                return false;
            }
        }
        // só retorna verdadeiro se tudo foi executado sem erro
        return true;
    }

}