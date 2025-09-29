package com.example.restaurantapp;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import java.util.List;
import java.util.function.Consumer;

public class AppViewModel extends AndroidViewModel {

    private final AppRepository repo;
    private final MutableLiveData<String> searchQuery = new MutableLiveData<>("");

    public AppViewModel(@NonNull Application application) {
        super(application);
        repo = new AppRepository(application);
    }

     public void setSearchQuery(String query) { searchQuery.setValue(query); }
    public LiveData<String> getSearchQuery() { return searchQuery; }

     public LiveData<List<Entity_Users>> getAllUsers() { return repo.getAllUsers(); }
    public LiveData<Entity_Users> login(String email, String password) { return repo.login(email, password); }
    public void insertUser(Entity_Users user) { repo.insertUser(user); }
    public void updateUserProfile(int userId, String name, String phone, String address) { repo.updateUserProfile(userId, name, phone, address); }
    void updateUserProfile1(int userId,
                            String newName,
                            String newEmail,
                            String newPhone,
                            String newAddress,
                            String newPassword,
                            String newImageUri) {
        repo.updateUserProfile1(userId, newName, newEmail, newPhone, newAddress, newPassword, newImageUri);
    }
    void updateUser(Entity_Users user) { repo.updateUser(user); }
    LiveData<Entity_Users> getUserById(int userId){ return repo.getUserById(userId); }

     public LiveData<List<Entity_Dishes>> getAllDishes() { return repo.getAllDishes(); }
    public LiveData<List<Entity_Dishes>> getDishesByCategory(String category) { return repo.getDishesByCategory(category); }

     public LiveData<List<Entity_Dishes>> searchDishes(String keyword) { return repo.searchDishes(keyword); }

     public LiveData<List<Entity_Dishes>> searchDishesByCategory(String category, String keyword) {
        return repo.searchDishesByCategory(category, keyword);
    }

    public void insertDish(Entity_Dishes dish) { repo.insertDish(dish); }
    public void getDishById(int dishId, Consumer<Entity_Dishes> callback) { repo.getDishById(dishId, callback); }

     public LiveData<List<CartItemDTO>> observeCartItems() { return repo.observeCartItems(); }
    public LiveData<Double> observeCartTotal() { return repo.observeCartTotal(); }
    public List<CartItemDTO> getCartItemsOnce() { return repo.getCartItemsOnce(); }
    public double getCartTotalOnce() { return repo.getCartTotalOnce(); }
    public void addToCart(int dishId, int qty) { repo.addToCartOrIncrement(dishId, qty); }
    public void setCartQuantity(int cartId, int qty) { repo.setCartQuantity(cartId, qty); }
    public void deleteCartItem(int cartId) { repo.deleteCartItem(cartId); }
    public void clearCart() { repo.clearCart(); }
    public void placeOrderNow(String status) { repo.placeOrderFromCart(status); }

     public LiveData<List<Entity_Orders>> getAllOrders() { return repo.getAllOrders(); }
    public LiveData<List<Entity_Orders>> getOrdersByStatus(String status) { return repo.getOrdersByStatus(status); }
    public void updateOrderStatus(int orderId, String newStatus) { repo.updateOrderStatus(orderId, newStatus); }
}
