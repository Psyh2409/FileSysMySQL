package com.gmail.psyh2409.entity;

import javax.persistence.*;
import java.io.File;

@Entity
public class MyFile extends Catalog {

    @Column(name = "myblob")
    private File content;

    public MyFile() {
        super();
    }

    public MyFile(String name, String path, File file) {
        super(name, path);
        this.content = file;
    }

    public File getContent() {
        return content;
    }

    public void setContent(File content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "MyFile{" +
                "id=" + getId()+
                ", name='" + getName() + '\'' +
                ", path='" + getMyPath() + '\'' +
                '}';
    }
}
