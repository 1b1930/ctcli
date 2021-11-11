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
        
        System.out.println("Usuário criado");

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
    
}
