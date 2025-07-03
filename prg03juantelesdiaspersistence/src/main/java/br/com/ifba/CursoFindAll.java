/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba;

import br.com.ifba.curso.entity.Curso;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List; // Importa a interface List

/**
 *
 * @author juant
 */
public class CursoFindAll {
    // A EntityManagerFactory é compartilhada, pois sua criação é custosa.
    private static final EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("gerenciamento-cursos");
            
    /**
     * Método que busca todos os objetos Curso no banco de dados.
     * @return Uma lista (List) contendo todos os cursos encontrados.
     */
    public List<Curso> buscarTodos() {
        // O EntityManager gerencia a conexão e as entidades.
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            // 1. Cria a consulta usando JPQL (Java Persistence Query Language).
            // A consulta "SELECT c FROM Curso c" significa:
            // "Selecione todos os objetos 'c' onde 'c' é uma entidade do tipo Curso".
            // Note que "Curso" é o nome da sua classe @Entity, não o nome da tabela no SQL.
            List<Curso> cursos = entityManager.createQuery("SELECT c FROM Curso c", Curso.class)
                                              .getResultList();
            
            // 2. Retorna a lista de cursos encontrados.
            return cursos;
            
        } catch (Exception e) {
            // Em caso de erro na consulta, relança a exceção.
            throw new RuntimeException("Erro ao buscar os cursos no banco de dados: " + e.getMessage(), e);
        } finally {
            // 3. Garante que o EntityManager seja sempre fechado para liberar recursos.
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }
}