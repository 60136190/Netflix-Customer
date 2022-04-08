package com.example.moviettn.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.moviettn.tab_layout_home.TabCategoriesFragment;
import com.example.moviettn.tab_layout_home.TabHomeFragment;
import com.example.moviettn.tab_layout_home.TabMoviesFragment;
import com.example.moviettn.tab_layout_home.TabTvShowsFragment;
import com.example.moviettn.tab_layout_new_hot.ComingSoonFragment;
import com.example.moviettn.tab_layout_new_hot.WatchingFragment;

public class ViewPagerTabNewHotAdapter extends FragmentStatePagerAdapter {
    public ViewPagerTabNewHotAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new ComingSoonFragment();
            case 1:
                return new WatchingFragment();
            default:
                return new ComingSoonFragment();
        }

    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title = "Coming Soon";
                break;
            case 1:
                title = "Everyone's Watching";
                break;
        }
        return title;
    }
}
