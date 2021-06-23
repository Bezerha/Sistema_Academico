package br.ufac.academico.logic;

import java.util.ArrayList;
import java.util.List;

import br.ufac.academico.db.*;
import br.ufac.academico.entity.Curso;
import br.ufac.academico.exception.DataBaseGenericException;
import br.ufac.academico.exception.DataBaseNotConnectedException;
import br.ufac.academico.exception.EntityAlreadyExistsException;
import br.ufac.academico.exception.EntityInvalidFieldsException;
import br.ufac.academico.exception.EntityNotExistsException;
import br.ufac.academico.exception.EntityTableIsEmptyException;

public class CursoLogic {

	private CursoDB cdb;

	public CursoLogic(Conexao cnx) {

		this.cdb = new CursoDB(cnx);

	}
	
	public CursoLogic() {
		cdb = new CursoDB();				
	}

	public void setConexao(Conexao cnx) {
		cdb.setConexao(cnx);
	}

	public void adicionar(int codigo, String nome) 
			throws DataBaseNotConnectedException, DataBaseGenericException, 
				EntityAlreadyExistsException, EntityInvalidFieldsException 
			{
				List<String> campos = new ArrayList<String>();
				Curso curso = null;
				
				if (codigo <=0) {
					campos.add("Sigla = '" + codigo + "'");
				}
				if (nome.isEmpty() || nome.length() > 50) {
					campos.add("Nome = '" + nome + "'");
				}
				
				if (campos.size() > 0) {
					throw new EntityInvalidFieldsException("Curso", campos);
				}
				
				try {
					curso  = recuperar(codigo);
					if(curso != null) {
						throw new EntityAlreadyExistsException("Curso [codigo = '" + codigo + "']");
					}			
				} catch (EntityNotExistsException e) {
					curso = new Curso(codigo, nome);
					cdb.adicionar(curso);
				}
			}
			
			public Curso recuperar(int codigo) 
			throws DataBaseNotConnectedException, DataBaseGenericException, 
				EntityNotExistsException 
			{

				return cdb.recuperar(codigo);
				
			}

			public void atualizar(int codigo, String nome)  
			throws DataBaseNotConnectedException, DataBaseGenericException, 
				EntityInvalidFieldsException, EntityNotExistsException 
			{
				List<String> campos = new ArrayList<String>();
				Curso curso = null;
				
				if (nome.isEmpty() || nome.length() > 50) {
					campos.add("Nome = '" + nome + "'");
				}
				
				if (campos.size() > 0) {
					throw new EntityInvalidFieldsException("Curso", campos);
				}
				
				curso = recuperar(codigo);
				curso = new Curso(codigo, nome);
				cdb.atualizar(curso);

			}
			
			public void remover(int codigo) 
			throws DataBaseNotConnectedException, DataBaseGenericException,
				EntityNotExistsException
			{

					Curso curso = recuperar(codigo);
					cdb.remover(curso);						
				
			}

			public List<Curso> recuperarTodos() 
			throws DataBaseNotConnectedException, DataBaseGenericException, 
				EntityTableIsEmptyException
			{

				return cdb.recuperarTodos();
				
			}

			public List<Curso> recuperarTodosPorNome() 
			throws DataBaseNotConnectedException, DataBaseGenericException, 
				EntityTableIsEmptyException
			{

				return cdb.recuperarTodosPorNome();
				
			}
			
			public List<Curso> recuperarTodosPorNomeContendo(String nome) 
				throws DataBaseNotConnectedException, DataBaseGenericException
			{

				return cdb.recuperarTodosPorNomeContendo(nome);
				
			}	
}
