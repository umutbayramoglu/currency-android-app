package com.uemre.evrekacurrencyapp.di.modules;

import com.uemre.evrekacurrencyapp.ui.activities.LaunchActivity;
import com.uemre.evrekacurrencyapp.ui.activities.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(includes = {CurrencyModule.class})
public abstract class FragmentBuildersModule {

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract MainActivity contributeCurrencyActivities();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract LaunchActivity contributeLaunchActivities();
}
