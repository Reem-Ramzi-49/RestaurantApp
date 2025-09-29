package com.example.restaurantapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface OrdersDao {

     @Insert(onConflict = OnConflictStrategy.ABORT)
    long insertOrder(Entity_Orders order);



    @Query("SELECT * FROM orders ORDER BY order_date DESC")
    LiveData<List<Entity_Orders>> getAllOrders();

     @Query("SELECT * FROM orders WHERE status = :status ORDER BY order_date DESC")
    LiveData<List<Entity_Orders>> getOrdersByStatus(String status);

     @Query("SELECT * FROM orders WHERE order_id = :orderId LIMIT 1")
    LiveData<Entity_Orders> getOrderById(int orderId);



    @Update
    void updateOrder(Entity_Orders order);

    @Query("UPDATE orders SET status = :newStatus WHERE order_id = :orderId")
    void updateOrderStatus(int orderId, String newStatus);



    @Query("DELETE FROM orders WHERE order_id = :orderId")
    void deleteOrder(int orderId);

    @Query("DELETE FROM orders")
    void deleteAllOrders();
}
