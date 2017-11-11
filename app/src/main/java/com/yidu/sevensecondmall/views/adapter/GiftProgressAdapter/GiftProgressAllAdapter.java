package com.yidu.sevensecondmall.views.adapter.GiftProgressAdapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yidu.sevensecondmall.Activity.Distribution.AddVelocityDetailActivity;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Others.GiftProgressBean;
import com.yidu.sevensecondmall.networks.HttpApi;
import com.yidu.sevensecondmall.utils.AppUtils;
import com.yidu.sevensecondmall.utils.LoginUtils;
import com.yidu.sevensecondmall.utils.OkHttpUtil;
import com.yidu.sevensecondmall.utils.ToastUtil;
import com.yidu.sevensecondmall.utils.ViewUtils;
import com.yidu.sevensecondmall.views.adapter.RecyclerViewAdapter;
import com.yidu.sevensecondmall.views.adapter.TransferOrderItemAdapter;
import com.yidu.sevensecondmall.views.widget.DensityUtils;
import com.yidu.sevensecondmall.views.widget.widget.BaseHolder;
import com.yidu.sevensecondmall.views.widget.widget.CircularProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/11/7.
 */

public class GiftProgressAllAdapter extends RecyclerViewAdapter<GiftProgressBean.ResultBean.ListBean, GiftProgressAllAdapter.MyHolder> {


    private Context context;

    public GiftProgressAllAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_all_gift_progress;
    }

    @Override
    public MyHolder getViewHolder(View view) {
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {
        final GiftProgressBean.ResultBean.ListBean bean = getItem(position);
        if (bean != null) {
            holder.peopleNum.setVisibility(View.GONE);
            holder.tvName.setText(bean.getNickname());
            holder.orderNumber.setText("订单：" + bean.getOrder_sn());
            holder.tvPrice.setText("剩余：¥" + bean.getRedis_amount());
            holder.time.setText("开始时间：" + bean.getStart_time());
            holder.totalPrice.setText("总金额：" + bean.getTotal_amount());
            holder.tvMessage.setText("当前折扣：" + bean.getDiscount().getNow_discount() + "折，" + bean.getDiscount().getLeft_days() + "天后降为" + bean.getDiscount().getLeft_discount() + "折");
            holder.circularProgressBar.setCircleWidth(DensityUtils.dip2px(context, 7));
            holder.circularProgressBar.setProgress(Float.valueOf(bean.getRedis_rate()));
            holder.progressNum.setText(bean.getRedis_rate() + "%");
            LinearLayoutManager manager = new LinearLayoutManager(context);
            manager.setOrientation(LinearLayoutManager.HORIZONTAL);
            holder.recycleImage.setLayoutManager(manager);
            TransferOrderItemAdapter adapter = new TransferOrderItemAdapter(context);
            holder.recycleImage.setAdapter(adapter);
            List<GiftProgressBean.ResultBean.ListBean.OriginalImgBean> imgBeen = bean.getOriginal_img();
            List<String> listImg = new ArrayList<>();
            if (imgBeen != null && imgBeen.size() > 0) {

                for (int i = 0; i < imgBeen.size(); i++) {
                    listImg.add(imgBeen.get(i).getImgurl());
                }
                adapter.addAll(listImg);
            }
            holder.tvTransfer.setVisibility(View.VISIBLE);


        }
        holder.tvTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bean.getOrder_id() == null ){
                    ToastUtil.showToast(context,"order_id===null");
                }else{
                    pop(bean.getOrder_id(), position);
                }
            }
        });
        holder.rlProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bean.getOrder_id() != null){
                    Log.e("order_id====bean",bean.getOrder_id());
                    context.startActivity(new Intent(context, AddVelocityDetailActivity.class).putExtra("order_id",bean.getOrder_id()));
                }else{
                    ToastUtil.showToast(context,"order_id==null");
                }
            }
        });

    }

    class MyHolder extends BaseHolder {

        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.order_number)
        TextView orderNumber;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.total_price)
        TextView totalPrice;
        @BindView(R.id.recycle_image)
        RecyclerView recycleImage;
        @BindView(R.id.circular_progress_bar)
        CircularProgressBar circularProgressBar;
        @BindView(R.id.progress_num)
        TextView progressNum;
        @BindView(R.id.rl_progress)
        RelativeLayout rlProgress;
        @BindView(R.id.tv_message)
        TextView tvMessage;
        @BindView(R.id.tv_transfer)
        TextView tvTransfer;
        @BindView(R.id.people_num)
        TextView peopleNum;

        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    private void pop(final String order_id, final int position) {
        ViewUtils.makeConfirm(context, "确定转入加速区么？", "确定", "取消", new ViewUtils.ButtonCallback() {
            @Override
            public void onPositive(Dialog d) {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("token", LoginUtils.getToken());
                params.put("order_id", order_id);
                params.put("unique_id", "android");
                OkHttpUtil.postSubmitForm(HttpApi.addOrderToQuick, params, new OkHttpUtil.OnDownDataListener() {
                    @Override
                    public void onResponse(String url, String json) {
                        Log.e("转入加速区json", json);
                        try {
                            JSONObject object = new JSONObject(json);
                            int code = object.getInt("code");
                            if (code == 0) {
                                ToastUtil.showToast(context, object.getString("message"));
                                remove(getItem(position));
                                notifyDataSetChanged();

                            } else {
                                ToastUtil.showToast(context, object.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(String url, String error) {

                    }
                });


            }

            @Override
            public void onNegative(Dialog d) {

            }
        }).show();
    }
}
