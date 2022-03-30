package com.example.moviettn.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.moviettn.tab_layout.TabCategoriesFragment;
import com.example.moviettn.tab_layout.TabTvShowsFragment;
import com.example.moviettn.tab_layout.TabHomeFragment;
import com.example.moviettn.tab_layout.TabMoviesFragment;

public class ViewPagerTabAdapter extends FragmentStatePagerAdapter {
    public ViewPagerTabAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new TabHomeFragment();
            case 1:
                return new TabMoviesFragment();
            case 2:
                return new TabTvShowsFragment();
            case 3:
                return new TabCategoriesFragment();
            default:
                return new TabHomeFragment();
        }

    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title = "Home";
                break;
            case 1:
                title = "Movies";
                break;
            case 2:
                title = "Tv Shows";
                break;
            case 3:
                title = "Categories";
                break;
        }
        return title;
    }
}
