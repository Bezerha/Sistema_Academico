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

class DisciplinaConsulta extends JFrame {

	Academico pai;
	DisciplinaCadastro filho;
	ResultSet rs;

	private DisciplinaLogic pl;

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

	DisciplinaConsulta(Academico pai, Conexao cnx) {
		super("Consulta de Disciplinas");
		setSize(800, 400);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		this.pai = pai;

		filho = new DisciplinaCadastro(this, cnx);
		pl = new DisciplinaLogic(cnx);

		tblQuery = new JTable();
		tblQuery.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblQuery.addMouseListener(new HabilitarEdicaoExclusao());

		pnlRotulos = new JPanel(new GridLayout(2, 1, 5, 5));
		pnlRotulos.add(new JLabel("Buscar por"));
		pnlRotulos.add(new JLabel("Valor"));

		cmbChaves = new JComboBox<String>(new String[] { "Código", "Nome" });
		fldValor = new JTextField();

		pnlChaves = new JPanel(new GridLayout(2, 1, 5, 5));
		pnlChaves.add(cmbChaves);
		pnlChaves.add(fldValor);

		pnlControles = new JPanel(new BorderLayout(5, 5));
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

		pnlBotoes = new JPanel(new GridLayout(2, 1));
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

	class AcaoBuscar extends AbstractAction {

		AcaoBuscar() {
			super("Buscar");
			putValue(MNEMONIC_KEY, KeyEvent.VK_B);
			putValue(SHORT_DESCRIPTION, "Buscar registro de disciplina!");
			putValue(SMALL_ICON, new ImageIcon("images/general/Search24.gif"));

		}

		@Override
		public void actionPerformed(ActionEvent e) {

			buscar();

		}

	}

	class AcaoIncluir extends AbstractAction {

		AcaoIncluir() {
			super("Incluir");
			putValue(MNEMONIC_KEY, KeyEvent.VK_I);
			putValue(SHORT_DESCRIPTION, "Incluir registro de disciplina!");
			putValue(SMALL_ICON, new ImageIcon("images/general/New24.gif"));

		}

		@Override
		public void actionPerformed(ActionEvent e) {

			filho.incluir();

		}

	}

	class AcaoEditar extends AbstractAction {

		AcaoEditar() {
			super("Editar");
			putValue(MNEMONIC_KEY, KeyEvent.VK_E);
			putValue(SHORT_DESCRIPTION, "Editar registro de disciplina!");
			putValue(SMALL_ICON, new ImageIcon("images/general/Edit24.gif"));

			setEnabled(false);
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			String codigo = (String) tblQuery.getValueAt(tblQuery.getSelectedRow(), 0);
			filho.editar(codigo);

		}

	}

	class AcaoExcluir extends AbstractAction {

		AcaoExcluir() {
			super("Excluir");
			putValue(MNEMONIC_KEY, KeyEvent.VK_X);
			putValue(SHORT_DESCRIPTION, "Excluir registro de disciplina!");
			putValue(SMALL_ICON, new ImageIcon("images/general/Delete24.gif"));
			setEnabled(false);
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			String codigo = (String) tblQuery.getValueAt(tblQuery.getSelectedRow(), 0);
			filho.excluir(codigo);

		}

	}

	class AcaoSair extends AbstractAction {

		AcaoSair() {
			super("Voltar");
			putValue(MNEMONIC_KEY, KeyEvent.VK_R);
			putValue(SHORT_DESCRIPTION, "Voltar ao Menu Principal!");
			putValue(SMALL_ICON, new ImageIcon("images/navigation/Back24.gif"));

		}

		@Override
		public void actionPerformed(ActionEvent e) {

			DisciplinaConsulta.this.setVisible(false);
			pai.setVisible(true);

		}

	}

	class HabilitarEdicaoExclusao extends MouseAdapter {

		public void mousePressed(MouseEvent e) {
			if (tblQuery.getSelectedRow() >= 0) {
				actEditar.setEnabled(true);
				actExcluir.setEnabled(true);
			} else {
				actEditar.setEnabled(false);
				actExcluir.setEnabled(false);
			}

		}

	}

	public void buscar() {

		List<Disciplina> disciplinas = new ArrayList<Disciplina>();

		try {

			if (fldValor.getText().isEmpty()) {
				disciplinas = pl.recuperarTodosPorNome();
			} else {

				String chaveDeBusca;
				chaveDeBusca = fldValor.getText();

				switch (cmbChaves.getSelectedIndex()) {
					case 0:
						disciplinas.add(pl.recuperar(chaveDeBusca));
						break;
					case 1:
						disciplinas = pl.recuperarTodosPorNomeContendo(chaveDeBusca);
						break;
				}
			}

			tblQuery.setModel(new DisciplinaTableModel(disciplinas));
			actEditar.setEnabled(false);
			actExcluir.setEnabled(false);
		} catch (NumberFormatException nfe) {
			JOptionPane.showMessageDialog(DisciplinaConsulta.this,
					"A Chave de busca código deve conter apenas números!", "Academico", JOptionPane.ERROR_MESSAGE);
		} catch (DataBaseNotConnectedException | DataBaseGenericException | EntityNotExistsException
				| EntityTableIsEmptyException e) {
			JOptionPane.showMessageDialog(DisciplinaConsulta.this, e.getMessage(), "Academico",
					JOptionPane.ERROR_MESSAGE);
		}
	}

}
