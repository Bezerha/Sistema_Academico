package br.ufac.academico.exception;

public class EntityNotExistsException extends Exception {

	public EntityNotExistsException(String entidade) {
		super(String.format("Entidade n�o existe: %s!", entidade));	
	}
	
}
