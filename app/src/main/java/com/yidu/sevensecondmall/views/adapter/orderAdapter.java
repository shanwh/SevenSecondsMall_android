package com.yidu.sevensecondmall.views.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yidu.sevensecondmall.Activity.Order.OrderDetailActivity;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.OrderMessage.OrderListBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 已经弃用
 * Created by Administrator on 2017/5/6.
 */
public class orderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;
    private ArrayList<OrderListBean> list;
    private LayoutInflater inflater;
    private int position = 0;
    private boolean comment = false;

    public orderAdapter(Context context, ArrayList<OrderListBean> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        OrderViewHolder holder = new OrderViewHolder(inflater.inflate(R.layout.item_order, null), list, position);
        position++;
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OrderViewHolder) {
            ((OrderViewHolder) holder).tvOrderId.setText("订单编号: " + list.get(position).getOrder_sn());
            ((OrderViewHolder) holder).tvStatus.setText(list.get(position).getOrder_status_desc());
            ((OrderViewHolder) holder).tvStoreName.setText(list.get(position).getShop_name() + "");
//            ((OrderViewHolder) holder).tvTotalNum.setText("共有" + list.get(position).getGoods_list().size() + "件商品");
            ((OrderViewHolder) holder).tvTotalNum.setText("共有" + list.get(position).getGoods_num() + "件商品");
            ((OrderViewHolder) holder).tvPayPrice.setText("合计: ￥" + list.get(position).getTotal_amount() + "" + "(含运费0.00)");
            ((OrderViewHolder) holder).tvShipOrCancel.setVisibility(View.GONE);
            ((OrderViewHolder) holder).tvSureOrCancel.setVisibility(View.VISIBLE);
            switch (list.get(position).getOrder_status_code()) {
                case "WAITSEND":
                    ((OrderViewHolder) holder).tvSureOrCancel.setVisibility(View.INVISIBLE);
                    break;
                case "WAITPAY":
                    ((OrderViewHolder) holder).tvSureOrCancel.setText("待支付");
                    break;
                case "WAITRECEIVE":
                    ((OrderViewHolder) holder).tvSureOrCancel.setText("确认收货");
                    ((OrderViewHolder) holder).tvShipOrCancel.setVisibility(View.VISIBLE);
                    break;
                case "WAITCOMMENT":
                    ((OrderViewHolder) holder).tvShipOrCancel.setVisibility(View.VISIBLE);
                    ((OrderViewHolder) holder).tvSureOrCancel.setText("去评价");
                    break;
                case "FINISH":
                    ((OrderViewHolder) holder).tvSureOrCancel.setText("查看详情");
                    break;
            }

//            if (list.get(position).getGoods_list().size() == 1 && !list.get(position).getOrder_status_code().equals("WAITCOMMENT")) {
//                ((OrderViewHolder) holder).goodsList.setVisibility(View.VISIBLE);
//                ((OrderViewHolder) holder).horList.setVisibility(View.GONE);
//                LinearLayoutManager layoutManager = new LinearLayoutManager(context);
//                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//                ((OrderViewHolder) holder).goodsList.setLayoutManager(layoutManager);
////                ((OrderViewHolder) holder).adapter = new orderItemAdapter(context, list.get(position).getGoods_list(), false);
//                ((OrderViewHolder) holder).goodsList.setAdapter(((OrderViewHolder) holder).adapter);
//
//            } else if (list.get(position).getGoods_list().size() > 1 && !list.get(position).getOrder_status_code().equals("WAITCOMMENT")) {
//                ((OrderViewHolder) holder).goodsList.setVisibility(View.GONE);
//                ((OrderViewHolder) holder).horList.setVisibility(View.VISIBLE);
//                LinearLayoutManager manager = new LinearLayoutManager(context);
//                manager.setOrientation(LinearLayoutManager.HORIZONTAL);
//                ((OrderViewHolder) holder).horList.setLayoutManager(manager);
////                ((OrderViewHolder) holder).imgadapter = new smallImageAdapter(context, list.get(position).getGoods_list());
//                ((OrderViewHolder) holder).horList.setAdapter(((OrderViewHolder) holder).imgadapter);
//
//            } else if (list.get(position).getOrder_status_code().equals("WAITCOMMENT")) {
//                ((OrderViewHolder) holder).goodsList.setVisibility(View.VISIBLE);
//                ((OrderViewHolder) holder).horList.setVisibility(View.GONE);
//                LinearLayoutManager layoutManager = new LinearLayoutManager(context);
//                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//                ((OrderViewHolder) holder).goodsList.setLayoutManager(layoutManager);
////                ((OrderViewHolder) holder).adapter = new orderItemAdapter(context, this.list.get(position).getGoods_list(), true);
//                ((OrderViewHolder) holder).goodsList.setAdapter(((OrderViewHolder) holder).adapter);
//            }
            ((OrderViewHolder) holder).orderLinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, OrderDetailActivity.class);
                    i.putExtra("orderid", list.get(position).getOrder_id());
                    i.putExtra("ordersn", list.get(position).getOrder_sn());
                    i.putExtra("status", list.get(position).getOrder_status_code());
                    context.startActivity(i);
                }
            });

            ((OrderViewHolder) holder).tvSureOrCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (list.get(position).getOrder_status_code().equals("WAITRECEIVE")) {
//                        new AlertDialog.Builder(context)
//                                .setTitle("收货")
//                                .setMessage("确认收货")
//                                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(final DialogInterface dialog, int which) {
//                                        OrderDAO.orderConfirm(list.get(position).getOrder_id(), new BaseCallBack() {
//                                            @Override
//                                            public void success(Object data) {
//                                                dialog.dismiss();
//                                                Toast.makeText(context,"确认收货",Toast.LENGTH_SHORT).show();
//                                            }
//
//                                            @Override
//                                            public void failed(int errorCode, Object data) {
//                                                dialog.dismiss();
//                                                Toast.makeText(context,""+data,Toast.LENGTH_SHORT).show();
//                                            }
//                                        });
//                                    }
//                                })
//                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        dialog.dismiss();
//                                    }
//                                })
//                                .create()
//                                .show();
                    } else {
                        Intent i = new Intent(context, OrderDetailActivity.class);
                        i.putExtra("orderid", list.get(position).getOrder_id());
                        i.putExtra("ordersn", list.get(position).getOrder_sn());
                        i.putExtra("status", list.get(position).getOrder_status_code());
                        context.startActivity(i);
                    }

                }
            });
        }
    }

    /**
     * @param b1 延长收货
     * @param b2 查看物流
     * @param b3 确认收货/取消订单/支付/评价
     */
    private void setAddAndShipAndCancel(boolean b1, boolean b2, boolean b3) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void clearPoition() {
//        position = 0;
    }


    public class OrderViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.tv_order_id)
        TextView tvOrderId;
        @BindView(R.id.tv_store_name)
        TextView tvStoreName;
        @BindView(R.id.tv_status)
        TextView tvStatus;
        @BindView(R.id.rl_head)
        RelativeLayout rlHead;
        @BindView(R.id.hor_list)
        RecyclerView horList;
        @BindView(R.id.goods_list)
        RecyclerView goodsList;
        @BindView(R.id.rl_item)
        RelativeLayout rlItem;
        @BindView(R.id.tv_total_num)
        TextView tvTotalNum;
        @BindView(R.id.tv_pay_price)
        TextView tvPayPrice;
        //        @BindView(R.id.tv_ship)
//        TextView tvShip;
        @BindView(R.id.tv_ship_or_cancel)
        TextView tvShipOrCancel;
        @BindView(R.id.tv_sure_or_cancel)
        TextView tvSureOrCancel;
        @BindView(R.id.rl_money)
        RelativeLayout rlMoney;
        @BindView(R.id.order_linear)
        LinearLayout orderLinear;

        private ArrayList<OrderListBean> list = new ArrayList<>();
        private orderItemAdapter adapter;
        private smallImageAdapter imgadapter;
        private int position;

        public OrderViewHolder(View itemView, ArrayList<OrderListBean> list, int position) {
            super(itemView);
            this.list = list;
            this.position = position;
            ButterKnife.bind(this, itemView);
        }
    }


}
