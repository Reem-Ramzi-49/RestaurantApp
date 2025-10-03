package com.example.restaurantapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DishesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertDish(Entity_Dishes dish);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDishes(List<Entity_Dishes> dishes);

    @Query("SELECT * FROM dishes ORDER BY name_text ASC")
    LiveData<List<Entity_Dishes>> getAllDishes();

    @Query("SELECT * FROM dishes WHERE category = :category ORDER BY name_text ASC")
    LiveData<List<Entity_Dishes>> getDishesByCategory(String category);

     @Query("SELECT * FROM dishes WHERE category = :category " +
            "AND LOWER(name_text) LIKE LOWER(:keyword) || '%'")
    LiveData<List<Entity_Dishes>> searchDishesByCategory(String category, String keyword);

    @Query("SELECT * FROM dishes WHERE dish_id = :dishId LIMIT 1")
    Entity_Dishes getDishById(int dishId);

    @Update
    void updateDish(Entity_Dishes dish);

    @Query("UPDATE dishes SET is_available = :available WHERE dish_id = :dishId")
    void updateDishAvailability(int dishId, boolean available);

    @Query("DELETE FROM dishes WHERE dish_id = :dishId")
    void deleteDish(int dishId);

    @Query("DELETE FROM dishes")
    void deleteAllDishes();
}
