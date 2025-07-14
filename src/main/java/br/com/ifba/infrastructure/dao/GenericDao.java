/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package br.com.ifba.infrastructure.dao;

import br.com.ifba.infrastructure.entity.PersistenceEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.lang.reflect.ParameterizedType;
import java.util.List;

@SuppressWarnings("unchecked")
@Repository
public class GenericDao<Entity extends PersistenceEntity> implements GenericIDao<Entity> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Entity obj) {
        entityManager.persist(obj);
    }

    @Override
    public void update(Entity obj) {
        entityManager.merge(obj);
    }

    @Override
    public void delete(Entity obj) {
        Entity managed = entityManager.contains(obj) ? obj : entityManager.merge(obj);
        entityManager.remove(managed);
    }

    @Override
    public List<Entity> findAll() {
        return entityManager.createQuery("FROM " + getTypeClass().getName(), (Class<Entity>) getTypeClass())
                            .getResultList();
    }

    @Override
    public Entity findById(Long id) {
        return entityManager.find((Class<Entity>) getTypeClass(), id);
    }

    private Class<?> getTypeClass() {
        return (Class<?>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
}
