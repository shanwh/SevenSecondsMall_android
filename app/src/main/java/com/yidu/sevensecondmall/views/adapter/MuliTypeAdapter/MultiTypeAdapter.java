package com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.yidu.sevensecondmall.i.IBottom2;
import com.yidu.sevensecondmall.views.adapter.BaseViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/3/29 0029.
 */
public class MultiTypeAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private TypeFactory typeFactory;
    private List<Visitable> models;
    private IBottom2 iBottom;

    public MultiTypeAdapter(List<Visitable> models) {
        this.models = models;
        this.typeFactory = new TypeFactoryForList();
    }

    public MultiTypeAdapter(List<Visitable> models, Context context) {
        this.models = models;
        this.typeFactory = new TypeFactoryForList(context);
    }

    public MultiTypeAdapter(List<Visitable> models, IBottom2 iBottom) {
        this.models = models;
        this.typeFactory = new TypeFactoryForList();
        this.iBottom = iBottom;
    }

    public MultiTypeAdapter(List<Visitable> models, Context context, IBottom2 iBottom) {
        this.models = models;
        this.typeFactory = new TypeFactoryForList(context);
        this.iBottom = iBottom;
    }

    public void addMoreData(List<Visitable> models) {
        this.models.addAll(models);
        notifyDataSetChanged();
    }

    public void refreshData(List<Visitable> models) {
        if (this.models != null) {
            this.models.clear();
            this.models.addAll(models);
        } else {
            this.models = models;
        }
        notifyDataSetChanged();
    }

    private void specialUpdate() {
        Handler handler = new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                notifyItemChanged(getItemCount() - 1);
            }
        };
        handler.post(r);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
//        View itemView=  LayoutInflater.from(context).inflate(viewType,parent,false);
       View itemView = View.inflate(context, viewType, null);
        return typeFactory.createViewHolder(viewType, itemView);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.setUpView(models.get(position), position, this);
        if (iBottom != null) {
            if (position == getItemCount() - 1) {
                iBottom.isBottom(true, models.get(position).isList);
            } else {
                iBottom.isBottom(false, models.get(position).isList);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (null == models) {
            return 0;
        }
        return models.size();
    }


    @Override
    public int getItemViewType(int position) {
        return models.get(position).type(typeFactory);
    }

    public List<Visitable> getModels() {
        return models;
    }

}
