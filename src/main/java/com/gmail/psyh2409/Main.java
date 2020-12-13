package com.gmail.psyh2409;

import com.gmail.psyh2409.entity.Catalog;
import com.gmail.psyh2409.entity.MyFile;

import javax.persistence.EntityManager;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    //    C:\Users\Psyh\IdeaProjects\FileSysMySQL\src\main\java\com\gmail\psyh2409\Main.java
    public static void main(String[] args) {
        var manager = new Manager();
        try {
            EntityManager em = manager.getEmf().createEntityManager();
            Catalog catalog = new Catalog("grandCatalog", "");
            Catalog subCat = new Catalog("catalog", "grandCatalog");
            File file = new File("text.txt");
            if (!file.exists()) {
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                    file.createNewFile();
                    bw.write("Hello DataBase!");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
            MyFile myFile = new MyFile("text.txt", subCat, "grandCatalog.catalog");
            myFile.setContent(file);
            subCat.getFiles().add(myFile);
            em.getTransaction().begin();
            em.persist(subCat);
            em.getTransaction().commit();

            catalog.setSubCatalog(subCat);

            em.getTransaction().begin();
            em.persist(catalog);
            em.getTransaction().commit();
            em.close();

            Long catalogId = manager.createCatalog("MyFamily", "catalog");
            System.out.println(catalogId);

        } finally {
            manager.emfClose();
        }
    }
}
