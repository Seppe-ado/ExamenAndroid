package com.example.examenandroid;

import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class Migrations extends Migration {
    public Migrations(){
        super(1,2);
    }
    public void migrate(SupportSQLiteDatabase database){
        database.execSQL("ALTER TABLE CountriesInfo ADD COLUMN officialName VARCHAR(255)");
    }
}
