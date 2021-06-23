package br.ufac.academico.gui;

import javax.swing.*;

import br.ufac.academico.db.*;
import br.ufac.academico.entity.*;
import br.ufac.academico.exception.DataBaseGenericException;
import br.ufac.academico.exception.DataBaseNotConnectedException;
import br.ufac.academico.exception.EntityAlreadyExistsException;
import br.ufac.academico.exception.EntityInvalidFieldsException;
import br.ufac.academico.exception.EntityNotExistsException;
import br.ufac.academico.exception.EntityTableIsEmptyException;
import br.ufac.academico.logic.*;

import java.awt.*;
import java.awt.event.*;

class DisciplinaCadastro extends JFrame {

	private static final int INCLUSAO = 0;
	private static final int EDICAO = 1;
	private static final int EXCLUSAO = 2;

	private int acao;
	private DisciplinaConsulta pai;
	private DisciplinaLogic pl;
	private CentroLogic cl;

	private JPanel pnlControles, pnlOperacoes, pnlRotulos, pnlCampos;
	private JComboBox<Centro> cmbCentro;
	private JTextField fldCodigo, fldNome, fldCh;
	private JButton btnConfirmar, btnCancelar;

	AcaoConfirmar actConfirmar = new AcaoConfirmar();
	AcaoCancelar actCancelar = new AcaoCancelar();

	DisciplinaCadastro(DisciplinaConsulta pai, Conexao cnx) {
		super("");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		this.pai = pai;
		pl = new DisciplinaLogic(cnx);
		cl = new CentroLogic(cnx);

		pnlRotulos = new JPanel(new GridLayout(4, 1, 5, 5));
		pnlRotulos.add(new JLabel("Código"));
		pnlRotulos.add(new JLabel("Nome"));
		pnlRotulos.add(new JLabel("CH"));
		pnlRotulos.add(new JLabel("Centro"));

		fldCodigo = new JTextField();
		fldNome = new JTextField();
		fldCh = new JTextField();

		try {
			cmbCentro = new JComboBox<Centro>(cl.recuperarTodosPorNome().toArray(new Centro[0]));
		} catch (DataBaseNotConnectedException | DataBaseGenericException | EntityTableIsEmptyException e) {
			JOptionPane.showMessageDialog(DisciplinaCadastro.this, e.getMessage(), "Academico",
					JOptionPane.ERROR_MESSAGE);
		}

		pnlCampos = new JPanel(new GridLayout(4, 1, 5, 5));
		pnlCampos.add(fldCodigo);
		pnlCampos.add(fldNome);
		pnlCampos.add(fldCh);
		pnlCampos.add(cmbCentro);

		pnlControles = new JPanel(new BorderLayout(5, 5));
		pnlControles.add(pnlRotulos, BorderLayout.WEST);
		pnlControles.add(pnlCampos);

		btnConfirmar = new JButton(actConfirmar);
		btnCancelar = new JButton(actCancelar);

		pnlOperacoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
		pnlOperacoes.add(btnConfirmar);
		pnlOperacoes.add(btnCancelar);

		add(pnlControles);
		add(pnlOperacoes, BorderLayout.SOUTH);

		pack();
		setLocationRelativeTo(null);

	}

	class AcaoConfirmar extends AbstractAction {

		AcaoConfirmar() {
			super("Confirmar");
			putValue(MNEMONIC_KEY, KeyEvent.VK_C);
			putValue(SHORT_DESCRIPTION, "Confirmar operação!");
			putValue(SMALL_ICON, new ImageIcon("images/general/Save24.gif"));

		}

		@Override
		public void actionPerformed(ActionEvent e) {
			int ch;
			String codigo, nome;
			Centro centro;

			try {

				codigo = fldCodigo.getText();
				nome = fldNome.getText();
				ch = Integer.parseInt(fldCh.getText());
				centro = (Centro) cmbCentro.getSelectedItem();

				switch (acao) {
					case INCLUSAO:
						pl.adicionar(codigo, nome, ch, centro);
						break;
					case EDICAO:
						pl.atualizar(codigo, nome, ch, centro);
						break;
					case EXCLUSAO:
						pl.remover(codigo);
						break;
				}

				DisciplinaCadastro.this.setVisible(false);
				pai.setVisible(true);
				pai.buscar();

			} catch (NumberFormatException nfe) {
				// EXCEÃ‡ÃƒO LEVANTADA PELO parseLong
				JOptionPane.showMessageDialog(DisciplinaCadastro.this,
						"Os campos Matrícula, RG e CPF, devem conter apenas números!", "Academico",
						JOptionPane.ERROR_MESSAGE);
			} catch (DataBaseNotConnectedException | DataBaseGenericException | EntityNotExistsException
					| EntityAlreadyExistsException | EntityInvalidFieldsException err) {
				JOptionPane.showMessageDialog(DisciplinaCadastro.this, err.getMessage(), "Academico",
						JOptionPane.ERROR_MESSAGE);
			}

		}
	}

	class AcaoCancelar extends AbstractAction {

		AcaoCancelar() {
			super("Cancelar");
			putValue(MNEMONIC_KEY, KeyEvent.VK_L);
			putValue(SHORT_DESCRIPTION, "Cancelar operação!");
			putValue(SMALL_ICON, new ImageIcon("images/general/Stop24.gif"));

		}

		@Override
		public void actionPerformed(ActionEvent e) {

			DisciplinaCadastro.this.setVisible(false);
			pai.setVisible(true);

		}

	}

	public void incluir() {

		setTitle("Inclusão de Disciplina");
		acao = INCLUSAO;
		limparCampos();
		fldCodigo.setEnabled(true);
		fldNome.setEnabled(true);
		fldCh.setEnabled(true);
		cmbCentro.setEnabled(true);

		setVisible(true);
		pai.setVisible(false);

	}

	public void editar(String codigo) {

		setTitle("Edição de Disciplina");
		acao = EDICAO;
		limparCampos();
		carregarCampos(codigo);
		fldCodigo.setEnabled(false);
		fldNome.setEnabled(true);
		fldCh.setEnabled(true);
		cmbCentro.setEnabled(true);

		setVisible(true);
		pai.setVisible(false);

	}

	public void excluir(String codigo) {

		setTitle("Exclusão de Disciplina");
		acao = EXCLUSAO;
		limparCampos();
		carregarCampos(codigo);
		fldCodigo.setEnabled(false);
		fldNome.setEnabled(false);
		fldCh.setEnabled(false);
		cmbCentro.setEnabled(false);

		setVisible(true);
		pai.setVisible(false);

	}

	private void carregarCampos(String codigo) {

		Disciplina d = null;
		Centro c = null;

		try {
			d = pl.recuperar(codigo);
			fldCodigo.setText(String.valueOf(d.getCodigo()));
			fldNome.setText(d.getNome());
			fldCh.setText(String.valueOf(d.getCh()));

			for (int i = 0; i < cmbCentro.getItemCount(); i++) {
				c = cmbCentro.getItemAt(i);
				if (c.getSigla().equals(d.getCentro().getSigla())) {
					cmbCentro.setSelectedItem(c);
					break;
				}
			}

		} catch (DataBaseNotConnectedException | DataBaseGenericException | EntityNotExistsException e) {
			JOptionPane.showMessageDialog(DisciplinaCadastro.this, e.getMessage(), "Academico",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	private void limparCampos() {
		fldCodigo.setText("");
		fldNome.setText("");
		fldCh.setText("");
		cmbCentro.setSelectedIndex(-1);
	}

}
