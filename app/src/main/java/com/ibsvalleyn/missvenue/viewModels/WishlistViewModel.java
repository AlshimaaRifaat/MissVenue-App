package com.ibsvalleyn.missvenue.viewModels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ibsvalleyn.missvenue.api.RetrofitClient;
import com.ibsvalleyn.missvenue.models.Wishlists;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WishlistViewModel extends ViewModel {

    private Context context;

    private MutableLiveData<Wishlists> wishList;
    //we will call this method to get the data
    public LiveData<Wishlists> getWishlist(int id , Context context) {
        //if the list is null
        if (wishList == null) {
            wishList = new MutableLiveData<>();
            //we will load it asynchronously from server in this method
            loadWishlist(id, context);
        }
        //finally we will return the list
        return wishList;
    }


    //This method is using Retrofit to get the JSON data from URL
    private void loadWishlist(int id , Context context) {

        Call<Wishlists> addEVEnt_call = RetrofitClient.getInstance(context)
                .getWishlist(id);
        addEVEnt_call.enqueue(new Callback<Wishlists>() {

            @Override
            public void onResponse(Call<Wishlists> call, Response<Wishlists> response) {

                if (response.isSuccessful()) {
                    //finally we are setting the list to our MutableLiveData
                    wishList.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Wishlists> call, Throwable t) {

            }


        });
    }
}
