package com.example.restaurantapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.restaurantapp.databinding.FragmentSignInBinding;

public class SignInFragment extends Fragment {

    private FragmentSignInBinding binding;
    private AppViewModel viewModel;
    private boolean isPasswordVisible = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSignInBinding.inflate(inflater, container, false);
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

        binding.btnSignIn.setOnClickListener(v -> {
            String email = binding.emailInput.getText().toString().trim();
            String password = binding.passwordInput.getText().toString().trim();

            if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.emailInput.setError(getString(R.string.error_invalid_email));
                return;
            }
            if (TextUtils.isEmpty(password)) {
                binding.passwordInput.setError(getString(R.string.error_empty_password));
                return;
            }

            binding.btnSignIn.setEnabled(false);

            viewModel.login(email, password).observe(getViewLifecycleOwner(), user -> {
                binding.btnSignIn.setEnabled(true);

                if (user != null) {
                    SessionManager.saveLogin(requireContext(), user.getUser_id());
                    Intent i = new Intent(requireContext(), MainActivity.class);
                    i.putExtra("open_tab", "profile");
                    startActivity(i);
                    requireActivity().finish();
                    Toast.makeText(getContext(), getString(R.string.login_success), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), getString(R.string.login_failed), Toast.LENGTH_SHORT).show();
                }
            });
        });

        binding.signUp.setOnClickListener(v -> {
            if (requireActivity() instanceof Authentication) {
                ((Authentication) requireActivity()).goToSignUPTab();
            }
        });

        return binding.getRoot();
    }

    public static class SessionManager {
        private static final String PREF = "session";
        private static final String KEY_LOGGED_IN = "logged_in";
        private static final String KEY_USER_ID = "user_id";

        public static void saveLogin(Context ctx, int userId) {
            ctx.getSharedPreferences(PREF, Context.MODE_PRIVATE)
                    .edit()
                    .putBoolean(KEY_LOGGED_IN, true)
                    .putInt(KEY_USER_ID, userId)
                    .apply();
        }

        public static int getUserId(Context ctx) {
            return ctx.getSharedPreferences(PREF, Context.MODE_PRIVATE)
                    .getInt(KEY_USER_ID, -1);
        }

        public static boolean isLoggedIn(Context ctx) {
            return ctx.getSharedPreferences(PREF, Context.MODE_PRIVATE)
                    .getBoolean(KEY_LOGGED_IN, false);
        }

        public static void logout(Context ctx) {
            ctx.getSharedPreferences(PREF, Context.MODE_PRIVATE)
                    .edit()
                    .clear()
                    .apply();
        }
    }
}
