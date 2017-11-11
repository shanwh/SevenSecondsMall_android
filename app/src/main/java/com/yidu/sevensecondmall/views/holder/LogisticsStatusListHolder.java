package com.yidu.sevensecondmall.views.holder;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Order.ShipDataBean;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.MultiTypeAdapter;
import com.yidu.sevensecondmall.views.widget.widget.CircleInterCircleView;

/**
 * Created by Administrator on 2017/8/22.
 */
public class LogisticsStatusListHolder extends BaseContextViewHolder {


    public LogisticsStatusListHolder(View itemView, Context context) {
        super(itemView, context);
//        Log.e("1","111111111111111111111111111111111111");
    }

    @Override
    public void setUpView(Object model, int position, MultiTypeAdapter adapter) {
        View view = getView(R.id.view_up);
        ShipDataBean bean = null;

        TextView tvCompany = (TextView) getView(R.id.tv_content);
        TextView tvTime = (TextView) getView(R.id.tv_time);
        View v = getView(R.id.iv_circle);
        CircleInterCircleView circle = (CircleInterCircleView) getView(R.id.v_circle_big);
        circle.setVisibility(View.GONE);
        v.setVisibility(View.VISIBLE);
            bean = (ShipDataBean) model;
        if (null != bean) {
            tvCompany.setText(bean.getContext() + "");
            tvTime.setText(bean.getTime());
            view.setBackgroundColor(position == 0 ? Color.WHITE : Color.parseColor("#dddddd"));
            Log.e("1",bean.getTime()+""+bean.getContext());
            if (position == 0) {
                v.setVisibility(View.INVISIBLE);
                circle.setVisibility(View.VISIBLE);
            }
        }
    }
}
