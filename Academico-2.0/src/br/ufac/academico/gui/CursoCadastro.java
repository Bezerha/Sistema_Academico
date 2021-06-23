package br.ufac.academico.gui;

import br.ufac.academico.db.*;
import br.ufac.academico.entity.Curso;
import br.ufac.academico.exception.DataBaseGenericException;
import br.ufac.academico.exception.DataBaseNotConnectedException;
import br.ufac.academico.exception.EntityAlreadyExistsException;
import br.ufac.academico.exception.EntityInvalidFieldsException;
import br.ufac.academico.exception.EntityNotExistsException;
import br.ufac.academico.logic.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CursoCadastro extends JFrame {

	private static final int INCLUSAO = 0;
	private static final int EDICAO = 1;
	private static final int EXCLUSAO = 2;

	private int acao;
	private CursoConsulta pai;
	private CursoLogic cl;

	private JPanel pnlControles, pnlOperacoes, pnlRotulos, pnlCampos;
	private JTextField fldCodigo, fldNome;
	private JButton btnConfirmar, btnCancelar;

	AcaoConfirmar actConfirmar = new AcaoConfirmar();
	AcaoCancelar actCancelar = new AcaoCancelar();

	CursoCadastro(CursoConsulta pai, Conexao cnx) {
		super("");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		this.pai = pai;
		cl = new CursoLogic(cnx);

		pnlRotulos = new JPanel(new GridLayout(2, 1, 5, 5));
		pnlRotulos.add(new JLabel("Codigo"));
		pnlRotulos.add(new JLabel("Nome"));

		fldCodigo = new JTextField();
		fldNome = new JTextField();

		pnlCampos = new JPanel(new GridLayout(2, 1, 5, 5));
		pnlCampos.add(fldCodigo);
		pnlCampos.add(fldNome);

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
			putValue(SHORT_DESCRIPTION, "Confirmar opera��o!");
			putValue(SMALL_ICON, new ImageIcon("images/general/Save24.gif"));

		}

		@Override
		public void actionPerformed(ActionEvent e) {

			try {

				switch (acao) {
					case INCLUSAO:
						cl.adicionar(Integer.parseInt(fldCodigo.getText()), fldNome.getText());
						limparCampos();
						break;
					case EDICAO:
						cl.atualizar(Integer.parseInt(fldCodigo.getText()), fldNome.getText());
						limparCampos();
						break;
					case EXCLUSAO:
						cl.remover(Integer.parseInt(fldCodigo.getText()));
						limparCampos();
						break;
				}
				CursoCadastro.this.setVisible(false);
				pai.setVisible(true);
				pai.buscar();
			} catch (DataBaseNotConnectedException | DataBaseGenericException | EntityNotExistsException
					| EntityAlreadyExistsException | EntityInvalidFieldsException err) {
				JOptionPane.showMessageDialog(CursoCadastro.this, err.getMessage(), "Academico",
						JOptionPane.ERROR_MESSAGE);
			}

		}
	}

	class AcaoCancelar extends AbstractAction {

		AcaoCancelar() {
			super("Cancelar");
			putValue(MNEMONIC_KEY, KeyEvent.VK_L);
			putValue(SHORT_DESCRIPTION, "Cancelar opera��o!");
			putValue(SMALL_ICON, new ImageIcon("images/general/Stop24.gif"));

		}

		@Override
		public void actionPerformed(ActionEvent e) {

			limparCampos();
			CursoCadastro.this.setVisible(false);
			pai.setVisible(true);

		}

	}

	public void incluir() {

		setTitle("Inclus�o de Curso");
		acao = INCLUSAO;
		limparCampos();
		fldCodigo.setEnabled(true);
		fldNome.setEnabled(true);

		setVisible(true);
		pai.setVisible(false);

	}

	public void editar(int codigo) {

		setTitle("Edica��o de Curso");
		acao = EDICAO;
		limparCampos();
		carregarCampos(codigo);
		fldCodigo.setEnabled(false);
		fldNome.setEnabled(true);

		setVisible(true);
		pai.setVisible(false);

	}

	public void excluir(int codigo) {

		setTitle("Exclus�o de Curso");
		acao = EXCLUSAO;
		limparCampos();
		carregarCampos(codigo);
		fldCodigo.setEnabled(false);
		fldNome.setEnabled(false);

		setVisible(true);
		pai.setVisible(false);

	}

	private void carregarCampos(int codigo) {
		Curso c;
		try {
			c = cl.recuperar(codigo);
			fldCodigo.setText(String.valueOf(c.getCodigo()));
			fldNome.setText(c.getNome());
		} catch (DataBaseNotConnectedException | DataBaseGenericException | EntityNotExistsException e) {
			JOptionPane.showMessageDialog(CursoCadastro.this, e.getMessage(), "Academico", JOptionPane.ERROR_MESSAGE);
		}

	}

	private void limparCampos() {
		fldCodigo.setText("");
		fldNome.setText("");
	}

}