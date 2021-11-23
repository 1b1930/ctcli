package com.anhanguera.ctcli.terminal.menu.mensagens;

// enum que armazena todos os erros que o menu pode querer usar

public enum Erro {
    ARG_NUM_INVALIDO(0,"Número de argumentos inválidos. Tente novamente."),
    ARG_PESO_INVALIDO(1,"Valor do argumento [peso] inválido. Tente novamente."
    ,"Valor tem que ser número, ter pelo menos dois dígitos, e ser menor que 500."),
    ARG_ALTURA_INVALIDO(2,"Valor do argumento [altura] inválido. Tente novamente."
    ,"Valor tem que ser número, ter pelo menos dois dígitos, e ser menor que 300."),
    ARG_IDADE_INVALIDO(3,"Valor do argumento [idade] inválido. Tente novamente."
    ,"Valor tem que ser número, ser maior que 10, e ser menor que 110."),
    ARG_SEXO_INVALIDO(4, "Valor do argumento [sexo] inválido. Tente novamente."
    ,"Valor tem que ser M ou F."),
    USUARIO_JA_EXISTE(5,"O usuário já existe no banco de dados. Tente novamente."),
    USUARIO_NAO_CRIADO(6,"Usuário não criado. Tente novamente."),
    USUARIO_NAO_REMOVIDO(7,"Usuário não removido.","Uso: usuario remover [nome]"),
    PROP_INVALIDA(8,"Propriedade inválida."
    ,"Propriedades válidas: nome, peso, altura, idade, sexo, nivelatv"),
    USUARIO_DADOS_INVALIDOS(9,"Dados inválidos. Tente novamente."),
    LOGIN_CAMPO_EM_BRANCO(10,"Campo de usuário em branco. Digite um usuário para logar"
    ,"Comando: logar [usuario]"),
    USUARIO_NAO_ENCONTRADO(11,"Usuário não encontrado. Tente novamente"),
    CMD_INVALIDO_GLOBAL(12,"Comando inválido. (Global)"),
    CMD_INVALIDO_USUARIO(13, "Comando inválido. (Usuário)."),
    CMD_INVALIDO_DIARIO(14, "Comando inválido. (Diário)"),
    PERMALOGIN_USUARIO_NOT_FOUND(15, "Usuário especificado em $permalogin não existe (ctcli.config)"),
    PERMALOGIN_USUARIO_JA_HAB(16, "Permalogin já está ativado para esse usuário."),
    PERMALOGIN_NAO_FOI_HAB(17, "Permalogin não foi habilitado."),
    PERMALOGIN_NAO_ESTA_HAB(18, "Permalogin não está habilitado para esse usuário."),
    PERMALOGIN_NAO_FOI_DESAB(19, "Permalogin não foi desabilitado."),
    PERMALOGIN_JA_HABILITADO(20, "Permalogin já está ativado para esse usuário."),
    ARG_INVALIDO(20, "Argumentos inválidos. Tente novamente."),
    DIARIO_NOME_INCORRETO(21, "O nome do alimento deve conter pelo menos uma letra."),
    DIARIO_NOME_COMPRIMENTO_EXCEDIDO(22, "Nome do alimento muito grande."),
    DIARIO_CALORIAS_NAO_ENC(23, "Valor [calorias consumidas] não encontrado."),
    DIARIO_ADD_NOME_MUITOS_ESPACOS(24, "Nome do alimento contém muitos espaços."),
    DIARIO_NOTA_COMPRIMENTO_EXCEDIDO(25, "Comprimento da nota excedido."),
    DIARIO_ALIMENTO_NAO_ADICIONADO(26, "Alimento não foi adicionado."),
    ARG_KCAL_INVALIDO(27, "Argumento [calorias consumidas] inválido. Tente novamente."),
    DIARIO_ALIMENTO_NAO_EXISTE(28,"Alimento não existe."),
    ERRO_INIT(29,"Erro ao criar os arquivos necessários para o funcionamento do programa.");
  
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
      System.out.println("ERRO: "+descricao+" (Código "+codigo+")");
    }
  
    public int getcodigo() {
       return codigo;
    }
    
    // Sobrescrever o que toString() retorna, ao invés de retornar só
    // o valor do Enum (erro), retorna o código + descrição do Enum
    @Override
    public String toString() {
        if(codigo == -1) {
          return "ERRO: " + descricao;

        }

        if(!info2.isBlank()) {
            return "ERRO: " + descricao + " (Código " 
            + codigo + ")" + "\n" + info1 
            + "\n" + info2;
        }

        if(!info1.isBlank()) {
          return "ERRO: " + descricao + " (Código " 
          + codigo + ")" + "\n" + info1;
      }

      return "ERRO: " + descricao + " (Código " 
      + codigo + ")";
    }
}

