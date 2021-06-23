package br.ufac.academico.gui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.ufac.academico.entity.Curriculo;

public class CurriculoTableModel extends AbstractTableModel {
	private List<Curriculo> curriculo;

	public CurriculoTableModel(List<Curriculo> curriculo) {
		this.curriculo = curriculo;
	}

	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public int getRowCount() {
		return curriculo.size();
	}

	@Override
	public String getColumnName(int column) {

		String nomeDaColuna;

		switch (column) {
			case 0:
				nomeDaColuna = "Codigo";
				break;
			case 1:
				nomeDaColuna = "Curso";
				break;
			case 2:
				nomeDaColuna = "Descrição";
				break;

			default:
				nomeDaColuna = "";
				break;
		}

		return nomeDaColuna;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		Object valor = null;
		Curriculo c = curriculo.get(rowIndex);

		switch (columnIndex) {
			case 0:
				valor = c.getCodigo();
				break;
			case 1:
				valor = c.getCurso();
				break;
			case 2:
				valor = c.getDescricao();
				break;

			default:
				valor = null;
				break;
		}
		return valor;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {

		// return getValueAt(0, columnIndex).getClass();

		// PESSOAL, TEMOS QUE EXPLICITAR DIRETAMENTE AS CLASS, SEGUINDO
		// A MESMA LÃ“GICA QUE USAMOS PARA DEVOLVER OS NOMES DA COLUNAS
		// POIS, OS DADOS PODEM SER NULOS, COMO ESTAVAM NO FONE E ISSO
		// QUE QUEBRA O FUNCIONAMENTO DO CÃ“DIGO ACIMA,COM 'getValueAt'

		Object obj;

		switch (columnIndex) {
			case 0:
				obj = Long.class;
				break;
			case 1:
				obj = int.class;
				break;
			case 2:
				obj = String.class;
				break;
			default:
				obj = null;
				break;
		}

		return obj.getClass();

	}

}
