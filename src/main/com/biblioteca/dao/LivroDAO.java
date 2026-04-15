package main.com.biblioteca.dao;

import main.com.biblioteca.consulta.notaLivro;
import main.com.biblioteca.model.Livro;
import net.bytebuddy.asm.Advice;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;


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
    public void emprestarLivro(Livro livro){
        this.openTransaction();
        livro.setEmprestado(true);
        this.closeTransaction();
    }

    public Double consultarMedia(String consulta){
        Query query = entityManager.createNamedQuery(consulta);

        notaLivro resultado = (notaLivro) query.getSingleResult();
        return resultado.getMedia();

    }
}
