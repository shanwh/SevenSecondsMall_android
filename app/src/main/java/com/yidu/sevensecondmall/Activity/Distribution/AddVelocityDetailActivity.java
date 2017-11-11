package com.yidu.sevensecondmall.Activity.Distribution;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.Activity.Order.GoodsDetailActivity;
import com.yidu.sevensecondmall.Activity.Order.OrderDetailActivity;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Distribution.AddVelocityDetailBean;
import com.yidu.sevensecondmall.networks.HttpApi;
import com.yidu.sevensecondmall.utils.LoginUtils;
import com.yidu.sevensecondmall.utils.OkHttpUtil;
import com.yidu.sevensecondmall.utils.ToastUtil;
import com.yidu.sevensecondmall.views.adapter.RecommendAdapter;
import com.yidu.sevensecondmall.views.adapter.RecyclerAdapter;
import com.yidu.sevensecondmall.views.widget.DensityUtils;
import com.yidu.sevensecondmall.views.widget.itemdecorator.GridSpacingItemDecoration;
import com.yidu.sevensecondmall.views.widget.widget.CircularProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/11/2.
 */

public class AddVelocityDetailActivity extends BaseActivity {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.circular_progress_bar)
    CircularProgressBar circularProgressBar;
    @BindView(R.id.progress_num)
    TextView progressNum;
    @BindView(R.id.rl_progress)
    RelativeLayout rlProgress;
    @BindView(R.id.current_progress)
    TextView currentProgress;
    @BindView(R.id.ratio_people)
    TextView ratioPeople;
    @BindView(R.id.ratio_price)
    TextView ratioPrice;
    @BindView(R.id.message_tv)
    LinearLayout messageTv;
    @BindView(R.id.ding_tv)
    TextView dingTv;
    @BindView(R.id.tv_ding)
    EditText tvDing;
    @BindView(R.id.all_recharge)
    TextView allRecharge;
    @BindView(R.id.sure)
    TextView sure;
    @BindView(R.id.recommend_rv)
    RecyclerView recommendRv;
    @BindView(R.id.tv)
    TextView tv;

    private RecommendAdapter adapter;

    float currentprogress = 0f;
    float ratio;
    String order_id;

    @Override
    protected int setViewId() {
        return R.layout.activity_add_velocity;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
        toolbarTitle.setText("赠品加速");
        order_id = getIntent().getStringExtra("order_id");
        circularProgressBar.setCircleWidth(DensityUtils.dip2px(this, 10));
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvDing.getText().toString().equals("") || tvDing.getText().toString().equals("0")) {
                    ToastUtil.showToast(AddVelocityDetailActivity.this, "请输入要加速的DING宝数");
                } else if (Integer.valueOf(tvDing.getText().toString()) > Float.valueOf(price_ding)) {
                    ToastUtil.showToast(AddVelocityDetailActivity.this, "您输入的DING数大于了账户余额");
                } else {
//                    // TODO: 2017/11/2   接口请求加速
                    sureDate();
                }
            }
        });
        adapter = new RecommendAdapter(this);
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recommendRv.addItemDecoration(new GridSpacingItemDecoration(2, DensityUtils.dip2px(this, 12), false));
        recommendRv.setLayoutManager(manager);
        recommendRv.setAdapter(adapter);
        allRecharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvDing.setText((int) Math.floor(Double.valueOf(price_ding)) + "");
            }
        });
        adapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                startActivity(new Intent(AddVelocityDetailActivity.this, GoodsDetailActivity.class).putExtra("id",Integer.valueOf(adapter.getItem(position).getGoods_id())));
            }
        });


    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void init() {

    }


    @Override
    protected void loadData() {
        Intent intent = getIntent();
        String order_id = intent.getStringExtra("order_id");
        if (!order_id.equals("") && order_id != null) {
            Log.e("order_id", order_id);
            initData(order_id);
        }

    }

    private String price_ding;

    private void initData(String order_id) {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", LoginUtils.getToken());
        params.put("order_id", order_id);
        OkHttpUtil.postSubmitForm(HttpApi.getQuickOrderDetail, params, new OkHttpUtil.OnDownDataListener() {
            @Override
            public void onResponse(String url, String json) {
                Log.e("赠品加速详情json", json);
                AddVelocityDetailBean bean = new Gson().fromJson(json, AddVelocityDetailBean.class);
                if (bean.getCode() == 0) {
                    if (bean.getResult().getData() != null) {
                        AddVelocityDetailBean.ResultBean.DataBean dataBean = bean.getResult().getData();
                        progressNum.setText(dataBean.getRedis_rate() + "%");
                        circularProgressBar.setProgress(Float.valueOf(dataBean.getRedis_rate()));
                        price_ding = dataBean.getDing_coin();
                        currentProgress.setText(dataBean.getRedis_rate() + "%");
                        ratioPeople.setText(dataBean.getUser_rate() + "%");
                        ratioPrice.setText(dataBean.getRedis_amount());
//                        当前折扣：5折，20天后降为4折
                        tv.setText("当前折扣：" + dataBean.getDiscount().getNow_discount() + "折，" + dataBean.getDiscount().getLeft_days() + "天后降为" + dataBean.getDiscount().getLeft_discount() + "折");
                        dingTv.setText(dataBean.getDing_coin());
                        adapter.addAll(bean.getResult().getRandomGoods());
                    }
                } else {
                    ToastUtil.showToast(AddVelocityDetailActivity.this, bean.getMessage());
                }

            }

            @Override
            public void onFailure(String url, String error) {

            }
        });


    }

    float fixed = 0f;
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            ratio = (float) msg.obj;
            ratio = ((float) (Math.round(ratio * 100)) / 100);
            circularProgressBar.setProgress(ratio);
            progressNum.setText(ratio + "%");
            if (ratio >= currentprogress) {
                circularProgressBar.setProgress(currentprogress);
                progressNum.setText(currentprogress + "%");
                sure.setClickable(true);
            } else {
                Message message = mHandler.obtainMessage();
                message.obj = ratio + fixed;
                message.what = 2;
                sure.setClickable(false);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mHandler.sendMessage(message);

            }
            super.handleMessage(msg);
        }

    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeMessages(1);
    }

    private void sureDate() {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", LoginUtils.getToken());
        params.put("order_id", order_id);
        params.put("ding_coin", tvDing.getText().toString());
        OkHttpUtil.postSubmitForm(HttpApi.DingCoinToIncreaseOrderRate, params, new OkHttpUtil.OnDownDataListener() {
            @Override
            public void onResponse(String url, String json) {
                Log.e("确认加速的json", json);
                try {
                    JSONObject object = new JSONObject(json);
                    int code = object.getInt("code");
                    if (code == 0) {
                        // TODO: 2017/11/10 成功
                        JSONObject result = object.getJSONObject("result");
                        ToastUtil.showToast(AddVelocityDetailActivity.this, object.getString("message"));
                        currentprogress = Float.valueOf(result.getString("new_gear_rate"));
                        //当前进度
                        currentProgress.setText(currentprogress+"%");
                        //个人加速占比
                        ratioPeople.setText(currentprogress+"");
                        //剩余加速金额
                        ratioPrice.setText("00000");
                        //修改ding宝数量
                        dingTv.setText(price_ding);
                        //清空输入的数据
                        tvDing.setText("");

                        circularProgressBar.setProgress(0);
                        ratio = (Float.valueOf(result.getString("new_gear_rate"))) / 10;
                        fixed = ratio;
                        Message message = mHandler.obtainMessage();
                        message.obj = ratio;
                        message.what = 1;
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        mHandler.sendMessage(message);
                    } else {
                        ToastUtil.showToast(AddVelocityDetailActivity.this, object.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(String url, String error) {

            }
        });
    }

}
