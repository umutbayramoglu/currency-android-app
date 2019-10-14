package com.uemre.evrekacurrencyapp.data.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ResultResponse extends BaseObservable {

    @SerializedName("base")
    @Expose
    private String base;

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("timestamp")
    @Expose
    private String timestamp;


    @SerializedName("rates")
    @Expose
    private Map<String, String> rates;

    @Bindable
    public String getBase() {
        return base;
    }

    @Bindable
    public Map<String, String> getRates() {
        return rates;
    }

    public ArrayList<String> getCountryList(){
        ArrayList<String> countryList = new ArrayList(Arrays.asList(rates.keySet().toArray()));
        return countryList;
    }

    public String getDate() {
        return date;
    }

    public long getTimeStamp() {
        long time = Long.parseLong(timestamp);
        return time;
    }
}
