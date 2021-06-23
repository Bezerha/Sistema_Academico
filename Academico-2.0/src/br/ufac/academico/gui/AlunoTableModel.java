package br.ufac.academico.gui;
import br.ufac.academico.entity.*;
import java.util.*;
import javax.swing.table.*;

public class AlunoTableModel extends AbstractTableModel{
    private List<Aluno> alunos;

    public AlunoTableModel(List<Aluno> alunos) {
        this.alunos = alunos;
    }

    public int getColumnCount() {
        return 7;
    }


    public int getRowCount() {
        return alunos.size();
    }


    public String getColumnName(int column) {

        String nomeDaColuna;

        switch (column) {
        case 0:
            nomeDaColuna = "Matrícula";
            break;
        case 1:
            nomeDaColuna = "Nome";
            break;
        case 2:
            nomeDaColuna = "Fone";
            break;
        case 3:
            nomeDaColuna = "Endereço";
            break;
        case 4:
            nomeDaColuna = "CEP";
            break;
        case 5:
            nomeDaColuna = "Sexo";
            break;
        case 6:
            nomeDaColuna = "Curso";
            break;
        default:
            nomeDaColuna="";
            break;
        }

        return nomeDaColuna;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {

        Object valor = null;
        Aluno a = alunos.get(rowIndex);

        switch (columnIndex) {
        case 0:
            valor = a.getMatricula();  
            break;
        case 1:
            valor = a.getNome();
            break;
        case 2:
            valor = a.getFone();
            break;
        case 3:
            valor = a.getEndereco();
            break;
        case 4:
            valor = a.getCep();
            break;
        case 5:
            valor = a.getSexo();
            break;
        case 6:
            valor = a.getCurso().getCodigo();
            break;
        default:
            valor = null;
            break;
        }
        return valor;
    }

 
    public Class<?> getColumnClass(int columnIndex) {
        
        Object obj;

        switch (columnIndex) {
            case 0: obj = Long.class; break;
            case 1: obj = String.class; break;
            case 2: obj = String.class; break;
            case 3: obj = String.class; break;
            case 4: obj = String.class; break;
            case 5: obj = String.class; break;
            case 6: obj = int.class; break;            
            default: obj = null; break;
        }        

        return obj.getClass();        

    }
}