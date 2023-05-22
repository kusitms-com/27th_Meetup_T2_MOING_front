package com.example.moing.retrofit;

import com.example.moing.Request.BoardMakeVoteRequest;
import com.example.moing.Request.ChangeJwtRequest;
import com.example.moing.Request.LoginRequest;
import com.example.moing.Request.MakeTeamRequest;
import com.example.moing.Request.RegisterAddressRequest;
import com.example.moing.Request.TeamUpdateRequest;
import com.example.moing.Response.AllVoteResponse;
import com.example.moing.Response.BoardFireResponse;
import com.example.moing.Response.BoardMakeVoteResponse;
import com.example.moing.Response.BoardMoimResponse;
import com.example.moing.Response.BoardNoReadNoticeResponse;
import com.example.moing.Response.BoardNoReadVoteResponse;
import com.example.moing.Response.ChangeJwtResponse;
import com.example.moing.Response.CheckAdditionalInfo;
import com.example.moing.Response.InvitationCodeResponse;
import com.example.moing.Response.InviteTeamResponse;
import com.example.moing.Response.LoginResponse;
import com.example.moing.Response.MakeTeamResponse;
import com.example.moing.Response.AllNoticeResponse;
import com.example.moing.Response.RegisterAddressResponse;
import com.example.moing.Response.RegisterNameResponse;
import com.example.moing.Response.TeamListResponse;
import com.example.moing.Response.TeamResponse;
import com.example.moing.Response.TeamUpdateResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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

    /** 추가정보 입력여부 확인 **/
    @GET("/api/v1/users/additional-info")
    Call<CheckAdditionalInfo> checkAdditionalInfo(@Header("Authorization") String token);

    /** 목표보드 소모임 정보 보기 **/
    @GET("/api/v1/team/{teamId}/goal-board")
    Call<BoardMoimResponse> moimInfo(@Header("Authorization") String token, @Path("teamId") Long teamId);

    /** 목표보드 소모임 불 새로고침 **/
    @GET("/api/v1/{teamId}/missions/teamRate")
    Call<BoardFireResponse> newBoardFire(@Header("Authorization") String token, @Path("teamId") Long teamId);

    /** 홈 화면 소모임 목록 GET **/
    @GET("/api/v1/team")
    Call<TeamListResponse> getTeamList(@Header("Authorization") String token);

    /** 공지 안 읽은 것만 조회 **/
    @GET("/api/v1/{teamId}/notice/unread")
    Call<BoardNoReadNoticeResponse> noReadNotice(@Header("Authorization") String token, @Path("teamId") Long teamId);

    /** 투표 안 읽은 것만 조회 **/
    @GET("/api/v1/{teamId}/vote/unread")
    Call<BoardNoReadVoteResponse> noReadVote(@Header("Authorization") String token, @Path("teamId") Long teamId);

    /** 공지 전체 조회 **/
    @GET("/api/v1/{teamId}/notice")
    Call<AllNoticeResponse> viewNotice(@Header("Authorization") String token, @Path("teamId") Long teamId);

    /** 투표 전체 조회 **/
    @GET("/api/v1/{teamId}/vote")
    Call<AllVoteResponse> viewVote(@Header("Authorization") String token, @Path("teamId") Long teamId);

    /** 투표 생성 **/
    @POST("/api/v1/{teamId}/vote")
    Call<BoardMakeVoteResponse> makeVote(@Header("Authorization") String token, @Path("teamId") Long teamId, @Body BoardMakeVoteRequest boardMakeVoteRequest);

    /** 소모임 정보 조회 (수정을 위한 정보 조회) **/
    @GET("/api/v1/team/{teamId}")
    Call<TeamResponse> getTeam(@Header("Authorization") String token, @Path("teamId") Long teamId);

    /** 소모임 초대 코드 조회  **/
    @GET("/api/v1/team/{teamId}/invite-code")
    Call<InvitationCodeResponse> getInvitationCode(@Header("Authorization") String token, @Path("teamId") Long teamId);

    /** 소모임 정보 수정  **/
    @PUT("/api/v1/team/{teamId}")
    Call<TeamUpdateResponse> putTeamUpdate(@Header("Authorization") String token, @Path("teamId") Long teamId, @Body TeamUpdateRequest teamUpdateRequest);

    /** 초대 코드를 통한 소모임 참 **/
    @POST("/api/v1/team/join")
    Call<InviteTeamResponse> postAuthInvitationCode(@Header("Authorization") String token, @Body String invitationCode);


}
