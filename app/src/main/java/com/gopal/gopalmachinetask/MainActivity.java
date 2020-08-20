package com.gopal.gopalmachinetask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.gopal.gopalmachinetask.API.ClientApi;
import com.gopal.gopalmachinetask.Model.search.Restaurant;
import com.gopal.gopalmachinetask.Model.search.RestaurantsItem;
import com.gopal.gopalmachinetask.Model.search.SearchResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RestaurantsAdapter.AdapterCallback {

    List<RestaurantsItem> restaurantList;
    RecyclerView recyclerViewRestaurant;
    LinearLayoutManager linearLayoutManager;
    RestaurantsAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        restaurantList = new ArrayList<>();
        recyclerViewRestaurant = (RecyclerView) findViewById(R.id.recyclerViewRestaurant);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerViewRestaurant.setLayoutManager(mLayoutManager);
        recyclerViewRestaurant.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerViewRestaurant.setItemAnimator(new DefaultItemAnimator());

        getCategorySearch();
    }



    public void getCategorySearch(){

        ClientApi.getService().getCategorySearch("5fcc04090fa978462f0c5ae64d8aacc3","21.170240","72.831062","Dine-out",20).enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){
                        restaurantList.addAll(response.body().getRestaurants());
                    }
                    adapter = new RestaurantsAdapter(MainActivity.this, restaurantList);
                    recyclerViewRestaurant.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                    Log.e("restaurantList", restaurantList.size() +"");
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void respond(String id) {
        Intent i = new Intent(MainActivity.this, RestaurantDetailsActivity.class);
        i.putExtra("ID", id);
        startActivity(i);
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}