package dev.mxt.banhang.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import dev.mxt.banhang.R;
import dev.mxt.banhang.fragments.AccountFragment;
import dev.mxt.banhang.fragments.CategoryFragment;
import dev.mxt.banhang.fragments.HomeFragment;
import dev.mxt.banhang.fragments.SearchFragment;
import dev.mxt.banhang.model.ShoppingCart;

public class MainActivity extends AppCompatActivity{

    public static ArrayList<ShoppingCart> shoppingCartArrayList;
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavigationListener);

        loadData();

        this.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();
    }

    private boolean hasLogin() {
        MainActivity.sharedPreferences  = this.getSharedPreferences("userData", Context.MODE_PRIVATE);
        String strPhoneNumber = MainActivity.sharedPreferences.getString("phone", null);
        Log.d(TAG, "hasLogin: " + strPhoneNumber);
        if (strPhoneNumber != null) {
            return true;
        }
        return false;
    }

    private void loadData() {
        sharedPreferences = getSharedPreferences("shoppingCartArray", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String cartList = sharedPreferences.getString("cartList", null);
        Type type = new TypeToken<List<ShoppingCart>>(){}.getType();
        shoppingCartArrayList = gson.fromJson(cartList, type);
        Log.d(TAG, "loadData: " + shoppingCartArrayList);
        if (shoppingCartArrayList == null) {
            shoppingCartArrayList = new ArrayList<>();
        }
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
                break;
            case R.id.logout_item:
                MainActivity.sharedPreferences  = getSharedPreferences("userData", Context.MODE_PRIVATE);
                MainActivity.sharedPreferences.edit().clear().apply();
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.login_item:
                if (hasLogin()) {
                    Snackbar.make(findViewById(R.id.login)
                            , "You've already login!!!"
                            , BaseTransientBottomBar.LENGTH_LONG).show();
                } else startActivity(new Intent(this, LoginActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavigationListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.nav_category:
                            selectedFragment = new CategoryFragment();
                            break;
                        case R.id.nav_search:
                            selectedFragment = new SearchFragment();
                            break;
                        case R.id.nav_account:
                            selectedFragment = new AccountFragment();
                            break;
                    }

                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, selectedFragment)
                            .commit();
                    return true;
                }
            };
}
