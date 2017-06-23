package com.tmbdnews.viewmodel;


import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.tmbdnews.App;

public abstract class BaseViewModel<T> extends BaseInjectViewModel {
    T item;

    @BindingAdapter({"imageUrl"})
    public static void bindImage(ImageView view, String url) {
        if (url != null && !url.isEmpty())
            Picasso.with(App.getComponent().getContext()).load(url).into(view);
    }
}
