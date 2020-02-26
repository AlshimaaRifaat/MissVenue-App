package com.ibsvalleyn.missvenue.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ibsvalleyn.missvenue.R;

import java.util.List;

public abstract class RadioAdapter<T> extends RecyclerView.Adapter<RadioAdapter.ViewHolder> {
    public int mSelectedItem = -1;
    public List<T> mItems;
    private Context mContext;

    public RadioAdapter(Context context, List<T> items) {
        mContext = context;
        mItems = items;
    }

    @Override
    public void onBindViewHolder(RadioAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.mRadio.setChecked(i == mSelectedItem);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        final View view = inflater.inflate(R.layout.view_item, viewGroup, false);
        return new ViewHolder(view);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        RadioButton mRadio;
        TextView mText;
        private ImageView image;
        RadioGroup radioGroup1;


        public ViewHolder(final View inflate) {
            super(inflate);
            mText = inflate.findViewById(R.id.text);
            radioGroup1 = inflate.findViewById(R.id.radioGroup1);
            mRadio = inflate.findViewById(R.id.radio);
            image = inflate.findViewById(R.id.image);

            View.OnClickListener clickListener = v -> {
                mSelectedItem = getAdapterPosition();
                notifyItemRangeChanged(0, mItems.size());
            };
            itemView.setOnClickListener(clickListener);
            mRadio.setOnClickListener(clickListener);
            radioGroup1.setOnClickListener(clickListener);
        }
    }
}