/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.curso.view.util;

import java.awt.Component;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;
/**
 *
 * @author juant
 */

public class ButtonRenderer extends JButton implements TableCellRenderer {

    private ImageIcon editIcon;
    private ImageIcon deleteIcon;

    public ButtonRenderer() {
        setOpaque(true);

        // Define o tamanho desejado para os ícones (ex: 32x32 pixels)
        // Deve ser um pouco menor que a altura da linha para ter uma margem.
        int iconSize = 18;

        try {
            // Carrega e redimensiona as imagens usando um método auxiliar
            editIcon = scaleImage("/imagens/update.png", iconSize);
            deleteIcon = scaleImage("/imagens/delete.png", iconSize);
        } catch (Exception e) {
            System.err.println("Erro ao carregar ou redimensionar ícones: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // --- MÉTODO AUXILIAR NOVO ---
    // Este método carrega uma imagem e a redimensiona para o tamanho especificado
    private ImageIcon scaleImage(String path, int size) {
        ImageIcon icon = new ImageIcon(getClass().getResource(path));
        Image img = icon.getImage();
        // Image.SCALE_SMOOTH prioriza a qualidade da imagem redimensionada
        Image scaledImg = img.getScaledInstance(size, size, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImg);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        
        // Esta parte do código NÃO MUDA.
        // Ela já vai usar os ícones redimensionados que foram preparados no construtor.

        if (isSelected) {
            setForeground(table.getSelectionForeground());
            setBackground(table.getSelectionBackground());
        } else {
            setForeground(table.getForeground());
            setBackground(UIManager.getColor("Button.background"));
        }

        if (column == 4) { // Coluna Editar
            setIcon(editIcon);
        } else if (column == 5) { // Coluna Remover
            setIcon(deleteIcon);
        }
        
        // Removemos o texto para exibir apenas o ícone
        setText("");

        return this;
    }
}