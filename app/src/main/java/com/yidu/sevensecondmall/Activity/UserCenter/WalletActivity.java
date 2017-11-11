package com.yidu.sevensecondmall.Activity.UserCenter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.se7en.utils.SystemUtil;
import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.DAO.UserDAO;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.User.ServerBean;
import com.yidu.sevensecondmall.bean.User.UserBean;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.CustomRecyclerView;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.MultiTypeAdapter;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.Visitable;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/10.
 * 钱包activity
 */
public class WalletActivity extends BaseActivity {

    @BindView(R.id.back_button)
    ImageView backButton;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.title_login)
    RelativeLayout titleLogin;
    @BindView(R.id.socre)
    LinearLayout socre;
    @BindView(R.id.money)
    LinearLayout money;
    @BindView(R.id.card)
    LinearLayout card;
    //    @BindView(R.id.service)
//    GridView service;
    @BindView(R.id.v)
    View v;
    @BindView(R.id.tv_back_tip)
    TextView tvBackTip;
    @BindView(R.id.tv_user_money)
    TextView tvUserMoney;
    @BindView(R.id.custom_rv)
    CustomRecyclerView custom_rv;

    int currentPage = 1;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    private int[] iconList = new int[]{R.string.icon_phone_pay, R.string.icon_movie, R.string.icon_white_line, R.string.icon_life_pay, R.string.icon_buy_air, R.string.icon_back_money};
    private int[] colorList = new int[]{R.color.color_phone_pay, R.color.color_movie, R.color.color_white_line, R.color.color_life_pay, R.color.color_buy_air, R.color.color_back_money};
    private int[] textList = new int[]{R.string.str_phone_pay, R.string.str_movie, R.string.str_white_line, R.string.str_life_pay, R.string.str_buy_air, R.string.str_back_money};

    @Override
    protected int setViewId() {
        return R.layout.activity_wallet;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
        titleName.setText("钱包");
        toolbarTitle.setText("钱包");
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void init() {

    }

    @Override
    protected void loadData() {
        ArrayList<Visitable> list = new ArrayList<>();
        for (int i = 0; i < iconList.length; i++) {
            ServerBean bean = new ServerBean();
            bean.setIcon(iconList[i]);
            bean.setColor(colorList[i]);
            bean.setText(textList[i]);
            list.add(bean);
        }
        MultiTypeAdapter adapter = custom_rv.getAdapter();
        if (currentPage == 1) {
            if (list.size() == 0) {
                custom_rv.showEmptyView();
            } else {
                custom_rv.hideEmptyView();
            }
            adapter.refreshData(list);
            if (custom_rv.isRefreshing()) {
                showShortToast("刷新成功");
            }
            custom_rv.stopSwipeRefresh();
        } else {
            adapter.addMoreData(list);
        }
    }

    @Override
    protected void onResume() {
        setValue();
        super.onResume();
    }

    @OnClick({R.id.socre, R.id.money, R.id.card, R.id.back})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.socre:
                showShortToast("正在建设中,敬请期待");
                break;
            case R.id.money:
                intent = new Intent(WalletActivity.this, MyMoneyActivity.class);
                startActivity(intent);
                break;
            case R.id.card:
                intent = new Intent(this, BankCardActivity.class);
                SystemUtil.setSharedInt("BankCardListFrom", 0);
                startActivity(intent);
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    private void setValue() {
        UserDAO.getUserInfo(new BaseCallBack() {
            @Override
            public void success(Object data) {
                if (data != null) {
                    UserBean bean = (UserBean) data;
                    String user_money = bean.getUser_money();
                    tvUserMoney.setText(user_money);
                }
            }

            @Override
            public void failed(int errorCode, Object data) {
                showShortToast("" + data);
            }
        });
    }

}
