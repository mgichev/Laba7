package com.example.laba3;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CityData {
    @SerializedName("Population")
    @Expose
    private int population;
    @SerializedName("Country")
    @Expose
    private String country;
    @SerializedName("Name")
    @Expose
    private String capital;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("square")
    @Expose
    private int square;

    public CityData(int population, int square, String country, String capital, String language) {
        this.population = population;
        this.square = square;
        this.country = country;
        this.capital = capital;
        this.language = language;
    }

    public CityData() {}

    public String getCapital() {
        return capital;
    }

    public String getCountry() { return country; }

    public int getSquare() {
        return square;
    }

    public int getPopulation() {
        return population;
    }

    public String getLanguage() {
        return language;
    }
}

