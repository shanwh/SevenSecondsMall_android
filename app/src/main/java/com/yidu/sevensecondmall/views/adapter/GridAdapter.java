package com.yidu.sevensecondmall.views.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Order.AttrsBean;
import com.yidu.sevensecondmall.utils.SpecHelper;
import com.yidu.sevensecondmall.utils.UiData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/23.
 */
public class GridAdapter extends android.widget.BaseAdapter{

    private List<AttrsBean.GoodsSpecListBean> list;
    private UiData data;
    private Context context;
    private String key;
    private boolean clickable = false;
    private SpecHelper helper;
    private boolean[] select;
    private StringBuffer buffer;
    private StringBuffer namebuffer;
    private RefreshPop poplistener;

    public GridAdapter(Context context,List<AttrsBean.GoodsSpecListBean> list,UiData data,String key) {
        this.context = context;
        this.list = list;
        this.data = data;
        this.key = key;
        helper = SpecHelper.getInstance();
        select= new boolean[100];
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

    public List<AttrsBean.GoodsSpecListBean> getList(){
        return list;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_griditem,null);
//            viewHolder.text = (TextView)convertView.findViewById(R.id.content);
            viewHolder.item = (LinearLayout)convertView.findViewById(R.id.item);
            viewHolder.button = (RadioButton)convertView.findViewById(R.id.content);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.button.setText(list.get(position).getItem()+"");
//        if(select[position]){
//            viewHolder.button.setChecked(true);
//        }else {
//            viewHolder.button.setChecked(false);
//        }

        switch(list.get(position).getStatus()){
            case 0:
                //未选
                viewHolder.button.setChecked(false);
                viewHolder.button.setTextColor(Color.BLACK);
//                viewHolder.button.setBackgroundResource(R.color.un_select_color);
                viewHolder.button.setClickable(true);
                break;
            case 1:
                //可选
                viewHolder.button.setChecked(true);
                viewHolder.button.setTextColor(Color.WHITE);
//                viewHolder.button.setBackgroundResource(R.color.un_select_color);
                viewHolder.button.setClickable(true);
                break;
            case 2:
                //不可选
                viewHolder.button.setChecked(false);
//                viewHolder.button.setBackgroundColor(Color.GRAY);
                viewHolder.button.setClickable(false);
                break;
        }


        viewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(list.get(position).getStatus() == 2){
//                    return;
//                }
                for(int i = 0;i < list.size();i++){
                    if(String.valueOf(list.get(position).getItem_id()).equals(String.valueOf(list.get(i).getItem_id()))){
                        list.get(position).setStatus(1);
                    }else {
                        list.get(position).setStatus(list.get(position).getStatus() == 2 ? 2 : 0);
                    }
                }



//                for(int i = 0; i < list.size();i++){
//                    if(i == position){
//                        select[i] = true;
//                    }else {
//                        select[i] = false;
//                    }
//                }

                //将点击的按钮全部存放起来
                if(!helper.Selected(list.get(position))){
                    helper.SelectSpec(list.get(position));
                }

                //处理没有点击的按钮
                for(int i = 0;i < list.size();i++){
                    //当库存为0以及id为空时候
                    if(data.getResult().get(String.valueOf(list.get(i).getItem_id())) == null
                            ||data.getResult().get(String.valueOf(list.get(i).getItem_id())).getStock() <= 0){
                        list.get(i).setStatus(2);
                    }else if(helper.Selected(list.get(position)) && list.get(position).getItem_id() == list.get(i).getItem_id()){
                        //可选时候
                        list.get(i).setStatus(1);
                    }else {
                        list.get(i).setStatus(0);
                    }
                }

                //先添加数据到list里面，再冒泡排序从小到大重新排序整个规格key
                List<AttrsBean.GoodsSpecListBean> cachelist = new ArrayList<AttrsBean.GoodsSpecListBean>();
                HashMap<String,AttrsBean.GoodsSpecListBean> cachemap = helper.getMap();
                for(Map.Entry<String,AttrsBean.GoodsSpecListBean>item : cachemap.entrySet()){
                    cachelist.add(item.getValue());
                }

                //冒泡排序
                for(int i = 0;i < cachelist.size()-1;i++){
                    for(int j = 0; j < cachelist.size()-1-i;j++){
                        AttrsBean.GoodsSpecListBean bean;
                        if(cachelist.get(j).getItem_id() > cachelist.get(j+1).getItem_id()){
                            bean = cachelist.get(j);
                            cachelist.set(j,cachelist.get(j+1));
                            cachelist.set(j+1,bean);
                        }
                    }
                }

                //重组list会组合属性
                buffer = new StringBuffer();
                namebuffer = new StringBuffer();
                for(int i = 0;i < cachelist.size();i++){
                    buffer.append(String.valueOf(cachelist.get(i).getItem_id())+"_");
                    namebuffer.append(String.valueOf(cachelist.get(i).getSpec_name()+"_"));
                }
                helper.setStringbuffer(buffer);
                helper.setName(namebuffer);
                Log.e("namebuffer",namebuffer.toString());
                Log.e("buffer",buffer+"");

                //检查数据
                if(data.getResult().get(buffer.substring(0,buffer.length()-1)) != null
                        && data.getResult().get(buffer.substring(0,buffer.length()-1)).getStock() > 0){
                    Log.e("datacheck",list.get(position).getStatus()+"");
                    list.get(position).setStatus(list.get(position).getStatus() == 1 ? 1 :0);
                }else {
                    list.get(position).setStatus(2);
                }

                for(Map.Entry<String,AttrsBean.GoodsSpecListBean> item : helper.getMap().entrySet()){
                    Log.e(item.getKey()+":",""+item.getValue().getItem());
                }
                switch (list.get(position).getStatus()){
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        Toast.makeText(context,"该规格已售完",Toast.LENGTH_SHORT).show();
                        break;
                }

                poplistener.refreshpop();

                notifyDataSetChanged();

//                EventBus.getDefault().post(new IAttrsEvent(list.get(position).getItem_id()));
            }
        });

        return convertView;
    }

    public StringBuffer getBuffer(){
        return buffer;
    }

    public void clearSelect(){
        for(int i = 0;i<list.size();i++){
            list.get(i).setStatus(0);
        }
        notifyDataSetChanged();
    }

    public interface RefreshPop{
        void refreshpop();
    }

    public void setOnRefreshListener(RefreshPop pop){
        this.poplistener = pop;
    }

    public class ViewHolder{
        TextView text;
        LinearLayout item;
        RadioButton button;
    }


}
