package br.ufac.academico.db;

import br.ufac.academico.entity.*;
import br.ufac.academico.exception.DataBaseGenericException;
import br.ufac.academico.exception.DataBaseNotConnectedException;
import br.ufac.academico.exception.EntityNotExistsException;
import br.ufac.academico.exception.EntityTableIsEmptyException;

import java.sql.*;
import java.util.*;

public class AlunoDB {

	private Conexao cnx;
	private CursoDB cdb;
	private ResultSet rs;
	
	public AlunoDB(Conexao cnx) {
		this.cnx = cnx;
		cdb = new CursoDB(cnx);
	}
	public AlunoDB() {
		cdb = new CursoDB();
	}
	
	public void setConexao(Conexao cnx) {
		this.cnx = cnx;
		cdb.setConexao(cnx);
	}

	public void adicionar(Aluno aluno) 
	throws DataBaseNotConnectedException, DataBaseGenericException 
	{

		String sqlAtualize = "INSERT INTO alunos (matricula, nome, fone, endereco, cep, sexo, curso) "
				+ "VALUES ("
				+ 		aluno.getMatricula() 	+","
				+ "'" + aluno.getNome() 		+ "', "
				+ "'" + aluno.getFone() 		+ "', "
                + "'" + aluno.getEndereco() 	+ "', "
                + "'" + aluno.getCep() 			+ "', "
                + "'" + aluno.getSexo() 		+ "', "
				+ ""+ 	aluno.getCurso().getCodigo() +");";

		cnx.atualize(sqlAtualize);
		
	}
	
	public void atualizar(Aluno aluno) 
	throws DataBaseNotConnectedException, DataBaseGenericException 
	{

		String sqlAtualize = "UPDATE alunos SET "
				+ "nome = '" + aluno.getNome() +"', "
				+ "fone = '" + 	aluno.getFone() + "', "
                + "endereco = '" + 	aluno.getEndereco() + "', "
                + "cep = '" + 	aluno.getCep()  + "', "
                + "sexo = '" + 	aluno.getSexo() + "', "
				+ "curso = "+ 	aluno.getCurso().getCodigo() +""
				+ " WHERE matricula = " + aluno.getMatricula() +";";

		cnx.atualize(sqlAtualize);
		
	}
	
	public void remover(Aluno aluno) 
	throws DataBaseNotConnectedException, DataBaseGenericException 
	{

		String sqlAtualize = "DELETE FROM alunos "
				+ " WHERE matricula = " + aluno.getMatricula() +";";

		cnx.atualize(sqlAtualize);
		
	}
	
	public Aluno recuperar(long matricula) 
	throws DataBaseNotConnectedException, DataBaseGenericException, 
		EntityNotExistsException 
	{
		
		String sqlConsulta = "SELECT matricula AS 'Matrícula', "
				+ "nome AS 'Nome', "
                + "fone AS 'Telefone', "
                + "endereco AS 'Endereço', "
				+ "cep AS 'CEP', "		
				+ "sexo AS 'Sexo', "
				+ "curso AS 'Curso' "
				+ "FROM alunos "
				+ "WHERE matricula = " + matricula +";";
		
		Aluno aluno = null;
		Curso curso = null;
		
		rs = cnx.consulte(sqlConsulta);
			
		try {
			if(rs.next()) {				
				curso = cdb.recuperar(rs.getInt(6));
				aluno = new Aluno(rs.getLong(1), rs.getString(2), 
						rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), curso);
			}else {
				throw new EntityNotExistsException("Aluno [matricula = " + matricula + "]");
			}
		} catch (SQLException e) {
			throw new DataBaseGenericException(e.getErrorCode(), e.getMessage());				
		}
		return aluno;
	}

	public List<Aluno> recuperarTodos() 
	throws DataBaseNotConnectedException, DataBaseGenericException, 
		EntityNotExistsException, EntityTableIsEmptyException 
	{
		
        String sqlConsulta = "SELECT matricula AS 'Matrícula', "
                + "nome AS 'Nome', "
                + "fone AS 'Telefone', "
                + "endereco AS 'Endereço', "
                + "cep AS 'CEP', "		
                + "sexo AS 'Sexo', "
				+ "curso AS 'Curso' "	
                + "FROM alunos;";

		List<Aluno> alunos = new ArrayList<Aluno>();
		Aluno aluno = null;
		Curso curso = null;
		
		rs = cnx.consulte(sqlConsulta);		
		
		try {
			while(rs.next()) {
				curso = cdb.recuperar(rs.getInt(6));
				aluno = new Aluno(rs.getLong(1), rs.getString(2), 
						rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), curso);
				alunos.add(aluno);
			}
		} catch (SQLException e) {
			throw new DataBaseGenericException(e.getErrorCode(), e.getMessage());			
		}
		if (alunos.size() < 1) {
			throw new EntityTableIsEmptyException("Aluno");
		}
		return alunos;
	}

	public List<Aluno> recuperarTodosPorNome()  
	throws DataBaseNotConnectedException, DataBaseGenericException, 
		EntityNotExistsException, EntityTableIsEmptyException 
	{
		
        String sqlConsulta = "SELECT matricula AS 'Matrícula', "
                + "nome AS 'Nome', "
                + "fone AS 'Telefone', "
                + "endereco AS 'Endereço', "
                + "cep AS 'CEP', "		
                + "sexo AS 'Sexo' "
                + "FROM alunos "
				+ "ORDER BY nome;";

		List<Aluno> alunos = new ArrayList<Aluno>();
		Aluno aluno = null;
		Curso curso = null;
		
		rs = cnx.consulte(sqlConsulta);		
		
		try {
			while(rs.next()) {
				curso = cdb.recuperar(rs.getInt(6));
				aluno = new Aluno(rs.getLong(1), rs.getString(2), 
						rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), curso);
				alunos.add(aluno);
			}
		} catch (SQLException e) {
			throw new DataBaseGenericException(e.getErrorCode(), e.getMessage());			
		}
		if (alunos.size() < 1) {
			throw new EntityTableIsEmptyException("Aluno");
		}
		return alunos;
	}
	
	public List<Aluno> recuperarTodosPorNomeContendo(String nome) 
	throws DataBaseNotConnectedException, DataBaseGenericException, 
		EntityNotExistsException 
	{
		
        String sqlConsulta = "SELECT matricula AS 'Matrícula', "
                + "nome AS 'Nome', "
                + "fone AS 'Telefone', "
                + "endereco AS 'Endereço', "
                + "cep AS 'CEP', "		
                + "sexo AS 'Sexo' "
                + "FROM alunos "
				+ "WHERE nome like '%" + nome + "%' "
				+ "ORDER BY nome;";

		List<Aluno> alunos = new ArrayList<Aluno>();
		Aluno aluno = null;
		Curso curso = null;
		
		rs = cnx.consulte(sqlConsulta);
		
		try {
			while(rs.next()) {
				
				curso = cdb.recuperar(rs.getInt(6));
				aluno = new Aluno(rs.getLong(1), rs.getString(2), 
						rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), curso);
				alunos.add(aluno);
			}
		} catch (SQLException e) {
			throw new DataBaseGenericException(e.getErrorCode(), e.getMessage());				
		}
		return alunos;
	}	
	
}
