package com.tmbdnews.view.navigation;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.tmbdnews.R;
import com.tmbdnews.view.fragments.FrgFilmDetails;
import com.tmbdnews.view.fragments.FrgFilmsList;

import javax.inject.Inject;


public class Navigator {
    private final FragmentManager fragmentManager;

    @Inject
    Navigator(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void showFrgFilmsList() {
        showFragment(FrgFilmsList.newInstance(), false);
    }

    public void showFrgFilmDetails(int id) {
        showFragmentWithoutReplace(FrgFilmDetails.newInstance(id), true);
    }

    private void showFragment(Fragment frg, boolean addToBackStack) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.fragment_container, frg, frg.getClass().getSimpleName());
        if (addToBackStack)
            ft.addToBackStack(frg.getClass().getSimpleName());
        ft.commitAllowingStateLoss();
    }

    private void showFragmentWithoutReplace(Fragment frg, boolean addToBackStack) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(R.id.fragment_container, frg, frg.getClass().getSimpleName());
        if (addToBackStack)
            ft.addToBackStack(frg.getClass().getSimpleName());
        ft.commitAllowingStateLoss();
    }

    public void finish() {
        fragmentManager.popBackStack();
    }

}
