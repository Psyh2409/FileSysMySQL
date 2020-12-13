package com.gmail.psyh2409;

import com.gmail.psyh2409.entity.Catalog;
import com.gmail.psyh2409.entity.MyFile;

import javax.persistence.EntityManager;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Main {
    //    C:\Users\Psyh\IdeaProjects\FileSysMySQL\src\main\java\com\gmail\psyh2409\Main.java
    public static void main(String[] args) {
        var manager = new Manager();
        try {
            EntityManager em = manager.getEmf().createEntityManager();
            Catalog catalog = new Catalog("grandCatalog", "");
            manager.createCatalog(catalog.getName(), catalog.getMyPath(), null);
            manager.createCatalog("myfamily", catalog.getName(), null);
            manager.createCatalog("vitiafamily", catalog.getName(), null);
            manager.createCatalog("sergiiko", catalog.getName()+".myfamily", null);
            File file = manager.createAndWriteInNewFile("text.txt", "Hello DataBase!");
            manager.createCatalog("text.txt", catalog.getName() + ".myfamily.sergiiko", file);

            List<Catalog> catalogLis = manager.dir("");
            for (Catalog c: catalogLis) {
                System.out.println(c.getName());
            }
            List<Catalog> catalogList = manager.dir("grandCatalog");
            for (Catalog c: catalogList) {
                System.out.println(c.getName());
            }
            List<Catalog> catalogs = manager.ls("grandCatalog");
            for (Catalog c: catalogs) {
                System.out.println(c.getMyPath() +"."+ c.getName());
            }
            System.out.println(
                    manager.findByPathName(
                            "grandCatalog.myfamily.sergiiko", "text.txt"));
            Catalog catalog1 = manager.findByPathName(
                    "grandCatalog.myfamily.sergiiko", "text.txt");
            File file1 = manager.readFile((MyFile) catalog1);
        } finally {
            manager.emfClose();
        }
    }
}
