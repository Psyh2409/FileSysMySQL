package com.gmail.psyh2409;

import com.gmail.psyh2409.entity.Catalog;
import com.gmail.psyh2409.entity.MyFile;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Manager {

    private EntityManagerFactory emf;

    public Manager() {
        super();
        emf = Persistence.createEntityManagerFactory("JPATest");
    }

    public EntityManagerFactory getEmf() {
        return emf;
    }

    public Long createCatalog(String name, String myPath, File file) {
        Catalog catalog;
        if (file == null) {
            catalog = new Catalog(name, myPath);
        } else {
            catalog = new MyFile(name, myPath, file);
        }
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(catalog);
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


//    private Catalog getParentFromPath(String myPath) {
//        String catName = null;
//        if (myPath.contains(".")) {
//            String[] temp = myPath.split("\\.");
//            catName = temp[temp.length - 1];
//        } else {
//            catName = myPath;
//        }
//        return findByPathName(catName);
//    }

    public Catalog findByPathName(String catPath, String catName) {
        EntityManager em = null;
        Catalog result = null;
        try {
            em = emf.createEntityManager();
            String query = "select x from Catalog x where x.myPath=:path and x.name=:name";
            Query emQuery = em.createQuery(query);
            emQuery.setParameter("path", catPath);
            emQuery.setParameter("name", catName);
            result = (Catalog) emQuery.getSingleResult();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return result;
    }

    public File createAndWriteInNewFile(String name, String content ) {
        File file = new File(name);
        if (!file.exists()) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                file.createNewFile();
                bw.write(content);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        return file;
    }

    public List<Catalog> dir(String dirName){
        List<Catalog> catalogs = new ArrayList<>();
        if (dirName == null) {
            dirName = "";
        }
        EntityManager em = null;
        try{
            em = emf.createEntityManager();
            String query = "select x from Catalog x where x.myPath=:parampampath";
            Query emQuery = em.createQuery(query);
            emQuery.setParameter("parampampath", dirName);
            catalogs = (List<Catalog>) emQuery.getResultList();
        }finally {
            if (em != null) {
                em.close();
            }
        }
        return catalogs;
    }

    public List<Catalog> ls(String dirName){
        List<Catalog> catalogs = new ArrayList<>();
        if (dirName == null) {
            dirName = "";
        }
        EntityManager em = null;
        try{
            em = emf.createEntityManager();
            String query = "select x from Catalog x where x.myPath like :parampampath";
            Query emQuery = em.createQuery(query);
            emQuery.setParameter("parampampath", dirName+"%");
            catalogs = (List<Catalog>) emQuery.getResultList();
        }finally {
            if (em != null) {
                em.close();
            }
        }
        return catalogs;
    }

    public File readFile(MyFile myFile) {
        File file = myFile.getContent();
        try {
            file.createNewFile();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return file;
    }
}
