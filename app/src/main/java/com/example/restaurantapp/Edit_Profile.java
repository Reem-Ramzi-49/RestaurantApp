package com.example.restaurantapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.restaurantapp.databinding.ActivityEditProfileBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class Edit_Profile extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 100;

    private ActivityEditProfileBinding binding;
    private AppViewModel viewModel;
    private int currentUserId;

    private String savedImagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(AppViewModel.class);

        currentUserId = SignInFragment.SessionManager.getUserId(this);

         if (currentUserId != -1) {
            viewModel.getUserById(currentUserId).observe(this, user -> {
                if (user != null) {
                    binding.etName.setText(user.getName());
                    binding.etPhone.setText(user.getPhone());
                    binding.etAddress.setText(user.getAddress());
                    binding.etEmail.setText(user.getEmail()); // للعرض فقط

                    if (user.getImageUri() != null) {
                        savedImagePath = user.getImageUri();
                        File file = new File(savedImagePath);
                        if (file.exists()) {
                            binding.profileImage.setImageURI(Uri.fromFile(file));
                        } else {
                            binding.profileImage.setImageResource(R.drawable.baseline_person_24);
                        }
                    }
                }
            });
        }

         binding.cameraIcon.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        });

         binding.btnSave.setOnClickListener(v -> {
            String name = binding.etName.getText().toString().trim();
            String email = binding.etEmail.getText().toString().trim();
            String phone = binding.etPhone.getText().toString().trim();
            String address = binding.etAddress.getText().toString().trim();
            String password = binding.etPassword.getText().toString().trim();

            if (TextUtils.isEmpty(name)) {
                binding.etName.setError(getString(R.string.error_name_required));
                return;
            }

             viewModel.updateUserProfile1(currentUserId,
                    name, email, phone, address, password, savedImagePath);

            Toast.makeText(this, getString(R.string.profile_updated_success), Toast.LENGTH_SHORT).show();
            finish();
        });

         binding.backIcon.setOnClickListener(view -> finish());
    }

     @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            savedImagePath = saveImageToInternalStorage(uri);

            if (savedImagePath != null) {
                binding.profileImage.setImageURI(Uri.fromFile(new File(savedImagePath)));
            }
        }
    }

     private String saveImageToInternalStorage(Uri uri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            File file = new File(getFilesDir(), "profile_" + System.currentTimeMillis() + ".jpg");
            FileOutputStream outputStream = new FileOutputStream(file);

            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, len);
            }

            outputStream.close();
            inputStream.close();

            return file.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
