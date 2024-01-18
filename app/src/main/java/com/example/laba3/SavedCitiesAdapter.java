package com.example.laba3;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laba3.ui.city_list.CityListFragment;
import com.example.laba3.ui.current_city.CurrentCityFragment;

import java.util.ArrayList;

public class SavedCitiesAdapter extends RecyclerView.Adapter<CityViewHolder> {

    public interface OnFragmentSendDataListener {
        public void onSendData();
    }

    public interface OnCityClickListener {
        public void onCityClick(CityData cityData, int position);
    }

    private ArrayList<CityPair> list;
    private OnCityClickListener onCityClickListener;

    private OnFragmentSendDataListener fragmentSendDataListener;


    public SavedCitiesAdapter(ArrayList<CityPair> citiesList) {
        this.list = citiesList;
    }
    @Override
    public CityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_city_layout, parent, false);
        return new CityViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull CityViewHolder holder, int position) {
        holder.setCityName(list.get(position).name);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = 0;
                String city = list.get(holder.getAdapterPosition()).name;
                CityData[] cityData = CityListFragment.currentCityDataArray;
                for (int i = 0; i < cityData.length; i++) {
                    if (cityData[i].getCapital().equals(city)) {
                        pos = i;
                    }
                }
                CurrentCityFragment.ChangeFragment(v, pos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
