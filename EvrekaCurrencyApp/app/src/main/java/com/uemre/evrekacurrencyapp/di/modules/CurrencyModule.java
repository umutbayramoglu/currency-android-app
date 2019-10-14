package com.uemre.evrekacurrencyapp.di.modules;

import com.uemre.evrekacurrencyapp.data.remote.APIService;
import com.uemre.evrekacurrencyapp.data.repository.CurrencyRepo;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class CurrencyModule {

    @Singleton
    @Provides
    static CurrencyRepo provideCurrencyRepo(APIService api){
        return new CurrencyRepo(api);
    }

    @Provides
    @Singleton
    static APIService provideApi(Retrofit retrofit) {
        return retrofit.create(APIService.class);
    }
}
