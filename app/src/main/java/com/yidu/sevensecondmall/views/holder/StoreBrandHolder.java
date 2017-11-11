package com.yidu.sevensecondmall.views.holder;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Others.StoreBrandBean;
import com.yidu.sevensecondmall.views.adapter.BaseTextAdapter;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.GridItemDecoration;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.MultiTypeAdapter;

/**选择商店内的品牌
 * Created by Administrator on 2017/8/23.
 */
public class StoreBrandHolder extends BaseContextViewHolder {

    public StoreBrandHolder(View itemView, Context context) {
        super(itemView, context);
    }

    @Override
    public void setUpView(Object model, int position, MultiTypeAdapter adapter) {
        TextView tvBrand = (TextView) getView(R.id.tv_brand);
        final RecyclerView subBrandList = (RecyclerView) getView(R.id.rv_sub_brands);
        final StoreBrandBean bean = (StoreBrandBean) model;
        //设置内容高度]
        int length = bean.getSubBrands().size();

        Context context = getHolderContext();
        tvBrand.setText(bean.getBrand());
        GridLayoutManager manager = new GridLayoutManager(context, 2);
        GridItemDecoration decoration =new GridItemDecoration
                .Builder(context)
                .size(8)
                .color(R.color.white)
                .margin(0,0)
                .build();
        subBrandList.setLayoutManager(manager);
        subBrandList.setHasFixedSize(true);
        subBrandList.addItemDecoration(decoration);
        subBrandList.setAdapter(new BaseTextAdapter(bean.getSubBrands(), context));
        getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subBrandList.setVisibility(subBrandList.getVisibility()==View.GONE? View.VISIBLE:View.GONE);
            }
        });
    }
}
