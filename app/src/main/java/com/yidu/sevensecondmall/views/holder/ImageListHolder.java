package com.yidu.sevensecondmall.views.holder;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Main.ImageListBean;
import com.yidu.sevensecondmall.views.adapter.ImageAdapter;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.MultiTypeAdapter;

import static android.widget.GridLayout.VERTICAL;

/**
 * Created by Administrator on 2017/9/7.
 */

public class ImageListHolder extends BaseContextViewHolder {

    public ImageListHolder(View itemView, Context context) {
        super(itemView, context);
    }

    @Override
    public void setUpView(Object model, int position, MultiTypeAdapter adapter) {
        Log.e("ImageListHolder","--------------------------------");
        RecyclerView rv  = (RecyclerView) getView(R.id.rv_list);
        GridLayoutManager manager = new GridLayoutManager(getHolderContext(),2);
        manager.setOrientation(VERTICAL);
        rv.setLayoutManager(manager);
        ImageAdapter ada = new ImageAdapter(getHolderContext(),((ImageListBean)model).getList());
        rv.setFocusableInTouchMode(false);//设置不需要焦点
        rv.requestFocus();
        rv.setAdapter(ada);
        // TODO: 2017/9/7
    }


}
