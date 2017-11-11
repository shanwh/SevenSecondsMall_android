package com.yidu.sevensecondmall.views.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Order.AttrsBean;
import com.yidu.sevensecondmall.utils.SpecHelper;
import com.yidu.sevensecondmall.utils.UiData;
import com.yidu.sevensecondmall.views.widget.FlowGridView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/23.
 */
public class TypeItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<String> namelist;
    private HashMap<String,List<AttrsBean.GoodsSpecListBean>> allsort;
    private UiData data;
    private String key = "";
    private List<GridAdapter>adapterList = new ArrayList<>();
    private Refresh refreshlistener;
    private SpecHelper helper = SpecHelper.getInstance();


    public TypeItemAdapter(Context context, List<String> namelist,
                           HashMap<String,List<AttrsBean.GoodsSpecListBean>>allsort,UiData data) {
        this.context = context;
        this.namelist = namelist;
        inflater = LayoutInflater.from(context);
        this.allsort = allsort;
        this.data = data;
//        if(key.equals("")&&allsort != null&&allsort.size() > 0){
//            key = (TextUtil.transToAttrs(mullist.get(0)))[0];
//        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TypeViewHolder(inflater.inflate(R.layout.typeitem_list, null));
    }

    //当前选择的类型
    public void setKey(int key){
        this.key = String.valueOf(key);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof TypeViewHolder){
            String name = namelist.get(position);
            ((TypeViewHolder) holder).sortName.setText(namelist.get(position));
            ((TypeViewHolder) holder).adapter = new GridAdapter(context,allsort.get(namelist.get(position)),data,key);
            ((TypeViewHolder) holder).adapter.setOnRefreshListener(new GridAdapter.RefreshPop() {
                @Override
                public void refreshpop() {
                    if(helper.getMap().size() == namelist.size()){
                        refreshlistener.refresh();
                    }
                }
            });
//            ((TypeViewHolder) holder).adapter = new TagAdapter<AttrsBean.GoodsSpecListBean>(allsort.get(namelist.get(position))) {
//                @Override
//                public View getView(FlowLayout parent, int position, AttrsBean.GoodsSpecListBean o) {
//                    View itemview = inflater.inflate(R.layout.layout_griditem,parent,false);
//                    TextView text = (TextView)itemview.findViewById(R.id.content);
//                    text.setText(o.getItem());
//                    return itemview;
//                }
//            };
            ((TypeViewHolder) holder).itemList.setAdapter(((TypeViewHolder) holder).adapter);
//            ((TypeViewHolder) holder).itemList.setNumColumns(3);
            if(!adapterList.contains(((TypeViewHolder) holder).adapter)){
                adapterList.add(((TypeViewHolder) holder).adapter);
            }
        }
    }

    public void clearSelect(){
        for(GridAdapter adapter : adapterList){
            adapter.clearSelect();
        }
    }

    @Override
    public int getItemCount() {
        return namelist.size();
    }

    public interface Refresh{
        void refresh();
    }

    public void setOnRefreshListener(Refresh refreshlistener){
        this.refreshlistener = refreshlistener;
    }

    class TypeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_list)
        FlowGridView itemList;
        @BindView(R.id.sort_name)
        TextView sortName;
        private GridAdapter adapter;
        private GridLayoutManager manager;

        public TypeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
