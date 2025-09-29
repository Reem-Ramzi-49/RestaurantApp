package com.example.restaurantapp;

import androidx.room.ColumnInfo;

public class CartItemDTO {

   @ColumnInfo(name = "cartId")
   public int cartId;

   @ColumnInfo(name = "dishId")
   public int dishId;

   @ColumnInfo(name = "dishName")
   public String dishName;

   @ColumnInfo(name = "unitPrice")
   public double unitPrice;

   @ColumnInfo(name = "quantity")
   public int quantity;

   @ColumnInfo(name = "lineTotal")
   public double lineTotal;

   @ColumnInfo(name = "imageRes")
   public int imageRes;

   @ColumnInfo(name = "imageUrl")
   public String imageUrl;

   public CartItemDTO() {
   }

   public int getCartId() {
      return cartId;
   }

   public void setCartId(int cartId) {
      this.cartId = cartId;
   }

   public int getDishId() {
      return dishId;
   }

   public void setDishId(int dishId) {
      this.dishId = dishId;
   }

   public String getDishName() {
      return dishName;
   }

   public void setDishName(String dishName) {
      this.dishName = dishName;
   }

   public double getUnitPrice() {
      return unitPrice;
   }

   public void setUnitPrice(double unitPrice) {
      this.unitPrice = unitPrice;
   }

   public int getQuantity() {
      return quantity;
   }

   public void setQuantity(int quantity) {
      this.quantity = quantity;
   }

   public double getLineTotal() {
      return lineTotal;
   }

   public void setLineTotal(double lineTotal) {
      this.lineTotal = lineTotal;
   }

   public int getImageRes() {
      return imageRes;
   }

   public void setImageRes(int imageRes) {
      this.imageRes = imageRes;
   }

   public String getImageUrl() {
      return imageUrl;
   }

   public void setImageUrl(String imageUrl) {
      this.imageUrl = imageUrl;
   }
}
