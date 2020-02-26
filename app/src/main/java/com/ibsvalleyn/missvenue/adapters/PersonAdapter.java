package com.ibsvalleyn.missvenue.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.ibsvalleyn.missvenue.activities.CheckOutActivity;
import com.ibsvalleyn.missvenue.models.OrderView.PaymentMethod;

import java.util.List;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class PersonAdapter extends RadioAdapter<PaymentMethod> {
    CheckOutActivity context;
    String shppingId, billingId;
    SharedPreferences dataSaver;

//String methodToPay;

//    public String getMethodToPay() {
//        return methodToPay;
//    }

    public PersonAdapter(Context context, List<PaymentMethod> items, String shppingId, String billingId) {
        super(context, items);
        this.context = (CheckOutActivity) context;

        this.billingId = billingId;
        this.shppingId = shppingId;
    }

    @Override
    public void onBindViewHolder(RadioAdapter.ViewHolder viewHolder, @SuppressLint("RecyclerView") final int i) {
        super.onBindViewHolder(viewHolder, i);
        viewHolder.mText.setText(mItems.get(i).getTitle());


//        viewHolder.mRadio.setBackgroundColor(BLACK);
        viewHolder.radioGroup1.setOnClickListener(view -> {
//
        });

        viewHolder.mRadio.setOnCheckedChangeListener((check, isChecked) -> {


            if (check.isChecked()) {
                dataSaver = getDefaultSharedPreferences(context);

                //     Toast.makeText(context, String.valueOf(profile.get(position).getAddressId()), Toast.LENGTH_SHORT).show();
                dataSaver.edit()
                        .putString("PayMethod", String.valueOf(mItems.get(i).getTitle()))
                        .apply();
                Log.e("PayMethod_id", "PayMethod_id " + mItems.get(i).getId());
                dataSaver.edit()
                        .putInt("PayMethod_id", mItems.get(i).getId())
                        .apply();
            }


        });
//        viewHolder.radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, int i) {
//
//                dataSaver = getDefaultSharedPreferences(context);
//
//                //     Toast.makeText(context, String.valueOf(profile.get(position).getAddressId()), Toast.LENGTH_SHORT).show();
//                dataSaver.edit()
//                        .putString("PayMethod", String.valueOf(mItems.get(i).getTitle()))
//                        .apply();
//                Log.e("PayMethod_id", "PayMethod_id " + mItems.get(i).getId());
//                dataSaver.edit()
//                        .putInt("PayMethod_id", mItems.get(i).getId())
//                        .apply();
//            }
//        });


    }
}