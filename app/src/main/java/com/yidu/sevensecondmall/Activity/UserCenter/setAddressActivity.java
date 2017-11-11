package com.yidu.sevensecondmall.Activity.UserCenter;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.se7en.utils.SystemUtil;
import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.DAO.OrderDAO;
import com.yidu.sevensecondmall.DAO.PositionDao;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.OrderMessage.AddressBean;
import com.yidu.sevensecondmall.bean.User.CityModel;
import com.yidu.sevensecondmall.bean.User.DistrictModel;
import com.yidu.sevensecondmall.bean.User.ProvinceModel;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.i.IEventTag;
import com.yidu.sevensecondmall.i.RefreshEvent;
import com.yidu.sevensecondmall.views.widget.AddressAlertDialog;
import com.yidu.sevensecondmall.views.widget.widget.OnWheelChangedListener;
import com.yidu.sevensecondmall.views.widget.widget.WheelView;
import com.yidu.sevensecondmall.views.widget.widget.adapters.ArrayWheelAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/8.
 */
public class setAddressActivity extends BaseActivity {
    private static final String TAG = "setAddressActivity";

    @BindView(R.id.back_button)
    ImageView backButton;
    @BindView(R.id.tv_back_tip)
    TextView tvBackTip;
    @BindView(R.id.back)
    LinearLayout back;
    //    @BindView(R.id.title_login)
//    RelativeLayout titleLogin;
    @BindView(R.id.save)
    TextView save;
    @BindView(R.id.name_edit)
    EditText nameEdit;
    @BindView(R.id.tel_edit)
    EditText telEdit;
    @BindView(R.id.select_name)
    TextView selectName;
    @BindView(R.id.add_edit)
    TextView addEdit;
    @BindView(R.id.detail_txt)
    EditText detailTxt;
    @BindView(R.id.post_edit)
    EditText postEdit;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.rl_head)
    RelativeLayout rlHead;

    private int provinceid = 0;
    private int cityid = 0;
    private int districtid = 0;
    private WheelView mViewProvince;
    private WheelView mViewCity;
    private WheelView mViewDistrict;
    private List<ProvinceModel> provincelist = new ArrayList<>();

    /**
     * 所有省
     */
    protected String[] mProvinceDatas;
    /**
     * key - 省 value - 市
     */
    protected Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
    /**
     * key - 市 values - 区
     */
    protected Map<String, String[]> mDistrictDatasMap = new HashMap<String, String[]>();

    /**
     * key - 区 values - 邮编
     */
    protected Map<String, String> mZipcodeDatasMap = new HashMap<String, String>();

    /**
     * 当前省的名称
     */
    protected String mCurrentProviceName;
    /**
     * 当前市的名称
     */
    protected String mCurrentCityName;
    /**
     * 当前区的名称
     */
    protected String mCurrentDistrictName = "";

    /**
     * 当前区的邮政编码
     */
    protected String mCurrentZipCode = "";
    private int type = 0;

    private Activity context;
    private String addressId;

    @Override
    protected int setViewId() {
        return R.layout.myaddress_layout;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
        context = setAddressActivity.this;
        if (getIntent().hasExtra("type")) {
            if (getIntent().getIntExtra("type", 0) == 0) {
                type = 0;
//                titleName.setText("新增地址");
                toolbarTitle.setText("新增地址");
            } else {
                type = 1;
//                titleName.setText("编辑地址");
                toolbarTitle.setText("编辑地址");
            }
        }
//        EventBus.getDefault().register(this);
    }

    @Override
    protected void initEvent() {


    }

    @Override
    protected void init() {
        AddressBean addressBean = getIntent().getParcelableExtra("addressBean");
        if (addressBean == null) return;
        addressId = addressBean.getAddress_id();
        nameEdit.setText(addressBean.getConsignee());
        int length = nameEdit.getText().length();
        nameEdit.setSelection(length);
        telEdit.setText(addressBean.getMobile());
        Log.d(TAG, "init:  " + addressBean.getMergeName());
        String mergeName = addressBean.getMergeName();
        String[] split = mergeName.split(",");
        String mergeNameStr = "";
        Log.d(TAG, "init: split.length " + split.length);
        for (int i = 0; i < split.length; i++) {
            if (i != 0) {
                mergeNameStr = mergeNameStr + split[i] + " ";
            }
        }
        addEdit.setText(mergeNameStr);
        detailTxt.setText(addressBean.getAddress());
        provinceid = Integer.parseInt(addressBean.getProvince());
        cityid = Integer.parseInt(addressBean.getCity());
        districtid = Integer.parseInt(addressBean.getDistrict());
    }

    @Override
    protected void loadData() {

    }

    private View showDialog() {
        View contentView = LayoutInflater.from(this).inflate(
                R.layout.view_dialog_selectzone, null);
        setUpViews(contentView);
        setUpData();
        return contentView;
    }

    private void setUpViews(View view) {
        mViewProvince = (WheelView) view.findViewById(R.id.id_province);
        mViewCity = (WheelView) view.findViewById(R.id.id_city);
        mViewDistrict = (WheelView) view.findViewById(R.id.id_district);
    }

    private void setUpData() {
        initProvinceDatas();
        mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(this, mProvinceDatas));
        // 设置可见条目数量
        mViewProvince.setVisibleItems(7);
        mViewCity.setVisibleItems(7);
        mViewDistrict.setVisibleItems(7);
        setEvent();
        updateCities();
        updateAreas();
    }

    private void setEvent() {
        mViewProvince.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                if (wheel == mViewProvince) {
                    updateCities();
                } else if (wheel == mViewCity) {
                    updateAreas();
                } else if (wheel == mViewDistrict) {
                    mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
                    mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
                }
            }
        });
        mViewCity.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                if (wheel == mViewProvince) {
                    updateCities();
                } else if (wheel == mViewCity) {
                    updateAreas();
                } else if (wheel == mViewDistrict) {
                    mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
                    mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
                }
            }
        });

        mViewDistrict.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                if (wheel == mViewProvince) {
                    updateCities();
                } else if (wheel == mViewCity) {
                    updateAreas();
                } else if (wheel == mViewDistrict) {
                    mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
                    mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
                }
            }
        });
    }

    /**
     * 根据当前的市，更新区WheelView的信息
     */
    private void updateAreas() {
        try {

            int pCurrent = mViewCity.getCurrentItem();
            mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
            String[] areas = mDistrictDatasMap.get(mCurrentCityName);

            if (areas == null) {
                areas = new String[]{""};
            }
            mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(this, areas));
//        mViewDistrict.setCurrentItem(0);
            mViewDistrict.setCurrentItem(0);
            mCurrentDistrictName = areas[0];

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据当前的省，更新市WheelView的信息
     */
    private void updateCities() {
        try {
            int pCurrent = mViewProvince.getCurrentItem();
            mCurrentProviceName = mProvinceDatas[pCurrent];
            String[] cities = mCitisDatasMap.get(mCurrentProviceName);
            if (cities == null) {
                cities = new String[]{""};
            }
            mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(this, cities));
            mViewCity.setCurrentItem(0);
            updateAreas();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void initProvinceDatas() {
        List<ProvinceModel> provinceList = null;
        try {
            provinceList = PositionDao.getInstance().list;
            Log.d(TAG, "initProvinceDatas: " + provinceList.size());
            //*/ 初始化默认选中的省、市、区
            if (provinceList != null && !provinceList.isEmpty()) {
                mCurrentProviceName = provinceList.get(0).getName();

                List<CityModel> cityList = provinceList.get(0).getCityList();
                if (cityList != null && !cityList.isEmpty()) {
                    mCurrentCityName = cityList.get(0).getName();
                    List<DistrictModel> districtList = cityList.get(0).getDistrictList();
                    mCurrentDistrictName = districtList.get(0).getName();
                    mCurrentZipCode = districtList.get(0).getZipcode();
                }
            } else {
                Toast.makeText(context, "城市列表为空", Toast.LENGTH_SHORT).show();
            }
            //*/
            mProvinceDatas = new String[provinceList.size()];
            for (int i = 0; i < provinceList.size(); i++) {
                // 遍历所有省的数据
                mProvinceDatas[i] = provinceList.get(i).getName();
                List<CityModel> cityList = provinceList.get(i).getCityList();
                String[] cityNames = new String[cityList.size()];
                for (int j = 0; j < cityList.size(); j++) {
                    // 遍历省下面的所有市的数据
                    cityNames[j] = cityList.get(j).getName();
                    List<DistrictModel> districtList = cityList.get(j).getDistrictList();
                    String[] distrinctNameArray = new String[districtList.size()];
                    DistrictModel[] distrinctArray = new DistrictModel[districtList.size()];
                    for (int k = 0; k < districtList.size(); k++) {
                        // 遍历市下面所有区/县的数据
                        DistrictModel districtModel = new DistrictModel(districtList.get(k).getName(), districtList.get(k).getZipcode());
                        // 区/县对于的邮编，保存到mZipcodeDatasMap
                        mZipcodeDatasMap.put(districtList.get(k).getName(), districtList.get(k).getZipcode());
                        distrinctArray[k] = districtModel;
                        distrinctNameArray[k] = districtModel.getName();
                    }
                    // 市-区/县的数据，保存到mDistrictDatasMap
                    mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
                }
                // 省-市的数据，保存到mCitisDatasMap
                mCitisDatasMap.put(provinceList.get(i).getName(), cityNames);
                provincelist = provinceList;
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {

        }
    }


    @OnClick({R.id.back_button, R.id.save, R.id.add_edit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_button:
                finish();
                break;
            case R.id.save:
                if (type == 0) {
                    addressId = "0";
                } else {
                    if (addressId == null) addressId = "0";
                }
                OrderDAO.addAddress(addressId, nameEdit.getText().toString(), provinceid, districtid, cityid, detailTxt.getText().toString(),
                        telEdit.getText().toString(), new BaseCallBack() {
                            @Override
                            public void success(Object data) {
                                save.setClickable(false);
                                if (type == 0) {
                                    Toast.makeText(context, "保存成功", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "编辑成功", Toast.LENGTH_SHORT).show();
                                }
                                EventBus.getDefault().post(new RefreshEvent(IEventTag.REFRESHADDRESS));
                                finish();
                            }

                            @Override
                            public void failed(int errorCode, Object data) {
                                Toast.makeText(SystemUtil.getContext(), "" + data, Toast.LENGTH_SHORT).show();
                            }
                        });

                break;
            case R.id.add_edit:
                View dialogView = showDialog();
                final AddressAlertDialog dialog = new AddressAlertDialog(setAddressActivity.this)
                        .builder().setView(dialogView).setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                for (int i = 0; i < provincelist.size(); i++) {
                                    if (provincelist.get(i).getName().equals(mCurrentProviceName)) {
                                        provinceid = provincelist.get(i).getId();

                                        for (int j = 0; j < provincelist.get(i).getCityList().size(); j++) {
                                            if (provincelist.get(i).getCityList().get(j).getName().equals(mCurrentCityName)) {
                                                cityid = provincelist.get(i).getCityList().get(j).getId();

                                                for (int k = 0; k < provincelist.get(i).getCityList().get(j).getDistrictList().size();
                                                     k++) {
                                                    if (provincelist.get(i).getCityList().get(j).getDistrictList().get(k).getName().equals(mCurrentDistrictName)) {
                                                        districtid = provincelist.get(i).getCityList().get(j).getDistrictList().get(k).getId();
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                Log.e("id", "provinceid:" + provinceid + " cityid:" + cityid + " districtid:" + districtid);
                                addEdit.setText(mCurrentProviceName + " " + mCurrentCityName + " "
                                        + mCurrentDistrictName);

                            }
                        });
                dialog.setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                dialog.show();
                break;
        }
    }

}
