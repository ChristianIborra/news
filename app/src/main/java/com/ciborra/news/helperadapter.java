package com.ciborra.news;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class helperadapter extends RecyclerView.Adapter {

    List<data> datalist;

    public helperadapter(List<data> datalist) {
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemdiarios,parent,false);
        ViewHolderClass viewHolderClass= new ViewHolderClass(view);
        return viewHolderClass;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolderClass viewHolderClass =(ViewHolderClass)holder;
        data Data = datalist.get(position);
        viewHolderClass.name.setText(Data.getName());
        viewHolderClass.url.setText(Data.getUrl());
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }
    public class ViewHolderClass extends RecyclerView.ViewHolder{
        TextView name,url;
        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            url = itemView.findViewById(R.id.url);
        }
    }
}
