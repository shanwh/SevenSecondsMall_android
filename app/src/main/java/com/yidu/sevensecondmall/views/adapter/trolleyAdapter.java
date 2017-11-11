package com.yidu.sevensecondmall.views.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Order.CartlistBean;
import com.yidu.sevensecondmall.i.PriceEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/15.
 */
public class trolleyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private boolean[] editstate;//记录商店内部商品的编辑状态
    private boolean[] allstate;//记录商店大栏的选中状态
    private int[] prices;//记录每个商店购买的总金额
    private boolean hide = false;
    private ArrayList<goodsAdapter> adapterslist= new ArrayList<goodsAdapter>();
    private List<CartlistBean.CartListBean> list = new ArrayList<CartlistBean.CartListBean>();
    private boolean refresh = false;
    private boolean order = false;

    public trolleyAdapter(Context context,List<CartlistBean.CartListBean> list) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        editstate = new boolean[300];
        allstate = new boolean[300];
        prices = new int[300];
        this.list = list;
    }


    public void hidecheck(boolean hide){
        this.hide = hide;
    }

    public void fromorder(boolean order){
        this.order = order;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TrolleyViewHolder(inflater.inflate(R.layout.layout_trolleyitem, null),list);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof TrolleyViewHolder) {
//            if(allstate[position]){
//                ((TrolleyViewHolder) holder).selectShop.setText(R.string.icon_select);
//            }else {
//                ((TrolleyViewHolder) holder).selectShop.setText(R.string.icon_un_select);
//            }
            if(hide){
//                ((TrolleyViewHolder) holder).selectShop.setVisibility(View.GONE);
                ((TrolleyViewHolder) holder).goodsadapter.hide(true);
            }else {
//                ((TrolleyViewHolder) holder).selectShop.setVisibility(View.VISIBLE);
                ((TrolleyViewHolder) holder).goodsadapter.hide(false);
            }
            //此处修改了所有状态
            if (allstate[position]){
                ((TrolleyViewHolder) holder).goodsadapter.changeState(true);
            }else {
                ((TrolleyViewHolder) holder).goodsadapter.changeState(false);
            }
//            ((TrolleyViewHolder) holder).selectShop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    if (isChecked) {
//                        ((TrolleyViewHolder) holder).goodsadapter.changeState(true);
//                    } else {
//                        ((TrolleyViewHolder) holder).goodsadapter.changeState(false);
//                    }
//                }
//            });
            if(editstate[position]){
//                ((TrolleyViewHolder) holder).editState.setText("完成");
                ((TrolleyViewHolder) holder).goodsadapter.changeEditState(true);
            }else {
//                ((TrolleyViewHolder) holder).editState.setText("编辑");
                ((TrolleyViewHolder) holder).goodsadapter.changeEditState(false);
            }
            EventBus.getDefault().post(new PriceEvent(0,true));
//            ((TrolleyViewHolder) holder).editState.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(!editstate[position]){
//                        editstate[position] = true;
//                        ((TrolleyViewHolder) holder).editState.setText("完成");
//                        ((TrolleyViewHolder) holder).goodsadapter.changeEditState(true);
//                    }else {
//                        editstate[position] = false;
//                        ((TrolleyViewHolder) holder).editState.setText("编辑");
//                        ((TrolleyViewHolder) holder).goodsadapter.changeEditState(false);
//                    }
//                }
//            });
            ((TrolleyViewHolder) holder).goodsadapter.fromorder(order);
            if(!adapterslist.contains(((TrolleyViewHolder) holder).goodsadapter)){
                adapterslist.add(((TrolleyViewHolder) holder).goodsadapter);
            }


        }
    }

    public void setEditState(boolean state){
        for(int i = 0;i < editstate.length;i++){
            editstate[i] = state;
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    //计算所有商店的价格
    public double getTotalPrice(){
        double totalprice = 0;
        if (adapterslist.size() != 0) {
            if (getItemCount() > 0) {
                for (int i = 0; i < getItemCount(); i++) {
                    totalprice = totalprice + adapterslist.get(i).getTotalPrice();
                }
            }
        }
        return totalprice;
    }


    //返回件数
    public int getCount(){
        int count = 0;
        if (adapterslist.size() > 0) {
            for (int i = 0; i < getItemCount(); i++) {
                count = count + adapterslist.get(i).getCount();
            }
        }
        return count;
    }


    //改变所有商店大栏状态
    public void changeAllState(boolean newstate){
        for(int i = 0;i < getItemCount();i++){
            allstate[i] = newstate;

        }
        notifyDataSetChanged();

    }

    //没选中时,改变单个商店状态为选中状态
    public void changesingleState(int position){
        if(!allstate[position]){
            allstate[position] = !allstate[position];
            notifyDataSetChanged();
        }

    }

    //改变编辑状态
    public class TrolleyViewHolder extends RecyclerView.ViewHolder {
//        @BindView(R.id.select_shop)
//        IconFontTextView selectShop;
        @BindView(R.id.goodslist)
        RecyclerView goodslist;
//        @BindView(R.id.edit_state)
//        TextView editState;
//        @BindView(R.id.edit_goods)
//        LinearLayout editGoods;

        private goodsAdapter goodsadapter;
        private List<CartlistBean.CartListBean> list = new ArrayList<CartlistBean.CartListBean>();

        public TrolleyViewHolder(View itemView,List<CartlistBean.CartListBean> list) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.list = list;
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            goodslist.setLayoutManager(layoutManager);
            goodsadapter = new goodsAdapter(context,list);
            goodslist.setAdapter(goodsadapter);
        }


    }
}
