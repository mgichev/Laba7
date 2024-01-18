package com.example.laba3;

import com.example.laba3.ui.city_list.CityListFragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.laba3.ui.current_city.CurrentCityFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.laba3.databinding.ActivitySecondBinding;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SecondActivity extends AppCompatActivity implements RecyclerViewAdapter.OnFragmentSendDataListener{

    private ActivitySecondBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySecondBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_city_list,
                R.id.navigation_current_city,
                R.id.navigation_saved_cities)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_second);
        NavigationUI.setupWithNavController(binding.navView, navController);

    }

    @Override
    public void onSendData() {
        View view = findViewById(R.id.navigation_current_city);
        view.performClick();
    }
}