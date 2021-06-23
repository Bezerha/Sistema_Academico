package br.ufac.academico.gui;

import br.ufac.academico.db.*;
import br.ufac.academico.exception.*;

import java.awt.event.*;
import javax.swing.*;

public class Academico extends JFrame {

	private JMenuBar mb;
	private JMenu mEntidades, mAjuda;
	private AcaoAluno actAluno;
	private AcaoCentro actCentro;
	private AcaoProfessor actProfessor;
	private AcaoCurso actCurso;
	private AcaoCurriculo actCurriculo;
	private AcaoDisciplina actDisciplina;
	private AcaoSair actSair;
	private AcaoSobre actSobre;
	private Conexao cnx;
	private AlunoConsulta alunoConsulta;
	private CentroConsulta centroConsulta;
	private CursoConsulta cursoConsulta;
	private CurriculoConsulta curriculoConsulta;
	private DisciplinaConsulta disciplinaConsulta;
	private ProfessorConsulta professorConsulta;

	public Academico(Conexao cnx) {

		setTitle("Sistema de Controle Acadêmico");
		setSize(500, 300);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);

		this.cnx = cnx;
		alunoConsulta = new AlunoConsulta(this, cnx);
		centroConsulta = new CentroConsulta(this, cnx);
		cursoConsulta = new CursoConsulta(this, cnx);
		curriculoConsulta = new CurriculoConsulta(this, cnx);
		disciplinaConsulta = new DisciplinaConsulta(this, cnx);
		professorConsulta = new ProfessorConsulta(this, cnx);


		actCentro = new AcaoCentro();
		actProfessor = new AcaoProfessor();
		actAluno = new AcaoAluno();
		actCurso = new AcaoCurso();
		actCurriculo = new AcaoCurriculo();
		actDisciplina = new AcaoDisciplina();
		actSair = new AcaoSair();
		actSobre = new AcaoSobre();

		mb = new JMenuBar();
		mEntidades = new JMenu("Entidades");
		mEntidades.setMnemonic(KeyEvent.VK_E);
		mAjuda = new JMenu("Ajuda");
		mAjuda.setMnemonic(KeyEvent.VK_U);

		mEntidades.add(actAluno);
		mEntidades.add(actProfessor);
		mEntidades.add(actCurriculo);
		mEntidades.add(actDisciplina);
		mEntidades.add(actCurso);
		mEntidades.add(actCentro);
		mEntidades.addSeparator();
		mEntidades.add(actSair);
		mAjuda.add(actSobre);

		mb.add(mEntidades);
		mb.add(mAjuda);

		setJMenuBar(mb);

	}

	class AcaoAluno extends AbstractAction {

		AcaoAluno() {
			super("Aluno");
			putValue(MNEMONIC_KEY, KeyEvent.VK_A);
			putValue(SHORT_DESCRIPTION, "Gerenciamento de Alunos!");
			putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));
			putValue(SMALL_ICON, new ImageIcon("images/development/J2EEApplicationClient24.gif"));
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			Academico.this.setVisible(false);
			alunoConsulta.setVisible(true);

		}

	}
	class AcaoCentro extends AbstractAction {

		AcaoCentro() {
			super("Centro");
			putValue(MNEMONIC_KEY, KeyEvent.VK_C);
			putValue(SHORT_DESCRIPTION, "Gerenciamento de Centros!");
			putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
			putValue(SMALL_ICON, new ImageIcon("images/development/J2EEServer24.gif"));
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			Academico.this.setVisible(false);
			centroConsulta.setVisible(true);

		}

	}
	class AcaoCurso extends AbstractAction {

		AcaoCurso() {
			super("Curso");
			putValue(MNEMONIC_KEY, KeyEvent.VK_U);
			putValue(SHORT_DESCRIPTION, "Gerenciamento de Cursos!");
			putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.CTRL_DOWN_MASK));
			putValue(SMALL_ICON, new ImageIcon("images/development/J2EEServer24.gif"));
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			Academico.this.setVisible(false);
			cursoConsulta.setVisible(true);

		}

	}
	class AcaoCurriculo extends AbstractAction {

		AcaoCurriculo() {
			super("Curriculo");
			putValue(MNEMONIC_KEY, KeyEvent.VK_R);
			putValue(SHORT_DESCRIPTION, "Gerenciamento de Curriculos!");
			putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK));
			putValue(SMALL_ICON, new ImageIcon("images/development/J2EEApplicationClient24.gif"));
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			Academico.this.setVisible(false);
			curriculoConsulta.setVisible(true);

		}

	}
	class AcaoProfessor extends AbstractAction {

		AcaoProfessor() {
			super("Professor");
			putValue(MNEMONIC_KEY, KeyEvent.VK_P);
			putValue(SHORT_DESCRIPTION, "Gerenciamento de Professores!");
			putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK));
			putValue(SMALL_ICON, new ImageIcon("images/development/J2EEApplicationClient24.gif"));
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			Academico.this.setVisible(false);
			professorConsulta.setVisible(true);

		}

	}
	class AcaoDisciplina extends AbstractAction {

        AcaoDisciplina(){
            super("Disciplina");
            putValue(MNEMONIC_KEY, KeyEvent.VK_D);
            putValue(SHORT_DESCRIPTION, "Gerenciamento de Disciplina!");
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_DOWN_MASK));
            putValue(SMALL_ICON, new ImageIcon("images/development/J2EEApplicationClient24.gif"));
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            Academico.this.setVisible(false);
            disciplinaConsulta.setVisible(true);

        }
	}
	class AcaoSobre extends AbstractAction {

		AcaoSobre() {
			super("Sobre...");
			putValue(MNEMONIC_KEY, KeyEvent.VK_E);
			putValue(SHORT_DESCRIPTION, "Sobre o Sistema!");
			putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK));
			putValue(SMALL_ICON, new ImageIcon("images/general/About24.gif"));
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(Academico.this,
					"Sistema de Controle Acadêmico\n" + "Desenvolvido por Felipe Bezerra e Gabriel Batista",
					"Acadêmico", JOptionPane.INFORMATION_MESSAGE);
		}

	}

	class AcaoSair extends AbstractAction {

		AcaoSair() {
			super("Encerrar");
			putValue(MNEMONIC_KEY, KeyEvent.VK_R);
			putValue(SHORT_DESCRIPTION, "Encerrar a aplicaÃ§Ã£o");
			putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK));
			putValue(SMALL_ICON, new ImageIcon("images/general/Stop24.gif"));
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				cnx.desconecte();
				System.exit(0);
			} catch (DataBaseGenericException | DataBaseNotConnectedException err) {
				JOptionPane.showMessageDialog(null, err.getMessage(), "Academico", JOptionPane.ERROR_MESSAGE);

			}

		}

	}
}