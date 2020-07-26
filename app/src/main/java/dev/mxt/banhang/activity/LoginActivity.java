package dev.mxt.banhang.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import dev.mxt.banhang.R;
import dev.mxt.banhang.api.RetrofitClient;
import dev.mxt.banhang.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText etPhone, etPassword;
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etPhone = (EditText) findViewById(R.id.phone_number);
        etPassword = (EditText) findViewById(R.id.password);
        findViewById(R.id.login).setOnClickListener(this);
        findViewById(R.id.register).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                userLogin();
                break;
            case R.id.register:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private void userLogin() {
        String phone = etPhone.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (phone.isEmpty()){
            etPhone.setError("Phone is required");
            etPhone.requestFocus();
            return;
        }

        if (password.isEmpty()){
            etPassword.setError("Password required");
            etPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            etPassword.setError("Password should be at least 6 character long");
            etPassword.requestFocus();
            return;
        }

        Call<User> call = RetrofitClient
                .getInstance()
                .getApi()
                .login(phone, password);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Response Failed", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "onResponse: response is Failed " + response.code());
                    return;
                }
                User user = response.body();
                if (user.getPhone() != null) {
                    saveData(user.getId(), user.getPhone(), user.getAddress(), user.getName(), user.getEmail());
                    Toast.makeText(LoginActivity.this, "Dang Nhap Thanh Cong", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void saveData(Integer id, String phone, String address, String name, String email) {
        MainActivity.sharedPreferences = getSharedPreferences("userData", Context.MODE_PRIVATE);
        MainActivity.editor = MainActivity.sharedPreferences.edit();
        MainActivity.editor.putString("phone", phone);
        MainActivity.editor.putString("name", name);
        MainActivity.editor.putString("email", email);
        MainActivity.editor.putString("address", address);
        MainActivity.editor.putInt("id", id);
        MainActivity.editor.apply();
    }
}
