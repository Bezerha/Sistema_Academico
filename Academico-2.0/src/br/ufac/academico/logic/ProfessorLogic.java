package br.ufac.academico.logic;


import br.ufac.academico.db.Conexao;
import br.ufac.academico.db.ProfessorDB;
import br.ufac.academico.entity.Centro;
import br.ufac.academico.entity.Professor;
import br.ufac.academico.exception.DataBaseGenericException;
import br.ufac.academico.exception.DataBaseNotConnectedException;
import br.ufac.academico.exception.EntityAlreadyExistsException;
import br.ufac.academico.exception.EntityInvalidFieldsException;
import br.ufac.academico.exception.EntityNotExistsException;
import br.ufac.academico.exception.EntityTableIsEmptyException;

import java.util.*;

public class ProfessorLogic {

	private ProfessorDB pdb;
	private CentroLogic cl;

	public ProfessorLogic(Conexao cnx) {

		this.cl = new CentroLogic(cnx);
		this.pdb = new ProfessorDB(cnx);

	}
	
	public ProfessorLogic() {
		cl = new CentroLogic();
		pdb = new ProfessorDB();	
	}
	
	public void setConexao(Conexao cnx) {
		cl.setConexao(cnx);
		pdb.setConexao(cnx);
	}

	public void adicionar(long matricula, String nome, long rg, long cpf, 
		String endereco, String fone, Centro centro) 
	throws DataBaseNotConnectedException, DataBaseGenericException, 
		EntityAlreadyExistsException, EntityInvalidFieldsException
	{

		List<String> campos = new ArrayList<String>();
		Professor professor = null;

		if (matricula <= 0) {
			campos.add("Matrícula = " + matricula + "");
		}

		if (nome.isEmpty() || nome.length() > 50) {
			campos.add("Nome = '" + nome + "'");
		}
		if (rg <= 0) {
			campos.add("RG = " + rg + "");
		}
		if (cpf <= 0) {
			campos.add("CPF = " + cpf + "");
		}
		if (endereco.isEmpty() || endereco.length() > 60) {
			campos.add("Endereço = '" + endereco + "'");
		}
		if (fone.isEmpty() || fone.length() > 11) {
			campos.add("Telefone = '" + fone + "'");
		}

		try {
			centro = cl.recuperar(centro.getSigla());
		} catch (EntityNotExistsException | NullPointerException e) {
			campos.add("Centro = '" + centro + "'");
		}
		if (campos.size() > 0) {
			throw new EntityInvalidFieldsException("Professor", campos);
		}
		try {
			professor  = recuperar(matricula);
			if(professor != null) {
				throw new EntityAlreadyExistsException("Professor [matricula = " + matricula + "]");
			}			
		} catch (EntityNotExistsException e) {
			professor = new Professor(matricula, nome, rg, cpf, endereco, fone, centro);
			pdb.adicionar(professor);
		}

	}

	public Professor recuperar(long matricula) 
	throws DataBaseNotConnectedException, DataBaseGenericException, 
		EntityNotExistsException 
	{

		return pdb.recuperar(matricula); 		

	}

	public void atualizar(long matricula, String nome, long rg, long cpf, String endereco, String fone, Centro centro) 
	throws DataBaseNotConnectedException, DataBaseGenericException, 
		EntityNotExistsException, EntityInvalidFieldsException
	{

		List<String> campos = new ArrayList<String>();
		Professor professor = null;

		if (nome.isEmpty() || nome.length() > 50) {
			campos.add("Nome = '" + nome + "'");
		}
		if (rg <= 0) {
			campos.add("RG = " + rg + "");
		}
		if (cpf <= 0) {
			campos.add("CPF = " + cpf + "");
		}
		if (endereco.isEmpty() || endereco.length() > 60) {
			campos.add("Endereço = '" + endereco + "'");
		}
		if (fone.isEmpty() || fone.length() > 11) {
			campos.add("Telefone = '" + fone + "'");
		}

		try {
			centro = cl.recuperar(centro.getSigla());
		} catch (EntityNotExistsException | NullPointerException e) {
			campos.add("Centro = '" + centro + "'");
		}
		if (campos.size() > 0) {
			throw new EntityInvalidFieldsException("Professor", campos);
		}		
		professor  = recuperar(matricula);
		professor = new Professor(matricula, nome, rg, cpf, endereco, fone, centro);
		pdb.atualizar(professor);
		
	}

	public void remover(long matricula) 
	throws DataBaseNotConnectedException, DataBaseGenericException, 
		EntityNotExistsException 
	{

		Professor professor = recuperar(matricula);
		pdb.remover(professor);

	}

	public List<Professor> recuperarTodos() 
	throws DataBaseNotConnectedException, DataBaseGenericException, 
		EntityNotExistsException, EntityTableIsEmptyException
	{

		return pdb.recuperarTodos();

	}

	public List<Professor> recuperarTodosPorNome() 
	throws DataBaseNotConnectedException, DataBaseGenericException, 
		EntityNotExistsException, EntityTableIsEmptyException
	{

		return pdb.recuperarTodosPorNome();

	}

	public List<Professor> recuperarTodosPorNomeContendo(String nome) 
	throws DataBaseNotConnectedException, DataBaseGenericException, 
		EntityNotExistsException
	{

		return pdb.recuperarTodosPorNomeContendo(nome);

	}	

}
