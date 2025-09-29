package com.example.restaurantapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.restaurantapp.databinding.FragmentProfileBinding;

import java.io.File;
import java.util.Locale;

public class ProfileFragment extends Fragment {

    AppViewModel viewModel;
    FragmentProfileBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);

        int userID = SignInFragment.SessionManager.getUserId(requireContext());
        if (userID != -1) {
            viewModel.getUserById(userID).observe(getViewLifecycleOwner(), user -> {
                if (user != null) {
                    binding.userName.setText(user.getName());
                    binding.userEmail.setText(user.getEmail());

                    if (user.getImageUri() != null) {
                        File file = new File(user.getImageUri());
                        if (file.exists()) {
                            binding.profileImage.setImageURI(Uri.fromFile(file));
                        } else {
                            binding.profileImage.setImageResource(R.drawable.baseline_person_24);
                        }
                    } else {
                        binding.profileImage.setImageResource(R.drawable.baseline_person_24);
                    }
                }
            });
        }

         binding.editIcon.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Edit_Profile.class);
            startActivity(intent);
        });

         binding.btnSignOut.setOnClickListener(v -> {
            LayoutInflater inflater1 = getLayoutInflater();
            View dialogView = inflater1.inflate(R.layout.dialog_logout, null);

            android.app.AlertDialog dialog = new android.app.AlertDialog.Builder(requireContext(), R.style.CustomDialog)
                    .setView(dialogView)
                    .create();

            dialogView.findViewById(R.id.btnCancel).setOnClickListener(view -> dialog.dismiss());

            dialogView.findViewById(R.id.btnConfirm).setOnClickListener(view -> {
                SignInFragment.SessionManager.logout(requireContext());

                Intent intent = new Intent(getActivity(), Social_Login_Screen.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                requireActivity().finish();
            });

            dialog.show();
        });

         binding.btnLanguage.setOnClickListener(v -> switchLanguage());

         binding.btnTheme.setOnClickListener(v -> switchTheme());

        return binding.getRoot();
    }

    private void switchLanguage() {
        String currentLang = getResources().getConfiguration().locale.getLanguage();
        String newLang = currentLang.equals("ar") ? "en" : "ar";

        android.content.res.Configuration config = new android.content.res.Configuration();
        config.setLocale(new Locale(newLang));
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        Intent refresh = new Intent(requireActivity(), MainActivity.class);
        refresh.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(refresh);
        requireActivity().finish();
    }

    private void switchTheme() {
        int currentMode = AppCompatDelegate.getDefaultNightMode();
        if (currentMode == AppCompatDelegate.MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }
}
