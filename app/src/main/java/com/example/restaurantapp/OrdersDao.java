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

     @Query("SELECT * FROM orders WHERE status = 'pending' ORDER BY order_date DESC")
    LiveData<List<Entity_Orders>> getPendingOrders();

     @Query("SELECT * FROM orders ORDER BY order_date DESC")
    LiveData<List<Entity_Orders>> getAllOrders();

     @Query("SELECT * FROM orders WHERE user_id = :userId ORDER BY order_date DESC")
    LiveData<List<Entity_Orders>> getOrdersByUser(int userId);

     @Query("SELECT IFNULL(MAX(display_number), 0) FROM orders WHERE user_id = :userId")
    int getLastDisplayNumberForUser(int userId);

     @Query("SELECT * FROM orders WHERE status = :status ORDER BY order_date DESC")
    LiveData<List<Entity_Orders>> getOrdersByStatus(String status);

     @Query("SELECT * FROM orders WHERE user_id = :userId AND status = :status ORDER BY order_date DESC")
    LiveData<List<Entity_Orders>> getOrdersByUserAndStatus(int userId, String status);

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

     @Query("SELECT o.order_id, o.display_number, o.order_date, o.status, " +
            "o.total_price, o.items_count, o.user_id, u.name AS userName " +
            "FROM orders o JOIN users u ON o.user_id = u.user_id " +
            "ORDER BY o.order_date DESC")
    LiveData<List<OrderWithUser>> getAllOrdersWithUser();

    @Query("SELECT o.order_id, o.display_number, o.order_date, o.status, " +
            "o.total_price, o.items_count, o.user_id, u.name AS userName " +
            "FROM orders o JOIN users u ON o.user_id = u.user_id " +
            "WHERE o.status = 'pending' OR o.status = 'confirmed' " +
            "ORDER BY o.order_date DESC")
    LiveData<List<OrderWithUser>> getActiveOrdersWithUser();

}
