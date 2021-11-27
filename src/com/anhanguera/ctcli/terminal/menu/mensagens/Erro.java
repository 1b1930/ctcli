package com.anhanguera.ctcli.terminal.menu.mensagens;

import com.anhanguera.ctcli.terminal.util.UtilidadesCLI;

// enum que armazena todos os erros que o menu pode querer usar

public enum Erro {
    ARG_NUM_INVALIDO(0,"Numero de argumentos invalidos. Tente novamente."),
    ARG_NOME_INVALIDO(1,"Valor do argumento [nome] invalido. Tente novamente."
    ,"Valor tem que ter no máximo 10 letras, e nao pode ter virgulas ou espaços."),
    ARG_PESO_INVALIDO(1,"Valor do argumento [peso] invalido. Tente novamente."
    ,"Valor tem que ser numero, ter pelo menos dois digitos, e ser menor que 500."),
    ARG_ALTURA_INVALIDO(2,"Valor do argumento [altura] invalido. Tente novamente."
    ,"Valor tem que ser numero, ter pelo menos dois digitos, e ser menor que 300."),
    ARG_IDADE_INVALIDO(3,"Valor do argumento [idade] invalido. Tente novamente."
    ,"Valor tem que ser numero, ser maior que 10, e ser menor que 110."),
    ARG_SEXO_INVALIDO(4, "Valor do argumento [sexo] invalido. Tente novamente."
    ,"Valor tem que ser M ou F."),
    USUARIO_JA_EXISTE(5,"O usuario ja existe no banco de dados. Tente novamente."),
    USUARIO_NAO_CRIADO(6,"Usuario nao criado. Tente novamente."),
    USUARIO_NAO_REMOVIDO(7,"Usuario nao removido.","Uso: usuario remover [nome]"),
    PROP_INVALIDA(8,"Propriedade invalida."
    ,"Propriedades validas: nome, peso, altura, idade, sexo, nivelatv"),
    USUARIO_DADOS_INVALIDOS(9,"Dados invalidos. Tente novamente."),
    LOGIN_CAMPO_EM_BRANCO(10,"Campo de usuario em branco. Digite um usuario para logar"
    ,"Comando: logar [usuario]"),
    USUARIO_NAO_ENCONTRADO(11,"Usuario nao encontrado. Tente novamente"),
    CMD_INVALIDO_GLOBAL(12,"Comando invalido. (Global)"),
    CMD_INVALIDO_USUARIO(13, "Comando invalido. (Usuario)."),
    CMD_INVALIDO_DIARIO(14, "Comando invalido. (Diario)"),
    PERMALOGIN_USUARIO_NOT_FOUND(15, "Usuario especificado em $permalogin nao existe (ctcli.config)"),
    PERMALOGIN_USUARIO_JA_HAB(16, "Permalogin ja esta ativado para esse usuario."),
    PERMALOGIN_NAO_FOI_HAB(17, "Permalogin nao foi habilitado."),
    PERMALOGIN_NAO_ESTA_HAB(18, "Permalogin nao esta habilitado para esse usuario."),
    PERMALOGIN_NAO_FOI_DESAB(19, "Permalogin nao foi desabilitado."),
    PERMALOGIN_JA_HABILITADO(20, "Permalogin ja esta ativado para esse usuario."),
    ARG_INVALIDO(20, "Argumentos invalidos. Tente novamente."),
    DIARIO_NOME_INCORRETO(21, "O nome do alimento deve conter pelo menos uma letra."),
    DIARIO_NOME_COMPRIMENTO_EXCEDIDO(22, "Nome do alimento muito grande."),
    DIARIO_CALORIAS_NAO_ENC(23, "Valor [calorias consumidas] nao encontrado."),
    DIARIO_ADD_NOME_MUITOS_ESPACOS(24, "Nome do alimento contem muitos espaços."),
    DIARIO_NOTA_COMPRIMENTO_EXCEDIDO(25, "Comprimento da nota excedido."),
    DIARIO_ALIMENTO_NAO_ADICIONADO(26, "Alimento nao foi adicionado."),
    ARG_KCAL_INVALIDO(27, "Argumento [calorias consumidas] invalido. Tente novamente."),
    DIARIO_ALIMENTO_NAO_EXISTE(28,"Alimento nao existe."),
    ERRO_INIT(29,"Erro ao criar os arquivos necessarios para o funcionamento do programa.");
  
    private final int codigo;
    private final String descricao;
    private final String info1;
    private final String info2;


    private Erro(String descricao) {
      this.codigo = -1;
      this.descricao = descricao;
      this.info1 = "";
      this.info2 = "";

    }


  
    private Erro(int codigo, String descricao) {
      this.codigo = codigo;
      this.descricao = descricao;
      this.info1 = "";
      this.info2 = "";

    }

    private Erro(int codigo, String descricao, String info1) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.info1 = info1;
        this.info2 = "";
  
      }

    private Erro(int codigo, String descricao, String info1, String info2) {
      this.codigo = codigo;
      this.descricao = descricao;
      this.info1 = info1;
      this.info2 = info2;

  
    }


  
    public String getDescricao() {
       return descricao;
    }

    public void print() {
      System.out.println("ERRO: "+descricao+" (Codigo "+codigo+")");
    }
  
    public int getcodigo() {
       return codigo;
    }
    
    // Sobrescrever o que toString() retorna, ao inves de retornar so
    // o valor do Enum (erro), retorna o codigo + descriçao do Enum
    @Override
    public String toString() {
        if(codigo == -1) {
          return "ERRO: " + descricao;

        }

        if(!UtilidadesCLI.isBlankString(info2)) {
            return "ERRO: " + descricao + " (Codigo " 
            + codigo + ")" + "\n" + info1 
            + "\n" + info2;
        }

        if(!UtilidadesCLI.isBlankString(info1)) {
          return "ERRO: " + descricao + " (Codigo " 
          + codigo + ")" + "\n" + info1;
      }

      return "ERRO: " + descricao + " (Codigo " 
      + codigo + ")";
    }
}

