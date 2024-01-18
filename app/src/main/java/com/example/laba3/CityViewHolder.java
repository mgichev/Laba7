package com.example.laba3;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CityViewHolder extends RecyclerView.ViewHolder {
    TextView cityNameTextView;
    public CityViewHolder(@NonNull View itemView) {
        super(itemView);
        cityNameTextView = itemView.findViewById(R.id.cityTitle);
    }
    public void setCityName (String name){
        cityNameTextView.setText(name);
    }
}

