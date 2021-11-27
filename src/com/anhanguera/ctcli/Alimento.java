package com.anhanguera.ctcli;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import com.anhanguera.ctcli.terminal.util.Listas;

public class Alimento extends Diario {

    String nome;
    String kcal;
    String nota;
    String usuario;

    public Alimento(String nome, String kcal, String nota, String usuario) {
        super(usuario);
        this.nome = nome;
        this.kcal = kcal;
        this.nota = nota;
        this.usuario = usuario;

    }

    public Alimento(String nome, String usuario) {
        super(usuario);
        this.nome = nome;

    }

    public boolean adicionar() {

        String[] dados = { nome, kcal, nota };

        // formatador de data, basicamente o metodo como formatará uma data
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        // pega a data e hora atuais
        LocalDateTime now = LocalDateTime.now();
        // transforma essa data numa string
        String data = dtf.format(now).toString().replace(" ", "-");

        // adiciona os elementos (+data) no CSV
        String[] fileira = { dados[0], dados[1], data, dados[2] };
        arqDiario.acrescentarAoCSV(fileira);
        return true;

    }

    public boolean remover() {
        // lista que armazena o conteudo do diario
        List<String> b = getDiario();

        // itera pela lista
        for (int i = b.size(); i > 0; i--) {
            // System.out.println(b.get(i));
            String[] elem = Listas.separarElementos(b.get(i - 1));
            if (elem[0].contains(nome)) {
                // o b.size() começa em 1, e por causa que o substituirFila() lê todo o arquivo,
                // incluindo o cabeçalho
                // não é necessário o -1 aqui. Seria necessário um +1 se tivessemos iterando
                // pela lista de cima pra baixo
                // mas já que estamos indo de baixo pra cima, usando b.size() como o "topo", não
                // é necessário.
                // System.out.println(i);
                arqDiario.substituirFila(i);
                // System.out.println("werks?");
                return true;
            }
        }
        return false;
    }

}
