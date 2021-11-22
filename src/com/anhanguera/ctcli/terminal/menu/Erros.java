package com.anhanguera.ctcli.terminal.menu;
import static com.anhanguera.ctcli.terminal.menu.CodigosANSI.*;

public enum Erros {
    USER_PERMALOGIN_NOT_FOUND(0, "Usuário especificado em $permalogin não existe (ctcli.config)"),
    DUPLICATE_USER(1, "This user already exists.");
  
  
    private final int codigo;
    private final String descricao;
  
    private Erros(int codigo, String descricao) {
      this.codigo = codigo;
      this.descricao = descricao;

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
  
    @Override
    public String toString() {
      return codigo + ": " + descricao;
    }
}
