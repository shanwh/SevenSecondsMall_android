package com.yidu.sevensecondmall.Activity.Distribution;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhouwei.library.CustomPopWindow;
import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.Activity.UserCenter.IndentActivity;
import com.yidu.sevensecondmall.DAO.UserDAO;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.User.UserBean;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.views.widget.iconfonts.IconFontTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/10 0010.
 * 已弃用
 */
public class NoDistributionActivity extends BaseActivity {

    @BindView(R.id.back)
    IconFontTextView back;
    @BindView(R.id.rl_head)
    RelativeLayout rlHead;
    @BindView(R.id.tv_request)
    TextView tvRequest;
    @BindView(R.id.more)
    TextView more;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;

    @Override
    protected int setViewId() {
        return R.layout.activity_no_distribution;
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

    }

    @Override
    protected void loadData() {

    }


    @OnClick({R.id.back, R.id.tv_request, R.id.more})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_request:
                UserDAO.getUserInfo(new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        if (data != null) {
                            UserBean bean = (UserBean) data;
                            String is_authentication = bean.getIs_authentication();
                            if (is_authentication.equals("0")) {
                                showPopupWindow();
                            } else {
                                Intent intent = new Intent(NoDistributionActivity.this, RequestActivity.class);
                                intent.putExtra("name", bean.getRealname());
                                startActivity(intent);
                                finish();
                            }
                        }
                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        showShortToast(data + "");
                    }
                });
                break;
            case R.id.more:
                Toast.makeText(NoDistributionActivity.this, "正在建设中", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    CustomPopWindow popupWindow;

    private void showPopupWindow() {
        View contentView = LayoutInflater.from(NoDistributionActivity.this).inflate(R.layout.pop_window_indentification, null);
        //处理popWindow 显示内容
        handleLogic(contentView);
        popupWindow = new CustomPopWindow.PopupWindowBuilder(NoDistributionActivity.this)
                .setView(contentView)//显示的布局，还可以通过设置一个View
                .size(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)//显示大小,默认包裹内容
                .setFocusable(true)//是否获取焦点，默认为ture
                .setOutsideTouchable(true)//是否PopupWindow 以外触摸dissmiss
                .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                .setBgDarkAlpha(0.5f) // 控制亮度
                .create()//创建PopupWindow
                .showAtLocation(tvRequest, Gravity.CENTER, 0, 0);//显示PopupWindow
    }

    /**
     * 处理弹出显示内容、点击事件等逻辑
     *
     * @param contentView
     */
    private void handleLogic(View contentView) {

        contentView.findViewById(R.id.tv_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NoDistributionActivity.this, IndentActivity.class);
                NoDistributionActivity.this.startActivity(intent);
                popupWindow.dissmiss();
            }
        });

    }
}
