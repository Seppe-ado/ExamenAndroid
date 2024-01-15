package com.example.examenandroid.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface CountriesInfoDAO {

    @Query("Select * FROM CountriesInfo")
    List<CountriesInfo> getAllCountries();
    @Query("SELECT * FROM countriesinfo WHERE name LIKE :name")
    CountriesInfo findByName(String name);
    @Delete
    void deleteCountriesInfo(CountriesInfo i);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCountriesInfo(CountriesInfo i);
    @Update
    void updateCountriesInfo(CountriesInfo i);
}
