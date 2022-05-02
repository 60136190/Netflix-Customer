package com.example.moviettn.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.moviettn.R;
import com.example.moviettn.adapters.ViewPagerTabNewHotAdapter;
import com.example.moviettn.api.ApiClient;
import com.example.moviettn.model.request.UpdateStateUserRequest;
import com.example.moviettn.model.response.ProfileResponse;
import com.example.moviettn.utils.Contants;
import com.example.moviettn.utils.StoreUtil;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;

import java.util.concurrent.Executor;

import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NewHotFragment extends Fragment {
    private View view;
    private TabLayout tableLayout;
    private ViewPager viewPager;
    private ImageView imgUser;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_new_hot, container, false);
        initUi();
        getProfile();

        ViewPagerTabNewHotAdapter viewPagerBillAndRatingAdapter = new ViewPagerTabNewHotAdapter(getParentFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPagerBillAndRatingAdapter);
        view.clearAnimation();

        tableLayout.setupWithViewPager(viewPager);

        return view;
    }

    private void initUi() {
        tableLayout = view.findViewById(R.id.tab_tv_show);
        viewPager = view.findViewById(R.id.view_pager_tv_show);
        imgUser = view.findViewById(R.id.img_user);
    }

    public void getProfile() {
        Call<ProfileResponse> proifileResponseCall = ApiClient.getUserService().getProfile(
                StoreUtil.get(getContext(), "Authorization"));
        proifileResponseCall.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if (response.isSuccessful()) {
                    String im = response.body().getUser().getImage().getUrl();
                    String adult = response.body().getUser().getAdult();
                    StoreUtil.save(getContext(), Contants.adult, adult);
                    String a="1";

                    if (adult.equals(a)) {
                        Glide.with(getContext())
                                .load(im)
                                .into(imgUser);
                    } else {
                        imgUser.setImageResource(R.drawable.logokid);
                    }

                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener((Executor) this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    }
                });
    }

}