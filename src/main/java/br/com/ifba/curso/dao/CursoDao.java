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
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author juant
 */
@Repository
public class CursoDao extends GenericDao<Curso> implements CursoIDao{
    
    // injetando o entitymanager gerenciado pelo spring
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Curso> findByName(String nome) {
        // 3. USE O ENTITYMANAGER INJETADO DIRETAMENTE.
        //    O Spring gerencia o ciclo de vida dele, então não precisa de try-with-resources.
        try {
            /* A consulta JPQL usa a cláusula WHERE e o operador LIKE.
              LOWER() é usado para fazer a busca sem diferenciar maiúsculas/minúsculas.
              :nomeBusca é um "parâmetro nomeado". É um espaço reservado seguro. */
            String jpql = "SELECT c FROM Curso c WHERE LOWER(c.nome) LIKE LOWER(:nomeBusca)";

            // Cria uma TypedQuery, que já sabe que o resultado será uma lista de Cursos.
            TypedQuery<Curso> query = entityManager.createQuery(jpql, Curso.class);
            query.setParameter("nomeBusca", "%" + nome + "%");

            // Executa a consulta e retorna a lista de resultados.
            return query.getResultList();

        } 
        catch (Exception e) {
            // É uma boa prática logar o erro ou tratá-lo de forma mais específica
            throw new RuntimeException("Erro ao buscar cursos por nome: " + e.getMessage(), e);
        }
    }
}