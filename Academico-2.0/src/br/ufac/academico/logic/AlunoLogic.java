package br.ufac.academico.logic;

import br.ufac.academico.db.Conexao;
import br.ufac.academico.db.AlunoDB;
import br.ufac.academico.entity.Curso;
import br.ufac.academico.entity.Aluno;
import br.ufac.academico.exception.DataBaseGenericException;
import br.ufac.academico.exception.DataBaseNotConnectedException;
import br.ufac.academico.exception.EntityAlreadyExistsException;
import br.ufac.academico.exception.EntityInvalidFieldsException;
import br.ufac.academico.exception.EntityNotExistsException;
import br.ufac.academico.exception.EntityTableIsEmptyException;

import java.util.*;

public class AlunoLogic {

	private AlunoDB adb;
	private CursoLogic curl;

	public AlunoLogic(Conexao cnx) {

		this.curl = new CursoLogic(cnx);
		this.adb = new AlunoDB(cnx);

	}
	
	public AlunoLogic() {
		curl = new CursoLogic();
		adb = new AlunoDB();	
	}
	
	public void setConexao(Conexao cnx) {
		curl.setConexao(cnx);
		adb.setConexao(cnx);
	}

	public void adicionar(long matricula, String nome, String fone, String endereco, String cep, String sexo,
			Curso curso) throws DataBaseNotConnectedException, DataBaseGenericException, EntityAlreadyExistsException,
			EntityInvalidFieldsException {

		List<String> campos = new ArrayList<String>();
		Aluno aluno = null;

		if (matricula <= 0) {
			campos.add("Matrícula = " + matricula + "");
		}
		if (nome.isEmpty() || nome.length() > 50) {
			campos.add("Nome = '" + nome + "'");
		}
		if (fone.isEmpty() || fone.length() > 11) {
			campos.add("Fone = '" + fone + "'");
		}
		if (endereco.isEmpty() || endereco.length() > 60) {
			campos.add("Endereço = '" + endereco + "'");
		}
		if (cep.isEmpty() || cep.length() > 7) {
			campos.add("CEP = '" + cep + "'");
		}
		if (sexo.isEmpty() || sexo.length() > 1) {
			campos.add("Sexo = '" + sexo + "'");
		}
		try {
			curso = curl.recuperar(curso.getCodigo());
		} catch (EntityNotExistsException | NullPointerException e) {
			campos.add("Curso = " + curso + "");
		}
		if (campos.size() > 0) {
			throw new EntityInvalidFieldsException("Aluno", campos);
		}
		try {
			aluno = recuperar(matricula);
			if (aluno != null) {
				throw new EntityAlreadyExistsException("aluno [matricula = " + matricula + "]");
			}
		} catch (EntityNotExistsException e) {
			aluno = new Aluno(matricula, nome, fone, endereco, cep, sexo, curso);
			adb.adicionar(aluno);
		}

	}

	public Aluno recuperar(long matricula)
			throws DataBaseNotConnectedException, DataBaseGenericException, EntityNotExistsException {

		return adb.recuperar(matricula);

	}

	public void atualizar(long matricula, String nome, String fone, String endereco, String cep, String sexo,
			Curso curso) throws DataBaseNotConnectedException, DataBaseGenericException, EntityAlreadyExistsException,
			EntityInvalidFieldsException, EntityNotExistsException 
	{

		List<String> campos = new ArrayList<String>();
		Aluno aluno = null;

		if (nome.isEmpty() || nome.length() > 50) {
			campos.add("Nome = '" + nome + "'");
		}
		if (fone.isEmpty() || fone.length() > 11) {
			campos.add("Fone = '" + fone + "'");
		}
		if (endereco.isEmpty() || endereco.length() > 60) {
			campos.add("Endereço = '" + endereco + "'");
		}
		if (cep.isEmpty() || cep.length() > 7) {
			campos.add("CEP = '" + cep + "'");
		}
		if (sexo.isEmpty() || sexo.length() > 1) {
			campos.add("Sexo = '" + sexo + "'");
		}
		try {
			curso = curl.recuperar(curso.getCodigo());
		} catch (EntityNotExistsException | NullPointerException e) {
			campos.add("Curso = " + curso + "");
		}
		if (campos.size() > 0) {
			throw new EntityInvalidFieldsException("Aluno", campos);
		}
		aluno = recuperar(matricula);
		aluno = new Aluno(matricula, nome, fone, endereco, cep, sexo, curso);
		adb.atualizar(aluno);

	}

	public void remover(long matricula)
			throws DataBaseNotConnectedException, DataBaseGenericException, EntityNotExistsException {

		Aluno aluno = recuperar(matricula);
		adb.remover(aluno);

	}

	public List<Aluno> recuperarTodos() throws DataBaseNotConnectedException, DataBaseGenericException,
			EntityNotExistsException, EntityTableIsEmptyException {

		return adb.recuperarTodos();

	}

	public List<Aluno> recuperarTodosPorNome() throws DataBaseNotConnectedException, DataBaseGenericException,
			EntityNotExistsException, EntityTableIsEmptyException {

		return adb.recuperarTodosPorNome();

	}

	public List<Aluno> recuperarTodosPorNomeContendo(String nome)
			throws DataBaseNotConnectedException, DataBaseGenericException, EntityNotExistsException {

		return adb.recuperarTodosPorNomeContendo(nome);

	}

}