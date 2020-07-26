package dev.mxt.banhang.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Objects;

import dev.mxt.banhang.R;
import dev.mxt.banhang.api.Api;
import dev.mxt.banhang.api.RetrofitClient;
import dev.mxt.banhang.model.ShoppingCart;
import dev.mxt.banhang.model.Smartphone;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhoneDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "PhoneDetailsActivity";
    private ImageView imgImageDetail;
    private TextView txPhoneName, txPrice, txReviewSum, txStarAvg;
    private HtmlTextView htmlTextView;
    private Integer phoneId;
    private List<Smartphone> smartphoneList;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_details);

        init();
        setActionToolbar();

        if (getIntent().hasExtra("id")) {

            phoneId = getIntent().getIntExtra("id", 0);
        }
        Log.d(TAG, "onCreate: phoneId: " + phoneId);

        getSmartphoneResponse();
    }

    private void init() {
        imgImageDetail = (ImageView) findViewById(R.id.image_details);
        txPhoneName = (TextView) findViewById(R.id.phone_name);
        txPrice = (TextView) findViewById(R.id.price);
        htmlTextView = (HtmlTextView) findViewById(R.id.html_text);
        findViewById(R.id.add_to_cart).setOnClickListener(this);
        findViewById(R.id.buy_now).setOnClickListener(this);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
    }

    private void setActionToolbar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
        toolbar.bringToFront();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tool_bar_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cart:
                startActivity(new Intent(getApplicationContext(), CartActivity.class));
        }
        return super.onOptionsItemSelected(item);
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

    private void getSmartphoneResponse() {
        Api api = RetrofitClient.getInstance().getApi();
        Call<List<Smartphone>> phoneDetailCall = api.getPhoneDetail(phoneId);

        phoneDetailCall.enqueue(new Callback<List<Smartphone>>() {
            @Override
            public void onResponse(Call<List<Smartphone>> call, Response<List<Smartphone>> response) {
                if (!response.isSuccessful()) {
                    Log.d("mxt", "Code: " + response.code());
                    return;
                }
                smartphoneList = response.body();
                showSmartphoneDetail(response.body());
                Toast.makeText(PhoneDetailsActivity.this, "Success", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<List<Smartphone>> call, Throwable t) {
                Log.d("mxt", "fail: " + t.getMessage());
                Toast.makeText(PhoneDetailsActivity.this, "Faild", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showSmartphoneDetail(List<Smartphone> body) {
        DecimalFormat decimalFormat = new DecimalFormat("###,###,### Đ");
        String priceFormatted = decimalFormat.format(body.get(0).getProPrice());
        Picasso.get().load(body.get(0).getProAvatar()).into(imgImageDetail);
        txPhoneName.setText(body.get(0).getProName());
        txPrice.setText(priceFormatted);
        htmlTextView.setHtml(body.get(0).getProContent()
                , new HtmlHttpImageGetter(htmlTextView, null, true));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_to_cart:
                addNewProductToCart();
                Toast.makeText(this, "Them san pham thanh cong!!!", Toast.LENGTH_SHORT);
                break;
            case R.id.buy_now:
                if (hasLogin()) {
                    addNewProductToCart();
                    startActivity(new Intent(PhoneDetailsActivity.this, CartActivity.class));
                } else {
                    Intent intentToLoginActivity = new Intent(PhoneDetailsActivity.this, LoginActivity.class);
                    startActivity(intentToLoginActivity);
                    Snackbar.make(findViewById(R.id.add_and_buy_button_layout)
                            , "You should Login first!!!"
                            , BaseTransientBottomBar.LENGTH_LONG).show();
                }
                break;
        }
    }

    private void addNewProductToCart() {
        if (MainActivity.shoppingCartArrayList.size() > 0) {
            boolean exists = false;
            for (int i = 0; i < MainActivity.shoppingCartArrayList.size(); i++) { // Lap tat ca item trong mang
                if (MainActivity.shoppingCartArrayList.get(i).getId() == smartphoneList.get(0).getId()) { // Phone (in cart) Id = Phone (in detail) Id
                    // set so luong cho dien thoai do = so luong da co + 1
                    MainActivity.shoppingCartArrayList.get(i).setQuantity(
                            MainActivity.shoppingCartArrayList.get(i).getQuantity() + 1
                    );
                    if (MainActivity.shoppingCartArrayList.get(i).getQuantity() >= 10) {
                        MainActivity.shoppingCartArrayList.get(i).setQuantity(10);
                    }
                    MainActivity.shoppingCartArrayList.get(i).setPrice(
                            smartphoneList.get(0).getProPrice() * MainActivity.shoppingCartArrayList.get(i).getQuantity()
                    );
                    exists = true;
                }
            }
            if (!exists) {
                MainActivity.shoppingCartArrayList
                        .add(new ShoppingCart(smartphoneList.get(0).getId()
                                , smartphoneList.get(0).getProName()
                                , smartphoneList.get(0).getProPrice()
                                , smartphoneList.get(0).getProAvatar()
                                , smartphoneList.get(0).getCName()
                                , 1));
            }
        } else {
            MainActivity.shoppingCartArrayList
                    .add(new ShoppingCart(smartphoneList.get(0).getId()
                            , smartphoneList.get(0).getProName()
                            , smartphoneList.get(0).getProPrice()
                            , smartphoneList.get(0).getProAvatar()
                            , smartphoneList.get(0).getCName()
                            , 1));


        }
        saveCartData();
    }

    private void saveCartData() {
        Gson gson = new Gson();
        String json = gson.toJson(MainActivity.shoppingCartArrayList);
        MainActivity.sharedPreferences = getSharedPreferences("shoppingCartArray", Context.MODE_PRIVATE);
        MainActivity.editor = MainActivity.sharedPreferences.edit();
        MainActivity.editor.putString("cartList", json);
        Log.d(TAG, "saveCartData: " + getSharedPreferences("shoppingCartArray", Context.MODE_PRIVATE).getString("cartList", null));
        MainActivity.editor.apply();
    }
}