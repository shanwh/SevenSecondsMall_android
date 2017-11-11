package com.yidu.sevensecondmall.views.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.views.widget.iconfonts.IconFontTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/26.
 */
public class PicAdapter extends BaseAdapter{

    public Context context;
    public List<String> list = new ArrayList<>();
    private LayoutInflater inflater;


    public PicAdapter(Context context,ArrayList<String> list){
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size()+1;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.pic_select_item,null);
            holder = new ViewHolder();
            holder.imageView = (ImageView)convertView.findViewById(R.id.pic);
            holder.textView = (IconFontTextView)convertView.findViewById(R.id.select_pic);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        if(list.size() == 0){
            holder.imageView.setVisibility(View.INVISIBLE);
            holder.textView.setVisibility(View.VISIBLE);
        }else {
            if(position < list.size()){
                holder.imageView.setVisibility(View.VISIBLE);
                holder.textView.setVisibility(View.INVISIBLE);
                Glide.with(context)
                        .load(list.get(position))
                        .into(holder.imageView);
            }else if(position == list.size()) {
                holder.imageView.setVisibility(View.INVISIBLE);
                holder.textView.setVisibility(View.VISIBLE);
            }
        }

        if(position == 4){
            holder.imageView.setVisibility(View.INVISIBLE);
            holder.textView.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }


    class ViewHolder{
        ImageView imageView;
        IconFontTextView textView;
    }
}
