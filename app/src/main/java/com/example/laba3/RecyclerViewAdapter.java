package com.example.laba3;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laba3.ui.current_city.CurrentCityFragment;

public class RecyclerViewAdapter extends RecyclerView.Adapter<CityViewHolder> {

    public interface OnFragmentSendDataListener {
        public void onSendData();
    }

    public interface OnCityClickListener {
        public void onCityClick(CityData cityData, int position);
    }

    private ListViewCity[] cities;
    private OnCityClickListener onCityClickListener;

    private OnFragmentSendDataListener fragmentSendDataListener;


    public RecyclerViewAdapter(ListViewCity[] cities) {
        this.cities = cities;
    }
    @Override
    public CityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_city_layout, parent, false);
        return new CityViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull CityViewHolder holder, int position) {
        holder.setCityName(cities[position].getCityName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentCityFragment.ChangeFragment(v, holder.getAdapterPosition());
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("capital", cities[holder.getAdapterPosition()].getCityName());
                bundle.putInt("id", cities[holder.getAdapterPosition()].getCityID());
                OpenSavedCitiesDialogFragment openSavedCitiesDialogFragment =
                        new OpenSavedCitiesDialogFragment();
                SecondActivity activity = (SecondActivity) holder.itemView.getContext();
                openSavedCitiesDialogFragment.setArguments(bundle);
                openSavedCitiesDialogFragment.show(activity.getSupportFragmentManager(), "AskAdd");
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return cities.length;
    }
}
