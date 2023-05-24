package com.example.garbage.Entidade;

public class Requirement {

    int id;
    String name;
    String description;
    String registerDate;
    String importance;
    String dificulty;
    String develTime;

    int idImg;

    String latitude;

    String longitude;

    public Requirement(int id, String name, String description, String registerDate, String importance, String dificulty, String develTime, int idImg, String latitude, String longitude) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.registerDate = registerDate;
        this.importance = importance;
        this.dificulty = dificulty;
        this.develTime = develTime;
        this.idImg = idImg;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Requirement(String name, String description, String registerDate, String importance, String dificulty, String develTime, int idImg, String latitude, String longitude) {
        this.name = name;
        this.description = description;
        this.registerDate = registerDate;
        this.importance = importance;
        this.dificulty = dificulty;
        this.develTime = develTime;
        this.idImg = idImg;
        this.latitude = latitude;
        this.longitude = longitude;
    }



    public Requirement(String name, String description, String registerDate, String importance, String dificulty, String develTime) {
        this.name = name;
        this.description = description;
        this.registerDate = registerDate;
        this.importance = importance;
        this.dificulty = dificulty;
        this.develTime = develTime;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getImportance() {
        return importance;
    }

    public void setImportance(String importance) {
        this.importance = importance;
    }

    public String getDificulty() {
        return dificulty;
    }

    public void setDificulty(String dificulty) {
        this.dificulty = dificulty;
    }

    public String getDevelTime() {
        return develTime;
    }

    public void setDevelTime(String develTime) {
        this.develTime = develTime;
    }

    public int getIdImg() {
        return idImg;
    }

    public void setIdImg(int idImg) {
        this.idImg = idImg;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
