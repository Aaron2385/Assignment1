package com.example.assignment1;

public class Dish {
    private int id;
    private String name;
    private String type;
    private String ingredients;
    private double price;
    private String imageLink; // updated from imageUri to imageLink

    public Dish(int id, String name, String type, String ingredients, double price, String imageLink) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.ingredients = ingredients;
        this.price = price;
        this.imageLink = imageLink;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getType() { return type; }
    public String getIngredients() { return ingredients; }
    public double getPrice() { return price; }
    public String getImageLink() { return imageLink; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setType(String type) { this.type = type; }
    public void setIngredients(String ingredients) { this.ingredients = ingredients; }
    public void setPrice(double price) { this.price = price; }
    public void setImageLink(String imageLink) { this.imageLink = imageLink; }
}
