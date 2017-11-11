package com.yidu.sevensecondmall.Activity.Order;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.DAO.GoodsDAO;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Order.CartlistBean;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.i.PriceEvent;
import com.yidu.sevensecondmall.utils.LoginUtils;
import com.yidu.sevensecondmall.utils.SpecHelper;
import com.yidu.sevensecondmall.views.adapter.trolleyAdapter;
import com.yidu.sevensecondmall.views.widget.iconfonts.IconFontTextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/10.
 * 购物车activity
 */
public class TrolleyActivity extends BaseActivity {

    @BindView(R.id.back_button)
    ImageView backButton;
    @BindView(R.id.tv_back_tip)
    TextView tvBackTip;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.title_login)
    RelativeLayout titleLogin;
    @BindView(R.id.change)
    TextView change;
    @BindView(R.id.title)
    RelativeLayout title;
    @BindView(R.id.car_list)
    RecyclerView carList;
    @BindView(R.id.select_all)
    LinearLayout selectAll;
    @BindView(R.id.tv_all)
    TextView tvAll;
    @BindView(R.id.tv_rmb)
    TextView tvRmb;
    @BindView(R.id.total_price)
    TextView totalPrice;
    @BindView(R.id.toalcount2)
    TextView toalcount2;
    @BindView(R.id.total)
    RelativeLayout total;
    @BindView(R.id.select_state)
    IconFontTextView selectState;
    @BindView(R.id.tip)
    LinearLayout tip;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    private boolean state = false;
    private boolean allstate = false;
    private JSONArray array;
    private SpecHelper specHelper = SpecHelper.getInstance();
    private ProgressDialog dialog;


    private CartlistBean bean = new CartlistBean();
    private List<CartlistBean.CartListBean> list = new ArrayList<CartlistBean.CartListBean>();

    private trolleyAdapter adapter;
    private ArrayList<String> passwordList = new ArrayList<>();

    public TrolleyActivity() {
//        EventBus.getDefault().register(this);
    }

    @Override
    protected int setViewId() {
        return R.layout.activity_trolley;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void init() {
        dialog = new ProgressDialog(TrolleyActivity.this);
        dialog.setMessage("同步数据中");
        LinearLayoutManager layoutManager = new LinearLayoutManager(TrolleyActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        carList.setLayoutManager(layoutManager);
        toolbarTitle.setText("购物车");
        toolbarRight.setText("编辑");
        toolbarRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adapter != null) {
                    state = !state;
                    adapter.setEditState(state);
                }
            }
        });
        titleName.setText("购物车");
        selectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!allstate) {
                    allstate = true;
                    selectState.setText(R.string.icon_select);
                    selectState.setTextColor(Color.RED);
                } else {
                    allstate = false;
                    selectState.setText(R.string.icon_un_select);
                    selectState.setTextColor(Color.GRAY);
                }
                adapter.changeAllState(allstate);
                for (int i = 0; i < array.length(); i++) {
                    try {
                        JSONObject obj = array.getJSONObject(i);
                        obj.put("selected", "1");
                        array.put(i, obj);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                //同步信息
                dialog.show();
                if (LoginUtils.isLogin()) {
                    GoodsDAO.sendCartJsonWithToken(array.toString(), new BaseCallBack() {
                        @Override
                        public void success(Object data) {
                            dialog.dismiss();
                        }

                        @Override
                        public void failed(int errorCode, Object data) {
                            dialog.dismiss();
                        }
                    });
                } else {
                    GoodsDAO.sendCartJsonWithOutToken(array.toString(), new BaseCallBack() {
                        @Override
                        public void success(Object data) {
                            dialog.dismiss();
                        }

                        @Override
                        public void failed(int errorCode, Object data) {
                            dialog.dismiss();
                        }
                    });
                }

            }
        });

//        selectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                adapter.changeAllState(isChecked);
//            }
//        });
    }

    @Override
    protected void loadData() {
        if (!LoginUtils.isLogin()) {
            GoodsDAO.getCartList(new BaseCallBack() {
                @Override
                public void success(Object data) {
                    if (data != null) {
                        bean = (CartlistBean) data;
                        list = bean.getCartList();
                        if (list.size() == 0) {
                            tip.setVisibility(View.VISIBLE);
                            total.setVisibility(View.INVISIBLE);
                        } else {
                            tip.setVisibility(View.INVISIBLE);
                            total.setVisibility(View.VISIBLE);
                        }
                        array = bean.getArray();
                        specHelper.setJson(array);
                        adapter = new trolleyAdapter(TrolleyActivity.this, list);
                        carList.setAdapter(adapter);
                    }
                }

                @Override
                public void failed(int errorCode, Object data) {
                    Toast.makeText(TrolleyActivity.this, "" + data, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            GoodsDAO.getCartListWithToken(new BaseCallBack() {
                @Override
                public void success(Object data) {
                    if (data != null) {
                        bean = (CartlistBean) data;
                        list = bean.getCartList();
                        if (list.size() == 0) {
                            tip.setVisibility(View.VISIBLE);
                        } else {
                            tip.setVisibility(View.INVISIBLE);
                        }
                        array = bean.getArray();
                        specHelper.setJson(array);
                        adapter = new trolleyAdapter(TrolleyActivity.this, list);
                        carList.setAdapter(adapter);
                    }
                }

                @Override
                public void failed(int errorCode, Object data) {
                    Toast.makeText(TrolleyActivity.this, "" + data, Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handlerCenter(PriceEvent event) {
//        adapter.changesingleState(event.founctionTag);
        if (!event.tag.equals("") && event.tag.equals("refresh")) {
            tip.setVisibility(View.VISIBLE);
        } else if (adapter != null && event.founctionTag > -1) {
            double price = adapter.getTotalPrice();
            int count = adapter.getCount();
            totalPrice.setText("" + price);
            toalcount2.setText("结算(" + count + ")");
            Log.e("price", price + "");
            Log.e("count", count + "");
        }

    }

    @Override
    public void onDestroy() {
//        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public Activity getActivity() {
        return TrolleyActivity.this;
    }

    @OnClick({R.id.back, R.id.change, R.id.select_all, R.id.toalcount2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.change:
                if (adapter != null) {
                    state = !state;
                    adapter.setEditState(state);
                }
                break;
            case R.id.select_all:

                break;
            case R.id.toalcount2:
//                showPopupWindow(view);
                Intent finish = new Intent(TrolleyActivity.this, OrderSureActivity.class);
                startActivity(finish);
                break;

        }
    }

}
