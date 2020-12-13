package com.gmail.psyh2409.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "mycatalog")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Catalog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "mypath")
    private String myPath;


    public Catalog() {
        super();
    }

    public Catalog(String name, String myPath) {
        this.name = name;
        this.myPath = myPath;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMyPath() {
        return myPath;
    }

    public void setMyPath(String myPath) {
        this.myPath = myPath;
    }

    @Override
    public String toString() {
        return "Catalog{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", myPath='" + myPath + '\'' +
                '}';
    }
}
