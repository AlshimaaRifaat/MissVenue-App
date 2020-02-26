package com.ibsvalleyn.missvenue.viewModels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ibsvalleyn.missvenue.api.RetrofitClient;
import com.ibsvalleyn.missvenue.models.StaticsModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StaticsViewModel extends ViewModel {

    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    private MutableLiveData<StaticsModel> statics;

    //we will call this method to get the data
    public LiveData<StaticsModel> getStatics(String name) {
        //if the list is null
        if (statics == null) {
            statics = new MutableLiveData<>();
            //we will load it asynchronously from server in this method
            loadStatics(name);
        }

        //finally we will return the list
        return statics;
    }


    //This method is using Retrofit to get the JSON data from URL
    private void loadStatics(String name) {

        Call<StaticsModel> addEVEnt_call = RetrofitClient.getInstance(context)
                .staticPage(name);
        addEVEnt_call.enqueue(new Callback<StaticsModel>() {

            @Override
            public void onResponse(Call<StaticsModel> call, Response<StaticsModel> response) {

                if (response.isSuccessful()) {
                    //finally we are setting the list to our MutableLiveData
                    statics.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<StaticsModel> call, Throwable t) {

            }


        });
    }
}
