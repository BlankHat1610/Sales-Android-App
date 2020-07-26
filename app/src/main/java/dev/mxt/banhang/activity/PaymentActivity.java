package dev.mxt.banhang.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.Objects;

import dev.mxt.banhang.R;
import dev.mxt.banhang.adapter.CartAdapter;
import dev.mxt.banhang.api.RetrofitClient;
import dev.mxt.banhang.dialog.ShippingInfoDialog;
import dev.mxt.banhang.model.Transaction;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity
        implements View.OnClickListener, ShippingInfoDialog.ShippingInfoDialogListener{

    private Toolbar toolbar;
    private TextView tvDeliveryAddress;
    private TextView tvDeliveryPhone;
    private TextView tvChangeContactInfo;
    private TextView tvTotalPrice;
    private EditText etNote;
    private Button btnOrder;
    private RecyclerView cartRecyclerView;
    private int totalPrice = 0;
    private static final String TAG = "PaymentActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        MainActivity.sharedPreferences  = getSharedPreferences("userData", Context.MODE_PRIVATE);

        init();
        setActionToolbar();
        calculateTotalPrice();
    }

    private boolean hasLogin() {
        MainActivity.sharedPreferences  = getSharedPreferences("userData", Context.MODE_PRIVATE);
        String strPhoneNumber = MainActivity.sharedPreferences.getString("phone", null);
        Log.d(TAG, "hasLogin: " + strPhoneNumber);
        if (strPhoneNumber != null) {
            return true;
        }
        return false;
    }

    private void calculateTotalPrice() {
        for (int i = 0; i < MainActivity.shoppingCartArrayList.size(); i++) {
            totalPrice += MainActivity.shoppingCartArrayList.get(i).getPrice();
        }

        DecimalFormat decimalFormat = new DecimalFormat("###,###,### Đ");
        tvTotalPrice.setText(decimalFormat.format(totalPrice + 30000));
    }

    private void setActionToolbar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void init() {
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        tvChangeContactInfo = (TextView) findViewById(R.id.change_contact_info);
        tvChangeContactInfo.setOnClickListener(this);
        tvDeliveryAddress = (TextView) findViewById(R.id.destination_address);
        tvDeliveryAddress.setText(MainActivity.sharedPreferences.getString("address", null));
        tvDeliveryPhone = (TextView) findViewById(R.id.destination_phone_number);
        tvDeliveryPhone.setText(MainActivity.sharedPreferences.getString("phone", null));
        tvTotalPrice = (TextView) findViewById(R.id.total_price);
        etNote = (EditText) findViewById(R.id.edit_note);
        btnOrder = (Button) findViewById(R.id.order);
        btnOrder.setOnClickListener(this);

        cartRecyclerView = (RecyclerView) findViewById(R.id.cart_recyclerview);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        cartRecyclerView.setAdapter(new CartAdapter(this, MainActivity.shoppingCartArrayList));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.change_contact_info:
                openChangeInfoDialog();
                break;
            case R.id.order:
                if (hasLogin()) {
                    order();
                    MainActivity.sharedPreferences = getSharedPreferences("shoppingCartArray", Context.MODE_PRIVATE);
                    MainActivity.sharedPreferences.edit().clear().apply();
                    startActivity(new Intent(PaymentActivity.this, MainActivity.class));
                } else Snackbar.make(findViewById(R.id.order)
                        , "You should Login first!!!"
                        , BaseTransientBottomBar.LENGTH_LONG).show();
                break;
            default: break;
        }
    }

    private void order() {
        String address = tvDeliveryAddress.getText().toString().trim();
        String phone = tvDeliveryPhone.getText().toString().trim();
        String note = etNote.getText().toString().trim();

        Gson gson = new Gson();
        String cartItemJson = gson.toJson(MainActivity.shoppingCartArrayList);

        Log.d(TAG, "order: address " + address + "phone " + phone + "note " + note + "userid " + MainActivity.sharedPreferences.getInt("id", 0));
        Log.d(TAG, "order: json " + cartItemJson);

        /*Do the registration using the api call*/
        Call<Transaction> call = RetrofitClient
                .getInstance()
                .getApi()
                .createOrder(
                        MainActivity.sharedPreferences.getInt("id", 0),
                        totalPrice, note, address, phone, cartItemJson);
        call.enqueue(new Callback<Transaction>() {
            @Override
            public void onResponse(Call<Transaction> call, Response<Transaction> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(PaymentActivity.this, "Response Failed", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "onResponse: response is Failed " + response.code());
                    return;
                }
                Toast.makeText(PaymentActivity.this, "Dang Ky Thanh Cong", Toast.LENGTH_LONG).show();
                startActivity(new Intent(PaymentActivity.this, MainActivity.class));
            }

            @Override
            public void onFailure(Call<Transaction> call, Throwable t) {
                Toast.makeText(PaymentActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void openChangeInfoDialog() {
        ShippingInfoDialog shippingInfoDialog = new ShippingInfoDialog();
        shippingInfoDialog.show(getSupportFragmentManager(), "Shipping Change Info Dialog");
    }

    @Override
    public void applyTexts(String address, String phone) {
        tvDeliveryAddress.setText(address);
        tvDeliveryPhone.setText(phone);
    }
}