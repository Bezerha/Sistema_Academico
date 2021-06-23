package br.ufac.academico.exception;

public class EntityTableIsEmptyException extends Exception {

	public EntityTableIsEmptyException(String entidade) {
		super(String.format("Tabela da Entidade '%s' está vazia!", entidade));	
	}
	
}
