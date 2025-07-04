/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.infrastructure.dao;

import br.com.ifba.infrastructure.entity.PersistenceEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 *
 * @author juant
 */
@SuppressWarnings("unchecked")
public class GenericDao<Entity extends PersistenceEntity> implements GenericIDao<Entity>{

    protected static EntityManager entityManager;
    
    static {
        EntityManagerFactory factory = 
            Persistence.createEntityManagerFactory("gerenciamento-cursos");
        entityManager = factory.createEntityManager();
    }
    
    @Override
    public void save(Entity obj) {
        try {
            // Inicia a transação
            entityManager.getTransaction().begin(); 
            //Persiste o objeto no banco de dados
            entityManager.persist(obj);        
            // Comita a transação (confirma as alterações)
            entityManager.getTransaction().commit(); 
        } 
        catch (Exception e) {
            // Se ocorrer qualquer erro, faz o rollback da transação
            if (entityManager != null && entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            // Relança a exceção para que a camada de visão (interface) saiba que algo deu errado
            throw new RuntimeException("Erro ao salvar o curso no banco de dados: " + e.getMessage(), e);
        }
    }
    
    @Override
    public void update(Entity obj) {
        try {
            // Inicia a transação
            entityManager.getTransaction().begin(); 
            //Persiste o objeto no banco de dados
            entityManager.merge(obj);        
            // Comita a transação (confirma as alterações)
            entityManager.getTransaction().commit(); 
        } 
        catch (Exception e) {
            // Se ocorrer qualquer erro, faz o rollback da transação
            if (entityManager != null && entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            // Relança a exceção para que a camada de visão (interface) saiba que algo deu errado
            throw new RuntimeException("Erro ao salvar o curso no banco de dados: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(Entity obj) {
        try {
            // Inicia a transação
            entityManager.getTransaction().begin(); 
            //Persiste o objeto no banco de dados
            entityManager.remove(obj);        
            // Comita a transação (confirma as alterações)
            entityManager.getTransaction().commit(); 
        } 
        catch (Exception e) {
            // Se ocorrer qualquer erro, faz o rollback da transação
            if (entityManager != null && entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            // Relança a exceção para que a camada de visão (interface) saiba que algo deu errado
            throw new RuntimeException("Erro ao salvar o curso no banco de dados: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Entity> findAll() {
        try {
            /* Cria a consulta usando JPQL (Java Persistence Query Language).
            etorna a lista de dados encontrados.*/
        return entityManager.createQuery(("from " + getTypeClass().getName())).getResultList();
        }
        catch (Exception e) {
            // Em caso de erro na consulta, relança a exceção.
            throw new RuntimeException("Erro ao buscar os cursos no banco de dados: " + e.getMessage(), e);
        }
    }

    @Override
    public Entity findById(Long id) {
        return (Entity) entityManager.find(getTypeClass(), id);
    }
    
    protected Class<?> getTypeClass () {
        Class<?> clazz = (Class<?>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return clazz;
    }
}