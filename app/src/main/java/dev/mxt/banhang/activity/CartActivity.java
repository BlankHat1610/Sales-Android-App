package dev.mxt.banhang.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.text.DecimalFormat;
import java.util.Objects;

import dev.mxt.banhang.R;
import dev.mxt.banhang.adapter.CartAdapter;

public class CartActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView cartRecyclerView;
    private static TextView tvTotalPrice;
    private TextView tvEmptyMessage;
    private Button btnCheckOut;
    private CartAdapter cartAdapter;
    private static final String TAG = "CartActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        init();
        setActionToolbar();
        checkData();
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

    public static void calculateTotalPrice() {
        long totalprice = 0;
        for (int i = 0; i < MainActivity.shoppingCartArrayList.size(); i++) {
            totalprice += MainActivity.shoppingCartArrayList.get(i).getPrice();
        }

        DecimalFormat decimalFormat = new DecimalFormat("###,###,### Đ");
        tvTotalPrice.setText(decimalFormat.format(totalprice));
    }

    public void checkData() {
        if (MainActivity.shoppingCartArrayList.size() <= 0) {
            cartAdapter.notifyDataSetChanged();
            tvEmptyMessage.setVisibility(View.VISIBLE);
            cartRecyclerView.setVisibility(View.GONE);
        } else {
            cartAdapter.notifyDataSetChanged();
            tvEmptyMessage.setVisibility(View.GONE);
            cartRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void setActionToolbar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void init() {
        tvTotalPrice = (TextView) findViewById(R.id.total_price);
        tvEmptyMessage = (TextView) findViewById(R.id.empty_message);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        btnCheckOut = (Button) findViewById(R.id.checkout);
        btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.shoppingCartArrayList.size() <= 0) {
                    Snackbar.make(findViewById(R.id.checkout)
                            , "Cart is empty!!!"
                            , BaseTransientBottomBar.LENGTH_LONG).show();
                } else if (hasLogin()) {
                    startActivity(new Intent(CartActivity.this, PaymentActivity.class));
                } else Snackbar.make(findViewById(R.id.checkout)
                            , "You should Login first!!!"
                            , BaseTransientBottomBar.LENGTH_LONG).show();
            }
        });

        cartRecyclerView = (RecyclerView) findViewById(R.id.cart_recyclerview);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        cartAdapter = new CartAdapter(this, MainActivity.shoppingCartArrayList);
        cartRecyclerView.setAdapter(cartAdapter);
    }
}