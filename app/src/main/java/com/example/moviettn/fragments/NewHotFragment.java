package com.example.moviettn.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moviettn.R;
import com.example.moviettn.adapters.ViewPagerTabAdapter;
import com.example.moviettn.adapters.ViewPagerTabNewHotAdapter;
import com.google.android.material.tabs.TabLayout;


public class NewHotFragment extends Fragment {


    private View view;
    private TabLayout tableLayout;
    private ViewPager viewPager;
    public NewHotFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_new_hot, container, false);
        tableLayout = view.findViewById(R.id.tab_tv_show);
        viewPager = view.findViewById(R.id.view_pager_tv_show);

        ViewPagerTabNewHotAdapter viewPagerBillAndRatingAdapter = new ViewPagerTabNewHotAdapter(getParentFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPagerBillAndRatingAdapter);
        view.clearAnimation();

        tableLayout.setupWithViewPager(viewPager);
        return view;
    }
}