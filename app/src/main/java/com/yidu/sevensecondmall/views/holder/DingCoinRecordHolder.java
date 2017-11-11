package com.yidu.sevensecondmall.views.holder;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Distribution.DingCoinRecordBean;
import com.yidu.sevensecondmall.utils.TimeFormatUtils;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.MultiTypeAdapter;

/**
 * Created by Administrator on 2017/3/29 0029.
 */
public class DingCoinRecordHolder extends BaseContextViewHolder<DingCoinRecordBean> {

    public DingCoinRecordHolder(View itemView, Context context) {
        super(itemView,context);
    }

    @Override
    public void setUpView(final DingCoinRecordBean model, final int position, final MultiTypeAdapter adapter) {
        final Context context = getHolderContext();

        TextView tv_name = (TextView)getView(R.id.tv_name);
        tv_name.setText(model.getNote());
        TextView tv_time = (TextView)getView(R.id.tv_time);
        tv_time.setText(TimeFormatUtils.formatC(Integer.parseInt(model.getChange_time())*1000L));
        TextView tv_count = (TextView)getView(R.id.tv_count);
        tv_count.setText(model.getChange_amount());
        if (model.getCurrent_amount().startsWith("-")){
            tv_count.setTextColor(ContextCompat.getColor(context, R.color.colorBottomBlack));
        }else {
            tv_count.setTextColor(ContextCompat.getColor(context, R.color.app_theme));
        }
    }
}
