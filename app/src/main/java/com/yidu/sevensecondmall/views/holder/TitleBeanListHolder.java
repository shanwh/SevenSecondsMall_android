package com.yidu.sevensecondmall.views.holder;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zhouwei.library.CustomPopWindow;
import com.yidu.sevensecondmall.Activity.Distribution.DistributionNewActivity;
import com.yidu.sevensecondmall.Activity.Login.LoginActivity;
import com.yidu.sevensecondmall.Activity.Order.SearchActivity;
import com.yidu.sevensecondmall.Activity.UserCenter.IndentActivity;
import com.yidu.sevensecondmall.Activity.UserCenter.InvitedActivity;
import com.yidu.sevensecondmall.Activity.UserCenter.ShopApplyActivity;
import com.yidu.sevensecondmall.DAO.UserDAO;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Main.TitleBean;
import com.yidu.sevensecondmall.bean.Main.TitleBeanList;
import com.yidu.sevensecondmall.bean.User.UserBean;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.networks.HttpApi;
import com.yidu.sevensecondmall.utils.LoginUtils;
import com.yidu.sevensecondmall.utils.ToastUtil;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.MultiTypeAdapter;

/**
 * Created by Administrator on 2017/9/8.
 */

public class TitleBeanListHolder extends BaseContextViewHolder {
    private  View itemView ;
    public TitleBeanListHolder(View itemView, Context context) {
        super(itemView, context);
        this.itemView = itemView;
    }

    @Override
    public void setUpView(final Object model, int position, MultiTypeAdapter adapter) {
        final TitleBeanList mod = (TitleBeanList) model;

        for (int i = 0; i < mod.getList().size(); i++) {
            final TitleBean bean = mod.getList().get(i);
            LinearLayout ll = (LinearLayout) getView(getId("ll_", i));
            ll.setVisibility(View.VISIBLE);
            TextView tv = (TextView) getView(getId("text_title_", i));
            ImageView iv = (ImageView) getView(getId("iv_title_", i));
            tv.setText(bean.getAd_name());
            Glide.with(getHolderContext())
                    .load(HttpApi.getFullImageUrl(bean.getAd_code()))
                    .into(iv);
            ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (null != bean.getType()) {
                        if (bean.getType().equals("search_id")) {
                            if (bean.getCat_id() != null) {
                                Intent i = new Intent(getHolderContext(), SearchActivity.class);
                                i.putExtra("id", Integer.parseInt(bean.getCat_id()));
                                getHolderContext().startActivity(i);
                            } else {
                                Log.e("搜索ID为空", "---------");
                            }
                        } else if (bean.getType().equals("search_keywords")) {
                            if (bean.getKeywords() != null) {
                                Intent i = new Intent(getHolderContext(), SearchActivity.class);
                                i.putExtra("keyword", bean.getKeywords());
                                getHolderContext().startActivity(i);
                            } else {
                                Log.e("搜索keyword为空", "---------");
                            }

                        } else if (bean.getType().equals("api_invite")) {
                            Intent intent;
                            //邀请有奖
                            if (LoginUtils.isLogin()) {
                                intent = new Intent(getHolderContext(), InvitedActivity.class);
                                getHolderContext().startActivity(intent);
                            } else {
                                intent = new Intent(getHolderContext(), LoginActivity.class);
                                getHolderContext().startActivity(intent);
                            }

                        } else if (bean.getType().equals("api_vip")) {
                            //VIP专区
                            Intent intent = new Intent(getHolderContext(), DistributionNewActivity.class);
//                            Log.e(TAG, "onClick: " + bean.getUser_id());
                            intent.putExtra("id", LoginUtils.getUserId());
//                        intent.putExtra("level", level);
                            getHolderContext().startActivity(intent);

                        } else if (bean.getType().equals("api_join_shop")) {
                            joinshop();
                        }

                    }
//                    null != bean.getKeywords()
                }
            });
        }


    }

    public void joinshop() {
        UserDAO.getUserInfo(new BaseCallBack() {
            @Override
            public void success(Object data) {
                if (data != null) {
                    UserBean bean = (UserBean) data;
                    String is_authentication = bean.getIs_authentication();
                    if (is_authentication.equals("0")) {
                        showPopupWindow();
                    } else {
                        String business_apply_status = bean.getBusiness_apply_status();

                        if ("notapplied".equals(business_apply_status)) {
                            Intent intent = new Intent(getHolderContext(), ShopApplyActivity.class);
                            intent.putExtra("name", bean.getRealname());
                            getHolderContext().startActivity(intent);
                        } else {

                            switch (business_apply_status) {
                                case "auditing":
                                    business_apply_status = "审核中";
                                    break;
                                case "passed":
                                    business_apply_status = "已通过";
                                    break;
                                case "refuse":
                                    business_apply_status = "已被拒绝";
                                    break;
                            }
                            showShortToast("您的申请" + business_apply_status);
                        }
                    }
                }
            }

            @Override
            public void failed(int errorCode, Object data) {
                showShortToast(data + "");
            }
        });
    }

    CustomPopWindow popupWindow;

    private void showPopupWindow() {

        View contentView = LayoutInflater.from(getHolderContext()).inflate(R.layout.pop_window_indentification, null);

        //处理popWindow 显示内容
        handleLogic(contentView);
        popupWindow = new CustomPopWindow.PopupWindowBuilder(getHolderContext())
                .setView(contentView)//显示的布局，还可以通过设置一个View
                .size(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)//显示大小,默认包裹内容
                .setFocusable(true)//是否获取焦点，默认为ture
                .setOutsideTouchable(true)//是否PopupWindow 以外触摸dissmiss
                .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                .setBgDarkAlpha(0.5f) // 控制亮度
                .create()//创建PopupWindow
                .showAtLocation( itemView, Gravity.CENTER, 0, 0);//显示PopupWindow
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
                Intent intent = new Intent(getHolderContext(), IndentActivity.class);
                getHolderContext().startActivity(intent);
                popupWindow.dissmiss();
            }
        });

    }

    public void showShortToast(String str) {
        ToastUtil.showToast(getHolderContext(), str);
    }

    private int getId(String str, int i) {
        return getHolderContext().getResources().getIdentifier(str + i, "id", getHolderContext().getPackageName());
    }
}

