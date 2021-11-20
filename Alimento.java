import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
// import org.apache.commons.lang3.StringUtils;


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

     Alimento(String nome) {
         this.nome = nome;

     }

     Alimento() {}

     // adiciona um alimento usando acrescentaraocsv

     boolean adicionarAlimento() {

        if(alimentoExiste(this.nome)) {
            // System.out.println("Alimento existe. Abortando...");
            return false;
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");  
        LocalDateTime now = LocalDateTime.now();  
        data = dtf.format(now).toString();
        // System.out.println(data);  
        String[] fileira = { nome, kcal, data };
        arquivoOps.acrescentarAoCSV(Main.CSVALIMENTOS, fileira);
        return true;
     }

     // mais relax que o verificador de usuario, acho que é melhor assim
     boolean alimentoExiste(String nome) {
        String[] arrt;
        String element; 
        List<String> lista = new ArrayList<String>(arquivoOps.listaCSVRemoverHeader(arquivoOps.lerDadosCSV(Main.CSVALIMENTOS)));
        for(int i=0; i<lista.size(); i++) {
            // Limpando a string, removendo caracteres inúteis
            element = lista.get(i).replaceAll("[\\[\\]]", "");
            // quebrando a string em um array usando , como ponto de quebra
            arrt = element.split(",");
            // ao invés de iterar todos os itens, é só verificar arrt[0]
            if(arrt[0].equals(nome)) {
                return true;
            }

        }
        return false;
        
     }

     // retorna os dados de 1 alimento, em formato String array[]
     String[] getDadosAlimento(String nome) {

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
                    element = b.get(i).replaceAll("[\\[\\]]", "");
                    // quebrando a string em um array usando , como ponto de quebra
                    arrt = element.split(",");

                    // printa os elementos do array, so debug
                    // for(int j=0;j<arrt.length; j++) {
                    //     System.out.println(arrt[j]);
                    // }
                    return arrt;
                }

            }

        } else {return null;}
        return null;

    }

    boolean removerAlimento(String nome) {
        List<String> b = arquivoOps.listaCSVRemoverHeader(arquivoOps.lerDadosCSV(Main.CSVALIMENTOS));
        for(int i=0;i<b.size();i++) {
            if(b.get(i).contains(nome)) {
                // o +1 é porque o substituirFila() lê todo o arquivo, incluindo o cabeçalho
                // então é necessário pular o cabeçalho, por isso o +1
                arquivoOps.substituirFila(Main.CSVALIMENTOS, i+1);
                // System.out.println("werks?");
                return true;
            }
        }
    return false;
    }

    // TODO: Adicionar suporte aos diários
    boolean alterarDados(String nome, String prop, String valAlt, boolean bdPessoal) {
        // Pegando a lista de usuários
        List<String> lista = new ArrayList<String>(arquivoOps.listaCSVRemoverHeader(arquivoOps.lerDadosCSV(Main.CSVALIMENTOS)));
        // Iterando a lista
        for(int i=0;i<lista.size();i++) {
            // tenta acha o nome do usuário
            if(lista.get(i).contains(nome)) {
                // controla qual propriedade irá ser substituida
                switch(prop) {
                    case "kcal":
                        String[] alt = getDadosAlimento(nome);
                        alt[1] = valAlt;
                        arquivoOps.substituirFila(Main.CSVALIMENTOS, i+1, alt);
                        //System.out.println("bleh");
                        return true;
                    case "nome":
                        String[] alt2 = getDadosAlimento(nome);
                        alt2[0] = valAlt;
                        arquivoOps.substituirFila(Main.CSVALIMENTOS, i+1, alt2);
                        // System.out.println("bleh2");
                        return true;

                    case default:
                        return false;
                        //System.out.println("lol");

                }

            }

        }
        return false;
        

    }

    void printDadosAlimento(String nome) {
        String[] dados = getDadosAlimento(nome);
        if(dados != null) {
            System.out.println("Nome: "+dados[0].replace("_"," "));
            System.out.println("KCAL/100g: "+dados[1]);
            System.out.println("Data da Modificação: "+dados[2]);
            
        } else {System.out.println("Usuário não existe.");}
    }

    static void printAlimentos() {
        // ArquivoOps arquivoOps = new ArquivoOps();
        List<String> lista = arquivoOps.listaCSVRemoverHeader(arquivoOps.lerDadosCSV(Main.CSVALIMENTOS));
            // Método bonito pra printar todos os elementos de uma lista
            // lista.forEach(System.out::println);

        // Printa a lista que só tem os elementos (sem cabeçalho)
         for (int i=0; i<lista.size(); i++) {
             System.out.println(lista.get(i));
        }
    }
}

