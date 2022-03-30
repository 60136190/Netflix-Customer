package com.example.moviettn.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.moviettn.R;
import com.example.moviettn.api.ApiClient;
import com.example.moviettn.model.response.ProfileResponse;
import com.example.moviettn.model.response.ResponseDTO;
import com.example.moviettn.utils.Contants;
import com.example.moviettn.utils.StoreUtil;
import com.github.ybq.android.spinkit.style.Circle;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.File;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MoreFragment extends Fragment {

    private LinearLayout lnInformation;
    private LinearLayout lnLogout;
    private ImageView imgUser;
    private TextView tvFullName;
    private View view;
    GoogleSignInClient mGoogleSignInClient;
    public MoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_more, container, false);
        getProfile();
        initUi();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);

        lnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        return view;
    }

    private void initUi() {
        lnInformation = view.findViewById(R.id.ln_information);
        lnLogout = view.findViewById(R.id.ln_logout);
        tvFullName = view.findViewById(R.id.tv_fullname);
        imgUser = view.findViewById(R.id.img_user);
    }

    public void logout(){
        final Dialog dialog = new Dialog(getContext());
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

        ProgressBar progressBar =dialog.findViewById(R.id.spin_kit);
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

    public void getProfile() {
        Call<ProfileResponse> proifileResponseCall = ApiClient.getUserService().getProfile(
                StoreUtil.get(getContext(), "Authorization"));
        proifileResponseCall.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
//                String fullName = response.body().getUser().getFullname();
//                String im = response.body().getUser().getImage().getUrl();
//                tvFullName.setText(fullName);
//
//
//                if (im.isEmpty()) {
//                    imgUser.setImageResource(R.drawable.backgroundslider);
//                }else{
//                    Glide.with(getContext())
//                            .load(im)
//                            .into(imgUser);
//                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        deleteCache(getContext());
    }

    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) { e.printStackTrace();}
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
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