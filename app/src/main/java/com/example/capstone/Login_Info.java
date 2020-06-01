package com.example.capstone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Login_Info extends Activity {

    String strNickname, strProfile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer);

        TextView tvNickname = findViewById(R.id.tvNickname);
        TextView tvProfile = findViewById(R.id.tvProfile);

        Intent intent = getIntent();
        strNickname = intent.getStringExtra("name");
        strProfile = intent.getStringExtra("profile");

        tvNickname.setText(strNickname);
        tvProfile.setText(strProfile);

    }
}