/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba;

import br.com.ifba.curso.entity.Curso;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery; // Importa TypedQuery
import java.util.List;

/**
 *
 * @author juant
 */

public class CursoFind {
    
    private static final EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("gerenciamento-cursos");

    /**
     * Busca cursos cujo nome contenha o texto fornecido.
     * A busca não diferencia maiúsculas de minúsculas e encontra o texto em qualquer parte do nome.
     * @param nome O texto a ser procurado no nome dos cursos.
     * @return Uma lista de objetos Curso que correspondem ao critério de busca.
     */
    public List<Curso> buscarPorNome(String nome) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            // 1. A consulta JPQL usa a cláusula WHERE e o operador LIKE.
            //    LOWER() é usado para fazer a busca sem diferenciar maiúsculas/minúsculas.
            //    :nomeBusca é um "parâmetro nomeado". É um espaço reservado seguro.
            String jpql = "SELECT c FROM Curso c WHERE LOWER(c.nome) LIKE LOWER(:nomeBusca)";

            // 2. Cria uma TypedQuery, que já sabe que o resultado será uma lista de Cursos.
            TypedQuery<Curso> query = entityManager.createQuery(jpql, Curso.class);

            // 3. Define o valor do parâmetro nomeado.
            //    Os sinais de porcentagem (%) são curingas que significam "qualquer coisa".
            //    "%"+nome+"%" significa que o texto pode aparecer em qualquer lugar no nome.
            query.setParameter("nomeBusca", "%" + nome + "%");

            // 4. Executa a consulta e retorna a lista de resultados.
            return query.getResultList();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar cursos por nome: " + e.getMessage(), e);
        } finally {
            entityManager.close();
        }
    }
}
