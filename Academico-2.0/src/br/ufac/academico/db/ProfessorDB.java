package br.ufac.academico.db;

import br.ufac.academico.entity.*;
import br.ufac.academico.exception.DataBaseGenericException;
import br.ufac.academico.exception.DataBaseNotConnectedException;
import br.ufac.academico.exception.EntityNotExistsException;
import br.ufac.academico.exception.EntityTableIsEmptyException;

import java.sql.*;
import java.util.*;

public class ProfessorDB {

	private Conexao cnx;
	private CentroDB cdb;
	private ResultSet rs;
	
	public ProfessorDB(Conexao cnx) {
		this.cnx = cnx;
		cdb = new CentroDB(cnx);
	}
	
	public ProfessorDB() {
		cdb = new CentroDB();
	}
	
	public void setConexao(Conexao cnx) {
		this.cnx = cnx;
		cdb.setConexao(cnx);
	}

	public void adicionar(Professor professor) 
	throws DataBaseNotConnectedException, DataBaseGenericException 
	{

		String sqlAtualize = "INSERT INTO professores (matricula, nome, rg, cpf, endereco, fone, centro) "
				+ "VALUES ("
				+ 		professor.getMatricula() 	+","
				+ "'" + professor.getNome() 		+ "', "
				+ "" + 	professor.getRg() 			+ ", "
				+ "" + 	professor.getCpf() 			+ ", "
				+ "'" + professor.getEndereco() 		+ "', "
				+ "'" + professor.getFone() 		+ "', "				
				+ "'"+ 	professor.getCentro().getSigla() +"');";

		cnx.atualize(sqlAtualize);
		
	}
	
	public void atualizar(Professor professor) 
	throws DataBaseNotConnectedException, DataBaseGenericException 
	{

		String sqlAtualize = "UPDATE professores SET "
				+ "nome = '" + professor.getNome() +"', "
				+ "rg = " + professor.getRg() +", "
				+ "cpf = " + professor.getCpf() +", "
				+ "endereco = '" + professor.getEndereco() +"', "
				+ "fone = '" + professor.getFone() +"', "
				+ "centro = '"+ professor.getCentro().getSigla() +"'"
				+ " WHERE matricula = " + professor.getMatricula() +";";

		cnx.atualize(sqlAtualize);
		
	}
	
	public void remover(Professor professor) 
	throws DataBaseNotConnectedException, DataBaseGenericException 
	{
		String sqlAtualize = "DELETE FROM professores "
				+ " WHERE matricula = " + professor.getMatricula() +";";

		cnx.atualize(sqlAtualize);
		
	}
	
	public Professor recuperar(long matricula) 
	throws DataBaseNotConnectedException, DataBaseGenericException, 
		EntityNotExistsException 
	{
		
		String sqlConsulta = "SELECT matricula AS 'Matrícula', "
				+ "nome AS 'Nome', "
				+ "rg AS 'RG', "
				+ "cpf AS 'CPF',"
				+ "endereco AS 'Endereço',"
				+ "fone AS 'Telefone',"				
				+ "centro AS 'Centro' "
				+ "FROM professores "
				+ "WHERE matricula = " + matricula +";";
		
		Professor professor = null;
		Centro centro = null;
		
		rs = cnx.consulte(sqlConsulta);
			
		try {
			if(rs.next()) {				
				centro = cdb.recuperar(rs.getString(7));
				professor = new Professor(rs.getLong(1), rs.getString(2), 
						rs.getLong(3), rs.getLong(4), rs.getString(5), rs.getString(6), centro);
			}else {
				throw new EntityNotExistsException("Professor [matricula = " + matricula + "]");
			}
		} catch (SQLException e) {
			throw new DataBaseGenericException(e.getErrorCode(), e.getMessage());				
		}
		return professor;
	}

	public List<Professor> recuperarTodos() 
	throws DataBaseNotConnectedException, DataBaseGenericException, 
		EntityNotExistsException, EntityTableIsEmptyException 
	{
		
		String sqlConsulta = "SELECT matricula AS 'Matrícula', "
				+ "nome AS 'Nome', "
				+ "rg AS 'RG', "
				+ "cpf AS 'CPF',"
				+ "endereco AS 'Endereço',"
				+ "fone AS 'Telefone',"				
				+ "centro AS 'Centro' "
				+ "FROM professores;";

		List<Professor> professores = new ArrayList<Professor>();
		Professor professor = null;
		Centro centro = null;
		
		rs = cnx.consulte(sqlConsulta);		
		
		try {
			while(rs.next()) {
				centro = cdb.recuperar(rs.getString(7));
				professor = new Professor(rs.getLong(1), rs.getString(2), 
						rs.getLong(3), rs.getLong(4), rs.getString(5), rs.getString(6), centro);
				
				professores.add(professor);
			}
		} catch (SQLException e) {
			throw new DataBaseGenericException(e.getErrorCode(), e.getMessage());			
		}
		if (professores.size() < 1) {
			throw new EntityTableIsEmptyException("Professor");
		}
		return professores;
	}

	public List<Professor> recuperarTodosPorNome()  
	throws DataBaseNotConnectedException, DataBaseGenericException, 
		EntityNotExistsException, EntityTableIsEmptyException 
	{
		
		String sqlConsulta = "SELECT matricula AS 'Matrícula', "
				+ "nome AS 'Nome', "
				+ "rg AS 'RG', "
				+ "cpf AS 'CPF',"
				+ "endereco AS 'Endereço',"
				+ "fone AS 'Telefone',"				
				+ "centro AS 'Centro' "
				+ "FROM professores "
				+ "ORDER BY nome;";

		List<Professor> professores = new ArrayList<Professor>();
		Professor professor = null;
		Centro centro = null;
		
		rs = cnx.consulte(sqlConsulta);		
		
		try {
			while(rs.next()) {
				centro = cdb.recuperar(rs.getString(7));
				professor = new Professor(rs.getLong(1), rs.getString(2), 
						rs.getLong(3), rs.getLong(4), rs.getString(5), rs.getString(6), centro);
				
				professores.add(professor);
			}
		} catch (SQLException e) {
			throw new DataBaseGenericException(e.getErrorCode(), e.getMessage());			
		}
		if (professores.size() < 1) {
			throw new EntityTableIsEmptyException("Professor");
		}
		return professores;
	}
	
	public List<Professor> recuperarTodosPorNomeContendo(String nome) 
	throws DataBaseNotConnectedException, DataBaseGenericException, 
		EntityNotExistsException 
	{
		
		String sqlConsulta = "SELECT matricula AS 'Matrícula', "
				+ "nome AS 'Nome', "
				+ "rg AS 'RG', "
				+ "cpf AS 'CPF',"
				+ "endereco AS 'Endereço',"
				+ "fone AS 'Telefone',"				
				+ "centro AS 'Centro' "
				+ "FROM professores "
				+ "WHERE nome like '%" + nome + "%' "
				+ "ORDER BY nome;";

		List<Professor> professores = new ArrayList<Professor>();
		Professor professor = null;
		Centro centro = null;
		
		rs = cnx.consulte(sqlConsulta);
		
		try {
			while(rs.next()) {
				
				centro = cdb.recuperar(rs.getString(7));
				professor = new Professor(rs.getLong(1), rs.getString(2), 
						rs.getLong(3), rs.getLong(4), rs.getString(5), rs.getString(6), centro);
				
				professores.add(professor);
			}
		} catch (SQLException e) {
			throw new DataBaseGenericException(e.getErrorCode(), e.getMessage());				
		}
		return professores;
	}	
	
}
