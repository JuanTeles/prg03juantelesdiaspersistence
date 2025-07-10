/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.curso.service;

import br.com.ifba.curso.dao.CursoDao;
import br.com.ifba.curso.dao.CursoIDao;
import br.com.ifba.curso.entity.Curso;
import br.com.ifba.curso.util.StringUtil;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;

/**
 *
 * @author juant
 */
public class CursoService implements CursoIService{
    
    private final CursoIDao cursoDao = new CursoDao();

    @Override
    public void save(Curso curso) throws IllegalArgumentException {
        // validação do objeto inteiro
        if (curso == null) {
            throw new IllegalArgumentException("Dados do curso não podem ser nulos.");
        }
        
        // validações de regras de negócio
        if (StringUtil.isNullOrEmpty(curso.getNome())) {
            throw new IllegalArgumentException("O nome do curso é obrigatório.");
        }
        if (StringUtil.isNullOrEmpty(curso.getCodigo())) {
            throw new IllegalArgumentException("O código do curso é obrigatório.");
        }
        if (curso.getCargaHoraria() <= 0) {
            throw new IllegalArgumentException("A carga horária deve ser maior que zero.");
        }

        // retira os espaços extras antes de salvar no banco
        curso.setNome(StringUtil.normalize(curso.getNome()));
        curso.setCodigo(StringUtil.normalize(curso.getCodigo()));


        // se todas as validações passaram, salva o curso.
        cursoDao.save(curso);
    }

    @Override
    public void update(Curso curso) throws IllegalArgumentException{
        // validação da entrada: o objeto e seu ID devem ser válidos
        if (curso == null || curso.getId() == null || curso.getId() <= 0) {
            throw new IllegalArgumentException("Não é possível excluir um curso com dados ou ID inválidos.");
        }

        // validação específica do UPDATE: O ID deve ser válido.
        if (curso.getId() == null || curso.getId() <= 0) {
            throw new IllegalArgumentException("Não é possível atualizar um curso sem um ID válido.");
        }

        // validações de regras de negócio
        if (StringUtil.isNullOrEmpty(curso.getNome())) {
            throw new IllegalArgumentException("O nome do curso é obrigatório.");
        }
        if (StringUtil.isNullOrEmpty(curso.getCodigo())) {
            throw new IllegalArgumentException("O código do curso é obrigatório.");
        }
        if (curso.getCargaHoraria() <= 0) {
            throw new IllegalArgumentException("A carga horária deve ser maior que zero.");
        }

        // se todas as validações passaram, atualiza o curso.
        cursoDao.update(curso);
    }

    @Override
    public void delete(Curso curso) throws IllegalArgumentException{
        if(curso == null){
            throw new RuntimeException("Dados do curso não preenchidos");
        }
        
        // se a verificação passar, deleta o curso.
        cursoDao.delete(curso);        
    }

    @Override
    public List<Curso> findAll(){
        return cursoDao.findAll();
    }

    @Override
    public Curso findById(Long id) throws IllegalArgumentException{
        // O ID deve ser válido.
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("O ID fornecido para busca é inválido.");
        }
        
        // se a validação passar, executa a busca
        return cursoDao.findById(id);
    }

    @Override
    public List<Curso> findByName(String name) throws RuntimeException{
        // validação do critério de busca
        if (StringUtil.isNullOrEmpty(name)) {
            throw new IllegalArgumentException("O nome para a busca não pode ser nulo ou vazio.");
        }
        
        // se a validação passar, executa a busca
        return cursoDao.findByName(name);
    }
}
