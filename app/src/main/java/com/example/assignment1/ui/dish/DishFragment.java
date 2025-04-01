package com.example.assignment1.ui.dish;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.assignment1.Dish;
import com.example.assignment1.DishAdapter;
import com.example.assignment1.DishDatabaseManager;
import com.example.assignment1.databinding.FragmentDishBinding;

public class DishFragment extends Fragment {

    private FragmentDishBinding binding;
    private DishDatabaseManager db;
    private DishAdapter adapter;
    private String imageUri = "";

    ActivityResultLauncher<Intent> imagePickerLauncher;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDishBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        db = new DishDatabaseManager(requireContext());
        binding.recyclerDish.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new DishAdapter(db.getAllDishes());
        binding.recyclerDish.setAdapter(adapter);

        // --- Button handlers ---

        binding.btnAdd.setOnClickListener(v -> addDish());
        binding.btnCancel.setOnClickListener(v -> clearFields());
        binding.btnView.setOnClickListener(v -> viewDishes());
        binding.btnUpdate.setOnClickListener(v -> updateDish());
        binding.btnBrowse.setOnClickListener(v -> openImagePicker());

        // --- Image Picker result handler ---
        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Uri uri = result.getData().getData();
                        if (uri != null) {
                            imageUri = uri.toString();
                            binding.ivDishImage.setImageURI(uri);
                        }
                    }
                }
        );

        return root;
    }

    private void addDish() {
        String idStr = binding.etDishId.getText().toString();  // Get the dish id entered by the user
        String name = binding.etDishName.getText().toString();
        String ingredients = binding.etIngredients.getText().toString();
        String priceStr = binding.etPrice.getText().toString();

        // Basic validation
        if (idStr.isEmpty() || name.isEmpty() || ingredients.isEmpty() || priceStr.isEmpty() || binding.rgDishType.getCheckedRadioButtonId() == -1) {
            Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int id = Integer.parseInt(idStr);  // Parse the user-entered id
        double price = Double.parseDouble(priceStr);
        String type = ((RadioButton) binding.getRoot().findViewById(binding.rgDishType.getCheckedRadioButtonId())).getText().toString();

        // Save dish with user-provided id
        db.addDish(id, name, type, ingredients, price, "");
        Toast.makeText(requireContext(), "Dish Added", Toast.LENGTH_SHORT).show();

        // Refresh List
        adapter.setDishes(db.getAllDishes());
        adapter.notifyDataSetChanged();
    }

    private void updateDish() {
        if (!validateInput()) return;

        int id = Integer.parseInt(binding.etDishId.getText().toString());
        Dish dish = getDishFromInput();
        db.updateDish(id, dish.getName(), dish.getType(), dish.getIngredients(), dish.getPrice(), dish.getImageLink());
        Toast.makeText(requireContext(), "Dish Updated!", Toast.LENGTH_SHORT).show();
        clearFields();
    }

    private void clearFields() {
        binding.etDishId.setText("");
        binding.etDishName.setText("");
        binding.rgDishType.clearCheck();
        binding.etIngredients.setText("");
        binding.etPrice.setText("");
        binding.ivDishImage.setImageResource(0);
        imageUri = "";
    }

    private void viewDishes() {
        adapter.setDishes(db.getAllDishes());
        adapter.notifyDataSetChanged();
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        imagePickerLauncher.launch(Intent.createChooser(intent, "Select Image"));
    }

    private Dish getDishFromInput() {
        String name = binding.etDishName.getText().toString();
        String type = ((RadioButton) binding.getRoot().findViewById(binding.rgDishType.getCheckedRadioButtonId())).getText().toString();
        String ingredients = binding.etIngredients.getText().toString();
        double price = Double.parseDouble(binding.etPrice.getText().toString());
        return new Dish(0, name, type, ingredients, price, imageUri);
    }

    private boolean validateInput() {
        if (binding.etDishName.getText().toString().isEmpty() ||
                binding.rgDishType.getCheckedRadioButtonId() == -1 ||
                binding.etIngredients.getText().toString().isEmpty() ||
                binding.etPrice.getText().toString().isEmpty()) {
            Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
