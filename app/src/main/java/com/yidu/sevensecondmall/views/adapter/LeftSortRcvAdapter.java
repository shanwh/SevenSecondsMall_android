package com.yidu.sevensecondmall.views.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.se7en.utils.SystemUtil;
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
public class LeftSortRcvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "LeftSortRcvAdapter";

    private Context context;
    private ArrayList<goodCategoryBean> list = new ArrayList<>();
    private LayoutInflater inflater;
    private int currentId;

    public LeftSortRcvAdapter(Context context, ArrayList<goodCategoryBean> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    public void refreshData(ArrayList<goodCategoryBean> list){
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(inflater.inflate(R.layout.item_left_sort, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            goodCategoryBean bean = list.get(position);
            ((ItemViewHolder)holder).tv.setText(bean.getMobile_name());
            ((ItemViewHolder)holder).tv.setOnClickListener(new ItemClickListener(position));

            if (bean.isChoose()){
                ((ItemViewHolder)holder).tv.setBackground(ContextCompat.getDrawable(SystemUtil.getContext(), R.drawable.sort_bg));
                ((ItemViewHolder)holder).tv.setTextColor(ContextCompat.getColor(SystemUtil.getContext(), R.color.white));
            }else {
                ((ItemViewHolder)holder).tv.setBackground(null);
                ((ItemViewHolder)holder).tv.setTextColor(ContextCompat.getColor(SystemUtil.getContext(), R.color.colorBottomBlack));
            }

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
            Log.e(TAG, "onClick: " + list.get(position).getId());
            for (int i = 0; i < list.size(); i++) {
                list.get(i).setChoose(false);
            }
            list.get(position).setChoose(true);

            notifyDataSetChanged();

            EventBus.getDefault().post(new SortEvent(IEventTag.REFRESH_RIGHT_LIST, list.get(position).getId()));

        }
    }
}
