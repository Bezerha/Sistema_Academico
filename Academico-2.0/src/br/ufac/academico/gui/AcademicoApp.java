package br.ufac.academico.gui;

import br.ufac.academico.db.*;
import br.ufac.academico.exception.AccessDeniedException;
import br.ufac.academico.exception.DataBaseAlreadyConnectedException;
import br.ufac.academico.exception.DataBaseGenericException;

import javax.swing.JOptionPane;


class AcademicoApp {

	private static final String urlSchema = "jdbc:mysql://localhost/academico?scrollTolerantForwardOnly=true"; 

	public static void main(String args[]){

		Conexao cnx = null;

		String usuario, senha;
		boolean conectado = false;
		int tentativas=0;

		try {
			cnx = new Conexao();
			do {
				usuario = JOptionPane.showInputDialog("Informe o nome do usuário:");
				senha = JOptionPane.showInputDialog("Informe a senha do usuário:");
				conectado = cnx.conecte(urlSchema, usuario, senha);

				tentativas++;
			} while (!conectado && tentativas <= 3);
		} catch (DataBaseGenericException | DataBaseAlreadyConnectedException | AccessDeniedException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Academico", JOptionPane.ERROR_MESSAGE);
		}
		if (conectado) {

			Academico pc = new Academico(cnx);
			pc.setVisible(true);

		}

	}
}


