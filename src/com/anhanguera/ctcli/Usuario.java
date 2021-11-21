package com.anhanguera.ctcli;

import java.util.ArrayList;

import java.util.List;

import com.anhanguera.ctcli.arquivo.OperadorArquivos;
import com.anhanguera.ctcli.terminal.Utilidades;


public class Usuario {
    
    String nome;
    String peso;
    String altura;
    String nivelatv;
    String idade;
    String sexo;

    public static final OperadorArquivos arquivoOps = new OperadorArquivos();

    public Usuario(String nome, String peso, String altura, String idade, String sexo, String nivelatv) {

        this.nome = nome;
        this.peso = peso;
        this.altura = altura;
        this.nivelatv = nivelatv;
        this.idade = idade;
        this.sexo = sexo;

    }

    public Usuario(String nome) {
        this.nome = nome;
    }

    // retorna verdadeiro somente se conseguiu criar o usuário + csv pessoal dele
    public boolean criarUsuario() {
        if(!(usuarioExiste(nome) && csvPessoalExiste(nome))) {
            String data = Utilidades.getDataHora();
            Double tdee = calcularTDEE();
            if(tdee == -1.0) {
                return false;
            }
            String strtdee = String.format("%.0f", tdee);
            String[] fileira = { nome, peso, altura, idade, sexo.toUpperCase(), nivelatv, strtdee, data };
            // ArquivoOps arquivoOps = new ArquivoOps();
            arquivoOps.acrescentarAoCSV(Main.CSVUSUARIO, fileira);
            // Depois de acrescentar ao CSV, checa se o usuário agora está presente
            if(Usuario.usuarioExiste(nome)) {
                System.out.println("Usuário criado");
                // Se está presente, tenta criar o diário do usuário (CSV Pessoal)
                if(criarCSVPessoal()) {
                    System.out.println("Diário criado");
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        return false;

    }

    // retorna verdadeiro se e somente se o CSV pessoal de nome existe.
    public boolean csvPessoalExiste(String nome) {
        List<String> arqs = new ArrayList<String>();
        arqs.addAll(Utilidades.getListaArq(Main.CSVLOGDIR));

        for(int i=0;i<arqs.size();i++) {
            // System.out.println("match: "+arqs.get(i));
            if(arqs.get(i).contains(nome)) {
                System.out.println("existe!");
                return true;

            }
        }
        return false;
    }

    // retorna verdadeiro só se conseguiu criar o CSV pessoal.
    public boolean criarCSVPessoal() {
        if(usuarioExiste(nome)) {
            if(!(csvPessoalExiste(nome))) {
                if(arquivoOps.criarCSVeMontarCabecalho(Main.CSVLOGDIR, nome)) {
                    // System.out.println("CSV criado");
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    // Remove um usuário, se achou o usuário no arquivo e removeu, retorna true, se não, false
    public boolean removerUsuario() {
        List<String> b = arquivoOps.listaCSVRemoverHeader(arquivoOps.lerDadosCSV(Main.CSVUSUARIO));
        for(int i=0;i<b.size();i++) {
            if(b.get(i).contains(nome)) {
                // o +1 é porque o removerFila() lê todo o arquivo, incluindo o cabeçalho
                // então é necessário pular o cabeçalho, por isso o +1
                arquivoOps.substituirFila(Main.CSVUSUARIO, i+1);
                Diario c = new Diario(nome);
                if(c.deletarDiario()) {
                    return true;
                } else {
                    return false;
                }
                // return true;
            }
        }
        return false;
    }

    // altera um dado de um usuário específico dentro do CSV
    // Parâmetros: propriedade a ser alterada, novo valor da propriedade
    public boolean alterarDados(String prop, String novoValor) {
        // Pegando a lista de usuários
        List<String> lista = new ArrayList<String>(arquivoOps.listaCSVRemoverHeader(arquivoOps.lerDadosCSV(Main.CSVUSUARIO)));
        Usuario u = new Usuario(nome);
        // Iterando a lista
        for(int i=0;i<lista.size();i++) {
            // tenta acha o nome do usuário
            if(lista.get(i).contains(nome)) {
                // controla qual propriedade irá ser substituida
                switch(prop) {

                    case "nome":
                        String[] alt = getDadosUsuario(nome);
                        alt[0] = novoValor;
                        alt[7] = Utilidades.getDataHora();
                        arquivoOps.substituirFila(Main.CSVUSUARIO, i+1, alt);
                        System.out.println("bleh2");
                        return true;

                    case "peso":
                        String[] alt2 = getDadosUsuario(nome);
                        alt2[1] = novoValor;
                        alt2[7] = Utilidades.getDataHora();
                        u = new Usuario(alt2[0],alt2[1],alt2[2],alt2[3],alt2[4],alt2[5]);
                        alt2[6] = String.format("%.0f",u.calcularTDEE());
                        arquivoOps.substituirFila(Main.CSVUSUARIO, i+1, alt2);
                        System.out.println("bleh");
                        return true;

                    
                    case "altura":
                        String[] alt3 = getDadosUsuario(nome);
                        alt3[2] = novoValor;
                        alt3[7] = Utilidades.getDataHora();
                        u = new Usuario(alt3[0],alt3[1],alt3[2],alt3[3],alt3[4],alt3[5]);
                        alt3[6] = String.format("%.0f",u.calcularTDEE());
                        arquivoOps.substituirFila(Main.CSVUSUARIO, i+1, alt3);
                        System.out.println("bleh3");
                        return true;


                    case "idade":
                        String[] alt4 = getDadosUsuario(nome);
                        alt4[3] = novoValor;
                        alt4[7] = Utilidades.getDataHora();
                        u = new Usuario(alt4[0],alt4[1],alt4[2],alt4[3],alt4[4],alt4[5]);
                        alt4[6] = String.format("%.0f",u.calcularTDEE());
                        arquivoOps.substituirFila(Main.CSVUSUARIO, i+1, alt4);
                        System.out.println("bleh5");
                        return true;


                    case "sexo":
                        String[] alt5 = getDadosUsuario(nome);
                        alt5[4] = novoValor.toUpperCase();
                        alt5[7] = Utilidades.getDataHora();
                        u = new Usuario(alt5[0],alt5[1],alt5[2],alt5[3],alt5[4],alt5[5]);
                        alt5[6] = String.format("%.0f",u.calcularTDEE());
                        arquivoOps.substituirFila(Main.CSVUSUARIO, i+1, alt5);
                        System.out.println("bleh6");
                        return true;

                    case "nivelatv":
                        String[] alt6 = getDadosUsuario(nome);
                        alt6[5] = novoValor;
                        alt6[7] = Utilidades.getDataHora();
                        u = new Usuario(alt6[0],alt6[1],alt6[2],alt6[3],alt6[4],alt6[5]);
                        alt6[6] = String.format("%.0f",u.calcularTDEE());
                        arquivoOps.substituirFila(Main.CSVUSUARIO, i+1, alt6);
                        System.out.println("bleh4");
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
    public static boolean usuarioExiste(String nome) {
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
    public static void printUsuarios() {
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
    public static String[] getDadosUsuario(String nome) {
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
    public static void printDadosUsuario(String nome) {
            String[] dados = Usuario.getDadosUsuario(nome);
            if(dados != null) {
                System.out.println("Nome: "+dados[0]);
                System.out.println("Peso: "+dados[1]+"kg");
                System.out.println("Altura: "+dados[2]+"cm");
                System.out.println("Idade: "+dados[3]+" anos");
                System.out.println("Sexo: "+dados[4]);
                switch(dados[5]) {
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
                    System.out.println("printDadosUsuario: nivelatv inválido.");
                    break;
            }

            System.out.println("TDEE: "+dados[6]);
            System.out.println("Última atualização: "+dados[7].replace("-"," "));
                
        }
    }

    // Calcula BMI do usuário usando a fórmula de Harris-Benedict
    // BMI = Índice Metabólico Basal, quantas calorias o corpo gasta em repouso durante 24h
    // Necessário pra calcular o TDEE
    // Fórmula:
    // Mulheres: 655 + (9,6 x peso em kg) + (1,8 x altura em cm) – (4,7 x idade em anos)
    // Homens: 66 + (13,7 x peso em kg) + (5 x altura em cm) – (6,5 x idade em anos)
    public double calcularBMI() {

        if(sexo.equalsIgnoreCase("f")) {
            double bmi = 655
             + (9.6 * Double.parseDouble(peso))
              + (1.8 * Double.parseDouble(altura))
               - (4.7 * Double.parseDouble(idade));

            return bmi;

            
        } else if(sexo.equalsIgnoreCase("m")) {
            double bmi = 66
            + (13.7 * Double.parseDouble(peso))
             + (5 * Double.parseDouble(altura))
              - (6.5 * Double.parseDouble(idade));

            // System.out.println(bmi);
            return bmi;
        }
        return 0.0;
    }

    public double calcularTDEE() {
        Double tdee;
        Double bmi = calcularBMI();
        if(bmi == 0.0) {
            return -1.0;
        }
        switch(nivelatv) {
            case "1":
                tdee = bmi * 1.2;
                return tdee;
            case "2":
                tdee = bmi * 1.375;
                return tdee;
            case "3":
                tdee = bmi * 1.55;
                return tdee;
            case "4":
                tdee = bmi * 1.725;
                return tdee;
            case "5":
                tdee = bmi * 1.9;
                return tdee;
            case default:
                return -1.0;

        }

    }

    public String getTDEE() {
        String[] dados = getDadosUsuario(nome);
        if(dados.length < 8) {
            return "";
        }
        return dados[6].trim();

    }

    // seria melhor usar códigos de retorno ao invés de true ou false.
    // TODO: migrar esses prints pra interfaceCLI, mudar de boolean pra int pra controlar mensagens de erro


    // Códigos de retorno:
    // 0 - Sucesso
    // 1 - Falha - Usuário já existe
    // 2 - Falha - Valor do parâmetro peso inválido
    // 3 - Falha - Valor do parâmetro altura inválido
    // 4 - Falha - Valor do parâmetro idade inválido
    // 5 - Falha - Valor do parâmetro sexo inválido
    public static int validarDadosUsuario(String[] dados) {
        if(Usuario.usuarioExiste(dados[0])) {
            return 1;
        }

        if(!dados[1].matches("[0-9]+") || dados[1].length() < 2 || Integer.parseInt(dados[1]) > 500) {
            return 2;
        }

        if(!dados[2].matches("[0-9]+") || dados[2].length() < 2 || Integer.parseInt(dados[2]) > 300) {
            return 3;
        }

        if(!dados[3].matches("[0-9]+") || Integer.parseInt(dados[3]) > 110 || Integer.parseInt(dados[3]) < 10) {
            return 4;
        }

        if(!(dados[4].equalsIgnoreCase("f") || dados[4].equalsIgnoreCase("m"))) {
            return 5;
        }

        return 0;

    }

    // método sobrecarregado, ao invés de validar todos os dados, valida só um $dado conforme $prop
    // TODO: Migrar mensagens de erro pra interfaceCLI, usar códigos de retorno igual o de cima
    public static boolean validarDadosUsuario(String prop, String dado) {
        switch(prop) {
            case "peso":
                if(!dado.matches("[0-9]+") || dado.length() < 2 || Integer.parseInt(dado) > 500) {
                    System.out.println("Parâmetro [peso] inválido. Tente novamente.");
                    return false;
                }
                return true;

            case "altura":
                if(!dado.matches("[0-9]+") || dado.length() < 2 || Integer.parseInt(dado) > 300) {
                    System.out.println("Parâmetro [altura] inválido. Tente novamente.");
                    return false;
                }
                return true;

            case "idade":
                int d5 = Integer.parseInt(dado);
                if(!dado.matches("[0-9]+") || d5 > 110 || d5 < 10) {
                    System.out.println("Parâmetro [idade] inválido. Tente novamente.");
                    return false;
                }
                return true;

            case "sexo":

                if(!(dado.equalsIgnoreCase("f") || dado.equalsIgnoreCase("m"))) {
                    System.out.println("Parâmetro [sexo] inválido. Tente novamente.");
                    return false;
                }
                return true;

            case "nivelatv":

                int d6 = Integer.parseInt(dado);
                if(!dado.matches("[0-9]+") || d6 > 5 || d6 < 1) {
                    System.out.println("Parâmetro [nivelatv] inválido. Tente novamente.");
                    return false;
                }
                return true;
    
            case "default":
                return false;
        }

        return false;
    }


    
}
