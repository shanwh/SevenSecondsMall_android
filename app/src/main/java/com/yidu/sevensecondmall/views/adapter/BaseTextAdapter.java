package com.yidu.sevensecondmall.views.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yidu.sevensecondmall.R;

import java.util.List;

/**
 * Created by Administrator on 2017/8/23.
 */
public class BaseTextAdapter extends RecyclerView.Adapter{

  //  private LayoutInflater inflater;
    private List<String> models;
    private Context context;

    public BaseTextAdapter(List<String> models, Context context) {
        this.models = models;
        this.context = context;
        //inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =View.inflate(context,R.layout.item_text,null);
        return new TextViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((TextViewHolder)holder).tv.setText(models.get(position));
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class TextViewHolder extends RecyclerView.ViewHolder{
        TextView tv;
        public TextViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv);
        }
    }
}
