package thm.com.gr2.model;

import java.io.Serializable;

public class Advice implements Serializable {
    private String name;
    private String content;

    public Advice() {
    }

    public Advice(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}