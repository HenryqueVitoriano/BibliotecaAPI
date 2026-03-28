package com.biblioteca.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Leitor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "Nome_leitor")
    String nome;

    @Column(name = "Qtd_livrosEmprestados")
    int quantidadeLivrosEmprestados;

    @OneToMany(mappedBy = "leitor")
    List<Livro> livroList = new ArrayList<>();

    public Leitor() {
        this(null);
    }

    public Leitor(String nome) {
        super();
        this.nome = nome;
        this.quantidadeLivrosEmprestados = 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Livro> getLivroList() {
        return livroList;
    }

    public void setLivroList(List<Livro> livroList) {
        this.livroList = livroList;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQuantidadeLivrosEmprestados() {
        return quantidadeLivrosEmprestados;
    }

    public void setQuantidadeLivrosEmprestados(int quantidadeLivrosEmprestados) {
        this.quantidadeLivrosEmprestados = quantidadeLivrosEmprestados;
    }
}
