package com.example.laba3;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CityDataService {
    @GET("City2022.json")
    Call<List<CityData>> GetJson();
}

