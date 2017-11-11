package com.yidu.sevensecondmall.Activity.UserCenter;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.Activity.Order.SearchActivity;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.networks.HttpApi;
import com.yidu.sevensecondmall.utils.LoginUtils;
import com.yidu.sevensecondmall.utils.OkHttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/10/25.
 */

public class RechargeGameActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.balance)
    TextView balance;
    @BindView(R.id.ll_yue)
    LinearLayout llYue;
    @BindView(R.id.ll_ding)
    LinearLayout llDing;
    @BindView(R.id.ll_card)
    LinearLayout llCard;
    String user_money;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;

    @Override
    protected int setViewId() {
        return R.layout.layout_rechargegame;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
        toolbarTitle.setText("游戏充值");
        toolbarRight.setVisibility(View.VISIBLE);
        toolbarRight.setText("明细");
    }

    @Override
    protected void initEvent() {
        llYue.setOnClickListener(this);
        llDing.setOnClickListener(this);
        llCard.setOnClickListener(this);
        toolbarRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RechargeGameActivity.this, GameGoldDetailActivity.class));

            }
        });
    }

    @Override
    protected void init() {

    }


    @Override
    protected void loadData() {

        HashMap<String, String> params = new HashMap<>();
        params.put("token", LoginUtils.getToken());
        params.put("userid", LoginUtils.getUserId());
        OkHttpUtil.postSubmitForm(HttpApi.getUserMoney, params, new OkHttpUtil.OnDownDataListener() {
            @Override
            public void onResponse(String url, String json) {
                Log.e("获取用户余额", url);
                Log.e("获取用户余额的Json", json);
                try {
                    JSONObject object = new JSONObject(json);
                    JSONObject result = object.getJSONObject("result");
                    String ding_coin = result.getString("ding_coin");
                    user_money = result.getString("user_money");
                    String daxiong_coin = result.getString("daxiong_coin");
                    balance.setText(daxiong_coin);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String url, String error) {

            }
        });
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                balance.setText(LoginUtils.getDaxiong_coin());
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.ll_yue:
                intent = new Intent(this, BalanceRechargeActivity.class);
                intent.putExtra("price", user_money);
                startActivityForResult(intent, 1);
                break;
            case R.id.ll_ding:
                intent = new Intent(this, DingRechargeActivity.class);
                startActivityForResult(intent, 2);
                break;
            case R.id.ll_card:
                intent = new Intent( this, SearchActivity.class);
                intent.putExtra("keyword","充值卡");
                startActivity(intent);
                break;
        }
    }
}
