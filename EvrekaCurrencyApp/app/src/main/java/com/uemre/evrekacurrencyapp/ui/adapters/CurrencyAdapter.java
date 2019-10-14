package com.uemre.evrekacurrencyapp.ui.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.uemre.evrekacurrencyapp.R;
import com.uemre.evrekacurrencyapp.data.model.CurrencyItem;
import com.uemre.evrekacurrencyapp.databinding.CurrencyItemBinding;

import java.text.DecimalFormat;
import java.util.Map;

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.MyViewHolder> {
    private static final String TAG = "CurrenyAdapter";
    private Map<String, String> ratesMap;
    private Object[] currencies;
    private Resources res;
    private Context context;
    private long amount;

    public CurrencyAdapter(Context context, Map<String, String> ratesMap,long amount) {
        this.context = context;
        this.ratesMap = ratesMap;
        this.amount = amount;
        this.currencies = ratesMap.keySet().toArray();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        CurrencyItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.currency_item, parent, false);

        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return ratesMap.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CurrencyItemBinding binding;

        public MyViewHolder(CurrencyItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private void bind(int position) {
            currencies = ratesMap.keySet().toArray();
            String currency = (String) currencies[position];
            String rate = ratesMap.get(currency);
            Double newAmount = Double.valueOf(rate)  * amount;

            res = context.getResources();
            String mDrawableName = "flag_" + currency.toLowerCase();
            int resID;
            Drawable drawable;
            try {
                resID = res.getIdentifier(mDrawableName, "drawable", context.getPackageName());
                drawable = res.getDrawable(resID);
            } catch (Exception e) {
                resID = res.getIdentifier("not_found", "drawable", context.getPackageName());
                drawable = res.getDrawable(resID);
            }

            DecimalFormat df = new DecimalFormat(".##");
            binding.setItem(new CurrencyItem(currency, String.valueOf(df.format(newAmount)), drawable));


        }
    }


}
