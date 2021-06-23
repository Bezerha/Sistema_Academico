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

class ProfessorCadastro extends JFrame {

	private static final int INCLUSAO=0;
	private static final int EDICAO=1;
	private static final int EXCLUSAO=2;

	private int acao;
	private ProfessorConsulta pai;
	private ProfessorLogic pl;
	private CentroLogic cl;

	private JPanel pnlControles, pnlOperacoes, pnlRotulos, pnlCampos;
	private JComboBox<Centro> cmbCentro;
	private JTextField fldMatricula, fldNome, fldRg, fldCpf, fldEndereco, fldFone;
	private JButton btnConfirmar, btnCancelar;

	AcaoConfirmar actConfirmar = new AcaoConfirmar();
	AcaoCancelar actCancelar = new AcaoCancelar();

	ProfessorCadastro(ProfessorConsulta pai, Conexao cnx){
		super("");
		setSize(800, 600);		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		this.pai = pai;
		pl = new ProfessorLogic(cnx);
		cl = new CentroLogic(cnx);

		pnlRotulos = new JPanel(new GridLayout(7,1,5,5));
		pnlRotulos.add(new JLabel("Matrícula"));
		pnlRotulos.add(new JLabel("Nome"));
		pnlRotulos.add(new JLabel("RG"));		
		pnlRotulos.add(new JLabel("CPF"));
		pnlRotulos.add(new JLabel("Endereço"));
		pnlRotulos.add(new JLabel("Telefone"));
		pnlRotulos.add(new JLabel("Centro"));		

		fldMatricula = new JTextField();
		fldNome = new JTextField();		
		fldRg = new JTextField();		
		fldCpf = new JTextField();
		fldEndereco = new JTextField();		
		fldFone = new JTextField();

		try {
			cmbCentro = new JComboBox<Centro>(cl.recuperarTodosPorNome().toArray(new Centro[0]));
		} catch (DataBaseNotConnectedException | DataBaseGenericException | EntityTableIsEmptyException e) {
			JOptionPane.showMessageDialog(ProfessorCadastro.this, e.getMessage(),
					"Academico", JOptionPane.ERROR_MESSAGE);
		}

		pnlCampos = new JPanel(new GridLayout(7,1,5,5));
		pnlCampos.add(fldMatricula);
		pnlCampos.add(fldNome);
		pnlCampos.add(fldRg);
		pnlCampos.add(fldCpf);
		pnlCampos.add(fldEndereco);
		pnlCampos.add(fldFone);		
		pnlCampos.add(cmbCentro);;

		pnlControles = new JPanel(new BorderLayout(5,5));
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

	class AcaoConfirmar extends AbstractAction{

		AcaoConfirmar(){
			super("Confirmar");
			putValue(MNEMONIC_KEY, KeyEvent.VK_C);
			putValue(SHORT_DESCRIPTION, 
					"Confirmar operação!");
			putValue(SMALL_ICON, 
					new ImageIcon("images/general/Save24.gif"));

		}

		@Override
		public void actionPerformed(ActionEvent e) {
			long matricula, rg, cpf;
			String nome, endereco, fone;
			Centro centro;

			try {

				matricula = Long.parseLong(fldMatricula.getText());
				nome = fldNome.getText();
				rg = Long.parseLong(fldRg.getText());
				cpf = Long.parseLong(fldCpf.getText());
				endereco = fldEndereco.getText();
				fone = fldFone.getText();
				centro = (Centro) cmbCentro.getSelectedItem();

				switch (acao) {
				case INCLUSAO:
					pl.adicionar(matricula, nome, rg, cpf, endereco, fone, centro);
					break;
				case EDICAO:
					pl.atualizar(matricula, nome, rg, cpf, endereco, fone, centro);
					break;
				case EXCLUSAO:
					pl.remover(matricula);
					break;
				}

				ProfessorCadastro.this.setVisible(false);
				pai.setVisible(true);
				pai.buscar();

			} catch (NumberFormatException nfe) {
				// EXCEÃ‡ÃƒO LEVANTADA PELO parseLong
				JOptionPane.showMessageDialog(ProfessorCadastro.this, 
						"Os campos Matrícula, RG e CPF, devem conter apenas números!",
						"Academico", JOptionPane.ERROR_MESSAGE);
			} catch (DataBaseNotConnectedException | DataBaseGenericException | 
					EntityNotExistsException | EntityAlreadyExistsException | 
					EntityInvalidFieldsException err){
				JOptionPane.showMessageDialog(ProfessorCadastro.this, err.getMessage(),
						"Academico", JOptionPane.ERROR_MESSAGE);
			}

		}
	}

	class AcaoCancelar extends AbstractAction{

		AcaoCancelar(){
			super("Cancelar");
			putValue(MNEMONIC_KEY, KeyEvent.VK_L);
			putValue(SHORT_DESCRIPTION, 
					"Cancelar operação!");
			putValue(SMALL_ICON, 
					new ImageIcon("images/general/Stop24.gif"));

		}

		@Override
		public void actionPerformed(ActionEvent e) {

			ProfessorCadastro.this.setVisible(false);
			pai.setVisible(true);

		}

	}

	public void incluir() {

		setTitle("Inclusão de Professor");
		acao = INCLUSAO;
		limparCampos();
		fldMatricula.setEnabled(true);
		fldNome.setEnabled(true);
		fldRg.setEnabled(true);
		fldCpf.setEnabled(true);
		fldEndereco.setEnabled(true);
		fldFone.setEnabled(true);
		cmbCentro.setEnabled(true);

		setVisible(true);
		pai.setVisible(false);

	}

	public void editar(long matricula) {

		setTitle("Edição de Professor");
		acao = EDICAO;
		limparCampos();
		carregarCampos(matricula);
		fldMatricula.setEnabled(false);
		fldNome.setEnabled(true);
		fldRg.setEnabled(true);
		fldCpf.setEnabled(true);
		fldEndereco.setEnabled(true);
		fldFone.setEnabled(true);
		cmbCentro.setEnabled(true);

		setVisible(true);
		pai.setVisible(false);

	}

	public void excluir(long matricula) {

		setTitle("Exclusão de Professor");
		acao = EXCLUSAO;
		limparCampos();
		carregarCampos(matricula);
		fldMatricula.setEnabled(false);
		fldNome.setEnabled(false);
		fldRg.setEnabled(false);
		fldCpf.setEnabled(false);
		fldEndereco.setEnabled(false);
		fldFone.setEnabled(false);
		cmbCentro.setEnabled(false);

		setVisible(true);
		pai.setVisible(false);

	}	


	private void carregarCampos(long matricula) {

		Professor p = null;
		Centro c = null;
		
		try {
			p = pl.recuperar(matricula);
			fldMatricula.setText(String.valueOf(p.getMatricula()));
			fldNome.setText(p.getNome());
			fldRg.setText(String.valueOf(p.getRg()));
			fldCpf.setText(String.valueOf(p.getCpf()));
			fldEndereco.setText(p.getEndereco());
			fldFone.setText(p.getFone());

			for (int i = 0; i < cmbCentro.getItemCount(); i++) {
				c = cmbCentro.getItemAt(i);
				if (c.getSigla().equals(p.getCentro().getSigla())) {
					cmbCentro.setSelectedItem(c);
					break;
				}
			}

		} catch (DataBaseNotConnectedException | DataBaseGenericException | 
				EntityNotExistsException e) {
			JOptionPane.showMessageDialog(ProfessorCadastro.this, e.getMessage(),
					"Academico", JOptionPane.ERROR_MESSAGE);
		}

	}

	private void limparCampos() {
		fldMatricula.setText("");
		fldNome.setText("");
		fldRg.setText("");
		fldCpf.setText("");
		fldEndereco.setText("");
		fldFone.setText("");
		cmbCentro.setSelectedIndex(-1);
	}

}
