/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba;

import br.com.ifba.curso.entity.Curso;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

/**
 *
 * @author juant
 */

public class CursoDelete {
    
    private static final EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("gerenciamento-cursos");

    /**
     * Remove um curso do banco de dados com base no seu ID.
     * @param cursoId O ID (chave primária) do curso a ser removido.
     */
    public void deletar(Long cursoId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            // Antes de remover, precisamos buscar a entidade no banco.
            // O método 'remove' exige um objeto que esteja sendo gerenciado pelo EntityManager.
            Curso cursoParaRemover = entityManager.find(Curso.class, cursoId);

            // Verifica se o curso foi realmente encontrado antes de tentar remover.
            if (cursoParaRemover != null) {
                entityManager.remove(cursoParaRemover);
                System.out.println("Curso com ID " + cursoId + " removido com sucesso.");
            } else {
                System.out.println("Curso com ID " + cursoId + " não encontrado.");
            }

            transaction.commit();

        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Erro ao deletar o curso: " + e.getMessage(), e);
        } finally {
            entityManager.close();
        }
    }
}