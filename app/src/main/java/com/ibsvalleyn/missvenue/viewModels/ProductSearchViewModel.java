package com.ibsvalleyn.missvenue.viewModels;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ibsvalleyn.missvenue.api.RetrofitClient;
import com.ibsvalleyn.missvenue.helper.AppFunctions;
import com.ibsvalleyn.missvenue.models.SearchProductCategory;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductSearchViewModel extends ViewModel {


    private Context context;
    KProgressHUD kProgressHUD;

    public void setContext(Context context) {
        this.context = context;
    }

    private MutableLiveData<List<SearchProductCategory>> productSearch;

    //we will call this method to get the data
    @RequiresApi(api = Build.VERSION_CODES.M)
    public LiveData<List<SearchProductCategory>> getProductSearch(String searchWord, int page, int count) {
        //if the list is null
//        if (productSearch == null) {
        productSearch = new MutableLiveData<>();
        kProgressHUD = AppFunctions.getKProgressHUD(context);
        loadProductSearch(searchWord, page, 20);
        //  }

        //finally we will return the list
        return productSearch;

    }


    //This method is using Retrofit to get the JSON data from URL
    private void loadProductSearch(String searchWord, int page, int count) {
        kProgressHUD.show();

        Call<List<SearchProductCategory>> addEVEnt_call = RetrofitClient.getInstance(context)
                .SearchProductCategoryCall(searchWord, page, 20);

        addEVEnt_call.enqueue(new Callback<List<SearchProductCategory>>() {

            @Override
            public void onResponse(Call<List<SearchProductCategory>> call, Response<List<SearchProductCategory>> response) {

                if (response.isSuccessful()) {
                    //finally we are setting the list to our MutableLiveData
                    productSearch.setValue(response.body());
                    kProgressHUD.dismiss();
                } else {
                    kProgressHUD.dismiss();

                }
            }

            @Override
            public void onFailure(Call<List<SearchProductCategory>> call, Throwable t) {
                kProgressHUD.dismiss();

            }

        });
    }

}


