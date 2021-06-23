package br.ufac.academico.db;

import br.ufac.academico.entity.*;
import br.ufac.academico.exception.DataBaseGenericException;
import br.ufac.academico.exception.DataBaseNotConnectedException;
import br.ufac.academico.exception.EntityNotExistsException;
import br.ufac.academico.exception.EntityTableIsEmptyException;

import java.sql.*;
import java.util.*;

public class DisciplinaDB {

	private Conexao cnx;
	private CentroDB cdb;
	private ResultSet rs;
	
	public DisciplinaDB(Conexao cnx) {
		this.cnx = cnx;
		cdb = new CentroDB(cnx);
	}
	
	public DisciplinaDB() {
		cdb = new CentroDB();
	}
	
	public void setConexao(Conexao cnx) {
		this.cnx = cnx;
		cdb.setConexao(cnx);
	}

	public void adicionar(Disciplina disciplina) 
	throws DataBaseNotConnectedException, DataBaseGenericException 
	{

		String sqlAtualize = "INSERT INTO disciplinas (codigo, nome, ch, centro) "
				+ "VALUES ('"
				+ 		disciplina.getCodigo() 	+"',"
				+ "'" + disciplina.getNome() 		+ "', "
				+ "" + 	disciplina.getCh() 			+ ", "
				+ "'"+ 	disciplina.getCentro().getSigla() +"');";

		cnx.atualize(sqlAtualize);
		
	}
	
	public void atualizar(Disciplina disciplina) 
	throws DataBaseNotConnectedException, DataBaseGenericException 
	{

		String sqlAtualize = "UPDATE disciplinas SET "
				+ "nome = '" + disciplina.getNome() +"', "
				+ "ch = " + disciplina.getCh() +", "
				+ "centro = '"+ disciplina.getCentro().getSigla() +"'"
				+ " WHERE codigo = '" + disciplina.getCodigo() +"';";

		cnx.atualize(sqlAtualize);
		
	}
	
	public void remover(Disciplina disciplina) 
	throws DataBaseNotConnectedException, DataBaseGenericException 
	{

		String sqlAtualize = "DELETE FROM disciplinas "
				+ " WHERE codigo = '" + disciplina.getCodigo() +"';";

		cnx.atualize(sqlAtualize);
		
	}
	
	public Disciplina recuperar(String codigo) 
	throws DataBaseNotConnectedException, DataBaseGenericException, 
		EntityNotExistsException 
	{
		
		String sqlConsulta = "SELECT codigo AS 'Código', "
				+ "nome AS 'Nome', "
				+ "ch AS 'CH', "		
				+ "centro AS 'Centro' "
				+ "FROM disciplinas "
				+ "WHERE codigo = '" + codigo +"';";
		
		Disciplina disciplina = null;
		Centro centro = null;
		
		rs = cnx.consulte(sqlConsulta);
			
		try {
			if(rs.next()) {				
				centro = cdb.recuperar(rs.getString(4));
				disciplina = new Disciplina(rs.getString(1), rs.getString(2), 
							rs.getInt(3), centro);
			}else {
				throw new EntityNotExistsException("Disciplina [codigo = '" + codigo + "']");
			}
		} catch (SQLException e) {
			throw new DataBaseGenericException(e.getErrorCode(), e.getMessage());				
		}
		return disciplina;
	}

	public List<Disciplina> recuperarTodos() 
	throws DataBaseNotConnectedException, DataBaseGenericException, 
		EntityNotExistsException, EntityTableIsEmptyException 
	{
		
		String sqlConsulta = "SELECT codigo AS 'Código', "
				+ "nome AS 'Nome', "
				+ "ch AS 'CH', "		
				+ "centro AS 'Centro' "
				+ "FROM disciplinas;";

		List<Disciplina> disciplinas = new ArrayList<Disciplina>();
		Disciplina disciplina = null;
		Centro centro = null;
		
		rs = cnx.consulte(sqlConsulta);		
		
		try {
			while(rs.next()) {
				centro = cdb.recuperar(rs.getString(4));
				disciplina = new Disciplina(rs.getString(1), rs.getString(2), 
							rs.getInt(3), centro);
				disciplinas.add(disciplina);
			}
		} catch (SQLException e) {
			throw new DataBaseGenericException(e.getErrorCode(), e.getMessage());			
		}
		if (disciplinas.size() < 1) {
			throw new EntityTableIsEmptyException("Disciplina");
		}
		return disciplinas;
	}

	public List<Disciplina> recuperarTodosPorNome()  
	throws DataBaseNotConnectedException, DataBaseGenericException, 
		EntityNotExistsException, EntityTableIsEmptyException 
	{
		
		String sqlConsulta = "SELECT codigo AS 'Código', "
				+ "nome AS 'Nome', "
				+ "ch AS 'CH', "		
				+ "centro AS 'Centro' "
				+ "FROM disciplinas "
				+ "ORDER BY nome;";

		List<Disciplina> disciplinas = new ArrayList<Disciplina>();
		Disciplina disciplina = null;
		Centro centro = null;
		
		rs = cnx.consulte(sqlConsulta);		
		
		try {
			while(rs.next()) {
				centro = cdb.recuperar(rs.getString(4));
				disciplina = new Disciplina(rs.getString(1), rs.getString(2), 
							rs.getInt(3), centro);
				disciplinas.add(disciplina);
			}
		} catch (SQLException e) {
			throw new DataBaseGenericException(e.getErrorCode(), e.getMessage());			
		}
		if (disciplinas.size() < 1) {
			throw new EntityTableIsEmptyException("Disciplina");
		}
		return disciplinas;
	}
	
	public List<Disciplina> recuperarTodosPorNomeContendo(String nome) 
	throws DataBaseNotConnectedException, DataBaseGenericException, 
		EntityNotExistsException 
	{
		
		String sqlConsulta = "SELECT codigo AS 'Código', "
				+ "nome AS 'Nome', "
				+ "ch AS 'CH', "		
				+ "centro AS 'Centro' "
				+ "FROM disciplinas "
				+ "WHERE nome like '%" + nome + "%' "
				+ "ORDER BY nome;";

		List<Disciplina> disciplinas = new ArrayList<Disciplina>();
		Disciplina disciplina = null;
		Centro centro = null;
		
		rs = cnx.consulte(sqlConsulta);
		
		try {
			while(rs.next()) {
				
				centro = cdb.recuperar(rs.getString(4));
				disciplina = new Disciplina(rs.getString(1), rs.getString(2), 
							rs.getInt(3), centro);
				disciplinas.add(disciplina);
			}
		} catch (SQLException e) {
			throw new DataBaseGenericException(e.getErrorCode(), e.getMessage());				
		}
		return disciplinas;
	}	
	
}
