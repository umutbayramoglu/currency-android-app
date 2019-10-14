package com.uemre.evrekacurrencyapp.ui.base;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public abstract class BaseViewModel extends ViewModel {

    public final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    public final MutableLiveData<Throwable> onError = new MutableLiveData<>();
    public final MutableLiveData<Boolean> onSuccessful = new MutableLiveData<>();
}
