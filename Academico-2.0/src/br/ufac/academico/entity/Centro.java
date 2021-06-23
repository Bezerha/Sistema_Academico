package br.ufac.academico.entity;

public class Centro {

	private String sigla;
	private String nome;
	
	public Centro(String sigla, String nome) {
		super();
		this.sigla = sigla;
		this.nome = nome;
	}
	
	public Centro() {
		
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {
		return String.format("%s (%s)", nome, sigla);
	}
	
}
