package com.example.examenandroid.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.room.Database;

import com.example.examenandroid.model.CountriesData;
import com.example.examenandroid.model.CountriesInfo;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountriesViewModel extends AndroidViewModel {
    private ExecutorService executorService;
    private CountriesData countriesData;
    public CountriesViewModel(@NonNull Application application){
        super(application);
        executorService = Executors.newFixedThreadPool(2);
        countriesData= CountriesData.getInstance(application);

    }

    public List<CountriesInfo> getAllCountries(){
        return countriesData.getCountriesInfoDAO().getAllCountries();
    }

    public void addCountries(CountriesInfo country){
        executorService.execute(()->{
            countriesData.getCountriesInfoDAO().insertCountriesInfo(country);
        });
    }
    public CountriesInfo getCountry(String name){
        return countriesData.getCountriesInfoDAO().findByName(name);
    }
}
