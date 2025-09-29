package com.example.restaurantapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface CartDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    long insertCart(Entity_Cart cartItem);

    @Query("SELECT cart_id FROM cart WHERE dish_id = :dishId LIMIT 1")
    Integer getCartIdByDish(int dishId);

    @Query("UPDATE cart SET quantity = quantity + :delta WHERE cart_id = :cartId")
    void increaseCartQuantity(int cartId, int delta);

    @Query("UPDATE cart SET quantity = :newQty WHERE cart_id = :cartId")
    void setCartQuantity(int cartId, int newQty);

    @Query("DELETE FROM cart WHERE cart_id = :cartId")
    void deleteCartItem(int cartId);

    @Query("DELETE FROM cart")
    void clearCart();

    @Transaction
    default void addToCartOrIncrement(int dishId, int qty) {
        Integer existingId = getCartIdByDish(dishId);
        if (existingId == null) {
            insertCart(new Entity_Cart(dishId, qty));
        } else {
            increaseCartQuantity(existingId, qty);
        }
    }

     @Query("SELECT c.cart_id AS cartId, " +
            "c.dish_id AS dishId, " +
            "d.name_text AS dishName, " +
            "d.price AS unitPrice, " +
            "c.quantity AS quantity, " +
            "(d.price * c.quantity) AS lineTotal, " +
            "d.image_int AS imageRes, " +
            "d.image_url AS imageUrl " +
            "FROM cart c " +
            "JOIN dishes d ON d.dish_id = c.dish_id " +
            "ORDER BY c.cart_id DESC")
    LiveData<List<CartItemDTO>> observeCartItemsWithDetails();

    @Query("SELECT COALESCE(SUM(d.price * c.quantity), 0) " +
            "FROM cart c " +
            "JOIN dishes d ON d.dish_id = c.dish_id")
    LiveData<Double> observeCartTotal();

    @Query("SELECT c.cart_id AS cartId, " +
            "c.dish_id AS dishId, " +
            "d.name_text AS dishName, " +
            "d.price AS unitPrice, " +
            "c.quantity AS quantity, " +
            "(d.price * c.quantity) AS lineTotal, " +
            "d.image_int AS imageRes, " +
            "d.image_url AS imageUrl " +
            "FROM cart c " +
            "JOIN dishes d ON d.dish_id = c.dish_id " +
            "ORDER BY c.cart_id DESC")
    List<CartItemDTO> getCartItemsOnce();

    @Query("SELECT COALESCE(SUM(d.price * c.quantity), 0) " +
            "FROM cart c JOIN dishes d ON d.dish_id = c.dish_id")
    double getCartTotalOnce();
}
