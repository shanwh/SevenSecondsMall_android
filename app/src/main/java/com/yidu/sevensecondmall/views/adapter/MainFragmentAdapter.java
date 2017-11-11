package com.yidu.sevensecondmall.views.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Main.MainBean;
import com.yidu.sevensecondmall.networks.HttpApi;
import com.yidu.sevensecondmall.views.widget.itemdecorator.DividerItemDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2017/10/24.
 */

public class MainFragmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public MainFragmentAdapter(Context context, List list) {
        this.context = context;
        this.list = list;
    }

    List<List> list;
    private Context context;
    //    private MainBean.ResultBean resultBean;

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Item(LayoutInflater.from(context).inflate(R.layout.main_recycle, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (position) {
            case 0:
                List<MainBean.ResultBean.NavBean> navBeen = list.get(position);
                if (navBeen.size() > 0) {
                    for (int i = 0; i < navBeen.size(); i++) {
                        MainItem1Adapter mainItem1Adapter = new MainItem1Adapter(context);
                        LinearLayoutManager manager = new LinearLayoutManager(context);
                        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                        ((Item) holder).recycleMainItem.setHasFixedSize(true);
                        ((Item) holder).recycleMainItem.setLayoutManager(manager);
                        ((Item) holder).recycleMainItem.setAdapter(mainItem1Adapter);
                        ((Item) holder).recycleMainItem.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return true;
                            }
                        });
                        mainItem1Adapter.addAll(navBeen);
                    }
                }
                break;
            case 1:
                List<MainBean.ResultBean.ZoneBean> zoneBean = list.get(position);
                if (zoneBean.size() > 0) {
                    for (int i = 0; i < zoneBean.size(); i++) {
                        MainItem2Adapter mainItem2Adapter = new MainItem2Adapter(context);
                        ((Item) holder).recycleMainItem.setHasFixedSize(true);
                        ((Item) holder).recycleMainItem.setFocusable(true);
                        ((Item) holder).recycleMainItem.setLayoutManager(new LinearLayoutManager(context));
                        ((Item) holder).recycleMainItem.setAdapter(mainItem2Adapter);
                        ((Item) holder).recycleMainItem.addItemDecoration(new DividerItemDecoration(context));
                        mainItem2Adapter.addAll(zoneBean);
                    }
                }
                break;
            case 2:
                List<MainBean.ResultBean.ShopBean> shop = list.get(position);
                if (shop.size() > 0) {
                    for (int i = 0; i < shop.size(); i++) {
                        MainItem3Adapter  mainItem3Adapter = new MainItem3Adapter(context);
                        ((Item) holder).recycleMainItem.setHasFixedSize(true);
                        ((Item) holder).recycleMainItem.setFocusable(true);
                        ((Item) holder).recycleMainItem.setLayoutManager(new LinearLayoutManager(context));
                        ((Item) holder).recycleMainItem.setAdapter(mainItem3Adapter);
                        ((Item) holder).recycleMainItem.addItemDecoration(new DividerItemDecoration(context));
                        mainItem3Adapter.addAll(shop);
                    }
                }
                break;
        }
//        if (holder instanceof Item1) {
//            Log.e("Item1==", position + "============" + resultBean.getNav().size());
//            if (resultBean.getNav().size() > 0) {
//                for (int i = 0; i < resultBean.getNav().size(); i++) {
//                    MainBean.ResultBean.NavBean navBean = resultBean.getNav().get(i);
//                    MainItem1Adapter mainItem1Adapter = new MainItem1Adapter(context);
//                    if (navBean != null) {
//                        LinearLayoutManager manager = new LinearLayoutManager(context);
//                        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
//                        ((Item1) holder).recycleMainItem.setHasFixedSize(true);
//                        ((Item1) holder).recycleMainItem.setLayoutManager(manager);
//                        ((Item1) holder).recycleMainItem.setAdapter(mainItem1Adapter);
//                        mainItem1Adapter.addAll(navBean);
//                        Log.e("mainItem1Adapter", navBean.toString());
//                        Log.e("mainItem1Adapter", mainItem1Adapter.getList().size() + "");
//                    }
//                }
//            }
//        } else if (holder instanceof Item2) {
//            Log.e("Item2==", position + "============" + resultBean.getZone().size());
//
//            for (int i = 0; i < resultBean.getZone().size(); i++) {
//                MainBean.ResultBean.ZoneBean zoneBean = resultBean.getZone().get(i);
//                if (zoneBean != null) {
//                    MainItem2Adapter mainItem2Adapter = new MainItem2Adapter(context);
//                    ((Item2) holder).recycleMainItem.setHasFixedSize(true);
//                    ((Item2) holder).recycleMainItem.setFocusable(true);
//                    ((Item2) holder).recycleMainItem.setLayoutManager(new LinearLayoutManager(context));
//                    ((Item2) holder).recycleMainItem.setAdapter(mainItem2Adapter);
//                    mainItem2Adapter.addAll(zoneBean);
//                }
//
//            }
//        } else if (holder instanceof Item3) {
//
//            Log.e("Item3==", position + "============" + resultBean.getShop().size());
//
//        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Item extends RecyclerView.ViewHolder {
        @BindView(R.id.recycle_main_item)
        RecyclerView recycleMainItem;

        public Item(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
