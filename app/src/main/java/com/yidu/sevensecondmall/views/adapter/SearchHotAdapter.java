package com.yidu.sevensecondmall.views.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.i.IEventTag;
import com.yidu.sevensecondmall.i.UIEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/24.
 */
public class SearchHotAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;
    private LayoutInflater inflater;
    private List<String> hotlist;
    private String currentTag = "";

    public SearchHotAdapter(Context context,List<String> hotlist) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.hotlist = hotlist;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HotViewHolder(inflater.inflate(R.layout.layout_searchitem, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof HotViewHolder){
            ((HotViewHolder) holder).text.setText(hotlist.get(position)+"");
            ((HotViewHolder) holder).item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentTag = hotlist.get(position);
                    EventBus.getDefault().post(new UIEvent(IEventTag.REFRESHEDIT));
                }
            });
        }
    }

    public String getTag(){
        return currentTag;
    }

    @Override
    public int getItemCount() {
        return hotlist.size();
    }

    public class HotViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text)
        TextView text;
        @BindView(R.id.item)
        LinearLayout item;
        public HotViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
