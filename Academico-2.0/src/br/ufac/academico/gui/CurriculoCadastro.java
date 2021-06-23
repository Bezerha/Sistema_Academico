package br.ufac.academico.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import br.ufac.academico.db.Conexao;
import br.ufac.academico.entity.Curso;
import br.ufac.academico.entity.Curriculo;
import br.ufac.academico.exception.DataBaseGenericException;
import br.ufac.academico.exception.DataBaseNotConnectedException;
import br.ufac.academico.exception.EntityAlreadyExistsException;
import br.ufac.academico.exception.EntityInvalidFieldsException;
import br.ufac.academico.exception.EntityNotExistsException;
import br.ufac.academico.exception.EntityTableIsEmptyException;
import br.ufac.academico.logic.CursoLogic;
import br.ufac.academico.logic.CurriculoLogic;

public class CurriculoCadastro extends JFrame {
	private static final int INCLUSAO = 0;
	private static final int EDICAO = 1;
	private static final int EXCLUSAO = 2;

	private int acao;
	private CurriculoConsulta pai;
	private CurriculoLogic cl;
	private CursoLogic curl;

	private JPanel pnlControles, pnlOperacoes, pnlRotulos, pnlCampos;
	private JComboBox<Curso> cmbCurso;
	private JTextField fldCodigo, fldDescricao, fldCurso;
	private JButton btnConfirmar, btnCancelar;

	AcaoConfirmar actConfirmar = new AcaoConfirmar();
	AcaoCancelar actCancelar = new AcaoCancelar();

	CurriculoCadastro(CurriculoConsulta pai, Conexao cnx) {
		super("");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		this.pai = pai;
		cl = new CurriculoLogic(cnx);
		curl = new CursoLogic(cnx);

		pnlRotulos = new JPanel(new GridLayout(3, 1, 5, 5));
		pnlRotulos.add(new JLabel("Codigo"));
		pnlRotulos.add(new JLabel("Curso"));
		pnlRotulos.add(new JLabel("Descrição"));

		fldCodigo = new JTextField();
		fldCurso = new JTextField();
		fldDescricao = new JTextField();

		try {
			cmbCurso = new JComboBox<Curso>(curl.recuperarTodosPorNome().toArray(new Curso[0]));
		} catch (DataBaseNotConnectedException | DataBaseGenericException | EntityTableIsEmptyException e) {
			JOptionPane.showMessageDialog(CurriculoCadastro.this, e.getMessage(), "Academico",
					JOptionPane.ERROR_MESSAGE);
		}

		pnlCampos = new JPanel(new GridLayout(7, 1, 5, 5));
		pnlCampos.add(fldCodigo);
		pnlCampos.add(fldDescricao);
		pnlCampos.add(cmbCurso);
		;

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
			long codigo;
			String descricao;
			Curso curso;

			try {

				codigo = Long.parseLong(fldCodigo.getText());
				descricao = fldDescricao.getText();
				curso = (Curso) cmbCurso.getSelectedItem();

				switch (acao) {
					case INCLUSAO:
						cl.adicionar(codigo, curso, descricao);
						break;
					case EDICAO:
						cl.atualizar(codigo, curso, descricao);
						break;
					case EXCLUSAO:
						cl.remover(codigo); // remover pelo codigo;
						break;
				}

				CurriculoCadastro.this.setVisible(false);
				pai.setVisible(true);
				pai.buscar();

			} catch (NumberFormatException nfe) {
				// EXCEÃ‡ÃƒO LEVANTADA PELO parseLong
				JOptionPane.showMessageDialog(CurriculoCadastro.this, "O campo codigo deve conter apenas números!",
						"Academico", JOptionPane.ERROR_MESSAGE);
			} catch (DataBaseNotConnectedException | DataBaseGenericException | EntityNotExistsException
					| EntityAlreadyExistsException | EntityInvalidFieldsException err) {
				JOptionPane.showMessageDialog(CurriculoCadastro.this, err.getMessage(), "Academico",
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

			CurriculoCadastro.this.setVisible(false);
			pai.setVisible(true);

		}

	}

	public void incluir() {

		setTitle("Inclusão de Codigo");
		acao = INCLUSAO;
		limparCampos();
		fldCodigo.setEnabled(true);
		fldDescricao.setEnabled(true);
		cmbCurso.setEnabled(true);

		setVisible(true);
		pai.setVisible(false);

	}

	public void editar(long codigo) {

		setTitle("Edição de Codigo");
		acao = EDICAO;
		limparCampos();
		carregarCampos(codigo);
		fldCodigo.setEnabled(false);
		fldDescricao.setEnabled(true);
		cmbCurso.setEnabled(true);

		setVisible(true);
		pai.setVisible(false);

	}

	public void excluir(long codigo) {

		setTitle("Exclusão de Codigo");
		acao = EXCLUSAO;
		limparCampos();
		carregarCampos(codigo);
		fldCodigo.setEnabled(false);
		fldDescricao.setEnabled(false);
		cmbCurso.setEnabled(false);

		setVisible(true);
		pai.setVisible(false);

	}

	private void carregarCampos(long codigo) {

		Curriculo curri = null;
		Curso c = null;

		try {
			curri = cl.recuperar(codigo);
			fldCodigo.setText(String.valueOf(curri.getCodigo()));
			fldCurso.setText(String.valueOf(curri.getCurso()));
			fldDescricao.setText(curri.getDescricao());

			for (int i = 0; i < cmbCurso.getItemCount(); i++) {
				c = cmbCurso.getItemAt(i);
				if (c.getCodigo() == (curri.getCurso().getCodigo())) {
					cmbCurso.setSelectedItem(c);
					break;
				}
			}

		} catch (DataBaseNotConnectedException | DataBaseGenericException | EntityNotExistsException e) {
			JOptionPane.showMessageDialog(CurriculoCadastro.this, e.getMessage(), "Academico",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	private void limparCampos() {
		fldCodigo.setText("");
		fldDescricao.setText("");
		cmbCurso.setSelectedIndex(-1);
	}
}
