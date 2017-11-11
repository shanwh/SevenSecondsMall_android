package com.yidu.sevensecondmall.views.holder;

import android.view.View;
import android.widget.TextView;

import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Distribution.RebateListBean;
import com.yidu.sevensecondmall.utils.TimeFormatUtils;
import com.yidu.sevensecondmall.views.adapter.BaseViewHolder;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.MultiTypeAdapter;

/**
 * Created by Administrator on 2017/3/29 0029.
 */
public class RecordHolder extends BaseViewHolder<RebateListBean> {

    public RecordHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setUpView(final RebateListBean model, final int position, final MultiTypeAdapter adapter) {
        TextView tv_time = (TextView)getView(R.id.tv_code);
        if (model.getChange_time() == null ||"null".equals(model.getChange_time())){
            tv_time.setVisibility(View.INVISIBLE);
        }else {
            String format = TimeFormatUtils.format(Long.parseLong(model.getChange_time())*1000L);
            tv_time.setText(format);
        }
        TextView tvType = (TextView)getView(R.id.tv_date);
        String type = model.getDesc();
        tvType.setText(type);
        TextView tvAmount = (TextView)getView(R.id.tv_price);
        tvAmount.setText(model.getUser_money());

    }
}
