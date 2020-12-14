package com.gmail.psyh2409.entity;

import javax.persistence.*;
import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Catalog catalog = (Catalog) o;
        return Objects.equals(id, catalog.id) &&
                Objects.equals(name, catalog.name) &&
                Objects.equals(myPath, catalog.myPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, myPath);
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
