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

    void salvar() {}

    void excluir() {}

    void alterarDados() {}

    static boolean usuarioExiste(String nome) {
        ArquivoOps arquivoOps = new ArquivoOps();
        List<String> lista = new ArrayList<String>(arquivoOps.lerDadosCSV(Main.CSVUSUARIO));
        for(int i=0; i<lista.size(); i++) {
            if(lista.get(i).contains(nome)) {
                return true;
            }
        }
        return false;
    }

    static void printUsuarios() {
        ArquivoOps arquivoOps = new ArquivoOps();
        List<String> lista = arquivoOps.lerDadosCSV(Main.CSVUSUARIO);
        // Printa a lista que só tem os elementos (sem cabeçalho)
         for (int i=0; i<lista.size(); i++) {
             System.out.println(lista.get(i));
        }
    }
    
}
