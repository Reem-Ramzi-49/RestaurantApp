package com.example.restaurantapp;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;

@Entity(tableName = "dishes")
public class Entity_Dishes {

    @PrimaryKey(autoGenerate = true)
    private int dish_id;

     @ColumnInfo(name = "name_res")
    private int nameResId;

    @ColumnInfo(name = "desc_res")
    private int descResId;

     @ColumnInfo(name = "name_text")
    private String nameText;

    @ColumnInfo(name = "desc_text")
    private String descText;

    @ColumnInfo(name = "category")
    private String category;

    @ColumnInfo(name = "price")
    private double price;

    @ColumnInfo(name = "image_url")
    private String imageUrl;

    @ColumnInfo(name = "image_int")
    private int imageInt;

    @ColumnInfo(name = "is_available")
    private boolean isAvailable;

     public Entity_Dishes(int nameResId, String nameText,
                         String category,
                         int descResId, String descText,
                         double price, String imageUrl,
                         boolean isAvailable) {
        this.nameResId = nameResId;
        this.nameText = nameText;
        this.category = category;
        this.descResId = descResId;
        this.descText = descText;
        this.price = price;
        this.imageUrl = imageUrl;
        this.isAvailable = isAvailable;
    }

     @Ignore
    public Entity_Dishes(int nameResId, String nameText,
                         String category,
                         int descResId, String descText,
                         double price, int imageInt,
                         boolean isAvailable) {
        this.nameResId = nameResId;
        this.nameText = nameText;
        this.category = category;
        this.descResId = descResId;
        this.descText = descText;
        this.price = price;
        this.imageInt = imageInt;
        this.isAvailable = isAvailable;
    }

     public int getDish_id() { return dish_id; }
    public void setDish_id(int dish_id) { this.dish_id = dish_id; }

    public int getNameResId() { return nameResId; }
    public void setNameResId(int nameResId) { this.nameResId = nameResId; }

    public int getDescResId() { return descResId; }
    public void setDescResId(int descResId) { this.descResId = descResId; }

    public String getNameText() { return nameText; }
    public void setNameText(String nameText) { this.nameText = nameText; }

    public String getDescText() { return descText; }
    public void setDescText(String descText) { this.descText = descText; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public int getImageInt() { return imageInt; }
    public void setImageInt(int imageInt) { this.imageInt = imageInt; }

    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }
}
