package com.yidu.sevensecondmall.Activity.UserCenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.DAO.OrderDAO;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.OrderMessage.AddressBean;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.i.IEventTag;
import com.yidu.sevensecondmall.i.RefreshEvent;
import com.yidu.sevensecondmall.views.adapter.AddressAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collection;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/15.
 */
public class AddressActivity extends BaseActivity implements View.OnClickListener {


    TextView tvBackTip;
    RelativeLayout titleLogin;
    RecyclerView addressList;
    TextView title;
    RelativeLayout linear;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    private AddressAdapter adapter;
    ArrayList<AddressBean> list;
    private LinearLayoutManager manager;

    @Override
    protected int setViewId() {
        return R.layout.activity_address;
    }

    @Override
    protected void findViews() {
//        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
        toolbarTitle.setText("地址管理");
        linear = (RelativeLayout) findViewById(R.id.linear);
        linear.setOnClickListener(this);
        addressList = (RecyclerView) findViewById(R.id.address_list);
        manager = new LinearLayoutManager(AddressActivity.this);
        addressList.setLayoutManager(manager);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void init() {
        list = new ArrayList<>();
        adapter = new AddressAdapter(AddressActivity.this);
        OrderDAO.getAddressList(new BaseCallBack() {
            @Override
            public void success(Object data) {
                if (data != null) {
                    list = (ArrayList<AddressBean>) data;
                    if (list.size() > 0) {
//                    list.addAll((Collection<? extends AddressBean>) data);
                        adapter = new AddressAdapter(AddressActivity.this, list);
                        addressList.setAdapter(adapter);
//                    list.addAll((Collection<? extends AddressBean>) data);
//                    adapter.notifyDataSetChanged();
//                    adapter.setData((List<AddressBean>) data);
//                    adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void failed(int errorCode, Object data) {

            }
        });
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        EventBus.getDefault().post(new RefreshEvent(IEventTag.REFRESHADDRESS));
    }

    @Override
    protected void loadData() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void center(RefreshEvent ev) {
        switch (ev.founctionTag) {
            case IEventTag.REFRESHADDRESS:
                OrderDAO.getAddressList(new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        if (data != null) {
                            if (list.size() == 0 || list == null) {
                                list = (ArrayList<AddressBean>) data;
                                adapter = new AddressAdapter(AddressActivity.this, list);
                                addressList.setAdapter(adapter);
                            } else {
                                list.clear();
//                            adapter.setData((List<AddressBean>) data);
//                            ArrayList<AddressBean> list2 = (ArrayList<AddressBean>)data;
                                list.addAll((Collection<? extends AddressBean>) data);
                                adapter.notifyDataSetChanged();
                            }
                        }

                    }

                    @Override
                    public void failed(int errorCode, Object data) {

                    }
                });
                break;
        }
    }


    @OnClick({R.id.linear})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linear:
                Intent i = new Intent(AddressActivity.this, setAddressActivity.class);
                i.putExtra("type", 0);
                startActivity(i);
                break;
        }


    }

    @Override
    protected void onDestroy() {
//        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

}
