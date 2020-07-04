package dev.mxt.banhang.api;

import java.util.List;

import dev.mxt.banhang.model.Smartphone;
import dev.mxt.banhang.model.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Api {

    @GET("products")
    Call<List<Smartphone>> getPhones();

    @GET("products/{id}")
    Call<List<Smartphone>> getPhoneDetail(@Path("id") Integer id);

    @FormUrlEncoded
    @POST("register")
    Call<List<User>> createUser(
            @Header("accept") String type,
            @Field("name") String name,
            @Field("email") String email,
            @Field("phone") String phone,
            @Field("address") String address,
            @Field("password") String password
    );
}
