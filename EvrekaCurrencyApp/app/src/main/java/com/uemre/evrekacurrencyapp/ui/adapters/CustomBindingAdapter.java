package com.uemre.evrekacurrencyapp.ui.adapters;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.drawerlayout.widget.DrawerLayout;

public class CustomBindingAdapter {


    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView view, Drawable resource) {
        view.setImageDrawable(resource);
    }
}

