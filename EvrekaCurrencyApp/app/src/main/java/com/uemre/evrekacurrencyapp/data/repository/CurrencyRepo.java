package com.uemre.evrekacurrencyapp.data.repository;

import com.uemre.evrekacurrencyapp.data.model.ResultResponse;
import com.uemre.evrekacurrencyapp.data.remote.APIService;

import javax.inject.Inject;

import io.reactivex.Single;
import retrofit2.Retrofit;

public class CurrencyRepo {

    private APIService api;

    @Inject
    public CurrencyRepo(APIService api){
        this.api = api;
    }

    public Single<ResultResponse> getRates(){
        return api.getResponse();
    }
}
