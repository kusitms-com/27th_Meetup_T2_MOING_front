package com.example.test_google_login;

import android.content.ActivityNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.kakao.sdk.common.KakaoSdk;
import com.kakao.sdk.common.util.KakaoCustomTabsClient;
import com.kakao.sdk.share.ShareClient;
import com.kakao.sdk.share.WebSharerClient;
import com.kakao.sdk.template.model.Content;
import com.kakao.sdk.template.model.FeedTemplate;
import com.kakao.sdk.template.model.ItemContent;
import com.kakao.sdk.template.model.ItemInfo;
import com.kakao.sdk.template.model.Link;
import com.kakao.sdk.template.model.Social;
import com.kakao.sdk.user.UserApiClient;

import java.util.Arrays;

/** 카카오 링크 **/
public class KakaoLinkActivity extends AppCompatActivity {

    // 템플릿 종류 : Feed, List, Location, Commerce, Text, Calender
    // Feed
    FeedTemplate feedTemplate = new FeedTemplate(
            new Content("오늘의 디저트",
                    "http://mud-kage.kakao.co.kr/dn/Q2iNx/btqgeRgV54P/VLdBs9cvyn8BJXB3o7N8UK/kakaolink40_original.png",
                    new Link("https://developers.kakao.com",
                            "https://developers.kakao.com"),
                    "#케익 #딸기 #삼평동 #카페 #분위기 #소개팅"
            ),
            new ItemContent("Kakao",
                    "http://mud-kage.kakao.co.kr/dn/Q2iNx/btqgeRgV54P/VLdBs9cvyn8BJXB3o7N8UK/kakaolink40_original.png",
                    "Cheese cake",
                    "http://mud-kage.kakao.co.kr/dn/Q2iNx/btqgeRgV54P/VLdBs9cvyn8BJXB3o7N8UK/kakaolink40_original.png",
                    "Cake",
                    Arrays.asList(new ItemInfo("cake1", "1000원")),
                    "Total",
                    "15000원"
            ),
            new Social(286, 45, 845),
            Arrays.asList(new com.kakao.sdk.template.model.Button("웹으로 보기", new Link("https://developers.kakao.com", "https://developers.kakao.com")))
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kakao_link);

        // Kakao SDK 초기화
        KakaoSdk.init(this, "2595c6013ac700285aab413683c5771d");

        // 버튼 클릭시 카카오 링크 공유
        Button button = findViewById(R.id.activity_kakao_link_bt);
        button.setOnClickListener(v ->{
            if (UserApiClient.getInstance().isKakaoTalkLoginAvailable(KakaoLinkActivity.this)) {
                kakaoLink();
            } else {
                webKakaoLink();
            }
        });
    }

    /** 카카오 링크 공유 **/
    public void kakaoLink() {
        String TAG = "kakaoLink()";

        ShareClient.getInstance().shareDefault(KakaoLinkActivity.this, feedTemplate, null, (linkResult, error) -> {
            if (error != null) {
                // 공유 실패
                Log.e("TAG", "카카오링크 보내기 실패", error);
            } else if (linkResult != null) {
                // 공유 성공
                Log.d(TAG, "카카오링크 보내기 성공 ${linkResult.intent}");
                KakaoLinkActivity.this.startActivity(linkResult.getIntent());

                // 카카오 링크 보내기에 성공 했지만 아래 경고 메시지가 존재할 경우 일부 컨텐츠가 정상 동작하지 않을 수 있음
                Log.w("TAG", "Warning Msg: " + linkResult.getWarningMsg());
                Log.w("TAG", "Argument Msg: " + linkResult.getArgumentMsg());
            }
            return null;
        });
    }

    /** 카카오톡 미설치 시 웹을 통한 카카오톡 접근 후 공유 **/
    public void webKakaoLink() {
        String TAG = "webKakaoLink()";

        // 카카오톡 미설치: 웹 공유 사용 권장
        // 웹 공유 예시 코드
        Uri sharerUrl = WebSharerClient.getInstance().makeDefaultUrl(feedTemplate);

        // CustomTabs으로 웹 브라우저 열기
        // 1. CustomTabs으로 Chrome 브라우저 열기
        try {
            KakaoCustomTabsClient.INSTANCE.openWithDefault(KakaoLinkActivity.this, sharerUrl);
        } catch (UnsupportedOperationException e) {
            // Chrome 브라우저가 없을 때 예외처리
        }

        // 2. CustomTabs으로 디바이스 기본 브라우저 열기
        try {
            KakaoCustomTabsClient.INSTANCE.open(KakaoLinkActivity.this, sharerUrl);
        } catch (ActivityNotFoundException e) {
            // 인터넷 브라우저가 없을 때 예외처리
        }
    }



}