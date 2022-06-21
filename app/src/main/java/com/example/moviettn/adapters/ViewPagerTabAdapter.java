package com.example.moviettn.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.moviettn.tab_layout_home.TabCategoriesFragment;
import com.example.moviettn.tab_layout_home.TabTvShowsFragment;
import com.example.moviettn.tab_layout_home.TabHomeFragment;
import com.example.moviettn.tab_layout_home.TabMoviesFragment;

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
                title = "\uD83C\uDF9E Movies";
                break;
            case 2:
                title = "\uD83D\uDCFA Tv Shows";
                break;
            case 3:
                title = "Categories";
                break;
        }
        return title;
    }
}
