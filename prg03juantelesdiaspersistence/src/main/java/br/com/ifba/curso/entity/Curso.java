/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.curso.entity;

import br.com.ifba.infrastructure.entity.PersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.io.Serializable;


/**
 *
 * @author juant
 */


@Entity // Diz que esta classe é uma tabela
@Table(name = "cursos")
public class Curso extends PersistenceEntity implements Serializable{

    // Coluna "codigo" na tabela, não pode ser nula e deve ser única
    @Column (name = "codigo", nullable = false, unique = true)
    private String codigo;
    
    // Coluna "nome" na tabela, não pode ser nula
    @Column (name = "nome", nullable = false)
    private String nome;
    
    // Coluna "nome" na tabela, não pode ser nula
    @Column (name = "carga_horaria", nullable = false)
    private int cargaHoraria;

    // Coluna "ativo" na tabela, indica se o curso está ativo
    @Column (name = "ativo")
    private boolean ativo;
    
    // O construtor padrão vazio é criado automaticamente.
    
    // Getters e Setters
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
