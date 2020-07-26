package dev.mxt.banhang.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import dev.mxt.banhang.R;
import dev.mxt.banhang.activity.CartActivity;
import dev.mxt.banhang.activity.PhoneInCategoryActivity;
import dev.mxt.banhang.adapter.CategoryAdapter;
import dev.mxt.banhang.api.Api;
import dev.mxt.banhang.api.RetrofitClient;
import dev.mxt.banhang.model.Category;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryFragment extends Fragment implements CategoryAdapter.OnCategoryListener {

    private View view;
    private ImageView cart;
    private RecyclerView categoryRecyclerView;
    private ArrayList<Category> categoryArrayList;
    private EditText et_search;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_category, container, false);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) getActivity().findViewById(R.id.bottom_nav);
        bottomNavigationView.getMenu().getItem(1).setChecked(true);
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
        cart = (ImageView) view.findViewById(R.id.imgCart_id);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CartActivity.class));
            }
        });
        categoryRecyclerView = (RecyclerView) view.findViewById(R.id.category_recyclerview);
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        categoryRecyclerView.setHasFixedSize(true);
        getCategoryResponse();
        return view;
    }

    private void getCategoryResponse() {
        Api api = RetrofitClient.getInstance().getApi();

        Call<List<Category>> categoriesCall = api.getCategories();

        categoriesCall.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if(!response.isSuccessful()) {
                    Log.d("mxt", "Code: " + response.code());
                    return;
                }

                showCategories(response.body());
                Toast.makeText(getActivity(), "Success", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.d("mxt", "fail: " + t.getMessage());
                Toast.makeText(getActivity(), "Faild", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showCategories(List<Category> body) {
        categoryArrayList = new ArrayList<Category>(body);
        categoryRecyclerView.setAdapter(new CategoryAdapter(categoryArrayList, getContext(), this));
    }

    @Override
    public void onCategoryClick(int position) {
        Intent intent = new Intent(getContext(), PhoneInCategoryActivity.class);
        intent.putExtra("id", categoryArrayList.get(position).getId());
        startActivity(intent);
    }
}
