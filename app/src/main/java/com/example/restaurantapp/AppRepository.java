package com.example.restaurantapp;

import android.app.Application;

import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class AppRepository {

    private final UsersDao usersDao;
    private final DishesDao dishesDao;
    private final CartDao cartDao;
    private final OrdersDao ordersDao;

    private static final ExecutorService EXECUTOR_SERVICE = Executors.newSingleThreadExecutor();

    public AppRepository(Application app) {
        AppDatabase db = AppDatabase.getInstance(app);
        usersDao  = db.usersDao();
        dishesDao = db.dishesDao();
        cartDao   = db.cartDao();
        ordersDao = db.ordersDao();
    }


    public LiveData<List<Entity_Users>> getAllUsers() { return usersDao.getAllUsers(); }
    public LiveData<Entity_Users> login(String email, String password) { return usersDao.login(email, password); }
    public void insertUser(Entity_Users u) { EXECUTOR_SERVICE.execute(() -> usersDao.insertUser(u)); }
    public void updateUserProfile(int userId, String name, String phone, String address) { EXECUTOR_SERVICE.execute(() -> usersDao.updateUserProfile(userId, name, phone, address)); }

     void updateUserProfile1(int userId, String newName, String newEmail, String newPhone, String newAddress, String newPassword, String newImageUri) {
        EXECUTOR_SERVICE.execute(() ->
                usersDao.updateUserProfile1(userId, newName, newEmail, newPhone, newAddress, newPassword, newImageUri));
    }

    void updateUser(Entity_Users user) { EXECUTOR_SERVICE.execute(() -> usersDao.updateUser(user)); }
    LiveData<Entity_Users> getUserById(int userId){ return usersDao.getUserById(userId); }

     public LiveData<List<Entity_Dishes>> getAllDishes() { return dishesDao.getAllDishes(); }
    public LiveData<List<Entity_Dishes>> getDishesByCategory(String category) { return dishesDao.getDishesByCategory(category); }
    public LiveData<List<Entity_Dishes>> searchDishes(String q) { return dishesDao.searchDishes(q); }
    public void insertDish(Entity_Dishes dish) { EXECUTOR_SERVICE.execute(() -> dishesDao.insertDish(dish)); }
    public void getDishById(int dishId, Consumer<Entity_Dishes> callback) {
        EXECUTOR_SERVICE.execute(() -> {
            Entity_Dishes dish = dishesDao.getDishById(dishId);
            callback.accept(dish);
        });
    }

     public LiveData<List<Entity_Dishes>> searchDishesByCategory(String category, String q) {
        return dishesDao.searchDishesByCategory(category, q);
    }

     public void addToCartOrIncrement(int dishId, int qty) { EXECUTOR_SERVICE.execute(() -> cartDao.addToCartOrIncrement(dishId, qty)); }
    public void setCartQuantity(int cartId, int newQty) { EXECUTOR_SERVICE.execute(() -> cartDao.setCartQuantity(cartId, newQty)); }
    public void deleteCartItem(int cartId) { EXECUTOR_SERVICE.execute(() -> cartDao.deleteCartItem(cartId)); }
    public void clearCart() { EXECUTOR_SERVICE.execute(cartDao::clearCart); }
    @WorkerThread public List<CartItemDTO> getCartItemsOnce() { return cartDao.getCartItemsOnce(); }
    @WorkerThread public double getCartTotalOnce() { return cartDao.getCartTotalOnce(); }
    public LiveData<List<CartItemDTO>> observeCartItems() { return cartDao.observeCartItemsWithDetails(); }
    public LiveData<Double> observeCartTotal() { return cartDao.observeCartTotal(); }

     public LiveData<List<Entity_Orders>> getAllOrders() { return ordersDao.getAllOrders(); }
    public LiveData<List<Entity_Orders>> getOrdersByStatus(String status) { return ordersDao.getOrdersByStatus(status); }

    public void placeOrderFromCart(String status) {
        EXECUTOR_SERVICE.execute(() -> {
            double total = cartDao.getCartTotalOnce();
            int itemsCount = 0;
            List<CartItemDTO> items = cartDao.getCartItemsOnce();
            for (CartItemDTO it : items) itemsCount += it.quantity;
            String now = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date());
            Entity_Orders order = new Entity_Orders(now, status, total, itemsCount);
            ordersDao.insertOrder(order);
            cartDao.clearCart();
        });
    }

    public void updateOrderStatus(int orderId, String newStatus) { EXECUTOR_SERVICE.execute(() -> ordersDao.updateOrderStatus(orderId, newStatus)); }
}
