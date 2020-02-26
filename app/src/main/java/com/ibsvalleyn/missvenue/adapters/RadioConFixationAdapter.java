package com.ibsvalleyn.missvenue.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ibsvalleyn.missvenue.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class RadioConFixationAdapter<T> extends RecyclerView.Adapter<RadioConFixationAdapter.ViewHolder> {
     int mSelectedItem = -1;
     List<T> mItems;
     Context mContext;

    RadioConFixationAdapter(Context context, List<T> items) {
        mContext = context;
        mItems = items;
    }

    @Override
    public void onBindViewHolder(@NotNull RadioConFixationAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.mRadio.setChecked(i == mSelectedItem);
    }


    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        final View view = inflater.inflate(R.layout.view_item_confrimations, viewGroup, false);
        return new ViewHolder(view);
    }

  public   class ViewHolder extends RecyclerView.ViewHolder {

        RadioButton mRadio;

        TextView fullName, phone, address, editAddress;


        public ViewHolder(final View inflate) {
            super(inflate);
            mRadio = inflate.findViewById(R.id.radioButton);

            fullName = itemView.findViewById(R.id.full_name);
            phone = itemView.findViewById(R.id.phone);
            address = itemView.findViewById(R.id.address);
            editAddress = itemView.findViewById(R.id.editAddress);

            View.OnClickListener clickListener = v -> {
                mSelectedItem = getAdapterPosition();
                notifyItemRangeChanged(0, mItems.size());
            };
            mRadio.setOnClickListener(clickListener);
        }
    }
}



