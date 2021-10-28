package com.mobileapps.apuntesfr.model;

public class Apunte {

    private String id;
    private String username;
    private String body;
    private long data;

    public Apunte() {
    }

    public Apunte(String id, String username, String body, long data) {
        this.id = id;
        this.username = username;
        this.body = body;
        this.data = data;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public long getData() {
        return data;
    }

    public void setData(long data) {
        this.data = data;
    }
}
