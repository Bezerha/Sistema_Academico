package br.ufac.academico.exception;

public class DataBaseNotConnectedException extends Exception {

	public DataBaseNotConnectedException(String dbName) {
		super(String.format("Banco de dados '%s' n?o est? conectado!", dbName));	
	}
	
}
