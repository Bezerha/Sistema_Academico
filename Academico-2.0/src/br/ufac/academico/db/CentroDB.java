package br.ufac.academico.db;

import br.ufac.academico.entity.*;
import br.ufac.academico.exception.*;

import java.sql.*;
import java.util.*;

public class CentroDB {

	private Conexao cnx;
	private ResultSet rs ;
	
	public CentroDB(Conexao cnx) {
		this.cnx = cnx;
	}
	
	public CentroDB() {
		
	}
	
	public void setConexao(Conexao cnx) {
		this.cnx = cnx;		
	}

	public void adicionar(Centro centro) 
	throws DataBaseNotConnectedException, DataBaseGenericException 
	{

		String sqlAtualize = "INSERT INTO centros (sigla, nome) "
				+ "VALUES ('" + centro.getSigla() + "', "
						+ "'" + centro.getNome() + "');";
			
		cnx.atualize(sqlAtualize);
		
	}
	
	public void atualizar(Centro centro) 
	throws DataBaseNotConnectedException, DataBaseGenericException 
	{

		String sqlAtualize = "UPDATE centros "
				+ "SET nome = '" + centro.getNome() + "' "
				+ "WHERE sigla = '" + centro.getSigla() + "';";

		cnx.atualize(sqlAtualize);
		
	}
	
	public void remover(Centro centro) 
	throws DataBaseNotConnectedException, DataBaseGenericException 
	{

		String sqlAtualize = "DELETE FROM centros "
				+ "WHERE sigla = '" + centro.getSigla() + "';";

		cnx.atualize(sqlAtualize);
		
	}
	
	public Centro recuperar(String sigla) 
	throws DataBaseNotConnectedException, DataBaseGenericException,
		EntityNotExistsException
	{
		
		String sqlConsulta = "SELECT sigla, nome "
				+ "FROM centros "
				+ "WHERE sigla = '" + sigla + "';";
		
		Centro centro = null;
		
		rs = cnx.consulte(sqlConsulta);
			
		try {
			if(rs.next()) {
				centro = new Centro(rs.getString(1), rs.getString(2));
			}else{
				// CONSEGUIU EXECUTAR next, MAS RETORNOU false
				throw new EntityNotExistsException("Centro [sigla = '" + sigla + "']");
			}
		} catch (SQLException e) {
			//HOUVE ALGUMA FALHA NA EXECUÇÃO DO next
			throw new DataBaseGenericException(e.getErrorCode(), e.getMessage());
		}
		return centro;
	}

	public List<Centro> recuperarTodos() 
	throws DataBaseNotConnectedException, DataBaseGenericException, 
		EntityTableIsEmptyException 
	{
		
		String sqlConsulta = 
				"SELECT sigla, nome "
				+ "FROM centros;";

		List<Centro> centros = new ArrayList<Centro>();
		Centro centro = null;
		
		rs = cnx.consulte(sqlConsulta);		
		
		try {
			while(rs.next()) {
				centro = new Centro(rs.getString(1), rs.getString(2));
				centros.add(centro);
			}
		} catch (SQLException e) {
			throw new DataBaseGenericException(e.getErrorCode(), e.getMessage());		
		}
		if (centros.size() < 1) {
			throw new EntityTableIsEmptyException("Centro");
		}		
		return centros;
	}

	public List<Centro> recuperarTodosPorNome() 
	throws DataBaseNotConnectedException, DataBaseGenericException, 
		EntityTableIsEmptyException
	{
		
		String sqlConsulta = 
				"SELECT sigla, nome "
				+ "FROM centros "
				+ "ORDER BY nome;";

		List<Centro> centros = new ArrayList<Centro>();
		Centro centro=null;
		
		rs = cnx.consulte(sqlConsulta);		
		
		try {
			while(rs.next()) {
				centro = new Centro(rs.getString(1), rs.getString(2));
				centros.add(centro);
			}
		} catch (SQLException e) {
			throw new DataBaseGenericException(e.getErrorCode(), e.getMessage());			
		}
		if (centros.size() < 1) {
			throw new EntityTableIsEmptyException("Centro");
		}
		return centros;
	}
	
	public List<Centro> recuperarTodosPorNomeContendo(String nome) 
	throws DataBaseNotConnectedException, DataBaseGenericException 
	{
		
		String sqlConsulta = 
				"SELECT sigla, nome "
				+ "FROM centros "
				+ "WHERE nome like '%" + nome + "%' "
				+ "ORDER BY nome;";

		List<Centro> centros = new ArrayList<Centro>();
		Centro centro=null;
		
		rs = cnx.consulte(sqlConsulta);
		
		try {
			while(rs.next()) {
				centro = new Centro(rs.getString(1), rs.getString(2));
				centros.add(centro);
			}
		} catch (SQLException e) {
			throw new DataBaseGenericException(e.getErrorCode(), e.getMessage());			
		}
		return centros;
	}	
	
}


