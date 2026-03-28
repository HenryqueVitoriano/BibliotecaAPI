package com.biblioteca.dao;

import com.biblioteca.model.Leitor;

public class leitorDAO extends genericDAO<Object> {

    public leitorDAO() {
        super();
    }

    public Leitor buscarLeitorID(Long ID) {
        return entityManager.find(Leitor.class, ID);

    }

    public Leitor novoLeitor(String nome) {
        Leitor leitor = new Leitor(nome);
        this.Insert(leitor);

        return leitor;
    }
}
