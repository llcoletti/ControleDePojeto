package com.example.garbage.Entidade;

public class User {
    String id;
    String name;
    String cpf;
    String birthDate;
    String emailValue;
    String password;
    String dataUsage;

    public User() {
    }

    public User(String id, String name, String cpf, String birthDate, String emailValue, String password, String dataUsage) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.birthDate = birthDate;
        this.emailValue = emailValue;
        this.password = password;
        this.dataUsage = dataUsage;
    }

    public User(String name, String cpf, String birthDate, String emailValue, String password, String dataUsage) {
        this.name = name;
        this.cpf = cpf;
        this.birthDate = birthDate;
        this.emailValue = emailValue;
        this.password = password;
        this.dataUsage = dataUsage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmailValue() {
        return emailValue;
    }

    public void setEmailValue(String emailValue) {
        this.emailValue = emailValue;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDataUsage() {
        return dataUsage;
    }

    public void setDataUsage(String dataUsage) {
        this.dataUsage = dataUsage;
    }
}
