package br.ufac.academico.entity;

public class Curriculo {
	
    private long codigo;
    private Curso curso;
	private String descricao;
	
    public Curriculo(long codigo, Curso curso, String descricao) {
        this.codigo = codigo;
        this.curso = curso;
        this.descricao = descricao;
    }
    
    public Curriculo() {

    }
    
    public long getCodigo() {
        return codigo;
    }
    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }
    public Curso getCurso() {
        return curso;
    }
    public void setCurso(Curso curso) {
        this.curso = curso;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
}
