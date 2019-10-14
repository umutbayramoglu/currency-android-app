package com.uemre.evrekacurrencyapp.di.components;

import android.app.Application;

import com.uemre.evrekacurrencyapp.di.App;
import com.uemre.evrekacurrencyapp.di.modules.AppModule;
import com.uemre.evrekacurrencyapp.di.modules.FragmentBuildersModule;
import com.uemre.evrekacurrencyapp.di.modules.NetworkModule;
import com.uemre.evrekacurrencyapp.di.modules.ViewModelFactoryModule;
import com.uemre.evrekacurrencyapp.di.modules.ViewModelModule;
import com.uemre.evrekacurrencyapp.ui.viewmodels.ViewModelFactory;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;


@Singleton
@Component(modules = {AndroidSupportInjectionModule.class, AppModule.class, NetworkModule.class,FragmentBuildersModule.class, ViewModelFactoryModule.class})
public interface AppComponent extends AndroidInjector<App> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}

