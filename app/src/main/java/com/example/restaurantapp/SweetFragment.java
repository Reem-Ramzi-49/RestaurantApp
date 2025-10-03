package com.example.restaurantapp;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.restaurantapp.databinding.FragmentSweetBinding;

import java.util.ArrayList;

public class SweetFragment extends Fragment {

    private AppViewModel viewModel;

    public SweetFragment() {}

    public static SweetFragment newInstance() {
        return new SweetFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentSweetBinding binding = FragmentSweetBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);

        AdapterMenu adapter = new AdapterMenu(new ArrayList<>(), dish -> {
            Bundle bundle = new Bundle();
            bundle.putInt("dishId", dish.getDish_id());

             bundle.putInt("nameRes", dish.getNameResId());
            bundle.putInt("descRes", dish.getDescResId());

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

        binding.recyclerSweet.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.recyclerSweet.setAdapter(adapter);

         viewModel.getSearchQuery().observe(getViewLifecycleOwner(), keyword -> {
            if (keyword != null && !keyword.isEmpty()) {
                viewModel.searchDishesByCategory("Sweets", keyword)
                        .observe(getViewLifecycleOwner(), adapter::setDishes);
            } else {
                viewModel.getDishesByCategory("Sweets")
                        .observe(getViewLifecycleOwner(), adapter::setDishes);
            }
        });

        return binding.getRoot();
    }
}
