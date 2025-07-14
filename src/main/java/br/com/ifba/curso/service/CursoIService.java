/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.com.ifba.curso.service;

import br.com.ifba.curso.entity.Curso;
import java.util.List;

/**
 *
 * @author juant
 */
public interface CursoIService {

    public void save(Curso curso);
    public void update(Curso curso);
    public void delete(Curso curso);
    public List<Curso> findAll();
    public Curso findById(Long id);
    public List<Curso> findByName(String name);
}
