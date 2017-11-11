package com.yidu.sevensecondmall.views.holder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.se7en.utils.SystemUtil;
import com.yidu.sevensecondmall.Activity.UserCenter.RechargeActivity;
import com.yidu.sevensecondmall.Activity.UserCenter.UnBindBankCardActivity;
import com.yidu.sevensecondmall.Activity.UserCenter.WithdrawActivity;
import com.yidu.sevensecondmall.DAO.UserDAO;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Distribution.VipMealBean;
import com.yidu.sevensecondmall.bean.Payment.BankCardBean;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.i.IEventTag;
import com.yidu.sevensecondmall.i.LoginEvent;
import com.yidu.sevensecondmall.networks.HttpApi;
import com.yidu.sevensecondmall.utils.ToastUtil;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.MultiTypeAdapter;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/3/29 0029.
 */
public class VipMealListHolder extends BaseContextViewHolder<VipMealBean> {

    public VipMealListHolder(View itemView, Context context) {
        super(itemView,context);
    }

    @Override
    public void setUpView(final VipMealBean model, final int position, final MultiTypeAdapter adapter) {
        final Context context = getHolderContext();
        TextView tv_time = (TextView)getView(R.id.tv_time);
        tv_time.setText(model.getTime());
        TextView tv_money = (TextView)getView(R.id.tv_money);
        tv_money.setText(model.getMoney());
        TextView tv = (TextView)getView(R.id.tv);
        tv.setText(model.getBtnName());
        getView(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserDAO.vipOrder(position + 1 + "", new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        try {
                            JSONObject content = new JSONObject(data.toString());
                            if (content.has("order_id")){
                                String order_id = content.getString("order_id");
                                EventBus.getDefault().post(new LoginEvent(IEventTag.SHOW_PAY_TYPE, order_id, model.getMoney()));
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        ToastUtil.showToast(context, data + "");
                    }
                });
            }
        });
    }
}
