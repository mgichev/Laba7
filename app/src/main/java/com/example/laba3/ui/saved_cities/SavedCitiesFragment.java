package com.example.laba3.ui.saved_cities;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laba3.DatabaseHelper;
import com.example.laba3.ListViewCity;
import com.example.laba3.R;
import com.example.laba3.RecyclerViewAdapter;
import com.example.laba3.SavedCitiesAdapter;
import com.example.laba3.User;
import com.example.laba3.databinding.FragmentSavedCitiesBinding;
import com.example.laba3.CityPair;

import java.util.ArrayList;

public class SavedCitiesFragment extends Fragment {

    private ArrayList<CityPair> cityList = new ArrayList<CityPair>();

    private FragmentSavedCitiesBinding binding;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getReadableDatabase();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        db.close();
    }

    private void fillCityListFromDB() {
        int currentUserID = User.getCurrentUserID();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_SAVED_CITIES, null);
        while (cursor.moveToNext()) {
            int id, userID;
            String city;
            id = cursor.getInt(0);
            city = cursor.getString(1);
            userID = cursor.getInt(2);
            Log.d("SavedCity", "ID: " + id + " Capital: " + city +
                    " UserID: " + userID);
            if (userID == currentUserID)
                cityList.add(new CityPair(id, city));
        }

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SavedCitiesViewModel savedCitiesViewModel =
                new ViewModelProvider(this).get(SavedCitiesViewModel.class);

        binding = FragmentSavedCitiesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fillCityListFromDB();
        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.recyclerViewSavedCities);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(new SavedCitiesAdapter(cityList));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public ListViewCity[] createElements(int count) {
        ListViewCity[] result = new ListViewCity[count];
        int index = 0;
        for (CityPair el : cityList) {
            result[index] = new ListViewCity() {
                String city = el.name;
                @Override
                public String getCityName() {
                    return city;
                }

                @Override
                public int getCityID() {
                    return el.id;
                }
            };
            index++;
        }
        return result;
    }
}