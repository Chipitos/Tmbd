package com.tmbdnews.view.handlers;

import android.view.View;

public interface Handlers {

    interface FilmListAdapterHandlers {
        void onItemClick(View v);
    }

    interface FilmListHandlers {
        void onRetryClick(View v);
    }

    interface FilmDetailsHandlers {
        void onBackClick(View v);

        void onRetryClick(View v);
    }
}
