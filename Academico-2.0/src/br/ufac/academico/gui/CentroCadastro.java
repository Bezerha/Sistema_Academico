package br.ufac.academico.gui;

import br.ufac.academico.db.*;
import br.ufac.academico.entity.Centro;
import br.ufac.academico.exception.DataBaseGenericException;
import br.ufac.academico.exception.DataBaseNotConnectedException;
import br.ufac.academico.exception.EntityAlreadyExistsException;
import br.ufac.academico.exception.EntityInvalidFieldsException;
import br.ufac.academico.exception.EntityNotExistsException;
import br.ufac.academico.logic.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CentroCadastro extends JFrame {

	private static final int INCLUSAO=0;
	private static final int EDICAO=1;
	private static final int EXCLUSAO=2;

	private int acao;
	private CentroConsulta pai;
	private CentroLogic cl;

	private JPanel pnlControles, pnlOperacoes, pnlRotulos, pnlCampos;
	private JTextField fldSigla, fldNome;
	private JButton btnConfirmar, btnCancelar;

	AcaoConfirmar actConfirmar = new AcaoConfirmar();
	AcaoCancelar actCancelar = new AcaoCancelar();

	CentroCadastro(CentroConsulta pai, Conexao cnx){
		super("");
		setSize(800, 600);		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		this.pai = pai;
		cl = new CentroLogic(cnx);

		pnlRotulos = new JPanel(new GridLayout(2,1,5,5));
		pnlRotulos.add(new JLabel("Sigla"));
		pnlRotulos.add(new JLabel("Nome"));

		fldSigla = new JTextField();
		fldNome = new JTextField();		

		pnlCampos = new JPanel(new GridLayout(2,1,5,5));
		pnlCampos.add(fldSigla);
		pnlCampos.add(fldNome);

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
			try {		
				switch (acao) {
				case INCLUSAO:
					cl.adicionar(fldSigla.getText(), fldNome.getText());
					limparCampos();
					break;
				case EDICAO:
					cl.atualizar(fldSigla.getText(), fldNome.getText());
					limparCampos();
					break;
				case EXCLUSAO:
					cl.remover(fldSigla.getText());
					limparCampos();
					break;
				}
				CentroCadastro.this.setVisible(false);
				pai.setVisible(true);
				pai.buscar();				
			} catch (DataBaseNotConnectedException | DataBaseGenericException | 
					EntityNotExistsException | EntityAlreadyExistsException | EntityInvalidFieldsException err){
				JOptionPane.showMessageDialog(CentroCadastro.this, err.getMessage(),
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

			limparCampos();
			CentroCadastro.this.setVisible(false);
			pai.setVisible(true);

		}

	}

	public void incluir() {

		setTitle("Inclusão de Centro");
		acao = INCLUSAO;
		limparCampos();
		fldSigla.setEnabled(true);
		fldNome.setEnabled(true);

		setVisible(true);
		pai.setVisible(false);

	}

	public void editar(String sigla) {

		setTitle("Edição de Centro");
		acao = EDICAO;
		limparCampos();
		carregarCampos(sigla);
		fldSigla.setEnabled(false);
		fldNome.setEnabled(true);

		setVisible(true);
		pai.setVisible(false);

	}

	public void excluir(String sigla) {

		setTitle("Exclusão de Centro");
		acao = EXCLUSAO;
		limparCampos();
		carregarCampos(sigla);
		fldSigla.setEnabled(false);
		fldNome.setEnabled(false);

		setVisible(true);
		pai.setVisible(false);

	}	


	private void carregarCampos(String sigla) {
		Centro c;
		try {
			c = cl.recuperar(sigla);
			fldSigla.setText(c.getSigla());
			fldNome.setText(c.getNome());
		} catch (DataBaseNotConnectedException | DataBaseGenericException | EntityNotExistsException e) {
			JOptionPane.showMessageDialog(CentroCadastro.this, e.getMessage(),
					"Academico", JOptionPane.ERROR_MESSAGE);
		}

	}

	private void limparCampos() {
		fldSigla.setText("");
		fldNome.setText("");
	}

}
