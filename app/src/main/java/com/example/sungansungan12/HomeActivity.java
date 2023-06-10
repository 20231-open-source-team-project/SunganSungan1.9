package com.example.sungansungan12;
//강수 담당
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class HomeActivity extends AppCompatActivity {
    AppCompatButton navi;
    AppCompatButton upload;
    AppCompatButton profile;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Log.i("SunganLog", "HomeActivity 실행");
            Log.i("SunganLog", "로그인 중");
            String email = user.getEmail();
            String uid = user.getUid();
            Log.i("SunganLog", "로그인 된 사용자: "+email+"고유식별자: "+uid);
            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            Log.i("SunganLog", "로그인정보 호출 성공");
        } else {
            Log.e("SunganLog", "로그인정보 호출 실패");
            // No user is signed in
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // 물품 탐색 버튼
        navi = findViewById(R.id.homeNaviButton);
        // 물품 등록 버튼
        upload = findViewById(R.id.homeUploadButton);
        // 개인 정보 수정 버튼
        profile = findViewById(R.id.homeProfileButton);

        navi.setOnClickListener(v -> {
            Intent intent = new Intent(this, NaviActivity.class);
            startActivity(intent);

            Log.i("SunganLog", "HomeActivity 백그라운드");
        });
        upload.setOnClickListener(v -> {
            Intent intent = new Intent(this, UploadActivity.class);
            startActivity(intent);

            Log.i("SunganLog", "HomeActivity 백그라운드");
        });
        profile.setOnClickListener(v -> {
            Intent intent = new Intent(this, profileActivity.class);
            startActivity(intent);

            Log.i("SunganLog", "HomeActivity 백그라운드");
        });

    }
}