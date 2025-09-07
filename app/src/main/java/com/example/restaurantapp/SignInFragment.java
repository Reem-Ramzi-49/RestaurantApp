package com.example.restaurantapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.restaurantapp.databinding.FragmentSignInBinding;
import com.example.restaurantapp.databinding.FragmentSignUpBinding;


public class SignInFragment extends Fragment {

     private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

     private String mParam1;
    private String mParam2;

    public SignInFragment() {
     }

     public static SignInFragment newInstance(String param1, String param2) {
        SignInFragment fragment = new SignInFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentSignInBinding binding = FragmentSignInBinding.inflate(inflater , container ,false);
        return binding.getRoot();    }
}