package com.tmbdnews.di.component;


import com.tmbdnews.PerActivity;
import com.tmbdnews.di.module.ActivityModule;
import com.tmbdnews.di.module.RetrofitModule;
import com.tmbdnews.view.activities.BaseInjectActivity;
import com.tmbdnews.view.fragments.BaseInjectFragment;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class, RetrofitModule.class})

public interface ActivityComponent {

    void inject(BaseInjectActivity activity);

    void inject(BaseInjectFragment fragment);
}
