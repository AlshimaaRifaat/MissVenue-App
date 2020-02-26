package com.ibsvalleyn.missvenue.viewModels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ibsvalleyn.missvenue.api.RetrofitClient;
import com.ibsvalleyn.missvenue.models.ShoppingCarts;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MycartViewModel extends ViewModel  {


    private  Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    private MutableLiveData<ShoppingCarts> myCart;

        //we will call this method to get the data
        public LiveData<ShoppingCarts> getMyCart(int id) {
            //if the list is null
            if (myCart == null) {
                myCart = new MutableLiveData<>();
                //we will load it asynchronously from server in this method
                loadMyCart(id);
            }

            //finally we will return the list
            return myCart;
        }


        //This method is using Retrofit to get the JSON data from URL
        private void loadMyCart(int id) {

            Call<ShoppingCarts> addEVEnt_call = RetrofitClient.getInstance(context)
                    .getCart(id);
            addEVEnt_call.enqueue(new Callback<ShoppingCarts>() {

                @Override
                public void onResponse(Call<ShoppingCarts> call, Response<ShoppingCarts> response) {

                    if (response.isSuccessful()) {
                        //finally we are setting the list to our MutableLiveData
                        myCart.setValue(response.body());

                    }
                }

                @Override
                public void onFailure(Call<ShoppingCarts> call, Throwable t) {

                }


            });
        }
    }


