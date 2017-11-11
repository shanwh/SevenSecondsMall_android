package com.yidu.sevensecondmall.views.holder;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.yidu.sevensecondmall.Activity.Distribution.AssignmentBuyActivity;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Distribution.AssignmentBean;
import com.yidu.sevensecondmall.utils.LoginUtils;
import com.yidu.sevensecondmall.utils.ToastUtil;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.MultiTypeAdapter;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.Visitable;
import com.yidu.sevensecondmall.views.widget.BitmapWave;
import com.yidu.sevensecondmall.views.widget.WaveProgressView;


/**
 * Created by Administrator on 2017/3/29 0029.
 */
public class AssignmentListHolder extends BaseContextViewHolder<AssignmentBean> {


    public AssignmentListHolder(View itemView, Context context) {
        super(itemView, context);

    }

    @Override
    public void setUpView(final AssignmentBean model, final int position, final MultiTypeAdapter adapter) {
        final Context context = getHolderContext();


        WaveProgressView waveProgressbar = (WaveProgressView) getView(R.id.waveProgressbar);

        int rate = 100 - (int)Double.parseDouble(model.getRate());
        if (rate == 0){
            rate = 1;
        }else if (rate == 100){
            rate = 120;
        }
        waveProgressbar.setCurrent(rate);

        waveProgressbar.invalidate();



        TextView tv_price = (TextView)getView(R.id.tv_price);
        tv_price.setText("¥"+model.getGoods_price());

        TextView tv_num = (TextView)getView(R.id.tv_num);
        tv_num.setText("赠品编号\n"+model.getOrder_sn());

        TextView tv_present = (TextView)getView(R.id.tv_present);
        tv_present.setText(model.getRate()+"%");


        getView(R.id.tv_buy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AssignmentBuyActivity.class);
                intent.putExtra("curr_id", model.getCurr_id());
                intent.putExtra("num", model.getOrder_sn());
                intent.putExtra("price", model.getGoods_price());
                intent.putExtra("sell_price", model.getSell_price());
                intent.putExtra("rate", model.getRate());
                intent.putExtra("id" , model.getOrder_id());
                context.startActivity(intent);
            }
        });

        if (LoginUtils.getUserId().equals(model.getUser_id())){
            getView(R.id.tv_buy).setVisibility(View.GONE);
        }else {
            getView(R.id.tv_buy).setVisibility(View.VISIBLE);
        }

    }


}
