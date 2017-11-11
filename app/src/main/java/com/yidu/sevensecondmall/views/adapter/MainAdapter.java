package com.yidu.sevensecondmall.views.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Main.MainBean;
import com.yidu.sevensecondmall.views.widget.widget.BaseHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/10/19.
 */

public class MainAdapter extends RecyclerAdapter<MainBean, MainAdapter.MainHolder> {

    private Context context;

    public static int type = 3;


    public MainAdapter(Context context) {
        this.context = context;
    }


    @Override
    public MainHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.e("viewType===", viewType + "MainAdapter");
//        View view = null;
//        switch (viewType) {
//            case 0:
//                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item1, parent, false);
//                break;
//            case 1:
//                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item2, parent, false);
//                break;
//            case 2:
//                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item3, parent, false);
//                break;

//        }
//        return new MainItem2Holder(view);
        return new MainHolder(LayoutInflater.from(context).inflate(R.layout.main_recycle, parent, false));
    }

    @Override
    public void onBindViewHolder(MainHolder holder, int position) {
        MainBean bean = getItem(position);
//        MainItem2Adapter mainItem2Adapter = new MainItem2Adapter(context);
//        if (bean.getResult().getZone() != null && bean.getResult().getZone().size() > 0) {
//            List<MainBean.ResultBean.ZoneBean> zoneBean = bean.getResult().getZone();
//            holder.recycleMainItem.setHasFixedSize(true);
//            holder.recycleMainItem.setFocusable(true);
//            holder.recycleMainItem.setLayoutManager(new LinearLayoutManager(context));
//            holder.recycleMainItem.setAdapter(mainItem2Adapter);
//            mainItem2Adapter.addAll(zoneBean);
//        }
        switch (position) {
            case 0:
                MainItem1Adapter mainItem1Adapter = new MainItem1Adapter(context);
                if (bean.getResult().getNav() != null && bean.getResult().getNav().size() > 0) {
                    List<MainBean.ResultBean.NavBean> navBeen = bean.getResult().getNav();
                    LinearLayoutManager manager = new LinearLayoutManager(context);
                    manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                    holder.recycleMainItem.setHasFixedSize(true);
                    holder.recycleMainItem.setLayoutManager(manager);
                    holder.recycleMainItem.setAdapter(mainItem1Adapter);
                    mainItem1Adapter.addAll(navBeen);
                    Log.e("mainItem1Adapter", navBeen.toString());
                    Log.e("mainItem1Adapter", mainItem1Adapter.getList().size() + "");
                }
                break;
            case 1:
                MainItem2Adapter mainItem2Adapter = new MainItem2Adapter(context);
                if (bean.getResult().getZone() != null && bean.getResult().getZone().size() > 0) {
                    List<MainBean.ResultBean.ZoneBean> zoneBean = bean.getResult().getZone();
                    holder.recycleMainItem.setHasFixedSize(true);
                    holder.recycleMainItem.setFocusable(true);
                    holder.recycleMainItem.setLayoutManager(new LinearLayoutManager(context));
                    holder.recycleMainItem.setAdapter(mainItem2Adapter);
                    mainItem2Adapter.addAll(zoneBean);
                }
                break;
            case 2:

                break;
        }

    }

    @Override
    public int getItemViewType(int position) {
        return type;
    }

    public class MainHolder extends BaseHolder {

        @BindView(R.id.recycle_main_item)
        RecyclerView recycleMainItem;
        int type;

        public MainHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
