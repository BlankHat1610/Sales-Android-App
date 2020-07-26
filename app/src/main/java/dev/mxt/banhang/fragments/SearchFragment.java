package dev.mxt.banhang.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import dev.mxt.banhang.R;
import dev.mxt.banhang.activity.CartActivity;
import dev.mxt.banhang.activity.PhoneDetailsActivity;
import dev.mxt.banhang.adapter.PhoneAdapter;
import dev.mxt.banhang.api.Api;
import dev.mxt.banhang.api.RetrofitClient;
import dev.mxt.banhang.model.Smartphone;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment implements PhoneAdapter.OnPhoneListener {

    private static final String TAG = "SearchFragment";
    private ProgressBar progressBar;
    private ArrayList<Smartphone> smartphoneArrayList;
    private RecyclerView searchRecyclerView;
    private Api api;
    private ImageView cart;
    private TextView tvNull;
    private EditText et_search;
    private RecyclerView.LayoutManager layoutManager;
    private PhoneAdapter phoneAdapter;
    private View view;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search, container, false);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) getActivity().findViewById(R.id.bottom_nav);
        bottomNavigationView.getMenu().getItem(2).setChecked(true);
        init(view);
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void init(View view) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        cart = (ImageView) view.findViewById(R.id.imgCart_id);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CartActivity.class));
            }
        });
        et_search = (EditText) view.findViewById(R.id.search_bar);
        et_search.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    fetchPhones(et_search.getText().toString().trim());
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                    Toast.makeText(getContext(), et_search.getText(), Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // TODO do something
                    fetchPhones(et_search.getText().toString().trim());
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                    Toast.makeText(getContext(), et_search.getText(), Toast.LENGTH_SHORT).show();
                    handled = true;
                }
                return handled;
            }
        });
        tvNull = (TextView) view.findViewById(R.id.null_search);

        progressBar = (ProgressBar) view.findViewById(R.id.progress);
        searchRecyclerView = (RecyclerView) view.findViewById(R.id.search_phone_recyclerview);
        layoutManager = new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL, false);
        searchRecyclerView.setLayoutManager(layoutManager);
        searchRecyclerView.hasFixedSize();
    }

    private void fetchPhones(String key) {
        api = RetrofitClient.getInstance().getApi();

        Call<List<Smartphone>> phonesCall = api.getPhoneFromSearchView(key);

        phonesCall.enqueue(new Callback<List<Smartphone>>() {
            @Override
            public void onResponse(Call<List<Smartphone>> call, Response<List<Smartphone>> response) {
                if (key == null || key.equals("") || response.body().isEmpty()) {
                    progressBar.setVisibility(View.GONE);
                    tvNull.setVisibility(View.VISIBLE);
                    searchRecyclerView.setVisibility(View.GONE);
                } else {
                    showData(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Smartphone>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.d("mxt", "fail: " + t.getMessage());
                Toast.makeText(getContext(), "Error on: " + t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showData(List<Smartphone> body) {
        progressBar.setVisibility(View.GONE);
        tvNull.setVisibility(View.GONE);
        searchRecyclerView.setVisibility(View.VISIBLE);
        smartphoneArrayList = new ArrayList<>(body);
        phoneAdapter = new PhoneAdapter(smartphoneArrayList, getContext(), this, R.layout.hot_item);
        searchRecyclerView.setAdapter(phoneAdapter);
        phoneAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPhoneClick(int position, ArrayList<Smartphone> smartphoneArrayList) {
        Intent intent = new Intent(getContext(), PhoneDetailsActivity.class);
        intent.putExtra("id", smartphoneArrayList.get(position).getId());
        Log.d(TAG, "onPhoneClick: " + smartphoneArrayList.get(position).getId() + smartphoneArrayList.get(position).getProName());
        getContext().startActivity(intent);
    }
}
