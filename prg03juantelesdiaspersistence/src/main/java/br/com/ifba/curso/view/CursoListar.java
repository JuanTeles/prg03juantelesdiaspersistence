/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package br.com.ifba.curso.view;

import br.com.ifba.curso.dao.CursoDao;
import br.com.ifba.curso.dao.CursoIDao;
import br.com.ifba.curso.entity.Curso;
import br.com.ifba.curso.view.util.ButtonRenderer;
import java.awt.Image;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author juant
 */
public class CursoListar extends javax.swing.JFrame {
    
    // Adiciona atributos para as classes de buscas
    private List<Curso> listaDeCursos; // guarda a lista de cursos carregada
    private final CursoIDao cursoDao = new CursoDao();
    
    /**
     * Creates new form CursoListar
    */    
    public CursoListar() {
        initComponents();
        carregarDados(); // carrega os dados na tabela
        setLocationRelativeTo(null); // inicializa o jframe no meio da tela
        configurarTabela(); // Novo método para organizar
        
        try {
            // Define o tamanho desejado para o ícone da lupa
            int iconSize = 20; // Ajuste o tamanho conforme o botão

            // Usa um método para carregar e redimensionar a imagem dos botões
            ImageIcon searchIcon = scaleImage("/imagens/search.png", iconSize);
            ImageIcon addIcon = scaleImage("/imagens/add.png", iconSize);

            // Aplica o ícone ao botão
            btnPesquisa.setIcon(searchIcon);
            btnAdiciona.setIcon(addIcon);

            // remove o texto do botão para exibir apenas o ícone
            btnPesquisa.setText("");
            btnAdiciona.setText("");

        } 
        catch (Exception e) {
            // Tratamento de erro caso a imagem não seja encontrada
            System.err.println("Erro ao carregar ícone de pesquisa: " + e.getMessage());
            btnPesquisa.setText("Buscar"); // Garante que o botão tenha um texto se o ícone falhar
            btnAdiciona.setText("Adicionar"); // Garante que o botão tenha um texto se o ícone falhar
        }
        
        adicionarListenerDeCliqueNaTabela();
    }
     
    private ImageIcon scaleImage(String path, int size) {
        ImageIcon icon = new ImageIcon(getClass().getResource(path));
        Image img = icon.getImage();
        Image scaledImg = img.getScaledInstance(size, size, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImg);
    }
    
    private void adicionarListenerDeCliqueNaTabela() {
        // Adiciona um "ouvinte" de eventos de mouse à nossa tabela
        this.tblCursos.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Pega a linha e a coluna exatas onde o clique do mouse ocorreu
                int linha = tblCursos.rowAtPoint(evt.getPoint());
                int coluna = tblCursos.columnAtPoint(evt.getPoint());

                // Garante que o clique foi em uma linha válida (e não no cabeçalho, por exemplo)
                if (linha >= 0) {

                    // AÇÃO DE EDITAR
                    if (coluna == 4) { // Verifica se o clique foi na 5ª coluna ("Editar")
                        Curso cursoParaEditar = listaDeCursos.get(linha);

                        System.out.println("Clicou em Editar para o curso: " + cursoParaEditar.getNome());

                        CursoAtualizar telaEdicao = new CursoAtualizar(cursoParaEditar, CursoListar.this);
                        telaEdicao.setVisible(true);
                        // ATUALIZA A TABELA DE FORMA SIMPLES E SEGURA
                        carregarDados(); 
                    } 
                    // AÇÃO DE REMOVER
                    else if (coluna == 5) { // Verifica se o clique foi na 6ª coluna ("Remover")
                        Curso cursoParaRemover = listaDeCursos.get(linha);
                        // Pede confirmação ao usuário antes de remover
                        int resposta = JOptionPane.showConfirmDialog(
                                CursoListar.this, // Parent component
                                "Deseja realmente remover o curso: \"" + cursoParaRemover.getNome() + "\"?", 
                                "Confirmar Remoção", 
                                JOptionPane.YES_NO_OPTION);

                        if (resposta == JOptionPane.YES_NO_OPTION) {
                            try {
                                // Usa o ID do objeto identificado corretamente
                                cursoDao.delete(cursoParaRemover);

                                // Mostra a mensagem de sucesso
                                JOptionPane.showMessageDialog(CursoListar.this, "Curso removido com sucesso!");

                                // ATUALIZA A TABELA DE FORMA SIMPLES E SEGURA
                                carregarDados(); 

                            } 
                            catch (Exception e) {
                                JOptionPane.showMessageDialog(CursoListar.this, "Erro ao remover curso: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        });
    }
    
    // Usando o índice da coluna!
    private void configurarTabela() {
        // Define a altura da linha para 40 pixels. Ajuste conforme necessário.
        tblCursos.setRowHeight(32);
        
        // Pega a coluna na posição 4 (a quinta coluna)
        tblCursos.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());
        // Pega a coluna na posição 5 (a sexta coluna)
        tblCursos.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
        
    }

    // carrega os dados do banco de dados
    public void carregarDados() {
        try {
            // Usa o buscador para obter a lista de cursos do banco
            this.listaDeCursos = cursoDao.findAll();

            // Pega o modelo da tabela
            DefaultTableModel model = (DefaultTableModel) tblCursos.getModel();
            model.setRowCount(0); // Limpa a tabela para evitar duplicatas

            // loop que adiciona cada curso como uma nova linha
            for (int i = 0; i < listaDeCursos.size(); i++) {
    
                // Pega o curso que está na posição 'i' da lista
                Curso curso = listaDeCursos.get(i);

                // adiciona linha 
                model.addRow(new Object[]{
                    curso.getCodigo(),
                    curso.getNome(),
                    curso.getCargaHoraria(),
                    curso.isAtivo(), // Exibe true/false na coluna
                    "Editar",
                    "Remover"
                });
            }
        } 
        catch (Exception e) {
            // Mostra uma mensagem de erro se a conexão com o banco falhar
            JOptionPane.showMessageDialog(this, 
                "Erro ao conectar ao banco de dados para listar os cursos.\n" +
                "Verifique sua conexão e as configurações de firewall.\n\n" +
                "Detalhes do erro: " + e.getMessage(),
                "Erro de Conexão", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); // Imprime o erro detalhado no console para depuração
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtPesquisar = new javax.swing.JTextField();
        btnAdiciona = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCursos = new javax.swing.JTable();
        btnPesquisa = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(51, 51, 255));
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));
        jPanel1.setForeground(new java.awt.Color(0, 51, 255));

        txtPesquisar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtPesquisar.setToolTipText("Pesquisar...");

        btnAdiciona.setText("adc");
        btnAdiciona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionaActionPerformed(evt);
            }
        });

        tblCursos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Codigo", "Nome", "Carga Horaria", "Ativo", "Editar", "Remover"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Boolean.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblCursos.setToolTipText("");
        jScrollPane1.setViewportView(tblCursos);

        btnPesquisa.setText("src");
        btnPesquisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnPesquisa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAdiciona))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 614, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnAdiciona)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(btnPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAdicionaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionaActionPerformed
        // TODO add your handling code here:
        // Cria uma nova instância (um novo objeto) da tela de cadastro
        CursoCadastrar telaCadastro = new CursoCadastrar(this);

        // Torna essa nova tela visível para o usuário
        telaCadastro.setVisible(true);
    }//GEN-LAST:event_btnAdicionaActionPerformed

    private void btnPesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisaActionPerformed
        // TODO add your handling code here:
        // Pega o texto que o usuário digitou no campo de pesquisa
        String nomePesquisa = txtPesquisar.getText();

        // Verifica se o campo de pesquisa está vazio
        if (nomePesquisa.isBlank()) {
            // Se estiver vazio, simplesmente recarrega todos os dados da tabela
            this.carregarDados();
            return; 
        }

        try {
            // Usa o buscadorNome para consultar o banco com o texto digitado
            this.listaDeCursos = cursoDao.findByName(nomePesquisa);

            // Pega o modelo da tabela
            DefaultTableModel model = (DefaultTableModel) tblCursos.getModel();
            model.setRowCount(0); // Limpa a tabela para exibir apenas os resultados da pesquisa

            // Se a busca não retornar resultados, exibe uma mensagem
            if (listaDeCursos.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nenhum curso encontrado com o nome informado.", 
                        "Pesquisa", 
                        JOptionPane.INFORMATION_MESSAGE);
            } 
            else {
                // Preenche a tabela com os cursos encontrados na pesquisa
                for (int i = 0; i < listaDeCursos.size(); i++) {
    
                    // Pega o curso que está na posição 'i' da lista
                    Curso curso = listaDeCursos.get(i);

                    // adiciona linha 
                    model.addRow(new Object[]{
                        curso.getCodigo(),
                        curso.getNome(),
                        curso.getCargaHoraria(),
                        curso.isAtivo(), // Exibe true/false na coluna
                        "Editar",
                        "Remover"
                    });
                }
            }
        } 
        catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                    "Ocorreu um erro ao realizar a busca.\nDetalhes: " + e.getMessage(), 
                    "Erro de Pesquisa", 
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnPesquisaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CursoListar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CursoListar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CursoListar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CursoListar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CursoListar().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdiciona;
    private javax.swing.JButton btnPesquisa;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblCursos;
    private javax.swing.JTextField txtPesquisar;
    // End of variables declaration//GEN-END:variables
}
