package com.anhanguera.ctcli;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.anhanguera.ctcli.arquivo.OperadorArquivos;
import com.anhanguera.ctcli.arquivo.ArquivoConfig;
import com.anhanguera.ctcli.terminal.menu.Menu;
import com.anhanguera.ctcli.terminal.menu.mensagens.Erro;

public class Main {
    public static final String VERSAO = "0.9";

    // constantes com os caminhos para os arquivos csv
    public static final String CSVBASEDIR = "dados";
    public static final String CSVLOGDIR = "dados/alimento_log/";
    public static final String CSVUSUARIO = "dados/DadosUsuario.csv";
    public static final String CTCLICONFIG = "dados/ctcli.config";

    public static int PRIMEIRAEXEC = 0;

    public static final ArquivoConfig ctcliConfig = new ArquivoConfig(CTCLICONFIG);

    public static void main(String[] args) throws FileNotFoundException, IOException {

        // se init retornar true (conseguiu executar todas as tarefas com sucesso)
        if (init()) {
            // iniciar menu
            Menu intf = new Menu();
            intf.iniciar();

            // se não, printar erro e sair
        } else {
            System.out.println(Erro.ERRO_INIT);
            System.exit(-1);
        }

    }

    // verificações iniciais antes de iniciar o menu
    // garante que os arquivos necessários sejam criados e populados.
    public static boolean init() {

        System.out.println();
        
        OperadorArquivos csvUsuarioArq = new OperadorArquivos(CSVUSUARIO);

        File logdir = new File(CSVLOGDIR);

        // verifica se logdir existe, se não existir, tenta criar, se não conseguir
        // criar, retornar falso (erro)
        if (!(logdir.exists())) {
            // mkdirs() só retorna verdadeiro se todos os diretórios e subdiretórios foram
            // criados
            if (logdir.mkdirs()) {
                System.err.println("DIR: Diretório 'dados' + subdiretórios criados.");
                PRIMEIRAEXEC++;
            } else {
                System.err.println("ERRO: Diretórios não foram criados!");
                return false;
            }

        }

        // se CSV não existe, tenta criar, se não conseguir criar, retorna falso (erro)
        if (!(csvUsuarioArq.arquivoExiste())) {
            if (csvUsuarioArq.criarCSVeMontarCabecalho()) {
                System.err.println("CSV: DadosUsuario.csv não existia e foi criado.");
                PRIMEIRAEXEC++;

            } else {
                System.out.println("CSV: DadosUsuario.csv não foi criado.");
                return false;
            }

        }

        // se config não existe, criar, se não conseguir criar, retornar falso (erro)
        if (!(ctcliConfig.configExiste())) {
            if (ctcliConfig.criarConfig()) {
                System.err.println("CONFIG: Arquivo criado. " + "(" + ctcliConfig.configArqPath + ")");
                PRIMEIRAEXEC++;
            } else {
                System.err.println("CONFIG: Arquivo nao foi criado.");
                return false;
            }
        }
        // só retorna verdadeiro se tudo foi executado sem erro
        return true;
    }

}