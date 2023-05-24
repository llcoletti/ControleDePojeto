package com.example.garbage.Entidade;

import org.w3c.dom.Text;

import java.sql.Blob;

public class Image {

    String name;
    byte[] image;

    public Image() {
    }

    public Image(String name, byte[] image) {
        this.name = name;
        this.image = image;
    }

    public Image(byte[] image) {
        this.image = image;
    }

    public byte[] getImage() {
        return image;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
