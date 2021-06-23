package br.ufac.academico.exception;

public class DataBaseAlreadyConnectedException extends Exception {

	public DataBaseAlreadyConnectedException(String dbName) {
		super(String.format("Banco de dados '%s' já está conectado!", dbName));	
	}
	
}
