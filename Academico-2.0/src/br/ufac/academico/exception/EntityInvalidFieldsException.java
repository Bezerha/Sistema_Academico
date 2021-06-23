package br.ufac.academico.exception;

import java.util.List;

public class EntityInvalidFieldsException extends Exception {

	public EntityInvalidFieldsException(String entidade, List<String> campos) {
		super(String.format("Campos de entidade inválidos: %s %s!", entidade, campos.toString())); 	
	}
	
}
