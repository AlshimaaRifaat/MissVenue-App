package com.ibsvalleyn.missvenue.viewModels;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ibsvalleyn.missvenue.api.RetrofitClient;
import com.ibsvalleyn.missvenue.helper.AppFunctions;
import com.ibsvalleyn.missvenue.models.Product_Categories;
import com.kaopiz.kprogresshud.KProgressHUD;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductCategoriesViewModel extends ViewModel {
    private Context context;
    KProgressHUD kProgressHUD;

    public void setContext(Context context) {
        this.context = context;
    }

    private MutableLiveData<Product_Categories> productList;

    //we will call this method to get the data
    @RequiresApi(api = Build.VERSION_CODES.M)
    public LiveData<Product_Categories> getProducts(int category_id, int page, int count) {
        //if the list is null
        //     if (productList == null) {
        productList = new MutableLiveData<>();
        kProgressHUD = AppFunctions.getKProgressHUD(context);
        loadProducts(category_id, page, count);

        //  }

        //finally we will return the list
        return productList;
    }


    //This method is using Retrofit to get the JSON data from URL
    private void loadProducts(int category_id, int page, int count) {
        kProgressHUD.show();
        Call<Product_Categories> addEVEnt_call = RetrofitClient.getInstance(context)
                .getCategoriesProduct(category_id, page, count);
        addEVEnt_call.enqueue(new Callback<Product_Categories>() {

            @Override
            public void onResponse(Call<Product_Categories> call, Response<Product_Categories> response) {

                if (response.isSuccessful()) {
                    Log.e("TAG", "isSuccessful");

                    //finally we are setting the list to our MutableLiveData
                    productList.setValue(response.body());
                    kProgressHUD.dismiss();

                } else {
                    kProgressHUD.dismiss();

                }
            }

            @Override
            public void onFailure(Call<Product_Categories> call, Throwable t) {
                kProgressHUD.dismiss();

            }


        });
    }
}
