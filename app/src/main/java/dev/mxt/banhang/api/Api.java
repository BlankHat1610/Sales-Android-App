package dev.mxt.banhang.api;

import java.util.List;

import dev.mxt.banhang.model.Category;
import dev.mxt.banhang.model.Smartphone;
import dev.mxt.banhang.model.Transaction;
import dev.mxt.banhang.model.User;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    @GET("products")
    Call<List<Smartphone>> getPhones();

    @GET("products/new")
    Call<List<Smartphone>> getNewPhones();

    @GET("products/suggestion")
    Call<List<Smartphone>> getSuggestionPhones();

    @GET("products/hot")
    Call<List<Smartphone>> getHotPhones();

    @GET("products/{id}")
    Call<List<Smartphone>> getPhoneDetail(@Path("id") Integer id);

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("register")
    Call<User> createUser(
            @Field("name") String name,
            @Field("email") String email,
            @Field("phone") String phone,
            @Field("address") String address,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("login")
    Call<User> login(
            @Field("phone") String phone,
            @Field("password") String password
    );

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("transaction")
    Call<Transaction> createOrder(
            @Field("transaction_user_id") Integer userId,
            @Field("transaction_total") Integer transactionTotal,
            @Field("transaction_note") String transactionNote,
            @Field("transaction_address") String transactionAddress,
            @Field("transaction_phone") String transactionPhone,
            @Field("cart_item") String cartItemJson
    );

    @GET("products/search")
    Call<List<Smartphone>> getPhoneFromSearchView(
            @Query("q") String searchText
    );

    @GET("category")
    Call<List<Category>> getCategories();

    @GET("category/{id}")
    Call<List<Smartphone>> getCategoryProduct(@Path("id") Integer id);
}
