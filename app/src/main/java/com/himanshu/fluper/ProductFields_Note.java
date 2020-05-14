package com.himanshu.fluper;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "product_fields")
public class ProductFields_Note {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String description;
    private int regularPrice;
    private int salePrice;
    private byte[] productPhoto; //binary
    private String colors;
    private String stores;

    public ProductFields_Note(String name, String description, int regularPrice, int salePrice, byte[] productPhoto, String colors, String stores) {
        this.name = name;
        this.description = description;
        this.regularPrice = regularPrice;
        this.salePrice = salePrice;
        this.productPhoto = productPhoto;
        this.colors = colors;
        this.stores = stores;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getRegularPrice() {
        return regularPrice;
    }

    public int getSalePrice() {
        return salePrice;
    }

    public byte[] getProductPhoto() {
        return productPhoto;
    }

    public String getColors() {
        return colors;
    }

    public String getStores() {
        return stores;
    }
}
