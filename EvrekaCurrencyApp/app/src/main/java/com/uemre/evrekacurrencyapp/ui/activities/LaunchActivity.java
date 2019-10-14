package com.uemre.evrekacurrencyapp.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.snackbar.Snackbar;
import com.uemre.evrekacurrencyapp.R;
import com.uemre.evrekacurrencyapp.ui.viewmodels.LaunchViewModel;
import com.uemre.evrekacurrencyapp.ui.viewmodels.ViewModelFactory;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

public class LaunchActivity extends DaggerAppCompatActivity {
    private static final String TAG = "LaunchScreen_Tag";
    private Boolean isConnected = true;
    private static int LOAD_TIME_OUT = 2000;

    @Inject
    ViewModelFactory vmFactory;

    LaunchViewModel vm;

    @BindView(R.id.logo)
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_screen);
        ButterKnife.bind(this);
        animation();
        checkApiConnection();
        handler();

    }

    private void checkApiConnection() {
       vm = ViewModelProviders.of(this,vmFactory).get(LaunchViewModel.class);
       vm.loadApiStatus();
       vm.onError.observe(this,err->{
           if(err != null){
               isConnected = false;
           }
       });

    }

    private void animation() {
        Animation shrink_in = AnimationUtils.loadAnimation(this,R.anim.shrink_in);
        logo.startAnimation(shrink_in);
    }

    private void handler() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(LaunchActivity.this,MainActivity.class);
                if(isConnected == true)
                    homeIntent.putExtra("ApiStatus","true");
                else if (isConnected == false)
                    homeIntent.putExtra("ApiStatus","false");
                startActivity(homeIntent);
                finish();
            }
        },LOAD_TIME_OUT);
    }




}
