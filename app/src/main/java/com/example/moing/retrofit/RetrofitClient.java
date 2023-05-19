package com.example.moing.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/** Retrofit 객체 생성 클래스 **/
public class RetrofitClient {

    // BASE_URL
    private static final String BASE_URL = "http://13.209.196.123";
    // Timeout 시간 (20초)
    private static final long CONNECT_TIMEOUT_SEC = 20L;
    private static Retrofit retrofit = null;

    /** Retrofit instance get - 없을 경우 생성 **/
    public static Retrofit getInstance() {
        if (retrofit == null) {
            // Interceptor 생성 - Http 요청/응답의 모든 정보를 로깅할 수 있도록 설정
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            // 헤더에 토큰 넣어 주는 코드 하나 추가

            // OkHttpClient 생성 - Interceptor 설정, Timeout 시간 설정
            OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
            clientBuilder.addInterceptor(loggingInterceptor)
                    .connectTimeout(CONNECT_TIMEOUT_SEC, TimeUnit.SECONDS);

            // GsonBuilder 설정
            Gson gson = new GsonBuilder().setLenient().create();

            // Retrofit 설정
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson)) // JSON -> Java 객체
                    .addConverterFactory(ScalarsConverterFactory.create())  // String, HTML -> Java 객체
                    .client(clientBuilder.build())
                    .build();
        }
        return retrofit;
    }

    /** Retrofit instance 를 통해 RetrofitAPI interface 의 구현체 를 반환 **/
    public static RetrofitAPI getApiService() {
        return getInstance().create(RetrofitAPI.class);
    }
}
