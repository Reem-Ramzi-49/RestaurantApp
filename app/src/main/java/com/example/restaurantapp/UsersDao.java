package com.example.restaurantapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UsersDao {

     @Insert
    long insertUser(Entity_Users user);

     @Query("SELECT * FROM users ORDER BY user_id ASC")
    LiveData<List<Entity_Users>> getAllUsers();

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    LiveData<Entity_Users> getUserByEmail(String email);

    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    LiveData<Entity_Users> login(String email, String password);

    @Query("SELECT * FROM users WHERE user_id = :userId LIMIT 1")
    LiveData<Entity_Users> getUserById(int userId);

     @Update
    void updateUser(Entity_Users user);

    @Query("UPDATE users SET name = :newName, phone = :newPhone, address = :newAddress WHERE user_id = :userId")
    void updateUserProfile(int userId, String newName, String newPhone, String newAddress);

     @Query("UPDATE users SET name = :newName, email = :newEmail, phone = :newPhone, address = :newAddress, password = :newPassword, image_uri = :newImageUri WHERE user_id = :userId")
    void updateUserProfile1(int userId,
                            String newName,
                            String newEmail,
                            String newPhone,
                            String newAddress,
                            String newPassword,
                            String newImageUri);

     @Query("DELETE FROM users WHERE user_id = :userId")
    void deleteUser(int userId);

    @Query("DELETE FROM users")
    void deleteAllUsers();
}
