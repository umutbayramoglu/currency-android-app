package com.uemre.evrekacurrencyapp.ui.viewmodels;

import androidx.lifecycle.MutableLiveData;

import com.uemre.evrekacurrencyapp.data.model.ResultResponse;
import com.uemre.evrekacurrencyapp.data.repository.CurrencyRepo;
import com.uemre.evrekacurrencyapp.ui.base.BaseViewModel;

import java.util.ArrayList;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class CurrencyRateViewModel extends BaseViewModel {

    private static final String TAG = "CurrencyViewModel_Tag";
    private CurrencyRepo repo;
    private MutableLiveData<Map<String, String>> rates = new MutableLiveData<>();
    private MutableLiveData<ArrayList<String>> countryList = new MutableLiveData<>();
    private MutableLiveData<String> base= new MutableLiveData<>();

    private CompositeDisposable disposable;

    @Inject
    public CurrencyRateViewModel(CurrencyRepo repo) {
        this.repo = repo;
        disposable = new CompositeDisposable();
    }

    public void loadCurrencyInfo() {
        disposable.add(repo.getRates()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(s -> isLoading.setValue(true))
                .doAfterTerminate(() -> isLoading.setValue(false))
                .subscribeWith(new DisposableSingleObserver<ResultResponse>() {
                    @Override
                    public void onSuccess(ResultResponse resultResponse) {
                        if(resultResponse != null){
                            onSuccessful.setValue(true);
                            rates.setValue(resultResponse.getRates());
                            base.setValue(resultResponse.getBase());
                            countryList.setValue(resultResponse.getCountryList());
                        }

                }
                    @Override
                    public void onError(Throwable e) {
                        onError.setValue(e);
                    }
                }));

    }



    public MutableLiveData<Map<String,String>> getCurrencyInfo() {
        return rates;
    }

    public MutableLiveData<String> getBase(){
        return base;
    }

    public MutableLiveData<ArrayList<String>> getCountryList() {
        return countryList;
    }
}
