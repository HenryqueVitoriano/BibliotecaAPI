package com.biblioteca.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class genericDAO<E> {
    private static EntityManagerFactory entityManagerFactory;
    protected EntityManager entityManager;
    private Class<E> classe;

    static {
        try{
            entityManagerFactory = Persistence.createEntityManagerFactory("biblioteca_JPA");
        }catch (Exception e){
            throw new RuntimeException("ERROR");
        }
    }

    public genericDAO() {
        this(null);
    }

    public genericDAO(Class<E> classe) {
        super();
        this.classe = classe;
        entityManager = entityManagerFactory.createEntityManager();
    }

    public genericDAO<E> openTransaction(){
        entityManager.getTransaction().begin();
        return this;
    }

    public genericDAO<E> closeTransaction(){
        entityManager.getTransaction().commit();
        return this;
    }

    public genericDAO<E> Insert(E entity){
        this.openTransaction();
        entityManager.persist(entity);
        this.closeTransaction();
        return this;
    }
}
