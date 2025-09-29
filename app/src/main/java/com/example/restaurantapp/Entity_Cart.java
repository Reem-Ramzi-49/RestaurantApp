package com.example.restaurantapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "cart",
        foreignKeys = @ForeignKey(
                entity = Entity_Dishes.class,
                parentColumns = "dish_id",
                childColumns = "dish_id",
                onDelete = ForeignKey.CASCADE
        )
)
public class Entity_Cart {

    @PrimaryKey(autoGenerate = true)
    private int cart_id;

    @ColumnInfo(name = "dish_id", index = true)
    private int dish_id;

    @ColumnInfo(name = "quantity")
    private int quantity;

    public Entity_Cart(int dish_id, int quantity) {
        this.dish_id = dish_id;
        this.quantity = quantity;
    }

    public int getCart_id() { return cart_id; }
    public void setCart_id(int cart_id) { this.cart_id = cart_id; }

    public int getDish_id() { return dish_id; }
    public void setDish_id(int dish_id) { this.dish_id = dish_id; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
