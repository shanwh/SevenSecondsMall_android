package com.yidu.sevensecondmall.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yidu.sevensecondmall.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/13.
 */
public abstract class BaseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(setViewId(), container, false);
        ButterKnife.bind(this,view);
        EventBus.getDefault().register(this);
        findViews(view);
        initEvent();
        init();
        loadData();
        return view;
    }

    @Subscribe
    public void onEvent(String event) {

    }

    /**
     * 设置布局id
     *
     * @return
     */
    protected abstract int setViewId();

    /**
     * 初始化控件
     */
    protected abstract void findViews(View view);

    /**
     * 初始化控件的点击事件
     */
    protected abstract void initEvent();

    /**
     * 初始化数据
     */
    protected abstract void init();

    /**
     * 加载数据
     */
    protected abstract void loadData();

    public void showShortToast(String str){
        ToastUtil.showToast(getActivity(), str);
    }


    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }


}
