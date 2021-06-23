package br.ufac.academico.entity;

public class Curso {

	private int codigo;
	private String nome;
	
    public Curso(int codigo, String nome) {
        super();
        this.codigo = codigo;
        this.nome = nome;
    }
    
    public Curso() {

    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
