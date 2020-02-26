package com.ibsvalleyn.missvenue.viewModels;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.ibsvalleyn.missvenue.api.RetrofitClient;
import com.ibsvalleyn.missvenue.helper.AppFunctions;
import com.ibsvalleyn.missvenue.models.HomeModel;
import com.kaopiz.kprogresshud.KProgressHUD;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductSectorsViewModel extends ViewModel {

    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    private MutableLiveData<HomeModel> productList;

    KProgressHUD kProgressHUD;

    //we will call this method to get the data
    @RequiresApi(api = Build.VERSION_CODES.M)
    public LiveData<HomeModel> getProducts(int count, int i, int page, int count1) {

        //if the list is null
//        if (productList == null) {
        productList = new MutableLiveData<>();
        kProgressHUD = AppFunctions.getKProgressHUD(context);

        //we will load it asynchronously from server in this method
        loadProducts(count, i, page, count1);
        //    }

        //finally we will return the list
        return productList;
    }


    //This method is using Retrofit to get the JSON data from URL
    private void loadProducts(int count, int i, int page, int count1) {

        kProgressHUD.show();
        // progressBar.setVisibility(View.VISIBLE);
        Call<HomeModel> addEVEnt_call = RetrofitClient.getInstance(context)
                .getCategories(count, i, page, count1);
        addEVEnt_call.enqueue(new Callback<HomeModel>() {

            @Override
            public void onResponse(Call<HomeModel> call, Response<HomeModel> response) {
                kProgressHUD.dismiss();
                if (response.isSuccessful()) {
                    //finally we are setting the list to our MutableLiveData
                    if (response.body().getProducts() != null) {
                        productList.setValue(response.body());

                    }
                    //    progressBar.setVisibility(View.GONE);

                } else {
                    //   progressBar.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<HomeModel> call, Throwable t) {
                // progressBar.setVisibility(View.GONE);
                kProgressHUD.dismiss();
            }


        });
    }
}
