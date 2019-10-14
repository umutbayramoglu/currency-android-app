package com.uemre.evrekacurrencyapp.ui.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.uemre.evrekacurrencyapp.R;
import com.uemre.evrekacurrencyapp.data.model.CurrencyItem;
import com.uemre.evrekacurrencyapp.databinding.SelectCurrencyDialogItemBinding;
import com.uemre.evrekacurrencyapp.util.Preferences;
import java.util.ArrayList;

public class SelectCurrenciesAdapter extends RecyclerView.Adapter<SelectCurrenciesAdapter.ViewHolder> {
    private Context context;
    private ArrayList<String> currenciesName;
    private Resources resources;
    private Preferences preferences;

    public SelectCurrenciesAdapter(ArrayList<String> currenciesName, Context context, Preferences preferences) {
        this.currenciesName = currenciesName;
        this.context = context;
        this.resources = context.getResources();
        this.preferences = preferences;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        SelectCurrencyDialogItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.select_currency_dialog_item, parent, false);
        return new ViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return currenciesName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SelectCurrencyDialogItemBinding binding;

        public ViewHolder(@NonNull SelectCurrencyDialogItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(int position) {
            String currency = currenciesName.get(position);
            String mDrawableName = "flag_" + currency.toLowerCase();

            int resID;
            Drawable drawable;
            try {
                resID = resources.getIdentifier(mDrawableName, "drawable", context.getPackageName());
                drawable = resources.getDrawable(resID);
            } catch (Exception e) {
                resID = resources.getIdentifier("not_found", "drawable", context.getPackageName());
                drawable = resources.getDrawable(resID);
            }

            if (preferences.selectedCurrencies.contains(currenciesName.get(position))) {
                binding.checkbox.setChecked(true);
            } else {
                binding.checkbox.setChecked(false);
            }

            checkBoxListener(position);
            CurrencyItem item = new CurrencyItem(currency, drawable);
            binding.setCurrencyItem(item);
        }

        private void checkBoxListener(int position) {
            binding.itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!binding.checkbox.isChecked()){
                        binding.checkbox.toggle();
                        if (!preferences.selectedCurrencies.contains(currenciesName.get(position))) {
                            Log.d("Adapter Add", "" + position);
                            preferences.selectedCurrencies.add(currenciesName.get(position));
                        }
                    }
                   else {
                        binding.checkbox.toggle();
                        if (preferences.selectedCurrencies.contains(currenciesName.get(position))) {
                            Log.d("Adapter Remove", "" + position);
                            preferences.selectedCurrencies.remove(currenciesName.get(position));
                        }
                    }
               }
           });

        }


    }

}
