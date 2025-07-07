/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.curso.service;

import br.com.ifba.curso.dao.CursoDao;
import br.com.ifba.curso.dao.CursoIDao;
import br.com.ifba.curso.entity.Curso;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;

/**
 *
 * @author juant
 */
public class CursoService implements CursoIService{
    
    private final CursoIDao cursoDao = new CursoDao();

    @Override
    public void save(Curso curso) throws RuntimeException {
        if(curso == null){
            throw new RuntimeException("Dados do curso não preenchidos");
        }
        else {
            cursoDao.save(curso);
        }
    }

    @Override
    public void update(Curso curso) throws RuntimeException{
        if(curso == null){
            throw new RuntimeException("Dados do curso não preenchidos");
        } 
        else {
            cursoDao.update(curso);
        }
    }

    @Override
    public void delete(Curso curso) throws RuntimeException{
        if(curso == null){
            throw new RuntimeException("Dados do curso não preenchidos");
        }
        else {
            cursoDao.delete(curso);
        }
    }

    @Override
    public List<Curso> findAll() throws RuntimeException{
        return cursoDao.findAll();
    }

    @Override
    public Curso findById(Long id) throws RuntimeException{
        if (id == null) {
            throw new RuntimeException ("ID inválido!");
        }
        return cursoDao.findById(id);
    }

    @Override
    public List<Curso> findByName(String name) throws RuntimeException{
        return cursoDao.findByName(name);
    }
}
