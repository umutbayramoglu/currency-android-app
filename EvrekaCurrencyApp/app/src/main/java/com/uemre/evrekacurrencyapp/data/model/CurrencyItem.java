package com.uemre.evrekacurrencyapp.data.model;

import android.graphics.drawable.Drawable;

public class CurrencyItem {


    private String curName;
    private String rate;
    private Drawable flag_resource;

    public CurrencyItem(String curName, String rate, Drawable flag_resource) {
        this.curName = curName;
        this.rate = rate;
        this.flag_resource = flag_resource;
    }

    public CurrencyItem(String curName, Drawable flag_resource) {
        this.curName = curName;
        this.flag_resource = flag_resource;
    }



    public String getCurName() {
        return curName;
    }

    public String getRate() {
        return rate;
    }

    public Drawable getFlag_resource() {
        return flag_resource;
    }

    public void setCurName(String curName) {
        this.curName = curName;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public void setFlag_resource(Drawable flag_resource) {
        this.flag_resource = flag_resource;
    }
}
