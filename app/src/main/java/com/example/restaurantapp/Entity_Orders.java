package com.example.restaurantapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;

@Entity(tableName = "orders")
public class Entity_Orders {

    @PrimaryKey(autoGenerate = true)
    private int order_id;

    @ColumnInfo(name = "order_date")
    private String order_date;

    @ColumnInfo(name = "status")
    private String status;

    @ColumnInfo(name = "total_price")
    private double total_price;

    @ColumnInfo(name = "items_count")
    private int items_count;

    @ColumnInfo(name = "user_id")
    private int user_id;

    // ✅ رقم العرض يبدأ من 1 لكل مستخدم
    @ColumnInfo(name = "display_number")
    private int display_number;

    public Entity_Orders(String order_date, String status, double total_price,
                         int items_count, int user_id, int display_number) {
        this.order_date = order_date;
        this.status = status;
        this.total_price = total_price;
        this.items_count = items_count;
        this.user_id = user_id;
        this.display_number = display_number;
    }

    // Getters & Setters
    public int getOrder_id() { return order_id; }
    public void setOrder_id(int order_id) { this.order_id = order_id; }

    public String getOrder_date() { return order_date; }
    public void setOrder_date(String order_date) { this.order_date = order_date; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public double getTotal_price() { return total_price; }
    public void setTotal_price(double total_price) { this.total_price = total_price; }

    public int getItems_count() { return items_count; }
    public void setItems_count(int items_count) { this.items_count = items_count; }

    public int getUser_id() { return user_id; }
    public void setUser_id(int user_id) { this.user_id = user_id; }

    public int getDisplay_number() { return display_number; }
    public void setDisplay_number(int display_number) { this.display_number = display_number; }
}
