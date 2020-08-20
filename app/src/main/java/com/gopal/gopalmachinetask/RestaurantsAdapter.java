package com.gopal.gopalmachinetask;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gopal.gopalmachinetask.Model.search.RestaurantsItem;

import java.util.List;

public class RestaurantsAdapter extends RecyclerView.Adapter<RestaurantsAdapter.MyViewHolder> {
    private Context mContext;
    private List<RestaurantsItem> restaurantsItemList;
    private AdapterCallback mAdapterCallback;

    public RestaurantsAdapter(Context mContext, List<RestaurantsItem> restaurantsItems) {
        this.mContext = mContext;
        this.restaurantsItemList = restaurantsItems;
        this.mAdapterCallback = ((AdapterCallback) mContext);

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, location;
        ImageView imageView;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            location = (TextView) view.findViewById(R.id.location);
            imageView = (ImageView) view.findViewById(R.id.thumbnail);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mAdapterCallback.respond(restaurantsItemList.get(getAdapterPosition()).getRestaurant().getId());

                }
            });

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAdapterCallback.respond(restaurantsItemList.get(getAdapterPosition()).getRestaurant().getId());
                }
            });
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restaurants_adapter_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        RestaurantsItem restaurantsItem = restaurantsItemList.get(position);
        holder.title.setText(restaurantsItem.getRestaurant().getName());
        holder.location.setText(restaurantsItem.getRestaurant().getLocation().getAddress());
        if (restaurantsItem.getRestaurant().getThumb().equalsIgnoreCase("")){
            Glide.with(mContext).load(R.drawable.ic_launcher_background).into(holder.imageView);

        }else {
            Glide.with(mContext).load(restaurantsItem.getRestaurant().getThumb()).into(holder.imageView);
        }


    }

    @Override
    public int getItemCount() {
        return restaurantsItemList.size();
    }



    public static interface AdapterCallback {
        void respond(String strID);
    }

}
