package com.biblioteca.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Editora {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true)
    String nome;

    @OneToMany(mappedBy = "editora")
    List<Livro> livrosEditora = new ArrayList<>();

    public Editora() {
    }

    public Editora(String nome) {
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Livro> getLivrosEditora() {
        return livrosEditora;
    }

    public void setLivrosEditora(List<Livro> livrosEditora) {
        this.livrosEditora = livrosEditora;
    }
}
