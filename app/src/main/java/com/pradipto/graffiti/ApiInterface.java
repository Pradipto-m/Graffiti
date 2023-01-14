package com.pradipto.graffiti;

import static com.pradipto.graffiti.RequestManager.API_KEY;

import com.pradipto.graffiti.models.ApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ApiInterface {

    String BASE_URL = "https://api.pexels.com/v1/";

    @Headers("Authorization: "+ API_KEY)
    @GET("curated/")
    Call<ApiResponse> getPhotos(
            @Query("page") int page,
            @Query("per_page") int per_page
    );

    @Headers("Authorization: "+ API_KEY)
    @GET("search")
    Call<ApiResponse> getSearchPhotos(
            @Query("query") String query,
            @Query("page") int page,
            @Query("per_page") int per_page
    );
}
