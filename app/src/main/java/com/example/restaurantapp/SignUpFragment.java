package com.example.restaurantapp;

import android.os.Bundle;
import android.text.InputType;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.restaurantapp.databinding.FragmentSignUpBinding;

public class SignUpFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";

    private FragmentSignUpBinding binding;
    private AppViewModel viewModel;
    private String mParam1;
    private boolean isPasswordVisible = false;

    public SignUpFragment() {}

    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
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
        binding = FragmentSignUpBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);

         binding.togglePassword.setOnClickListener(v -> {
            if (isPasswordVisible) {
                binding.passwordInput.setInputType(
                        InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD
                );
                binding.togglePassword.setImageResource(R.drawable.baseline_visibility_off_24);
                isPasswordVisible = false;
            } else {
                binding.passwordInput.setInputType(
                        InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                );
                binding.togglePassword.setImageResource(R.drawable.icon_visibility);
                isPasswordVisible = true;
            }
            binding.passwordInput.setSelection(binding.passwordInput.getText().length());
        });

         binding.btnSignUp.setOnClickListener(v -> {
            String name = binding.nameInput.getText().toString().trim();
            String email = binding.emailInput.getText().toString().trim();
            String password = binding.passwordInput.getText().toString().trim();

            if (name.isEmpty()) {
                binding.nameInput.setError(getString(R.string.error_name_required));
                return;
            }
            if (email.isEmpty() || !email.contains("@")) {
                binding.emailInput.setError(getString(R.string.error_invalid_email));
                return;
            }
            if (password.isEmpty() || password.length() < 8) {
                binding.passwordInput.setError(getString(R.string.error_password_short));
                return;
            }

            Entity_Users user = new Entity_Users(
                    name,
                    email,
                    password,
                    "",
                    ""
            );

            viewModel.insertUser(user);

            Toast.makeText(getContext(), getString(R.string.signup_success), Toast.LENGTH_SHORT).show();

             if (requireActivity() instanceof Authentication) {
                ((Authentication) requireActivity()).goToSignInTab();
            }

            binding.nameInput.setText("");
            binding.emailInput.setText("");
            binding.passwordInput.setText("");
        });

         binding.signIn.setOnClickListener(v -> {
            if (requireActivity() instanceof Authentication) {
                ((Authentication) requireActivity()).goToSignInTab();
            }
        });

        return binding.getRoot();
    }
}
