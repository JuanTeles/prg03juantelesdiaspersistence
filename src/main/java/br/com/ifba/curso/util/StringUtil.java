/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.curso.util;

/**
 *
 * @author juant
 */
public class StringUtil {

    // Verifica se uma string é nula ou vazia (incluindo espaços em branco)
    public static boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    // Verifica se a string tem um comprimento mínimo
    public static boolean hasMinLength(String value, int minLength) {
        return value != null && value.trim().length() >= minLength;
    }

    // Verifica se a string tem apenas letras
    public static boolean isAlphabetic(String value) {
        return value != null && value.matches("^[a-zA-ZáéíóúÁÉÍÓÚãõÃÕçÇ\\s]+$");
    }

    // Verifica se a string tem apenas números
    public static boolean isNumeric(String value) {
        return value != null && value.matches("\\d+");
    }

    // Remove espaços extras e retorna string normalizada
    public static String normalize(String value) {
        return value == null ? null : value.trim().replaceAll("\\s+", " ");
    }

    // Verifica se um e-mail é válido
    public static boolean isValidEmail(String email) {
        return email != null && email.matches("^[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,}$");
    }

    // Verifica se a string contém caracteres especiais
    public static boolean containsSpecialCharacters(String value) {
        return value != null && value.matches(".[^a-zA-Z0-9 ].");
    }

    // Verifica se a string está em formato de CPF (xxx.xxx.xxx-xx)
    public static boolean isValidCPFFormat(String cpf) {
        return cpf != null && cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}");
    }

}