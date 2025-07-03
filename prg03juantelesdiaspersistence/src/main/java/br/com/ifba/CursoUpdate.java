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

public class CursoUpdate {

    private static final EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("gerenciamento-cursos");

    /**
     * Atualiza um curso no banco de dados.
     * O objeto deve conter o ID do curso a ser atualizado e os novos dados nos outros campos.
     * @param curso O objeto Curso com os dados atualizados.
     */
    public void atualizar(Curso curso) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            // encontra o correspondente no banco de dados pelo ID, atualiza os dados e retorna uma referência gerenciada.
            entityManager.merge(curso);

            transaction.commit();
            
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Erro ao atualizar o curso: " + e.getMessage(), e);
        } finally {
            entityManager.close();
        }
    }
}
