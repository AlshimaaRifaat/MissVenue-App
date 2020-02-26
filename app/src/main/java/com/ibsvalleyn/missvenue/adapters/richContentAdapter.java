package com.ibsvalleyn.missvenue.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.ibsvalleyn.missvenue.R;
import com.ibsvalleyn.missvenue.activities.CheckOutActivity;
import com.ibsvalleyn.missvenue.models.OrderView.RichContent;

import java.util.List;


public class richContentAdapter extends RecyclerView.Adapter<richContentAdapter.NewsHolder> {

    private List<RichContent> modelList;



    public richContentAdapter(List<RichContent> modelList, CheckOutActivity context) {
        this.modelList = modelList;
        this.context = context;
    }

    CheckOutActivity context;


    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rich_content_row, parent, false);

        return new NewsHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull NewsHolder holder, int i) {

        holder.SystemName.setText(modelList.get(i).getSystemName());
        holder.Title.setText(modelList.get(i).getTitle());
        Log.i("ASdsadsa", "onBindViewHolder: "+new Gson().toJson(modelList));
        //stringToWebView( holder.webView, modelList.get(i).getBody(), context);

    }

    @Override
    public int getItemCount() {
        return modelList != null ? modelList.size() : 0;

    }


    class NewsHolder extends RecyclerView.ViewHolder {

        private TextView SystemName;
        private TextView Title;
        private WebView webView;

        NewsHolder(@NonNull View itemView) {
            super(itemView);

            SystemName = itemView.findViewById(R.id.SystemName);
            Title = itemView.findViewById(R.id.Title);
            webView = itemView.findViewById(R.id.webView);

        }}


}
