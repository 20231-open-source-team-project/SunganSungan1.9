package com.example.sungansungan12;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PostShowActivity extends AppCompatActivity {
    private TextView titleTextView;
    private ImageView photoImageView;
    private TextView contentTextView;
    private ImageView homeButton;
    private TextView userIdTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_show);

        titleTextView = findViewById(R.id.titleTextView);
        photoImageView = findViewById(R.id.photoImageView);
        contentTextView = findViewById(R.id.contentTextView);
        homeButton = findViewById(R.id.homeButton);
        userIdTextView = findViewById(R.id.userIdTextView);

        Intent intent = getIntent();
        String selectedPost = intent.getStringExtra("selectedPost");

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("posts");
        databaseReference.orderByChild("name").equalTo(selectedPost).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String postTitle = postSnapshot.child("name").getValue(String.class);
                    String postContent = postSnapshot.child("description").getValue(String.class);
                    String postImageUrl = postSnapshot.child("imageUrl").getValue(String.class);

                    titleTextView.setText(postTitle);
                    contentTextView.setText(postContent);
                    Glide.with(PostShowActivity.this).load(postImageUrl).into(photoImageView);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // 데이터 로드 실패 시 처리할 내용 작성
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostShowActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        // 올린 사람 ID 가져오기
        DatabaseReference uploaderRef = FirebaseDatabase.getInstance().getReference("posts");
        uploaderRef.orderByChild("name").equalTo(selectedPost).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String uploaderId = postSnapshot.child("userId").getValue(String.class);

                    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(uploaderId);
                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String userName = dataSnapshot.child("name").getValue(String.class);
                                userIdTextView.setText(userName);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // 데이터 로드 실패 시 처리할 내용 작성
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // 데이터 로드 실패 시 처리할 내용 작성
            }
        });
    }
}
