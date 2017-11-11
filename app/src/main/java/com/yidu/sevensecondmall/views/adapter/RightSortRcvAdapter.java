package com.yidu.sevensecondmall.views.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Order.STCategoryBean;
import com.yidu.sevensecondmall.views.widget.CustomGridLayoutManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/9 0009.
 */
public class RightSortRcvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Activity context;
    private ArrayList<STCategoryBean> list = new ArrayList<>();
    private LayoutInflater inflater;

    public RightSortRcvAdapter(Activity context, ArrayList<STCategoryBean> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    public void refreshData(ArrayList<STCategoryBean> list){
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(inflater.inflate(R.layout.item_right_sort, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            STCategoryBean bean = list.get(position);
            ((ItemViewHolder) holder).tvName.setText(bean.getMobile_name());
            ChildRcvAdapter adapter = new ChildRcvAdapter(context, (ArrayList<STCategoryBean.SubCategoryBean>) bean.getSub_category());
            CustomGridLayoutManager layoutManager = new CustomGridLayoutManager(context, 3);
            layoutManager.setScrollEnabled(false);
//        HeaderSpanSizeLookup headerSpanSizeLookup = new HeaderSpanSizeLookup(mainRecylceAdapter, layoutManager);
//        layoutManager.setSpanSizeLookup(headerSpanSizeLookup);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            ((ItemViewHolder) holder).rvChild.setLayoutManager(layoutManager);
            ((ItemViewHolder) holder).rvChild.setAdapter(adapter);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.rvChild)
        RecyclerView rvChild;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}