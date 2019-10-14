package com.uemre.evrekacurrencyapp.ui.viewmodels;

import androidx.lifecycle.MutableLiveData;

import com.uemre.evrekacurrencyapp.data.model.ResultResponse;
import com.uemre.evrekacurrencyapp.data.repository.CurrencyRepo;
import com.uemre.evrekacurrencyapp.ui.base.BaseViewModel;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class LaunchViewModel extends BaseViewModel {
    private CurrencyRepo repo;
    private CompositeDisposable disposable;

    @Inject
    public LaunchViewModel(CurrencyRepo repo) {
        this.repo = repo;
        disposable = new CompositeDisposable();
    }

    public void loadApiStatus() {
        disposable.add(repo.getRates()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ResultResponse>() {
                    @Override
                    public void onSuccess(ResultResponse resultResponse) {
                        if(resultResponse == null) {
                            onError.setValue(new Throwable());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        onError.setValue(e);
                    }
                }));

    }


}
