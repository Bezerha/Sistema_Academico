package br.ufac.academico.gui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.ufac.academico.entity.Disciplina;

public class DisciplinaTableModel extends AbstractTableModel{
	private List<Disciplina> disciplina;

	public DisciplinaTableModel(List<Disciplina> disciplina) {
		this.disciplina = disciplina;
	}

	@Override
	public int getColumnCount() {
		return 4;
	}

	@Override
	public int getRowCount() {
		return disciplina.size();
	}

	@Override
	public String getColumnName(int column) {

		String nomeDaColuna;

		switch (column) {
		case 0:
			nomeDaColuna = "C�digo";
			break;
		case 1:
			nomeDaColuna = "Nome";
			break;
		case 2:
			nomeDaColuna = "Ch";
			break;
		case 3:
			nomeDaColuna = "Centro";
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
		Disciplina p = disciplina.get(rowIndex);

		switch (columnIndex) {
		case 0:
			valor = p.getCodigo();  
			break;
		case 1:
			valor = p.getNome();
			break;
		case 2:
			valor = p.getCh();
			break;
		case 3:
			valor = p.getCentro();
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
			case 0: obj = String.class; break;
			case 1: obj = String.class; break;
			case 2: obj = int.class; break;
			case 3: obj = String.class; break;	
			default: obj = null; break;
		}		

		return obj.getClass();		

	}

}
