package com.demotxt.myapp.myapplication.model;

public class Comment {

    private  String id;

    private String productId;

    private String email;

    private String content;

    public Comment(){

    }


    public Comment(String id, String content, String email, String productId) {
        this.id = id;

        this.content = content;

        this.email = email;

        this.productId = productId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }
    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
}
