package com.demotxt.myapp.myapplication.model;


public class Product {

    private String title ;

    private String id;

    private String filename;



    public Product() {
    }

    public Product(String title,  String id, String filename) {
        this.title = title;

        this.id = id;

        this.filename = filename;

    }


    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }
    public void setFilename(String filename) {
        this.filename = filename;
    }

}
