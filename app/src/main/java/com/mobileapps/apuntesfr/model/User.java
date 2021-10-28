package com.mobileapps.apuntesfr.model;

public class User {

    private String id;
    private String name;
    private long date;

    public User() {
    }
    public User(String id, String name, long date) {
        this.id = id;
        this.name = name;
        this.date = date;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
