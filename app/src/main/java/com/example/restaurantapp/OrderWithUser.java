package com.example.restaurantapp;

// كلاس DTO (مش Entity) بيجمع بيانات الطلب + اسم المستخدم
public class OrderWithUser {

    private int order_id;
    private int display_number;   // رقم عرض الطلب (اختياري لو بدك تعرضه مثل 1,2,3...)
    private String order_date;
    private String status;
    private double total_price;
    private int items_count;
    private int user_id;

    // اسم المستخدم المرتبط بالطلب
    private String userName;

     public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getDisplay_number() {
        return display_number;
    }

    public void setDisplay_number(int display_number) {
        this.display_number = display_number;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public int getItems_count() {
        return items_count;
    }

    public void setItems_count(int items_count) {
        this.items_count = items_count;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
