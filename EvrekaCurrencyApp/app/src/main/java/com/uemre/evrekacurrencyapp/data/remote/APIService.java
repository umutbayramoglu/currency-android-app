package com.uemre.evrekacurrencyapp.data.remote;

import android.provider.SyncStateContract;

import com.uemre.evrekacurrencyapp.data.model.ResultResponse;
import com.uemre.evrekacurrencyapp.util.ApiConstants;

import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface APIService {

    @GET(ApiConstants.latest)
    Single<ResultResponse> getResponse();
}
