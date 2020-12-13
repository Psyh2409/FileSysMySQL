package com.gmail.psyh2409.entity;

import javax.persistence.*;
import java.io.File;

@Entity
@Table(name = "myfiles")
public class MyFile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "mycatalog_id")
    private Catalog parent;

    @Column(name = "path")
    private String path;

    @Column(name = "myblob")
    private File content;

    public MyFile() {
        super();
    }

    public MyFile(String name, Catalog parent, String path) {
        this.name = name;
        this.parent = parent;
        this.path = path;
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

    public Catalog getParent() {
        return parent;
    }

    public void setParent(Catalog catalog) {
        this.parent = catalog;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public File getContent() {
        return content;
    }

    public void setContent(File content) {
        this.content = content;
    }

}
