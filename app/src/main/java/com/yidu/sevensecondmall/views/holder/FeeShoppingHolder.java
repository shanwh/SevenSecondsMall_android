package com.yidu.sevensecondmall.views.holder;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhouwei.library.CustomPopWindow;
import com.se7en.utils.SystemUtil;
import com.yidu.sevensecondmall.Activity.Distribution.DingOrderActivity;
import com.yidu.sevensecondmall.DAO.UserDAO;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Order.FeeShoppingBean;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.i.IEventTag;
import com.yidu.sevensecondmall.i.LoadDataEvent;
import com.yidu.sevensecondmall.utils.ToastUtil;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.MultiTypeAdapter;
import com.yidu.sevensecondmall.views.widget.GearView;
import com.yidu.sevensecondmall.views.widget.iconfonts.IconFontTextView;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.text.DecimalFormat;

import static com.yidu.sevensecondmall.i.IEventTag.LOAD_DATA;

/**
 * Created by Administrator on 2017/3/29 0029.
 */
public class FeeShoppingHolder extends BaseContextViewHolder<FeeShoppingBean> {

    private String need_use_ding_coin;
    private String order_id;
    private String order_sn;
    private String reference;
    DecimalFormat df;
    private GearView bigGv;
    private int mTag = -1;
    FeeShoppingBean model;
    private LinearLayout ll_assignment;
    TextView tv_assignment;
    IconFontTextView if_assignment;

    public FeeShoppingHolder(View itemView, Context context) {
        super(itemView, context);
        df = new DecimalFormat("######0.00");
    }


    @Override
    public void setUpView(final FeeShoppingBean model, final int position, final MultiTypeAdapter adapter) {
        this.model = model;
        final Context context = getHolderContext();

        TextView tv_code = (TextView) getView(R.id.tv_code);
//        TextView tv_date = (TextView) getView(R.id.tv_date);
        TextView tv_price = (TextView) getView(R.id.tv_price);

        tv_code.setText(model.getCode());
//        tv_date.setText(model.getDate());
        tv_price.setText(model.getMoney());

        bigGv = (GearView) getView(R.id.gv_big);
        GearView smallGv = (GearView) getView(R.id.gv_small);
        TextView proNumsmall = (TextView) getView(R.id.small_pronum);
        TextView proNumbig = (TextView) getView(R.id.big_pronum);

        ll_assignment = (LinearLayout) getView(R.id.ll_assignment);
        tv_assignment = (TextView) getView(R.id.tv_assignment);
        if_assignment = (IconFontTextView) getView(R.id.if_assignment);

        getView(R.id.free_is_get).setVisibility(View.VISIBLE);
        getView(R.id.rl_pb).setVisibility(View.VISIBLE);
        if_assignment.setVisibility(View.VISIBLE);
        tv_assignment.setVisibility(View.VISIBLE);
        ll_assignment.setVisibility(View.VISIBLE);


        if ("1".equals(model.getFree_status())) {
            getView(R.id.free_is_get).setVisibility(View.VISIBLE);
            getView(R.id.rl_pb).setVisibility(View.GONE);
            if_assignment.setVisibility(View.GONE);
            tv_assignment.setVisibility(View.GONE);
            ll_assignment.setVisibility(View.GONE);


        } else {

            Double dou = Double.parseDouble(model.getGear_rate());
            Double dousmall = Double.parseDouble(model.getProgress());
            proNumbig.setText(dou + "%");
            proNumsmall.setText(dousmall + "%");
            bigGv.setProgress(dou * 0.01);
            smallGv.setProgress(dousmall * 0.01);

            if (Double.parseDouble(model.getProgress()) >= 100||Double.parseDouble(model.getGear_rate())>=100) {
                if_assignment.setVisibility(View.GONE);
                tv_assignment.setVisibility(View.VISIBLE);
                tv_assignment.setText("领取");
                ll_assignment.setVisibility(View.VISIBLE);
                ll_assignment.setBackgroundResource(R.drawable.button_shape_green);
                mTag = 1;
//                getView(R.id.rl_pb).setVisibility(View.GONE);
                getView(R.id.free_is_get).setVisibility(View.GONE);

            } else {

                if_assignment.setVisibility(View.GONE);
                tv_assignment.setVisibility(View.GONE);
                ll_assignment.setVisibility(View.GONE);
                getView(R.id.free_is_get).setVisibility(View.GONE);
                getView(R.id.rl_pb).setVisibility(View.VISIBLE);
            }

        }


//        if (Double.parseDouble(model.getProgress()) < 100) {
//            tv_out.setText(model.getProgress() + "%");
//        } else {
//            tv_out.setText("100%");
//        }
        //  IconFontTextView it = (IconFontTextView) getView(R.id.it);
        TextView tv = (TextView) getView(R.id.tv);
        if (SystemUtil.getSharedBoolean("isVip", false)) {
            //it.setTextColor(ContextCompat.getColor(context, R.color.colorGreenPb));
            tv.setTextColor(ContextCompat.getColor(context, R.color.colorGreenPb));
        } else {
            // it.setTextColor(ContextCompat.getColor(context, R.color.colorIdGray));
            tv.setTextColor(ContextCompat.getColor(context, R.color.colorIdGray));
        }


        bigGv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemUtil.getSharedBoolean("isVip", false)&& mTag!=1) {
                    order_id = model.getOrder_id();
//                    ToastUtil.showToast(context, "敬请期待");
                    UserDAO.viewEffectIfDingCoinAdd(model.getOrder_id(), new BaseCallBack() {
                        @Override
                        public void success(Object data) {
                            try {
                                JSONObject content = new JSONObject(data.toString());
                                if (content.has("need_use_ding_coin")) {

                                    /**
                                     *  "user_has_ding_coin": "82000.00",
                                     "old_gear_rate": 0,
                                     "need_use_ding_coin": 18000,
                                     "new_gear_rate": 100
                                     */
                                    String user_has_ding_coin = content.getString("user_has_ding_coin");
                                    need_use_ding_coin = content.getString("need_use_ding_coin");
                                    if (Double.parseDouble(user_has_ding_coin) <= 1) {
                                        ToastUtil.showToast(context, "DING宝不足");
                                    } else {
                                        showPopupWindow(getView(R.id.gv_big));
                                    }

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void failed(int errorCode, Object data) {
                            ToastUtil.showToast(context, data + "");
                        }
                    });

                }
            }
        });

        //按钮


//        if ("0".equals(model.getSell_status())) {
//            if_assignment.setVisibility(View.GONE);
//            tv_assignment.setVisibility(View.GONE);
//            ll_assignment.setVisibility(View.GONE);

//            if_assignment.setVisibility(View.VISIBLE);
//            tv_assignment.setText("转让");
//            ll_assignment.setBackgroundResource(R.drawable.button_shape);
        // mTag = 0;
//        } else {
//            if (Double.parseDouble(model.getProgress()) >= 100) {
//                if_assignment.setVisibility(View.GONE);
//                tv_assignment.setVisibility(View.VISIBLE);
//                tv_assignment.setText("领取");
//                ll_assignment.setVisibility(View.VISIBLE);
//                ll_assignment.setBackgroundResource(R.drawable.button_shape_green);
//                mTag = 1;
//            } else {
//                ll_assignment.setVisibility(View.GONE);
//                if_assignment.setVisibility(View.GONE);
//                tv_assignment.setVisibility(View.GONE);
//                if_assignment.setVisibility(View.GONE);
//                tv_assignment.setText("转让中");
//                ll_assignment.setBackgroundResource(R.drawable.button_shape_green);
//                mTag = 2;
////            }
//        }
        ll_assignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (mTag) {
                    case 0:
                        order_sn = model.getOrder_sn();
                        reference = df.format(Double.parseDouble(model.getMoney()) * Double.parseDouble(model.getProgress()) / 100);
                        showPopupWindowAssignment(getView(R.id.ll_assignment));
                        break;
                    case 1:
                        showPopupWindowGet(ll_assignment);
                        break;
                    case 2:
                        Intent intent = new Intent(context, DingOrderActivity.class);
                        intent.putExtra("sell_price", model.getSell_price());
                        intent.putExtra("Cancel_fee", model.getCancel_fee());
                        intent.putExtra("order_sn", model.getOrder_sn());
                        intent.putExtra("price", model.getMoney());
                        intent.putExtra("rate", model.getProgress());
                        context.startActivity(intent);
                        break;
                }
            }
        });
    }

    CustomPopWindow popupWindow;

    private void showPopupWindow(View view) {
        View contentView = LayoutInflater.from(getHolderContext()).inflate(R.layout.pop_window_ding_acc, null);
        //处理popWindow 显示内容
        handleLogic(contentView);
        popupWindow = new CustomPopWindow.PopupWindowBuilder(getHolderContext())
                .setView(contentView)//显示的布局，还可以通过设置一个View
                .size(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)//显示大小,默认包裹内容
                .setFocusable(true)//是否获取焦点，默认为ture
                .setOutsideTouchable(true)//是否PopupWindow 以外触摸dissmiss
                .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                .setBgDarkAlpha(0.5f) // 控制亮度
                .setInputMethodMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                .create()//创建PopupWindow
                .showAtLocation(view, Gravity.CENTER, 0, 0);//显示PopupWindow

    }


    /**
     * 处理弹出显示内容、点击事件等逻辑
     *
     * @param contentView
     */
    private void handleLogic(View contentView) {
        TextView tv_ding2 = (TextView) contentView.findViewById(R.id.tv_ding2);

        tv_ding2.setText(need_use_ding_coin + "DING宝");

        contentView.findViewById(R.id.tv_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserDAO.useDingCoinToIncreaseGearRate(order_id, new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        EventBus.getDefault().post(new LoadDataEvent(LOAD_DATA, 1));
                        popupWindow.dissmiss();
//                        bigGv.startRotate();
                        // bigGv.setProgress();
                        // TODO: 2017/8/31
                        ToastUtil.showToast(getHolderContext(), "加速成功");
                        EventBus.getDefault().post(new LoadDataEvent(IEventTag.LOAD_DATA));


                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        ToastUtil.showToast(getHolderContext(), data + "");
                    }
                });
            }
        });

        contentView.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dissmiss();
            }
        });

    }


    CustomPopWindow popupWindow2;
    EditText et;

    private void showPopupWindowAssignment(View view) {

        View contentView = LayoutInflater.from(getHolderContext()).inflate(R.layout.pop_window_ding_assignment, null);
        //处理popWindow 显示内容
        handleLogicAssignment(contentView);
        popupWindow2 = new CustomPopWindow.PopupWindowBuilder(getHolderContext())
                .setView(contentView)//显示的布局，还可以通过设置一个View
                .size(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)//显示大小,默认包裹内容
                .setFocusable(true)//是否获取焦点，默认为ture
                .setOutsideTouchable(true)//是否PopupWindow 以外触摸dissmiss
                .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                .setBgDarkAlpha(0.5f) // 控制亮度
                .create()//创建PopupWindow
                .showAtLocation(view, Gravity.CENTER, 0, 0);//显示PopupWindow
    }

    /**
     * 处理弹出显示内容、点击事件等逻辑
     *
     * @param contentView
     */
    private void handleLogicAssignment(View contentView) {
        et = (EditText) contentView.findViewById(R.id.et);
        TextView tv_reference = (TextView) contentView.findViewById(R.id.tv_reference);
        tv_reference.setText(reference + "元");
        contentView.findViewById(R.id.tv_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(et.getText().toString())) {
                    UserDAO.myFreeOrderSell(order_sn, et.getText().toString(), new BaseCallBack() {
                        @Override
                        public void success(Object data) {
                            EventBus.getDefault().post(new LoadDataEvent(LOAD_DATA, 1));
                            popupWindow2.dissmiss();
                            ToastUtil.showToast(getHolderContext(), "转让订单成功");
                        }

                        @Override
                        public void failed(int errorCode, Object data) {
                            ToastUtil.showToast(getHolderContext(), data + "");
                        }
                    });
                } else {
                    ToastUtil.showToast(getHolderContext(), "请输入转让金额");
                }
            }
        });

        contentView.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow2.dissmiss();
            }
        });

    }

    CustomPopWindow popupWindow3;

    private void showPopupWindowGet(View view) {
        View contentView = LayoutInflater.from(getHolderContext()).inflate(R.layout.pop_window_ding_get, null);
        //处理popWindow 显示内容
        handleLogicGet(contentView);
        popupWindow3 = new CustomPopWindow.PopupWindowBuilder(getHolderContext())
                .setView(contentView)//显示的布局，还可以通过设置一个View
                .size(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)//显示大小,默认包裹内容
                .setFocusable(true)//是否获取焦点，默认为ture
                .setOutsideTouchable(true)//是否PopupWindow 以外触摸dissmiss
                .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                .setBgDarkAlpha(0.5f) // 控制亮度
                .create()//创建PopupWindow
                .showAtLocation(view, Gravity.TOP, 0, 0);//显示PopupWindow
    }

    /**
     * 处理弹出显示内容、点击事件等逻辑
     *
     * @param contentView
     */
    private void handleLogicGet(final View contentView) {

        contentView.findViewById(R.id.tv_get_money).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**免单转让*/
                /**免单转让*/

                UserDAO.getFree(model.getOrder_id(), model.getProgress(),model.getGear_rate(), new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getHolderContext(), "领取成功", Toast.LENGTH_SHORT).show();

                            }
                        });
                        popupWindow3.dissmiss();
                        model.setFree_status("1");
                        getView(R.id.free_is_get).setVisibility(View.VISIBLE);
                        getView(R.id.rl_pb).setVisibility(View.GONE);
                        getView(R.id.ll_assignment).setVisibility(View.GONE);
                    }

                    @Override
                    public void failed(int errorCode, Object data) {

                    }
                });

            }
        });

//        contentView.findViewById(R.id.tv_get_gift).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                popupWindow3.dissmiss();
//            }
//        });

    }
}