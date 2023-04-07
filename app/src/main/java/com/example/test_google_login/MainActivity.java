package com.example.test_google_login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    // Google Sign in API와 호출할 구글 로그인 클라이언트
    GoogleSignInClient mGoogleSignInClient;
    private static final String TAG = "MainActivity";
    private final int check_signIn = 111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SignInButton btn_login = findViewById(R.id.sign_in_button);
        Button btn_logout = findViewById(R.id.logoutBt);
        btn_login.setOnClickListener(this);
        btn_logout.setOnClickListener(this);

        // 구글로그인

        // 앱에 필요한 사용자 데이터를 요청하도록 로그인 옵션을 설정.
        // DEFAULT_SIGN_IN parameter는 유저 ID 및 기본적인 프로필 정보를 요청하는데 사용된다.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                // 백엔드에서 사용자를 식별할 수 있는 사용자 ID 토큰 요청
                // 사용자의 기본 프로필(이름, 사진 ,URL 등)이 포함되므로 추가 호출할 필요 X
                .requestIdToken(getString(R.string.server_client_id))
                // 프론트에서 이메일 주소 요청
                .requestEmail()
                .build();

        // 위에서 만든 GoogleSignInOptions를 사용하여 GoogleSignInClient 객체를 생성한다.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // 사용자가 이미 Google로 앱에 로그인했는지 확인한다.
        GoogleSignInAccount gsa = GoogleSignIn.getLastSignedInAccount(this);

        // 로그인이 되어있는 경우
        if (gsa != null && gsa.getId() != null) {
            // 해당 처리를 해주면 된다
            Intent intent = new Intent(getApplicationContext(), loginSuccess.class);
            startActivity(intent);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount acct = completedTask.getResult(ApiException.class);

            if (acct != null) {
                String personName = acct.getDisplayName();      // 이름
                String personGivenName = acct.getGivenName();   // 성
                String personFamilyName = acct.getFamilyName(); // ?
                String personEmail = acct.getEmail();           // 이메일 주소
                String personId = acct.getId();                 // ?
                Uri personPhoto = acct.getPhotoUrl();           // 프로필 URL
                String AT = acct.getIdToken();

                Log.d(TAG, "handleSignInResult:personName "+personName);
                Log.d(TAG, "handleSignInResult:personGivenName "+personGivenName);
                Log.d(TAG, "handleSignInResult:personEmail "+personEmail);
                Log.d(TAG, "handleSignInResult:personId "+personId);
                Log.d(TAG, "handleSignInResult:personFamilyName "+personFamilyName);
                Log.d(TAG, "handleSignInResult:personPhoto "+personPhoto);
                Log.d(TAG, "ACCESS TOKEN" + AT);
            }
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.e(TAG, "signInResult:failed code=" + e.getStatusCode());

        }
    }

    public void onClick(View view) {
        // 버튼의 id를 가져온다.
        switch(view.getId())
        {
            case R.id.sign_in_button:
                // 로그인 버튼 클릭 시
                signIn();
                break;
            case R.id.logoutBt:
                //로그 아웃 버튼
                mGoogleSignInClient.signOut()
                        .addOnCompleteListener(this, task -> {
                            Log.d(TAG, "onClick:logout success ");
                            mGoogleSignInClient.revokeAccess()
                                    .addOnCompleteListener(this, task1 -> Log.d(TAG, "onClick:revokeAccess success "));
                        });
                Toast.makeText(getApplicationContext(), "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void signIn() {
        //getSignInIntent로 로그인 인텐트를 만든다.
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        // 인텐트 시작하여 로그인 버튼 탭을 처리한다.
        startActivityForResult(signInIntent, check_signIn);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        // 사용자가 로그인을 성공하면 사용자의 GoogleSignInAccount 객체를 가져올 수 있다.
        if (requestCode == check_signIn) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            // 정보 띄워주기 메서드 실행
            handleSignInResult(task);
            Intent intent = new Intent(getApplicationContext(), loginSuccess.class);
            startActivity(intent);
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}