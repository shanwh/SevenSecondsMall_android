package com.yidu.sevensecondmall.views.holder;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yidu.sevensecondmall.Activity.Order.OrderDetailActivity;
import com.yidu.sevensecondmall.Activity.UserCenter.LogisticsActivity;
import com.yidu.sevensecondmall.DAO.OrderDAO;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.OrderMessage.OrderListBean;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.i.IEventTag;
import com.yidu.sevensecondmall.i.LoadDataEvent;
import com.yidu.sevensecondmall.utils.ToastUtil;
import com.yidu.sevensecondmall.views.adapter.FreeAdapter;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.MultiTypeAdapter;
import com.yidu.sevensecondmall.views.adapter.orderItemAdapter;
import com.yidu.sevensecondmall.views.adapter.smallImageAdapter;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2017/3/29 0029.
 */
public class OrderAllHolder extends BaseContextViewHolder<OrderListBean> {
    private orderItemAdapter itemadapter;
    private smallImageAdapter imgadapter;
    private FreeAdapter freeAdapter;


    public OrderAllHolder(View itemView, Context context) {
        super(itemView, context);
    }

    @Override
    public void setUpView(final OrderListBean model, final int position, final MultiTypeAdapter adapter) {
        final Context context = getHolderContext();
        TextView tv_orderId = (TextView) getView(R.id.tv_order_id);
        TextView tv_storename = (TextView) getView(R.id.tv_store_name);
        TextView tv_num = (TextView) getView(R.id.tv_total_num);
        String num = "共有" + model.getGoods_num() + "件商品";
        tv_num.setText(num);
        tv_orderId.setText(model.getOrder_sn()+"");
        tv_orderId.setVisibility(View.GONE);
        tv_storename.setText(model.getShop_name()+"");
        TextView tv_status = (TextView) getView(R.id.tv_status);
        TextView tv_pay_price = (TextView) getView(R.id.tv_pay_price);
        tv_pay_price.setText("合计: ￥" + model.getTotal_amount() + "(含运费0.00)");

        final String order_status_code = model.getOrder_status_code();
        RecyclerView goodsList = (RecyclerView) getView(R.id.goods_list);
        RecyclerView horList = (RecyclerView) getView(R.id.hor_list);
//        TextView tv_cancel = (TextView)getView(R.id.tv_cancel);
//        tv_cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new AlertDialog.Builder(context)
//                        .setTitle("取消订单")
//                        .setMessage("取消订单?\n确认取消该订单")
//                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(final DialogInterface dialog, int which) {
//                                OrderDAO.cancelOrder(model.getOrder_id(), new BaseCallBack() {
//                                    @Override
//                                    public void success(Object data) {
//                                        EventBus.getDefault().post(new LoadDataEvent(IEventTag.LOAD_DATA, 1, 0));
//                                        EventBus.getDefault().post(new LoadDataEvent(IEventTag.LOAD_DATA, 1, 1));
//                                    }
//
//                                    @Override
//                                    public void failed(int errorCode, Object data) {
//                                        ToastUtil.showToast(context, data+"");
//                                    }
//                                });
//                            }
//                        })
//                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        })
//                        .create()
//                        .show();
//            }
//        });
        if (model.isFree()) {
//            getView(R.id.rl_money).setVisibility(View.GONE);
            tv_status.setText(model.getFree_status_desc());
            goodsList.setVisibility(View.VISIBLE);
            horList.setVisibility(View.GONE);
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            goodsList.setLayoutManager(layoutManager);
            freeAdapter = new FreeAdapter(context, model.getGoods_list());
            goodsList.setAdapter(freeAdapter);
        } else {
            tv_status.setText(model.getOrder_status_desc());
            if (model.getGoods_list().size() == 1 && !model.getOrder_status_code().equals("WAITCOMMENT")) {
                goodsList.setVisibility(View.VISIBLE);
                horList.setVisibility(View.GONE);
                LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                goodsList.setLayoutManager(layoutManager);
                itemadapter = new orderItemAdapter(context, model.getGoods_list(), false, model, OrderAllHolder.this);
                goodsList.setAdapter(itemadapter);

            } else if (model.getGoods_list().size() > 1 && !model.getOrder_status_code().equals("WAITCOMMENT")) {
                goodsList.setVisibility(View.GONE);
                horList.setVisibility(View.VISIBLE);
                LinearLayoutManager manager = new LinearLayoutManager(context);
                manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                horList.setLayoutManager(manager);
                imgadapter = new smallImageAdapter(context, model.getGoods_list(), model, OrderAllHolder.this);
                horList.setAdapter(imgadapter);


            } else if (model.getOrder_status_code().equals("WAITCOMMENT")) {
                goodsList.setVisibility(View.VISIBLE);
                horList.setVisibility(View.GONE);
                LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                goodsList.setLayoutManager(layoutManager);
                itemadapter = new orderItemAdapter(context, model.getGoods_list(), false, model, OrderAllHolder.this);
                goodsList.setAdapter(itemadapter);
            }

            getView(R.id.order_linear).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, OrderDetailActivity.class);
                    i.putExtra("orderid", model.getOrder_id());
                    i.putExtra("buyBackStatus", model.getBuy_back_status());
                    i.putExtra("buyBackAmount", model.getBuy_back_amount());
                    i.putExtra("ordersn", model.getOrder_sn());
                    i.putExtra("status", model.getOrder_status_code());
                    context.startActivity(i);
                }
            });

            TextView tvShipOrCancle = (TextView) getView(R.id.tv_ship_or_cancel);
            tvShipOrCancle.setVisibility(View.GONE);

            final TextView tvNext = (TextView) getView(R.id.tv_sure_or_cancel);
            tvNext.setVisibility(View.VISIBLE);
            if (order_status_code != null) {
                switch (order_status_code) {
                    case "WAITSEND":
                        tvNext.setVisibility(View.GONE);

//                        switch (model.getBuy_back_status()){
//
//                            default:
//                                tvNext.setVisibility(View.GONE);
//                                tv_cancel.setVisibility(View.GONE);
//                                break;
//                        }
                        break;
                    case "WAITPAY":
                        tvShipOrCancle.setVisibility(View.VISIBLE);
                        tvShipOrCancle.setText("取消订单");
                        tvNext.setText("待支付");
                        break;
                    case "WAITRECEIVE":
                        tvNext.setText("确认收货");
                        tvShipOrCancle.setVisibility(View.VISIBLE);
                        tvShipOrCancle.setText("查看物流");
                        break;
                    case "WAITCOMMENT":
                        tvNext.setText("去评价");
                        tvShipOrCancle.setVisibility(View.VISIBLE);
                        tvShipOrCancle.setText("查看物流");
                        break;
                    case "FINISH":
                        tvNext.setText("查看详情");
                        break;
                }

                tvShipOrCancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (view instanceof TextView) {
                            if (((TextView) view).getText().equals("取消订单")) {
                                new AlertDialog.Builder(context)
                                        .setTitle("取消订单")
                                        .setMessage("取消订单?\n确认取消该订单")
                                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(final DialogInterface dialog, int which) {
                                                OrderDAO.cancelOrder(model.getOrder_id(), new BaseCallBack() {
                                                    @Override
                                                    public void success(Object data) {
                                                        EventBus.getDefault().post(new LoadDataEvent(IEventTag.LOAD_DATA, 1, 0));
                                                        EventBus.getDefault().post(new LoadDataEvent(IEventTag.LOAD_DATA, 1, 1));
                                                    }

                                                    @Override
                                                    public void failed(int errorCode, Object data) {
                                                        ToastUtil.showToast(context, data + "");
                                                    }
                                                });
                                            }
                                        })
                                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        })
                                        .create()
                                        .show();
                            } else if (((TextView) view).getText().equals("查看物流")) {
                                Log.e("invoice_________no","-------------------"+model.getInvoice_no());
                                if(null ==model.getInvoice_no()||model.getInvoice_no().equals("null")||null==model.getShipping_code()){
                                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(context,"物流号为空",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }else {
                                    Intent i = new Intent(context, LogisticsActivity.class);
                                    i.putExtra("invoice_no", model.getInvoice_no());
                                    i.putExtra("shipping_code", model.getShipping_code());
                                    i.putExtra("shipping_name", model.getShipping_name());
                                    Log.e("物流解析"," model.getInvoice_no()-------"+ model.getInvoice_no());
                                    Log.e("物流解析","model.getShipping_code()--------"+model.getShipping_code());
                                    Log.e("物流解析","model.getShipping_name()------"+model.getShipping_name());
                                    context.startActivity(i);
                                }
                            }
                        }
                    }
                });

                tvNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        if (model.getOrder_status_code().equals("WAITRECEIVE")) {
//                            new AlertDialog.Builder(context)
//                                    .setTitle("收货")
//                                    .setMessage("确认收货")
//                                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(final DialogInterface dialog, int which) {
//                                            OrderDAO.orderConfirm(model.getOrder_id(), new BaseCallBack() {
//                                                @Override
//                                                public void success(Object data) {
//                                                    dialog.dismiss();
//                                                    Toast.makeText(context, "确认收货", Toast.LENGTH_SHORT).show();
//                                                }
//
//                                                @Override
//                                                public void failed(int errorCode, Object data) {
//                                                    dialog.dismiss();
//                                                    Toast.makeText(context, "" + data, Toast.LENGTH_SHORT).show();
//                                                }
//                                            });
//                                        }
//                                    })
//                                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            dialog.dismiss();
//                                        }
//                                    })
//                                    .create()
//                                    .show();
//                        } else{
                        Intent i = new Intent(context, OrderDetailActivity.class);
                        i.putExtra("orderid", model.getOrder_id());
                        i.putExtra("ordersn", model.getOrder_sn());
                        i.putExtra("status", model.getOrder_status_code());
                        context.startActivity(i);
//                        }
                    }
                });
            }
        }
    }

    public void toDetail(OrderListBean model) {
        Intent i = new Intent(getHolderContext(), OrderDetailActivity.class);
        i.putExtra("orderid", model.getOrder_id());
        i.putExtra("buyBackStatus", model.getBuy_back_status());
        i.putExtra("buyBackAmount", model.getBuy_back_amount());
        i.putExtra("ordersn", model.getOrder_sn());
        i.putExtra("status", model.getOrder_status_code());
        getHolderContext().startActivity(i);
    }
}

