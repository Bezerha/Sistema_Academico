package br.ufac.academico.logic;

import br.ufac.academico.db.Conexao;
import br.ufac.academico.db.DisciplinaDB;
import br.ufac.academico.entity.Centro;
import br.ufac.academico.entity.Disciplina;
import br.ufac.academico.exception.DataBaseGenericException;
import br.ufac.academico.exception.DataBaseNotConnectedException;
import br.ufac.academico.exception.EntityAlreadyExistsException;
import br.ufac.academico.exception.EntityInvalidFieldsException;
import br.ufac.academico.exception.EntityNotExistsException;
import br.ufac.academico.exception.EntityTableIsEmptyException;

import java.util.*;

public class DisciplinaLogic {

	private DisciplinaDB ddb;
	private CentroLogic cl;

	public DisciplinaLogic(Conexao cnx) {

		this.cl = new CentroLogic(cnx);
		this.ddb = new DisciplinaDB(cnx);

	}
	
	public DisciplinaLogic() {
		cl = new CentroLogic();
		ddb = new DisciplinaDB();	
	}
	
	public void setConexao(Conexao cnx) {
		cl.setConexao(cnx);
		ddb.setConexao(cnx);
	}

	public void adicionar(String codigo, String nome, int ch, 
			Centro centro) 
	throws DataBaseNotConnectedException, DataBaseGenericException, 
		EntityAlreadyExistsException, EntityInvalidFieldsException
	{

		List<String> campos = new ArrayList<String>();
		Disciplina disciplina = null;
		if (codigo.isEmpty() || codigo.length() > 8) {
			campos.add("Código = '" + codigo + "'");
		}
		if (nome.isEmpty() || nome.length() > 60) {
			campos.add("Nome = '" + nome + "'");
		}
		if (ch <= 0) {
			campos.add("Ch = " + ch );
		}
		try {
			centro = cl.recuperar(centro.getSigla());
		} catch (EntityNotExistsException | NullPointerException e) {
			campos.add("Centro = '" + centro + "'");
		}
		if (campos.size() > 0) {
			throw new EntityInvalidFieldsException("Disciplina", campos);
		}
		try {
			disciplina  = recuperar(codigo);
			if(disciplina != null) {
				throw new EntityAlreadyExistsException("Disciplina [codigo = " + codigo + "]");
			}			
		} catch (EntityNotExistsException e) {
			disciplina = new Disciplina (codigo, nome, ch, centro);
			ddb.adicionar(disciplina);
		}

	}

	public Disciplina recuperar(String codigo) 
	throws DataBaseNotConnectedException, DataBaseGenericException, 
		EntityNotExistsException 
	{

		return ddb.recuperar(codigo); 		

	}

	public void atualizar(String codigo, String nome, int ch, Centro centro) 
	throws DataBaseNotConnectedException, DataBaseGenericException, 
		EntityNotExistsException, EntityInvalidFieldsException
	{

		List<String> campos = new ArrayList<String>();
		Disciplina disciplina = null;

		
		if (nome.isEmpty() || nome.length() > 50) {
			campos.add("Nome = '" + nome + "'");
		}
		if (ch <= 0) {
			campos.add("Ch = " + ch + "");
		}
		try {
			centro = cl.recuperar(centro.getSigla());
		} catch (EntityNotExistsException | NullPointerException e) {
			campos.add("Centro = '" + centro + "'");
		}
		if (campos.size() > 0) {
			throw new EntityInvalidFieldsException("Disciplina", campos);
		}

		try {
			centro = cl.recuperar(centro.getSigla());
		} catch (EntityNotExistsException | NullPointerException e) {
			campos.add("Centro = '" + centro + "'");
		}
		if (campos.size() > 0) {
			throw new EntityInvalidFieldsException("Disciplina", campos);
		}		
		disciplina = recuperar(codigo);
		disciplina = new Disciplina (codigo, nome, ch, centro);
		ddb.atualizar(disciplina);
		}
		

	public void remover(String codigo) 
	throws DataBaseNotConnectedException, DataBaseGenericException, 
		EntityNotExistsException 
	{

		Disciplina disciplina = recuperar(codigo);
		ddb.remover(disciplina);

	}

	public List<Disciplina> recuperarTodos() 
	throws DataBaseNotConnectedException, DataBaseGenericException, 
		EntityNotExistsException, EntityTableIsEmptyException
	{

		return ddb.recuperarTodos();

	}

	public List<Disciplina> recuperarTodosPorNome() 
	throws DataBaseNotConnectedException, DataBaseGenericException, 
		EntityNotExistsException, EntityTableIsEmptyException
	{

		return ddb.recuperarTodosPorNome();

	}

	public List<Disciplina> recuperarTodosPorNomeContendo(String nome) 
	throws DataBaseNotConnectedException, DataBaseGenericException, 
		EntityNotExistsException
	{

		return ddb.recuperarTodosPorNomeContendo(nome);

	}	

}

