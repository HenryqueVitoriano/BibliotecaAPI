package com.biblioteca.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class genericDAO<E> {
    private static final EntityManagerFactory entityManagerFactory;
    protected EntityManager entityManager;

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
        entityManager = entityManagerFactory.createEntityManager();
    }

    public void openTransaction(){
        entityManager.getTransaction().begin();
    }

    public void closeTransaction(){
        entityManager.getTransaction().commit();
    }

    public void Insert(E entity){
        this.openTransaction();
        entityManager.persist(entity);
        this.closeTransaction();
    }
}
