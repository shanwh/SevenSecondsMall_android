package com.yidu.sevensecondmall.views.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yidu.sevensecondmall.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/26.
 */
public class typeAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> list;

    public typeAdapter(Context context, ArrayList<String>list){
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewholder;
        if(convertView == null){
            viewholder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.type_list_item,null);
            viewholder.text = (TextView)convertView.findViewById(R.id.text);
            viewholder.item = (LinearLayout)convertView.findViewById(R.id.item);
            convertView.setTag(viewholder);
        }else {
            viewholder = (ViewHolder) convertView.getTag();
        }
        viewholder.text.setText(list.get(position));
//        viewholder.item.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                EventBus.getDefault().post(new EventBehaveBean(list.get(position)));
//            }
//        });

        return convertView;
    }

    public class ViewHolder{
        TextView text;
        LinearLayout item;
    }
}
