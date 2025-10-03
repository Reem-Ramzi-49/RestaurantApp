package com.example.restaurantapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.restaurantapp.databinding.FragmentAddDishBinding;

public class AddDishFragment extends Fragment {

    private AppViewModel viewModel;
    private FragmentAddDishBinding binding;
    private static final int PICK_IMAGE = 100;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddDishBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);

         ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.dish_categories_display,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerCategory.setAdapter(adapter);

         binding.dishImage.setOnClickListener(v -> showImageOptionDialog());
        binding.cameraIcon.setOnClickListener(v -> showImageOptionDialog());

         binding.btnSaveDish.setOnClickListener(v -> saveDish());

        return binding.getRoot();
    }

    private void showImageOptionDialog() {
        String[] options = {
                getString(R.string.pick_from_gallery),
                getString(R.string.enter_image_url)
        };

        new AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.choose_image_method))
                .setItems(options, (dialog, which) -> {
                    if (which == 0) {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        startActivityForResult(intent, PICK_IMAGE);
                    } else {
                        final EditText input = new EditText(requireContext());
                        input.setHint(getString(R.string.enter_url_hint));

                        new AlertDialog.Builder(requireContext())
                                .setTitle(getString(R.string.enter_image_url))
                                .setView(input)
                                .setPositiveButton(getString(R.string.ok), (d, w) -> {
                                    String url = input.getText().toString().trim();
                                    if (!url.isEmpty()) {
                                        binding.etDishImageUrl.setText(url);
                                        Glide.with(requireContext())
                                                .load(url)
                                                .placeholder(R.drawable.kinfe)
                                                .error(R.drawable.error_image)
                                                .into(binding.dishImage);
                                    }
                                })
                                .setNegativeButton(getString(R.string.cancel), null)
                                .show();
                    }
                })
                .show();
    }

    private void saveDish() {
        String name = binding.etDishName.getText().toString().trim();
        String desc = binding.etDishDesc.getText().toString().trim();
        String priceStr = binding.etDishPrice.getText().toString().trim();
        String imageUrl = binding.etDishImageUrl.getText().toString().trim();

        if (name.isEmpty() || desc.isEmpty() || priceStr.isEmpty()) {
            Toast.makeText(getContext(), getString(R.string.fill_all_fields), Toast.LENGTH_SHORT).show();
            return;
        }

        double price = Double.parseDouble(priceStr);

         String[] categories = getResources().getStringArray(R.array.dish_categories_values);
        int pos = binding.spinnerCategory.getSelectedItemPosition();
        String category = categories[pos];

        Entity_Dishes dish = new Entity_Dishes(
                0,
                name,
                category,
                0,
                desc,
                price,
                imageUrl,
                true
        );

        viewModel.insertDish(dish);
        Toast.makeText(getContext(), getString(R.string.dish_added), Toast.LENGTH_SHORT).show();
        requireActivity().getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            binding.dishImage.setImageURI(selectedImage);
            binding.etDishImageUrl.setText(selectedImage.toString());
        }
    }
}
