package com.example.restaurantapp;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.restaurantapp.databinding.FragmentFoodBinding;

import java.util.ArrayList;

public class FoodFragment extends Fragment {

    private AppViewModel viewModel;
    private FragmentFoodBinding binding;

    public FoodFragment() {}

    public static FoodFragment newInstance() {
        return new FoodFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFoodBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);

        AdapterMenu adapter = new AdapterMenu(new ArrayList<>(), dish -> {
            Bundle bundle = new Bundle();
            bundle.putInt("dishId", dish.getDish_id());

            // ✅ نمرر resId
            bundle.putInt("nameRes", dish.getNameResId());
            bundle.putInt("descRes", dish.getDescResId());

            // ✅ نمرر النصوص كـ fallback
            bundle.putString("name", dish.getNameText());
            bundle.putString("desc", dish.getDescText());

            bundle.putDouble("price", dish.getPrice());
            bundle.putInt("imageRes", dish.getImageInt());
            bundle.putString("imageUrl", dish.getImageUrl());

            FoodDetailsFragment detailsFragment = new FoodDetailsFragment();
            detailsFragment.setArguments(bundle);

            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, detailsFragment)
                    .addToBackStack(null)
                    .commit();
        });

        binding.recyclerFood.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.recyclerFood.setAdapter(adapter);

        // 🔍 البحث أو العرض العادي
        viewModel.getSearchQuery().observe(getViewLifecycleOwner(), keyword -> {
            if (keyword != null && !keyword.isEmpty()) {
                viewModel.searchDishesByCategory("Food", keyword)
                        .observe(getViewLifecycleOwner(), adapter::setDishes);
            } else {
                viewModel.getDishesByCategory("Food")
                        .observe(getViewLifecycleOwner(), adapter::setDishes);
            }
        });

        return binding.getRoot();
    }
}
