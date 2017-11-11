package com.yidu.sevensecondmall.views.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Order.STCategoryBean;
import com.yidu.sevensecondmall.bean.Order.goodCategoryBean;
import com.yidu.sevensecondmall.i.IEventTag;
import com.yidu.sevensecondmall.i.SortEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/9 0009.
 */
public class ChildRcvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "ChildRcvAdapter";

    private ArrayList<STCategoryBean.SubCategoryBean> list = new ArrayList<>();
    private LayoutInflater inflater;

    public ChildRcvAdapter(Activity context, ArrayList<STCategoryBean.SubCategoryBean> list) {
        this.list = list;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(inflater.inflate(R.layout.item_child_sort, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            STCategoryBean.SubCategoryBean bean = list.get(position);
            ((ItemViewHolder)holder).tv.setText(bean.getMobile_name());
            ((ItemViewHolder)holder).tv.setOnClickListener(new ItemClickListener(position));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv)
        TextView tv;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class ItemClickListener implements View.OnClickListener{
        int position;

        public ItemClickListener(int position){
            this.position = position;
        }

        @Override
        public void onClick(View view) {
            Log.e(TAG, "onClick: "+list.get(position).getId());
            STCategoryBean.SubCategoryBean bean = list.get(position);
            EventBus.getDefault().post(new SortEvent(IEventTag.SEARCH_SORT, bean.getId(), bean.getMobile_name()));
        }
    }
}
