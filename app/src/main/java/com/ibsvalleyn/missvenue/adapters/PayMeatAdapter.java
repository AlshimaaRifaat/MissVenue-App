package com.ibsvalleyn.missvenue.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ibsvalleyn.missvenue.R;
import com.ibsvalleyn.missvenue.models.OrderView.PaymentMethod;

import java.util.List;

public class PayMeatAdapter extends RecyclerView.Adapter<PayMeatAdapter.NewsHolder> {

    private List<PaymentMethod> modelList;

    public static final int CHECKED = 1;
    public static final int NOT_CHECKED = 0;
    private int lastSelectedPosition = -1;

    public PayMeatAdapter(List<PaymentMethod> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    private Context context;

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_row, parent, false);
        return new NewsHolder(view);
    }

    private RadioButton lastCheckedRB = null;

    @Override
    public void onBindViewHolder(@NonNull NewsHolder holder, int i) {

        holder.radioButton.setText(modelList.get(i).getTitle());
        holder.radioButton.setOnCheckedChangeListener(ls);
        holder.radioButton.setTag(i);


    }

    private CompoundButton.OnCheckedChangeListener ls = ((buttonView, isChecked) -> {
        int tag = (int) buttonView.getTag();

        if (lastCheckedRB == null) {
            lastCheckedRB = (RadioButton) buttonView;
        } else if (tag != (int) lastCheckedRB.getTag()) {
            lastCheckedRB.setChecked(false);
            lastCheckedRB = (RadioButton) buttonView;
        }

    });

    @Override
    public int getItemCount() {
        return modelList != null ? modelList.size() : 0;

    }


    class NewsHolder extends RecyclerView.ViewHolder {

        private TextView payment;
        private RadioButton radioButton;
        private RadioGroup radioGroup1;

        NewsHolder(@NonNull View itemView) {
            super(itemView);
            radioButton = itemView.findViewById(R.id.radioButton);
            radioGroup1 = itemView.findViewById(R.id.radioGroup1);

//            payment = itemView.findViewById(R.id.payment);

        }
    }


}
