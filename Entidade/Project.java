package com.example.garbage.Entidade;

public class Project {

    int id;
    String name;
    String startDate;
    String expectDate;

    public Project() {
    }

    public Project(int id, String name, String startDate, String expectDate) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.expectDate = expectDate;
    }

    public Project(String name, String startDate, String expectDate) {
        this.name = name;
        this.startDate = startDate;
        this.expectDate = expectDate;
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getExpectDate() {
        return expectDate;
    }

    public void setExpectDate(String expectDate) {
        this.expectDate = expectDate;
    }
}
