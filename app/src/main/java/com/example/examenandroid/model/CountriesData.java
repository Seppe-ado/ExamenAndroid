package com.example.examenandroid.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;

import com.example.examenandroid.Migrations;

@Database(version=2, entities ={CountriesInfo.class} )
public abstract class CountriesData extends RoomDatabase {

    private static CountriesData INSTANCE;
   /* public static CountriesData getInstance(Context context){
        if (INSTANCE==null){
            INSTANCE = Room.databaseBuilder(context,CountriesData.class,"countries.sqlite").build();

        }
        return INSTANCE;
    } */
    public abstract CountriesInfoDAO getCountriesInfoDAO();

    public static final Migration migration= new Migrations();
    public static CountriesData getInstance(Context context){
        return Room.databaseBuilder(context.getApplicationContext(),CountriesData.class,"countries.sqlite")
                .addMigrations(migration).fallbackToDestructiveMigration().build();
    }
}
