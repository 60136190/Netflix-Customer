package com.example.moviettn.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.moviettn.R;
import com.example.moviettn.activities.ChangePasswordActivity;
import com.example.moviettn.activities.InformationUserActivity;
import com.example.moviettn.activities.ListFavotireActivity;
import com.example.moviettn.activities.LoginActivity;
import com.example.moviettn.activities.SelectStateActivity;
import com.example.moviettn.activities.UpdateInformationUserActivity;
import com.example.moviettn.adapters.ViewPagerTabAdapter;
import com.example.moviettn.api.ApiClient;
import com.example.moviettn.model.request.UpdateStateUserRequest;
import com.example.moviettn.model.response.ProfileResponse;
import com.example.moviettn.model.response.ResponseDTO;
import com.example.moviettn.model.response.UpdateStateUserResponse;
import com.example.moviettn.tab_layout_home.TabHomeFragment;
import com.example.moviettn.utils.Contants;
import com.example.moviettn.utils.StoreUtil;
import com.github.ybq.android.spinkit.style.Circle;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;

import java.util.concurrent.Executor;

import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

//    private ViewFlipper viewFlipper;
    private ImageView imgUser;
    private ImageView imgLogoKid;
    private CardView cardview_img_user;
    private GifImageView gifChangeState;
    private View view;
    private ProgressBar progressBar;
    GoogleSignInClient mGoogleSignInClient;
    private TabLayout tableLayout;
    private ViewPager viewPager;
    private UpdateStateUserRequest updateStateUserRequest;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        initUi();
        getProfile();
        tableLayout = view.findViewById(R.id.tab_tv_show);
        viewPager = view.findViewById(R.id.view_pager_tv_show);

        ViewPagerTabAdapter viewPagerBillAndRatingAdapter = new ViewPagerTabAdapter(getParentFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPagerBillAndRatingAdapter);
        view.clearAnimation();

        tableLayout.setupWithViewPager(viewPager);
//        int images[] = {R.drawable.vpa, R.drawable.vpb, R.drawable.vpc,
//                R.drawable.vpd, R.drawable.vpe, R.drawable.vpf};
//
//        viewFlipper = view.findViewById(R.id.viewflipper_slide);
//
//        for (int image : images) {
//            flipperImages(image);
//        }


        cardview_img_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View viewdialog = getLayoutInflater().inflate(R.layout.bottom_sheet_manage_profile, null);
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
                bottomSheetDialog.setContentView(viewdialog);
                bottomSheetDialog.show();
                ImageView img_close = viewdialog.findViewById(R.id.img_close);
                ImageView img_Adults = viewdialog.findViewById(R.id.img_adults);
                ImageView img_Kid = viewdialog.findViewById(R.id.img_kid);
                TextView tv_Logout = viewdialog.findViewById(R.id.tv_logout);
                TextView tv_edit_profile = viewdialog.findViewById(R.id.tv_edit_profile);
                LinearLayout ln_MyList = viewdialog.findViewById(R.id.ln_my_list);
                LinearLayout ln_Account = viewdialog.findViewById(R.id.ln_account);
                LinearLayout ln_Setting = viewdialog.findViewById(R.id.ln_setting);

                img_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();
                    }
                });
                img_Adults.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UpdateStateUsertoAdultorKid("1");
                        bottomSheetDialog.dismiss();
                        imgLogoKid.setVisibility(View.GONE);
                        getProfile();
                    }
                });
                img_Kid.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UpdateStateUsertoAdultorKid("0");
                        bottomSheetDialog.dismiss();
                        CountDownTimer countDownTimer = new CountDownTimer(3500, 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                gifChangeState.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onFinish() {
                                gifChangeState.setVisibility(View.GONE);
                                imgLogoKid.setVisibility(View.VISIBLE);
                                getProfile();
                            }

                        };
                        countDownTimer.start();
                    }
                });
                tv_Logout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        logout();
                    }
                });
                ln_MyList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), ListFavotireActivity.class);
                        startActivity(intent);
                    }
                });
                ln_Account.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), InformationUserActivity.class);
                        startActivity(intent);
                    }
                });
                ln_Setting.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), ChangePasswordActivity.class);
                        startActivity(intent);
                    }
                });
                tv_edit_profile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), UpdateInformationUserActivity.class);
                        startActivity(intent);
                    }
                });

            }
        });
        return view;
    }


    private void initUi() {
        imgUser = view.findViewById(R.id.img_user);
        progressBar = (ProgressBar) view.findViewById(R.id.spin_kit);
        cardview_img_user = view.findViewById(R.id.cardview_img_user);
        imgLogoKid = view.findViewById(R.id.img_logo_kid);
        gifChangeState = view.findViewById(R.id.gif_change_state);
    }

    // slider if me need
//    public void flipperImages(int image) {
//        ImageView imageView = new ImageView(getContext());
//        imageView.setBackgroundResource(image);
//        viewFlipper.addView(imageView);
//        viewFlipper.setFlipInterval(3500);
//        viewFlipper.setAutoStart(true);
//        viewFlipper.setInAnimation(getContext(), android.R.anim.slide_in_left);
//    }

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


                    if (adult.equals(a)){
                        imgLogoKid.setVisibility(View.INVISIBLE);
                    }
                    else{
                        imgLogoKid.setVisibility(View.VISIBLE);
                    }
                } else {
                    setProgressBar();
                }

            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setProgressBar() {
        Circle cubeGrid = new Circle();
        progressBar.setIndeterminateDrawable(cubeGrid);
        progressBar.setVisibility(View.VISIBLE);

        CountDownTimer countDownTimer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int current = progressBar.getProgress();
                if (current >= progressBar.getMax()) {
                    current = 0;
                }
                progressBar.setProgress(current + 10);
                Toast.makeText(getContext(), "Bạn đã hết phiên đăng nhập", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFinish() {
                progressBar.setVisibility(View.INVISIBLE);
                getActivity().finish();
            }

        };
        countDownTimer.start();
    }

    public void logout() {
         Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_confirm_delete);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAtribute = window.getAttributes();
        window.setAttributes(windowAtribute);

        ProgressBar progressBar = dialog.findViewById(R.id.spin_kit);
        Button btnCancel = dialog.findViewById(R.id.btn_cancel);
        Button btnLogout = dialog.findViewById(R.id.btn_confirm_delete);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        // show dialog
        dialog.show();
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<ResponseDTO> loginResponeCall = ApiClient.getUserService().logout(
                        StoreUtil.get(getContext(), Contants.accessToken));
                loginResponeCall.enqueue(new Callback<ResponseDTO>() {
                    @Override
                    public void onResponse(Call<ResponseDTO> call, retrofit2.Response<ResponseDTO> response) {
                        if (response.isSuccessful()) {
                            // delete access token
                            SharedPreferences preferences = getContext().getSharedPreferences("MySharedPref", 0);
                            preferences.edit().remove("Authorization").commit();

                            // delete google in shared preference
                            SharedPreferences google = getContext().getSharedPreferences("com.google.android.gms.signin", 0);
                            google.edit().clear().commit();

                            onStart();

                            // progress bar
                            Circle foldingCube = new Circle();
                            progressBar.setIndeterminateDrawable(foldingCube);
                            progressBar.setVisibility(View.VISIBLE);

                            CountDownTimer countDownTimer = new CountDownTimer(5000, 1000) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                    int current = progressBar.getProgress();
                                    if (current >= progressBar.getMax()) {
                                        current = 0;
                                    }
                                    progressBar.setProgress(current + 10);
                                }

                                @Override
                                public void onFinish() {
                                    // logout google
                                    signOut();
                                    getActivity().finish();
                                }

                            };
                            countDownTimer.start();
                        }
                    }

                    //
                    @Override
                    public void onFailure(Call<ResponseDTO> call, Throwable t) {

                    }
                });
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
    private void UpdateStateUsertoAdultorKid(String number) {
        updateStateUserRequest = new UpdateStateUserRequest(number);
        Call<UpdateStateUserResponse> updateStateAdult = ApiClient.getUserService().updateStateUser(
                StoreUtil.get(getContext(), Contants.accessToken), updateStateUserRequest);
        updateStateAdult.enqueue(new Callback<UpdateStateUserResponse>() {
            @Override
            public void onResponse(Call<UpdateStateUserResponse> call, Response<UpdateStateUserResponse> response) {

            }

            @Override
            public void onFailure(Call<UpdateStateUserResponse> call, Throwable t) {

            }
        });
    }

}