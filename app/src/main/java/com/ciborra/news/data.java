package com.ciborra.news;

import androidx.annotation.NonNull;

public class data {
    String id;
    String name;
    String url;

    public data(){}
    public data(String id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }

    public data(String nombre, String url) {
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    @Override
    public String toString() {
        return name;
    }
}
