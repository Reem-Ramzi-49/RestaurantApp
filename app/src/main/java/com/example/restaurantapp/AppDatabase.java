package com.example.restaurantapp;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(
        entities = {
                Entity_Users.class,
                Entity_Dishes.class,
                Entity_Cart.class,
                Entity_Orders.class
        },
        version = 1,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;

    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public abstract UsersDao usersDao();
    public abstract DishesDao dishesDao();
    public abstract CartDao cartDao();
    public abstract OrdersDao ordersDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    "restaurant_db"
                            )
                            .fallbackToDestructiveMigration()
                            .addCallback(new RoomDatabase.Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    databaseWriteExecutor.execute(() -> {
                                        DishesDao dao = INSTANCE.dishesDao();
                                        dao.deleteAllDishes();

                                        UsersDao usersDao = INSTANCE.usersDao();
                                        usersDao.insertUser(new Entity_Users(
                                                "Admin",
                                                "admin@gmail.com",
                                                "12345678",
                                                "0000000000",
                                                "HQ",
                                                null
                                        ) {{
                                            setRole(1);
                                        }});

                                        dao.insertDish(new Entity_Dishes(
                                                R.string.dish_biryani_name, "Biryani Rice",
                                                "Food",
                                                R.string.dish_biryani_desc, "Flavored basmati rice",
                                                6.00,
                                                "https://www.177milkstreet.com/assets/site/Recipes/_large/Chicken-Shawarma.jpg",
                                                true));

                                        dao.insertDish(new Entity_Dishes(
                                                R.string.dish_burger_name, "Cheese Burger",
                                                "Food",
                                                R.string.dish_burger_desc, "Juicy beef burger with cheese",
                                                7.50,
                                                "https://www.washingtonpost.com/wp-apps/imrs.php?src=https%3A%2F%2Farc-anglerfish-washpost-prod-washpost.s3.amazonaws.com%2Fpublic%2FM6HASPARCZHYNN4XTUYT7H6PTE.jpg",
                                                true));

                                        dao.insertDish(new Entity_Dishes(
                                                R.string.dish_curry_name, "Chicken Curry",
                                                "Food",
                                                R.string.dish_curry_desc, "Spicy curry",
                                                8.00,
                                                "https://healthyfitnessmeals.com/wp-content/uploads/2022/04/Chicken-Curry-Recipe-12-819x1024.jpg",
                                                true));

                                        dao.insertDish(new Entity_Dishes(
                                                R.string.dish_pizza_name, "Pizza",
                                                "Food",
                                                R.string.dish_pizza_desc, "Cheesy vegetarian pizza",
                                                9.00,
                                                "https://kfoods.com/images1/newrecipeicon/vegetarian-pizza_4988.jpg",
                                                true));

                                        dao.insertDish(new Entity_Dishes(
                                                R.string.dish_shawarma_name, "Shawarma",
                                                "Food",
                                                R.string.dish_shawarma_desc, "Chicken shawarma wrap",
                                                6.50,
                                                "https://www.tysonfoodservice.com/adobe/dynamicmedia/deliver/dm-aid--0cd6cbb0-364d-43ba-a560-124e2ea32f69/chicken-shawarma.jpg",
                                                true));

                                        dao.insertDish(new Entity_Dishes(
                                                R.string.dish_pasta_name, "Pasta",
                                                "Food",
                                                R.string.dish_pasta_desc, "Lemony hummus pasta",
                                                7.00,
                                                "https://static01.nyt.com/images/2025/01/17/multimedia/CR-Lemony-Hummus-Pasta-wtkj/CR-Lemony-Hummus-Pasta-wtkj-jumbo.jpg",
                                                true));

                                        dao.insertDish(new Entity_Dishes(
                                                R.string.dish_chicken_name, "Grilled Chicken",
                                                "Food",
                                                R.string.dish_chicken_desc, "Juicy grilled chicken breast",
                                                10.00,
                                                "https://www.budgetbytes.com/wp-content/uploads/2024/06/Grilled-Chicken-V1-1152x1536.jpeg",
                                                true));

                                        dao.insertDish(new Entity_Dishes(
                                                R.string.dish_salad_name, "SALAD",
                                                "Food",
                                                R.string.dish_salad_desc, "Pesto caprese salad",
                                                5.00,
                                                "https://www.nonguiltypleasures.com/wp-content/uploads/2025/01/pesto-caprese-salad.jpg",
                                                true));

                                        dao.insertDish(new Entity_Dishes(
                                                R.string.dish_sushi_name, "Sushi",
                                                "Food",
                                                R.string.dish_sushi_desc, "Japanese sushi platter",
                                                12.00,
                                                "https://www.licious.in/blog/wp-content/uploads/2022/04/shutterstock_1617156526-min.jpg",
                                                true));

                                        dao.insertDish(new Entity_Dishes(
                                                R.string.dish_steak_name, "Steak",
                                                "Food",
                                                R.string.dish_steak_desc, "Baked beef steak",
                                                15.00,
                                                "https://www.tasteofhome.com/wp-content/uploads/2024/09/Baked-Steak_EXPS_FT24_277371_EC_0905_5.jpg",
                                                true));

                                        dao.insertDish(new Entity_Dishes(
                                                R.string.dish_fried_rice_name, "Fried Rice",
                                                "Food",
                                                R.string.dish_fried_rice_desc, "Sweet and sour Thai fried rice",
                                                6.50,
                                                "https://fullofplants.com/wp-content/uploads/2020/05/sweet-and-sour-spicy-thai-fried-rice-easy-vegan-meal-with-vegetables-1.jpg",
                                                true));

                                        dao.insertDish(new Entity_Dishes(
                                                R.string.dish_orange_juice_name, "Orange Juice",
                                                "Drinks",
                                                R.string.dish_orange_juice_desc, "Fresh juice",
                                                3.50,
                                                "https://images.unsplash.com/photo-1577805947697-89e18249d767?w=640",
                                                true));

                                        dao.insertDish(new Entity_Dishes(
                                                R.string.dish_coffee_name, "Coffee",
                                                "Drinks",
                                                R.string.dish_coffee_desc, "Hot espresso",
                                                2.50,
                                                "https://www.sharmispassions.com/wp-content/uploads/2012/07/espresso-coffee-recipe022.jpg",
                                                true));

                                        dao.insertDish(new Entity_Dishes(
                                                R.string.dish_latte_name, "Latte",
                                                "Drinks",
                                                R.string.dish_latte_desc, "Hot coffee with milk",
                                                4.00,
                                                "https://www.cuisinart.com/dw/image/v2/ABAF_PRD/on/demandware.static/-/Sites-us-cuisinart-sfra-Library/default/dw42dcae51/images/recipe-Images/cafe-latte1-recipe_resized.jpg",
                                                true));

                                        dao.insertDish(new Entity_Dishes(
                                                R.string.dish_cola_name, "Coca Cola",
                                                "Drinks",
                                                R.string.dish_cola_desc, "Cold soft drink",
                                                2.50,
                                                "https://m.media-amazon.com/images/I/61HxfHNAc+L._AC_SX679_.jpg",
                                                true));

                                        dao.insertDish(new Entity_Dishes(
                                                R.string.dish_lemonade_name, "Lemonade",
                                                "Drinks",
                                                R.string.dish_lemonade_desc, "Fresh lemonade",
                                                2.50,
                                                "https://images.squarespace-cdn.com/content/v1/5ed13dd3465af021e2c1342b/a5b1e544-ee89-4268-b9af-ab49e9cc7006/IMG_1986+%281%29.jpg",
                                                true));

                                        dao.insertDish(new Entity_Dishes(
                                                R.string.dish_mango_juice_name, "Mango Juice",
                                                "Drinks",
                                                R.string.dish_mango_juice_desc, "Cold mango juice",
                                                3.50,
                                                "https://www.justfood.tv/UserFiles/mang17012023.jpg",
                                                true));

                                        dao.insertDish(new Entity_Dishes(
                                                R.string.dish_smoothie_name, "Strawberry Smoothie",
                                                "Drinks",
                                                R.string.dish_smoothie_desc, "Cold strawberry smoothie",
                                                3.50,
                                                "https://i.pinimg.com/736x/6f/f4/3f/6ff43f11656cbca20f25e8fbaf524b97.jpg",
                                                true));

                                        dao.insertDish(new Entity_Dishes(
                                                R.string.dish_mint_tea_name, "Mint Tea",
                                                "Drinks",
                                                R.string.dish_mint_tea_desc, "Refreshing peppermint tea",
                                                2.50,
                                                "https://www.luxmiestates.in/cdn/shop/articles/peppermint_tea_1084e7c3-92d2-4d8a-92c6-c698f41d2099.jpg",
                                                true));

                                        dao.insertDish(new Entity_Dishes(
                                                R.string.dish_cappuccino_name, "Cappuccino",
                                                "Drinks",
                                                R.string.dish_cappuccino_desc, "Hot cappuccino with cinnamon",
                                                3.50,
                                                "https://www.simplystacie.net/wp-content/uploads/2023/11/Cinnamon-Cappuccino-LOW-RES-42.jpg",
                                                true));

                                        dao.insertDish(new Entity_Dishes(
                                                R.string.dish_milkshake_name, "Milkshake",
                                                "Drinks",
                                                R.string.dish_milkshake_desc, "Bacon milkshake dessert",
                                                4.00,
                                                "https://cookienameddesire.com/wp-content/uploads/2019/06/bacon-milkshake-5.jpg",
                                                true));

                                        dao.insertDish(new Entity_Dishes(
                                                R.string.dish_watermelon_name, "Watermelon Juice",
                                                "Drinks",
                                                R.string.dish_watermelon_desc, "Fresh watermelon juice",
                                                3.00,
                                                "https://bellyfull.net/wp-content/uploads/2022/06/Watermelon-Juice-blog-4.jpg",
                                                true));


                                        dao.insertDish(new Entity_Dishes(
                                                R.string.dish_cake_name, "Chocolate Cake",
                                                "Sweets",
                                                R.string.dish_cake_desc, "Rich and tasty",
                                                5.00,
                                                "https://kitchen.sayidaty.net/uploads/small/1b/1bf0d766358e36b1cfa05dacc669c3d1_w750_h500.jpg",
                                                true));

                                        dao.insertDish(new Entity_Dishes(
                                                R.string.dish_cheesecake_name, "Cheesecake",
                                                "Sweets",
                                                R.string.dish_cheesecake_desc, "Sweet dessert",
                                                4.00,
                                                "https://www.jocooks.com/wp-content/uploads/2018/11/cheesecake-1-22.jpg",
                                                true));

                                        dao.insertDish(new Entity_Dishes(
                                                R.string.dish_donut_name, "Donut",
                                                "Sweets",
                                                R.string.dish_donut_desc, "Sweet chocolate donut",
                                                2.50,
                                                R.drawable.img,
                                                true));

                                        dao.insertDish(new Entity_Dishes(
                                                R.string.dish_cupcake_name, "Cupcake",
                                                "Sweets",
                                                R.string.dish_cupcake_desc, "Creamy vanilla cupcake",
                                                3.00,
                                                "https://cuisine.nessma.tv/uploads/1/2018-07/b6d942010f491d7ffe7e5c2368211591.jpg",
                                                true));

                                        dao.insertDish(new Entity_Dishes(
                                                R.string.dish_baklava_name, "Baklava",
                                                "Sweets",
                                                R.string.dish_baklava_desc, "Sweet layered pastry with pistachio",
                                                4.50,
                                                "https://houseandhome.com/wp-content/uploads/2025/01/Feature_Turkish-Pistachio-Baklava.jpg",
                                                true));

                                        dao.insertDish(new Entity_Dishes(
                                                R.string.dish_ice_cream_name, "Ice Cream",
                                                "Sweets",
                                                R.string.dish_ice_cream_desc, "Homemade Nutella ice cream",
                                                3.00,
                                                "https://www.justfood.tv/userfiles/image/0Homemade_Nutella_Ice_Cream_fortune_001_goodies.jpg",
                                                true));

                                        dao.insertDish(new Entity_Dishes(
                                                R.string.dish_kunafa_name, "Kunafa",
                                                "Sweets",
                                                R.string.dish_kunafa_desc, "Traditional kunafa with cheese",
                                                5.00,
                                                "https://d33wubrfki0l68.cloudfront.net/51bb16cb996ce3d0992f52846b63a660b5ebe8ed/df7d2/en/recipes/kunafa/featured_1_hub6e3f8dc209b40c9147f217b474e1546_264963_800x534_fill_q75_box_bottomleft.jpg",
                                                true));

                                        dao.insertDish(new Entity_Dishes(
                                                R.string.dish_brownies_name, "Brownies",
                                                "Sweets",
                                                R.string.dish_brownies_desc, "Chocolate cosmic brownies",
                                                3.50,
                                                "https://www.simplyrecipes.com/thmb/UBggXgTxmAWX-j0_tR-uTvZl0a0=/750x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/SimplyRecipes_CosmicBrownies_LEAD_11-2f9bf50c944345698b442c0b1940c801.jpg",
                                                true));

                                        dao.insertDish(new Entity_Dishes(
                                                R.string.dish_tiramisu_name, "Tiramisu",
                                                "Sweets",
                                                R.string.dish_tiramisu_desc, "Classic Italian tiramisu",
                                                5.50,
                                                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTgDE9n33wuB5wBGx4KXdFLqF0TgohtxceHHA&s",
                                                true));

                                        dao.insertDish(new Entity_Dishes(
                                                R.string.dish_basbousa_name, "Basbousa",
                                                "Sweets",
                                                R.string.dish_basbousa_desc, "Middle Eastern semolina cake",
                                                3.50,
                                                "https://shoutabkhin.wassfat.com/wp-content/uploads/2018/01/%D9%82%D8%A7%D9%84%D8%A8_%D8%A7%D9%84%D8%A8%D8%B3%D8%A8%D9%88%D8%B3%D8%A9_%D8%A8%D8%A7%D9%84%D9%82%D8%B4%D8%B7%D8%A9_%D8%B7%D8%B1%D9%8A%D9%82%D8%A9_%D8%AA%D8%AD%D8%B6%D9%8A%D8%B1_%D9%88%D8%B5%D9%81%D8%A9_%D8%B4%D9%88_%D8%B7%D8%A7%D8%A8%D8%AE%D9%8A%D9%86_%D8%A7%D9%84%D9%8A%D9%88%D9%85-848x477.jpg",
                                                true));
                                    });
                                }
                            })
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}