package com.yidu.sevensecondmall.Activity.Distribution;

import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.DAO.UserDAO;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.i.IEventTag;
import com.yidu.sevensecondmall.i.LoadDataEvent;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.CustomRecyclerView;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.MultiTypeAdapter;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.Visitable;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/8/17 0017.
 */
public class AssignmentActivity extends BaseActivity {
    @BindView(R.id.rl_head)
    RelativeLayout rlHead;
    @BindView(R.id.custom_rv)
    CustomRecyclerView customRv;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    private int page = 1;


    @Override
    protected int setViewId() {
        return R.layout.activity_assignment;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
        toolbarTitle.setText("赠品转让区");
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void init() {
        UserDAO.getFreeOrderSellList(new BaseCallBack() {
            @Override
            public void success(Object data) {
                ArrayList<Visitable> list = (ArrayList<Visitable>) data;
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
//        for (int i = 0; i < 10; i++) {
//            AssignmentBean bean = new AssignmentBean();
//            bean.setPresent(presents[i]);
//            list.add(bean);
//        }

    }

    @Override
    protected void loadData() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handlerCenter(LoadDataEvent event) {
        switch (event.functionTag) {
            case IEventTag.LOAD_DATA:
                page = event.page;
                init();
                break;
        }
    }

}
