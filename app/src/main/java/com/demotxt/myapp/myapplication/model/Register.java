package com.demotxt.myapp.myapplication.model;

public class Register {
    public int success;
    public String message;
    public String email;
    public String password;

    public Register(){

    }

    public Register(int success, String message, String email, String password) {
        this.success = success;
        this.message = message;
        this.email = email;
        this.password = password;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
