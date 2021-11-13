import java.util.ArrayList;
import java.util.List;

public class Usuario {
    
    String nome;
    String peso;
    String altura;
    String nivelatv;

    Usuario(String nome, String peso, String altura, String nivelatv) {

        this.nome = nome;
        this.peso = peso;
        this.altura = altura;
        this.nivelatv = nivelatv;

    }

    void criarUsuario() {
        String[] fileira = { nome, peso, altura, nivelatv };
        ArquivoOps arquivoOps = new ArquivoOps();
        arquivoOps.acrescentarAoCSV(Main.CSVUSUARIO, fileira);
        if(Usuario.usuarioExiste(nome)) {
            System.out.println("Usuário criado");

        } else {
            System.out.println("Oops, usuário não criado.");
        }

    }

    void excluirUsuario() {

    }

    void alterarDados() {
        
    }

    static boolean usuarioExiste(String nome) {
        ArquivoOps arquivoOps = new ArquivoOps();
        List<String> lista = new ArrayList<String>(arquivoOps.listaCSVRemoverHeader(arquivoOps.lerDadosCSV(Main.CSVUSUARIO)));
        for(int i=0; i<lista.size(); i++) {
            if(lista.get(i).contains(nome)) {
                return true;
            }
        }
        return false;
    }

    // Printa todos os usuários
    static void printUsuarios() {
        ArquivoOps arquivoOps = new ArquivoOps();
        List<String> lista = arquivoOps.listaCSVRemoverHeader(arquivoOps.lerDadosCSV(Main.CSVUSUARIO));
            // Método bonito pra printar todos os elementos de uma lista
            // lista.forEach(System.out::println);

            // Método mais flexível que o outro acima
            // Pula o cabeçalho.
            // for (int i=1; i<lista.size(); i++) {
            //     System.out.println(lista.get(i));
            // }

        // Printa a lista que só tem os elementos (sem cabeçalho)
         for (int i=0; i<lista.size(); i++) {
             System.out.println(lista.get(i));
        }
    }

    static String[] getDadosUsuario(String nome) {
        // inicializações tão dentro do if porque se estivessem fora,
        // e o usuário não existisse, seria perda de tempo iniciar e instanciar tudo isso
        if(usuarioExiste(nome)) {
            ArquivoOps a = new ArquivoOps();
            List<String> b = a.listaCSVRemoverHeader(a.lerDadosCSV(Main.CSVUSUARIO));
            String[] arrt;
            String element; 
            for (int i=0; i<b.size(); i++) {
                // System.out.println(b.get(i));
                if(b.get(i).contains(nome)) {
                    // System.out.println("funciona.");

                    // Limpando a string, removendo caracteres inúteis
                    element = b.get(i).replaceAll("[\\[\\] ]", "");
                    // quebrando a string em um array usando , como ponto de quebra
                    arrt = element.split(",");

                    // printa os elementos do array, so debug
                    // for(int j=0;j<arrt.length; j++) {
                    //     System.out.println(arrt[j]);
                    // }
                    return arrt;
                }

            }

            // for(int i=0;i<userData.size(); i++) {
            //     System.out.println(userData.get(i));
            // }
            // return arr;

        } else {System.out.println("Usuário não existe."); return null;}
        return null;

    }
    
}
