/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.curso.view.util; 

import br.com.ifba.curso.view.CursoAtualizar;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.JOptionPane;

/**
 * Esta classe é o "Editor" da célula. Ela é ativada quando o usuário clica
 * na célula para interagir com o botão.
 */
public class ButtonEditor extends DefaultCellEditor {

    protected JButton button;
    private int row;
    private int column;

    public ButtonEditor(JCheckBox checkBox) {
        super(checkBox); // Construtor padrão
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Este comando é importante para avisar a tabela que a "edição" (o clique) terminou.
                fireEditingStopped();
            }
        });
    }

    /**
     * Este método é chamado quando o usuário clica na célula.
     * Ele retorna o componente que será usado para a "edição" (o nosso botão clicável).
     */
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        
        this.row = row;
        this.column = column;

        // Pega o ícone do nosso ButtonRenderer para que o botão clicado também tenha a imagem.
        if (table.getCellRenderer(row, column) instanceof JButton) {
            JButton rendererButton = (JButton) table.getCellRenderer(row, column);
            button.setIcon(rendererButton.getIcon());
        }
        button.setText("");
        return button;
    }

    /**
     * Este método é chamado DEPOIS que o botão é clicado.
     * É aqui que colocamos a LÓGICA da ação.
     */
    @Override
    public Object getCellEditorValue() {
        // --- LÓGICA DE AÇÃO DENTRO DO EDITOR ---
        
        if (this.column == 4) { // Se for a coluna "Editar"
            try {
                // 1. Pega o ID da tabela usando a referência que guardamos
                // Supondo que o ID está na primeira coluna (índice 0)
                // Object valorId = this.table.getValueAt(this.row, 0);
                // int idDoCurso = Integer.parseInt(valorId.toString());

                // 2. INSTANCIA O OBJETO DA TELA DE EDIÇÃO AQUI DENTRO
                CursoAtualizar telaEdicao = new CursoAtualizar();
                telaEdicao.setVisible(true);

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(button, "Erro: O ID do curso na tabela não é um número válido.");
            }
            
        } 
        else if (this.column == 5) { // Se for a coluna "Remover"
            // A lógica de remoção também pode ser colocada aqui
            int resposta = JOptionPane.showConfirmDialog(button, "Deseja realmente remover a linha " + this.row + "?", "Confirmar Remoção", JOptionPane.YES_NO_OPTION);
            if (resposta == JOptionPane.YES_OPTION) {
                // Lógica para remover do banco...
                System.out.println("Remoção confirmada para a linha: " + this.row);
                // ((DefaultTableModel) this.table.getModel()).removeRow(this.row); // Isso removeria da tela
            }
        }
        
        return "";
    }
}
