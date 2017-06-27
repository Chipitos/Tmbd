package com.tmbdnews.view.activities;

import android.os.Bundle;

import com.tmbdnews.R;
import com.tmbdnews.annotations.Layout;
import com.tmbdnews.databinding.ActMainBinding;

@Layout(R.layout.act_main)
public class ActMain extends BaseBindingActivity<ActMainBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navigator.showFrgFilmsList();
    }

}
