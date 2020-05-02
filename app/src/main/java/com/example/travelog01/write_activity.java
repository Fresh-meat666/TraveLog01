package com.example.travelog01;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.travelog01.Database.DatabaseHelper;
import com.example.travelog01.datepicker.CustomDatePicker;
import com.example.travelog01.datepicker.DateFormatUtils;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;


public class write_activity extends AppCompatActivity implements View.OnClickListener{

    private TextView mTvSelectedTime;
    private CustomDatePicker mTimerPicker;
    public LinearLayout lTime;
    public ImageView lWeather;
    public LinearLayout addLlWeather;
    public ImageView sunny, thunderstorm, rainy, snowy, cloudy;
    public ImageView addIvShow;
    public ImageView lPhoto;

    EditText input_title, input_text;
    DatabaseHelper diaryDb;
    String weather;
    private String image;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        unbinder = ButterKnife.bind(this);
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

        lPhoto =  findViewById(R.id.add_photo);
        lPhoto.setOnClickListener(this);

        addIvShow =  findViewById(R.id.add_show);
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
            case R.id.add_photo:
                addLlWeather.setVisibility(View.GONE);
                checkSelfPermission();
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
            case R.id.menu_back:
                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setTitle("Attention");
                builder.setMessage("Are you sure to give up?");

                //设置确定按钮
                builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                //设置取消按钮
                builder.setPositiveButton("No",null);
                //显示提示框
                builder.show();
                return true;
        }
        return false;
    }

    /**
     * 检查权限
     */
    private void checkSelfPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)) {
                Snackbar.make(lPhoto, "需要读写权限", Snackbar.LENGTH_SHORT).show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
            }
        } else {
            if (image == null) {
                MultiImageSelector.create()
                        .showCamera(true)
                        .single()
                        .start(this, 0);
            } else {
                //RairUtils.showSnackar(addIvPhoto, "你已经选择了一张图片");
                Snackbar.make(lPhoto, "你已经选择了一张图片", Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 0:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    MultiImageSelector.create()
                            .showCamera(true) // 是否显示相机. 默认为显示
                            .single() // 单选模式
                            //.multi() // 多选模式, 默认模式;
                            .start(this, 0);
                } else {
                    //RairUtils.showSnackar(addIvPhoto, "没有授予读写权限，导出失败,请到设置中手动打开");
                    Snackbar.make(lPhoto, "没有授予读写权限，导出失败,请到设置中手动打开", Snackbar.LENGTH_SHORT).show();
                }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (requestCode == 0) {
            List<String> selectPaths = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
            assert selectPaths != null;
            if (selectPaths.size() != 0) {
                String imagePath = selectPaths.get(0);
                Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                addIvShow.setVisibility(View.VISIBLE);
                addIvShow.setImageBitmap(bitmap);
                image = imagePath;
            } else {
                addIvShow.setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}


