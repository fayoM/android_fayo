package com.example.fayoUM;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fayoUM.util.User;

public class UdetailA extends AppCompatActivity {
     TextView tvFullName;
     TextView tvMail;
     TextView tvPhone;
     TextView tvGender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
         getActionBar().setTitle("User Detail");
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        User user=(User)bundle.get("user");

        tvFullName=findViewById(R.id.fullName);
        tvMail=findViewById(R.id.email);
        tvPhone=findViewById(R.id.mobile);
        tvGender=findViewById(R.id.gender);

        tvFullName.setText(user.getFullName());
        tvMail.setText(user.getEmail());
        tvGender.setText(user.getGender());
        tvPhone.setText(user.getPhone());


    }
}
