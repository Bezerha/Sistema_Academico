package br.ufac.academico.gui;

import javax.swing.*;
import br.ufac.academico.db.*;
import br.ufac.academico.entity.*;
import br.ufac.academico.exception.DataBaseGenericException;
import br.ufac.academico.exception.DataBaseNotConnectedException;
import br.ufac.academico.exception.EntityNotExistsException;
import br.ufac.academico.exception.EntityTableIsEmptyException;
import br.ufac.academico.logic.*;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class ProfessorConsulta extends JFrame {

	Academico pai;
	ProfessorCadastro filho;
	ResultSet rs;

	private ProfessorLogic pl;

	JTable tblQuery;
	JPanel pnlSuperior, pnlControles, pnlBotoes, pnlOperacoes, pnlRotulos, pnlChaves;
	JComboBox<String> cmbChaves;
	JTextField fldValor;
	JButton btnBuscar, btnSair, btnIncluir, btnEditar, btnExcluir;

	AcaoBuscar actBuscar = new AcaoBuscar();
	AcaoIncluir actIncluir = new AcaoIncluir();
	AcaoEditar actEditar = new AcaoEditar();	
	AcaoExcluir actExcluir = new AcaoExcluir();	
	AcaoSair actSair = new AcaoSair();	

	ProfessorConsulta(Academico pai, Conexao cnx){
		super("Consulta de Professores");
		setSize(800, 400);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		this.pai = pai;

		filho = new ProfessorCadastro(this, cnx);
		pl = new ProfessorLogic(cnx);

		tblQuery = new JTable();
		tblQuery.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblQuery.addMouseListener(new HabilitarEdicaoExclusao());

		pnlRotulos = new JPanel(new GridLayout(2,1,5,5));
		pnlRotulos.add(new JLabel("Buscar por"));
		pnlRotulos.add(new JLabel("Valor"));

		cmbChaves = new JComboBox<String>(new String[] {"MatrÃ­cula", "Nome"});
		fldValor = new JTextField();

		pnlChaves = new JPanel(new GridLayout(2,1,5,5));
		pnlChaves.add(cmbChaves);
		pnlChaves.add(fldValor);

		pnlControles = new JPanel(new BorderLayout(5,5));
		pnlControles.add(pnlRotulos, BorderLayout.WEST);
		pnlControles.add(pnlChaves);

		btnBuscar = new JButton(actBuscar);
		btnSair = new JButton(actSair);
		btnIncluir = new JButton(actIncluir);
		btnEditar = new JButton(actEditar);
		btnExcluir = new JButton(actExcluir);


		pnlOperacoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
		pnlOperacoes.add(btnIncluir);
		pnlOperacoes.add(btnEditar);
		pnlOperacoes.add(btnExcluir);

		pnlBotoes = new JPanel(new GridLayout(2,1));
		pnlBotoes.add(btnBuscar);
		pnlBotoes.add(btnSair);

		pnlSuperior = new JPanel(new BorderLayout());
		pnlSuperior.add(pnlBotoes, BorderLayout.EAST);
		pnlSuperior.add(pnlControles);

		add(pnlSuperior, BorderLayout.NORTH);
		add(new JScrollPane(tblQuery));
		add(pnlOperacoes, BorderLayout.SOUTH);
		setLocationRelativeTo(null);

	}

	class AcaoBuscar extends AbstractAction{

		AcaoBuscar(){
			super("Buscar");
			putValue(MNEMONIC_KEY, KeyEvent.VK_B);
			putValue(SHORT_DESCRIPTION, 
					"Buscar registro de professor!");
			putValue(SMALL_ICON, 
					new ImageIcon("images/general/Search24.gif"));

		}

		@Override
		public void actionPerformed(ActionEvent e) {

			buscar();

		}

	}

	class AcaoIncluir extends AbstractAction{

		AcaoIncluir(){
			super("Incluir");
			putValue(MNEMONIC_KEY, KeyEvent.VK_I);
			putValue(SHORT_DESCRIPTION, 
					"Incluir registro de professor!");
			putValue(SMALL_ICON, 
					new ImageIcon("images/general/New24.gif"));

		}

		@Override
		public void actionPerformed(ActionEvent e) {

			filho.incluir();

		}

	}

	class AcaoEditar extends AbstractAction{

		AcaoEditar(){
			super("Editar");
			putValue(MNEMONIC_KEY, KeyEvent.VK_E);
			putValue(SHORT_DESCRIPTION, 
					"Editar registro de professor!");
			putValue(SMALL_ICON, 
					new ImageIcon("images/general/Edit24.gif"));

			setEnabled(false);
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			long matricula = (long)tblQuery.getValueAt(tblQuery.getSelectedRow(), 0);
			filho.editar(matricula);

		}

	}

	class AcaoExcluir extends AbstractAction{

		AcaoExcluir(){
			super("Excluir");
			putValue(MNEMONIC_KEY, KeyEvent.VK_X);
			putValue(SHORT_DESCRIPTION, 
					"Excluir registro de professor!");
			putValue(SMALL_ICON, 
					new ImageIcon("images/general/Delete24.gif"));
			setEnabled(false);
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			long matricula = (long)tblQuery.getValueAt(tblQuery.getSelectedRow(), 0);
			filho.excluir(matricula);

		}

	}	

	class AcaoSair extends AbstractAction{

		AcaoSair(){
			super("Voltar");
			putValue(MNEMONIC_KEY, KeyEvent.VK_R);
			putValue(SHORT_DESCRIPTION, 
					"Voltar ao Menu Principal!");
			putValue(SMALL_ICON, 
					new ImageIcon("images/navigation/Back24.gif"));

		}

		@Override
		public void actionPerformed(ActionEvent e) {

			ProfessorConsulta.this.setVisible(false);
			pai.setVisible(true);

		}

	}


	class HabilitarEdicaoExclusao extends MouseAdapter {

		public void mousePressed(MouseEvent e) {
			if (tblQuery.getSelectedRow() >= 0) {
				actEditar.setEnabled(true);
				actExcluir.setEnabled(true);
			}else {
				actEditar.setEnabled(false);
				actExcluir.setEnabled(false);
			}

		}

	}


	public void buscar() {

		List<Professor> professores = new ArrayList<Professor>();

		try {

			if (fldValor.getText().isEmpty()) {
				professores = pl.recuperarTodosPorNome();
			}else{

				String chaveDeBusca; 
				chaveDeBusca = fldValor.getText();			

				switch (cmbChaves.getSelectedIndex()) {
				case 0:			
					professores.add(pl.recuperar(Long.parseLong(chaveDeBusca)));
					break;
				case 1:		
					professores = pl.recuperarTodosPorNomeContendo(chaveDeBusca);
					break;
				}
			}	

			tblQuery.setModel(new ProfessorTableModel(professores));
			actEditar.setEnabled(false);
			actExcluir.setEnabled(false);
		} catch (NumberFormatException nfe) {
			// EXCEÃ‡ÃƒO LEVANTADA PELO parseLong
			JOptionPane.showMessageDialog(ProfessorConsulta.this, 
					"A Chave de Busca Matrícula deve conter apenas números!", "Academico", JOptionPane.ERROR_MESSAGE);
		} catch (DataBaseNotConnectedException | DataBaseGenericException | 
				EntityNotExistsException | EntityTableIsEmptyException e) {
			JOptionPane.showMessageDialog(ProfessorConsulta.this, e.getMessage(),
					"Academico", JOptionPane.ERROR_MESSAGE);
		}
	}	

}



