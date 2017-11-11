package com.yidu.sevensecondmall.views.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.yidu.sevensecondmall.Activity.Order.AddCommentsActivity;
import com.yidu.sevensecondmall.Activity.Order.ApplyReturnActivity;
import com.yidu.sevensecondmall.Activity.UserCenter.ReportActivity;
import com.yidu.sevensecondmall.DAO.OrderDAO;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.OrderMessage.GoodsBean;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.networks.HttpApi;
import com.yidu.sevensecondmall.utils.SpecHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/17.
 */
public class returnAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<GoodsBean> list;
    private Context context;
    private LayoutInflater inflater;
    private int statusclick = 0;
    private String ordersn;
    private int fw = 0;
    private int fm = 0;
    private int addw = 0;
    private int addm = 0;
    private int weight = 0;
    SpecHelper helper = SpecHelper.getInstance();

    public returnAdapter(Context context, List<GoodsBean> list, int statusclick, String ordersn) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.statusclick = statusclick;
        this.ordersn = ordersn;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new detailViewHolder(inflater.inflate(R.layout.order_detailed_item, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof detailViewHolder) {
            Glide.with(context)
                    .load(HttpApi.getFullImageUrl(list.get(position).getOriginal_img()))
                    .into(((detailViewHolder) holder).ivGoods);

            ((detailViewHolder) holder).tvGoodsName.setText(list.get(position).getGoods_name());
            ((detailViewHolder) holder).tvPrice.setText(list.get(position).getGoods_price());
            ((detailViewHolder) holder).tvMeal.setText("套餐：" + list.get(position).getSpec_key_name());
            ((detailViewHolder) holder).goodscount.setText("x"+list.get(position).getGoods_num());
            //
            if (list.get(position).getInfo() != null) {
                switch (list.get(position).getInfo().getType()) {
                    case "0":
                        switch (list.get(position).getInfo().getStatus()) {
                            case "0":
                                ((detailViewHolder) holder).finishstatus.setVisibility(View.INVISIBLE);
                                ((detailViewHolder) holder).status.setText("退货申请中");
                                break;
                            case "1":
                                ((detailViewHolder) holder).finishstatus.setVisibility(View.INVISIBLE);
                                ((detailViewHolder) holder).status.setText("退货客服处理");
                                break;
                            case "2":
                                ((detailViewHolder) holder).finishstatus.setVisibility(View.INVISIBLE);
                                ((detailViewHolder) holder).status.setText("退货完成");
                                break;
                        }
                        break;
                    case "1":
                        switch (list.get(position).getInfo().getStatus()) {
                            case "0":
                                ((detailViewHolder) holder).finishstatus.setVisibility(View.INVISIBLE);
                                ((detailViewHolder) holder).status.setText("换货申请中");
                                break;
                            case "1":
                                ((detailViewHolder) holder).finishstatus.setVisibility(View.INVISIBLE);
                                ((detailViewHolder) holder).status.setText("换货客服处理");
                                break;
                            case "2":
                                ((detailViewHolder) holder).finishstatus.setVisibility(View.INVISIBLE);
                                ((detailViewHolder) holder).status.setText("换货完成");
                                break;
                        }
                        break;
                    case "2":
                        switch (list.get(position).getInfo().getStatus()) {
                            case "0":
                                ((detailViewHolder) holder).finishstatus.setVisibility(View.INVISIBLE);
                                ((detailViewHolder) holder).status.setText("退款申请中");
                                break;
                            case "1":
                                ((detailViewHolder) holder).finishstatus.setVisibility(View.INVISIBLE);
                                ((detailViewHolder) holder).status.setText("退款客服处理");
                                break;
                            case "2":
                                ((detailViewHolder) holder).finishstatus.setVisibility(View.INVISIBLE);
                                ((detailViewHolder) holder).status.setText("退款完成");
                                break;
                        }
                        break;
                }
            } else {
                    switch (statusclick) {
                        case 1:
                            ((detailViewHolder) holder).status.setVisibility(View.INVISIBLE);
                            ((detailViewHolder) holder).finishstatus.setVisibility(View.INVISIBLE);
                            break;
                        case 2:
                            ((detailViewHolder) holder).finishstatus.setVisibility(View.INVISIBLE);
                            ((detailViewHolder) holder).status.setText("申请退款");
                            ((detailViewHolder) holder).status.setVisibility(View.VISIBLE);
                            ((detailViewHolder) holder).status.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(context, ApplyReturnActivity.class);
                                    intent.putExtra("id", list.get(position).getOrder_id());
                                    context.startActivity(intent);
//                                    GoodsDAO.applyRefund(list.get(position).getOrder_id(), list.get(position).getGoods_id(),
//                                            list.get(position).getSpec_key(), ordersn, new BaseCallBack() {
//                                                @Override
//                                                public void success(Object data) {
//
//                                                }
//
//                                                @Override
//                                                public void failed(int errorCode, Object data) {
//                                                    Toast.makeText(context, "" + data, Toast.LENGTH_SHORT).show();
//                                                }
//                                            });
                                }
                            });
                            break;
                        case 3:
                            ((detailViewHolder) holder).finishstatus.setVisibility(View.INVISIBLE);
                            ((detailViewHolder) holder).status.setText("确认收货");
                            ((detailViewHolder) holder).status.setVisibility(View.VISIBLE);
                            ((detailViewHolder) holder).status.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
//                                    new AlertDialog.Builder(context)
//                                            .setTitle("收货")
//                                            .setMessage("确认收货")
//                                            .setPositiveButton("确认", new DialogInterface.OnClickListener() {
//                                                @Override
//                                                public void onClick(final DialogInterface dialog, int which) {
//                                                    OrderDAO.orderConfirm(list.get(position).getOrder_id(), new BaseCallBack() {
//                                                        @Override
//                                                        public void success(Object data) {
//                                                            dialog.dismiss();
//                                                            Toast.makeText(context, "确认收货", Toast.LENGTH_SHORT).show();
//                                                        }
//
//                                                        @Override
//                                                        public void failed(int errorCode, Object data) {
//                                                            dialog.dismiss();
//                                                            Toast.makeText(context, "" + data, Toast.LENGTH_SHORT).show();
//                                                        }
//                                                    });
//                                                }
//                                            })
//                                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                                                @Override
//                                                public void onClick(DialogInterface dialog, int which) {
//                                                    dialog.dismiss();
//                                                }
//                                            })
//                                            .create()
//                                            .show();
                                }
                            });
                            break;
                        case 4:
                            ((detailViewHolder) holder).finishstatus.setVisibility(View.VISIBLE);
                            ((detailViewHolder) holder).finishstatus.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent i = new Intent(context, ReportActivity.class);
                                    i.putExtra("type", 1);
                                    i.putExtra("orderid", list.get(position).getOrder_id());
                                    i.putExtra("ordersn", list.get(position).getGoods_sn());
                                    i.putExtra("goodsid", list.get(position).getGoods_id());
                                    i.putExtra("spec_key", list.get(position).getSpec_key());
                                    context.startActivity(i);
                                }
                            });
                            ((detailViewHolder) holder).status.setText("去评价");
                            ((detailViewHolder) holder).status.setVisibility(View.VISIBLE);
                            ((detailViewHolder) holder).status.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent i = new Intent(context, AddCommentsActivity.class);
                                    i.putExtra("id", list.get(position).getGoods_id());
                                    i.putExtra("orderid", list.get(position).getOrder_id());
                                    i.putExtra("img", list.get(position).getOriginal_img());
                                    context.startActivity(i);
                                }
                            });
                            break;
                        default:
                            ((detailViewHolder) holder).finishstatus.setVisibility(View.INVISIBLE);
                            ((detailViewHolder) holder).status.setText("去评价");
                            ((detailViewHolder) holder).status.setVisibility(View.INVISIBLE);
                            ((detailViewHolder) holder).status.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            });
                            break;
                    }

            }


        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class detailViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_goods)
        ImageView ivGoods;
        @BindView(R.id.tv_goods_name)
        TextView tvGoodsName;
        @BindView(R.id.tv_meal)
        TextView tvMeal;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.goodscount)
        TextView goodscount;
        @BindView(R.id.finishstatus)
        TextView finishstatus;
        @BindView(R.id.status)
        TextView status;

        public detailViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
