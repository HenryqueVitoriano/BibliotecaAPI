package main.com.biblioteca.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true)
    String nome;

    @OneToMany(mappedBy = "autor")
    List<Livro> livrosList = new ArrayList<>();

    public Autor() {
    }

    public Autor(String nome) {
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

    public List<Livro> getLivrosList() {
        return livrosList;
    }

    public void setLivrosList(List<Livro> livrosList) {
        this.livrosList = livrosList;
    }
}
