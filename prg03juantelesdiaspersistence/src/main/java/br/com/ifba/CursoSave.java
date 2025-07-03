/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba;

import br.com.ifba.curso.entity.Curso; // Importa a sua entidade Curso
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

/**
 *
 * @author juant
 */
public class CursoSave {
    // A EntityManagerFactory é um objeto caro de ser criado,
    // então o criamos uma vez e o reutilizamos.
    private static final EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("gerenciamento-cursos");

    /**
     * Método que recebe um objeto Curso e o persiste no banco de dados.
     * @param curso O objeto Curso preenchido para ser salvo.
     */
    public void salvar(Curso curso) {
        // O EntityManager é quem gerencia as entidades e a conexão.
        // É importante criar e fechar um para cada operação.
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        // A EntityTransaction controla as transações com o banco.
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            // 1. Inicia a transação
            transaction.begin();
            
            // 2. Persiste o objeto no banco de dados
            entityManager.persist(curso);
            
            // 3. Comita a transação (confirma as alterações)
            transaction.commit();
            
        } catch (Exception e) {
            // Se ocorrer qualquer erro, faz o rollback da transação
            if (transaction.isActive()) {
                transaction.rollback();
            }
            // Relança a exceção para que a camada de visão (interface) saiba que algo deu errado
            throw new RuntimeException("Erro ao salvar o curso no banco de dados: " + e.getMessage(), e);
        } finally {
            // 4. Fecha o EntityManager para liberar os recursos
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }
}