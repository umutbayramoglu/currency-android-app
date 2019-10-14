package com.uemre.evrekacurrencyapp.ui.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.uemre.evrekacurrencyapp.R;
import com.uemre.evrekacurrencyapp.data.model.CurrencyItem;
import com.uemre.evrekacurrencyapp.databinding.ActivityMainBinding;
import com.uemre.evrekacurrencyapp.databinding.SelectCurrenciesBinding;
import com.uemre.evrekacurrencyapp.ui.adapters.CurrencyAdapter;
import com.uemre.evrekacurrencyapp.ui.adapters.SelectCurrenciesAdapter;
import com.uemre.evrekacurrencyapp.ui.viewmodels.CurrencyRateViewModel;
import com.uemre.evrekacurrencyapp.ui.viewmodels.ViewModelFactory;
import com.uemre.evrekacurrencyapp.util.Preferences;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class MainActivity extends DaggerAppCompatActivity {

    private static final String TAG = "MainTag";
    private Map<String, String> ratesMap = new HashMap<>();
    private CurrencyAdapter currencyAdapter;
    private ActivityMainBinding binding;
    private int progressStatus;
    private Boolean isConnected = false;
    private CurrencyRateViewModel vm;
    private Snackbar noApiConnection;
    private Handler handler;
    private CurrencyItem baseCurrencyItem;
    private ArrayList<String> countryList = new ArrayList<>();

    @Inject
    ViewModelFactory vmFactory;

    @Inject
    Preferences preferences;

    @BindView(R.id.horizontal_progress)
    MaterialProgressBar horizontalPBar;

    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;

    @BindView(R.id.body)
    LinearLayout body;

    @BindView(R.id.last_update)
    TextView lastUpdate;

    @BindView(R.id.api_status)
    TextView baseCurrency;

    @BindView(R.id.currencyRecyclerView)
    RecyclerView currencyRView;

    @BindView(R.id.amount_edit_text)
    EditText amount;

    @BindView(R.id.base_currency_flag)
    CircleImageView baseCurrencyFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        binding.setBaseCurrencyItem(baseCurrencyItem);

        ButterKnife.bind(this);
        noApiConnection = Snackbar.make(body, R.string.no_api_connection, Snackbar.LENGTH_SHORT);
        showApiConnectionMessage(getIntent().getStringExtra("ApiStatus"));
        setRecyclerView();
        addCurrencies();
        observeCurrencyData();
        loadCurrencyInfo();
        setLastUpdate();
        swipeRefresh();
        amountTextWatcher();
        timer();


    }


    private void amountTextWatcher() {
        amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().equals(""))
                    currencyAdapter = new CurrencyAdapter(MainActivity.this, ratesMap,Long.valueOf(s.toString()));
                    currencyRView.setAdapter(currencyAdapter);
            }
        });
    }

    private void setLastUpdate() {
        Date date = Calendar.getInstance().getTime();
        DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        lastUpdate.setText(String.format("Last Update: %s", f.format(date)));
    }

    private void loadCurrencyInfo() {
        vm.loadCurrencyInfo();
    }

    private void timer() {
        progressStatus = 0;
        handler = new Handler();

        final Runnable sendData = new Runnable() {
            public void run() {
                try {
                    progressStatus += 1;
                    horizontalPBar.setProgress(progressStatus);
                    handler.postDelayed(this, 5);

                    if (progressStatus == 100) {
                        progressStatus = 0;
                        loadCurrencyInfo();
                        setLastUpdate();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.post(sendData);
    }

    private void swipeRefresh() {
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadCurrencyInfo();
                setLastUpdate();
                progressStatus = 0;
                refresh.setRefreshing(false);
            }
        });
    }

    private void showApiConnectionMessage(String status) {

        if (status.equals("true")) {
            noApiConnection.setText(R.string.api_connection_successful);
        } else if (status.equals("false")) {
            noApiConnection.setText(R.string.no_api_connection);
        }
        noApiConnection.show();

        String statusMessage = status.equals("true") ? "Connected" : "Not Connected";
        binding.apiStatus.setText("Api Status: " + statusMessage);

    }

    private void observeCurrencyData() {
        vm = ViewModelProviders.of(this, vmFactory).get(CurrencyRateViewModel.class);

        // Observe country list
        vm.getCountryList().observe(this,countries -> {
            if(countries !=null){
                if(countryList.size() >0)
                    countryList.clear();

                countryList.addAll(countries);
            }

        });

        // Get rates data.
        vm.getCurrencyInfo().observe(this, rates -> {
            if (rates != null) {
                Map<String,String> newRates = getSelectedCurrencies(rates);

                if (checkRatesIsChanged(ratesMap,newRates)) {
                    ratesMap.clear();
                    ratesMap.putAll(getSelectedCurrencies(rates));

                    if (currencyAdapter == null) {
                        currencyAdapter = new CurrencyAdapter(this, ratesMap,Long.valueOf(amount.getText().toString()));
                        currencyRView.setAdapter(currencyAdapter);
                    } else {
                        currencyRView.getAdapter().notifyDataSetChanged();
                    }
                }
            }
        });


        // Get base currencyAdapter.
        vm.getBase().observe(this, base -> {
            int baseFlagId = getResources().getIdentifier("flag_" + base.toLowerCase(),"drawable",getPackageName());
            Drawable baseFlagDrawable = getResources().getDrawable(baseFlagId);

            baseCurrencyItem = new CurrencyItem(base,baseFlagDrawable);
            binding.setBaseCurrencyItem(baseCurrencyItem);


        });


        // Observe success status.
        vm.onSuccessful.observe(this, success -> {
            if (success != null && isConnected == false) {
                showApiConnectionMessage("true");
                isConnected = true;
              //  setBodyVisibility(true);
            }
        });

        // Observe error status.
        vm.onError.observe(this, err -> {

            if (err != null && isConnected == true) {
                showApiConnectionMessage("false");
                isConnected = false;
               // setBodyVisibility(false);
            }
        });

    }

    private Map<String,String> getSelectedCurrencies(Map<String,String> rates) {
        Map<String,String> selectedCurrenyRates = new HashMap<>();
        for(String currency: preferences.selectedCurrencies){
           String rate =  rates.get(currency);
           selectedCurrenyRates.put(currency,rate);
        }

        return selectedCurrenyRates;

    }

    private void setRecyclerView() {
        currencyRView.setLayoutManager(new GridLayoutManager(this, 1));
        currencyRView.setHasFixedSize(true);
    }

    private boolean checkRatesIsChanged(Map<String, String> oldRates, Map<String,String> newRates) {
        if (!oldRates.equals(newRates)) {
            Log.d(TAG, "Rates are changed");
            return true;
        }
        Log.d(TAG, "Rates data fetched from api did not change.");

        return false;
    }

    private void addCurrencies() {
        binding.addCurrencies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectCurrenciesDialog();
            }
        });
    }

    private void selectCurrenciesDialog() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);

        SelectCurrenciesBinding binding = DataBindingUtil.inflate(LayoutInflater.from(MainActivity.this), R.layout.select_currencies, null, false);
        mBuilder.setView(binding.getRoot());

        SelectCurrenciesAdapter adapter = new SelectCurrenciesAdapter(countryList,MainActivity.this,preferences);
        binding.allCurrenciesRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,1));
        binding.allCurrenciesRecyclerView.setAdapter(adapter);

        final AlertDialog dialog = mBuilder.create();
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogSlide;
        dialog.show();

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                observeCurrencyData();
            }
        });

        binding.closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }
}
