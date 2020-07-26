package dev.mxt.banhang.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
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

public class HomeFragment extends Fragment implements PhoneAdapter.OnPhoneListener {

    private RecyclerView suggestionRecyclerView, newPhoneRecyclerView, hotPhoneRecyclerView;
    private RecyclerView.Adapter suggestionPhoneAdapter, newPhoneAdapter, hotPhoneAdapter;
    private RecyclerView.LayoutManager suggestionPhoneLayoutManager, newPhoneLayoutManager, hotPhoneLayoutManager;
    private View view;
    private EditText et_search;
    private ViewFlipper viewFlipper;
    private Animation animIn, animOut;
    private ImageView cart;
    private ArrayList<Smartphone> hotSmartphoneArrayList;
    private ArrayList<Smartphone> newSmartphoneArrayList;
    private ArrayList<Smartphone> suggestionSmartphoneArrayList;
    private static final String TAG = "HomeFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        suggestionRecyclerView = (RecyclerView) view.findViewById(R.id.suggestion_recyclerview);
        newPhoneRecyclerView = (RecyclerView) view.findViewById(R.id.new_phone_recyclerview);
        hotPhoneRecyclerView = (RecyclerView) view.findViewById(R.id.hot_phone_recyclerview);
        cart = (ImageView) view.findViewById(R.id.imgCart_id);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CartActivity.class));
            }
        });
        et_search = (EditText) view.findViewById(R.id.search_bar);
        et_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new SearchFragment())
                        .commit();
            }
        });

        setUpViewFlipper();

        getHotSmartphoneResponse();
        getSuggestionSmartphoneResponse();
        getNewSmartphoneResponse();

        BottomNavigationView bottomNavigationView = (BottomNavigationView) getActivity().findViewById(R.id.bottom_nav);
        bottomNavigationView.getMenu().getItem(0).setChecked(true);
        return view;
    }

    private void getNewSmartphoneResponse() {
        Api api = RetrofitClient.getInstance().getApi();

        Call<List<Smartphone>> newPhonesCall = api.getNewPhones();

        newPhonesCall.enqueue(new Callback<List<Smartphone>>() {
            @Override
            public void onResponse(Call<List<Smartphone>> call, Response<List<Smartphone>> response) {
                if(!response.isSuccessful()) {
                    Log.d("mxt", "Code: " + response.code());
                    return;
                }

                showSmartphone(response.body(), newPhoneAdapter, newSmartphoneArrayList, newPhoneRecyclerView, R.layout.horizontal_item);
                Toast.makeText(getContext(), "Success", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<List<Smartphone>> call, Throwable t) {
                Log.d("mxt", "fail: " + t.getMessage());
                Toast.makeText(getContext(), "Faild", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getSuggestionSmartphoneResponse() {
        Api api = RetrofitClient.getInstance().getApi();

        Call<List<Smartphone>> suggestionPhonesCall = api.getSuggestionPhones();

        suggestionPhonesCall.enqueue(new Callback<List<Smartphone>>() {
            @Override
            public void onResponse(Call<List<Smartphone>> call, Response<List<Smartphone>> response) {
                if(!response.isSuccessful()) {
                    Log.d("mxt", "Code: " + response.code());
                    return;
                }

                showSmartphone(response.body(), suggestionPhoneAdapter, suggestionSmartphoneArrayList, suggestionRecyclerView, R.layout.horizontal_item);
                Toast.makeText(getContext(), "Success", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<List<Smartphone>> call, Throwable t) {
                Log.d("mxt", "fail: " + t.getMessage());
                Toast.makeText(getContext(), "Faild", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getHotSmartphoneResponse() {
        Api api = RetrofitClient.getInstance().getApi();

        Call<List<Smartphone>> hotPhonesCall = api.getHotPhones();

        hotPhonesCall.enqueue(new Callback<List<Smartphone>>() {
            @Override
            public void onResponse(Call<List<Smartphone>> call, Response<List<Smartphone>> response) {
                if(!response.isSuccessful()) {
                    Log.d("mxt", "Code: " + response.code());
                    return;
                }

                showSmartphone(response.body(), hotPhoneAdapter, hotSmartphoneArrayList, hotPhoneRecyclerView, R.layout.hot_item);
                Toast.makeText(getContext(), "Success", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<List<Smartphone>> call, Throwable t) {
                Log.d("mxt", "fail: " + t.getMessage());
                Toast.makeText(getContext(), "Faild", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showSmartphone(List<Smartphone> body
            , RecyclerView.Adapter phoneAdapter
            , ArrayList<Smartphone> smartphoneArrayList
            , RecyclerView phoneRecyclerView
            , int item) {

        initRecyclerView();
        smartphoneArrayList = new ArrayList<>(body);
        phoneAdapter = new PhoneAdapter(smartphoneArrayList, getActivity(), this, item);
        phoneRecyclerView.setAdapter(phoneAdapter);
    }

    private void initRecyclerView() {
        suggestionRecyclerView.setHasFixedSize(true);
        suggestionPhoneLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        suggestionRecyclerView.setLayoutManager(suggestionPhoneLayoutManager);

        newPhoneRecyclerView.setHasFixedSize(true);
        newPhoneLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        newPhoneRecyclerView.setLayoutManager(newPhoneLayoutManager);

        hotPhoneRecyclerView.setHasFixedSize(true);
        hotPhoneLayoutManager = new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL, false);
        hotPhoneRecyclerView.setLayoutManager(hotPhoneLayoutManager);
    }

    private void setUpViewFlipper() {
        int[] adsImageArray = {R.drawable.iphone_x, R.drawable.xiaomi_note_10, R.drawable.oppo_f11_pro};
        viewFlipper = (ViewFlipper) view.findViewById(R.id.view_flipper);
        for (int adsImage: adsImageArray) {
            ImageView imageView = new ImageView(getContext());
            imageView.setImageResource(adsImage);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        animIn = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
        animOut = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out);
        viewFlipper.setInAnimation(animIn);
        viewFlipper.setOutAnimation(animOut);
        viewFlipper.setAutoStart(true);
        viewFlipper.setFlipInterval(2000);
    }

    @Override
    public void onPhoneClick(int position, ArrayList<Smartphone> smartphoneArrayList) {
        Intent intent = new Intent(getContext(), PhoneDetailsActivity.class);
        intent.putExtra("id", smartphoneArrayList.get(position).getId());
        Log.d(TAG, "onPhoneClick: " + smartphoneArrayList.get(position).getId() + smartphoneArrayList.get(position).getProName());
        getContext().startActivity(intent);
    }
}
