package com.yidu.sevensecondmall.fragments.Store;

import android.view.View;

import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.fragments.BaseFragment;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/8/25.
 */
public class NewGoodsFragment extends BaseFragment {
    @Override
    protected int setViewId() {
        return R.layout.fragment_store_newgoods;
    }

    @Override
    protected void findViews(View view) {
        ButterKnife.bind(getActivity());
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void init() {

    }

    @Override
    protected void loadData() {

    }
}
