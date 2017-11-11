package com.yidu.sevensecondmall.views.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Others.StoreClassificationBean;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.MultiTypeAdapter;

/**选择商家的商品分类
 * Created by Administrator on 2017/8/23.
 */
public class StoreClassHolder extends BaseContextViewHolder {
    public StoreClassHolder(View itemView, Context context) {
        super(itemView, context);
    }

    @Override
    public void setUpView(Object model, int position, MultiTypeAdapter adapter) {
        final TextView tvName = (TextView) getView(R.id.tv_class_name);
        tvName.setText(((StoreClassificationBean)model).getClassifications());
        getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getHolderContext(), tvName.getText()+"",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
