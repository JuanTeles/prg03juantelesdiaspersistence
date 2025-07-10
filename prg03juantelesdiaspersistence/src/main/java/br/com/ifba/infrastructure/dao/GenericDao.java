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

@SuppressWarnings("unchecked")
public class GenericDao<Entity extends PersistenceEntity> implements GenericIDao<Entity> {

    // A Factory é pesada, thread-safe e deve ser criada uma vez.
    protected static final EntityManagerFactory entityManagerFactory;

    static {
        // Bloco estático para inicializar a Factory uma única vez.
        entityManagerFactory = Persistence.createEntityManagerFactory("gerenciamento-cursos");
    }

    // Método para fechar a factory quando a aplicação for encerrada.
    public static void shutdown() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }

    // O EntityManager será obtido e fechado em cada método.
    @Override
    public void save(Entity obj) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(obj);
            entityManager.getTransaction().commit(); 
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw new RuntimeException("Erro ao salvar a entidade no banco de dados.", e);
        } finally {
            // ESSENCIAL: Fechar o EntityManager para liberar os recursos.
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }

    @Override
    public void update(Entity obj) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(obj);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw new RuntimeException("Erro ao atualizar a entidade no banco de dados.", e);
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }

    @Override
    public void delete(Entity obj) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            if (!entityManager.contains(obj)) {
                obj = entityManager.merge(obj);
            }
            entityManager.remove(obj);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw new RuntimeException("Erro ao deletar a entidade no banco de dados.", e);
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }

    @Override
    public List<Entity> findAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.createQuery("FROM " + getTypeClass().getName(), (Class<Entity>) getTypeClass()).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar as entidades no banco de dados.", e);
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }

    @Override
    public Entity findById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.find((Class<Entity>) getTypeClass(), id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar entidade por ID no banco de dados.", e);
        } finally {
             if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }

    private Class<?> getTypeClass() {
        return (Class<?>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
}