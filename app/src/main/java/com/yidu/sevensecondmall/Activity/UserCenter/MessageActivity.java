package com.yidu.sevensecondmall.Activity.UserCenter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.Activity.Store.StoreHomepageActivity;
import com.yidu.sevensecondmall.DAO.UserDAO;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.User.MessageListInfo;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.views.widget.iconfonts.IconFontTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/30.
 * 客户->消息界面
 */
public class MessageActivity extends BaseActivity {
    private static final String TAG = "MessageActivity";
    @BindView(R.id.v)
    View v;
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
    @BindView(R.id.img_car)
    IconFontTextView imgCar;
    @BindView(R.id.tv_bubble_ship)
    TextView tvBubbleShip;
    @BindView(R.id.tv_ship_more)
    TextView tvShipMore;
    @BindView(R.id.rl1)
    RelativeLayout rl1;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.logistics_title)
    TextView logisticsTitle;
    @BindView(R.id.tv_ship_title)
    TextView tvShipTitle;
    @BindView(R.id.item)
    RelativeLayout item;
    @BindView(R.id.img2)
    IconFontTextView img2;
    @BindView(R.id.tv_bubble_inform)
    TextView tvBubbleInform;
    @BindView(R.id.tv_inform_more)
    TextView tvInformMore;
    @BindView(R.id.rl2)
    RelativeLayout rl2;
    @BindView(R.id.time2)
    TextView time2;
    @BindView(R.id.tv_system_title)
    TextView tvSystemTitle;
    @BindView(R.id.img3)
    ImageView img3;
    @BindView(R.id.time3)
    TextView time3;
    @BindView(R.id.tv_action_title)
    TextView tvActionTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;


    private ArrayList<TextView> bubbleList = new ArrayList<>();
    private ArrayList<TextView> moreList = new ArrayList<>();

    @Override
    protected int setViewId() {
        return R.layout.activity_message;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
        titleName.setText("消息");
        toolbarTitle.setText("消息");
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void init() {
        bubbleList.add(tvBubbleShip);
        bubbleList.add(tvBubbleInform);

        moreList.add(tvShipMore);
        moreList.add(tvInformMore);
    }

    @Override
    protected void loadData() {
        UserDAO.getMessageListInfo(new BaseCallBack() {
            @Override
            public void success(Object data) {
                MessageListInfo info = (MessageListInfo) data;
                setBubble(info.getShipCount(), 0);
                setBubble(info.getInformCount(), 1);
                if (info.getActionTitle() != null) {
                    tvActionTitle.setText(info.getActionTitle());
                } else {
                    tvActionTitle.setText("暂无消息");
                }
                if (info.getShipTitle() != null) {
                    tvShipTitle.setText(info.getShipTitle());
                } else {
                    tvShipTitle.setText("暂无消息");
                }
                if (info.getSysTitle() != null) {
                    tvSystemTitle.setText(info.getSysTitle());
                } else {
                    tvSystemTitle.setText("暂无消息");
                }

                time.setText(info.getShipTime());
                time2.setText(info.getSysTime());
                time3.setText(info.getActionTime());
            }

            @Override
            public void failed(int errorCode, Object data) {
                Log.e(TAG, "failed: errorCode" + errorCode);
                showShortToast("" + data);
            }
        });
    }


    @OnClick({R.id.back, R.id.item, R.id.rl_ntf_msg})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.item:
                Intent i = new Intent(MessageActivity.this, LogisticsActivity.class);
                startActivity(i);
                break;
            case R.id.rl_ntf_msg:
                Intent intent = new Intent(MessageActivity.this, StoreHomepageActivity.class);
                startActivity(intent);
        }
    }


    private void setBubble(int value, int position) {
        if (value == 0) {
            bubbleList.get(position).setVisibility(View.GONE);
            moreList.get(position).setVisibility(View.GONE);
        } else {
            bubbleList.get(position).setVisibility(View.VISIBLE);
            if (value > 99) {
                bubbleList.get(position).setText("99");
                moreList.get(position).setVisibility(View.VISIBLE);
            } else {
                bubbleList.get(position).setText(getBubbleValue(value));
                moreList.get(position).setVisibility(View.GONE);
            }
        }
    }

    private String getBubbleValue(int value) {
        return value + "";
    }


}
