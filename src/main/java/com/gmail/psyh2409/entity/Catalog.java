package com.gmail.psyh2409.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "mycatalog")
public class Catalog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

//    @Column(name = "parent_catalog")
//    private Catalog parent;

    @Column(name = "mypath")
    private String myPath;

    @ManyToOne
    @JoinColumn(name = "subcatalog")
    private Catalog subCatalog;

    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Set<MyFile> files;

    public Catalog() {
        super();
    }

    public Catalog(String name, String myPath) {
        this.name = name;
        this.myPath = myPath;
        this.files = new HashSet<MyFile>();
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

//    public Catalog getParent() {
//        return parent;
//    }
//
//    public void setParent(Catalog parent) {
//        this.parent = parent;
//    }
//
    public String getMyPath() {
        return myPath;
    }

    public void setMyPath(String myPath) {
        this.myPath = myPath;
    }

    public Catalog getSubCatalog() {
        return subCatalog;
    }

    public void setSubCatalog(Catalog subCatalog) {
        this.subCatalog = subCatalog;
    }

    public Set<MyFile> getFiles() {
        return files;
    }

    public void setFiles(Set<MyFile> files) {
        this.files = files;
    }


}
