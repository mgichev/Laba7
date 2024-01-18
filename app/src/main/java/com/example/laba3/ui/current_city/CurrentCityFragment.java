package com.example.laba3.ui.current_city;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.laba3.CityData;
import com.example.laba3.R;
import com.example.laba3.SecondActivity;
import com.example.laba3.databinding.FragmentCurrentCityBinding;
import com.example.laba3.ui.city_list.CityListFragment;

public class CurrentCityFragment extends Fragment {

    private FragmentCurrentCityBinding binding;
    private static int position = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CurrentCityViewModel currentCityViewModel =
                new ViewModelProvider(this).get(CurrentCityViewModel.class);

        binding = FragmentCurrentCityBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SetTextViewData();
    }

    private void SetTextViewData() {
        TextView capitalTextView, countryTextView, populationTextView, squareTextView, languageTextView;
        String capital, country, language, population, square;

        CityData[] cityData = CityListFragment.currentCityDataArray;
        capitalTextView = getView().findViewById(R.id.capitalTextView);
        countryTextView = getView().findViewById(R.id.countryTextView);
        populationTextView = getView().findViewById(R.id.populationTextView);
        squareTextView = getView().findViewById(R.id.squareTextView);
        languageTextView = getView().findViewById(R.id.languageTextView);

        population = populationTextView.getText().toString() + "" + cityData[position].getPopulation();
        square = squareTextView.getText().toString() + "" + cityData[position].getSquare();
        country = countryTextView.getText().toString() + cityData[position].getCountry();
        capital = cityData[position].getCapital();
        language = languageTextView.getText().toString() + cityData[position].getLanguage();

        populationTextView.setText(population);
        capitalTextView.setText(capital);
        countryTextView.setText(country);
        squareTextView.setText(square);
        languageTextView.setText(language);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public static void ChangeFragment(View view, int pos) {
        SecondActivity activity = (SecondActivity) view.getContext();
        position = pos;
        activity.onSendData();
    }

}