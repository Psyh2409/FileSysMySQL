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
import java.util.List;

public class Manager {

    private EntityManagerFactory emf;

    public Manager() {
        super();
        emf = Persistence.createEntityManagerFactory("FSinDB");
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
            if (em != null) {
                em.getTransaction().rollback();
            }
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

    public Catalog findByPathName(String catPath, String catName) {
        EntityManager em = null;
        Catalog result;
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

    public File createAndWriteInNewFile(String name, String content) {
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

    public List<Catalog> dir(String dirName) {
        List<Catalog> catalogs;
        if (dirName == null) {
            dirName = "";
        }
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            String query = "select x from Catalog x where x.myPath=:parampampath";
            Query emQuery = em.createQuery(query);
            emQuery.setParameter("parampampath", dirName);
            catalogs = (List<Catalog>) emQuery.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return catalogs;
    }

    public List<Catalog> ls(String dirName) {
        List<Catalog> catalogs;
        if (dirName == null) {
            dirName = "";
        }
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            String query = "select x from Catalog x where x.myPath like :parampampath";
            Query emQuery = em.createQuery(query);
            emQuery.setParameter("parampampath", dirName + "%");
            catalogs = (List<Catalog>) emQuery.getResultList();
        } finally {
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

    public boolean deleteByPathName(String path, String name) {
        return deleteById(findByPathName(path, name).getId());
    }

    public boolean deleteById(Long id) {
        Catalog catalog = getById(id);
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            String query = "delete from Catalog x where x.myPath like :pathname";
            Query emQuery = em.createQuery(query);
            emQuery.setParameter("pathname", catalog.getMyPath() + "." + catalog.getName() + "%");
            String query1 = "delete from Catalog x where x.id like :id";
            Query emQuery1 = em.createQuery(query1);
            emQuery1.setParameter("id", catalog.getId());
            em.getTransaction().begin();
            emQuery.executeUpdate();
            emQuery1.executeUpdate();
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return false;
    }

    public Catalog getById(Long id) {
        EntityManager em = null;
        Catalog result;
        try {
            em = emf.createEntityManager();
            String query = "select x from Catalog x where x.id=:id";
            Query emQuery = em.createQuery(query);
            emQuery.setParameter("id", id);
            result = (Catalog) emQuery.getSingleResult();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return result;
    }

    /*Доп домашка:
Допилить домашку про файловую систему так,
чтобы можно было выбрать на реальном диске каталог
и перенести его в вашу виртуальную фс в базе*/
    public void addAllFromFileToDB(File f, String path) {
        if (path == null) path = "";
        if (f != null) {
            if (f.exists()) {
                    if (f.isDirectory()) {
                        createCatalog(f.getName(), path, null);
                        if (f.listFiles().length > 0) {
                            for (File ff : f.listFiles()) {
                                Catalog catalog = findByPathName(path, f.getName());
                                addAllFromFileToDB(ff,
                                        catalog.getMyPath().isEmpty()
                                                ?catalog.getMyPath().concat(catalog.getName())
                                                :catalog.getMyPath().concat(".").concat(catalog.getName()));
                            }
                        }
                    } else {
                        createCatalog(f.getName(), path, f);
                    }
                }
            }
        }
    }


