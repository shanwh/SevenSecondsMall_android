package com.yidu.sevensecondmall.Activity.UserCenter;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.utils.LocationHelper;
import com.yidu.sevensecondmall.utils.LocationUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/14.
 */
public class AreaActivity extends BaseActivity {


    @BindView(R.id.back_button)
    ImageView backButton;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.title_login)
    RelativeLayout titleLogin;
    @BindView(R.id.area)
    TextView area;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    private double lng;
    private double lat;
    private String[] mNeedPermissionsList = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    private ProgressDialog dialog;
    private LocationUtils locationUtils;

    @Override
    protected int setViewId() {
        return R.layout.activity_area;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
        dialog = new ProgressDialog(AreaActivity.this);
        dialog.setMessage("正在定位中");
        toolbarTitle.setText("地区");
    }

    @Override
    protected void initEvent() {
        if (ContextCompat.checkSelfPermission(AreaActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(AreaActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED && GPSIsOPen(AreaActivity.this)) {
            dialog.show();
            locationUtils = LocationUtils.getInstance(AreaActivity.this);
            locationUtils.initLocation(new LocationHelper() {
                @Override
                public void UpdateLocation(Location location) {
                    lat = location.getLatitude();
                    lng = location.getLongitude();
                    String add = locationUtils.getStreet(AreaActivity.this, lat, lng);

                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }

                @Override
                public void UpdateStatus(String provider, int status, Bundle extras) {

                }

                @Override
                public void UpdateGPSStatus(GpsStatus pGpsStatus) {

                }

                @Override
                public void UpdateLastLocation(Location location) {

                }
            });
        } else {
            Snackbar.make(AreaActivity.this.findViewById(android.R.id.content), "需要gps和网络权限或者要打开gps定位和打开网络", Snackbar.LENGTH_LONG)
                    .setAction("去设置", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", AreaActivity.this.getPackageName(), null);
                            intent.setData(uri);
                            AreaActivity.this.startActivityForResult(intent, 1);
                        }
                    })
                    .show();
        }

    }

    @Override
    protected void init() {

    }

    @Override
    protected void loadData() {

    }

    public static final boolean GPSIsOPen(final Context context) {
        LocationManager locationManager
                = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        }
        return false;
    }


    @OnClick(R.id.back)
    public void onClick() {
        finish();
    }

}
