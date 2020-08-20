package com.gopal.gopalmachinetask.API;

import com.gopal.gopalmachinetask.Model.Restaurants.RestaurantResponse;
import com.gopal.gopalmachinetask.Model.search.SearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface Api {

    @GET("/api/v2.1/search")
    Call<SearchResponse> getCategorySearch(@Header ("user-key") String user_key, @Query("lat") String latitude,
                                           @Query("lon") String longitude,
                                           @Query("category") String category,
                                           @Query("count") int count);

    @GET("/api/v2.1/restaurant")
    Call<RestaurantResponse> getRestaurantDetails(@Header ("user-key") String user_key, @Query("res_id") String restaurantId);


}
