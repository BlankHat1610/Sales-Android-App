package dev.mxt.banhang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import dev.mxt.banhang.R;
import dev.mxt.banhang.api.RetrofitClient;
import dev.mxt.banhang.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etName, etEmail, etAddress, etPassword, etPhone;
    private static final String TAG = "RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName = (EditText) findViewById(R.id.name);
        etEmail = (EditText) findViewById(R.id.mail);
        etAddress = (EditText) findViewById(R.id.address);
        etPassword = (EditText) findViewById(R.id.password);
        etPhone = (EditText) findViewById(R.id.phone_number);
        findViewById(R.id.sign_up).setOnClickListener(this);
        findViewById(R.id.login).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_up:
                userSignUp();
                break;
            case R.id.login:
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private void userSignUp() {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (name.isEmpty()) {
            etName.setError("Name required");
            etName.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            etEmail.setError("Email is required");
            etEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Enter is a valid email");
            etEmail.requestFocus();
            return;
        }

        if (address.isEmpty()) {
            etAddress.setError("Address required");
            etAddress.requestFocus();
            return;
        }

        if (phone.isEmpty()) {
            etPhone.setError("Address required");
            etPhone.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            etPassword.setError("Password required");
            etPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            etPassword.setError("Password should be at least 6 character long");
            etPassword.requestFocus();
            return;
        }

        /*Do the registration using the api call*/
        Call<User> call = RetrofitClient
                .getInstance()
                .getApi()
                .createUser(name, email, phone, address, password);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Response Failed", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "onResponse: response is Failed " + response.code());
                    return;
                }
                Toast.makeText(RegisterActivity.this, "Dang Ky Thanh Cong", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}