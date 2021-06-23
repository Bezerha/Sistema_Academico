package br.ufac.academico.gui;

import br.ufac.academico.db.*;
import br.ufac.academico.entity.Centro;
import br.ufac.academico.exception.DataBaseGenericException;
import br.ufac.academico.exception.DataBaseNotConnectedException;
import br.ufac.academico.exception.EntityNotExistsException;
import br.ufac.academico.exception.EntityTableIsEmptyException;
import br.ufac.academico.logic.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class CentroConsulta extends JFrame {

	private Academico pai;
	private CentroCadastro filho;
	private Conexao cnx;
	private ResultSet rs;

	private CentroLogic cl;

	private JTable tblQuery;
	private JPanel pnlSuperior, pnlControles, pnlBotoes, pnlOperacoes, pnlRotulos, pnlChaves;
	private JComboBox<String> cmbChaves;
	private JTextField fldValor;
	private JButton btnBuscar, btnSair, btnIncluir, btnEditar, btnExcluir;

	private AcaoBuscar actBuscar = new AcaoBuscar();
	private AcaoIncluir actIncluir = new AcaoIncluir();
	private AcaoEditar actEditar = new AcaoEditar();	
	private AcaoExcluir actExcluir = new AcaoExcluir();	
	private AcaoSair actSair = new AcaoSair();	

	CentroConsulta(Academico pai, Conexao cnx){
		super("Consulta de Centros");
		setSize(800, 400);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		this.pai = pai;
		this.cnx = cnx;
		filho = new CentroCadastro(this, cnx);
		cl = new CentroLogic(cnx);

		tblQuery = new JTable();
		tblQuery.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblQuery.addMouseListener(new HabilitarEdicaoExclusao());

		pnlRotulos = new JPanel(new GridLayout(2,1,5,5));
		pnlRotulos.add(new JLabel("Buscar por"));
		pnlRotulos.add(new JLabel("Valor"));

		cmbChaves = new JComboBox<String>(new String[] {"Sigla", "Nome"});
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
					"Buscar registros de centros!");
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

			String sigla = tblQuery.getValueAt(tblQuery.getSelectedRow(), 0).toString();
			filho.editar(sigla);

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

			String sigla = tblQuery.getValueAt(tblQuery.getSelectedRow(), 0).toString();
			filho.excluir(sigla);

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
			CentroConsulta.this.setVisible(false);
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

		List<Centro> centros = new ArrayList<Centro>();

		try {		

			if (fldValor.getText().isEmpty()) {
				centros = cl.recuperarTodosPorNome();
			}else{

				String chaveDeBusca; 
				chaveDeBusca = fldValor.getText();			

				switch (cmbChaves.getSelectedIndex()) {
				case 0:
					centros.add(cl.recuperar(chaveDeBusca));
					break;
				case 1:		
					centros = cl.recuperarTodosPorNomeContendo(chaveDeBusca);
					break;
				}
			}

			tblQuery.setModel(new CentroTableModel(centros));
			actEditar.setEnabled(false);
			actExcluir.setEnabled(false);
		} catch (DataBaseNotConnectedException | DataBaseGenericException | 
				EntityNotExistsException | EntityTableIsEmptyException e) {
			JOptionPane.showMessageDialog(CentroConsulta.this, e.getMessage(),
					"Academico", JOptionPane.ERROR_MESSAGE);
		}
	}	

}



