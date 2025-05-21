package com.hcmus.app_computer_store_management.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.hcmus.app_computer_store_management.DatabaseHelper;
import com.hcmus.app_computer_store_management.R;

public class LoginActivity extends AppCompatActivity {
    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        dbHelper = new DatabaseHelper(this);

        loginButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập email và mật khẩu", Toast.LENGTH_SHORT).show();
                return;
            }

            if (dbHelper.checkUser(email, password)) {
                String role = dbHelper.getUserRole(email);
                Intent intent;
                if ("Admin".equals(role)) {
                    intent = new Intent(LoginActivity.this, AdminMainActivity.class);
                } else if ("Employee".equals(role)) {
                    intent = new Intent(LoginActivity.this, ClientMainActivity.class);
                } else {
                    Toast.makeText(this, "Vai trò không hợp lệ", Toast.LENGTH_SHORT).show();
                    return;
                }
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Email hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
            }
        });
    }
}