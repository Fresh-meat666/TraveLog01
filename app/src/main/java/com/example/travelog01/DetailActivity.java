package com.example.travelog01;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {
    private TextView mTitle, mContent, mLocDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mTitle = (TextView)findViewById(R.id.title);
        mContent = (TextView)findViewById(R.id.content);
        mLocDate = (TextView)findViewById(R.id.loc_date);

        mTitle.setText(getIntent().getExtras().getString("title"));
        mContent.setText(getIntent().getExtras().getString("content"));
        mLocDate.setText(getIntent().getExtras().getString("location") + ", " + getIntent().getExtras().getString("date"));
    }
}
