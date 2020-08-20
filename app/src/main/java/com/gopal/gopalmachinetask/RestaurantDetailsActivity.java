package com.gopal.gopalmachinetask;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.gopal.gopalmachinetask.API.ClientApi;
import com.gopal.gopalmachinetask.Model.Restaurants.RestaurantResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantDetailsActivity extends AppCompatActivity {


    private String strID = "";
    ImageView iv_imageView;
    TextView tv_name, tv_location, tv_timings, tv_cuisines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_details);

        iv_imageView = (ImageView) findViewById(R.id.iv_imageView);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_location = (TextView) findViewById(R.id.tv_location);
        tv_timings = (TextView) findViewById(R.id.tv_timings);
        tv_cuisines = (TextView) findViewById(R.id.tv_cuisines);
        Intent intent = getIntent();

        strID = intent.getStringExtra("ID");

        getRestaurantDetails();

    }

    public void getRestaurantDetails(){
        ClientApi.getService().getRestaurantDetails("5fcc04090fa978462f0c5ae64d8aacc3",strID).enqueue(new Callback<RestaurantResponse>() {
            @Override
            public void onResponse(Call<RestaurantResponse> call, Response<RestaurantResponse> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){

                        Glide.with(RestaurantDetailsActivity.this).load(response.body().getThumb()).into(iv_imageView);

                        tv_name.setText(response.body().getName());
                        tv_location.setText(response.body().getLocation().getAddress());
                        tv_timings.setText(response.body().getTimings());
                        tv_cuisines.setText(response.body().getCuisines());
                    }
                }
            }

            @Override
            public void onFailure(Call<RestaurantResponse> call, Throwable t) {

            }
        });
    }
}
