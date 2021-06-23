package br.ufac.academico.entity;

public class Professor {

	private long matricula;
	private String nome;
	private long rg;
	private long cpf;
	private String endereco;
	private String fone;
	private Centro centro;
	
	public Professor(long matricula, String nome, long rg, long cpf, String endereco, String fone, Centro centro) {
		super();
		this.matricula = matricula;
		this.nome = nome;
		this.rg = rg;
		this.cpf = cpf;
		this.endereco = endereco;
		this.fone = fone;
		this.centro = centro;
	}
	
	public Professor() {
		
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

	public long getRg() {
		return rg;
	}

	public void setRg(long rg) {
		this.rg = rg;
	}

	public long getCpf() {
		return cpf;
	}

	public void setCpf(long cpf) {
		this.cpf = cpf;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getFone() {
		return fone;
	}

	public void setFone(String fone) {
		this.fone = fone;
	}

	public Centro getCentro() {
		return centro;
	}

	public void setCentro(Centro centro) {
		this.centro = centro;
	}
	
	
	
}
