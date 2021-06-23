package br.ufac.academico.db;
import java.sql.*;

import br.ufac.academico.exception.AccessDeniedException;
import br.ufac.academico.exception.DataBaseAlreadyConnectedException;
import br.ufac.academico.exception.DataBaseGenericException;
import br.ufac.academico.exception.DataBaseNotConnectedException;

public class Conexao {

	private static final String urlDriver = "com.mysql.cj.jdbc.Driver";
	private String urlSchema = "";
	//	private static final String sqlConsulta = "select * from alunos";
	private Connection con = null;
	private Statement stm = null;

	private boolean conectado = false;

	//	CARREGAMENTO MANUAL DA CLASSE EM VERSÕES < 4.0 DO JDBC	
	public Conexao() throws DataBaseGenericException {
		try {
			Class.forName(urlDriver);
		} catch (ClassNotFoundException e) {
			throw new DataBaseGenericException(404, "Driver não encontrado: " + e.getMessage());
		}
	}

	public boolean conecte(String urlSchema, String userName, String userPass) 
			throws DataBaseGenericException, DataBaseAlreadyConnectedException,
			AccessDeniedException
	{

		if(conectado) {
			throw new DataBaseAlreadyConnectedException("academico");
		}else {
			try {
				this.urlSchema = urlSchema;
				con = DriverManager.getConnection(urlSchema, userName, userPass);				
				conectado = true;
				System.out.printf("Conexão com (%s) estabelecida com sucesso!\n", urlSchema);
			} catch (SQLException e) {
				conectado = false;
				if (e.getErrorCode() == 1045) {
					throw new AccessDeniedException(userName);
				}else {
					throw new DataBaseGenericException(e.getErrorCode(), e.getMessage());
				}
			}			
		}

		return conectado;
	}

	public boolean desconecte() 
			throws DataBaseNotConnectedException, DataBaseGenericException 
	{
		if(!conectado) {
			throw new DataBaseNotConnectedException("academico");
		}else{		
			try {
				con.close();
				conectado = false;
				System.out.printf("Conexão com (%s) encerrada com sucesso!\n", urlSchema);
			} catch (SQLException e) {
				conectado = true;
				throw new DataBaseGenericException(e.getErrorCode(), e.getMessage());						
			}
		}
		return conectado;
	}

	public ResultSet consulte(String sqlConsulta)
			throws DataBaseNotConnectedException, DataBaseGenericException 
	{
		ResultSet rs = null;
		if(!conectado) {
			throw new DataBaseNotConnectedException("academico");
		}else{	
			try {
				stm = con.createStatement();
				rs = stm.executeQuery(sqlConsulta);
				System.out.printf("Consulta (%s) realizada com sucesso!\n", sqlConsulta);
			} catch (SQLException e) {
				throw new DataBaseGenericException(e.getErrorCode(), e.getMessage());				
			}
		}

		return rs;
	}

	public int atualize(String sqlAtualiza) 
			throws DataBaseNotConnectedException, DataBaseGenericException
	{

		int linhasAfetadas = -1;

		if(!conectado) {
			throw new DataBaseNotConnectedException("academico");
		}else{	
			try {
				stm = con.createStatement();
				linhasAfetadas = stm.executeUpdate(sqlAtualiza);				
				System.out.printf("Atualização (%s) realizada com sucesso!\n", sqlAtualiza);				
			} catch (SQLException e) {
				linhasAfetadas = -1;				
				throw new DataBaseGenericException(e.getErrorCode(), e.getMessage());						
			}
		}
		return linhasAfetadas;
	}

	public boolean estaConectado() {
		return conectado;
	}
	
}
