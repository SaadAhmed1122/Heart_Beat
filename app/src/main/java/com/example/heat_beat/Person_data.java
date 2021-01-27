package com.example.heat_beat;

class Person_data {
    String date,name,stresslevel,id;

    public Person_data() {
    }

    public Person_data(String date, String name, String stresslevel, String id) {
        this.date = date;
        this.name = name;
        this.stresslevel = stresslevel;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStresslevel() {
        return stresslevel;
    }

    public void setStresslevel(String stresslevel) {
        this.stresslevel = stresslevel;
    }
}
