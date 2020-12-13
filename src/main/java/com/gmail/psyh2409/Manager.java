package com.gmail.psyh2409;

import com.gmail.psyh2409.entity.Catalog;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class Manager {

    private EntityManagerFactory emf;

    public Manager() {
        super();
        emf = Persistence.createEntityManagerFactory("JPATest");
    }

    public EntityManagerFactory getEmf() {
        return emf;
    }

    public Long createCatalog(String name, String myPath) {
        var catalog = new Catalog(name, myPath);
        Catalog parCat = getParentFromPath(myPath);
        parCat.setSubCatalog(catalog);
        EntityManager em = null;

        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(catalog);
            em.merge(parCat);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return catalog.getId();
    }

    public void emfClose() {
        emf.close();
    }

    private Catalog getParentFromPath(String myPath) {
        String catName = null;
        if (myPath.contains(".")) {
            String[] temp = myPath.split("\\.");
            catName = temp[temp.length - 1];
        } else {
            catName = myPath;
        }
        return findByName(catName);
    }

    private Catalog findByName(String catalogName) {
        EntityManager em = null;
        Catalog result = null;
        try {
            em = emf.createEntityManager();
            String query = "select x from Catalog x where x.name=:parampampath";
            Query emQuery = em.createQuery(query);
            emQuery.setParameter("parampampath", catalogName);
            result = (Catalog) emQuery.getSingleResult();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return result;
    }
}
