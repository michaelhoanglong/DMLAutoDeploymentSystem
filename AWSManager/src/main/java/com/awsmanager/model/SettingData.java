package com.awsmanager.model;

public class SettingData {
    private int id;
    private String name;
    private String value;

    public SettingData(){}

    public SettingData(String name, String value){
        this.name = name;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
