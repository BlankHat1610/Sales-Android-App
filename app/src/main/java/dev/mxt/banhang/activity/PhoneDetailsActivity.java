package dev.mxt.banhang.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.List;

import dev.mxt.banhang.R;
import dev.mxt.banhang.api.ApiConnect;
import dev.mxt.banhang.api.Api;
import dev.mxt.banhang.model.Smartphone;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PhoneDetailsActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "PhoneDetailsActivity";
    private Retrofit retrofit;
    private ImageView imgImageDetail;
    private TextView txPhoneName, txPrice, txReviewSum, txStarAvg;
    private HtmlTextView htmlTextView;
    private Integer phoneId;
    private Button btnAddToCart, btnBuyNow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_details);

        imgImageDetail = (ImageView) findViewById(R.id.image_details);
        txPhoneName = (TextView) findViewById(R.id.phone_name);
        txPrice = (TextView) findViewById(R.id.price);
        htmlTextView = (HtmlTextView) findViewById(R.id.html_text);
        findViewById(R.id.add_to_cart).setOnClickListener(this);
        findViewById(R.id.buy_now).setOnClickListener(this);

        if (getIntent().hasExtra("id")){

            phoneId = getIntent().getIntExtra("id", 1);
        }
        Log.d(TAG, "onCreate: phoneId: " + phoneId);

        getSmartphoneResponse();
    }

    private void checkLogin() {
    }

    private void getSmartphoneResponse() {
        retrofit = new Retrofit.Builder()
                .baseUrl(ApiConnect.getApiUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);

        Call<List<Smartphone>> phoneDetailCall = api.getPhoneDetail(phoneId);

        phoneDetailCall.enqueue(new Callback<List<Smartphone>>() {
            @Override
            public void onResponse(Call<List<Smartphone>> call, Response<List<Smartphone>> response) {
                if(!response.isSuccessful()) {
                    Log.d("mxt", "Code: " + response.code());
                    return;
                }

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
        Picasso.get().load(body.get(0).getProAvatar()).into(imgImageDetail);
        txPhoneName.setText(body.get(0).getProName());
        txPrice.setText(Integer.toString(body.get(0).getProPrice()));
        htmlTextView.setHtml(body.get(0).getProContent(), new HtmlHttpImageGetter(htmlTextView, null, true));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_to_cart:
                break;
            case R.id.buy_now:
                Intent intent = new Intent(PhoneDetailsActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
        }
    }
}