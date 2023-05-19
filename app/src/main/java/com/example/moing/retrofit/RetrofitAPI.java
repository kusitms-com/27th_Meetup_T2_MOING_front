package com.example.moing.retrofit;

import com.example.moing.Request.ChangeJwtRequest;
import com.example.moing.Request.LoginRequest;
import com.example.moing.Request.MakeTeamRequest;
import com.example.moing.Response.ChangeJwtResponse;
import com.example.moing.Response.MakeTeamResponse;
import com.example.moing.Request.RegisterAddressRequest;
import com.example.moing.Response.LoginResponse;
import com.example.moing.Response.RegisterAddressResponse;
import com.example.moing.Response.RegisterNameResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/** REST API와 통신하기 위한 Interface **/
public interface RetrofitAPI {
    //@POST("/api/v1/team/join")
    @POST("/api/v1/users/auth/kakao")
    Call<LoginResponse> kakaoLogin(@Body LoginRequest loginRequest);

    @GET("/api/v1/users/nickname/{nickname}/available")
    Call<RegisterNameResponse> NameAvailable(@Path("nickname") String nickname);

    @Headers({"Content-Type: application/json"})
    @POST("/api/v1/users/additional-info")
    Call<RegisterAddressResponse> AdditionalInfo(@Header("Authorization") String token, @Body RegisterAddressRequest registerAddressRequest);

    /** 소모임 생성 **/
    @POST("/api/v1/team")
    Call<MakeTeamResponse> makeTeam(@Header("Authorization") String token, @Body MakeTeamRequest makeTeamRequest);

    /** Jwt 갱신 **/
    @POST("/api/v1/users/auth/refresh")
    Call<ChangeJwtResponse> changeJwt(@Body ChangeJwtRequest changeJwtRequest);





}
