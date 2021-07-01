package com.Arid2760.fsshop.gertterSetter;

import android.graphics.Bitmap;

public class GetProductData {


    private int id;
    private String name;
    private String price;
    private String Description;
    private String imageBitmap;

//    public GetProductData(int id, String name, String price, String description, Bitmap imageBitmap) {
//        this.id = id;
//        this.name = name;
//        this.price = price;
//        Description = description;
//        this.imageBitmap = imageBitmap;
//    }

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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImageBitmap() {
        return imageBitmap;
    }

    public void setImageBitmap(String imageBitmap) {
        this.imageBitmap = imageBitmap;
    }

}
