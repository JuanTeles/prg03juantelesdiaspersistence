/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.curso.controller;

import br.com.ifba.curso.entity.Curso;
import br.com.ifba.curso.service.CursoService;
import java.util.List;

/**
 *
 * @author juant
 */
public class CursoController implements CursoIController {
    
    private final CursoService cursoService = new CursoService();

    @Override
    public void save(Curso curso) {
        cursoService.save(curso);
    }

    @Override
    public void update(Curso curso) {
        cursoService.update(curso);
    }

    @Override
    public void delete(Curso curso) {
        cursoService.delete(curso);
    }

    @Override
    public List<Curso> findAll() {
        return cursoService.findAll();
    }

    @Override
    public Curso findById(Long id) {
        return cursoService.findById(id);
    }

    @Override
    public List<Curso> findByName(String name) {
        return cursoService.findByName(name);
    }
    
}
