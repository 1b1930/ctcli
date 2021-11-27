package com.anhanguera.ctcli.arquivo;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class ArquivoConfig {

    public String configArqPath;

    private final OperadorArquivos configArq;

    public ArquivoConfig(String configArqPath) {
        this.configArqPath = configArqPath;
        configArq = new OperadorArquivos(configArqPath);
    }

    // tenta criar config

    // verifica se config existe
    public boolean configExiste() {
        if (configArq.arquivoExiste()) {
            return true;

        } else {
            return false;
        }
    }

    public boolean criarConfig() {
        if (configArq.criarArquivo()) {
            if (popularConfig()) {
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
            PrintWriter writer = new PrintWriter(configArqPath, "UTF-8");
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
        if (configExiste()) {
            String subs = "permalogin=" + usr;
            if (substituirNoArquivoConfig("permalogin", subs)) {
                return true;
            } else {
                return false;
            }
        }
        return false;

    }

    // retorna o que vem depois do = em permalogin= em ctcli.config como String,
    // pode ser "" ou null
    public String getPermaLoginUsr() {
        // lista com as linhas do arquivo
        List<String> lista = new ArrayList<>();
        // OperadorArquivos arquivoOps = new OperadorArquivos();
        // le o arquivo e adiciona todas as linhas na lista
        lista.addAll(configArq.lerArquivo());
        // itera pela lista
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).contains("permalogin")) {
                // retorna uma substring com tudo o que vem depois de =
                // remove espaços em branco
                return (lista.get(i).substring(lista.get(i).lastIndexOf("=") + 1).trim());
            }
        }
        // retorna str vazia se não achou nada
        return "";

    }

    public boolean substituirNoArquivoConfig(String match, String subs) {
        // lista com as linhas do Arquivo
        List<String> lista = new ArrayList<String>();
        // objeto config
        // adiciona todas as linhas de config na lista
        lista.addAll(configArq.lerArquivo());
        int cont = 0;
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).contains(match)) {
                lista.remove(i);
                lista.add(i, subs);
                cont++;
            }
        }
        // só continua se só achou uma instância de permalogin=
        if (cont == 1) {
            // deleta tudo do Arquivo
            if (configArq.escreverNoArquivo()) {
                for (int i = 0; i < lista.size(); i++) {
                    // escreve as linhas no Arquivo denovo, se nao conseguiu, retorna false
                    if (!(configArq.acrescentarAoArquivo(lista.get(i).toString()))) {
                        return false;
                    }
                }
            } else {
                return false;
            }
            return true;
        } else {
            System.out.println("ERRO: Mais de um 'permalogin' no Arquivo ctcli.config?");
        }
        return false;

    }

    // public boolean

}
