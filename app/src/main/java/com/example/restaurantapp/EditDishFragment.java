package com.example.restaurantapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.restaurantapp.databinding.FragmentEditDishBinding;

public class EditDishFragment extends Fragment {

    private FragmentEditDishBinding binding;
    private AppViewModel viewModel;
    private static final int PICK_IMAGE = 101;
    private Uri selectedImageUri;
    private int dishId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditDishBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);

        if (getArguments() != null) {
            dishId = getArguments().getInt("dishId", -1);

             viewModel.getDishById(dishId, dish -> {
                if (dish != null) {
                    requireActivity().runOnUiThread(() -> {
                        binding.etEditDishName.setText(dish.getNameText());
                        binding.etEditDishDesc.setText(dish.getDescText());
                        binding.etEditDishPrice.setText(String.valueOf(dish.getPrice()));
                        binding.etEditDishImageUrl.setText(dish.getImageUrl());

                         if (dish.getImageUrl() != null && !dish.getImageUrl().isEmpty()) {
                            Glide.with(this).load(dish.getImageUrl())
                                    .placeholder(R.drawable.kinfe)
                                    .error(R.drawable.error_image)
                                    .into(binding.editDishImage);
                        } else if (dish.getImageInt() != 0) {
                            binding.editDishImage.setImageResource(dish.getImageInt());
                        }

                         ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                                requireContext(),
                                R.array.dish_categories_display, // للعرض فقط
                                android.R.layout.simple_spinner_item
                        );
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        binding.spinnerEditCategory.setAdapter(adapter);

                         String[] values = getResources().getStringArray(R.array.dish_categories_values);
                        for (int i = 0; i < values.length; i++) {
                            if (values[i].equals(dish.getCategory())) {
                                binding.spinnerEditCategory.setSelection(i);
                                break;
                            }
                        }
                    });
                }
            });
        }

         binding.editCameraIcon.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, PICK_IMAGE);
        });

         binding.btnUpdateDish.setOnClickListener(v -> saveUpdates());

        return binding.getRoot();
    }

    private void saveUpdates() {
        String name = binding.etEditDishName.getText().toString().trim();
        String desc = binding.etEditDishDesc.getText().toString().trim();
        String priceStr = binding.etEditDishPrice.getText().toString().trim();
        String imageUrl = binding.etEditDishImageUrl.getText().toString().trim();

         String[] categoryValues = getResources().getStringArray(R.array.dish_categories_values);
        int selectedPos = binding.spinnerEditCategory.getSelectedItemPosition();
        String category = categoryValues[selectedPos]; // Food/Drinks/Sweets

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(desc) || TextUtils.isEmpty(priceStr)) {
            Toast.makeText(requireContext(), "املأ جميع الحقول", Toast.LENGTH_SHORT).show();
            return;
        }

        double price = Double.parseDouble(priceStr);

         Entity_Dishes updatedDish = new Entity_Dishes(
                0,
                name,
                category,
                0,
                desc,
                price,
                imageUrl,
                true
        );
        updatedDish.setDish_id(dishId);

         viewModel.updateDish(updatedDish);
        Toast.makeText(requireContext(), "تم تحديث المنتج", Toast.LENGTH_SHORT).show();

         FoodDetailsFragment detailsFragment = new FoodDetailsFragment();
        Bundle args = new Bundle();
        args.putInt("dishId", dishId);
        args.putString("name", name);
        args.putString("desc", desc);
        args.putDouble("price", price);
        args.putString("imageUrl", imageUrl);
        args.putString("category", category);
        detailsFragment.setArguments(args);

        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, detailsFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                binding.editDishImage.setImageURI(selectedImageUri);
                binding.etEditDishImageUrl.setText(selectedImageUri.toString());
            }
        }
    }
}
