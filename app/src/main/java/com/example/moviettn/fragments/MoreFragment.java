package com.example.moviettn.fragments;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.moviettn.R;
import com.example.moviettn.activities.ChangePasswordActivity;
import com.example.moviettn.activities.ConfirmPasswordActivity;
import com.example.moviettn.activities.HistoryBillActivity;
import com.example.moviettn.activities.InformationUserActivity;
import com.example.moviettn.activities.ListFavotireActivity;
import com.example.moviettn.activities.SendFeedBackActivity;
import com.example.moviettn.activities.UpdateInformationUserActivity;
import com.example.moviettn.api.ApiClient;
import com.example.moviettn.model.request.UpdateStateUserRequest;
import com.example.moviettn.model.response.ProfileResponse;
import com.example.moviettn.model.response.UpdateStateUserResponse;
import com.example.moviettn.utils.Contants;
import com.example.moviettn.utils.StoreUtil;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoreFragment extends Fragment {

    ImageView imgUser;
    TextView tvSendFeedBack;
    Button btnHistoryBill, btnSetUp;
    private GifImageView gifChangeState;
    private View view;
    private UpdateStateUserRequest updateStateUserRequest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_more, container, false);
        getProfile();
        initUi();

        tvSendFeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SendFeedBackActivity.class);
                startActivity(intent);
            }
        });

        btnHistoryBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HistoryBillActivity.class);
                startActivity(intent);
            }
        });

        btnSetUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View viewdialog = getLayoutInflater().inflate(R.layout.bottom_sheet_select_state, null);
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
                bottomSheetDialog.setContentView(viewdialog);
                bottomSheetDialog.show();
                ImageView img_close = viewdialog.findViewById(R.id.img_close);
                ImageView img_Adults = viewdialog.findViewById(R.id.img_adults);
                ImageView img_Kid = viewdialog.findViewById(R.id.img_kid);

                img_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();
                    }
                });
                img_Adults.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), ConfirmPasswordActivity.class);
                        startActivity(intent);

//                        UpdateStateUsertoAdultorKid("1");
//                        bottomSheetDialog.dismiss();
//                        getProfile();
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
                                getProfile();
                            }

                        };
                        countDownTimer.start();
                    }
                });

            }
        });

        return view;
    }

    private void initUi() {
        imgUser = view.findViewById(R.id.img_user);
        tvSendFeedBack = view.findViewById(R.id.tv_send_feedback);
        btnHistoryBill = view.findViewById(R.id.btn_history_bill);
        btnSetUp = view.findViewById(R.id.btn_setup);
        gifChangeState = view.findViewById(R.id.gif_change_state);
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

    @Override
    public void onResume() {
        super.onResume();
        getProfile();
    }
}