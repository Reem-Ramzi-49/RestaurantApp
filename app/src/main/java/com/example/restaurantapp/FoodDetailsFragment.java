package com.example.restaurantapp;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.InputType;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.example.restaurantapp.databinding.FragmentFoodDetailsBinding;

public class FoodDetailsFragment extends Fragment {

    private FragmentFoodDetailsBinding binding;
    private AppViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentFoodDetailsBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);

        if (getArguments() != null) {
            int dishId   = getArguments().getInt("dishId");

             int nameRes  = getArguments().getInt("nameRes", 0);
            String name  = getArguments().getString("name");

            int descRes  = getArguments().getInt("descRes", 0);
            String desc  = getArguments().getString("desc");

            double price = getArguments().getDouble("price");
            int imageRes = getArguments().getInt("imageRes", 0);
            String imageUrl = getArguments().getString("imageUrl");

             if (nameRes != 0) {
                binding.foodName.setText(getString(nameRes));
            } else {
                binding.foodName.setText(name != null ? name : "Unnamed");
            }

             if (descRes != 0) {
                binding.foodDetails.setText(getString(descRes));
            } else {
                binding.foodDetails.setText(desc != null ? desc : "No description");
            }

            binding.foodPrice.setText(String.format("$%.2f", price));

             if (imageRes != 0) {
                binding.foodImage.setImageResource(imageRes);
            } else if (imageUrl != null && !imageUrl.isEmpty()) {
                Glide.with(this)
                        .load(Uri.parse(imageUrl))
                        .placeholder(R.drawable.food1)
                        .into(binding.foodImage);
            } else {
                binding.foodImage.setImageResource(R.drawable.food1);
            }

             binding.btnAddToCart.setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setTitle(getString(R.string.dialog_add_to_cart_title));
                builder.setMessage(getString(R.string.dialog_enter_quantity));

                final EditText input = new EditText(requireContext());
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setHint(getString(R.string.dialog_quantity_hint));
                builder.setView(input);

                builder.setPositiveButton(getString(R.string.dialog_add), (dialog, which) -> {
                    String qtyStr = input.getText().toString().trim();
                    int qty = qtyStr.isEmpty() ? 1 : Integer.parseInt(qtyStr);

                    viewModel.getDishById(dishId, dish -> {
                        if (dish != null) {
                            viewModel.addToCart(dishId, qty);

                            new Handler(Looper.getMainLooper()).post(() ->
                                    Toast.makeText(requireContext(),
                                            getString(R.string.toast_item_added),
                                            Toast.LENGTH_SHORT).show()
                            );

                        } else {
                            Log.e("FoodDetails", "dishId " + dishId + " not found in dishes table");
                            new Handler(Looper.getMainLooper()).post(() ->
                                    new AlertDialog.Builder(requireContext())
                                            .setTitle(getString(R.string.error_title))
                                            .setMessage(getString(R.string.error_item_not_exist))
                                            .setPositiveButton(getString(R.string.ok), null)
                                            .show()
                            );
                        }
                    });
                });

                builder.setNegativeButton(getString(R.string.cancel), (dialog, which) -> dialog.dismiss());

                AlertDialog dialog = builder.create();
                if (dialog.getWindow() != null) {
                    dialog.getWindow().setBackgroundDrawable(
                            ContextCompat.getDrawable(requireContext(), R.drawable.dialog_bg));
                }
                dialog.show();

                dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                        .setTextColor(ContextCompat.getColor(requireContext(), R.color.orange));
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                        .setTextColor(ContextCompat.getColor(requireContext(), R.color.orange));
            });

              binding.editIcon.setVisibility(View.GONE);
            int userId = SignInFragment.SessionManager.getUserId(requireContext());
            viewModel.getUserById(userId).observe(getViewLifecycleOwner(), user -> {
                if (user != null && user.getRole() == 1) {
                    binding.editIcon.setVisibility(View.VISIBLE);
                    binding.editIcon.setOnClickListener(v -> {
                        Bundle bundle = new Bundle();
                        bundle.putInt("dishId", dishId);

                        EditDishFragment editFragment = new EditDishFragment();
                        editFragment.setArguments(bundle);

                        requireActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, editFragment)
                                .addToBackStack(null)
                                .commit();
                    });
                }
            });
        }

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
