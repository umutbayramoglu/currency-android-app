package com.uemre.evrekacurrencyapp.di.modules;

import androidx.lifecycle.ViewModel;
import com.uemre.evrekacurrencyapp.ui.viewmodels.CurrencyRateViewModel;
import com.uemre.evrekacurrencyapp.ui.viewmodels.LaunchViewModel;
import com.uemre.evrekacurrencyapp.ui.viewmodels.ViewModelKey;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CurrencyRateViewModel.class)
    public abstract ViewModel bindCurrencyViewModel(CurrencyRateViewModel vm);

    @Binds
    @IntoMap
    @ViewModelKey(LaunchViewModel.class)
    public abstract ViewModel bindViewModel(LaunchViewModel vm);
}
