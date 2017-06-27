package com.tmbdnews.view.activities;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.tmbdnews.annotations.Layout;


public abstract class BaseBindingActivity<B extends ViewDataBinding> extends BaseInjectActivity {
    private B binding;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (binding == null) {
            int layout = getClass().getAnnotation(Layout.class).value();
            if (layout == 0) {
                throw new IllegalStateException("Layout must not be null and should be implemented via initLayout");
            }
            binding = DataBindingUtil.setContentView(this, layout);
        }
    }
}


