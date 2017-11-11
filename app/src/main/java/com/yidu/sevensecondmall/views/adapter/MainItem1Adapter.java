package com.yidu.sevensecondmall.views.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zhouwei.library.CustomPopWindow;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yidu.sevensecondmall.Activity.Distribution.DistributionNewActivity;
import com.yidu.sevensecondmall.Activity.Distribution.TransferActivity;
import com.yidu.sevensecondmall.Activity.Login.LoginActivity;
import com.yidu.sevensecondmall.Activity.Order.SearchActivity;
import com.yidu.sevensecondmall.Activity.UserCenter.IndentActivity;
import com.yidu.sevensecondmall.Activity.UserCenter.InvitedActivity;
import com.yidu.sevensecondmall.Activity.UserCenter.ShopApplyActivity;
import com.yidu.sevensecondmall.DAO.UserDAO;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Main.MainBean;
import com.yidu.sevensecondmall.bean.User.UserBean;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.utils.LoginUtils;
import com.yidu.sevensecondmall.utils.ToastUtil;
import com.yidu.sevensecondmall.views.widget.widget.BaseHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/10/24.
 */

public class MainItem1Adapter extends RecyclerAdapter<MainBean.ResultBean.NavBean, MainItem1Adapter.Item1Holder> {

    public MainItem1Adapter(Context context) {
        this.context = context;
    }

    private Context context;

    @Override
    public Item1Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Item1Holder(LayoutInflater.from(context).inflate(R.layout.main_item1, parent, false));
    }

    @Override
    public void onBindViewHolder(Item1Holder holder, final int position) {
        final MainBean.ResultBean.NavBean navBean = getItem(position);
        if (navBean != null) {
            WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            int width = manager.getDefaultDisplay().getWidth() - 28;
            int size = getItemCount();
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width / size, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 20, 0, 5);
            holder.linearLayout.setLayoutParams(layoutParams);
            holder.ivTitle.setImageURI(Uri.parse(navBean.getAd_code()));
            holder.tv.setText(navBean.getAd_name());
            //点击事件

            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != navBean.getType()) {
                        if (navBean.getType().equals("gift_link")) {
                            if (LoginUtils.isLogin()) {
                                context.startActivity(new Intent(context, TransferActivity.class));
                            } else {
                                context.startActivity(new Intent(context, LoginActivity.class));
                            }
                        } else if (navBean.getType().equals("search_id")) {
                            if (navBean.getCat_id() != null) {
                                Intent i = new Intent(context, SearchActivity.class);
                                i.putExtra("nav_type", navBean.getNav_type());
                                i.putExtra("more_id", navBean.getMore_id());
                                context.startActivity(i);
                            } else {
                                Log.e("搜索ID为空", "---------");
                            }
                        } else if (navBean.getType().equals("search_keywords")) {
                            if (navBean.getKeywords() != null) {
                                Intent i = new Intent(context, SearchActivity.class);
                                i.putExtra("nav_type", navBean.getNav_type());
                                i.putExtra("more_id", navBean.getMore_id());
                                context.startActivity(i);
                            } else {
                                Log.e("搜索keyword为空", "---------");
                            }

                        } else if (navBean.getType().equals("api_invite")) {
                            Intent intent;
                            //邀请有奖
                            if (LoginUtils.isLogin()) {
                                intent = new Intent(context, InvitedActivity.class);
                                context.startActivity(intent);
                            } else {
                                intent = new Intent(context, LoginActivity.class);
                                context.startActivity(intent);
                            }

                        } else if (navBean.getType().equals("api_vip")) {
                            //VIP专区
                            Intent intent = new Intent(context, DistributionNewActivity.class);
//                            Log.e(TAG, "onClick: " + bean.getUser_id());
                            intent.putExtra("id", LoginUtils.getUserId());
//                        intent.putExtra("level", level);
                            context.startActivity(intent);

                        } else if (navBean.getType().equals("api_join_shop")) {
                            joinshop();
                        }
                    }

                }
            });
            Log.e("MainItem1Adapter", navBean.getAd_name());
            Log.e("MainItem1Adapter", navBean.getAd_code());
        }


    }

    public class Item1Holder extends BaseHolder {

        @BindView(R.id.iv_title)
        SimpleDraweeView ivTitle;
        @BindView(R.id.tv)
        TextView tv;
        @BindView(R.id.linearlayout)
        LinearLayout linearLayout;

        public Item1Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    CustomPopWindow popupWindow;

    private void showPopupWindow() {

        View contentView = LayoutInflater.from(context).inflate(R.layout.pop_window_indentification, null);

        //处理popWindow 显示内容
        handleLogic(contentView);
        popupWindow = new CustomPopWindow.PopupWindowBuilder(context)
                .setView(contentView)//显示的布局，还可以通过设置一个View
                .size(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)//显示大小,默认包裹内容
                .setFocusable(true)//是否获取焦点，默认为ture
                .setOutsideTouchable(true)//是否PopupWindow 以外触摸dissmiss
                .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                .setBgDarkAlpha(0.5f) // 控制亮度
                .create()//创建PopupWindow
                .showAtLocation(contentView, Gravity.CENTER, 0, 0);//显示PopupWindow
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
                Intent intent = new Intent(context, IndentActivity.class);
                context.startActivity(intent);
                popupWindow.dissmiss();
            }
        });

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
                            Intent intent = new Intent(context, ShopApplyActivity.class);
                            intent.putExtra("name", bean.getRealname());
                            context.startActivity(intent);
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
                            ToastUtil.showToast(context, "您的申请" + business_apply_status);
                        }
                    }
                }
            }

            @Override
            public void failed(int errorCode, Object data) {
                ToastUtil.showToast(context, data + "");
            }
        });
    }
}
