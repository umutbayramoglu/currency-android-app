package com.uemre.evrekacurrencyapp.di.modules;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.uemre.evrekacurrencyapp.util.Preferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Provides
    @Singleton
    Preferences providePreferences(){
        List defaultCurrencies = new ArrayList<String>(Arrays.asList("USD", "TRY", "JPY"));
        return new Preferences(defaultCurrencies);
    }

}
