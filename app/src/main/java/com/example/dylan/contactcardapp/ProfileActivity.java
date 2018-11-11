package com.example.dylan.contactcardapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ImageView profileImageView = (ImageView) findViewById(R.id.profileImageView);
        TextView nameTextView = (TextView) findViewById(R.id.nameTextView);
        Intent intent = getIntent();
        final String name = intent.getStringExtra(PersonAdapter.KEY_NAME);
        String image = intent.getStringExtra(PersonAdapter.KEY_IMAGE);
        Picasso.with(this)
                .load(image)
                .into(profileImageView);
        nameTextView.setText(name);
    }
}
