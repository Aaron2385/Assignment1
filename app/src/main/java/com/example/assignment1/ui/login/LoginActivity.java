package com.example.assignment1.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment1.MainActivity;
import com.example.assignment1.R;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;
    private Button loginButton;
    private ProgressBar loadingProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); // make sure this is your correct XML

        // Linking UI Components
        usernameEditText = findViewById(R.id.username);
        loginButton = findViewById(R.id.login);
        passwordEditText = findViewById(R.id.password);
        loadingProgressBar = findViewById(R.id.loading);

        // Enable login button initially (optional)
        loginButton.setEnabled(true);

        loginButton.setOnClickListener(v -> performLogin());

        passwordEditText.setOnEditorActionListener((TextView v, int actionId, KeyEvent event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                    actionId == EditorInfo.IME_NULL ||
                    (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)) {
                performLogin();
                return true;
            }
            return false;
        });
    }

    private void performLogin() {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        // Show loading
        loadingProgressBar.setVisibility(View.VISIBLE);

        // Simple input validation
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter both username and password", Toast.LENGTH_SHORT).show();
            loadingProgressBar.setVisibility(View.GONE);
            return;
        }

        // ✅ Hardcoded accounts
        if ((username.equals("admin") && password.equals("admin123")) ||
                (username.equals("normal") && password.equals("normal123"))) {

            Toast.makeText(this, "Login Successful as " + username, Toast.LENGTH_SHORT).show();

            // Redirect to MainActivity and pass role
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("role", username);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish(); // prevent back to login

        } else {
            Toast.makeText(this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
            loadingProgressBar.setVisibility(View.GONE);
        }
    }

}
