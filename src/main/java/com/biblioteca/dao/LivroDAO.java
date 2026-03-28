package com.biblioteca.dao;

import com.biblioteca.model.Livro;

import javax.persistence.NoResultException;

public class LivroDAO extends genericDAO<Livro> {

    public LivroDAO() {
        super();
    }

    public Livro buscarLivroTitulo(String titulo){

        String jpql = "SELECT l FROM Livro l WHERE l.Titulo = :titulo";
        Livro livro = new Livro();

            try {
                livro = entityManager
                        .createQuery(jpql, Livro.class)
                        .setParameter("titulo", titulo)
                        .getSingleResult();

            } catch (NoResultException e) {
                return livro;
            }

        return livro;
    }

    public boolean isLivroEmprestado(Livro livro){
        return livro.getEmprestado();

    }
}
