package com.biblioteca;

import com.biblioteca.service.Biblioteca;

public class main {
    public static void main(String[] args) throws Throwable {
        Biblioteca biblioteca = new Biblioteca();
        System.out.println("BEM VINDO");
        biblioteca.rodar();
    }
}
