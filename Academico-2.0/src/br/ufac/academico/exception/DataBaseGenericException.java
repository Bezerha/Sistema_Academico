package br.ufac.academico.exception;

public class DataBaseGenericException extends Exception {

	public DataBaseGenericException(int errorCode, String errorMessage) {
		super(String.format("Exce��o gen�rica: %d - %s!", errorCode, errorMessage));	
	}
	
}
