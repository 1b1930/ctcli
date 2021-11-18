import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import ExcecoesCustom.AlimentoNaoExisteException;


// TODO: Hack pra adicionar alimentos com espaço: substituir todos os espaços do nome do alimento com - e substituir devolta antes de printar

public class Alimento {

    String nome;
    String kcal;
    String data;
    String nota;

    public static final ArquivoOps arquivoOps = new ArquivoOps();

     Alimento(String nome, String kcal) {
         this.nome = nome;
         this.kcal = kcal;

     }

     Alimento() {

     }

     // adiciona um alimento usando acrescentaraocsv
     // TODO: Adicionar checks pra saber se o alimento já existe no banco de dados
     boolean adicionarAlimento() {

        if(alimentoExiste(this.nome)) {
            // System.out.println("Alimento existe. Abortando...");
            return false;
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");  
        LocalDateTime now = LocalDateTime.now();  
        data = dtf.format(now).toString();
        System.out.println(data);  
        String[] fileira = { nome, kcal, data };
        arquivoOps.acrescentarAoCSV(Main.CSVALIMENTOS, fileira);
        return true;
     }

     // mais relax que o verificador de usuario, acho que é melhor assim
     // TODO: Terminar de codar essa porra
     static boolean alimentoExiste(String nome) {
        String[] arrt;
        String element; 
        List<String> lista = new ArrayList<String>(arquivoOps.listaCSVRemoverHeader(arquivoOps.lerDadosCSV(Main.CSVALIMENTOS)));
        for(int i=0; i<lista.size(); i++) {
            // Limpando a string, removendo caracteres inúteis
            element = lista.get(i).replaceAll("[\\[\\]]", "");
            // quebrando a string em um array usando , como ponto de quebra
            arrt = element.split(",");
            // ao invés de iterar todos os itens, é só verificar arrt[0]
            if(arrt[0].equalsIgnoreCase(nome)) {
                return true;
            }

            // for (int j=0;j<arrt.length;j++) {
            //     System.out.println(arrt[j]);
            //     if(arrt[j].equalsIgnoreCase(nome)) {
            //         return true;
            //     }
            // }
        }
        return false;
        
     }

     // TODO: port de getDadosUsuario. Ainda não é usável, consertar isso
     static String[] getDadosAlimento(String nome) {
        // inicializações tão dentro do if porque se estivessem fora,
        // e o usuário não existisse, seria perda de tempo iniciar e instanciar tudo isso
        if(alimentoExiste(nome)) {
            // ArquivoOps a = new ArquivoOps();
            List<String> b = arquivoOps.listaCSVRemoverHeader(arquivoOps.lerDadosCSV(Main.CSVALIMENTOS));
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

        } else {System.out.println("Alimento não existe."); return null;}
        return null;

    }


}

