package com.example.moing.retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/** REST API 와 통신 하기 위한 Interface **/
public interface RetrofitAPI {
    //@POST("/api/v1/team/join")
    @POST("/api/v1/users/auth/kakao")
    Call<LoginResponse> kakaoLogin(@Body LoginRequest loginRequest);
}
