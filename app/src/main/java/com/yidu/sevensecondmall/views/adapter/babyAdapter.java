package com.yidu.sevensecondmall.views.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.yidu.sevensecondmall.Activity.Order.GoodsDetailActivity;
import com.yidu.sevensecondmall.DAO.GoodsDAO;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Order.CollectionBean;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.i.IEventTag;
import com.yidu.sevensecondmall.i.RefreshEvent;
import com.yidu.sevensecondmall.networks.HttpApi;
import com.yidu.sevensecondmall.views.widget.iconfonts.IconFontTextView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/15.
 */
public class babyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Activity context;
    private LayoutInflater inflater;
    private List<CollectionBean.ListBean> list;


    public babyAdapter(Activity context, List<CollectionBean.ListBean> list) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BabyViewHolder(inflater.inflate(R.layout.layout_babyitem, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BabyViewHolder) {
            ((BabyViewHolder) holder).price.setText(list.get(position).getShop_price() + "");
            ((BabyViewHolder) holder).container.setText(list.get(position).getGoods_name() + "");
//            ((BabyViewHolder) holder).price.setText("￥" + list.get(position).getShop_price() + "");
            if (list.get(position).isCart()) {
                ((BabyViewHolder) holder).cartStaus.setBackgroundResource(R.drawable.gray_round_item);
                ((BabyViewHolder) holder).cartStaus.setText("已加入购物车");
            } else {
                ((BabyViewHolder) holder).cartStaus.setBackgroundResource(R.drawable.round_edittext);
                ((BabyViewHolder) holder).cartStaus.setText("加入购物车");
            }
            if (!list.get(position).getOriginal_img().equals("")) {
                Glide.with(context)
                        .load(HttpApi.getFullImageUrl(list.get(position).getOriginal_img() + ""))
                        .asBitmap()
                        .centerCrop()
                        .into(((BabyViewHolder) holder).pic);

            }
            ((BabyViewHolder) holder).llItem.setOnClickListener(new MyClickListener(position));
            ((BabyViewHolder) holder).delete.setOnClickListener(new MyClickListener(position));

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class BabyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.pic)
        ImageView pic;
        @BindView(R.id.container)
        TextView container;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.price_linear)
        RelativeLayout priceLinear;
        @BindView(R.id.cart_staus)
        TextView cartStaus;
        @BindView(R.id.delete)
        IconFontTextView delete;
        @BindView(R.id.ll_item)
        LinearLayout llItem;

        public BabyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


    }

    public class MyClickListener implements View.OnClickListener {
        int position;

        public MyClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.ll_item:
                    Intent intent = new Intent(context, GoodsDetailActivity.class);
                    intent.putExtra("id", list.get(position).getGoods_id());
                    context.startActivity(intent);
                    break;
                case R.id.delete:
                    showPopupWindow(view ,position);
//                    new AlertDialog.Builder(context)
//                            .setTitle("温馨提示")
//                            .setMessage("确认从收藏夹移除吗")
//                            .setPositiveButton("确认", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(final DialogInterface dialog, int which) {
//                                    GoodsDAO.collectGoods(1,String.valueOf(list.get(position).getGoods_id()),new BaseCallBack() {
//                                        @Override
//                                        public void success(Object data) {
//                                            EventBus.getDefault().post(new RefreshEvent(IEventTag.REFRSHCOLLECT));
//                                            dialog.dismiss();
//                                        }
//
//                                        @Override
//                                        public void failed(int errorCode, Object data) {
//                                            Toast.makeText(context,""+data,Toast.LENGTH_SHORT).show();
//                                        }
//                                    });
//
//                                }
//                            })
//                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    }).create().show();
                    break;
            }
            babyAdapter.this.notifyDataSetChanged();
        }
    }


    PopupWindow popupWindow;

    private void showPopupWindow(View view,final int position) {

        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(context).inflate(
                R.layout.pop_tip_window, null);
        // 设置按钮的点击事件
        backgroundAlpha(context, 0.5f);//0.0-1.0

//
//        ImageView iv = (ImageView) contentView.findViewById(R.id.iv);
//        iv.setImageBitmap(bitmap);
        TextView sure = (TextView) contentView.findViewById(R.id.sure);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoodsDAO.collectGoods(1,String.valueOf(list.get(position).getGoods_id()),new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        EventBus.getDefault().post(new RefreshEvent(IEventTag.REFRSHCOLLECT));
                        popupWindow.dismiss();
                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        Toast.makeText(context, "" + data, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        popupWindow.setTouchable(true);

        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Log.i("mengdd", "onTouch : ");

                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(context, 1f);//0.0-1.0
            }
        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(context.getResources().getDrawable(
                R.drawable.alertdiag_bg));
        // 设置好参数之后再show
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);


    }

    public void backgroundAlpha(Activity context, float bgAlpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);
    }
}
