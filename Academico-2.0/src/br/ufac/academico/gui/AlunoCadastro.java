package br.ufac.academico.gui;

import br.ufac.academico.db.*;
import br.ufac.academico.entity.Aluno;
import br.ufac.academico.entity.Curso;
import br.ufac.academico.exception.DataBaseGenericException;
import br.ufac.academico.exception.DataBaseNotConnectedException;
import br.ufac.academico.exception.EntityAlreadyExistsException;
import br.ufac.academico.exception.EntityInvalidFieldsException;
import br.ufac.academico.exception.EntityNotExistsException;
import br.ufac.academico.exception.EntityTableIsEmptyException;
import br.ufac.academico.logic.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class AlunoCadastro extends JFrame {

	private static final int INCLUSAO = 0;
	private static final int EDICAO = 1;
	private static final int EXCLUSAO = 2;

	private int acao;
	private AlunoConsulta pai;
	private AlunoLogic al;
	private CursoLogic cl;

	private JPanel pnlControles, pnlOperacoes, pnlRotulos, pnlCampos;
	private JComboBox<Curso> cmbCurso;
	private JTextField fldMatricula, fldNome, fldFone, fldEndereco, fldCEP, fldSexo;
	private JButton btnConfirmar, btnCancelar;

	AcaoConfirmar actConfirmar = new AcaoConfirmar();
	AcaoCancelar actCancelar = new AcaoCancelar();

	AlunoCadastro(AlunoConsulta pai, Conexao cnx) {
		super("");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		this.pai = pai;
		al = new AlunoLogic(cnx);
		cl = new CursoLogic(cnx);

		pnlRotulos = new JPanel(new GridLayout(7, 1, 5, 5));
		pnlRotulos.add(new JLabel("Matrícula"));
		pnlRotulos.add(new JLabel("Nome"));
		pnlRotulos.add(new JLabel("Fone"));
		pnlRotulos.add(new JLabel("Endereço"));
		pnlRotulos.add(new JLabel("CEP"));
		pnlRotulos.add(new JLabel("Sexo"));
		pnlRotulos.add(new JLabel("Curso"));

		fldMatricula = new JTextField();
		fldNome = new JTextField();
		fldFone = new JTextField();
		fldEndereco = new JTextField();
		fldCEP = new JTextField();
		fldSexo = new JTextField();

		try {
			cmbCurso = new JComboBox<Curso>(cl.recuperarTodosPorNome().toArray(new Curso[0]));
		} catch (DataBaseNotConnectedException | DataBaseGenericException | EntityTableIsEmptyException e) {
			JOptionPane.showMessageDialog(AlunoCadastro.this, e.getMessage(), "Academico", JOptionPane.ERROR_MESSAGE);
		}

		pnlCampos = new JPanel(new GridLayout(7, 1, 5, 5));
		pnlCampos.add(fldMatricula);
		pnlCampos.add(fldNome);
		pnlCampos.add(fldFone);
		pnlCampos.add(fldEndereco);
		pnlCampos.add(fldCEP);
		pnlCampos.add(fldSexo);
		pnlCampos.add(cmbCurso);

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
			long matricula;
			String nome, endereco, fone, cep, sexo;
			Curso curso;

			try {

				matricula = Long.parseLong(fldMatricula.getText());
				nome = fldNome.getText();
				fone = fldFone.getText();
				endereco = fldEndereco.getText();
				cep = fldCEP.getText();
				sexo = fldSexo.getText();
				curso = (Curso) cmbCurso.getSelectedItem();

				switch (acao) {
					case INCLUSAO:
						al.adicionar(matricula, nome, fone, endereco, cep, sexo, curso);
						break;
					case EDICAO:
						al.atualizar(matricula, nome, fone, endereco, cep, sexo, curso);
						break;
					case EXCLUSAO:
						al.remover(matricula);
						break;
				}

				AlunoCadastro.this.setVisible(false);
				pai.setVisible(true);
				pai.buscar();
			} catch (NumberFormatException nfe) {
				// EXCEÃ‡ÃƒO LEVANTADA PELO parseLong
				JOptionPane.showMessageDialog(AlunoCadastro.this, "O campo Matricula deve conter apenas numeros!",
						"Academico", JOptionPane.ERROR_MESSAGE);
			} catch (DataBaseNotConnectedException | DataBaseGenericException | EntityNotExistsException
					| EntityAlreadyExistsException | EntityInvalidFieldsException err) {
				JOptionPane.showMessageDialog(AlunoCadastro.this, err.getMessage(), "Academico",
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

			AlunoCadastro.this.setVisible(false);
			pai.setVisible(true);

		}

	}

	public void incluir() {

		setTitle("Inclusão de Aluno");
		acao = INCLUSAO;
		limparCampos();
		fldMatricula.setEnabled(true);
		fldNome.setEnabled(true);
		fldFone.setEnabled(true);
		fldEndereco.setEnabled(true);
		fldCEP.setEnabled(true);
		fldSexo.setEnabled(true);
		cmbCurso.setEnabled(true);

		setVisible(true);
		pai.setVisible(false);

	}

	public void editar(long matricula) {

		setTitle("Edição de Aluno");
		acao = EDICAO;
		limparCampos();
		carregarCampos(matricula);
		fldMatricula.setEnabled(false);
		fldNome.setEnabled(true);
		fldFone.setEnabled(true);
		fldEndereco.setEnabled(true);
		fldCEP.setEnabled(true);
		fldSexo.setEnabled(true);
		cmbCurso.setEnabled(true);

		setVisible(true);
		pai.setVisible(false);

	}

	public void excluir(long matricula) {

		setTitle("Exclusão de Aluno");
		acao = EXCLUSAO;
		limparCampos();
		carregarCampos(matricula);
		fldMatricula.setEnabled(false);
		fldNome.setEnabled(false);
		fldFone.setEnabled(false);
		fldEndereco.setEnabled(false);
		fldCEP.setEnabled(false);
		fldSexo.setEnabled(false);
		cmbCurso.setEnabled(false);

		setVisible(true);
		pai.setVisible(false);

	}

	private void carregarCampos(long matricula) {

		Aluno a = null;
		Curso c = null;

		try {
			a = al.recuperar(matricula);
			fldMatricula.setText(String.valueOf(a.getMatricula()));
			fldNome.setText(a.getNome());
			fldFone.setText(a.getFone());
			fldEndereco.setText(a.getEndereco());
			fldCEP.setText(a.getCep());
			fldEndereco.setText(a.getEndereco());
			fldFone.setText(a.getFone());

			for (int i = 0; i < cmbCurso.getItemCount(); i++) {
				c = cmbCurso.getItemAt(i);
				if (c.getCodigo() == (a.getCurso().getCodigo())) {
					cmbCurso.setSelectedItem(c);
					break;
				}
			}

		} catch (DataBaseNotConnectedException | DataBaseGenericException | EntityNotExistsException e) {
			JOptionPane.showMessageDialog(AlunoCadastro.this, e.getMessage(), "Academico", JOptionPane.ERROR_MESSAGE);
		}

	}

	private void limparCampos() {
		fldMatricula.setText("");
		fldNome.setText("");
		fldFone.setText("");
		fldEndereco.setText("");
		fldCEP.setText("");
		fldSexo.setText("");
		cmbCurso.setSelectedIndex(-1);
	}

}