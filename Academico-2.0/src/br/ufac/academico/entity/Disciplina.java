package br.ufac.academico.entity;

public class Disciplina {

	private String codigo;
	private String nome;
	private int ch;
	private Centro centro;
	
    public Disciplina(String codigo, String nome, int ch, Centro centro) {
        super();
        this.codigo = codigo;
        this.nome = nome;
        this.ch = ch;
        this.centro = centro;
    }
    
    public Disciplina() {

    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCh() {
        return ch;
    }

    public void setCh(int ch) {
        this.ch = ch;
    }

    public Centro getCentro() {
        return centro;
    }

    public void setCentro(Centro centro) {
        this.centro = centro;
    }
	
}
