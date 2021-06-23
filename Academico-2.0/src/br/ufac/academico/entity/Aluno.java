package br.ufac.academico.entity;

public class Aluno {

    private long matricula;
	private String nome;
    private String fone;
	private String endereco;
    private String cep;
    private String sexo;
	private Curso curso;

    public Aluno(long matricula, String nome, String fone, String endereco, String cep, String sexo, Curso curso) {
        super();
        this.matricula = matricula;
        this.nome = nome;
        this.fone = fone;
        this.endereco = endereco;
        this.cep = cep;
        this.sexo = sexo;
        this.curso = curso;
    }
    
    public Aluno() {
    	
    }


    public long getMatricula() {
        return matricula;
    }

    public void setMatricula(long matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFone() {
        return fone;
    }

    public void setFone(String fone) {
        this.fone = fone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }
    
    
    
}
