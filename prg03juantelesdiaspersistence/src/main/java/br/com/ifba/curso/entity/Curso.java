/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.curso.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


/**
 *
 * @author juant
 */


@Entity // 1. Diz que esta classe é uma tabela
public class Curso {

    @Id // 2. Define que este atributo é a chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Deixa o MySQL controlar a numeração (AUTO_INCREMENT)
    private Long id;

    // Não precisa de construtores, getters, setters ou outros atributos PARA ESTE TESTE.
    // O construtor padrão vazio é criado automaticamente.
}
