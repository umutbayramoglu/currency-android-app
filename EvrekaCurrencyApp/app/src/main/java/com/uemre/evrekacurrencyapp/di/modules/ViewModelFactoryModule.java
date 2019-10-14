package com.uemre.evrekacurrencyapp.di.modules;

import androidx.lifecycle.ViewModelProvider;

import com.uemre.evrekacurrencyapp.ui.viewmodels.ViewModelFactory;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ViewModelFactoryModule {

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory modelProvider);


}
