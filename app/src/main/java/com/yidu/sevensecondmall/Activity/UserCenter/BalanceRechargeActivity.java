package com.yidu.sevensecondmall.Activity.UserCenter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.i.PaySuccess;
import com.yidu.sevensecondmall.utils.LoginUtils;
import com.yidu.sevensecondmall.utils.PayPopWindow;
import com.yidu.sevensecondmall.utils.ToastUtil;
import com.yidu.sevensecondmall.views.adapter.RecyclerAdapter;
import com.yidu.sevensecondmall.views.adapter.RecyclerViewAdapter;
import com.yidu.sevensecondmall.views.widget.itemdecorator.GridSpacingItemDecoration;
import com.yidu.sevensecondmall.views.widget.widget.BaseHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/10/26.
 */

public class BalanceRechargeActivity extends BaseActivity implements PaySuccess {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    @BindView(R.id.user_money)
    TextView userMoney;
    @BindView(R.id.bt_next)
    TextView btNext;
    @BindView(R.id.recycle)
    RecyclerView recycle;
    private MyAdapter receviceAdapter;

    @Override
    protected int setViewId() {

        return R.layout.activity_balancerecharge;
    }

    private String select_price = "50";

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
        toolbarTitle.setText("购买金币");
        receviceAdapter = new MyAdapter();
        recycle.setLayoutManager(new GridLayoutManager(this, 3));
        recycle.addItemDecoration(new GridSpacingItemDecoration(3, 6, false));
        recycle.setAdapter(receviceAdapter);
    }

    @Override
    protected void initEvent() {
        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(Integer.valueOf(intent_price)< Integer.valueOf(select_price)){
//                    ToastUtil.showToast(BalanceRechargeActivity.this, "余额不足");
//                }else{
                PayPopWindow popWindow = new PayPopWindow(BalanceRechargeActivity.this, btNext, select_price, true, "cur_money", BalanceRechargeActivity.this);
                popWindow.showPopupWindowPassWord();

//                }
            }
        });
        receviceAdapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                select_price = receviceAdapter.getItem(position);

                for (int i = 0; i < flagList.size(); i++) {
                    flagList.set(i, false);
                }
                flagList.set(position, true);
                receviceAdapter.notifyDataSetChanged();

            }
        });
    }

    List<String> ad;
    List<Boolean> flagList;

    private void initData() {
        ad = new ArrayList<>();
        ad.add(String.valueOf(50));
        ad.add(String.valueOf(100));
        ad.add(String.valueOf(300));
        ad.add(String.valueOf(500));
        ad.add(String.valueOf(1000));
        ad.add(String.valueOf(3000));
        ad.add(String.valueOf(5000));
        ad.add(String.valueOf(10000));
        receviceAdapter.addAll(ad);
        flagList = new ArrayList<>();
        for (int i = 0; i < ad.size(); i++) {
            if (i == 0) {
                flagList.add(true);
            } else {
                flagList.add(false);
            }

        }


    }

    String intent_price;

    @Override
    protected void init() {
        intent_price = getIntent().getStringExtra("price");
        userMoney.setText("（¥ " + intent_price + "）");
        initData();
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void paySuccess() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e("支付完成剩余的钱", LoginUtils.getUser_money());
                userMoney.setText("（¥ " + LoginUtils.getUser_money() + "）");
            }
        });
    }


    class MyAdapter extends RecyclerViewAdapter<String, MyAdapter.HolderView> {
        @Override
        public int getLayoutResId() {
            return R.layout.item_balance;
        }

        @Override
        public HolderView getViewHolder(View view) {
            return new HolderView(view);
        }

        @Override
        public void onBindViewHolder(final HolderView holder, final int position) {
            final String price = getItem(position);
            int gold = Integer.valueOf(price);
            holder.price.setText(price + "元");
            holder.gold.setText(gold * 100 + "金币");
            if (flagList.get(position)) {
                holder.price.setTextColor(getResources().getColor(R.color.textColor));
                holder.gold.setTextColor(getResources().getColor(R.color.textColor));
                holder.linearlayout.setBackground(getResources().getDrawable(R.drawable.item_bg_f1));
            } else {
                holder.price.setTextColor(getResources().getColor(R.color.textColor_bg));
                holder.gold.setTextColor(getResources().getColor(R.color.textColor_bg));
                holder.linearlayout.setBackground(getResources().getDrawable(R.drawable.item_bg_f0));
            }
//            select_price = price;
        }

        class HolderView extends BaseHolder {
            @BindView(R.id.price)
            TextView price;
            @BindView(R.id.gold)
            TextView gold;
            @BindView(R.id.linearlayout)
            LinearLayout linearlayout;

            public HolderView(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }


}
