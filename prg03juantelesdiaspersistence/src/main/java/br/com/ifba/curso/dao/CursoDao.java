/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.curso.dao;

import br.com.ifba.curso.entity.Curso;
import br.com.ifba.infrastructure.dao.GenericDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.util.List;

/**
 *
 * @author juant
 */
public class CursoDao extends GenericDao<Curso> implements CursoIDao{
    
    private static final EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("gerenciamento-cursos");
    
    @Override
    public List<Curso> findByName(String nome) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            /* A consulta JPQL usa a cláusula WHERE e o operador LIKE.
            //    LOWER() é usado para fazer a busca sem diferenciar maiúsculas/minúsculas.
                  :nomeBusca é um "parâmetro nomeado". É um espaço reservado seguro. */
            String jpql = "SELECT c FROM Curso c WHERE LOWER(c.nome) LIKE LOWER(:nomeBusca)";

            // Cria uma TypedQuery, que já sabe que o resultado será uma lista de Cursos.
            TypedQuery<Curso> query = entityManager.createQuery(jpql, Curso.class);
            query.setParameter("nomeBusca", "%" + nome + "%");

            // Executa a consulta e retorna a lista de resultados.
            return query.getResultList();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar cursos por nome: " + e.getMessage(), e);
        }
    }
}
