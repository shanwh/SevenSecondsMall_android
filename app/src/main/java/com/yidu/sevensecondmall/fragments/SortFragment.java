package com.yidu.sevensecondmall.fragments;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.yidu.sevensecondmall.Activity.Order.SearchActivity;
import com.yidu.sevensecondmall.DAO.GoodsDAO;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Order.STCategoryBean;
import com.yidu.sevensecondmall.bean.Order.goodCategoryBean;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.i.IEventTag;
import com.yidu.sevensecondmall.i.SortEvent;
import com.yidu.sevensecondmall.views.adapter.LeftSortRcvAdapter;
import com.yidu.sevensecondmall.views.adapter.RightSortRcvAdapter;
import com.yidu.sevensecondmall.views.widget.CustomLinearLayoutManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/6 0006.
 */
public class SortFragment extends BaseFragment {

    private static final String TAG = "SortFragment";

    @BindView(R.id.classify_left_list)
    RecyclerView classifyLeftList;
    @BindView(R.id.classify_right_list)
    RecyclerView classifyRightList;

    int currentLeftIndex;
    int clickLeftIndex;

    private ArrayList<goodCategoryBean> leftList = new ArrayList<>();
    private ArrayList<STCategoryBean> rightList = new ArrayList<>();

    private RightSortRcvAdapter rightAdapter;
    private LeftSortRcvAdapter adapter;
    @Override
    protected int setViewId() {
        return R.layout.fragment_sort;
    }

    @Override
    protected void findViews(View view) {
        ButterKnife.bind(this, view);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void init() {
        adapter = new LeftSortRcvAdapter(getActivity(), leftList);
        CustomLinearLayoutManager layoutManager = new CustomLinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        classifyLeftList.setLayoutManager(layoutManager);
        classifyLeftList.setAdapter(adapter);

        rightAdapter = new RightSortRcvAdapter(getActivity(), rightList);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity());
        layoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        classifyRightList.setLayoutManager(layoutManager2);
        classifyRightList.setAdapter(rightAdapter);

    }

    @Override
    protected void loadData() {
        GoodsDAO.getgoodsCategoryList(0, new BaseCallBack() {
            @Override
            public void success(Object data) {
                List<goodCategoryBean> list = (List<goodCategoryBean>)data;
                Log.e(TAG, "SortFragment success: "+ list.size() );
                adapter.refreshData((ArrayList<goodCategoryBean>) list);
                EventBus.getDefault().post(new SortEvent(IEventTag.REFRESH_RIGHT_LIST, list.get(0).getId()));
            }

            @Override
            public void failed(int errorCode, Object data) {
                Toast.makeText(getActivity(), ""+data, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public SortFragment() {
//        EventBus.getDefault().register(this);
    }


    private void refreshRightLayout() {
        try {
            if (clickLeftIndex == currentLeftIndex) return;
            GoodsDAO.getSecThird(clickLeftIndex, new BaseCallBack() {
                @Override
                public void success(Object data) {
                    currentLeftIndex = clickLeftIndex;
                    List<STCategoryBean> list = (List<STCategoryBean>)data;
                    Log.e(TAG, "success: "+list.size() );

                    if (rightAdapter == null)return;
                    rightAdapter.refreshData((ArrayList<STCategoryBean>) list);
                }

                @Override
                public void failed(int errorCode, Object data) {
                    currentLeftIndex = clickLeftIndex;
                    ArrayList<STCategoryBean> list = new ArrayList<>();
                    if (rightAdapter == null)return;
                    rightAdapter.refreshData(list);
                    Toast.makeText(getActivity(), ""+data, Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void searchSort(int id, String keyWork){
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        intent.putExtra("id", id);
//        intent.putExtra("keyWork",keyWork);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handler(SortEvent event) {
        switch (event.founctionTag) {
            case IEventTag.REFRESH_RIGHT_LIST:
                Log.e(TAG, "handler: "+ event.id);
                clickLeftIndex = event.id;
                refreshRightLayout();
                break;
            case IEventTag.SEARCH_SORT:
                Log.e(TAG, "handler: "+ event.id);
                searchSort(event.id, event.keyWork);
                break;
        }
    }
}
