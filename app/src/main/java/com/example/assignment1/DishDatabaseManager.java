package com.example.assignment1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DishDatabaseManager {

    public static final String DB_NAME = "MealOrderDB";
    public static final String DB_TABLE = "dishes";
    public static final int DB_VERSION = 1;

    private static final String CREATE_TABLE = "CREATE TABLE " + DB_TABLE +
            " (id INTEGER PRIMARY KEY, name TEXT, type TEXT, ingredients TEXT, price REAL, imageLink TEXT);";


    private SQLHelper helper;
    private SQLiteDatabase db;

    public DishDatabaseManager(Context context) {
        helper = new SQLHelper(context);
        db = helper.getWritableDatabase();
    }

    public void addDish(int id, String name, String type, String ingredients, double price, String imageLink) {
        ContentValues dish = new ContentValues();
        dish.put("id", id);  // Use the provided id
        dish.put("name", name);
        dish.put("type", type);
        dish.put("ingredients", ingredients);
        dish.put("price", price);
        dish.put("imageLink", imageLink);
        db.insert(DB_TABLE, null, dish);
    }

    public void updateDish(int id, String name, String type, String ingredients, double price, String imageLink) {
        ContentValues dish = new ContentValues();
        dish.put("ID", id);
        dish.put("name", name);
        dish.put("type", type);
        dish.put("ingredients", ingredients);
        dish.put("price", price);
        dish.put("imageLink", imageLink);

        // Update the dish in the database by ID
        db.update(DB_TABLE, dish, "id = ?", new String[]{String.valueOf(id)});
    }


    public ArrayList<Dish> getAllDishes() {
        ArrayList<Dish> dishList = new ArrayList<>();
        Cursor cursor = db.query(DB_TABLE, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Dish dish = new Dish(
                        cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("name")),
                        cursor.getString(cursor.getColumnIndexOrThrow("type")),
                        cursor.getString(cursor.getColumnIndexOrThrow("ingredients")),
                        cursor.getDouble(cursor.getColumnIndexOrThrow("price")),
                        cursor.getString(cursor.getColumnIndexOrThrow("imageLink"))
                );
                dishList.add(dish);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return dishList;
    }

    public void clearDishes() {
        db.execSQL("DELETE FROM " + DB_TABLE);
    }

    private static class SQLHelper extends SQLiteOpenHelper {
        public SQLHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
            onCreate(db);
        }
    }
}
