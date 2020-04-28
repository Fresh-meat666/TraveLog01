package com.example.travelog01.ui.map;


import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.travelog01.MainActivity;
import com.example.travelog01.R;

public class MapFragment extends Activity implements View.OnClickListener {
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    public LocationClient mLocationClient;
    public BDLocationListener myListener = new MyLocationListener();
    private Button bt;
    private Button button;
    private Button buttons;
    private LatLng latLng;
    private boolean isFirstLoc = true; // 是否首次定位

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        initView();
        initMap();
    }


    private void initMap() {
        //获取地图控件引用
        mBaiduMap = mMapView.getMap();
        //普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mBaiduMap.setMyLocationEnabled(true);

        //默认显示普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        //开启交通图
        //mBaiduMap.setTrafficEnabled(true);
        //开启热力图
        //mBaiduMap.setBaiduHeatMapEnabled(true);
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        //声明LocationClient类
        mLocationClient = new LocationClient(getApplicationContext());
        //配置定位SDK参数
        initLocation();
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        //开启定位
        mLocationClient.start();
        //图片点击事件，回到定位点
        mLocationClient.requestLocation();
    }

    //配置定位SDK参数
    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );
        //可选，默认gcj02，设置返回的定位结果坐标系
        option.setCoorType("bd09ll");
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        int span = 1000;
        option.setScanSpan(span);
        //可选，设置是否需要地址信息，默认不需要
        option.setIsNeedAddress(true);
        //可选，默认false,设置是否使用gps
        option.setOpenGps(true);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setLocationNotify(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation
        option.setIsNeedLocationDescribe(true);
        // .getLocationDescribe里得到，结果类似于“在北京天安门附近”
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIsNeedLocationPoiList(true);
        option.setIgnoreKillProcess(false);
        // 打开gps
        option.setOpenGps(true);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        //可选，默认false，设置是否收集CRASH信息，默认收集
        option.SetIgnoreCacheException(false);
        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        option.setEnableSimulateGps(false);
        mLocationClient.setLocOption(option);
    }

    //实现BDLocationListener接口,BDLocationListener为结果监听接口，异步获取定位结果
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            latLng = new LatLng(location.getLatitude(), location.getLongitude());
            // 构造定位数据
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100)
                    .latitude(location.getLatitude())
                    .longitude(location.getLongitude())
                    .build();

            // 设置定位数据
            mBaiduMap.setMyLocationData(locData);
            // 当不需要定位图层时关闭定位图层
            //mBaiduMap.setMyLocationEnabled(false);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                //loadPhoto();
                generateMarker();
                if (location.getLocType() == BDLocation.TypeGpsLocation) {
                    // GPS定位结果
                    Toast.makeText(MapFragment.this, location.getAddrStr(), Toast.LENGTH_SHORT).show();
                }
                else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
                    // 网络定位结果
                    Toast.makeText(MapFragment.this, location.getAddrStr(), Toast.LENGTH_SHORT).show();

                }
                else if (location.getLocType() == BDLocation.TypeOffLineLocation) {
                    // 离线定位结果
                    Toast.makeText(MapFragment.this, location.getAddrStr(), Toast.LENGTH_SHORT).show();
                } else if (location.getLocType() == BDLocation.TypeServerError) {
                    Toast.makeText(MapFragment.this, "服务器错误，请检查", Toast.LENGTH_SHORT).show();
                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                    Toast.makeText(MapFragment.this, "网络错误，请检查", Toast.LENGTH_SHORT).show();
                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                    Toast.makeText(MapFragment.this, "手机模式错误，请检查是否飞行", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void initView() {
        mMapView = (MapView) findViewById(R.id.bmapView);
        bt = (Button) findViewById(R.id.bt);
        bt.setOnClickListener(this);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
        buttons = (Button) findViewById(R.id.buttons);
        buttons.setOnClickListener(this);
    }

    private void loadPhoto()
    {
        //定义Maker坐标点
        LatLng point = new LatLng(latLng.latitude, latLng.longitude);
        //构建Marker图标
        //准备overlay图像数据，根据实情情况修复
        Drawable mark= getResources().getDrawable(R.drawable.example);
        ItemizedOverlay item1 = new ItemizedOverlay(mark,mMapView);


        Bitmap rawBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.example);
        Bitmap bitmap1 = Bitmap.createScaledBitmap(rawBitmap, 100, 100, true);
        BitmapDescriptor bitmap = BitmapDescriptorFactory.
                fromBitmap(bitmap1);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point) //必传参数
                .icon(bitmap) //必传参数
                .draggable(true);

        /*----------------处理照片信息------------------*/
    }

    private Bitmap getViewBitmap(View addViewContent) {

        addViewContent.setDrawingCacheEnabled(true);

        addViewContent.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        addViewContent.layout(0, 0, addViewContent.getMeasuredWidth(), addViewContent.getMeasuredHeight());

        addViewContent.buildDrawingCache();
        Bitmap cacheBitmap = addViewContent.getDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);

        return bitmap;
    }

    public void generateMarker()
    {
        /*此函数遍历数据库内的地点信息并显示到地图上
        （一）、 注意数据库至少必需包含的字段：
        1. 该地点的照片placePhoto
        2. 该地点的经纬度
        （二）、 从数据库中获取到地点的照片信息后：
        将下面的：
        placePhoto.setImageResource(R.drawable.example);
        改为：
        Bitmap bmpTemp = photoHelper.getBitmap("所获取的照片uri");
        placePhoto.setImageBitmap(bmpTemp);
        所获取的照片uri典型例子如：/sdcard/...，
        （三）、 从数据库中获得地点的经纬度信息后：
        将下面的：
        LatLng point = new LatLng(latLng.latitude, latLng.longitude);
        改为：
        double latitude = 从数据库中获取到的经度数据(double类型)
        double longitude = 从数据库中获取到的纬度数据(double类型)
        LatLng point = new LatLng(latitude,longitude)

        （四）、 这样就可完成相应的需求
        */

        //这里一般可用for语句读取数据库里的全部的照片地点信息
        //for(...){
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.container, null);
        ImageView placePhoto = view.findViewById(R.id.place);
        placePhoto.setImageResource(R.drawable.example);

        /*TextView placeTime = (TextView) view.findViewById(R.id.placeTime);
        placeTime.append("2020/04/24");*/
        BitmapDescriptor markerIcon = BitmapDescriptorFactory.fromBitmap(getViewBitmap(view));

        //定义Maker坐标点
        LatLng point = new LatLng(latLng.latitude, latLng.longitude);
        OverlayOptions oo = new MarkerOptions()
                .position(point)
                .icon(markerIcon)
                .zIndex(9)
                .draggable(true);
        mBaiduMap.addOverlay(oo);
        //for语句结束的地方
        //}
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt:
                //把定位点再次显现出来
                MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLng(latLng);
                mBaiduMap.animateMapStatus(mapStatusUpdate);
                break;
            case R.id.button:
                //卫星地图
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.buttons:
                //普通地图
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                break;
        }
    }

}
