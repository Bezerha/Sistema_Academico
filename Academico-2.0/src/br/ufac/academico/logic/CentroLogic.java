package br.ufac.academico.logic;

import br.ufac.academico.db.*;
import br.ufac.academico.entity.Centro;
import br.ufac.academico.exception.DataBaseGenericException;
import br.ufac.academico.exception.DataBaseNotConnectedException;
import br.ufac.academico.exception.EntityAlreadyExistsException;
import br.ufac.academico.exception.EntityInvalidFieldsException;
import br.ufac.academico.exception.EntityNotExistsException;
import br.ufac.academico.exception.EntityTableIsEmptyException;

import java.util.*;

public class CentroLogic {

	private CentroDB cdb;
	
	public CentroLogic(Conexao cnx) {

		cdb = new CentroDB(cnx);
		
	}
	
	public CentroLogic() {
		cdb = new CentroDB();				
	}

	public void setConexao(Conexao cnx) {
		cdb.setConexao(cnx);
	}
	
	public void adicionar(String sigla, String nome) 
	throws DataBaseNotConnectedException, DataBaseGenericException, 
		EntityAlreadyExistsException, EntityInvalidFieldsException 
	{
		List<String> campos = new ArrayList<String>();
		Centro centro = null;
		
		if (sigla.isEmpty() || sigla.length() > 5) {
			campos.add("Sigla = '" + sigla + "'");
		}
		if (nome.isEmpty() || nome.length() > 60) {
			campos.add("Nome = '" + nome + "'");
		}
		
		if (campos.size() > 0) {
			throw new EntityInvalidFieldsException("Centro", campos);
		}
		
		try {
			centro  = recuperar(sigla);
			if(centro != null) {
				throw new EntityAlreadyExistsException("Centro [sigla = '" + sigla + "']");
			}			
		} catch (EntityNotExistsException e) {
			centro = new Centro(sigla, nome);
			cdb.adicionar(centro);
		}
	}
	
	public Centro recuperar(String sigla) 
	throws DataBaseNotConnectedException, DataBaseGenericException, 
		EntityNotExistsException 
	{

		return cdb.recuperar(sigla); 		
		
	}

	public void atualizar(String sigla, String nome)  
	throws DataBaseNotConnectedException, DataBaseGenericException, 
		EntityInvalidFieldsException, EntityNotExistsException 
	{
		List<String> campos = new ArrayList<String>();
		Centro centro = null;
		
		if (nome.isEmpty() || nome.length() > 60) {
			campos.add("Nome = '" + nome + "'");
		}
		
		if (campos.size() > 0) {
			throw new EntityInvalidFieldsException("Centro", campos);
		}
		
		centro  = recuperar(sigla);
		centro = new Centro(sigla, nome);
		cdb.atualizar(centro);

	}
	
	public void remover(String sigla) 
	throws DataBaseNotConnectedException, DataBaseGenericException,
		EntityNotExistsException
	{

			Centro centro  = recuperar(sigla);
			cdb.remover(centro);						
		
	}

	public List<Centro> recuperarTodos() 
	throws DataBaseNotConnectedException, DataBaseGenericException, 
		EntityTableIsEmptyException
	{

		return cdb.recuperarTodos();
		
	}

	public List<Centro> recuperarTodosPorNome() 
	throws DataBaseNotConnectedException, DataBaseGenericException, 
		EntityTableIsEmptyException
	{

		return cdb.recuperarTodosPorNome();
		
	}
	
	public List<Centro> recuperarTodosPorNomeContendo(String nome) 
		throws DataBaseNotConnectedException, DataBaseGenericException
	{

		return cdb.recuperarTodosPorNomeContendo(nome);
		
	}	
	
}
