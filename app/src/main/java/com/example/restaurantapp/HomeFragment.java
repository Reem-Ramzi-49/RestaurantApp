package com.example.restaurantapp;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.restaurantapp.databinding.FragmentHomeBinding;
import com.example.restaurantapp.databinding.ItemTabBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private AppViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);


        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new FoodFragment());
        fragments.add(new DrinksFragment());
        fragments.add(new SweetFragment());

        ArrayList<String> tabs = new ArrayList<>();
        tabs.add(getString(R.string.tab_food));
        tabs.add(getString(R.string.tab_drink));
        tabs.add(getString(R.string.tab_sweet));


        int[] tabIcons = {R.drawable.food1, R.drawable.drinks2, R.drawable.img};

        AdapterHomeMenu adapter = new AdapterHomeMenu(requireActivity(), fragments);
        binding.viewPager.setAdapter(adapter);

         new TabLayoutMediator(binding.tabLayout, binding.viewPager,
                (tab, position) -> {
                    ItemTabBinding tabBinding = ItemTabBinding.inflate(LayoutInflater.from(getContext()));

                    tabBinding.tabImage.setImageResource(tabIcons[position]);
                    tabBinding.tabText.setText(tabs.get(position));

                    tab.setCustomView(tabBinding.getRoot());
                }).attach();

         binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View v = tab.getCustomView();
                if (v != null) {
                    ItemTabBinding tabBinding = ItemTabBinding.bind(v);
                    v.setBackgroundResource(R.drawable.tab_selected);
                    tabBinding.tabText.setTextColor(Color.WHITE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View v = tab.getCustomView();
                if (v != null) {
                    ItemTabBinding tabBinding = ItemTabBinding.bind(v);
                    v.setBackgroundResource(R.drawable.tab_unselected);
                    tabBinding.tabText.setTextColor(Color.BLACK);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

         binding.searchBar.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.setSearchQuery(s.toString().trim());
            }
        });

         binding.tabLayout.post(() -> {
            TabLayout.Tab firstTab = binding.tabLayout.getTabAt(0);
            if (firstTab != null && firstTab.getCustomView() != null) {
                firstTab.select();
                View v = firstTab.getCustomView();
                ItemTabBinding tabBinding = ItemTabBinding.bind(v);
                v.setBackgroundResource(R.drawable.tab_selected);
                tabBinding.tabText.setTextColor(Color.WHITE);
            }
        });

        return binding.getRoot();
    }
}
