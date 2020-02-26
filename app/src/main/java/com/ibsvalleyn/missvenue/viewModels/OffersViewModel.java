package com.ibsvalleyn.missvenue.viewModels;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ibsvalleyn.missvenue.api.RetrofitClient;
import com.ibsvalleyn.missvenue.models.ProductDetails;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OffersViewModel extends ViewModel {

    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    private MutableLiveData<List<ProductDetails>> offers;

    //we will call this method to get the data
    public LiveData<List<ProductDetails>> getOffers() {
        //if the list is null
        if (offers == null) {
            offers = new MutableLiveData<>();
            //we will load it asynchronously from server in this method
            loadOffers();
        }

        //finally we will return the list
        return offers;
    }


    //This method is using Retrofit to get the JSON data from URL
    private void loadOffers() {

        Call<List<ProductDetails>> addEVEnt_call = RetrofitClient.getInstance(context)
                .getOffers();
        addEVEnt_call.enqueue(new Callback<List<ProductDetails>>() {

            @Override
            public void onResponse(Call<List<ProductDetails>> call, Response<List<ProductDetails>> response) {

                if (response.isSuccessful()) {
                    Log.e("TAG", "isSuccessful");

                    offers.setValue(response.body());

                } else {
                    Log.e("TAG", "notSuccessful");
                }
            }

            @Override
            public void onFailure(Call<List<ProductDetails>> call, Throwable t) {
                Log.e("TAG ", "onFailure");
            }
        });
    }
}
