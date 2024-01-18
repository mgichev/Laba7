package com.example.laba3.ui.city_list;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
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

import com.example.laba3.CityData;
import com.example.laba3.CityDataService;
import com.example.laba3.DatabaseHelper;
import com.example.laba3.ListViewCity;
import com.example.laba3.R;
import com.example.laba3.RecyclerViewAdapter;
import com.example.laba3.databinding.FragmentCityListBinding;
import com.example.laba3.CityPair;

import java.util.ArrayList;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CityListFragment extends Fragment {

    private FragmentCityListBinding binding;
    final private int maxTimeLimit = 25;
    private ArrayList<CityData> cityDataListFromJSON;
    public static CityData[] currentCityDataArray;
    private ArrayList<CityPair> cityList = new ArrayList<CityPair>();
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private Cursor cursor;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        openDatabase(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        closeDatabaseObjects();
    }

    private void openDatabase(@NonNull Context context) {
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getReadableDatabase();
    }

    private void closeDatabaseObjects() {
        database.close();
        cursor.close();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CityListViewModel cityListViewModel =
                new ViewModelProvider(this).get(CityListViewModel.class);

        binding = FragmentCityListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences prefs = requireActivity().getSharedPreferences("PrefName", Context.MODE_PRIVATE);
        boolean isDownloadedCities = prefs.getBoolean("isLoadedCities", false);
        Handler handler = new Handler();
        if (!isDownloadedCities) {
            Thread cityLoadingThread = new Thread(() -> {
                downloadCities(prefs);
                handler.post(() -> {
                    createRecyclerView(view);
                });
            });
            cityLoadingThread.start();
        }
        else {
            createRecyclerView(view);
        }
    }

    private void createRecyclerView(View view) {
        loadDataFromDB();
        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(new RecyclerViewAdapter(createElements(cityList.size())));
    }

    private void loadDataFromDB() {
        cursor = database.rawQuery("SELECT * from " + DatabaseHelper.TABLE_CITIES,
                null);
        cityList.clear();
        if (cursor.getCount() != 0) {
            currentCityDataArray = new CityData[cursor.getCount()];
            int i = 0;
            while(cursor.moveToNext()) {
                String capital, country, language;
                int population, square;
                capital = cursor.getString(1);
                country = cursor.getString(2);
                population = cursor.getInt(3);
                square = cursor.getInt(4);
                language = cursor.getString(5);
                currentCityDataArray[i] = new CityData(population, square, country, capital, language);
                cityList.add(new CityPair(cursor.getInt(0), capital));
                i++;
            }
        }
        else {
            cityList.add(new CityPair(0, "No Value"));
        }
    }

    private void downloadCities(SharedPreferences prefs) {
        SharedPreferences.Editor editor =  prefs.edit();
        editor.putBoolean("isLoadedCities", true).apply();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com/Lpirskaya/JsonLab/master/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CityDataService service = retrofit.create(CityDataService.class);
        try {
            cityDataListFromJSON = (ArrayList<CityData>) service.GetJson().execute().body();
            for (CityData cd : cityDataListFromJSON) {
                String capital, country, language;
                int population, square;
                capital = cd.getCapital();
                country = cd.getCountry();
                language = cd.getLanguage();
                population = cd.getPopulation();
                square = cd.getSquare();

                ContentValues cv = new ContentValues();
                cv.put(DatabaseHelper.COLUMN_CAPITAL, capital);
                cv.put(DatabaseHelper.COLUMN_COUNTRY, country);
                cv.put(DatabaseHelper.COLUMN_LANGUAGE, language);
                cv.put(DatabaseHelper.COLUMN_POPULATION, population);
                cv.put(DatabaseHelper.COLUMN_SQUARE, square);
                database.insert(DatabaseHelper.TABLE_CITIES, null, cv);
            }
        } catch (Exception e) {
            editor.putBoolean("isLoadedCities", false).apply();
            throw new RuntimeException(e);
        }
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

    private void fillCityDataArray() {
        if (!cityDataListFromJSON.isEmpty()) {
            currentCityDataArray = new CityData[cityDataListFromJSON.size()];
            int index = 0;
            for (CityData el : cityDataListFromJSON) {
                currentCityDataArray[index] = new CityData(el.getPopulation(), el.getSquare(), el.getCountry(), el.getCapital(), el.getLanguage());
                index++;
            }
        }
    }

    private void outputLog() {
        String population, country, capital, language, square, logString;
        int id = 1;
        for (CityData el : cityDataListFromJSON) {
            population = String.valueOf(el.getPopulation());
            country = el.getCountry();
            capital = el.getCapital();
            language = el.getLanguage();
            square = String.valueOf(el.getSquare());
            logString = String.valueOf(id) + ": " + "Country: " + country
                    + " Capital: " + capital + " Population: " + population
                    + " Language: " + language + " Square: " + square;
            Log.d(" CityDataLog", logString);
            id++;
        }
    }
}