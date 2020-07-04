package dev.mxt.banhang.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import dev.mxt.banhang.api.ApiConnect;
import dev.mxt.banhang.api.Api;
import dev.mxt.banhang.activity.PhoneDetailsActivity;
import dev.mxt.banhang.R;
import dev.mxt.banhang.model.Smartphone;
import dev.mxt.banhang.adapter.PhoneAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment implements PhoneAdapter.OnPhoneListener {

    private RecyclerView suggestionRecyclerView, newPhoneRecyclerView, hotPhoneRecyclerView;
    private RecyclerView.Adapter suggestionAdapter, newPhoneAdapter, hotPhoneAdapter;
    private RecyclerView.LayoutManager suggestionLayoutManager, newPhoneLayoutManager, hotPhoneLayoutManager;
    private View view;
    private ViewFlipper viewFlipper;
    private Animation animIn, animOut;
    private ArrayList<Smartphone> smartphoneArrayList;
    private Retrofit retrofit;
    private static final String TAG = "HomeFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        suggestionRecyclerView = (RecyclerView) view.findViewById(R.id.suggestion_recyclerview);
        newPhoneRecyclerView = (RecyclerView) view.findViewById(R.id.new_phone_recyclerview);
        hotPhoneRecyclerView = (RecyclerView) view.findViewById(R.id.hot_phone_recyclerview);

        getSmartphoneResponse();

        setUpViewFlipper();
        return view;
    }

    private void getSmartphoneResponse() {
        retrofit = new Retrofit.Builder()
                .baseUrl(ApiConnect.getApiUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);

        Call<List<Smartphone>> phonesCall = api.getPhones();

        phonesCall.enqueue(new Callback<List<Smartphone>>() {
            @Override
            public void onResponse(Call<List<Smartphone>> call, Response<List<Smartphone>> response) {
                if(!response.isSuccessful()) {
                    Log.d("mxt", "Code: " + response.code());
                    return;
                }

                showSmartphone(response.body());
                Toast.makeText(getActivity().getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<List<Smartphone>> call, Throwable t) {
                Log.d("mxt", "fail: " + t.getMessage());
                Toast.makeText(getActivity().getApplicationContext(), "Faild", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showSmartphone(List<Smartphone> body) {
        initRecyclerView();
        smartphoneArrayList = new ArrayList<>(body);

        suggestionAdapter = new PhoneAdapter(smartphoneArrayList, getActivity().getApplicationContext(), this, R.layout.horizontal_item);
        suggestionRecyclerView.setAdapter(suggestionAdapter);

        newPhoneAdapter = new PhoneAdapter(smartphoneArrayList, getActivity().getApplicationContext(), this, R.layout.horizontal_item);
        newPhoneRecyclerView.setAdapter(newPhoneAdapter);

        hotPhoneAdapter = new PhoneAdapter(smartphoneArrayList, getActivity().getApplicationContext(), this, R.layout.hot_item);
        hotPhoneRecyclerView.setAdapter(hotPhoneAdapter);

        Log.d("mxt", "Body: " + smartphoneArrayList.get(0).getProAvatar());
    }

    private void initRecyclerView() {
        suggestionRecyclerView.setHasFixedSize(true);
        suggestionLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), RecyclerView.HORIZONTAL, false);
        suggestionRecyclerView.setLayoutManager(suggestionLayoutManager);

        newPhoneRecyclerView.setHasFixedSize(true);
        newPhoneLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), RecyclerView.HORIZONTAL, false);
        newPhoneRecyclerView.setLayoutManager(newPhoneLayoutManager);

        hotPhoneRecyclerView.setHasFixedSize(true);
        hotPhoneLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 2, RecyclerView.VERTICAL, false);
        hotPhoneRecyclerView.setLayoutManager(hotPhoneLayoutManager);
    }

    private void setUpViewFlipper() {
        int[] adsImageArray = {R.drawable.iphone_x, R.drawable.xiaomi_note_10, R.drawable.oppo_f11_pro};
        viewFlipper = (ViewFlipper) view.findViewById(R.id.view_flipper);
        for (int adsImage: adsImageArray) {
            ImageView imageView = new ImageView(getActivity().getApplicationContext());
            imageView.setImageResource(adsImage);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        animIn = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fade_in);
        animOut = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fade_out);
        viewFlipper.setInAnimation(animIn);
        viewFlipper.setOutAnimation(animOut);
        viewFlipper.setAutoStart(true);
        viewFlipper.setFlipInterval(2000);
    }

    @Override
    public void onPhoneClick(int position) {
        Intent intent = new Intent(getActivity().getApplicationContext(), PhoneDetailsActivity.class);
        intent.putExtra("id", smartphoneArrayList.get(position).getId());
        Log.d(TAG, "onPhoneClick: " + smartphoneArrayList.get(position).getId() + smartphoneArrayList.get(position).getProName());
        getActivity().getApplicationContext().startActivity(intent);
    }
}
