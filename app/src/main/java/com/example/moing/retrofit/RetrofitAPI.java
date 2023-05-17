package com.example.moing.retrofit;

import com.example.moing.Request.LoginRequest;
import com.example.moing.Request.RegisterAddressRequest;
import com.example.moing.Response.LoginResponse;
import com.example.moing.Response.RegisterAddressResponse;
import com.example.moing.Response.RegisterNameResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/** REST API 와 통신 하기 위한 Interface **/
public interface RetrofitAPI {
    //@POST("/api/v1/team/join")
    @POST("/api/v1/users/auth/kakao")
    Call<LoginResponse> kakaoLogin(@Body LoginRequest loginRequest);

    @GET("/api/v1/users/nickname/{nickname}/available")
    Call<RegisterNameResponse> NameAvailable(@Path("nickname") String nickname);

    @POST("/api/v1/users/additional-info")
    Call<RegisterAddressResponse> AdditionalInfo(@Body RegisterAddressRequest registerAddressRequest);

}
