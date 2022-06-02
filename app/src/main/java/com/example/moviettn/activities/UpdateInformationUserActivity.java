package com.example.moviettn.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.moviettn.R;
import com.example.moviettn.api.ApiClient;
import com.example.moviettn.model.request.DeleteImageRequest;
import com.example.moviettn.model.request.UpdateUserRequest;
import com.example.moviettn.model.response.Image;
import com.example.moviettn.model.response.ProfileResponse;
import com.example.moviettn.model.response.ResponseDTO;
import com.example.moviettn.model.response.UploadImageResponse;
import com.example.moviettn.model.response.User;
import com.example.moviettn.realpath.RealPathUtil;
import com.example.moviettn.utils.Contants;
import com.example.moviettn.utils.StoreUtil;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;
import com.github.ybq.android.spinkit.style.FoldingCube;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateInformationUserActivity extends AppCompatActivity {

    private static final int MY_REQUEST_CODE = 23;
    public static final String TAG = UpdateInformationUserActivity.class.getName();
    private Uri mUri;
    private ImageView imgBack;
    private EditText edtHoTen;
    private EditText edtUserName;
    private EditText edtNgaySinh;
    private EditText edtDienThoai;
    private Button btnUpdate;

    private TextInputLayout tilFullName;
    private TextInputLayout tilUserName;
    private TextInputLayout tilDateofBirth;
    private TextInputLayout tilPhoneNumber;
    private TextView tvValidateSex;
    private TextView tvEmail;
    private ImageView imgInfo;
    RequestBody requestBody;

    int male = 0;
    private RadioGroup radioGroup;
    private RadioButton rdbMale;
    private RadioButton rdbFemale;
    private ProgressBar progressBar;


    private ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Log.e(TAG, "onActivityResult");
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data == null) {
                            return;
                        }
                        Uri uri = data.getData();
                        mUri = uri;
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            imgInfo.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_information_user);
        getProfile();
        initUi();
        edtNgaySinh.addTextChangedListener(new TextWatcher() {
            private String current = "";
            private String ddmmyyyy = "DDMMYYYY";
            private Calendar cal = Calendar.getInstance();
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]", "");
                    String cleanC = current.replaceAll("[^\\d.]", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8){
                        clean = clean + ddmmyyyy.substring(clean.length());
                    }else{
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        int day  = Integer.parseInt(clean.substring(0,2));
                        int mon  = Integer.parseInt(clean.substring(2,4));
                        int year = Integer.parseInt(clean.substring(4,8));

                        if(mon > 12) mon = 12;
                        cal.set(Calendar.MONTH, mon-1);

                        year = (year<1900)?1900:(year>2100)?2100:year;
                        cal.set(Calendar.YEAR, year);
                        // ^ first set year for the line below to work correctly
                        //with leap years - otherwise, date e.g. 29/02/2012
                        //would be automatically corrected to 28/02/2012

                        day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                        clean = String.format("%02d%02d%02d", day,mon,year);
                    }

                    clean = String.format("%s-%s-%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    edtNgaySinh.setText(current);
                    edtNgaySinh.setSelection(sel < current.length() ? sel : current.length());

                }
            }


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {}
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.Male:
                        male = 1;
                        break;
                    case R.id.Female:
                        male = 0;
                        break;
                }
            }
        });

        imgInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRequestPermission();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateImage();

            }
        });
    }

    private void initUi() {
        imgBack = findViewById(R.id.img_back);
        imgInfo = findViewById(R.id.imgUserInfor);
        edtHoTen = findViewById(R.id.edt_ho_ten);
        edtUserName = findViewById(R.id.edt_username);
        edtNgaySinh = findViewById(R.id.edt_ngay_sinh);
        edtDienThoai = findViewById(R.id.edt_phone_number);
        tvValidateSex = findViewById(R.id.tv_validateSex);
        tvEmail = findViewById(R.id.tv_email);
        tilFullName = findViewById(R.id.til_full_name);
        tilDateofBirth = findViewById(R.id.til_date_of_birth);
        tilPhoneNumber = findViewById(R.id.til_phone_number);
        btnUpdate = findViewById(R.id.btn_update);

        radioGroup = findViewById(R.id.radioGroup);
        rdbMale = findViewById(R.id.Male);
        rdbFemale = findViewById(R.id.Female);
        progressBar = (ProgressBar)findViewById(R.id.spin_kit);
    }

    public void getProfile() {
        Call<ProfileResponse> proifileResponseCall = ApiClient.getUserService().getProfile(
                StoreUtil.get(UpdateInformationUserActivity.this, "Authorization"));
        proifileResponseCall.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {

                String fullName = response.body().getUser().getFullname();
                String email = response.body().getUser().getEmail();
                String im = response.body().getUser().getImage().getUrl();
                String date_of_birth = response.body().getUser().getDateOfBirth();
                String sdt = response.body().getUser().getPhoneNumber();

                edtHoTen.setText(fullName);
                tvEmail.setText(email);
                edtNgaySinh.setText(date_of_birth);
                edtDienThoai.setText(sdt);

                if (im.isEmpty()) {
                    imgInfo.setImageResource(R.drawable.backgroundslider);
                } else {
                    Glide.with(getApplicationContext())
                            .load(im)
                            .into(imgInfo);
                }
                if(response.body().getUser().getSex() == null){

                }else if (response.body().getUser().getSex() == 1) {
                    rdbMale.setChecked(true);
                } else {
                    rdbFemale.setChecked(true);
                }


            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {

            }
        });
    }

    private void onClickRequestPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openGallery();
            return;
        }
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permission, MY_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            }
        }
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent, "Select picture"));
    }


    public void updateImage() {
        if (validateFullName() && validateDateofBirth()
                && validatePhoneNumber() && validateSex()) {
            if (mUri != null) {
                String strRealPath = RealPathUtil.getRealPath(getApplicationContext(), mUri);
                File fileImage = new File(strRealPath);
                requestBody = RequestBody.create(MediaType.parse(getContentResolver().getType(mUri)), fileImage);
                MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("file", fileImage.getName(), requestBody);

                //delete image
                deleteImage();

                // upload new image
                Call<UploadImageResponse> responseDTOCall = ApiClient.getUserService().uploadImage(
                        StoreUtil.get(UpdateInformationUserActivity.this, Contants.accessToken),
                        multipartBody);
                responseDTOCall.enqueue(new Callback<UploadImageResponse>() {
                    @Override
                    public void onResponse(Call<UploadImageResponse> call, Response<UploadImageResponse> response) {
                        if (response.isSuccessful()) {
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put(Contants.accessToken, StoreUtil.get(UpdateInformationUserActivity.this, Contants.accessToken));
                            hashMap.put(Contants.contentType, "application/json");
                            hashMap.put(Contants.contentLength, "<calculated when request is sent>");

                            String url = response.body().getUrl();
                            String public_id = response.body().getPublic_id();
                            String hoten = edtHoTen.getText().toString();
                            String ngaySinh = edtNgaySinh.getText().toString();
                            String sdt = edtDienThoai.getText().toString();

                            Image image = new Image(public_id, url);

                            UpdateUserRequest updateUserRequest = new UpdateUserRequest(hoten, image, sdt, male, ngaySinh);
                            Call<ResponseDTO> loginResponeCall = ApiClient.getUserService().updateInfo(
                                    hashMap, updateUserRequest);
                            loginResponeCall.enqueue(new Callback<ResponseDTO>() {
                                @Override
                                public void onResponse(Call<ResponseDTO> call, retrofit2.Response<ResponseDTO> response) {
                                    setProgressBar();
                                }

                                @Override
                                public void onFailure(Call<ResponseDTO> call, Throwable t) {
                                    Toast.makeText(UpdateInformationUserActivity.this, "Upload image is wrong", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }

                    @Override
                    public void onFailure(Call<UploadImageResponse> call, Throwable t) {
                        Toast.makeText(UpdateInformationUserActivity.this, "Upload image is wrong", Toast.LENGTH_SHORT).show();
                    }
                });

            } else {
                Call<ProfileResponse> proifileResponseCall = ApiClient.getUserService().getProfile(
                        StoreUtil.get(UpdateInformationUserActivity.this, Contants.accessToken));
                proifileResponseCall.enqueue(new Callback<ProfileResponse>() {
                    @Override
                    public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                        if (response.isSuccessful()) {
                            String im = response.body().getUser().getImage().getUrl();
                            String public_id = response.body().getUser().getImage().getPublic_id();
                            Image image = new Image(public_id, im);
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put(Contants.accessToken, StoreUtil.get(UpdateInformationUserActivity.this, Contants.accessToken));
                            hashMap.put(Contants.contentType, "application/json");
                            hashMap.put(Contants.contentLength, "<calculated when request is sent>");

                            String hoten = edtHoTen.getText().toString();
                            String ngaySinh = edtNgaySinh.getText().toString();
                            String sdt = edtDienThoai.getText().toString();
                            UpdateUserRequest updateUserRequest = new UpdateUserRequest(hoten, image, sdt, male, ngaySinh);


                            Call<ResponseDTO> loginResponeCall = ApiClient.getUserService().updateInfo(
                                    hashMap, updateUserRequest);
                            loginResponeCall.enqueue(new Callback<ResponseDTO>() {
                                @Override
                                public void onResponse(Call<ResponseDTO> call, retrofit2.Response<ResponseDTO> response) {
                                    setProgressBar();
                                }

                                @Override
                                public void onFailure(Call<ResponseDTO> call, Throwable t) {
                                    Toast.makeText(UpdateInformationUserActivity.this, "Upload image is wrong", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }


                    }

                    @Override
                    public void onFailure(Call<ProfileResponse> call, Throwable t) {

                    }
                });
            }
        }
    }

    private void deleteImage() {
        Call<ProfileResponse> proifileResponseCall = ApiClient.getUserService().getProfile(
                StoreUtil.get(UpdateInformationUserActivity.this, Contants.accessToken));
        proifileResponseCall.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if (response.isSuccessful()) {
                    String public_id = response.body().getUser().getImage().getPublic_id();
                    DeleteImageRequest deleteImageRequest = new DeleteImageRequest(public_id);
                    Call<ResponseDTO> responseDTOCall = ApiClient.getUserService().deleteImage(
                            StoreUtil.get(UpdateInformationUserActivity.this, Contants.accessToken),deleteImageRequest);
                    responseDTOCall.enqueue(new Callback<ResponseDTO>() {
                        @Override
                        public void onResponse(Call<ResponseDTO> call, Response<ResponseDTO> response) {

                        }

                        @Override
                        public void onFailure(Call<ResponseDTO> call, Throwable t) {
                            Toast.makeText(UpdateInformationUserActivity.this, "Upload image is wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {

            }
        });
}

    public void setProgressBar(){
        btnUpdate.setVisibility(View.INVISIBLE);
        Sprite cubeGrid = new Circle();
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
            }

            @Override
            public void onFinish() {
                progressBar.setVisibility(View.INVISIBLE);
                onBackPressed();
            }

        };
        countDownTimer.start();
    }

    private boolean validateFullName() {
        String fullname = edtHoTen.getText().toString().trim();
        if (fullname.length() < 8) {
            tilFullName.setError("Minimum 8 Character");
            return false;
        } else if (!fullname.matches(".*[A-Z].*")) {
            tilFullName.setError("Must contain 1 upper-case Character");
            return false;
        } else if (!fullname.matches(".*[a-z].*")) {
            tilFullName.setError("Must contain 1 Lower-case Character");
            return false;
        } else if (fullname.matches(".*[0-9].*")) {
            tilFullName.setError("Not number");
            return false;
        } else if (fullname.matches(".*[@!#$%^&*()_+=<>?/|].*")) {
            tilFullName.setError("Not special character");
            return false;
        } else {
            tilFullName.setError(null);
            return true;
        }
    }

    private boolean validateDateofBirth() {
        String dateofbirth = edtNgaySinh.getText().toString().trim();
        if (dateofbirth.length() < 10) {
            tilDateofBirth.setError("Minimum 10 Character");
            return false;
        } else {
            tilDateofBirth.setError(null);
            return true;
        }
    }

    private boolean validateSex() {
        if (!rdbMale.isChecked() && !rdbFemale.isChecked()) {
            tvValidateSex.setError("");
            return false;
        } else {
            tvValidateSex.setError(null);
            return true;
        }
    }

    private boolean validatePhoneNumber() {
        String phonenumber = edtDienThoai.getText().toString().trim();
        if (phonenumber.length() > 10) {
            tilPhoneNumber.setError("Maximum 10 Character");
            return false;
        } else if (phonenumber.matches(".*[-,._].*")) {
            tilPhoneNumber.setError("Not special character");
            return false;
        } else if (phonenumber.matches(".*[A-Z].*")) {
            tilPhoneNumber.setError("Not character");
            return false;
        } else if (phonenumber.matches(".*[a-z].*")) {
            tilPhoneNumber.setError("Not character");
            return false;
        } else if (phonenumber.length() <10) {
            tilPhoneNumber.setError("Phone number must be 10 Character");
            return false;
        } else {
            tilPhoneNumber.setError(null);
            return true;
        }
    }
}