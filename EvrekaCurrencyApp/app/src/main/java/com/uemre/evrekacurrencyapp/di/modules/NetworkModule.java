package com.uemre.evrekacurrencyapp.di.modules;

import com.uemre.evrekacurrencyapp.data.remote.APIService;
import com.uemre.evrekacurrencyapp.util.ApiConstants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    @Singleton
    @Provides
    static Retrofit provideRetrofitInstance() {

        return new Retrofit.Builder()
                .baseUrl(ApiConstants.baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
