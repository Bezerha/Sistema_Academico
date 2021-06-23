package br.ufac.academico.exception;

public class EntityNotExistsException extends Exception {

	public EntityNotExistsException(String entidade) {
		super(String.format("Entidade não existe: %s!", entidade));	
	}
	
}
