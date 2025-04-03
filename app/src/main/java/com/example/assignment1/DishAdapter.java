package com.example.assignment1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DishAdapter extends RecyclerView.Adapter<DishAdapter.DishViewHolder> {

    private ArrayList<Dish> dishes;

    public DishAdapter(ArrayList<Dish> dishes) {
        this.dishes = dishes;
    }

    public void setDishes(ArrayList<Dish> dishes) {
        this.dishes = dishes;
    }

    @NonNull
    @Override
    public DishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_dish, parent, false);
        return new DishViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DishViewHolder holder, int position) {
        Dish dish = dishes.get(position);
        holder.tvID.setText("Dish ID: " + dish.getId());
        holder.tvName.setText("Name: " + dish.getName());
        holder.tvType.setText("Type: " + dish.getType());
        holder.tvPrice.setText("Price: $" + dish.getPrice());
        holder.tvIngredients.setText("Ingredients: " + dish.getIngredients());  // Add this line to display ingredients
    }

    @Override
    public int getItemCount() {
        return dishes.size();
    }

    public static class DishViewHolder extends RecyclerView.ViewHolder {
        TextView tvID, tvName, tvType, tvPrice, tvIngredients;  // Add tvIngredients

        public DishViewHolder(@NonNull View itemView) {
            super(itemView);
            tvID = itemView.findViewById(R.id.tv_dish_id);
            tvName = itemView.findViewById(R.id.tv_dish_name);
            tvType = itemView.findViewById(R.id.tv_dish_type);
            tvPrice = itemView.findViewById(R.id.tv_dish_price);
            tvIngredients = itemView.findViewById(R.id.tv_dish_ingredients);  // Initialize tvIngredients
        }
    }

}
