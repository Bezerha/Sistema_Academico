package br.ufac.academico.exception;

public class DataBaseGenericException extends Exception {

	public DataBaseGenericException(int errorCode, String errorMessage) {
		super(String.format("Exceção genérica: %d - %s!", errorCode, errorMessage));	
	}
	
}
