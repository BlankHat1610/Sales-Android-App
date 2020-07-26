package dev.mxt.banhang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import dev.mxt.banhang.R;
import dev.mxt.banhang.adapter.PhoneAdapter;
import dev.mxt.banhang.api.Api;
import dev.mxt.banhang.api.RetrofitClient;
import dev.mxt.banhang.model.Smartphone;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhoneInCategoryActivity extends AppCompatActivity implements PhoneAdapter.OnPhoneListener {

    private Toolbar toolbar;
    private TextView tvNull;
    private RecyclerView phoneInCategoryRecyclerView;
    private ProgressBar progressBar;
    private Integer categoryId;
    private ArrayList<Smartphone> smartphoneArrayList;
    private PhoneAdapter phoneAdapter;
    private static final String TAG = "PhoneInCategoryActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // fragment_search and activity_phone_in_category have the same layout
        // so i directly use fragment_search in convenient
        setContentView(R.layout.activity_phone_in_category);
        init();
        setActionToolbar();
        getCategoryProduct();
    }

    private void init() {
        tvNull = (TextView) findViewById(R.id.null_search);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        phoneInCategoryRecyclerView = (RecyclerView) findViewById(R.id.search_phone_recyclerview);
        phoneInCategoryRecyclerView.setLayoutManager(new GridLayoutManager(PhoneInCategoryActivity.this, 2, RecyclerView.VERTICAL, false));
        phoneInCategoryRecyclerView.hasFixedSize();
    }

    private void getCategoryProduct() {
        categoryId = getIntent().getIntExtra("id", -1);
        Api api = RetrofitClient.getInstance().getApi();

        Call<List<Smartphone>> categoriesProductCall = api.getCategoryProduct(categoryId);

        categoriesProductCall.enqueue(new Callback<List<Smartphone>>() {
            @Override
            public void onResponse(Call<List<Smartphone>> call, Response<List<Smartphone>> response) {
                if(!response.isSuccessful() || response.body().isEmpty()) {
                    progressBar.setVisibility(View.GONE);
                    tvNull.setVisibility(View.VISIBLE);
                    phoneInCategoryRecyclerView.setVisibility(View.GONE);
                    Log.d("mxt", "Code: " + response.code());
                    return;
                }

                showCategoryProduct(response.body());
                Toast.makeText(PhoneInCategoryActivity.this, "Success", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<List<Smartphone>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.d("mxt", "fail: " + t.getMessage());
                Toast.makeText(PhoneInCategoryActivity.this, "Faild", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showCategoryProduct(List<Smartphone> body) {
        progressBar.setVisibility(View.GONE);
        tvNull.setVisibility(View.GONE);
        phoneInCategoryRecyclerView.setVisibility(View.VISIBLE);
        smartphoneArrayList = new ArrayList<>(body);
        phoneAdapter = new PhoneAdapter(smartphoneArrayList, PhoneInCategoryActivity.this, this, R.layout.hot_item);
        phoneInCategoryRecyclerView.setAdapter(phoneAdapter);
        phoneAdapter.notifyDataSetChanged();
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

    @Override
    public void onPhoneClick(int position, ArrayList<Smartphone> smartphoneArrayList) {
        Intent intent = new Intent(PhoneInCategoryActivity.this, PhoneDetailsActivity.class);
        intent.putExtra("id", smartphoneArrayList.get(position).getId());
        Log.d(TAG, "onPhoneClick: " + smartphoneArrayList.get(position).getId() + smartphoneArrayList.get(position).getProName());
        PhoneInCategoryActivity.this.startActivity(intent);
    }
}