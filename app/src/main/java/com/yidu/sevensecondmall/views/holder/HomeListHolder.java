package com.yidu.sevensecondmall.views.holder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Main.GoodListBean;
import com.yidu.sevensecondmall.bean.Main.HomeGoodBean;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.CustomRecyclerView;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.MultiTypeAdapter;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.Visitable;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/6 0006.
 */
public class HomeListHolder  extends BaseContextViewHolder<HomeGoodBean> {
    public HomeListHolder(View itemView, Context context) {
        super(itemView, context);
    }

    @Override
    public void setUpView(final HomeGoodBean model, final int position, final MultiTypeAdapter adapter) {
        final Context context = getHolderContext();

        ArrayList<Visitable> list = model.getList();


//        RecyclerView rv = (RecyclerView) getView(R.id.rv);
//        GridLayoutManager mGridLayoutManager = new GridLayoutManager(context, 2);
//        mGridLayoutManager.setOrientation(OrientationHelper.VERTICAL);
//        rv.setLayoutManager(mGridLayoutManager);
//        rv.setItemAnimator(new DefaultItemAnimator());
//        MultiTypeAdapter childAdapter = new MultiTypeAdapter(showList, context);
//        rv.setAdapter(childAdapter);

        CustomRecyclerView customRv = (CustomRecyclerView) getView(R.id.rv);
        setData(customRv, list);


    }

    private void setData(CustomRecyclerView customRv, ArrayList<Visitable> list){
        MultiTypeAdapter adapter = customRv.getAdapter();

        if (list.size() == 0) {
            customRv.showEmptyView();
        } else {
            customRv.hideEmptyView();
        }
        adapter.refreshData(list);
        customRv.stopSwipeRefresh();
    }

}