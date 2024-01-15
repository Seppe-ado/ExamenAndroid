package com.example.examenandroid.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class CountriesInfo implements Serializable {
    @PrimaryKey
    @NonNull
    private String name;
    private String officialName;
    private String currencies;
    private String capital;
    private String region;
    private String  languages;
    private String landlocked;
    private String maps;
    private int population;
    private String timezone;

    public CountriesInfo(String name,String officialName, String currencies, String capital, String region, String languages, String landlocked, String maps, int population, String timezone) {
        this.name = name;
        this.officialName=officialName;
        this.currencies = currencies;
        this.capital = capital;
        this.region = region;
        this.languages = languages;
        this.landlocked = landlocked;
        this.maps = maps;
        this.population = population;
        this.timezone = timezone;
    }

    public String getLandlocked() {
        return landlocked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrencies() {
        return currencies;
    }

    public void setCurrencies(String currencies) {
        this.currencies = currencies;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String isLandlocked() {
        return landlocked;
    }

    public void setLandlocked(String landlocked) {
        this.landlocked = landlocked;
    }

    public String getMaps() {
        return maps;
    }

    public void setMaps(String googlemaps) {
        this.maps = googlemaps;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public String getOfficialName() {
        return officialName;
    }

    public void setOfficialName(String officialName) {
        this.officialName = officialName;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }



    @Override
    public String toString() {
        return  name + "*" +
                 currencies + "*" +
                 capital + "*" +
                 region + "*" +
                  languages + "*" +
                  landlocked +"*"+
                maps + "*"+
                population +"*"+
                timezone ;
    }
}
