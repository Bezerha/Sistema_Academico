package br.ufac.academico.gui;
import java.sql.*;
import javax.swing.table.*;

public class ResultSetTableModel extends AbstractTableModel{

	private ResultSet rs;
	private ResultSetMetaData rsmd;
	private int linhas, colunas;

	public ResultSetTableModel(ResultSet rs) {

		try {
			rsmd  = rs.getMetaData();
			colunas = rsmd.getColumnCount();
			rs.last();
			linhas = rs.getRow();
			rs.beforeFirst();
			this.rs = rs;
		} catch (SQLException e) {
			System.out.printf("Falha ao se comunicar com o servidor: %s\n", e.getMessage());
		}
		
	}
	
	@Override
	public int getColumnCount() {
		return colunas;
	}

	@Override
	public int getRowCount() {
		return linhas;
	}

	@Override
	public String getColumnName(int column) {

		String nomeDaColuna="";

		try {
			nomeDaColuna = rsmd.getColumnLabel(column+1);
		} catch (SQLException e) {
			System.out.printf("Falha ao se comunicar com o servidor: %s\n", e.getMessage());
		}
		
		return nomeDaColuna;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		Object valor = null;
		
		try {
			rs.absolute(rowIndex+1);
			valor = rs.getObject(columnIndex+1);		
		} catch (SQLException e) {
			System.out.printf("Falha ao se comunicar com o servidor: %s\n", e.getMessage());
		}
		
		return valor;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
 
		return getValueAt(0, columnIndex).getClass();
		
	}

}
