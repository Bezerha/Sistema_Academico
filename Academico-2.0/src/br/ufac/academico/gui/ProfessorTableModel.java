package br.ufac.academico.gui;

import br.ufac.academico.entity.*;

import java.util.*;
import javax.swing.table.*;

public class ProfessorTableModel extends AbstractTableModel{

	private List<Professor> professores;

	public ProfessorTableModel(List<Professor> professores) {
		this.professores = professores;
	}

	@Override
	public int getColumnCount() {
		return 7;
	}

	@Override
	public int getRowCount() {
		return professores.size();
	}

	@Override
	public String getColumnName(int column) {

		String nomeDaColuna;

		switch (column) {
		case 0:
			nomeDaColuna = "Matrícula";
			break;
		case 1:
			nomeDaColuna = "Nome";
			break;
		case 2:
			nomeDaColuna = "RG";
			break;
		case 3:
			nomeDaColuna = "CPF";
			break;
		case 4:
			nomeDaColuna = "Endereço";
			break;
		case 5:
			nomeDaColuna = "Telefone";
			break;
		case 6:
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
		Professor p = professores.get(rowIndex);

		switch (columnIndex) {
		case 0:
			valor = p.getMatricula();  
			break;
		case 1:
			valor = p.getNome();
			break;
		case 2:
			valor = p.getRg();
			break;
		case 3:
			valor = p.getCpf();
			break;
		case 4:
			valor = p.getEndereco();
			break;
		case 5:
			valor = p.getFone();
			break;
		case 6:
			valor = p.getCentro().getSigla();
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
		// A MESMA LÃ“GICA QUE USAMOS PARA DEVOLVER OS NOMES DA COLUNAS
		// POIS, OS DADOS PODEM SER NULOS, COMO ESTAVAM NO FONE E ISSO
		// QUE QUEBRA O FUNCIONAMENTO DO CÃ“DIGO ACIMA,COM 'getValueAt'
		
		Object obj;

		switch (columnIndex) {
			case 0: obj = Long.class; break;
			case 1: obj = String.class; break;
			case 2: obj = Long.class; break;
			case 3: obj = Long.class; break;
			case 4: obj = String.class; break;
			case 5: obj = String.class; break;
			case 6: obj = String.class; break;			
			default: obj = null; break;
		}		

		return obj.getClass();		

	}

}
