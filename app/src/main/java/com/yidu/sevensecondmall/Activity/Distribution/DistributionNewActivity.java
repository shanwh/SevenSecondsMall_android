package com.yidu.sevensecondmall.Activity.Distribution;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.zhouwei.library.CustomPopWindow;
import com.se7en.utils.SystemUtil;
import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.Activity.UserCenter.IndentActivity;
import com.yidu.sevensecondmall.DAO.UserDAO;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Distribution.VipMealBean;
import com.yidu.sevensecondmall.bean.User.UserBean;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.i.IEventOrderTag;
import com.yidu.sevensecondmall.i.IEventTag;
import com.yidu.sevensecondmall.i.LoginEvent;
import com.yidu.sevensecondmall.i.PaySuccess;
import com.yidu.sevensecondmall.i.WechatPayEvent;
import com.yidu.sevensecondmall.networks.HttpApi;
import com.yidu.sevensecondmall.utils.PayPopWindow;
import com.yidu.sevensecondmall.utils.TimeFormatUtils;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.CustomRecyclerView;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.MultiTypeAdapter;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.Visitable;
import com.yidu.sevensecondmall.views.widget.GlideCircleTransform;
import com.yidu.sevensecondmall.views.widget.iconfonts.IconFontTextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/10 0010.
 */
public class DistributionNewActivity extends BaseActivity implements PaySuccess {


    @BindView(R.id.back)
    IconFontTextView back;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_head)
    RelativeLayout rlHead;
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.ll_privilege)
    LinearLayout llPrivilege;
    @BindView(R.id.ll_up_lv)
    LinearLayout llUpLv;
    @BindView(R.id.ll_my_ding)
    LinearLayout llMyDing;
    @BindView(R.id.custom_rv)
    CustomRecyclerView custom_rv;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_role)
    TextView tvRole;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    private String[] times = new String[]{"1个月", "3个月", "6个月", "年费"};
    private String[] frees = new String[]{"200", "450", "720", "1000"};


    private boolean isVip;
    private boolean isShareHolder;

    @Override
    protected int setViewId() {
        return R.layout.activity_distribution_new;
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

    private void setVipMeal(String btnName) {
        ArrayList<Visitable> list = new ArrayList<>();
        for (int i = 0; i < times.length; i++) {
            VipMealBean bean = new VipMealBean();
            bean.setTime(times[i]);
            bean.setMoney(frees[i]);
            bean.setBtnName(btnName);
            list.add(bean);
        }

        MultiTypeAdapter adapter = custom_rv.getAdapter();
        adapter.refreshData(list);
    }

    @Override
    protected void loadData() {
        UserDAO.getUserInfo(
                new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        if (data != null) {
                            UserBean bean = (UserBean) data;

                            Glide.with(DistributionNewActivity.this)
                                    .load(bean.getHead_pic() == null ? "" : HttpApi.getFullImageUrl(bean.getHead_pic()) + "")
                                    .placeholder(R.drawable.star)
                                    .transform(new GlideCircleTransform(DistributionNewActivity.this))
                                    .into(iv);
                            tvName.setText(bean.getNickname());

                            isVip = bean.getIcon().isVip();
                            isShareHolder = bean.getIcon().isPartner();

                            if (isShareHolder) {
                                tvRole.setText("股东卡会员");
                                setVipMeal("续费");

                                SystemUtil.setSharedBoolean("isVip", false);
                            } else if (isVip) {
                                tvRole.setText("VIP会员");
                                setVipMeal("续费");
                                tvTime.setVisibility(View.VISIBLE);
                                if (bean.getVip_valid_period() != null) {
                                    String format = TimeFormatUtils.format(Integer.parseInt(bean.getVip_valid_period()) * 1000L);
                                    tvTime.setText("到期时间：" + format);
                                }
                                SystemUtil.setSharedBoolean("isVip", true);
                            } else {
                                tvRole.setText("普通会员");
                                setVipMeal("开通");
                                SystemUtil.setSharedBoolean("isVip", false);
                            }

                        }
                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        if (errorCode == 1006 || errorCode == 1005) {

                        } else {
                            showShortToast(data + "");
                        }
                    }
                }

        );


    }

    @OnClick({R.id.back, R.id.ll_privilege, R.id.ll_up_lv, R.id.ll_my_ding})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.ll_privilege:
                if (isShareHolder || isVip) {
                    intent = new Intent(this, TeamPrivilegeActivity.class);
                    startActivity(intent);
                } else {
                    showShortToast("成为VIP会员后开通此功能");
                }
                break;
            case R.id.ll_up_lv:
                if (isShareHolder || isVip) {
                    intent = new Intent(this, UpLvActivity.class);
                    startActivity(intent);
                } else {
                    showShortToast("成为VIP会员后开通此功能");
                }
                break;
            case R.id.ll_my_ding:
                if (isShareHolder || isVip) {
                    intent = new Intent(this, MyDingActivity.class);
                    startActivity(intent);
                } else {
                    showShortToast("成为VIP会员后开通此功能");
                }
                break;
        }
    }

    String orderid;
    String price;
    String type;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handler(LoginEvent event) {
        switch (event.founctionTag) {
            case IEventTag.SHOW_PAY_TYPE:
                orderid = event.id;
                price = event.price;
                type = "vipcard";

                UserDAO.getUserInfo(new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        if (data != null) {
                            UserBean bean = (UserBean) data;
                            String is_authentication = bean.getIs_authentication();
                            if (is_authentication.equals("0")) {
                                showPopupWindow();
                            } else {
                                PayPopWindow payPopWindow = new PayPopWindow(DistributionNewActivity.this, llUpLv, price, orderid, type, DistributionNewActivity.this);
                                payPopWindow.showPayPopWindow();
                            }
                        }
                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        showShortToast(data + "");
                    }
                });


                break;
        }
    }


    public void paySuccess() {
        loadData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handler(WechatPayEvent wechatPayEvent) {
        switch (wechatPayEvent.payEventTag) {
            case IEventOrderTag.WECHAT_PAY_SUCCESS:
                paySuccess();
                break;
            case IEventOrderTag.WECHAT_PAY_FAILURE:
                break;
            case IEventOrderTag.WECHAT_PAY_CANCEL:
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        String str = data.getExtras().getString("pay_result");
        if (str.equalsIgnoreCase("success")) {
            paySuccess();
        } else if (str.equalsIgnoreCase("fail")) {
            Toast.makeText(getApplicationContext(), "支付失败！", Toast.LENGTH_SHORT).show();
        } else if (str.equalsIgnoreCase("cancel")) {
            Toast.makeText(getApplicationContext(), "您取消了支付！", Toast.LENGTH_SHORT).show();
        }
    }

    CustomPopWindow popupWindow;

    private Activity getActivity() {
        return DistributionNewActivity.this;
    }

    private void showPopupWindow() {
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.pop_window_indentification, null);
        //处理popWindow 显示内容
        handleLogic(contentView);
        popupWindow = new CustomPopWindow.PopupWindowBuilder(getActivity())
                .setView(contentView)//显示的布局，还可以通过设置一个View
                .size(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)//显示大小,默认包裹内容
                .setFocusable(true)//是否获取焦点，默认为ture
                .setOutsideTouchable(true)//是否PopupWindow 以外触摸dissmiss
                .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                .setBgDarkAlpha(0.5f) // 控制亮度
                .setInputMethodMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                .create()//创建PopupWindow
                .showAtLocation(llUpLv, Gravity.CENTER, 0, 0);//显示PopupWindow
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
                Intent intent = new Intent(getActivity(), IndentActivity.class);
                getActivity().startActivity(intent);
                popupWindow.dissmiss();
            }
        });

    }
}
