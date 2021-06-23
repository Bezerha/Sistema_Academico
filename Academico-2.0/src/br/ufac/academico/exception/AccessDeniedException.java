package br.ufac.academico.exception;

public class AccessDeniedException extends Exception {

	public AccessDeniedException(String userName) {
		super(String.format("Acesso negado ao usuário '%s'!", userName));	
	}
	
}
