package br.ufac.academico.logic;

import java.util.ArrayList;
import java.util.List;

import br.ufac.academico.db.Conexao;
import br.ufac.academico.db.CurriculoDB;
import br.ufac.academico.entity.Curso;
import br.ufac.academico.entity.Curriculo;
import br.ufac.academico.exception.DataBaseGenericException;
import br.ufac.academico.exception.DataBaseNotConnectedException;
import br.ufac.academico.exception.EntityAlreadyExistsException;
import br.ufac.academico.exception.EntityInvalidFieldsException;
import br.ufac.academico.exception.EntityNotExistsException;
import br.ufac.academico.exception.EntityTableIsEmptyException;

public class CurriculoLogic {
	private CurriculoDB curridp;
	private CursoLogic cl;

	public CurriculoLogic(Conexao cnx) {

		this.cl = new CursoLogic(cnx);
		this.curridp = new CurriculoDB(cnx);

	}
	
	public CurriculoLogic() {
		cl = new CursoLogic();
		curridp = new CurriculoDB();	
	}
	
	public void setConexao(Conexao cnx) {
		cl.setConexao(cnx);
		curridp.setConexao(cnx);
	}
	public void adicionar(long codigo, Curso curso, String descricao) 
	throws DataBaseNotConnectedException, DataBaseGenericException, 
		EntityAlreadyExistsException, EntityInvalidFieldsException
	{

		List<String> campos = new ArrayList<String>();
		Curriculo curriculo = null;

		if (codigo <= 0) {
			campos.add("Codigo = " + codigo + "");
		}
		
		if (descricao.isEmpty() || descricao.length() > 45) {
			campos.add("Descrição = '" + descricao + "'");
		} 
		
		try {
			curso = cl.recuperar(curso.getCodigo());
		} catch (EntityNotExistsException | NullPointerException e) {
			campos.add("Curso = " + curso );
		}
		if (campos.size() > 0) {
			throw new EntityInvalidFieldsException("Curriculos", campos);
		}
		try {
			curriculo  = recuperar(codigo);
			if(curriculo != null) {
				throw new EntityAlreadyExistsException("Curriculos [codigo = " + codigo + "]");
			}			
		} catch (EntityNotExistsException e) {
			curriculo = new Curriculo(codigo, curso, descricao);
			curridp.adicionar(curriculo);
		}

	}

	public Curriculo recuperar(long codigo) 
	throws DataBaseNotConnectedException, DataBaseGenericException, 
		EntityNotExistsException 
	{

		return curridp.recuperar(codigo); 		

	}

	public void atualizar(long codigo, Curso curso, String descricao) 
	throws DataBaseNotConnectedException, DataBaseGenericException, 
		EntityNotExistsException, EntityInvalidFieldsException
	{

		List<String> campos = new ArrayList<String>();
		Curriculo curriculo = null;
		
		if (descricao.isEmpty() || descricao.length() > 45) {
			campos.add("Descrição = '" + descricao + "'");
		}
		try {
			curso = cl.recuperar(curso.getCodigo());
		} catch (EntityNotExistsException | NullPointerException e) {
			campos.add("Curso = " + curso + "");
		}
		if (campos.size() > 0) {
			throw new EntityInvalidFieldsException("Curriculos", campos);
		}		
		curriculo  = recuperar(codigo);
		curriculo = new Curriculo(codigo,curso, descricao);
		curridp.atualizar(curriculo);
		
	}

	public void remover(long codigo) 
	throws DataBaseNotConnectedException, DataBaseGenericException, 
		EntityNotExistsException 
	{

		Curriculo curriculo = recuperar(codigo);
		curridp.remover(curriculo);

	}

	public List<Curriculo> recuperarTodos() 
	throws DataBaseNotConnectedException, DataBaseGenericException, 
		EntityNotExistsException, EntityTableIsEmptyException
	{

		return curridp.recuperarTodos();

	}

	public List<Curriculo> recuperarTodosPorNome() 
	throws DataBaseNotConnectedException, DataBaseGenericException, 
		EntityNotExistsException, EntityTableIsEmptyException
	{

		return curridp.recuperarTodosPorNome();

	}

	public List<Curriculo> recuperarTodosPorNomeContendo(String descricao) 
	throws DataBaseNotConnectedException, DataBaseGenericException, 
		EntityNotExistsException
	{

		return curridp.recuperarTodosPorNomeContendo(descricao);

	}	

}
