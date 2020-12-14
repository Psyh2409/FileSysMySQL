package com.gmail.psyh2409.entity;

import javax.persistence.*;
import java.io.File;
import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MyFile myFile = (MyFile) o;
        return Objects.equals(content, myFile.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), content);
    }

    @Override
    public String toString() {
        return "MyFile{" +
                "content=" + content +
                '}';
    }
}
