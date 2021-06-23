package br.ufac.academico.gui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.ufac.academico.entity.Curso;

public class CursoTableModel extends AbstractTableModel {
	private List<Curso> cursos;

	public CursoTableModel(List<Curso> cursos) {
		this.cursos = cursos;
	}
	
	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public int getRowCount() {
		return cursos.size();
	}

	@Override
	public String getColumnName(int column) {

		String nomeDaColuna;
		
		switch (column) {
		case 0:
			nomeDaColuna = "Codigo";
			break;
		case 1:
			nomeDaColuna = "Nome";
			break;
		default:
			nomeDaColuna="";
			break;
		}
		
		return nomeDaColuna;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		Object valor = null;
		Curso curso = cursos.get(rowIndex);
		
		switch (columnIndex) {
		case 0:
			valor = curso.getCodigo();  
			break;
		case 1:
			valor = curso.getNome();
			break;
		default:
			valor = null;
			break;
		}
		
		return valor;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
 
		//return getValueAt(0, columnIndex).getClass();
		
		// PESSOAL, TEMOS QUE EXPLICITAR DIRETAMENTE AS CLASS, SEGUINDO
		// A MESMA LÓGICA QUE USAMOS PARA DEVOLVER OS NOMES DA COLUNAS
		// POIS, OS DADOS PODEM SER NULOS, COMO ESTAVAM NO FONE E ISSO
		// QUE QUEBRA O FUNCIONAMENTO DO CÓDIGO ACIMA,COM 'getValueAt'
		
		Object obj;
		
		switch (columnIndex) {
			case 0: obj = int.class; break;
			case 1: obj = String.class; break;
			default: obj = null; break;
		}		

		return obj.getClass();
		
	}
}
