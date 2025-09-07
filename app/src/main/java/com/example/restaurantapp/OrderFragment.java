package com.example.restaurantapp;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.restaurantapp.databinding.FragmentOrderBinding;

public class OrderFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";

    private String mParam1;

    public OrderFragment() {
    }

    public static OrderFragment newInstance(String param1) {
        OrderFragment fragment = new OrderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
         fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
         }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentOrderBinding binding = FragmentOrderBinding.inflate(inflater, container, false);

        if (mParam1 != null) {
            binding.tvName.setText(mParam1);
        }

        return binding.getRoot();
    }
}
