
import java.util.ArrayList;

import java.util.List;


public class Usuario {
    
    String nome;
    String peso;
    String altura;
    String nivelatv;
    String idade;

    public static final ArquivoOps arquivoOps = new ArquivoOps();

    Usuario(String nome, String peso, String altura, String idade, String nivelatv) {

        this.nome = nome;
        this.peso = peso;
        this.altura = altura;
        this.nivelatv = nivelatv;
        this.idade = idade;

    }

    Usuario() {}

    // retorna verdadeiro somente se conseguiu criar o usuário + csv pessoal dele
    boolean criarUsuario() {
        if(!(usuarioExiste(nome) && csvPessoalExiste(nome))) {
            String data = CLIUtil.getDataHora();
            String[] fileira = { nome, peso, altura, idade, nivelatv, data };
            // ArquivoOps arquivoOps = new ArquivoOps();
            arquivoOps.acrescentarAoCSV(Main.CSVUSUARIO, fileira);
            if(Usuario.usuarioExiste(nome)) {
                System.out.println("Usuário criado");
                if(criarCSVPessoal(nome)) {
                    System.out.println("CSV Pessoal criado");
                    return true;
                } else {
                    return false;
                }
            } else {
                // System.out.println("Oops, usuário não criado.");
                return false;
            }

        }

        return false;

    }

    // retorna verdadeiro se e somente se o CSV pessoal de uNome existe.
    boolean csvPessoalExiste(String uNome) {
        List<String> arqs = new ArrayList<String>();
        arqs.addAll(CLIUtil.getListaArq(Main.CSVLOGDIR));

        for(int i=0;i<arqs.size();i++) {
            // System.out.println("match: "+arqs.get(i));
            if(arqs.get(i).contains(uNome)) {
                System.out.println("existe!");
                return true;

            }
        }
        return false;
    }

    // retorna verdadeiro só se conseguiu criar o CSV pessoal.
    boolean criarCSVPessoal(String uNome) {
        if(usuarioExiste(uNome)) {
            if(!(csvPessoalExiste(uNome))) {
                if(arquivoOps.criarCSVeMontarCabecalho(Main.CSVLOGDIR, uNome)) {
                    // System.out.println("CSV criado");
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }



    // Remove um usuário, se achou o usuário dado como parâmetro no arquivo e removeu, retorna true, se não, false
    boolean removerUsuario(String uNome) {
        List<String> b = arquivoOps.listaCSVRemoverHeader(arquivoOps.lerDadosCSV(Main.CSVUSUARIO));
        for(int i=0;i<b.size();i++) {
            if(b.get(i).contains(uNome)) {
                // o +1 é porque o removerFila() lê todo o arquivo, incluindo o cabeçalho
                // então é necessário pular o cabeçalho, por isso o +1
                arquivoOps.substituirFila(Main.CSVUSUARIO, i+1);
                return true;
            }
        }
        return false;
    }

    // altera um dado de um usuário específico dentro do CSV
    // Parâmetros: nome do usuário, propriedade a ser alterada, novo valor da propriedade
    boolean alterarDados(String nome, String prop, String valAlt) {
        // Pegando a lista de usuários
        List<String> lista = new ArrayList<String>(arquivoOps.listaCSVRemoverHeader(arquivoOps.lerDadosCSV(Main.CSVUSUARIO)));
        // Iterando a lista
        for(int i=0;i<lista.size();i++) {
            // tenta acha o nome do usuário
            if(lista.get(i).contains(nome)) {
                // controla qual propriedade irá ser substituida
                switch(prop) {
                    case "peso":
                        String[] alt = getDadosUsuario(nome);
                        alt[1] = valAlt;
                        alt[5] = CLIUtil.getDataHora();
                        arquivoOps.substituirFila(Main.CSVUSUARIO, i+1, alt);
                        System.out.println("bleh");
                        return true;
                    case "nome":
                        String[] alt2 = getDadosUsuario(nome);
                        alt2[0] = valAlt;
                        alt2[5] = CLIUtil.getDataHora();
                        arquivoOps.substituirFila(Main.CSVUSUARIO, i+1, alt2);
                        System.out.println("bleh2");
                        return true;
                    
                    case "altura":
                        String[] alt3 = getDadosUsuario(nome);
                        alt3[2] = valAlt;
                        alt3[5] = CLIUtil.getDataHora();
                        arquivoOps.substituirFila(Main.CSVUSUARIO, i+1, alt3);
                        System.out.println("bleh3");
                        return true;

                    case "nivelatv":
                        String[] alt4 = getDadosUsuario(nome);
                        alt4[4] = valAlt;
                        alt4[5] = CLIUtil.getDataHora();
                        arquivoOps.substituirFila(Main.CSVUSUARIO, i+1, alt4);
                        System.out.println("bleh4");
                        return true;

                    case "idade":
                        String[] alt5 = getDadosUsuario(nome);
                        alt5[3] = valAlt;
                        alt5[5] = CLIUtil.getDataHora();
                        arquivoOps.substituirFila(Main.CSVUSUARIO, i+1, alt5);
                        System.out.println("bleh5");
                        return true;



                    case default:
                        System.out.println("lol");

                }

            }

        }
        return false;

    }


    // Retorna true se o usuário existe, se não, false
    // Novo método! não aceita mais nomes incompletos, agora o nome do usuário tem que ser exato.
    static boolean usuarioExiste(String nome) {
        String[] arrt;
        String element; 
        // ArquivoOps arquivoOps = new ArquivoOps();
        List<String> lista = new ArrayList<String>(arquivoOps.listaCSVRemoverHeader(arquivoOps.lerDadosCSV(Main.CSVUSUARIO)));
        for(int i=0; i<lista.size(); i++) {
            if(lista.get(i).contains(nome)) {
                // Limpando a string, removendo caracteres inúteis
                element = lista.get(i).replaceAll("[\\[\\] ]", "");
                // quebrando a string em um array usando , como ponto de quebra
                arrt = element.split(",");
                for (int j=0;j<arrt.length;j++) {
                    if(arrt[j].equals(nome)) {
                        return true;
                    }
                }
                // System.out.println(lista.get(i).toString());
                return false;
            }
        }
        return false;
    }

    // Printa todos os usuários
    static void printUsuarios() {
        // ArquivoOps arquivoOps = new ArquivoOps();
        List<String> lista = arquivoOps.listaCSVRemoverHeader(arquivoOps.lerDadosCSV(Main.CSVUSUARIO));
            // Método bonito pra printar todos os elementos de uma lista
            // lista.forEach(System.out::println);

        // Printa a lista que só tem os elementos (sem cabeçalho)
         for (int i=0; i<lista.size(); i++) {
             System.out.println(lista.get(i));
        }
    }

    // Retorna os dados do usuário em um array[]
    static String[] getDadosUsuario(String nome) {
        // inicializações tão dentro do if porque se estivessem fora,
        // e o usuário não existisse, seria perda de tempo iniciar e instanciar tudo isso
        if(usuarioExiste(nome)) {
            // ArquivoOps a = new ArquivoOps();
            List<String> b = arquivoOps.listaCSVRemoverHeader(arquivoOps.lerDadosCSV(Main.CSVUSUARIO));
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

    // printa os dados do usuário para o stdout
    static void printDadosUsuario(String nome) {
            String[] dados = Usuario.getDadosUsuario(nome);
            if(dados != null) {
                System.out.println("Nome: "+dados[0]);
                System.out.println("Peso: "+dados[1]+"kg");
                System.out.println("Altura: "+dados[2]+"cm");
                System.out.println("Idade: "+dados[3]+" anos");
                switch(dados[4]) {
                    case "1":
                    System.out.println("Nível de Atividade: Sedentário");
                    break;
                    case "2":
                    System.out.println("Nível de Atividade: Levemente Ativo");
                    break;
                    case "3":
                    System.out.println("Nível de Atividade: Moderadamente Ativo");
                    break;
                    case "4":
                    System.out.println("Nível de Atividade: Muito Ativo");
                    break;
                    case "5":
                    System.out.println("Nível de Atividade: Atleta");
                    break;
                    case default:
                    System.out.println("Argumento inválido.");
                    break;
            }
            System.out.println("Última atualização: "+dados[5].replace("-"," "));
                
        }
    }
    
}
