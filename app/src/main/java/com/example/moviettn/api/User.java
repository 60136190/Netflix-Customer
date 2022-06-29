package com.example.moviettn.api;

import com.example.moviettn.model.NewPassLoginGoogle;
import com.example.moviettn.model.request.ChangePasswordRequest;
import com.example.moviettn.model.request.CheckPassword;
import com.example.moviettn.model.request.DeleteImageRequest;
import com.example.moviettn.model.request.FeedBack;
import com.example.moviettn.model.request.ForgetPasswordRequest;
import com.example.moviettn.model.request.LoginGoogleRequest;
import com.example.moviettn.model.request.LoginRequest;
import com.example.moviettn.model.request.RegisterRequest;
import com.example.moviettn.model.request.UpdateStateUserRequest;
import com.example.moviettn.model.request.UpdateUserRequest;
import com.example.moviettn.model.response.LoginGoogleResponse;
import com.example.moviettn.model.response.LoginResponse;
import com.example.moviettn.model.response.ProfileResponse;
import com.example.moviettn.model.response.RefreshTokenResponse;
import com.example.moviettn.model.response.RegisterResponse;
import com.example.moviettn.model.response.ResponseDTO;
import com.example.moviettn.model.response.UpdateStateUserResponse;
import com.example.moviettn.model.response.UploadImageResponse;

import java.util.HashMap;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface User {
    // register
    @POST("api/auth/customer/register")
    Call<RegisterResponse> register(@Body RegisterRequest register);

    // login
    @POST("api/auth/customer/login")
    Call<LoginResponse> login(@HeaderMap HashMap<String, String> hashMap, @Body LoginRequest register);

    // logout
    @GET("api/auth/customer/logout")
    Call<ResponseDTO> logout(@Header("Cookie") String accessToken);

    // forget Password
    @POST("api/auth/customer/forget")
    Call<ResponseDTO> forgetPassword(@HeaderMap HashMap<String, String> hashMap,@Body ForgetPasswordRequest forgetPasswordRequest);

    // changePassword
    @PATCH("api/auth/customer/changePassword")
    Call<ResponseDTO> changePassword(@HeaderMap HashMap<String, String> hashMap, @Body ChangePasswordRequest changePasswordRequest);

    // create password for user login with google
    @PATCH("api/auth/customer/newPassword")
    Call<ResponseDTO> createNewPass(@Header("Authorization") String authorization, @Body NewPassLoginGoogle newPassLoginGoogle);

    // getProfile
    @GET("api/auth/customer/profile")
    Call<ProfileResponse> getProfile(@Header("Authorization") String authorization);

    // check password
    @POST("api/auth/customer/checkPassword")
    Call<ResponseDTO> checkPassword(@Header("Authorization") String authorization,@Body CheckPassword checkPassword);

    // uploadImage
    @Multipart
    @POST("api/uploadImageUser")
    Call<UploadImageResponse> uploadImage(@Header("Authorization") String authorization, @Part MultipartBody.Part file);

    // update infomation user
    @PATCH("api/auth/customer/profile/update")
    Call<ResponseDTO> updateInfo(@HeaderMap HashMap<String, String> hashMap, @Body UpdateUserRequest updateUserRequest);

    // delete image user
    @POST("api/destroyImageUser")
    Call<ResponseDTO> deleteImage(@Header("Authorization") String authorization, @Body DeleteImageRequest deleteImageRequest);

    // refresh token
    @GET("api/auth/customer/refresh_token")
    Call<RefreshTokenResponse> refreshToken(@Header("Cookie") String refreshToken);

    // login google
    @POST("api/auth/customer/loginGoogle")
    Call<LoginGoogleResponse> loginGoogle(@HeaderMap HashMap<String, String> hashMap, @Body LoginGoogleRequest loginGoogleRequest);

    // update state user
    @POST("api/film/selectForAdultOrChild")
    Call<UpdateStateUserResponse> updateStateUser(@Header("Authorization") String authorization, @Body UpdateStateUserRequest updateStateUserRequest);

    // send feedback
    @POST("api/feedback/send")
    Call<FeedBack> sendFeedback(@Header("Authorization") String authorization, @Body FeedBack feedBack);


}
