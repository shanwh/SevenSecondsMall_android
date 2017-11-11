package com.yidu.sevensecondmall.Activity.Distribution;

import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.DAO.UserDAO;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Distribution.DingCoinRecordInfo;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.i.IEventTag;
import com.yidu.sevensecondmall.i.LoadDataEvent;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.CustomRecyclerView;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.MultiTypeAdapter;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.Visitable;
import com.yidu.sevensecondmall.views.widget.iconfonts.IconFontTextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/8/21 0021.
 */
public class DingCoinRecordActivity extends BaseActivity {

    @BindView(R.id.back)
    IconFontTextView back;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_head)
    RelativeLayout rlHead;
    @BindView(R.id.custom_rv)
    CustomRecyclerView customRv;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    private int page = 1;
    private boolean isFirst = true;

    @Override
    protected int setViewId() {
        return R.layout.activity_ding_coin_record;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
        toolbarTitle.setText("DING宝明细");
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void init() {

    }

    @Override
    protected void loadData() {
        if (isFirst) {
            isFirst = false;
            loadNetWorkData();
        }
    }

    private void loadNetWorkData() {
        UserDAO.getMyDingCoinRecord(page, new BaseCallBack() {
            @Override
            public void success(Object data) {
                DingCoinRecordInfo info = (DingCoinRecordInfo) data;
                ArrayList<Visitable> list = info.getList();
                MultiTypeAdapter adapter = customRv.getAdapter();
                if (page == 1) {
                    if (list.size() == 0) {
                        customRv.showEmptyView();
                    } else {
                        customRv.hideEmptyView();
                    }
                    adapter.refreshData(list);
                    if (customRv.isRefreshing()) {
                        showShortToast("刷新成功");
                    }
                    customRv.stopSwipeRefresh();
                } else {
                    adapter.addMoreData(list);
                }
            }

            @Override
            public void failed(int errorCode, Object data) {
                showShortToast(data + "");
            }
        });
    }


    @OnClick(R.id.back)
    public void onClick() {
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handlerCenter(LoadDataEvent ev) {
        switch (ev.functionTag) {
            case IEventTag.LOAD_DATA:
                page = ev.page;
                loadNetWorkData();
                break;
        }
    }

}
