package com.yidu.sevensecondmall.views.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yidu.sevensecondmall.Activity.Store.StoreHomepageActivity;
import com.yidu.sevensecondmall.DAO.GoodsDAO;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Order.CartlistBean;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.i.PriceEvent;
import com.yidu.sevensecondmall.networks.HttpApi;
import com.yidu.sevensecondmall.utils.LoginUtils;
import com.yidu.sevensecondmall.utils.SpecHelper;
import com.yidu.sevensecondmall.utils.ToastUtil;
import com.yidu.sevensecondmall.views.widget.GlideSquareTransform;
import com.yidu.sevensecondmall.views.widget.iconfonts.IconFontTextView;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/15.
 */
public class goodsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;
    private LayoutInflater inflater;
    private boolean[] state = new boolean[300];
    private boolean[] isAllState = new boolean[300];
    private boolean[] editstate;
    private int price;
    private int[] tvNums;
    private double[] prices;
    private boolean hide = false;
    private List<CartlistBean.CartListBean> list = new ArrayList<CartlistBean.CartListBean>();
    private BigDecimal bigDecimal;
    private ProgressDialog dialog;
    private SpecHelper helper = SpecHelper.getInstance();
    private JSONArray array;
    private boolean order = false;


    public goodsAdapter(Context context, List<CartlistBean.CartListBean> list) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        editstate = new boolean[300];
        tvNums = new int[300];
        prices = new double[300];
        this.list = list;
        array = helper.getJson();
        dialog = new ProgressDialog(context);
        dialog.setMessage("同步数据中");
    }

    public void hide(boolean hide) {
        this.hide = hide;
    }

    public void fromorder(boolean order) {
        this.order = order;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new goodsViewHolder(inflater.inflate(R.layout.layout_gooditem, null));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {
        if (viewHolder instanceof goodsViewHolder) {
            final goodsViewHolder holder = (goodsViewHolder) viewHolder;
            //店名相同的只显示一个
            if (list.get(position).getBusiness_user_id() == null || list.get(position).getBusiness_user_id().equals("")) {
                holder.include.setVisibility(View.GONE);
            } else {
                if (position - 1 >= 0 && list.get(position - 1).getBusiness_user_id().equals(list.get(position).getBusiness_user_id())) {
                    holder.include.setVisibility(View.GONE);
                } else {
//                    maps.put(list.get(position).getBusiness_user_id(),position);
                    holder.include.setVisibility(View.VISIBLE);
                    if (checkFreeShipStatus(position)) {
                        holder.isFree_ship.setText("已免运费");
                    } else {
                        holder.isFree_ship.setText("");
                    }
                    holder.tvStoreName.setText(list.get(position).getShop_name());
                    holder.if_select_store.setText(isAllState[position] ? R.string.icon_select : R.string.icon_un_select);
                    holder.if_select_store.setTextColor(isAllState[position] ? Color.RED : Color.GRAY);
                    holder.tvStoreName.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(context, StoreHomepageActivity.class);
                            context.startActivity(intent);
                        }
                    });

                }
            }
//                if(holder.include.getVisibility()==View.VISIBLE){
//                    holder.if_select_store.setText(isAllState[position]?R.string.icon_select:R.string.icon_un_select);
//                    holder.if_select_store.setTextColor(isAllState[position]?Color.RED:Color.GRAY);
//                }
            if (!state[position]) {

                holder.selectGood.setText(R.string.icon_un_select);
                holder.selectGood.setTextColor(Color.GRAY);
                state[position] = false;
            } else {

                holder.selectGood.setText(R.string.icon_select);
                holder.selectGood.setTextColor(Color.RED);
                state[position] = true;
            }
            if (hide) {
                holder.selectGood.setVisibility(View.INVISIBLE);
            } else {
                holder.selectGood.setVisibility(View.VISIBLE);
            }

            if (!editstate[position]) {
//                holder.stateIn.setVisibility(View.VISIBLE);
//                holder.stateEdit.setVisibility(View.GONE);
//                holder.tvNum.setVisibility(View.VISIBLE);
                if (!hide) {
                    holder.llEdit.setVisibility(View.VISIBLE);
                    holder.tvNum.setVisibility(View.INVISIBLE);
                    holder.xtext.setVisibility(View.INVISIBLE);
                } else {
                    holder.llEdit.setVisibility(View.GONE);
                    holder.tvNum.setVisibility(View.VISIBLE);
                    holder.xtext.setVisibility(View.VISIBLE);
                }
                holder.ifTvDel.setVisibility(View.GONE);
                holder.tvEditNum.setText(holder.tvNum.getText().toString());
            } else {
//                holder.tvNum.setVisibility(View.GONE);
                if (!hide) {
                    holder.llEdit.setVisibility(View.VISIBLE);
                    holder.tvNum.setVisibility(View.INVISIBLE);
                    holder.xtext.setVisibility(View.INVISIBLE);
                } else {
                    holder.llEdit.setVisibility(View.GONE);
                    holder.tvNum.setVisibility(View.VISIBLE);
                    holder.xtext.setVisibility(View.VISIBLE);
                }
                holder.ifTvDel.setVisibility(View.VISIBLE);
//                holder.stateIn.setVisibility(View.GONE);
//                holder.stateEdit.setVisibility(View.VISIBLE);holder).tvEditNum.setText(holder.tvNum.getText().toString());
            }
            holder.goodsName.setText(list.get(position).getGoods_name() + "");
            holder.price.setText(list.get(position).getGoods_price() + "");
            holder.tvNum.setText(list.get(position).getGoods_num() + "");
            holder.goodsSort.setText(list.get(position).getSpec_key_name() + "");
            holder.tvEditNum.setText(list.get(position).getGoods_num() + "");

            holder.ifTvMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //同步信息给后台
                    String s = holder.tvEditNum.getText().toString();
                    int i = Integer.parseInt(s);
                    if (i != 1) {

                        updateGoodsNumByUser(position, holder, -1);
                        //同步后台数据
                        updateData(position);

                    }
                }
            });

            holder.ifTvPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!dialog.isShowing()) {
                        dialog.show();
                    }

                    updateGoodsNumByUser(position, holder, 1);
                    updateData(position);

                }
            });

            tvNums[position] = Integer.parseInt(holder.tvNum.getText().toString());
            if (!holder.price.getText().toString().equals("")) {
                prices[position] = Float.parseFloat(holder.price.getText().toString());
            }
//            Log.e("img",list.get(position).getOriginal_img());
            Glide.with(context)
                    .load(HttpApi.getFullImageUrl(list.get(position).getOriginal_img()))
                    .transform(new GlideSquareTransform(context))
                    .into(holder.goodsPic);

            holder.ifTvDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (LoginUtils.isLogin()) {
                        GoodsDAO.delCart(list.get(position).getId(), true, new BaseCallBack() {
                            @Override
                            public void success(Object data) {
                                ToastUtil.showToast(context, "删除成功");
                                list.remove(position);
                                notifyItemRemoved(position);
                                //删除成功之后做一次数组的数据交换
                                for (int i = position; i < list.size(); i++) {
                                    tvNums[position] = tvNums[position + 1];
                                    tvNums[list.size()] = 0;

                                    prices[position] = prices[position + 1];
                                    prices[list.size()] = 0;

                                    state[position] = state[position + 1];
                                    state[list.size()] = false;

                                    isAllState[position] = isAllState[position + 1];
                                    isAllState[list.size()] = false;

                                    editstate[position] = editstate[position + 1];
                                    editstate[list.size()] = false;
                                }
                                if (position != list.size()) {
                                    notifyItemRangeChanged(position, list.size());
                                }
                                if (list.size() == 0) {
                                    EventBus.getDefault().post(new PriceEvent("refresh"));
                                }


                            }

                            @Override
                            public void failed(int errorCode, Object data) {
                                ToastUtil.showToast(context, data + "");
                            }
                        });
                    } else {
                        GoodsDAO.delCart(list.get(position).getId(), false, new BaseCallBack() {
                            @Override
                            public void success(Object data) {
                                ToastUtil.showToast(context, "删除成功");
                                list.remove(position);
                                notifyItemRemoved(position);
                                //删除成功之后做一次数组的数据交换
                                for (int i = position; i < list.size(); i++) {
                                    tvNums[position] = tvNums[position + 1];
                                    tvNums[list.size()] = 0;

                                    prices[position] = prices[position + 1];
                                    prices[list.size()] = 0;

                                    isAllState[position] = isAllState[position + 1];
                                    isAllState[list.size()] = false;

                                    state[position] = state[position + 1];
                                    state[list.size()] = false;

                                    editstate[position] = editstate[position + 1];
                                    editstate[list.size()] = false;
                                }
                                if (position != list.size()) {
                                    notifyItemRangeChanged(position, list.size());
                                }
                                if (list.size() == 0) {
                                    EventBus.getDefault().post(new PriceEvent("refresh"));
                                }


                            }

                            @Override
                            public void failed(int errorCode, Object data) {
                                ToastUtil.showToast(context, data + "");
                            }
                        });
                    }

                }
            });

//            if (holder.selectGood.getText().equals(R.string.icon_select)) {
//                state[position] = true;
//                EventBus.getDefault().post(new PriceEvent(position, true));
//            } else {
//                state[position] = false;
//                EventBus.getDefault().post(new PriceEvent(position, true));
//            }
            selectListener listener = new selectListener(position);
            holder.rlTable.setOnClickListener(listener);
//            holder.selectGood.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    if(isChecked){
//                        state[position] = true;
//                    }else {
//                        state[position] = false;
//                    }
//                    EventBus.getDefault().post(new PriceEvent(position,true));
//                }
//            });
            holder.if_select_store.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    JSONArray jsonArray = new JSONArray();
                    // TODO: 2017/9/11
                    boolean isallstate = isAllState[position];
                    for (int i = position; i < list.size(); i++) {
                        if (list.get(position).getBusiness_user_id().equals(list.get(i).getBusiness_user_id())) {
                            JSONObject jsonOb = new JSONObject();
                            try {
                                jsonOb.put("businessUserId", Integer.parseInt(list.get(position).getBusiness_user_id()));
                                //如果是选中状态
                                if (isallstate) {
                                    state[i] = false;
                                    isAllState[i] = false;
                                    jsonOb.put("selected", 0);
                                    holder.if_select_store.setText(R.string.icon_un_select);
                                } else {
                                    state[i] = true;
                                    isAllState[i] = true;
                                    jsonOb.put("selected", 1);
                                    holder.if_select_store.setText(R.string.icon_select);
                                }
                                jsonArray.put(jsonOb);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            break;
                        }
                    }
                    updateDataForStore(jsonArray.toString());


                }
            });


        }
    }

    //检查物流状态
    private boolean checkFreeShipStatus(int position) {
        boolean flag = true;
        boolean flage2 = false;
        String id = list.get(position).getBusiness_user_id();
        for (int f = position; f < list.size(); f++) {
            if (list.get(f).getBusiness_user_id().equals(id) && state[f]) {
                flag = (flag && list.get(position).getIs_free_shipping().equals("1"));
                if (!flag) {
                    return false;
                }
                flage2 = true;
            }
        }
        return flage2 ? true : false;
    }

    private void checkStoreStatus(int position) {
        boolean flag = true;
        String id = list.get(position).getBusiness_user_id();
        boolean flaginter = true;
        int posi = -1;
        for (int f = 0; f < list.size(); f++) {
            if (list.get(f).getBusiness_user_id().equals(id)) {
                if (flaginter) {
                    //保存第一个位置
                    posi = f;
                    flaginter = false;
                }

                flag = (flag && state[f]);
                if (!flag) {
                    isAllState[posi] = false;
                    notifyDataSetChanged();
                    return;
                }
            }
        }
        isAllState[posi] = true;
//        list.get(posi).if_select_store.setText(R.string.icon_un_select);
        notifyDataSetChanged();

    }

    private void updateGoodsNumByUser(int position, goodsViewHolder holder, int num) {
        if (!dialog.isShowing()) {
            dialog.show();
        }
        try {
            JSONObject obj = array.getJSONObject(position);
            int tvEditNum = Integer.parseInt(holder.tvEditNum.getText().toString());
            int result = tvEditNum + num;
            if (tvNums[position] > 0) {
                tvNums[position] = tvNums[position] + num;
            }
            if (tvEditNum > 0 && tvEditNum < 999) {
                list.get(position).setGoods_num(result);
                holder.tvEditNum.setText(result + "");
                holder.tvNum.setText(result + "");
            }
            obj.put("goodsNum", String.valueOf(result));
            array.put(position, obj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //商店是否为空
    public boolean isEmpty() {
        for (int i = 0; i < getItemCount(); i++) {
            if (state[i]) {
                return true;
            }
        }
        return false;
    }

    public void changeState(boolean newstate) {
        for (int i = 0; i < getItemCount(); i++) {
            state[i] = newstate;
            isAllState[i] = newstate;
        }
        this.notifyDataSetChanged();
    }

    public void changeEditState(boolean newstate) {
        for (int i = 0; i < getItemCount(); i++) {
            editstate[i] = newstate;
        }
        notifyDataSetChanged();
    }

    //计算一个商店里所有物品的价格
    public double getTotalPrice() {
        double totalprice = 0;
        for (int i = 0; i < getItemCount(); i++) {
            if (state[i]) {
                totalprice = totalprice + (prices[i] * tvNums[i]);
                String str = String.format("%.2f", totalprice);
                totalprice = Double.parseDouble(str);
            }
        }
        return totalprice;
    }

    //计算几个商店购买的件数
    public int getCount() {
        int tvEditNums = 0;
        for (int i = 0; i < getItemCount(); i++) {
            if (state[i]) {
                tvEditNums = tvEditNums + tvNums[i];
            }

        }
        return tvEditNums;
    }

    public class goodsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.include)
        View include;
        @BindView(R.id.tv_store_name)
        TextView tvStoreName;
        @BindView(R.id.select_good)
        IconFontTextView selectGood;
        @BindView(R.id.goods_pic)
        ImageView goodsPic;
        @BindView(R.id.goods_name)
        TextView goodsName;
        @BindView(R.id.goods_sort)
        TextView goodsSort;
        @BindView(R.id.priceleft)
        TextView priceleft;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.tv_num)
        TextView tvNum;
        @BindView(R.id.x)
        TextView xtext;
        @BindView(R.id.if_tv_minus)
        IconFontTextView ifTvMinus;
        @BindView(R.id.tv_edit_num)
        TextView tvEditNum;
        @BindView(R.id.if_tv_plus)
        IconFontTextView ifTvPlus;
        @BindView(R.id.ll_edit)
        LinearLayout llEdit;
        @BindView(R.id.if_tv_del)
        IconFontTextView ifTvDel;
        @BindView(R.id.state_in)
        RelativeLayout stateIn;
        @BindView(R.id.rl_item)
        RelativeLayout rlItem;
        @BindView(R.id.rl_table)
        RelativeLayout rlTable;
        @BindView(R.id.if_select_good_store)
        IconFontTextView if_select_store;
        @BindView(R.id.isfree_ship)
        TextView isFree_ship;

        public goodsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class selectListener implements View.OnClickListener {
        private int position;

        public selectListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View view) {
            //同步选择信息
            if (!order) {
                if (!dialog.isShowing()) {
                    dialog.show();
                }
                state[position] = !state[position];
                checkStoreStatus(position);
                try {
                    JSONObject obj = array.getJSONObject(position);
                    obj.put("selected", state[position] ? "1" : "0");
                    array.put(position, obj);
                    list.get(position).setSelected(1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                updateData(position);

            }

        }
    }


    //商家全部信息同步后台数据
    public void updateDataForStore(String json) {
        //同步信息
        if (LoginUtils.isLogin()) {
            GoodsDAO.sendCartJsonWithTokenByStore(json, new BaseCallBack() {
                @Override
                public void success(Object data) {
                    dialog.dismiss();
                    notifyDataSetChanged();
                }

                @Override
                public void failed(int errorCode, Object data) {
                    dialog.dismiss();
                    notifyDataSetChanged();
                }
            });
            PriceEvent event = new PriceEvent("update");
            event.founctionTag = 1;
            EventBus.getDefault().post(event);
        } else {
            GoodsDAO.sendCartJsonWithOutTokenBySotre(array.toString(), new BaseCallBack() {
                @Override
                public void success(Object data) {
                    dialog.dismiss();
                    notifyDataSetChanged();
                }

                @Override
                public void failed(int errorCode, Object data) {
                    dialog.dismiss();
                    notifyDataSetChanged();
                }
            });
            PriceEvent event = new PriceEvent("update");
            event.founctionTag = 1;
            EventBus.getDefault().post(event);
        }

    }


    //同步后台数据
    public void updateData(final int position) {
        //同步信息
        if (LoginUtils.isLogin()) {
            GoodsDAO.sendCartJsonWithToken(array.toString(), new BaseCallBack() {
                @Override
                public void success(Object data) {
                    dialog.dismiss();
                    notifyDataSetChanged();
                    EventBus.getDefault().post(new PriceEvent(position, true));
                }

                @Override
                public void failed(int errorCode, Object data) {
                    dialog.dismiss();
                    notifyDataSetChanged();
                    EventBus.getDefault().post(new PriceEvent(position, true));
                }
            });
        } else {
            GoodsDAO.sendCartJsonWithOutToken(array.toString(), new BaseCallBack() {
                @Override
                public void success(Object data) {
                    dialog.dismiss();
                    notifyDataSetChanged();
                    EventBus.getDefault().post(new PriceEvent(position, true));
                }

                @Override
                public void failed(int errorCode, Object data) {
                    dialog.dismiss();
                    notifyDataSetChanged();
                    EventBus.getDefault().post(new PriceEvent(position, true));
                }
            });
        }
    }

}
