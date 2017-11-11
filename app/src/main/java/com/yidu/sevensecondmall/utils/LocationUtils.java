package com.yidu.sevensecondmall.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Administrator on 2017/1/25 0025.
 */
public class LocationUtils {

    private volatile static LocationUtils uniqueInstance;
    private LocationHelper mLocationHelper;
    private MyLocationListener myLocationListener;
    private LocationManager mLocationManager;
    private Context mContext;

    private LocationUtils(Context context) {
        mContext = context;
        mLocationManager = (LocationManager) context
                .getSystemService( Context.LOCATION_SERVICE );
    }

    //采用Double CheckLock(DCL)实现单例
    public static LocationUtils getInstance(Context context) {
        if (uniqueInstance == null) {
            synchronized (LocationUtils.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new LocationUtils( context );
                }
            }
        }
        return uniqueInstance;
    }

    /**
     * 初始化位置信息
     * @param locationHelper 传入位置回调接口
     */
    public void initLocation(LocationHelper locationHelper) {
        Location location = null;
        mLocationHelper = locationHelper;
        if (myLocationListener == null) {
            myLocationListener = new MyLocationListener();
        }
        // 需要检查权限,否则编译报错,想抽取成方法都不行,还是会报错。只能这样重复 code 了。
        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission( mContext, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission( mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ToastUtil.showToast(mContext,"not permission");
            return  ;
        }
        if (mLocationManager.isProviderEnabled( LocationManager.NETWORK_PROVIDER )) {
//            ToastUtil.showToast(mContext,"NETWORK_PROVIDER----:true");
            location = mLocationManager.getLastKnownLocation( LocationManager.NETWORK_PROVIDER );
//            Log.e("MoLin", "LocationManager.NETWORK_PROVIDER");
            if (location != null) {
                locationHelper.UpdateLastLocation(location);
//                ToastUtil.showToast(mContext,"UpdateLastLocation-------location != null");
            }
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    0, 0, myLocationListener);
//            ToastUtil.showToast(mContext,"requestLocationUpdates");
        } else {
//            ToastUtil.showToast(mContext,"NETWORK_PROVIDER----:false");
//            Log.e("MoLin", "LocationManager.GPS_PROVIDER");
            location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                locationHelper.UpdateLastLocation(location);
//                ToastUtil.showToast(mContext,"UpdateLastLocation---gps----location != null");
            }
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    5000, 0, myLocationListener);
//            ToastUtil.showToast(mContext,"gps----requestLocationUpdates");
        }
    }

    private class MyLocationListener implements LocationListener {
        //定位服务状态改变会触发该函数
        @Override
        public void onStatusChanged(String provider, int status,
                                    Bundle extras) {
//            Log.e("MoLin", "onStatusChanged!");
            if (mLocationHelper != null) {
                mLocationHelper.UpdateStatus(provider, status, extras);
            }
        }
        // 定位功能开启时会触发该函数
        @Override
        public void onProviderEnabled(String provider) {
//            Log.e("MoLin", "onProviderEnabled!" + provider);
        }
        // 定位功能关闭时会触发该函数
        @Override
        public void onProviderDisabled(String provider) {
//            Log.e("MoLin", "onProviderDisabled!" + provider);
        }
        // 当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
        @Override
        public void onLocationChanged(Location location) {
//            Log.e("MoLin", "onLocationChanged!");
            if (mLocationHelper != null) {
                mLocationHelper.UpdateLocation(location);
            }
        }
    }


    public static Address getAddress(Context context, double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) return addresses.get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据经纬度获取所在国家
     *
     * @param context   上下文
     * @param latitude  纬度
     * @param longitude 经度
     * @return 所在国家
     */
    public static String getCountryName(Context context, double latitude, double longitude) {
        Address address = getAddress(context, latitude, longitude);
        return address == null ? "unknown" : address.getCountryName();
    }

    /**
     * 根据经纬度获取所在地
     *
     * @param context   上下文
     * @param latitude  纬度
     * @param longitude 经度
     * @return 所在地
     */
    public static String getLocality(Context context, double latitude, double longitude) {
        Address address = getAddress(context, latitude, longitude);
        return address == null ? "unknown" : address.getLocality();
    }

    /**
     * 根据经纬度获取所在街道
     *
     * @param context   上下文
     * @param latitude  纬度
     * @param longitude 经度
     * @return 所在街道
     */
    public static String getStreet(Context context, double latitude, double longitude) {
        Address address = getAddress(context, latitude, longitude);
        return address == null ? "unknown" : address.getAddressLine(0)+address.getAddressLine(1)+address.getAddressLine(2);
    }


    // 移除定位监听
    public void removeLocationUpdatesListener() {
        // 需要检查权限,否则编译不过
        if ( Build.VERSION.SDK_INT >= 23 &&
                ActivityCompat.checkSelfPermission( mContext, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission( mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return  ;
        }
        if (mLocationManager != null) {
            mLocationManager.removeUpdates(myLocationListener);
        }
    }
}
