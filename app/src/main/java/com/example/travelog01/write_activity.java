package com.example.travelog01;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.travelog01.Database.DatabaseHelper;
import com.example.travelog01.datepicker.CustomDatePicker;
import com.example.travelog01.datepicker.DateFormatUtils;


public class write_activity extends AppCompatActivity implements View.OnClickListener{

    private TextView mTvSelectedTime;
    private CustomDatePicker mTimerPicker;
    public LinearLayout lTime;
    public ImageView lWeather;
    public LinearLayout addLlWeather;
    public ImageView sunny, thunderstorm, rainy, snowy, cloudy;

    EditText input_title, input_text;
    DatabaseHelper diaryDb;
    String weather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        mTvSelectedTime = findViewById(R.id.tv_selected_time);
        input_text = (EditText) findViewById(R.id.input_text);
        input_title = (EditText) findViewById(R.id.input_title);
        diaryDb = new DatabaseHelper(this);

        initView();

    }

    private void initView() {

        lTime =  findViewById(R.id.ll_time);
        mTvSelectedTime = findViewById(R.id.tv_selected_time);
        lTime.setOnClickListener(this);
        initTimerPicker();

        addLlWeather = findViewById(R.id.add_ll_weather);

        lWeather =  findViewById(R.id.add_weather);
        lWeather.setOnClickListener(this);

        sunny =  (ImageView) findViewById(R.id.add_sunny);
        sunny.setOnClickListener(this);
        rainy =  (ImageView) findViewById(R.id.add_rain);
        rainy.setOnClickListener(this);
        cloudy =  (ImageView) findViewById(R.id.add_cloudy);
        cloudy.setOnClickListener(this);
        thunderstorm =  (ImageView) findViewById(R.id.add_thunderstorm);
        thunderstorm.setOnClickListener(this);
        snowy =  (ImageView) findViewById(R.id.add_snowy);
        snowy.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_time:
                // 日期格式为yyyy-MM-dd HH:mm
                mTimerPicker.show(mTvSelectedTime.getText().toString());
                break;

            case R.id.add_sunny:
                addLlWeather.setVisibility(View.GONE);
                lWeather.setImageResource(R.drawable.ic_weather_sunny);
                weather = "sunny";
                break;
            case R.id.add_cloudy:
                addLlWeather.setVisibility(View.GONE);
                lWeather.setImageResource(R.drawable.ic_weather_cloudy);
                weather = "cloudy";
                break;
            case R.id.add_rain:
                addLlWeather.setVisibility(View.GONE);
                lWeather.setImageResource(R.drawable.ic_weather_rain);
                weather = "rainy";
                break;
            case R.id.add_thunderstorm:
                addLlWeather.setVisibility(View.GONE);
                lWeather.setImageResource(R.drawable.ic_weather_thunderstorm);
                weather = "thunderstorm";
                break;
            case R.id.add_snowy:
                addLlWeather.setVisibility(View.GONE);
                lWeather.setImageResource(R.drawable.ic_weather_snowy);
                weather = "snowy";
                break;
            case R.id.add_weather:
                addLlWeather.setVisibility(View.VISIBLE);
                break;
        }
    }


    private void initTimerPicker() {
        String beginTime = "2000-01-01 00:00";
        String endTime = DateFormatUtils.long2Str(System.currentTimeMillis(), true);

        mTvSelectedTime.setText(endTime);

        // 通过日期字符串初始化日期，格式请用：yyyy-MM-dd HH:mm
        mTimerPicker = new CustomDatePicker(this, new CustomDatePicker.Callback() {
            @Override
            public void onTimeSelected(long timestamp) {
                mTvSelectedTime.setText(DateFormatUtils.long2Str(timestamp, true));
            }
        }, beginTime, endTime);
        // 允许点击屏幕或物理返回键关闭
        mTimerPicker.setCancelable(true);
        // 显示时和分
        mTimerPicker.setCanShowPreciseTime(true);
        // 允许循环滚动
        mTimerPicker.setScrollLoop(true);
        // 允许滚动动画
        mTimerPicker.setCanShowAnim(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the aaction bar if it is present
        getMenuInflater().inflate(R.menu.add_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_add:
                Toast.makeText(this, "Settings Button Clicked !",
                        Toast.LENGTH_LONG).show();
                boolean isInserted = diaryDb.insertData(mTvSelectedTime.getText().toString(),
                        input_title.getText().toString(), input_text.getText().toString(), "weather");
                this.finish();
                return true;

        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}


