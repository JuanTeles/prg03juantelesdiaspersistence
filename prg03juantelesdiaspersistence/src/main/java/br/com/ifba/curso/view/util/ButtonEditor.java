/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.curso.view.util; 

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
        // LÓGICA DE TESTE SIMPLES:
        // Verifica em qual coluna o clique ocorreu e mostra uma mensagem.
        if (column == 4) { // Coluna "Editar"
            JOptionPane.showMessageDialog(button, "Botão EDITAR clicado na linha: " + row);
            
        } else if (column == 5) { // Coluna "Remover"
            JOptionPane.showMessageDialog(button, "Botão REMOVER clicado na linha: " + row);
        }
        
        // Retorna um valor qualquer, pois nosso botão não "salva" um valor na célula.
        return "";
    }
}
