package br.ufac.academico.exception;

public class EntityAlreadyExistsException extends Exception {

	public EntityAlreadyExistsException(String entidade) {
		super(String.format("Entidade já existe: %s!", entidade));	
	}
	
}
