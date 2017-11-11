package com.yidu.sevensecondmall.Activity.Distribution;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xys.libzxing.zxing.encoding.EncodingUtils;
import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.DAO.DistributionDAO;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.views.widget.iconfonts.IconFontTextView;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/10 0010.
 * 已弃用
 */
public class DistributionCopyActivity extends BaseActivity {

    @BindView(R.id.back)
    IconFontTextView back;
    @BindView(R.id.if_tv_add_more)
    IconFontTextView ifTvAddMore;
    @BindView(R.id.if_tv_record)
    IconFontTextView ifTvRecord;
    @BindView(R.id.rl_head)
    RelativeLayout rlHead;
    @BindView(R.id.tv_all)
    TextView tvAll;
    @BindView(R.id.tv_recommend_back)
    TextView tvRecommendBack;
    @BindView(R.id.tv_consume_back)
    TextView tvConsumeBack;
    @BindView(R.id.tv_sell_back)
    TextView tvSellBack;
    @BindView(R.id.fl_recommend)
    FrameLayout flRecommend;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    @BindView(R.id.rl_recommend)
    RelativeLayout rlRecommend;
    @BindView(R.id.fl_root)
    FrameLayout flRoot;
    @BindView(R.id.rl_root)
    RelativeLayout rlRoot;
    @BindView(R.id.fl_team)
    FrameLayout flTeam;
    @BindView(R.id.rl_team)
    RelativeLayout rlTeam;
    @BindView(R.id.fl_recommend_team)
    FrameLayout flRecommendTeam;
    @BindView(R.id.rl_recommend_team)
    RelativeLayout rlRecommendTeam;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;


    private int id;
    private static final String TAG = "DistributionActivity";

    @Override
    protected int setViewId() {
        return R.layout.activity_distribution_copy;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
        toolbarTitle.setText("VIP专区");
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void init() {
//        int level = getIntent().getIntExtra("level", 2);
//        switch (level) {
//            case 2:
//                rlRecommendTeam.setVisibility(View.GONE);
//                rlTeam.setVisibility(View.GONE);
//                break;
//            case 3:
//                tvTitle.setText("合伙人专区");
//                break;
//        }
        id = getIntent().getIntExtra("id", 1);

    }

    @Override
    protected void loadData() {
        DistributionDAO.getRebateInfo(new BaseCallBack() {
            @Override
            public void success(Object data) {
                try {
                    JSONObject content = new JSONObject(data.toString());
                    if (content.has("rebateInfo")) {
                        JSONObject rebateInfo = content.getJSONObject("rebateInfo");
                        String today_money = rebateInfo.getString("today_money");
                        tvAll.setText(today_money.equals("0") ? "暂无收益" : today_money);
                        tvRecommendBack.setText(rebateInfo.getString("extension_money"));
                        tvConsumeBack.setText(rebateInfo.getString("consumption_money"));
                        tvSellBack.setText(rebateInfo.getString("may_money"));
                        String extension_nums = "我推荐的人(" + rebateInfo.getString("extension_nums") + ")";
                        tvRecommend.setText(extension_nums);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(int errorCode, Object data) {
                showShortToast(data + "");
            }
        });
    }


    @OnClick({R.id.back, R.id.if_tv_add_more, R.id.if_tv_record, R.id.rl_recommend, R.id.rl_root, R.id.rl_team, R.id.rl_recommend_team})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.if_tv_add_more:

                /**
                 * "type": "member",
                 "user_id": "2597"
                 */
                Log.e(TAG, "onClick: " + id);
                String routerURL = "member" + "/" + id;
                Bitmap bitmap = EncodingUtils.createQRCode(routerURL, 500, 500, null);

                showPopupWindow(view, bitmap);
                break;
            case R.id.if_tv_record:
                intent = new Intent(this, RecordActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_recommend:
                intent = new Intent(this, MemberListActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_root:
                intent = new Intent(this, MyRootActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_team:
                intent = new Intent(this, MemberListActivity.class);
                intent.putExtra("type", 1);
                startActivity(intent);
                break;
            case R.id.rl_recommend_team:
                intent = new Intent(this, MemberListActivity.class);
                intent.putExtra("type", 2);
                startActivity(intent);
                break;
        }
    }

    PopupWindow popupWindow;

    private void showPopupWindow(View view, Bitmap bitmap) {

        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(this).inflate(
                R.layout.pop_code_window, null);
        // 设置按钮的点击事件

        ImageView iv = (ImageView) contentView.findViewById(R.id.iv);
        iv.setImageBitmap(bitmap);

        popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        popupWindow.setTouchable(true);

        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Log.i("mengdd", "onTouch : ");

                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.alertdiag_bg));

        // 设置好参数之后再show
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

    }

}
