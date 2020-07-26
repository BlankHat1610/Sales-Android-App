package dev.mxt.banhang.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import dev.mxt.banhang.R;
import dev.mxt.banhang.activity.CartActivity;
import dev.mxt.banhang.activity.LoginActivity;
import dev.mxt.banhang.activity.MainActivity;
import dev.mxt.banhang.util.CircleTransform;

public class AccountFragment extends Fragment implements View.OnClickListener{

    private View view;
    private ImageView imgProfileAvatar;
    private LinearLayout profileLinearLayout;
    private TextView tvSearch;
    private TextView tvProduct;
    private TextView tvCategory;
    private TextView tvProfileName;
    private TextView tvProfileEmail;
    private TextView tvProfilePhoneNumber;
    private static final String TAG = "AccountFragment";
    private TextView tvProfileAddress;
    private Toolbar toolbarAccount;
    private ImageView imgLogout;
    private ImageView imgLogin;
    private ImageView cart;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MainActivity.sharedPreferences = Objects.requireNonNull(getActivity()).getSharedPreferences("userData", Context.MODE_PRIVATE);
        view = inflater.inflate(R.layout.fragment_account, container, false);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) getActivity().findViewById(R.id.bottom_nav);
        bottomNavigationView.getMenu().getItem(3).setChecked(true);
        init(view);
        return view;
    }

    private void init(View v) {
        imgProfileAvatar = (ImageView) v.findViewById(R.id.profile_avatar);
        imgLogout = (ImageView) v.findViewById(R.id.img_logout);
        imgLogin = (ImageView) v.findViewById(R.id.img_login);
        cart = (ImageView) v.findViewById(R.id.img_cart);
        toolbarAccount = (Toolbar) v.findViewById(R.id.toolbar_account);
        toolbarAccount.bringToFront();
        if (hasLogin()) {
            imgLogin.setVisibility(View.GONE);
            imgLogout.setVisibility(View.VISIBLE);
        } else if (!hasLogin()) {
            imgLogout.setVisibility(View.GONE);
            imgLogin.setVisibility(View.VISIBLE);
        }

        imgLogin.setOnClickListener(this);
        imgLogout.setOnClickListener(this);
        cart.setOnClickListener(this);

        if (MainActivity.sharedPreferences.getString("phone", null) != null) {
            Picasso.get().load(R.drawable.avartar_iu).transform(new CircleTransform()).into(imgProfileAvatar);
        } else Picasso.get().load(R.drawable.blank_profile_picture).transform(new CircleTransform()).into(imgProfileAvatar);

        tvProduct = (TextView) v.findViewById(R.id.product);
        tvCategory = (TextView) v.findViewById(R.id.category);
        tvSearch = (TextView) v.findViewById(R.id.search);

        tvSearch.setOnClickListener(this);
        tvCategory.setOnClickListener(this);
        tvProduct.setOnClickListener(this);

        tvProfileName = (TextView) v.findViewById(R.id.profile_name);
        tvProfileName.setText(MainActivity.sharedPreferences.getString("name", null));
        tvProfileEmail = (TextView) v.findViewById(R.id.profile_email);
        tvProfileEmail.setText(MainActivity.sharedPreferences.getString("email", null));
        tvProfilePhoneNumber = (TextView) v.findViewById(R.id.profile_phone_number);
        tvProfilePhoneNumber.setText(MainActivity.sharedPreferences.getString("phone", null));
        tvProfileAddress = (TextView) v.findViewById(R.id.profile_address);
        tvProfileAddress.setText(MainActivity.sharedPreferences.getString("address", null));

        imgProfileAvatar.bringToFront();
        profileLinearLayout = (LinearLayout) v.findViewById(R.id.profile_info_layout);
        profileLinearLayout.bringToFront();
    }

    private boolean hasLogin() {
        MainActivity.sharedPreferences  = getActivity().getSharedPreferences("userData", Context.MODE_PRIVATE);
        String strPhoneNumber = MainActivity.sharedPreferences.getString("phone", null);
        Log.d(TAG, "hasLogin: " + strPhoneNumber);
        if (strPhoneNumber != null) {
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_logout:
                MainActivity.sharedPreferences.edit().clear().apply();
                startActivity(new Intent(getActivity(), MainActivity.class));
                break;
            case R.id.img_login:
                if (hasLogin()) {
                    Snackbar.make(v.findViewById(R.id.img_login)
                            , "You've already login!!!"
                            , BaseTransientBottomBar.LENGTH_LONG).show();
                } else startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case R.id.img_cart:
                startActivity(new Intent(getActivity(), CartActivity.class));
                break;
            case R.id.product:
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new HomeFragment())
                        .commit();
                break;
            case R.id.category:
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new CategoryFragment())
                        .commit();
                break;
            case R.id.search:
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new SearchFragment())
                        .commit();
                break;
            default:break;
        }
    }
}
